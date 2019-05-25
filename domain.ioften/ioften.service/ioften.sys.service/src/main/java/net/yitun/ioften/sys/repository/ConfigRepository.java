package net.yitun.ioften.sys.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

import net.yitun.basic.model.vo.DeleteVo;
import net.yitun.ioften.sys.entity.Config;
import net.yitun.ioften.sys.entity.vo.ConfigQueryVo;

/**
 * <pre>
 * <b>系统 配置持久化.</b>
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
@Repository("sys.ConfigRepository")
public interface ConfigRepository {

    // 表名
    static final String TABLE = "sys_config";

    // 查询字段
    static final String COLUMNS = "t1.id, t1.code, t1.name, t1.scope, t1.value, t1.remark, t1.modifier, t1.modifier_id, t1.ctime, t1.mtime";

    @ResultType(Config.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.id=#{id}")
    Config get(@Param("id") long id);

    @ResultType(Config.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.id=#{id} for update")
    Config lock(@Param("id") long id);

    @ResultType(Config.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.code=#{code}")
    Config findByCode(@Param("code") String code);

    @ResultType(Config.class)
    @Select("<script>" //
            + "select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1" //
            + " <if test='null!=id'>and t1.id=#{id}</if>" //
            + " <if test='null!=code'>and t1.code=#{code}</if>" //
            + " <if test='null!=name'>and t1.name=#{name}</if>" //
            + " <if test='null!=modifier'>and t1.modifier=#{modifier}</if>" //
            + " <if test='null!=modifierId'>and t1.modifier_id=#{modifierId}</if>" //
            + " <if test='null!=etime'>and t1.ctime&lt;=#{etime}</if>" //
            + " <if test='null!=stime'>and t1.ctime&gt;=#{stime}</if>" //
            + " <if test='null!=scopes'><foreach collection='scopes' item='item' separator=', ' open=' and t1.scope in (' close=')'>#{item}</foreach></if></script>")
    Page<Config> query(ConfigQueryVo queryVo);

    @ResultType(Boolean.class)
    @Insert("insert ignore into " + TABLE //
            + " ( id, code, name, scope, value, remark, modifier, modifier_id, ctime, mtime, valid )" //
            + " select #{id}, #{code}, #{name}, #{scope}, #{value}, #{remark}, #{modifier}, #{modifierId}, #{ctime}, #{mtime}, 1" //
            + "    from dual where not exists ( select 1 from " + TABLE + " t1 where t1.valid=1 and t1.code=#{code} )")
    boolean create(Config entity);

    @ResultType(Boolean.class)
    @Update("update " + TABLE + " t1" //
            + " set t1.name=#{name}, t1.scope=#{scope}, t1.value=#{value}, t1.remark=#{remark}"
            + "    , t1.modifier=#{modifier}, t1.modifier_id=#{modifierId}, t1.mtime=#{mtime} where t1.valid=1 and t1.id=#{id}")
    boolean modify(Config entity);

    @ResultType(Integer.class)
    @Update("<script>" //
            + "update " + TABLE + " t1 set t1.valid=0, t1.modifier=#{modifier}, t1.modifier_id=#{uid}, t1.mtime=#{mtime}" //
            + " where t1.valid=1 <if test='null!=ids'><foreach collection='ids' item='item' separator=', ' open=' and t1.id in (' close=')'>#{item}</foreach></if></script>")
    int delete(DeleteVo deleteVo);

}
