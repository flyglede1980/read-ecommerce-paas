package net.yitun.ioften.cms;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import net.yitun.basic.model.Result;
import net.yitun.ioften.cms.conf.Conf;
import net.yitun.ioften.cms.model.card.CardTypeCreate;
import net.yitun.ioften.cms.model.card.CardTypeDetail;
import net.yitun.ioften.cms.model.card.CardTypeModify;
import net.yitun.ioften.cms.model.card.CardTypeOutline;
import net.yitun.ioften.cms.model.card.PrivilegeDetail;

/**
 * 卡种信息、特权信息、卡种特权信息对外服务接口定义
 * @since 1.0.0
 * @see RequestParam
 * @see CardTypeOutline
 * @author Flyglede
 */
public interface CardApi {
    /**
     * 根据启用状态获取卡种列表信息
     * @param status--开启或关闭,0代表禁用;1代表启用;-1代表所有
     * @return Result<List<CardTypeOutline>>--卡种列表信息
     */
    @GetMapping(value = Conf.ROUTE //
            + "/cards", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<List<CardTypeOutline>> list(@RequestParam(value = "status", required = false) Integer status);

    /**
     * 根据卡种编号获取卡种详情信息
     * @param cardTypeId--卡种编号
     * @return Result<CardTypeDetail>--卡种详细信息
     */
    @GetMapping(value = Conf.ROUTE //
            + "/cards/card", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<CardTypeDetail> get(@RequestParam(value = "cardTypeId", required = true) Long cardTypeId);

    /**
     * 加载所有特权列表信息
     * @return Result<List<PrivilegeDetail>>--特权列表信息
     */
    @GetMapping(value = Conf.ROUTE //
            + "/cards/privileges", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<List<PrivilegeDetail>> load();

    /**
     * 创建卡种信息及卡种特权信息
     * @param model--卡种及卡种特权信息
     * @return 创建结果信息
     */
    @PostMapping(value = Conf.ROUTE //
            + "/cards/cardtype", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> create(@RequestBody CardTypeCreate model );

    /**
     * 创建卡种信息及卡种特权信息
     * @param model--卡种及卡种特权信息
     * @return 创建结果信息
     */
    @PutMapping(value = Conf.ROUTE //
            + "/cards/cardtype", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> modify(@RequestBody CardTypeModify model);
}