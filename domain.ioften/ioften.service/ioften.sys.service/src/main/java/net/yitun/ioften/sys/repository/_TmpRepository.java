package net.yitun.ioften.sys.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

import net.yitun.basic.model.vo.DeleteVo;
import net.yitun.ioften.sys.entity._Tmp;
import net.yitun.ioften.sys.entity.vo._TmpQueryVo;

/**
 * <pre>
 * <b>Tmp 持久化.</b>
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
@Repository("sys._TmpRepository")
public interface _TmpRepository {

    // 表名
    static final String TABLE = "sys_tmp";

    // 查询字段
    static final String COLUMNS = "t1.id, t1.name, t1.status, t1.ctime, t1.mtime";

    @ResultType(_Tmp.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.id=#{id}")
    _Tmp get(@Param("id") long id);

    @ResultType(_Tmp.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.code=#{code}")
    _Tmp findByName(@Param("name") String name);

    @ResultType(_Tmp.class)
    @Select("<script>" //
            + "select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1" //
            + " <if test='null!=id'>and t1.id=#{id}</if>" //
            + " <if test='null!=name'>and t1.name=#{name}</if>" //
            + " <if test='null!=etime'>and t1.ctime&lt;=#{etime}</if>" //
            + " <if test='null!=stime'>and t1.ctime&gt;=#{stime}</if>" //
            + " <if test='null!=statuss'><foreach collection='statuss' item='item' separator=', ' open=' and t1.status in (' close=')'>#{item}</foreach></if></script>")
    Page<_Tmp> query(_TmpQueryVo queryVo);

    @ResultType(Boolean.class)
    @Insert("insert ignore into " + TABLE //
            + " ( id, name, status, ctime, mtime, valid ) " //
            + " select #{id}, #{name}, #{status}, #{ctime}, #{mtime}, 1 " //
            + "    from dual where not exists ( select 1 from " + TABLE + " t1 where t1.valid=1 and t1.name=#{name} )")
    boolean create(_Tmp entity);

    @ResultType(Boolean.class)
    @Update("update " + TABLE + " t1 set t1.name=#{name}, t1.mtime=#{mtime} where t1.valid=1 and t1.id=#{id}")
    boolean modify(_Tmp entity);

    @ResultType(Integer.class)
    @Update("<script>" //
            + "update " + TABLE + " t1 set t1.valid=0, t1.mtime=#{mtime}" //
            + " where t1.valid=1 <if test='null!=ids'><foreach collection='ids' item='item' separator=', ' open=' and t1.id in (' close=')'>#{item}</foreach></if></script>")
    int delete(DeleteVo deleteVo);

}
