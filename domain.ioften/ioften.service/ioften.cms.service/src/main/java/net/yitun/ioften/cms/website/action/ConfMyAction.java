package net.yitun.ioften.cms.website.action;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.IdUtil;
import net.yitun.ioften.cms.ConfApi;
import net.yitun.ioften.cms.ConfMyApi;
import net.yitun.ioften.cms.model.conf.CmsConfDetail;
import net.yitun.ioften.cms.model.conf.TopicItem;
import net.yitun.ioften.cms.model.mytopic.MyTopicsDetail;
import net.yitun.ioften.cms.service.ConfMyService;

@Api(tags = "资讯 我的设置")
@RestController("cms.ConfMyApi")
public class ConfMyAction implements ConfMyApi {

    @Autowired
    protected ConfApi confApi;

    @Autowired
    protected ConfMyService service;

    @Override
    @ApiOperation(value = "我关注话题")
    public Result<Collection<TopicItem>> myTopic() {
        // 获取当前用户的ID
        Long uid = SecurityUtil.getId();

        // 如果用户ID无效即有可能未登录，则返回初始设置
        if (null == uid || IdUtil.MIN >= uid.longValue())
            uid = 0L; // 0 代表是未注册、未登录的游客用户

        // 调用底层业务服务获取我关注话题清单
        Collection<TopicItem> myTopics = this.service.myTopic(uid);

        return new Result<>(myTopics);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "我关注话题和可选追加关注话题")
    public Result<MyTopicsDetail> myTopics() {
        // 调用底层业务服务获取我关注话题清单
        Long uid = SecurityUtil.getId(); // 获取用户ID便于Cache AOP
        Collection<TopicItem> myTopics = this.service.myTopic(uid);

        // 再获取系统设置的默认可选话题清单
        Set<TopicItem> recTopics = null;
        CmsConfDetail cmsConf = this.confApi.cmsConfInfo();
        if (null != (recTopics = cmsConf.getRecTopics()) //
                && null != myTopics && 0 != myTopics.size()) {
            recTopics.removeAll(myTopics); // 移除自定组已经选择的话题项
        }

        return new Result<MyTopicsDetail>(new MyTopicsDetail(myTopics, recTopics));
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "修改我关注话题")
    public Result<?> myTopicsModify(@Validated @RequestBody List<TopicItem> models) {
        // 修复Bug: http://zentao.ytdev.org/zentao/bug-view-48.html
        // // 限制关注的话题数量为 0~18个
        // if (null == models || 18 < models.size())
        // return new Result<>(Code.BAD_REQ, "关注的话题数限制0~18个");

        // 调用底层业务服务更新我关注话题清单
        Long uid = SecurityUtil.getId(); // 获取用户ID便于Cache AOP
        return this.service.myTopicModify(uid, models);
    }

}
