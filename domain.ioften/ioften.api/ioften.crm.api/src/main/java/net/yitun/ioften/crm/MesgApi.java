package net.yitun.ioften.crm;

import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.ioften.crm.conf.Conf;
import net.yitun.ioften.crm.model.mesg.MesgDetail;
import net.yitun.ioften.crm.model.mesg.MesgQuery;
import net.yitun.ioften.crm.model.mesg.MesgStore;
import net.yitun.ioften.crm.model.mesg.MyMesgTotal;

/**
 * <pre>
 * <b>用户 消息接口.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月10日 下午4:13:01 LH
 *         new file.
 * </pre>
 */
public interface MesgApi {

    /**
     * 详细信息
     *
     * @param id ID
     * @return Result<MesgDetail> 详细信息
     */
    @GetMapping(value = Conf.ROUTE //
            + "/mesg/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<MesgDetail> detail(@PathVariable("id") Long id);

    /**
     * 分页查询
     * 
     * @param query 查询参数
     * @return PageResult<MesgDetail> 分页结果
     */
    @GetMapping(value = Conf.ROUTE //
            + "/mesgs", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageResult<MesgDetail> query(MesgQuery query);

    /**
     * 消息统计
     *
     * @return Result<MyMesgTotal> 我的消息统计
     */
    @GetMapping(value = Conf.ROUTE //
            + "/mesg/mytotal", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<MyMesgTotal> myTotal();

    /**
     * 存储消息
     * 
     * @param model 消息消息
     * @return Result<Boolean> 存储结果
     */
    Result<Boolean> store(@RequestBody MesgStore model);

    /**
     * 标记查看
     * 
     * @param ids ID清单
     * @return Result<Boolean> 标记结果
     */
    @PutMapping(value = Conf.ROUTE //
            + "/mesg", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<Boolean> viewed(@RequestParam("id") Set<Long> ids);

}
