package net.yitun.ioften.crm;

import org.springframework.web.bind.annotation.RequestBody;

import net.yitun.ioften.crm.model.setting.SettingDetail;
import net.yitun.ioften.crm.model.setting.SettingModify;

/**
 * <pre>
 * <b>用户 我的设置.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月4日 下午8:23:10 LH
 *         new file.
 * </pre>
 */
public interface SettingApi {

    /**
     * 获取我的设置
     * 
     * @param id   用户ID
     * @param code 设置代码
     * @return SettingDetail 设置
     */
    SettingDetail get(Long id, String code);

    /**
     * 更新我的设置
     * 
     * @param model 设置模型
     * @return Boolean 更新结果
     */
    Boolean modify(@RequestBody SettingModify model);

}
