package net.yitun.ioften.pay.website.action;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.ioften.pay.WalletApi;
import net.yitun.ioften.pay.dicts.Channel;
import net.yitun.ioften.pay.dicts.Currency;
import net.yitun.ioften.pay.dicts.Way;
import net.yitun.ioften.pay.entity.Flows;
import net.yitun.ioften.pay.entity.Wallet;
import net.yitun.ioften.pay.entity.vo.wallet.WalletBoard;
import net.yitun.ioften.pay.model.flows.FlowsDetail;
import net.yitun.ioften.pay.model.wallet.MyWallet;
import net.yitun.ioften.pay.model.wallet.WalletAdjust;
import net.yitun.ioften.pay.model.wallet.WalletDetail;
import net.yitun.ioften.pay.model.wallet.WalletQuery;
import net.yitun.ioften.pay.service.WalletService;

@Api(tags = "支付 钱包接口")
@RestController("pay.WalletApi")
@SuppressWarnings("unchecked")
public class WalletAction implements WalletApi {

    @Autowired
    protected WalletService service;

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "账户信息")
    public Result<WalletDetail> my() {
        Wallet wallet = null;
        Long id = SecurityUtil.getId();
        if (null == (wallet = this.service.get(id)))
            return Result.UNEXIST;

        return new Result<>(new WalletDetail(wallet.getId(), wallet.getGive(), wallet.getIncome(), wallet.getBalance(),
                wallet.getTotalIncome(), wallet.getTotalAddedRmb(), wallet.getContribution(), wallet.getBlockAddr(),
                wallet.getStatus(), wallet.getCtime(), wallet.getMtime()));
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "账户总览")
    public Result<MyWallet> board() {
        WalletBoard board = null;
        Long id = SecurityUtil.getId();
        if (null == (board = this.service.board(id)))
            return Result.UNEXIST;

        return new Result<>(new MyWallet(board.getSum(), board.getSumCash(), board.getTotalIncome(), board.getTotalIncomeCash(),
                board.getYesterdayIncome(), board.getYesterdayIncomeCash(), board.getLevel(), board.getLevelMultiple(),
                board.getNextLevel(), board.getNextLevelMultiple()));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParam(name = "id", value = "用户ID或账户ID", required = true)
    public Result<WalletDetail> detail(@PathVariable("id") Long id) {
        Wallet wallet = null;
        if (null == (wallet = this.service.get(id)))
            return Result.UNEXIST;

        return new Result<>(new WalletDetail(wallet.getId(), wallet.getGive(), wallet.getIncome(), wallet.getBalance(),
                wallet.getTotalIncome(), wallet.getTotalAddedRmb(), wallet.getContribution(), wallet.getBlockAddr(),
                wallet.getStatus(), wallet.getCtime(), wallet.getMtime()));
    }

    @Override
    @ApiOperation(value = "分页查询")
    @PreAuthorize("hasRole('ADMIN')")
    public PageResult<WalletDetail> query(@Validated WalletQuery query) {
        Page<Wallet> page = this.service.query(query);
        return new PageResult<>(page.getTotal(), page.getPages(), page.stream()
                .map(wallet -> new WalletDetail(wallet.getId(), wallet.getGive(), wallet.getIncome(), wallet.getBalance(),
                        wallet.getTotalIncome(), wallet.getTotalAddedRmb(), wallet.getContribution(), wallet.getBlockAddr(),
                        wallet.getStatus(), wallet.getCtime(), wallet.getMtime()))
                .collect(Collectors.toList()));
    }

    @Override
    public Result<FlowsDetail> adjust(@Validated @RequestBody WalletAdjust adjust) {
        Flows flows = this.service.adjust(adjust);
        return new Result<>(new FlowsDetail(flows.getId(), flows.getUid(), flows.getWay(), flows.getAmount(),
                flows.getCurrency(), flows.getTarget(), flows.getChannel(), flows.getOutFlows(), flows.getRemark(),
                flows.getStatus(), flows.getCtime(), flows.getMtime()));
    }

    @Override
    public Result<FlowsDetail> adjust(@RequestParam("uid") Long uid, @RequestParam("way") Way way,
            @RequestParam("amount") Long amount, @RequestParam("currency") Currency currency,
            @RequestParam("target") Long target, @RequestParam("channel") Channel channel,
            @RequestParam("outFlows") String outFlows, @RequestParam("remark") String remark) {
        WalletAdjust adjust //
                = new WalletAdjust(uid, way, amount, currency, target, channel, outFlows, remark);
        return this.adjust(adjust);
    }

}
