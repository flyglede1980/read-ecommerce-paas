package net.yitun.ioften.cms.service;

import java.util.Collection;
import java.util.List;

import net.yitun.basic.model.Result;
import net.yitun.ioften.cms.model.conf.TopicItem;

/**
 * <pre>
 * <b>资讯 我的设置服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月4日 下午5:14:38 LH
 *         new file.
 * </pre>
 */
public interface ConfMyService {

    /**
     * 获取我关注话题
     * 
     * @param id 用户id, 0: 代表游客
     * @return Collection<TopicItem>
     */
    Collection<TopicItem> myTopic(Long id);

    /**
     * 更新我关注话题
     * 
     * @param id     用户id, 0: 代表游客
     * @param topics 待更新的话题列表
     * @return Result<?> 更新结果
     */
    Result<?> myTopicModify(Long id, List<TopicItem> topics);

}
