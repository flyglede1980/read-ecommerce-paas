package net.yitun.basic.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import net.yitun.basic.Code;

/**
 * <pre>
 * <b>业务结果</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-10-01 15:05:29.583
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2017-10-01 15:05:29.583 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@Accessors(chain = true)
@SuppressWarnings("rawtypes")
public class Result<T> implements Serializable {

    @ApiModelProperty(value = "数据")
    protected T data;

    @ApiModelProperty(value = "状态, 200:成功")
    protected int code;

    @ApiModelProperty(value = "跟踪ID")
    protected Long trace;

    @ApiModelProperty(value = "消息")
    protected String message;

    @ApiModelProperty(value = "完成时间")
    protected Long etimes;

    public final static Result OK = new Result();

    public final static Result FAIL = new Result(Code.BIZ_ERROR, "业务失败");

    public final static Result ERROR = new Result(Code.SERVER_ERROR, "服务异常");

    public final static Result UNEXIST = new Result(Code.NOT_FOUND, "资源不存在");

    public final static Result API_ERROR = new Result(Code.API_ERROR, "API接口不可用");

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public Result() {
        this(Code.OK, null, null);
    }

    public Result(T data) {
        this(Code.OK, null, data);
    }

    public Result(int code, String message) {
        this(code, message, null);
    }

    public Result(int code, String message, T data) {
        super();
        this.data = data;
        this.code = code;
        // this.trace = TraceUtil.tid();
        this.message = message;
        this.etimes = System.currentTimeMillis();
    }

    public boolean ok() {
        return Code.OK == this.code;
    }

}
