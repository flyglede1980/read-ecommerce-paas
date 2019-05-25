package net.yitun.ioften.crm;

import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.ioften.crm.conf.Conf;
import net.yitun.ioften.crm.model.shield.ShieldDetail;
import net.yitun.ioften.crm.model.shield.ShieldQuery;

/**
 * <pre>
 * <b>用户 拉黑接口.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月17日 下午2:18:46 LH
 *         new file.
 * </pre>
 */
public interface ShieldApi {

    /**
     * 分页查询
     * 
     * @param query 查询参数
     * @return PageResult<ShieldDetail> 分页结果
     */
    @GetMapping(value = Conf.ROUTE //
            + "/shields", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageResult<ShieldDetail> query(ShieldQuery query);

    /**
     * 新增拉黑
     * 
     * @param id 对方ID
     * @return Result<?> 屏蔽结果
     */
    @PostMapping(value = Conf.ROUTE //
            + "/shield/{target}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> stamp(@PathVariable("id") Long id);

    /**
     * 删除拉黑
     * 
     * @param ids ID清单
     * @return Result<?> 删除结果
     */
    @DeleteMapping(value = Conf.ROUTE //
            + "/shield", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> delete(@RequestParam("id") Set<Long> ids);

}
