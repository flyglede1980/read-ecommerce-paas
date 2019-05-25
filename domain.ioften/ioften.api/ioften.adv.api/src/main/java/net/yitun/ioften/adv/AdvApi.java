package net.yitun.ioften.adv;

import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.ioften.adv.conf.Conf;
import net.yitun.ioften.adv.model.adv.AdvCreate;
import net.yitun.ioften.adv.model.adv.AdvDetail;
import net.yitun.ioften.adv.model.adv.AdvModify;
import net.yitun.ioften.adv.model.adv.AdvQuery;

/**
 * <pre>
 * <b>广告 内容接口.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月5日 下午6:46:42 LH
 *         new file.
 * </pre>
 */
public interface AdvApi {

    /**
     * 广告详细
     *
     * @param id ID
     * @return Result<AdvDetail> 广告详细
     */
    @GetMapping(value = Conf.ROUTE //
            + "/adv/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<AdvDetail> detail(@PathVariable("id") Long id);

    /**
     * 分页查询
     * 
     * @param query 筛选参数
     * @return PageResult<PlanDetail> 分页列表
     */
    @GetMapping(value = Conf.ROUTE //
            + "/advs", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageResult<AdvDetail> query(AdvQuery query);

    /**
     * 新增广告
     * 
     * @param model 广告信息
     * @return Result<?> 新增结果
     */
    @PostMapping(value = Conf.ROUTE //
            + "/adv", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> create(@RequestBody AdvCreate model);

    /**
     * 修改广告
     * 
     * @param model 广告信息
     * @return Result<?> 修改结果
     */
    @PutMapping(value = Conf.ROUTE //
            + "/adv", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> modify(@RequestBody AdvModify model);

    /**
     * 删除广告
     * 
     * @param ids ID清单
     * @return Result<?> 删除结果
     */
    @DeleteMapping(value = Conf.ROUTE //
            + "/adv", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> delete(@RequestParam("id") Set<Long> ids);

}
