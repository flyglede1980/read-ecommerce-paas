package net.yitun.ioften.crm.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

import net.yitun.basic.model.vo.DeleteVo;
import net.yitun.ioften.crm.entity.Identity;
import net.yitun.ioften.crm.entity.vo.IdentityQueryVo;

/**
 * <pre>
 * <b>用户 认证持久化.</b>
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
public interface IdentityRepository {

    // 表名
    static final String TABLE = "crm_identity";

    // 查询字段
    static final String COLUMNS = "t1.id, t1.uid, t1.type, t1.name, t1.phone, t1.city, t1.idcard, t1.evidences, t1.operator"
            + ", t1.category_id, t1.category_name, t1.sub_category_id, t1.sub_category_name, t1.audit, t1.audit_id, t1.audit_time, t1.audit_remark, t1.status, t1.ctime, t1.mtime";

    @ResultType(Identity.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.id=#{id}")
    Identity get(@Param("id") long id);

    @ResultType(Identity.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.id=#{id} for update")
    Identity lock(@Param("id") long id);

    @ResultType(Identity.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.uid=#{uid} order by t1.status desc, t1.type desc, t1.id desc limit 1")
    Identity findByUid(@Param("uid") long id);

    @ResultType(Identity.class)
    @Select("<script>" //
            + "select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1" //
            + " <if test='null!=id'>and t1.id=#{id}</if>" //
            + " <if test='null!=uid'>and t1.uid=#{uid}</if>" //
            + " <if test='null!=name'>and t1.name=#{name}</if>" //
            + " <if test='null!=phone'>and t1.phone=#{phone}</if>" //
            + " <if test='null!=etime'>and t1.ctime&lt;=#{etime}</if>" //
            + " <if test='null!=stime'>and t1.ctime&gt;=#{stime}</if>" //
            + " <if test='null!=types'><foreach collection='types' item='item' separator=', ' open=' and t1.type in (' close=')'>#{item}</foreach></if>"
            + " <if test='null!=statuss'><foreach collection='statuss' item='item' separator=', ' open=' and t1.status in (' close=')'>#{item}</foreach></if></script>")
    Page<Identity> query(IdentityQueryVo queryVo);

    @ResultType(Boolean.class)
    @Insert("insert ignore into " + TABLE //
            + " ( id, uid, type, name, phone, city, idcard, evidences, operator"
            + "    , category_id, category_name, sub_category_id, sub_category_name, audit, audit_id, audit_time, audit_remark, status, ctime, mtime, valid )"
            + " select  #{id}, #{uid}, #{type}, #{name}, #{phone}, #{city}, #{idcard}, #{evidences}"
            + "    , #{operator}, #{categoryId}, #{categoryName}, #{subCategoryId}, #{subCategoryName}, #{audit}, #{auditId}, #{auditTime}, #{auditRemark}, #{status}, #{ctime}, #{mtime}, 1" //
            + "    from dual where not exists ( select 1 from " + TABLE
            + "        t1 where t1.valid=1 and t1.uid=#{uid} and t1.type=#{type})")
    boolean create(Identity entity);

    @ResultType(Boolean.class)
    @Update("update " + TABLE + " t1 set t1.audit=#{audit}, t1.audit_id=#{auditId}, t1.audit_time=#{auditTime}"
            + "    , t1.audit_remark=#{auditRemark}, t1.status=#{status}, t1.mtime=#{mtime} where t1.valid=1 and t1.id=#{id} and t1.status<>#{status}")
    boolean modify(Identity entity);

    @ResultType(Integer.class)
    @Update("<script>" //
            + "update " + TABLE + " t1 set t1.valid=0, t1.mtime=#{mtime}" //
            + " where t1.valid=1" //
            + " <if test='null!=ids'><foreach collection='ids' item='item' separator=', ' open=' and t1.id in (' close=')'>#{item}</foreach></if></script>")
    int delete(DeleteVo deleteVo);

}
