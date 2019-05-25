package net.yitun.ioften.cms;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.yitun.basic.model.Result;
import net.yitun.ioften.cms.conf.Conf;
import net.yitun.ioften.cms.dicts.Type;
import net.yitun.ioften.cms.model.conf.CmsConfDetail;
import net.yitun.ioften.cms.model.conf.CmsConfModify;

/**
 * <pre>
 * <b>系统 应用设置.</b>
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
public interface ConfApi {

    /**
     * 资讯配置信息
     * 
     * @return Result<CmsConfDetail> 资讯配置
     */
    @GetMapping(value = Conf.ROUTE //
            + "/conf", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<CmsConfDetail> cmsConf();

    /**
     * 修改取资讯配置
     * 
     * @param model 修改模型
     * @return Result<?> 修改结果
     */
    @PutMapping(value = Conf.ROUTE //
            + "/conf", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> cmsConfModify(@RequestBody CmsConfModify model);

    /**
     * 获取资讯配置
     * 
     * @return CmsConfDetail
     */
    CmsConfDetail cmsConfInfo();

    /**
     * 获取资讯列表数量
     * 
     * @param type   模式, N:其他; TEXT:文章; IMAGE:图片; VIDEO:音视频
     * @param isHots true: 推荐列表; false:常规列表
     * @return int 分页大小
     */
    int getCmsShowSize(Type type, boolean isHots);

}
