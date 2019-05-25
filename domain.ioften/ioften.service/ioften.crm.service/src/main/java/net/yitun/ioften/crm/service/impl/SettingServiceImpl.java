package net.yitun.ioften.crm.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.utils.IdUtil;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.ioften.crm.entity.Setting;
import net.yitun.ioften.crm.model.setting.SettingDetail;
import net.yitun.ioften.crm.model.setting.SettingModify;
import net.yitun.ioften.crm.repository.SettingRepository;
import net.yitun.ioften.crm.service.SettingService;

@Slf4j
@Service("sys.SettingService")
public class SettingServiceImpl implements SettingService {

    @Autowired
    protected SettingRepository repository;

    @Override
    public SettingDetail get(Long uid, String code) {
        Setting setting = null;
        if (null == (setting = this.repository.getByUid(uid, code)))
            return null;

        return new SettingDetail(setting.getId(), setting.getUid(), setting.getCode(), setting.getValue(), setting.getCtime(),
                setting.getMtime());
    }

    @Override
    @Transactional
    public Boolean modify(SettingModify model) {
        Object valObj = model.getValue();
        String value = (valObj instanceof String) ? value = (String) valObj : JsonUtil.toJson(valObj);

        Date nowTime = new Date(System.currentTimeMillis());
        Setting setting = new Setting(null, model.getUid(), model.getCode(), value, nowTime, nowTime);

        // 如果修改失败, 默认认为用户从未设置过
        if (!this.repository.modify(setting)) {

            setting.setId(IdUtil.id());
            // 进行第一次设置操作，新增!
            return this.repository.create(setting);
        }

        if (log.isInfoEnabled())
            log.info("<<< {}.modify() json:{}", this.getClass().getName(), JsonUtil.toJson(setting));

        return true;
    }

}
