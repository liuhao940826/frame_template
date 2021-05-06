package com.ovopark.web.aspect;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ovopark.constants.CommonConstants;
import com.ovopark.constants.ConfigurationConstants;
import com.ovopark.expection.SysErrorException;
import com.ovopark.model.login.TokenInfo;
import com.ovopark.model.login.Users;
import com.ovopark.pojo.BaseResult;
import com.ovopark.pojo.sso.TokenValueResp;
import com.ovopark.service.UserRemoteService;
import com.ovopark.utils.CommonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.ovopark.context.HttpContext;
import com.ovopark.expection.ResultCode;
import com.ovopark.model.resp.JsonNewResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Random;

/**
 * 服务请求拦截
 * @author jim
 * @date 16/10/12
 */
@Aspect
@Component
public class ServiceRequestAspect {
    private static final Logger logger = LoggerFactory.getLogger(ServiceRequestAspect.class);
    private static final Logger defaultLogger = LoggerFactory.getLogger("R-R-LOG");


    @Value("${ovopark.sso.server.url}")
    private String  ssoServerUrl;

    @Autowired
    private UserRemoteService userRemoteService;

    @Autowired
    ConfigurationConstants configurationConstants;


    @Pointcut("execution(public * com.ovopark.web.controller..*(..))")
    private void allMethod() {
    }

