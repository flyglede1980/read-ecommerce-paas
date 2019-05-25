package net.yitun.ioften.cms.website.action;

import io.lettuce.core.GeoArgs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.Code;
import net.yitun.basic.Util;
import net.yitun.basic.model.Result;
import net.yitun.ioften.cms.CardApi;
import net.yitun.ioften.cms.entity.card.CardprivilegeBO;
import net.yitun.ioften.cms.entity.card.Cardtypes;
import net.yitun.ioften.cms.entity.card.Privilegeset;
import net.yitun.ioften.cms.model.card.*;
import net.yitun.ioften.cms.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 卡种信息、特权信息、卡种特权信息服务控制层服务
 * @since 1.0.0
 * @see Api
 * @see RestController
 * @see ApiOperation
 * @see ApiImplicitParam
 * @see RequestParam
 * @see Validated
 * @see RequestBody
 * @see CardApi
 * @see CardTypeOutline
 * @see Cardtypes
 * @see CardTypeDetail
 * @see CardprivilegeBO
 * @see PrivilegeDetail
 * @see Privilegeset
 * @see CardTypeCreate
 * @see CardTypeModify
 * @author Flyglede
 * @date 2019-01-10
 */
@Api(tags = "VIP会员卡服务接口")
@RestController("cms.CardApi")
public class CardAction implements CardApi {
    /**会员卡卡种服务引用*/
    @Autowired
    protected CardService cardService;

    /**
     * 根据启用状态获取卡种列表信息,通过对字段的筛选满足卡种信息展示的需要
     * @param status--开启或关闭,0代表禁用;1代表启用;-1代表所有
     * @return Result<List<CardTypeOutline>>--卡种列表信息
     */
    @Override
    @ApiOperation(value = "根据启用状态查询并返回卡种列表信息")
    @ApiImplicitParam(name = "status", value = "开启或关闭,0代表禁用;1代表启用;-1代表所有", required = true)
    public Result<List<CardTypeOutline>> list(@RequestParam(value = "status", required = true) Integer status) {
        List<CardTypeOutline> lstCardTypeOutline = new ArrayList<>();
        List<Cardtypes> lstCardTypes = new ArrayList<>();
        if (null == status)
            return new Result<>(Code.BAD_REQ, "开启或关闭状态无效,卡种列表信息获取失败");
        if(status > 1 || status< -1)
            return new Result<>(Code.BAD_REQ, "开启或关闭状态不在有效范围值内,卡种列表信息获取失败");
        lstCardTypes = this.cardService.query(status);
        if(lstCardTypes!=null && !lstCardTypes.isEmpty()) {
            for(int i=0;i<lstCardTypes.size();i++) {
                Cardtypes cardtypes = lstCardTypes.get(i);
                lstCardTypeOutline.add(new CardTypeOutline(cardtypes.getCardTypeId(),cardtypes.getName(),cardtypes.getCardIcon(),cardtypes.getPrivilegeIcon(),
                        cardtypes.getIdentityIcon(),cardtypes.getDescription(),cardtypes.getPrice(),cardtypes.getPeriod()));
            }
        }
        return new Result<>(lstCardTypeOutline);
    }

