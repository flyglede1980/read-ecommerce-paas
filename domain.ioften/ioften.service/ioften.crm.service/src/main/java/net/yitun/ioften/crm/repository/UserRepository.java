package net.yitun.ioften.crm.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

import net.yitun.basic.model.vo.DeleteVo;
import net.yitun.ioften.crm.entity.User;
import net.yitun.ioften.crm.entity.vo.user.UserQueryVo;
import net.yitun.ioften.crm.entity.vo.user.UserStatusModifyVo;

/**
 * <pre>
 * <b>用户 用户管理持久化.</b>
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
@Repository
public interface UserRepository {

    // 表名
    static final String TABLE = "crm_user";

    // 查询字段
    static final String COLUMNS = "t1.id, t1.nick, t1.name, t1.phone, t1.passwd, t1.pay_passwd,  t1.sex, t1.type, t1.level"
            + ", t1.city, t1.birthday, t1.idcard, t1.operator, t1.intro, t1.invite, t1.device, t1.category_id, t1.category_name, t1.sub_category_id, t1.sub_category_name, t1.remark, t1.status, t1.ctime, t1.mtime";

    @ResultType(User.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.id=#{id}")
    User get(@Param("id") long id);

    @ResultType(User.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.id=#{id} for update")
    User lock(@Param("id") long id);

    @ResultType(User.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.invite=#{invite}")
    User findByInvite(String invite);

    @ResultType(User.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.phone=#{phone}")
    User findByPhone(@Param("phone") String phone);

    @ResultType(User.class)
    @Select("<script>" //
            + "select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1" //
            + " <if test='null!=id'>and t1.id=#{id}</if>" //
            + " <if test='null!=nick'>and t1.nick=#{nick}</if>" //
            + " <if test='null!=name'>and t1.name=#{name}</if>" //
            + " <if test='null!=phone'>and t1.phone=#{phone}</if>" //
            + " <if test='null!=city'>and t1.city=#{city}</if>" //
            + " <if test='null!=idcard'>and t1.idcard=#{idcard}</if>" //
            + " <if test='null!=operator'>and t1.operator=#{operator}</if>" //
            + " <if test='null!=invite'>and t1.invite=#{invite}</if>" //
            + " <if test='null!=device'>and t1.device=#{device}</if>" //
            + " <if test='null!=categoryId'>and t1.category_id=#{categoryId}</if>" //
            + " <if test='null!=subCategoryId'>and t1.sub_category_id=#{subCategoryId}</if>" //
            + " <if test='null!=sexs'><foreach collection='sexs' item='item' separator=', ' open=' and t1.sex in (' close=')'>#{item}</foreach></if>"
            + " <if test='null!=types'><foreach collection='types' item='item' separator=', ' open=' and t1.type in (' close=')'>#{item}</foreach></if>"
            + " <if test='null!=levels'><foreach collection='levels' item='item' separator=', ' open=' and t1.level in (' close=')'>#{item}</foreach></if>"
            + " <if test='null!=statuss'><foreach collection='statuss' item='item' separator=', ' open=' and t1.status in (' close=')'>#{item}</foreach></if>"
            + " <if test='null!=etime'>and t1.ctime&lt;=#{etime}</if>" //
            + " <if test='null!=stime'>and t1.ctime&gt;=#{stime}</if>" //
            + "</script>")
    Page<User> query(UserQueryVo queryVo);

    @ResultType(Boolean.class)
    @Insert("insert ignore into " + TABLE //
            + " ( id, nick, name, phone, passwd, pay_passwd, sex, type, level"
            + "    , city, birthday, idcard, operator, intro, invite, device, category_id, category_name, sub_category_id, sub_category_name, remark, status, ctime, mtime, valid )"
            + " select  #{id}, #{nick}, #{name}, #{phone}, #{passwd}, #{payPasswd}, #{sex}, #{type}, #{level}"
            + "    , #{city}, #{birthday}, #{idcard}, #{operator}, #{intro}, #{invite}, #{device}, #{categoryId}, #{categoryName}, #{subCategoryId}, #{subCategoryName}, #{remark}, #{status}, #{ctime}, #{mtime}, 1" //
            + "    from dual where not exists ( select 1 from " + TABLE + " t1 where t1.valid=1 and t1.phone=#{phone} )")
    boolean create(User entity);

    @ResultType(Boolean.class)
    @Update("update " + TABLE + " t1 set t1.passwd=#{passwd}, t1.mtime=#{mtime} where t1.valid=1 and t1.phone=#{phone}")
    boolean modifyPwd(User entity);

    @ResultType(Boolean.class)
    @Update("update " + TABLE + " t1 set t1.phone=#{phone}, t1.mtime=#{mtime} where t1.valid=1 and t1.id=#{id}")
    boolean modifyPhone(User entity);

    @ResultType(Boolean.class)
    @Update("update " + TABLE + " t1 set t1.pay_passwd=#{payPasswd}, t1.mtime=#{mtime} where t1.valid=1 and t1.id=#{id}")
    boolean modifyPayPwd(User entity);

    @ResultType(Boolean.class)
    @Update("<script>" //
            + "update " + TABLE + " t1 set t1.mtime=#{mtime}" //
            + " <if test='null!=sex'>, t1.sex=#{sex}</if>" //
            + " <if test='null!=city'>, t1.city=#{city}</if>" //
            + " <if test='null!=nick'>, t1.nick=#{nick}</if>" //
            + " <if test='null!=intro'>, t1.intro=#{intro}</if>" //
            + " <if test='null!=device'>, t1.device=#{device}</if>" // 设备ID
            + " <if test='null!=birthday'>, t1.birthday=#{birthday}</if>" //
            + " where t1.valid=1 and t1.id=#{id}" //
            + " <if test='null!=invite'>and ( t1.invite is null or t1.invite&lt;&gt;#{invite})</if></script>")
    boolean modifyDatum(User user);

    @ResultType(Boolean.class)
    @Update("<script>select count(1) from " + TABLE
            + " t1 where t1.valid=1 and t1.id&lt;&gt;#{id} and t1.invite=#{invite}</script>")
    boolean existsInvite(User user);

    @ResultType(Boolean.class)
    @Update("update " + TABLE + " t1 set t1.invite=#{invite}, t1.mtime=#{mtime} where t1.valid=1 and t1.id=#{id}")
    boolean modifyInvite(User user);

    @ResultType(Boolean.class)
    @Update("update " + TABLE + " t1" //
            + " set t1.city=#{city}, t1.name=#{name}, t1.name=#{name}, t1.phone=#{phone}"
            + "    , t1.type=#{type}, t1.idcard=#{idcard}, t1.operator=#{operator}, t1.category_id=#{categoryId}, t1.category_name=#{categoryName}"
            + "    , t1.sub_category_id=#{subCategoryId}, t1.sub_category_name=#{subCategoryName}, t1.mtime=#{mtime} where t1.valid=1 and t1.id=#{id}")
    boolean modifyIdentity(User entity);

    @ResultType(Boolean.class)
    @Update("<script>" //
            + "update " + TABLE + " t1" //
            + " set t1.status=#{status}, t1.mtime=#{mtime}" //
            + "    <if test='null!=remark'>, t1.remark=#{remark}</if>" //
            + " where t1.valid=1 and t1.status&lt;&gt;#{status}" //
            + "    <if test='null!=ids and 0!=ids.size()'><foreach collection='ids' item='item' separator=', ' open=' and t1.id in (' close=')'>#{item}</foreach></if></script>")
    boolean modifyStatus(UserStatusModifyVo modifyVo);

    @ResultType(Integer.class)
    @Update("<script>" //
            + "update " + TABLE + " t1 set t1.valid=0, t1.mtime=#{mtime}" //
            + " where t1.valid=1 <if test='null!=ids'><foreach collection='ids' item='item' separator=', ' open=' and t1.id in (' close=')'>#{item}</foreach></if></script>")
    int delete(DeleteVo deleteVo);

}
