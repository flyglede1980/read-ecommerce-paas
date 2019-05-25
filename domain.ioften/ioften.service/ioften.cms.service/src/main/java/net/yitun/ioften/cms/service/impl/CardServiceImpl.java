package net.yitun.ioften.cms.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.Code;
import net.yitun.basic.exp.BizException;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.IdUtil;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.comon.CobjsApi;
import net.yitun.comon.model.cobj.Cobj;
import net.yitun.ioften.cms.entity.card.Cardprivilege;
import net.yitun.ioften.cms.entity.card.CardprivilegeBO;
import net.yitun.ioften.cms.entity.card.Cardtypes;
import net.yitun.ioften.cms.entity.card.Privilegeset;
import net.yitun.ioften.cms.model.card.CardPrivilegeCreate;
import net.yitun.ioften.cms.model.card.CardTypeCreate;
import net.yitun.ioften.cms.model.card.CardTypeModify;
import net.yitun.ioften.cms.repository.CardRepository;
import net.yitun.ioften.cms.service.CardService;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 卡种信息、特权信息、卡种特权信息服务接口实现
 * @since 1.0.0
 * @see Slf4j
 * @see Service
 * @see Autowired
 * @see CardRepository
 * @see Cardtypes
 * @author Flyglede
 */
@Slf4j
@Service("cms.CardService")
public class CardServiceImpl implements CardService {

    @Autowired
    protected CardRepository cardRepository;

    @Autowired
    protected CobjsApi cobjsApi;
    /**
     * 根据启用状态获取卡种列表信息
     * @param status--开启或关闭,0代表禁用;1代表启用;-1代表所有
     * @return 卡种列表信息
     */
    @Override
    public List<Cardtypes> query(Integer status) {
        List<Cardtypes> lstCardTypes = new ArrayList<Cardtypes>();
        lstCardTypes = this.cardRepository.query(status);
        return lstCardTypes;
    }

    /**
     * 根据卡种编号获取卡种详情信息
     * @param cardTypeId--卡种编号
     * @return 卡种详情信息
     */
    @Override
    public Cardtypes get(Long cardTypeId) {
        return this.cardRepository.get(cardTypeId);
    }

    /**
     * 根据卡种编号获取卡种特权列表信息
     * @param cardTypeId--卡种编号
     * @return 卡种特权列表信息
     */
    @Override
    public List<CardprivilegeBO> find(Long cardTypeId) {
        List<CardprivilegeBO> lstCardprivilegeBO = new ArrayList<>();
        lstCardprivilegeBO = this.cardRepository.find(cardTypeId);
        return lstCardprivilegeBO;
    }

    /**
     * 加载所有特权列表信息
     * @return 特权列表信息
     */
    @Override
    public List<Privilegeset> load() {
        return this.cardRepository.load();
    }

