package self.alan.expection;

/**
 * @Classname SysErrorException
 * @Description 自定义异常model`
 * @Date 2020/7/9 8:10 下午
 * @Created by liuhao
 */
public class SysErrorException extends RuntimeException {
    private int code = -1;

    private ResultCodeInfo resultCodeInfo;
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public SysErrorException(String message) {
        super(message);
    }

    public SysErrorException(ResultCodeInfo error, Object... args) {
        super(String.format(error.getDesc(), args));
        this.resultCodeInfo = error;
        this.code = error.getCode();
    }

    public SysErrorException(Validation validation) {
        super(validation.getErrorMsg());
        this.code = validation.getErrorCode();
        this.resultCodeInfo = new ResultCodeInfo(validation.getErrorCode(), "", validation.getErrorMsg());
    }

    public int getCode() {
        return this.code;
    }

    public ResultCodeInfo getResultCodeInfo() {
        return this.resultCodeInfo;
    }
}
