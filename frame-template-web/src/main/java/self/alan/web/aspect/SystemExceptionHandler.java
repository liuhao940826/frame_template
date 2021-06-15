package self.alan.web.aspect;
import self.alan.expection.ResultCode;
import self.alan.expection.ResultCodeInfo;
import self.alan.expection.SysErrorException;
import self.alan.model.resp.JsonNewResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

/**
 * @Classname SystemExceptionHandler
 * @Description
 * @Date 2020/7/9 8:10 下午
 * @Created by liuhao
 */
@ControllerAdvice
public class SystemExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(SystemExceptionHandler.class);

    private static Pattern pat = Pattern.compile("[\u4e00-\u9fa5]");

    /**
     * 系统异常处理
     *
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonNewResult<Object> defaultErrorHandler(final HttpServletRequest req, HttpServletResponse resp, final Exception e) throws Exception {

        logger.error("拦截到错误", e);
        /*  使用response返回    */
        resp.setStatus(HttpStatus.OK.value()); //设置状态码
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType
        resp.setCharacterEncoding("UTF-8"); //避免乱码
        resp.setHeader("Cache-Control", "no-cache, must-revalidate");

        ResultCodeInfo resultCodeInfo = null;
        String cause = null;
        if(e instanceof SysErrorException) {
            SysErrorException de = (SysErrorException) e;
            cause = de.getMessage();
//            logger.error("_trace_id:"+ HttpContext.getTraceId()+";SysErrorException Error reason!" + de.getMessage(), de);
            resultCodeInfo = de.getResultCodeInfo();
            if(null == resultCodeInfo){
                resultCodeInfo = ResultCode.FAILURE;
            }
            return JsonNewResult.error(resultCodeInfo);
        }

        throw e;
    }

    private static boolean isContainsChinese(String str) {
        if (str != null) {
            return pat.matcher(str).find();
        }
        return false;
    }
}
