package net.yitun.ioften.cms.repository;

import net.yitun.ioften.cms.entity.card.Cardprivilege;
import net.yitun.ioften.cms.entity.card.CardprivilegeBO;
import net.yitun.ioften.cms.entity.card.Cardtypes;
import net.yitun.ioften.cms.entity.card.Privilegeset;
import org.apache.ibatis.annotations.*;
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
@Repository
public interface CardRepository {
    /**
     * 卡种信息表名定义
     */
    static final String T_CardTypes = "user_cardtypes";
    /**
     * 卡种特权表名定义
     */
    static final String T_CardPrivilege = "user_cardprivilege";
    /**
     * 特权配置表定义
     */
    static final String T_PrivilegeSet = "user_privilegeset";
    /**
     * 卡种信息表字段定义
     */
    static final String T_CardTypes_Columns = "cardtypeid, userid, name, cardicon, privilegeicon, identityicon, description,"
            + "price, period, isgive, isupgrade, isdelay, priority, orderid, status, ctime, utime";
    /**
     * 卡种特权表字段定义
     */
    static final String T_CardPrivilege_Columns = "cardprivilegeid, cardtypeid, privilegeid, isswitch, privilegevalue";
    /**
     * 特权配置字段定义
     */
    static final String T_PrivilegeSet_Columns = "privilegeid, name, code, type, coefficient, unit, tips";
    /**
     * 创建卡种信息
     * @param cardtypes--卡种信息
     * @return true--创建成功;false--创建失败
     */
    @ResultType(Boolean.class)
    @Insert("insert into " + T_CardTypes + "(" + T_CardTypes_Columns + ") values(#{cardTypeId}, #{userId}, #{name}, #{cardIcon},#{privilegeIcon}, "
            + "#{identityIcon}, #{description}, #{price},#{period}, #{isGive}, #{isUpgrade}, #{isDelay},#{priority}, #{orderID}, #{status}, "
            + "#{cTime}, #{uTime})")
    boolean createCardTypes(Cardtypes cardtypes);

    /**
     * 创建卡种特权信息
     * @param cardprivilege--卡种特权信息
     * @return true--创建成功;false--创建失败
     */
    @ResultType(Boolean.class)
    @Insert("insert into " + T_CardPrivilege + "(" + T_CardPrivilege_Columns + ") values(#{cardPrivilegeId}, #{cardTypeId}, #{privilegeId}, "
            + "#{isSwitch}, #{privilegeValue})")
    boolean createCardPrivilege(Cardprivilege cardprivilege);

    /**
     * 更新卡种信息
     * @param cardtypes--卡种信息
     * @return true--更新成功;false--更新失败
     */
    @ResultType(Boolean.class)
    @Update("update " + T_CardTypes + " set userid=#{userId}, name=#{name}, cardicon=#{cardIcon}, privilegeicon=#{privilegeIcon}, "
            + "identityicon=#{identityIcon}, description=#{description}, price=#{price}, period=#{period}, isgive=#{isGive}, "
            + "isupgrade=#{isUpgrade}, isdelay=#{isDelay}, priority=#{priority}, orderid=#{orderID}, status=#{status}, ctime=#{cTime}, "
            + "utime=#{uTime} WHERE cardtypeid=#{cardTypeId}")
    boolean updateCardTypes(Cardtypes cardtypes);

    /**
     * 根据卡种编号cardTypeId批量删除(物理删除)卡种特权信息
     * @param cardTypeId--卡种编号
     * @return true--删除成功;false--删除失败
     */
    @ResultType(Boolean.class)
    @Delete("delete from " + T_CardPrivilege + " where cardtypeid=#{cardTypeId}")
    boolean deleteCardPrivilege(@Param("cardTypeId") Long cardTypeId);

    /**
     * 根据卡种编号禁用或启用指定卡种信息
     * @param cardTypeId--卡种编号
     * @param status--开启或关闭,0代表禁用;1代表启用
     * @return true--启用或禁用操作成功;false--启用或禁用操作失败
     */
    @ResultType(Boolean.class)
    @Update("update " + T_CardTypes + " set status=#{status} where cardtypeid=#{cardTypeId}")
    boolean switchCardTypes(@Param("cardTypeId") Long cardTypeId, @Param("status") Integer status);

    /**
     * 根据启用状态获取卡种列表信息
     * @param status--开启或关闭,0代表禁用;1代表启用;-1代表所有
     * @return 卡种列表信息
     */
    @ResultType(Cardtypes.class)
    @Select("<script> select " + T_CardTypes_Columns + " from " + T_CardTypes  + " where 1=1 " +
            "<if test=\"null!=status and status>-1\"> and status=#{status} </if>" +
            "</script>")
    List<Cardtypes> query(@Param("status") Integer status);

    /**
     * 根据卡种编号获取卡种详情信息
     * @param cardTypeId--卡种编号
     * @return 卡种详情信息
     */
    @ResultType(Cardtypes.class)
    @Select("select " + T_CardTypes_Columns + " from " + T_CardTypes + " where cardtypeid=#{cardTypeId}")
    Cardtypes get(@Param("cardTypeId") Long cardTypeId);

    /**
     * 根据卡种编号获取卡种特权列表信息
     * @param cardTypeId--卡种编号
     * @return 卡种特权列表信息
     */
    @ResultType(CardprivilegeBO.class)
    @Select("select a.cardprivilegeid,a.cardtypeid,a.privilegeid,a.isswitch,a.privilegevalue,b.name,b.code,b.type,b.coefficient,b.unit,b.tips"
            + " from user_cardprivilege a,user_privilegeset b where a.privilegeid = b.privilegeid and a.cardtypeid=#{cardTypeId}")
    List<CardprivilegeBO> find(@Param("cardTypeId") Long cardTypeId);

    /**
     * 加载所有特权列表信息
     * @return 特权列表信息
     */
    @ResultType(Privilegeset.class)
    @Select("select " + T_PrivilegeSet_Columns + " from " + T_PrivilegeSet)
    List<Privilegeset> load();
}