    /**
     * 根据卡种编号获取卡种详情信息,包括卡种基本信息和卡种对应的特权信息
     * @param cardTypeId--卡种编号
     * @return Result<CardTypeDetail>--卡种详细信息
     */
    @Override
    @ApiOperation(value = "根据卡种编号获取卡种详情信息")
    @ApiImplicitParam(name = "cardTypeId", value = "卡种编号", required = true)
    public Result<CardTypeDetail> get(@RequestParam(value = "cardTypeId", required = true) Long cardTypeId) {
        /**卡种特权列表*/
        List<CardPrivilegeDetail> lstPrivilege = new ArrayList<>();
        CardTypeDetail cardTypeDetail = null;
        Cardtypes cardtypes = null;
        List<CardprivilegeBO> lstCardprivilegeBO = new ArrayList<>();
        if(null == cardTypeId || Util.MIN_ID > cardTypeId)
            return new Result<>(Code.BAD_REQ, "卡种编号无效,卡种详情信息获取失败");
        cardtypes = this.cardService.get(cardTypeId);
        lstCardprivilegeBO = this.cardService.find(cardTypeId);
        if(null == cardtypes)
            return new Result<>(Code.BAD_REQ, "卡种信息不存在,请联系管理员核对");
        if(lstCardprivilegeBO==null || lstCardprivilegeBO.isEmpty())
            return new Result<>(Code.BAD_REQ, "卡种无任何对应的权限,请联系管理员确认");
        if(lstCardprivilegeBO!=null && !lstCardprivilegeBO.isEmpty()) {
            for(int i=0;i<lstCardprivilegeBO.size();i++) {
                CardprivilegeBO cardprivilegeBO = lstCardprivilegeBO.get(i);
                lstPrivilege.add(new CardPrivilegeDetail(cardprivilegeBO.getCardPrivilegeId(),cardprivilegeBO.getCardTypeId(),cardprivilegeBO.getPrivilegeId(),
                        cardprivilegeBO.getIsSwitch(),cardprivilegeBO.getPrivilegeValue(),cardprivilegeBO.getName(),cardprivilegeBO.getCode(),
                        cardprivilegeBO.getType(),cardprivilegeBO.getCoefficient(),cardprivilegeBO.getUnit(),cardprivilegeBO.getTips()));
            }
        }
        cardTypeDetail = new CardTypeDetail(cardtypes.getCardTypeId(),cardtypes.getName(),cardtypes.getCardIcon(),cardtypes.getPrivilegeIcon(),
                cardtypes.getIdentityIcon(),cardtypes.getDescription(),cardtypes.getPrice(),cardtypes.getPeriod(),lstPrivilege);
        return new Result<>(cardTypeDetail);
    }

    /**
     * 加载所有特权配置项列表信息
     * @return Result<List<PrivilegeDetail>>--特权配置项列表信息
     */
    @Override
    @ApiOperation(value = "查询所有特权配置项列表信息")
    public Result<List<PrivilegeDetail>> load() {
        List<Privilegeset> lstPrivilegeset = new ArrayList<>();
        List<PrivilegeDetail> lstPrivilegeDetail = new ArrayList<>();
        lstPrivilegeset = this.cardService.load();
        if(null == lstPrivilegeset)
            return new Result<>(Code.BAD_REQ, "特权配置项信息不存在,请联系管理员");
        if(lstPrivilegeset!=null && !lstPrivilegeset.isEmpty()){
            for(int i=0;i<lstPrivilegeset.size();i++) {
                Privilegeset privilegeset = lstPrivilegeset.get(i);
                lstPrivilegeDetail.add(new PrivilegeDetail(privilegeset.getPrivilegeId(),privilegeset.getName(),privilegeset.getCode(),
                        privilegeset.getType(),privilegeset.getCoefficient(),privilegeset.getUnit(),privilegeset.getTips()));
            }
        }
        return new Result<>(lstPrivilegeDetail);
    }

    /**
     * 创建卡种及卡种特权信息
     * @param model--卡种及卡种特权信息
     * @return 创建操作结果信息
     */
    @Override
    @ApiOperation(value = "创建卡种信息")
    public Result<?> create(@Validated @RequestBody CardTypeCreate model) {
        if(null == model.getName() || model.getName().equals(""))
            return new Result<>(Code.NOT_FOUND,"卡种名称为空,创建卡种失败");
        if(null == model.getPeriod() || model.getPeriod() == 0)
            return new Result<>(Code.NOT_FOUND,"有效期为空或为0,创建卡种失败");
        if(null == model.getPrice())
            return new Result<>(Code.NOT_FOUND,"会员卡价格为空,创建卡种失败");
        return this.cardService.create(model);
    }

    /**
     * 更新卡种及卡种特权信息
     * @param model--卡种及卡种特权信息
     * @return 更新操作结果信息
     */
    @Override
    @ApiOperation(value = "更新卡种信息")
    public Result<?> modify(@Validated @RequestBody CardTypeModify model) {
        if(null == model.getName() || model.getName().equals(""))
            return new Result<>(Code.NOT_FOUND,"卡种名称为空,创建卡种失败");
        if(null == model.getPeriod() || model.getPeriod() == 0)
            return new Result<>(Code.NOT_FOUND,"有效期为空或为0,创建卡种失败");
        if(null == model.getPrice())
            return new Result<>(Code.NOT_FOUND,"会员卡价格为空,创建卡种失败");
        return this.cardService.modify(model);
    }
}