package net.yitun.ioften.pay.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.yitun.basic.model.Result;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.ioften.pay.conf.Constant;
import net.yitun.ioften.pay.entity.vo.conf.GainConf;
import net.yitun.ioften.pay.entity.vo.conf.GainConfRatio;
import net.yitun.ioften.pay.model.conf.gain.GainConfModify;
import net.yitun.ioften.pay.service.ConfService;
import net.yitun.ioften.pay.service.RateService;
import net.yitun.ioften.sys.ConfigApi;
import net.yitun.ioften.sys.dicts.Scope;
import net.yitun.ioften.sys.model.config.ConfigDetail;
import net.yitun.ioften.sys.model.config.ConfigStore;

@Service("pay.ConfService")
public class ConfServiceImpl implements ConfService {

    @Autowired
    protected ConfigApi configApi;

    @Autowired
    protected RateService rateService;

    @Override
    public boolean can() {
//        boolean can = false;
//        ConfigDetail config = null;
//        if (null != (config = this.configApi.code(Constant.ON))) {
//            String value = config.getValue();
//            // 非 1 或 on 或 true 都表示未打开支付
//            can = StringUtils.equalsAnyIgnoreCase(value, "1", "on", "true");
//        }
        return false;
    }

    @Override
    public GainConf gainConf() {
        GainConf conf = null;
        ConfigDetail config = null;
        if (null != (config = this.configApi.code(Constant.GAIN_CONF)))
            conf = JsonUtil.toBean(config.getValue(), GainConf.class);

        // 有可能缓存不存在, 则返回默认配置
        return null != conf ? conf : new GainConf();
    }

    @Override
    public Result<?> gainConfModify(GainConfModify model) {
        ConfigStore config //
                = new ConfigStore(Constant.GAIN_CONF, "挖矿计算配置", Scope.SYS, model, null);
        return this.configApi.store(config);
    }

    @Override
    public Float gainConfRechargeRatio(Long amount) {
        GainConf conf = null;
        ConfigDetail config = null;
        if (null != (config = this.configApi.code(Constant.GAIN_CONF)))
            conf = JsonUtil.toBean(config.getValue(), GainConf.class);

        if (null == conf)
            conf = new GainConf();

        // 获取配置中的充值系数配置
        int index = 0;
        List<GainConfRatio> ratios = conf.getGainConfRatios();
        if (null != ratios && 0 != (index = ratios.size())) {
            for (index--; index >= 0; index--) {
                GainConfRatio ratio = ratios.get(index);
                if (amount >= ratio.getAmount())
                    return ratio.getRatio();
            }
        }

        return 1.0F; // 默认：1.0
    }

}
