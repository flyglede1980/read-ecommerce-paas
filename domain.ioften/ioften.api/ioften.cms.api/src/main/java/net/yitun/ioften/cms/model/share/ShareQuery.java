package net.yitun.ioften.cms.model.share;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.model.Page;
import net.yitun.ioften.cms.dicts.ShareWay;

/**
 * <pre>
 * <b>资讯 分享记录分页查询.</b>
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
@Setter
@Getter
@ToString
public class ShareQuery extends Page {

    @ApiModelProperty(value = "ID")
    protected Long id;

    @ApiModelProperty(value = "用户ID")
    protected Long uid;

    @ApiModelProperty(value = "资讯ID")
    protected Long articleId;

    @ApiModelProperty(value = "代码长度为4-16个字符")
    protected String code;

    @ApiModelProperty(value = "资讯名称")
    protected String title;

    @ApiModelProperty(value = "分享途径, OTHER:其他; TWITTER:微博; WECHAT:微信; QQ:QQ空间")
    protected ShareWay pathway;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public ShareQuery() {
        // 排序规则, 默认: ID DESC
        super(null, null, null, "t1.id desc");
    }

}
