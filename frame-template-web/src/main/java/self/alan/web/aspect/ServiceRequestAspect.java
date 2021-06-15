package self.alan.web.aspect;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import self.alan.constants.CommonConstants;
import self.alan.constants.ConfigurationConstants;
import self.alan.expection.SysErrorException;
import self.alan.model.login.TokenInfo;
import self.alan.model.login.Users;
import self.alan.utils.CommonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import self.alan.context.HttpContext;
import self.alan.expection.ResultCode;
import self.alan.model.resp.JsonNewResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 服务请求拦截
 * @author jim
 * @date 16/10/12
 */
@Aspect
@Component
@Order(1)
public class ServiceRequestAspect {
    private static final Logger logger = LoggerFactory.getLogger(ServiceRequestAspect.class);
    private static final Logger defaultLogger = LoggerFactory.getLogger("R-R-LOG");


    @Value("${ovopark.sso.server.url}")
    private String  ssoServerUrl;

    @Autowired
    ConfigurationConstants configurationConstants;


    @Pointcut("execution(public * self.alan.web.controller..*(..))")
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
