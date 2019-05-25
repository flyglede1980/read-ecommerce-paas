package net.yitun.ioften.sys.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

import net.yitun.basic.model.vo.DeleteVo;
import net.yitun.ioften.sys.entity.Admin;
import net.yitun.ioften.sys.entity.vo.AdminQueryVo;

/**
 * <pre>
 * <b>系统 管理员持久化.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月7日 下午2:41:05 LH
 *         new file.
 * </pre>
 */
@Repository("sys.AdminRepository")
public interface AdminRepository {

    // 表名
    static final String TABLE = "sys_admin";

    // 查询字段
    static final String COLUMNS = "t1.id, t1.name, t1.account, t1.passwd, t1.roles, t1.phone, t1.email, t1.status, t1.ctime, t1.mtime";

    @ResultType(Admin.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.id=#{id}")
    Admin get(@Param("id") long id);

    @ResultType(Admin.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.account=#{account}")
    Admin findByAccount(@Param("account") String account);

    @ResultType(Admin.class)
    @Select("<script>" //
            + "select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1" //
            + " <if test='null!=id'>and t1.id=#{id}</if>" //
            + " <if test='null!=email'>and t1.email=#{email}</if>" //
            + " <if test='null!=name'>and t1.name=#{name}</if>" //
            + " <if test='null!=phone'>and t1.modifier=#{phone}</if>" //
            + " <if test='null!=account'>and t1.account=#{account}</if>" //
            + " <if test='null!=statuss'><foreach collection='statuss' item='item' separator=', ' open=' and t1.status in (' close=')'>#{item}</foreach></if></script>")
    Page<Admin> query(AdminQueryVo query);

    @ResultType(Integer.class)
    @Insert("insert ignore into " + TABLE //
            + " ( id, name, account, passwd, roles, phone, email, status, ctime, mtime )" //
            + " select  #{id}, #{code}, #{value}, #{modifier}, #{ctime}, #{mtime}, 1" //
            + "    from dual where not exists ( select 1 from " + TABLE + " t1 where t1.valid=1 and t1.account=#{account} )")
    int create(Admin entity);

    @ResultType(Integer.class)
    @Update("update " + TABLE + " t1" //
            + " set t1.status=#{status}, t1.modifier=#{modifier}, t1.mtime=#{mtime} where t1.valid=1 and t1.id=#{id}")
    int modifyStatus(Admin entity);

    @ResultType(Integer.class)
    @Update("<script>" //
            + "update " + TABLE + " t1 set t1.valid=0, t1.modifier=#{modifier}, t1.mtime=#{mtime}" //
            + " where t1.valid=1 <if test='null!=ids'><foreach collection='ids' item='item' separator=', ' open=' and t1.id in (' close=')'>#{item}</foreach></if></script>")
    int delete(DeleteVo delete);

}
