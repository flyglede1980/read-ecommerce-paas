package net.yitun.ioften.pay.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.Code;
import net.yitun.basic.cache.utils.IocUtil;
import net.yitun.basic.dict.Status;
import net.yitun.basic.exp.BizException;
import net.yitun.basic.utils.CalcUtil;
import net.yitun.basic.utils.DateUtil;
import net.yitun.basic.utils.IdUtil;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.ioften.crm.UserApi;
import net.yitun.ioften.crm.model.user.UserDetail;
import net.yitun.ioften.pay.RateApi;
import net.yitun.ioften.pay.conf.Constant;
import net.yitun.ioften.pay.dicts.Currency;
import net.yitun.ioften.pay.dicts.Way;
import net.yitun.ioften.pay.entity.Flows;
import net.yitun.ioften.pay.entity.Wallet;
import net.yitun.ioften.pay.entity.vo.flows.MyFlowsTotal;
import net.yitun.ioften.pay.entity.vo.wallet.WalletBoard;
import net.yitun.ioften.pay.entity.vo.wallet.WalletQueryVo;
import net.yitun.ioften.pay.model.wallet.WalletAdjust;
import net.yitun.ioften.pay.model.wallet.WalletQuery;
import net.yitun.ioften.pay.repository.FlowsRepository;
import net.yitun.ioften.pay.repository.WalletRepository;
import net.yitun.ioften.pay.service.FlowsService;
import net.yitun.ioften.pay.service.WalletService;
import net.yitun.ioften.pay.support.BlockSupport;

@Slf4j
@Service("pay.WalletService")
public class WalletServiceImpl implements WalletService {

    @Autowired
    protected RateApi rateApi;

    @Autowired
    protected UserApi userApi;

    @Autowired
    protected BlockSupport block;

    @Autowired
    protected FlowsService flowsService;

    @Autowired
    protected WalletRepository repository;

    @Autowired
    protected FlowsRepository flowsRepository;

    @Override
    @Cacheable(key = "#id", value = Constant.CKEY_WALLET)
    public Wallet get(Long id) {
        Wallet wallet = null;
        if (null != (wallet = this.repository.get(id)))
            return wallet;

        return this.create(id);
    }

    @Override
    public Page<Wallet> query(WalletQuery query) {
        WalletQueryVo queryVo //
                = new WalletQueryVo(query.getId());
        return this.repository.query(queryVo);
    }

    @Override
    @Cacheable(key = "#id", value = Constant.CKEY_WALLET_BOARD)
    public WalletBoard board(Long id) {
        Wallet wallet = null;
        // 经AOP方式获取账户信息
        WalletService service = IocUtil.getBean(WalletService.class);
        if (null == (wallet = service.get(id)))
            return null;

        // 获取当前汇率
        Long rate = this.rateApi.now();
        BigDecimal _rate = new BigDecimal(rate).divide(new BigDecimal(CalcUtil.SCALE));

        // 余额合计
        Long sum = wallet.getGive() + wallet.getIncome() + wallet.getBalance();
        BigDecimal sumCash = new BigDecimal(sum).multiply(_rate);

        // 累计收益
        Long totalIncome = wallet.getTotalIncome();
        BigDecimal totalIncomeCash = new BigDecimal(totalIncome).multiply(_rate);

        // 昨日累计收益
        String yesterday = DateUtil.format(DateUtil.addDay(-1));
        MyFlowsTotal myFlowsTotal = this.flowsService.myTotal(id, yesterday, true);
        Long yesterdayIncome = myFlowsTotal.getIncome();
        BigDecimal yesterdayIncomeCash = new BigDecimal(yesterdayIncome).multiply(_rate);

        // 获取用户资料
        UserDetail userInfo = this.userApi.my().getData();

        return new WalletBoard(sum, sumCash.longValue(), totalIncome, totalIncomeCash.longValue(), yesterdayIncome,
                yesterdayIncomeCash.longValue(), userInfo.getLevel(), 1.5F /* levelMultiply */,
                userInfo.getLevel().nextLevel(), 2.5F /* nextLevelMultiply */); // TODO 需要考虑VIP用户对应的挖矿翻倍
    }

