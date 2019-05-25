package net.yitun.ioften.pay.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

import net.yitun.ioften.pay.entity.Wallet;
import net.yitun.ioften.pay.entity.vo.wallet.WalletQueryVo;

/**
 * <pre>
 * <b>支付 账户持久化.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月6日 下午5:57:16 LH
 *         new file.
 * </pre>
 */
@Repository("pay.WalletRepository")
public interface WalletRepository {

    // 表名
    static final String TABLE = "pay_wallet";

    // 查询字段
    static final String COLUMNS = "t1.id, t1.give, t1.income, t1.balance, t1.total_income, t1.total_added_rmb, t1.contribution, t1.block_addr, t1.status, t1.ctime, t1.mtime";

    /** 账户详细 */
    @ResultType(Wallet.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.id=#{id}")
    Wallet get(@Param("id") Long id);

    /** 账户加锁 */
    @ResultType(Wallet.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.status=1 and t1.valid=1 and t1.id=#{id} for update")
    Wallet lock(@Param("id") Long id);

    /** 分页查询 */
    @ResultType(Wallet.class)
    @Select("<script>" //
            + "select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1" //
            + " <if test='null!=id'>and t1.id=#{id}</if></script>")
    Page<Wallet> query(WalletQueryVo queryVo);

    /** 账户开户 */
    @ResultType(Boolean.class)
    @Insert("insert ignore into " + TABLE //
            + " ( id, give, income, balance, total_income, total_added_rmb, contribution, block_addr, status, ctime, mtime, valid )" //
            + " select #{id}, #{give}, #{income}, #{balance}, #{totalIncome}, #{totalAddedRmb}, #{contribution}, #{blockAddr}, #{status}, #{ctime}, #{mtime}, 1" //
            + "    from dual where not exists ( select 1 from " + TABLE + " t1 where t1.valid=1 and t1.id=#{id} )")
    boolean create(Wallet wallet);

    /** 账户调账 */
    @ResultType(Boolean.class)
    @Update("update " + TABLE
            + " t1 set t1.give=#{give}, t1.income=#{income}, t1.balance=#{balance}, t1.total_income=#{totalIncome}, t1.mtime=#{mtime} where t1.valid=1 and t1.id=#{id}")
    boolean adjust(Wallet wallet);

    /** 累加账户收益 */
    @ResultType(Boolean.class)
    @Update("update " + TABLE
            + " t1 set t1.income=t1.income+#{income}, t1.total_income=t1.total_income+#{income}, t1.mtime=#{mtime} where t1.valid=1 and t1.id=#{id}")
    boolean income(Wallet wallet);

    /** 修改区块地址 */
    @ResultType(Boolean.class)
    @Update("update " + TABLE + " t1 set t1.block_addr=#{blockAddr}, t1.mtime=#{mtime} where t1.valid=1 and t1.id=#{id}")
    boolean modifyBlock(Wallet wallet);

}
