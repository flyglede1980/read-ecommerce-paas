package net.yitun.ioften.crm.service;

import java.util.Set;

import com.github.pagehelper.Page;

import net.yitun.ioften.crm.entity.Mesg;
import net.yitun.ioften.crm.model.mesg.MesgQuery;
import net.yitun.ioften.crm.model.mesg.MesgStore;
import net.yitun.ioften.crm.model.mesg.MyMesgTotal;

/**
 * <pre>
 * <b>用户 消息服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月17日 下午1:56:01
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月17日 下午1:56:01 LH
 *         new file.
 * </pre>
 */
public interface MesgService {

    /**
     * 详情信息
     * 
     * @param id ID
     * @return Mesg 详情信息
     */
    Mesg get(Long id);

    /**
     * 分页查询
     * 
     * @param query 筛选参数
     * @return Page<MesgRepository> 分页结果
     */
    Page<Mesg> query(MesgQuery query);

    /**
     * 消息统计
     *
     * @param uid 用户ID
     * @return MyMesgTotal
     */
    MyMesgTotal myTotal(Long uid);

    /**
     * 存储消息
     * 
     * @param model 消息消息
     * @return boolean 存储结果
     */
    boolean store(MesgStore model);

    /**
     * 标记已查看
     * 
     * @param ids 消息ID清单
     * @return boolean true:标记成功; false:标记失败
     */
    boolean viewed(Set<Long> ids);

}
