package net.yitun.ioften.cms.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.dict.Status;
import net.yitun.basic.dict.YesNo;
import net.yitun.ioften.cms.model.show.NewsList;

/**
 * <pre>
 * <b>资讯 浏览记录模型.</b>
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
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Browse implements Serializable {

    @ApiModelProperty(value = "ID")
    protected Long id;

    @ApiModelProperty(value = "用户ID, 外键: crm_user.id")
    protected Long uid;

    @ApiModelProperty(value = "文章ID, 外键: cms_article.id")
    protected Long aid;

    @ApiModelProperty(value = "是否阅读, NO:否; YES:是")
    protected YesNo view;

    @ApiModelProperty(value = "是否点赞, NO:否; YES:是")
    protected YesNo enjoy;

    @ApiModelProperty(value = "挖矿金额")
    protected Long award;

    @ApiModelProperty(value = "阅读挖矿金额")
    protected Long viewAward;

    @ApiModelProperty(value = "点赞时间")
    protected Date enjoyTime;

    @ApiModelProperty(value = "点赞挖矿金额")
    protected Long enjoyAward;

    @ApiModelProperty(value = "打赏金额, 0表示未打赏")
    protected Long reward;

    @ApiModelProperty(value = "状态, DISABLE:被删除; ENABLE:正常")
    protected Status status;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

    @ApiModelProperty(value = "资讯简要信息")
    protected NewsList news;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public Browse(Long id) {
        super();
        this.id = id;
    }

    public Browse(Long id, Long uid, Long aid, YesNo view, YesNo enjoy, Long award, Long viewAward, Long enjoyAward,
            Long reward, Status status, Date ctime, Date mtime) {
        super();
        this.id = id;
        this.uid = uid;
        this.aid = aid;
        this.view = view;
        this.enjoy = enjoy;
        this.award = award;
        this.viewAward = viewAward;
        this.enjoyAward = enjoyAward;
        this.reward = reward;
        this.status = status;
        this.ctime = ctime;
        this.mtime = mtime;
    }

}