    @Around("allMethod()")
    public Object doAround(ProceedingJoinPoint call) throws Throwable {
        if (call.getArgs().length == 0) {
            return call.proceed();
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();

        //生成traceId
        HttpContext.start(request, response);

        //处理用户信息
        boolean vaildTokenResult = vaildToken(request, response);

        if(!vaildTokenResult){
            return JsonNewResult.error(ResultCode.RESULT_INVALID_TOKEN);
        }

        String reqParams="";
        // 获取参数, 只取自定义的参数, 自带的HttpServletRequest, HttpServletResponse不管
        if (call.getArgs().length > 0) {
            for (Object o : call.getArgs()) {
                if (o instanceof HttpServletRequest || o instanceof HttpServletResponse) {
                    continue;
                }
                logger.info("请求参数 : " + JSON.toJSONString(o));
                reqParams+=JSON.toJSONString(o);
            }
        }
        String api = request.getRequestURI();

        String reqId = getRandom(10);
        String ip = getIpAddress(request);
        defaultLogger.info("[" + reqId + ",REQUEST]" + api + "," + ip + "[" + reqParams + "]");
        Date startDate = new Date();
        boolean requestResult = false;


        try {
            //3.请求处理
            Object object = call.proceed();
            defaultLogger.info("[" + reqId + ",RESPONSE]" + JSONObject.toJSONString(object));
            requestResult = true;
            return object;

        }  catch (Exception e) {
            logger.error("网关其它处理异常", e);
            defaultLogger.info("[" + reqId + "RESPONSE_ERROR]" + ResultCode.SYS_ERROR.getDesc());
            return JsonNewResult.error(ResultCode.SYS_ERROR, e.getMessage());
        } finally {
            Date endDate = new Date();
            long runTime = endDate.getTime() - startDate.getTime();
            String log = String.format("[%s]PERFORMANCE:%s,%s,%sms", reqId, api, requestResult, runTime);
            defaultLogger.info(log);
        }
    }

    private <T extends Annotation> T getAnnotation(ProceedingJoinPoint jp, Class<T> clazz) {
        MethodSignature joinPointObject = (MethodSignature) jp.getSignature();
        Method method = joinPointObject.getMethod();
        return method.getAnnotation(clazz);
    }

    private static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private static Random random = new Random();

    private static String getRandom(int length) {
        StringBuilder ret = new StringBuilder();

        for (int i = 0; i < length; ++i) {
            boolean isChar = random.nextInt(2) % 2 == 0;
            if (isChar) {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                ret.append((char) (choice + random.nextInt(26)));
            } else {
                ret.append(Integer.toString(random.nextInt(10)));
            }
        }

        return ret.toString();
    }

    /**
     * token的校验逻辑
     * @param request
     * @param response
     */
    private boolean vaildToken(HttpServletRequest request,HttpServletResponse response) {

        boolean isNewLogin = false;
        String token = request.getParameter("token");

        //新的token信息
        String authorization = request.getHeader("Ovo-Authorization");

        if (StringUtils.isEmpty(authorization)) {
            authorization = request.getHeader("authorization");
        }

        if (StringUtils.isEmpty(authorization)) {
            authorization = request.getParameter("ticket");
        }

        if (!StringUtils.isEmpty(authorization)) {
            isNewLogin = true;
        }

        if (isNewLogin) {
            //解析新的
            String[] auths = authorization.split(" ");
            //组装clientInfo信息
            token = assemblyClientInfo(auths, token);

            HttpResponse ovoResult = HttpUtil.createGet(ssoServerUrl + CommonConstants.PARSE_TOKEN_URL + "?token=" + token).timeout(3000).execute();
            if (ovoResult == null || ovoResult.getStatus() != 200 || StringUtils.isEmpty(ovoResult.body())) {
                logger.error("调用sso失败");
                throw new SysErrorException(ResultCode.RESULT_INVALID_TOKEN);
            }
            BaseResult<TokenValueResp> tokenValueBaseResult = null;
            try {
                tokenValueBaseResult = JSON.parseObject(ovoResult.body(), new TypeReference<BaseResult<TokenValueResp>>() {
                });
            } catch (Exception e) {
                logger.error("异常信息", e);
                throw new SysErrorException(ResultCode.INTERNAL_SERVER_ERROR);
            }
            //处理用户信息
            if (!tokenValueBaseResult.getIsError()) {
                TokenValueResp resp = tokenValueBaseResult.getData();
                //获取用户
                Users user = userRemoteService.queryUserById(resp.getUserId());
                //这个一定要放在前面赋值
                HttpContext.setContextInfoUser(user);
                return true;
            } else {
                // 解析code
                throw new SysErrorException(tokenValueBaseResult.getCode());
            }
        } else {
            String authenticator = request.getHeader("authenticator");
            //不为空 去处理截断逻辑
            if (authenticator != null) {
                String[] auths = authenticator.split(" ");

                //组装clientInfo信息
                token = assemblyClientInfo(auths, token);
            }

            //设置user
            if (!StringUtils.isEmpty(token)) {
                TokenInfo tokenBo = CommonUtil.decodeToken(token);
                Integer kUserId = tokenBo.getUserId();
                if (kUserId != null) {
                    Users user = userRemoteService.queryUserById(kUserId);
                    if (user != null) {
                        String tokenPassword = tokenBo.getPassword();
                        String dbPassword = user.getPassword();
                        Date date = new Date();
                        Date tokenDate = tokenBo.getDate();
                        Date expiredDate = null;
                        if (tokenDate != null) {
                            if (tokenBo.getExpires() != null) {
                                expiredDate = new Date(tokenDate.getTime() + tokenBo.getExpires());
                            } else {
                                Long expiredTime = configurationConstants.getExpiredTime();
                                expiredDate = new Date(tokenDate.getTime() + expiredTime);
                            }
                        }
                        // 用户名错误
                        if (tokenBo.getUserName() != null && !user.getUserName().equals(tokenBo.getUserName())) {
                            return false;
                        }
                        // 密码错误
                        if (!StringUtils.isEmpty(tokenPassword) && !tokenPassword.equals(dbPassword)) {
                            return false;
                        }
                        // token过期
                        if (expiredDate != null && date.after(expiredDate)) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                    HttpContext.setContextInfoUser(user);
                    return true;
                }
                return false;
            } else {
                return false;
            }

        }


    }


    /**
     * 赋值对应的客户短信息
     * @param auths
     * @param token
     */
    private String assemblyClientInfo(String[] auths,String token){
        if (auths.length > 0 && StringUtils.isEmpty(token)) {
            token = auths[0];
        }

        HttpContext.setContextInfoToken(token);

        return  token;
    }


    public String getSsoServerUrl() {
        return ssoServerUrl;
    }

    public void setSsoServerUrl(String ssoServerUrl) {
        this.ssoServerUrl = ssoServerUrl;
    }
}
