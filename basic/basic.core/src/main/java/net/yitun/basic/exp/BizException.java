package net.yitun.basic.exp;

/**
 * <pre>
 * <b>业务异常</b>
 * <b>Description:</b>
 *    当调用某个服务或者方法时，如果有抛出该异常时，需要自行决定是否处理；如果不做处理，将会统一由
 * <b>Author:</b> LH
 * <b>Date:</b> 2018-02-26 14:01:46.854
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018-02-26 14:01:46.854 LH
 *         new file.
 * </pre>
 */
public class BizException extends RuntimeException {

    /** 结果码 */
    protected int code;

    /** 输入数据 */
    protected Object input;

    /** SVUID */
    private static final long serialVersionUID = 1L;

    public BizException(int code) {
        super();
        this.code = code;
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BizException(int code, String message, Object input) {
        super(message);
        this.code = code;
        this.input = input;
    }

    public BizException(int code, String message, Object input, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.input = input;
    }

    public int getCode() {
        return code;
    }

    public Object getInput() {
        return input;
    }

}
