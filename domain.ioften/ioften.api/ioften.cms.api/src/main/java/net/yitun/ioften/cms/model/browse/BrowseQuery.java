package net.yitun.ioften.cms.model.browse;

import javax.validation.constraints.Min;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.basic.model.Page;

/**
 * <pre>
 * <b>资讯 浏览记录分页查询.</b>
 * <b>Description:</b>
 *
 * <b>Author:</b> XJN
 * <b>Date:</b> 2018-11-12 11:17:00.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月12日 上午11:17:06 XJN
 *         new file.
 * </pre>
 */
@Getter
@Setter
@ToString
public class BrowseQuery extends Page {

    @ApiModelProperty(value = "ID")
    @Min(value = Util.MIN_ID, message = "ID无效")
    protected Long id;

    @ApiModelProperty(value = "用户ID")
    @Min(value = Util.MIN_ID, message = "用户ID无效")
    protected Long uid;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public BrowseQuery() {
        // 排序规则, 默认: ID DESC
        super(null, null, null, "t1.mtime desc");
    }

}
