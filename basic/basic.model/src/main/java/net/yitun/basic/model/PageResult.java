package net.yitun.basic.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import net.yitun.basic.Code;

/**
 * <pre>
 * <b>数据分页</b>
 * <b>Description:</b>
 *
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-11-13 17:31:50.204
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2017-11-13 17:31:50.204 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@Accessors(chain = true)
@SuppressWarnings("rawtypes")
public class PageResult<T> implements Serializable {

    @ApiModelProperty(value = "页码")
    protected Integer pageNo;

    @ApiModelProperty(value = "分页大小")
    protected Integer pageSize;

    @ApiModelProperty(value = "总数")
    protected Long total;

    @ApiModelProperty(value = "总页数")
    protected Integer pages;

    @ApiModelProperty(value = "列表")
    protected List<T> data;

    @ApiModelProperty(value = "状态, 200:成功")
    protected int code;

    @ApiModelProperty(value = "跟踪ID")
    protected Long trace;

    @ApiModelProperty(value = "消息")
    protected String message;

    @ApiModelProperty(value = "完成时间")
    protected Long etimes;

    public static final PageResult OK = new PageResult(Code.OK, null);

    public static final PageResult ERROR = new PageResult(Code.SERVER_ERROR, "服务异常");

    public static final PageResult API_ERROR = new PageResult(Code.API_ERROR, "API服务不可用");

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public PageResult(int code, String message) {
        this(null, null, null, null, null, code, message);
    }

    public PageResult(Long total, Integer pages, List<T> list) {
        this(list, total, pages, null, null, Code.OK, null);
    }

    public PageResult(Long total, Integer pages, Integer pageNo, Integer pageSize, List<T> list) {
        this(list, total, pages, pageNo, pageSize, Code.OK, null);
    }

    public PageResult(List<T> list, Long total, Integer pages, Integer pageNo, Integer pageSize, int code,
            String message) {
        super();
        this.data = list;
        this.total = total;
        this.pages = pages;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.code = code;
        // this.trace = TraceUtil.tid();
        this.message = message;
        this.etimes = System.currentTimeMillis();
    }

}
