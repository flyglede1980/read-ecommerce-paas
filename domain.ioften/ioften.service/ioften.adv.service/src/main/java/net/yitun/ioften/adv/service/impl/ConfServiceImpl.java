package net.yitun.ioften.adv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import net.yitun.ioften.adv.conf.Constant;
import net.yitun.ioften.adv.service.ConfService;
import net.yitun.ioften.cms.ConfApi;
import net.yitun.ioften.cms.model.conf.CmsConfDetail;

@Service("adv.ConfService")
@CacheConfig(cacheNames = Constant.CK_CONF)
public class ConfServiceImpl implements ConfService {

    @Autowired
    protected ConfApi confApi;

    @Override
    @Cacheable(key = "'switch:flash'")
    public boolean isFlashAdv() {
        // 获取资讯版本设置
        CmsConfDetail cmsConf = this.confApi.cmsConfInfo();

        // 判断资讯设置: 是否开启闪屏广告
        return cmsConf.isAdvStartupSwitch();
    }

    @Override
    @Cacheable(key = "'switch:banner'")
    public boolean isBannerAdv() {
        // 获取资讯版本设置
        CmsConfDetail cmsConf = this.confApi.cmsConfInfo();

        // 判断资讯设置: 是否开启推荐页Banner广告
        return cmsConf.isAdvRecBannerSwitch();
    }

}
