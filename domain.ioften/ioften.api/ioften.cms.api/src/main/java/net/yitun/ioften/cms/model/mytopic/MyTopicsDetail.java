package net.yitun.ioften.cms.model.mytopic;

import java.io.Serializable;
import java.util.Collection;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.ioften.cms.model.conf.TopicItem;

/**
 * <pre>
 * <b>资讯 我关注话题.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月4日 上午11:57:23 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyTopicsDetail implements Serializable {

    @ApiModelProperty(value = "我关注话题, 数量限制: 0~18 个", required = true)
    protected Collection<TopicItem> myTopics;

    @ApiModelProperty(value = "系统提供的推荐频道栏目")
    protected Collection<TopicItem> recTopics;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
