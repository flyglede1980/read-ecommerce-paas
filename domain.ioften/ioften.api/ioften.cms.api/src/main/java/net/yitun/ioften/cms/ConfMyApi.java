package net.yitun.ioften.cms;

import java.util.Collection;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.yitun.basic.model.Result;
import net.yitun.ioften.cms.conf.Conf;
import net.yitun.ioften.cms.model.conf.TopicItem;
import net.yitun.ioften.cms.model.mytopic.MyTopicsDetail;

/**
 * <pre>
 * <b>资讯 我的设置.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月6日 上午10:07:02 LH
 *         new file.
 * </pre>
 */
public interface ConfMyApi {

    /**
     * 我关注话题
     * 
     * @return Result<Collection<TopicItem>> 关注话题
     */
    @GetMapping(value = Conf.ROUTE //
            + "/my/topic", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<Collection<TopicItem>> myTopic();

    /**
     * 我关注话题和可选追加关注话题
     * 
     * @return Result<MyTopicsDetail> 话题清单
     */
    @GetMapping(value = Conf.ROUTE //
            + "/my/topics", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<MyTopicsDetail> myTopics();

    /**
     * 修改我关注话题
     * 
     * @param models 待更新的话题模型
     * @return Result<?> 更新结果
     */
    @PutMapping(value = Conf.ROUTE //
            + "/my/topics", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> myTopicsModify(@RequestBody List<TopicItem> models);

}