    @Override
    @Transactional
    @Caching(evict = { //
            @CacheEvict(key = "#uid", value = Constant.CKEY_WALLET),
            @CacheEvict(key = "#uid", value = Constant.CKEY_WALLET_BOARD) })
    public Wallet create(Long uid) {
        if (log.isInfoEnabled())
            log.info(">>> {}.create() create wallet, uid:{}", this.getClass().getName(), uid);

        Wallet wallet = null;
        // 检查用户账户不存在时, 只需全新生成
        if (null == (wallet = this.repository.lock(uid))) {
            // 为钱包产生新的区块地址
            String blockAddr = this.block.newAddr(uid);

            // 构建初始化钱包账户信息并持久化
            Date nowTime = new Date(System.currentTimeMillis());
            wallet = new Wallet(uid, 0L /* give */, 0L /* income */, 0L /* balance */, 0L /* totalIncome */,
                    0L /* totalAddedRmb */, 0L /* contribution */, blockAddr /* blockAddr */,
                    Status.ENABLE /* status */, nowTime, nowTime);
            Flows flows = new Flows(uid, uid, Way.N, 0L, Currency.SDC, null /* target */, null /* channel */,
                    null /* outFlows */, 0L /* give */, 0L /* income */, 0L /* balance */, "开户", Status.ENABLE, nowTime,
                    nowTime);
            if (!this.repository.create(wallet) //
                    || !this.flowsRepository.create(flows))
                throw new BizException(Code.BIZ_ERROR, "钱包账号开户失败");

            if (log.isInfoEnabled())
                log.info("<<< {}.create() create wallet success, wallet:{}", this.getClass().getName(),
                        JsonUtil.toJson(wallet));
            return wallet;
        }

        // 账户已经开通的清空下只需检查账户对应的区块地址是否有效，无效需要及时重新获取并绑定
        if (StringUtils.isNotBlank(wallet.getBlockAddr()))
            return wallet;

        // 为钱包产生新的区块地址
        String blockAddr = this.block.newAddr(uid);
        if (StringUtils.isBlank(blockAddr)) {
            if (log.isWarnEnabled())
                log.info("<<< {}.create() modify wallet block falid, block api unavailable, uid:{}",
                        this.getClass().getName(), uid);
            return wallet;
        }

        wallet.setBlockAddr(blockAddr);
        wallet.setMtime(new Date(System.currentTimeMillis()));
        this.repository.modifyBlock(wallet);

        if (log.isInfoEnabled())
            log.info("<<< {}.create() modify wallet block success, wallet:{}", this.getClass().getName(),
                    JsonUtil.toJson(wallet));
        return wallet;
    }

    @Override
    @Transactional
    @Caching(evict = { //
            @CacheEvict(key = "#adjust.uid", value = Constant.CKEY_WALLET),
            @CacheEvict(key = "#adjust.uid", value = Constant.CKEY_WALLET_BOARD) })
    public Flows adjust(WalletAdjust adjust) {
        if (log.isInfoEnabled())
            log.info(">>> {}.adjust() adjust:{}", this.getClass().getName(), JsonUtil.toJson(adjust));

        Wallet wallet = null;
        Long uid = adjust.getUid();
        // 钱包加锁，不存在时自动开通
        if (null == (wallet = this.repository.lock(uid)))
            wallet = this.create(uid);

        // 确认当前调账的金额, 是否增加还是扣减
        Long amount = adjust.getAmount(), newGive = wallet.getGive(), //
                newIncome = wallet.getIncome(), newBalance = wallet.getBalance(),
                newTotalIncome = wallet.getTotalIncome();
        if (0 <= amount) { // 如果增加收益, 则只需在收益账户上累加即可
            newIncome += amount;
            newTotalIncome += amount;

        } else {
            // 钱包账户金额就行扣款, 扣减顺序余额->赠送->收益
            newBalance = wallet.getBalance() + amount;
            // 如果balance出现超扣情况，需要考虑通过 give就行扣除，以下类似
            if (0 > newBalance) {
                newBalance = 0L;
                amount += wallet.getBalance();
                newGive = wallet.getGive() + amount;
                if (0 > newGive) {
                    newGive = 0L;
                    amount += wallet.getGive();
                    newIncome = wallet.getIncome() + amount;
                    if (0 > newIncome)
                        throw new BizException(Code.BIZ_ERROR, "余额不足");
                }
            }
        }

        // 将最新的账户结余就行持久化
        Date nowTime = new Date(System.currentTimeMillis());
        wallet = new Wallet(adjust.getUid(), newGive, newIncome, newBalance, newTotalIncome, nowTime);
        // 调账流水信息就行持久化
        Flows flows = new Flows(IdUtil.id(), adjust.getUid(), adjust.getWay(), adjust.getAmount(), adjust.getCurrency(),
                adjust.getTarget(), adjust.getChannel(), adjust.getOutFlows(), newGive, newIncome, newBalance,
                adjust.getRemark(), Status.ENABLE, nowTime, nowTime);
        if (!this.repository.adjust(wallet) //
                || !this.flowsRepository.create(flows))
            throw new BizException(Code.BIZ_ERROR, "账户调账失败");

        if (log.isInfoEnabled())
            log.info("<<< {}.adjust() wallet adjust success, flows:{}", this.getClass().getName(),
                    JsonUtil.toJson(flows));
        return flows;
    }

}