    /**
     * 创建卡种及卡种特权信息
     * @param model--卡种及卡种特权信息
     * @return 创建操作结果信息
     */
    @Override
    @Transactional
    public Result<?> create(CardTypeCreate model) {
        Long cardTypeId = IdUtil.id();
        Long userId = SecurityUtil.getId();
        Date now = new Date(System.currentTimeMillis());
        List<CardPrivilegeCreate> lstCardPrivilegeCreate = new ArrayList<>();
        lstCardPrivilegeCreate = model.getLstCardPrivilege();
        Set<Cobj> cobjs = new LinkedHashSet<>();
        String strDescription = "";
        String strNewPath = "card/" + cardTypeId.toString() + "/";
        String strNewCardIconKey = "";
        String strNewPrivilegeIcon = "";
        String strNewIdentityIcon = "";
        String strCardIcon = model.getCardIcon();
        String strPrivilegeIcon = model.getPrivilegeIcon();
        String strIdentityIcon = model.getIdentityIcon();
        long index = System.currentTimeMillis();
        /**上传图片文件名称不为空的处理*/
        if(null != strCardIcon && !strCardIcon.equals("")) {
            /**新上传图片的处理*/
            if(strCardIcon.indexOf(".")!=-1) {
                strNewCardIconKey = strNewPath + (++index);
                cobjs.add(new Cobj(strCardIcon, strNewCardIconKey));
            }
            /**图片未做任何改动的处理*/
            else {
                strNewCardIconKey = strCardIcon;
            }
        }
        /**上传图片文件名称不为空的处理*/
        if(null != strPrivilegeIcon && !strPrivilegeIcon.equals("")) {
            /**新上传图片的处理*/
            if(strPrivilegeIcon.indexOf(".")!=-1) {
                strNewPrivilegeIcon = strNewPath + (++index);
                cobjs.add(new Cobj(strPrivilegeIcon, strNewPrivilegeIcon));
            }
            /**图片未做任何改动的处理*/
            else {
                strNewPrivilegeIcon = strPrivilegeIcon;
            }
        }
        /**上传图片文件名称不为空的处理*/
        if(null != strIdentityIcon && !strIdentityIcon.equals("")) {
            /**新上传图片的处理*/
            if(strIdentityIcon.indexOf(".")!=-1) {
                strNewIdentityIcon = strNewPath + (++index);
                cobjs.add(new Cobj(strIdentityIcon, strNewIdentityIcon));
            }
            /**图片未做任何改动的处理*/
            else {
                strNewIdentityIcon = strIdentityIcon;
            }
        }
        /**卡种图片上传处理*/
        if(!cobjs.isEmpty()) {
            if(!uploadPicFile(cobjs)) {
                throw new BizException(Code.BIZ_ERROR, "卡种图片信息上传失败");
            }
        }
        Cardtypes cardtypes = new Cardtypes(cardTypeId, userId, model.getName(), strNewCardIconKey, strNewPrivilegeIcon,strNewIdentityIcon,
                strDescription, model.getPrice(), model.getPeriod(), 0, 0, 0, 0, 0, 1, now, now);
        if(null == lstCardPrivilegeCreate ||  lstCardPrivilegeCreate.isEmpty())
            throw new BizException(Code.BIZ_ERROR, "卡种无任何特权,卡种信息创建失败");
        for(int i=0;i<lstCardPrivilegeCreate.size();i++) {
            CardPrivilegeCreate cardPrivilegeCreate = lstCardPrivilegeCreate.get(i);
            Long cardPrivilegeId = IdUtil.id();
            Integer privilegeValue = 0;
            if(cardPrivilegeCreate.getPrivilegeValue()==null) {
                privilegeValue = 0;
            } else {
                privilegeValue = cardPrivilegeCreate.getPrivilegeValue();
            }
            Cardprivilege cardprivilege = new Cardprivilege(cardPrivilegeId, cardTypeId, cardPrivilegeCreate.getPrivilegeId(),
                    cardPrivilegeCreate.getIsSwitch(), privilegeValue);
            boolean blCreatePrivilege = this.cardRepository.createCardPrivilege(cardprivilege);
            if(!blCreatePrivilege)
                throw new BizException(Code.BIZ_ERROR, "卡种特权信息保存失败");
        }
        boolean blCreateCard = this.cardRepository.createCardTypes(cardtypes);
        if(!blCreateCard)
            throw new BizException(Code.BIZ_ERROR, "卡种信息保存失败");
        if (log.isInfoEnabled())
            log.info("<<< {}.create() id:{}", this.getClass().getName(), cardTypeId);
        return Result.OK;
    }

