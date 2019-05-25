package net.yitun.basic.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

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
public class Page implements Serializable {

    @ApiModelProperty(value = "页码, 默认: 1", example = "1", required = true)
    protected int pageNo = 1;

    @ApiModelProperty(value = "分页大小, 默认: 5", example = "5", required = true)
    protected int pageSize = 5;

    @ApiModelProperty(value = "是否统计总记录数, 默认: true", required = false, allowEmptyValue = true)
    protected Boolean count = true;

    @ApiModelProperty(value = "排序规则, 默认: null", required = false, allowEmptyValue = true)
    protected String sortBy = null;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public Page() {
        this(null, null, null, null);
    }

    public Page(Integer pageNo, Integer pageSize, Boolean count, String sortBy) {
        super();
        this.count = count;
        this.sortBy = sortBy;
        this.setPageNo(pageNo);
        this.setPageSize(pageSize);
    }

    public Page setPageNo(Integer pageNo) {
        if (null != pageNo && 1 <= pageNo)
            this.pageNo = pageNo;
        return this;
    }

    public Page setPageSize(Integer pageSize) {
        if (null != pageSize && 1 <= pageSize)
            this.pageSize = pageSize;
        return this;
    }

}
