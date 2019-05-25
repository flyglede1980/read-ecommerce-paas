package net.yitun.ioften.cms.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.ioften.cms.model.show.NewsList;

/**
 * <pre>
 * <b>资讯 收藏资讯模型.</b>
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
public class Favorite implements Serializable {

    @ApiModelProperty(value = "ID")
    protected Long id;

    @ApiModelProperty(value = "用户ID, 外键: crm_user.id")
    protected Long uid;

    @ApiModelProperty(value = "文章ID, 外键: cms_article.id")
    protected Long aid;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

    @ApiModelProperty(value = "资讯简要信息")
    protected NewsList news;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public Favorite(Long id) {
        super();
        this.id = id;
    }

    public Favorite(Long id, Long uid, Long aid, Date ctime, Date mtime) {
        super();
        this.id = id;
        this.uid = uid;
        this.aid = aid;
        this.ctime = ctime;
        this.mtime = mtime;
    }

}