    /**
     * 更新卡种及卡种特权信息
     * @param model--卡种及卡种特权信息
     * @return 更新操作结果信息
     */
    @Override
    @Transactional
    public Result<?> modify(CardTypeModify model) {
        Long cardTypeId = model.getCardTypeId();
        Long userId = SecurityUtil.getId();
        Date now = new Date(System.currentTimeMillis());
        List<CardPrivilegeCreate> lstCardPrivilegeCreate = new ArrayList<>();
        lstCardPrivilegeCreate = model.getLstCardPrivilege();
        Set<Cobj> cobjs = new LinkedHashSet<>();
        String strDescription = "";
        String strNewPath = "card/" + cardTypeId.toString() + "/";
        String strNewCardIconKey = "";
        String strNewPrivilegeIcon = "";
        String strNewIdentityIcon = "";
        String strCardIcon = model.getCardIcon();
        String strPrivilegeIcon = model.getPrivilegeIcon();
        String strIdentityIcon = model.getIdentityIcon();
        long index = System.currentTimeMillis();
        /**上传图片文件名称不为空的处理*/
        if(null != strCardIcon && !strCardIcon.equals("")) {
            /**新上传图片的处理*/
            if(strCardIcon.indexOf(".")!=-1) {
                strNewCardIconKey = strNewPath + (++index);
                cobjs.add(new Cobj(strCardIcon, strNewCardIconKey));
            }
            /**图片未做任何改动的处理*/
            else {
                strNewCardIconKey = strCardIcon;
            }
        }
        /**上传图片文件名称不为空的处理*/
        if(null != strPrivilegeIcon && !strPrivilegeIcon.equals("")) {
            /**新上传图片的处理*/
            if(strPrivilegeIcon.indexOf(".")!=-1) {
                strNewPrivilegeIcon = strNewPath + (++index);
                cobjs.add(new Cobj(strPrivilegeIcon, strNewPrivilegeIcon));
            }
            /**图片未做任何改动的处理*/
            else {
                strNewPrivilegeIcon = strPrivilegeIcon;
            }
        }
        /**上传图片文件名称不为空的处理*/
        if(null != strIdentityIcon && !strIdentityIcon.equals("")) {
            /**新上传图片的处理*/
            if(strIdentityIcon.indexOf(".")!=-1) {
                strNewIdentityIcon = strNewPath + (++index);
                cobjs.add(new Cobj(strIdentityIcon, strNewIdentityIcon));
            }
            /**图片未做任何改动的处理*/
            else {
                strNewIdentityIcon = strIdentityIcon;
            }
        }
        /**卡种图片上传处理*/
        if(!cobjs.isEmpty()) {
            if(!uploadPicFile(cobjs)) {
                throw new BizException(Code.BIZ_ERROR, "卡种图片信息上传失败");
            }
        }
        Cardtypes cardtypes = new Cardtypes(cardTypeId, userId, model.getName(), strNewCardIconKey, strNewPrivilegeIcon, strNewIdentityIcon,
                strDescription, model.getPrice(), model.getPeriod(), 0, 0, 0, 0, 0, 1, now, now);
        if(null == lstCardPrivilegeCreate ||  lstCardPrivilegeCreate.isEmpty())
            throw new BizException(Code.BIZ_ERROR, "卡种无任何特权,卡种信息更新失败");
        boolean blDetelePrivilege = this.cardRepository.deleteCardPrivilege(cardTypeId);
        if(!blDetelePrivilege)
            throw new BizException(Code.BIZ_ERROR, "清空卡种特权信息失败");
        for(int i=0;i<lstCardPrivilegeCreate.size();i++) {
            CardPrivilegeCreate cardPrivilegeCreate = lstCardPrivilegeCreate.get(i);
            Long cardPrivilegeId = IdUtil.id();
            Integer privilegeValue = 0;
            if(cardPrivilegeCreate.getPrivilegeValue()==null) {
                privilegeValue = 0;
            } else {
                privilegeValue = cardPrivilegeCreate.getPrivilegeValue();
            }
            Cardprivilege cardprivilege = new Cardprivilege(cardPrivilegeId, cardTypeId, cardPrivilegeCreate.getPrivilegeId(),
                    cardPrivilegeCreate.getIsSwitch(), privilegeValue);
            boolean blCreatePrivilege = this.cardRepository.createCardPrivilege(cardprivilege);
            if(!blCreatePrivilege)
                throw new BizException(Code.BIZ_ERROR, "卡种特权信息更新失败");
        }
        boolean blModifyCard = this.cardRepository.updateCardTypes(cardtypes);
        if(!blModifyCard)
            throw new BizException(Code.BIZ_ERROR, "卡种信息更新失败");
        if (log.isInfoEnabled())
            log.info("<<< {}.modify() id:{}", this.getClass().getName(), cardTypeId);
        return Result.OK;
    }

    /**
     * 上传图片到正式华为云存储中
     * @param cobjSet--图片键值对
     * @return true--上传成功;false--上传失败
     */
    private boolean uploadPicFile(Set<Cobj> cobjSet) {
        Result<?> result;
        if (null == (result = this.cobjsApi.sure(cobjSet)) || !result.ok()) {
            if (log.isInfoEnabled())
                log.info("<<< {}.create() cobj sure image faild, {}", this.getClass().getName(),
                        JsonUtil.toJson(result));
            return false;
        }
        return true;
    }
}