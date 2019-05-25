package net.yitun.ioften.cms.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import net.yitun.basic.Code;
import net.yitun.basic.model.Result;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.ioften.cms.ConfApi;
import net.yitun.ioften.cms.conf.Constant;
import net.yitun.ioften.cms.model.conf.CmsConfDetail;
import net.yitun.ioften.cms.model.conf.TopicItem;
import net.yitun.ioften.cms.service.ConfMyService;
import net.yitun.ioften.crm.SettingApi;
import net.yitun.ioften.crm.model.setting.SettingDetail;
import net.yitun.ioften.crm.model.setting.SettingModify;

@Service("cms.ConfMyService")
public class ConfMyServiceImpl implements ConfMyService {

    @Autowired
    protected ConfApi confApi;

    @Autowired
    protected SettingApi settingApi;

    static final String MY_TOPIC = "my.topic";

    @Override
    @Cacheable(key = "#id", value = Constant.CK_MY_TOPIC)
    public Collection<TopicItem> myTopic(Long id) {
        SettingDetail setting = null;
        Collection<TopicItem> myTopics = null;
        if (null != (setting = this.settingApi.get(id, MY_TOPIC)))
            myTopics = JsonUtil.toList(setting.getValue(), TopicItem.class);

        if (null == myTopics) { // 个人未设置关注话题时，默认取初始配置
            CmsConfDetail cmsConf = this.confApi.cmsConfInfo();
            myTopics = cmsConf.getInitTopics();
        }

        return myTopics;
    }

    @Override
    @CacheEvict(key = "#id", value = Constant.CK_MY_TOPIC)
    public Result<?> myTopicModify(Long id, List<TopicItem> topics) {
        SettingModify setting //
                = new SettingModify(id, MY_TOPIC, topics);

        if (!this.settingApi.modify(setting)) // 进行关注话题的修改保存操作
            return new Result<>(Code.BIZ_ERROR, "关注话题修改失败");

        return Result.OK;
    }

}
