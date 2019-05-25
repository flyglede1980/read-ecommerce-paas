package net.yitun.ioften.sys.model.issue;

import javax.validation.constraints.Min;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.basic.model.Page;

/**
 * <pre>
 * <b>分页查询.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年10月17日 下午4:36:45 LH
 *         new file.
 * </pre>
 */
@Setter
@Getter
@ToString
public class IssueQuery extends Page {

    @Min(value = Util.MIN_ID, message = "ID无效")
    @ApiModelProperty(value = "ID")
    protected Long id;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public IssueQuery() {
        super(null, null, null, null);
        this.setSortBy(null);
        this.setPageSize(null);
    }

    @Override
    public IssueQuery setSortBy(String sortBy) {
        super.setSortBy("t1.sequence asc, t1.mtime desc");
        return this;
    }

    @Override
    public Page setPageSize(Integer pageSize) {
        return super.setPageSize(10); // 限制固定返回10条
    }

}
