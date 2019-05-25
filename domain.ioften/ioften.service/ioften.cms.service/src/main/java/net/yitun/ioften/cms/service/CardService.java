package net.yitun.ioften.cms.service;

import net.yitun.basic.model.Result;
import net.yitun.ioften.cms.entity.card.Cardtypes;
import net.yitun.ioften.cms.entity.card.CardprivilegeBO;
import net.yitun.ioften.cms.entity.card.Privilegeset;
import net.yitun.ioften.cms.model.card.CardTypeCreate;
import net.yitun.ioften.cms.model.card.CardTypeModify;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 卡种信息、特权信息、卡种特权信息服务接口定义
 * @since 1.0.0
 * @see Repository
 * @see ResultType
 * @see Insert
 * @see Update
 * @see Cardtypes
 * @author Flyglede
 */
public interface CardService {
    /**
     * 根据启用状态获取卡种列表信息
     * @param status--开启或关闭,0代表禁用;1代表启用;-1代表所有
     * @return 卡种列表信息
     */
    List<Cardtypes> query(Integer status);

    /**
     * 根据卡种编号获取卡种详情信息
     * @param cardTypeId--卡种编号
     * @return 卡种详情信息
     */
    Cardtypes get(Long cardTypeId);

    /**
     * 根据卡种编号获取卡种特权列表信息
     * @param cardTypeId--卡种编号
     * @return 卡种特权列表信息
     */
    List<CardprivilegeBO> find(Long cardTypeId);

    /**
     * 加载所有特权列表信息
     * @return 特权列表信息
     */
    List<Privilegeset> load();

    /**
     * 创建卡种及卡种特权信息
     * @param model--卡种及卡种特权信息
     * @return 创建操作结果信息
     */
    Result<?> create(CardTypeCreate model);

    /**
     * 更新卡种及卡种特权信息
     * @param model--卡种及卡种特权信息
     * @return 更新操作结果信息
     */
    Result<?> modify(CardTypeModify model);
}