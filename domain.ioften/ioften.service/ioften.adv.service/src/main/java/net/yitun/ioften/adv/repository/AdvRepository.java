package net.yitun.ioften.adv.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

import net.yitun.basic.model.vo.DeleteVo;
import net.yitun.ioften.adv.entity.Adv;
import net.yitun.ioften.adv.entity.vo.AdvQueryVo;

/**
 * <pre>
 * <b>广告 内容持久化.</b>
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
@Repository("adv.AdvRepository")
public interface AdvRepository {

    // 表名
    static final String TABLE = "adv_adv";

    // 查询字段
    static final String COLUMNS = "t1.id, t1.title, t1.cover, t1.seat, t1.action, t1.show_btn, t1.action_conf, t1.sequence, t1.remark, t1.status, t1.ctime, t1.mtime";

    @ResultType(Adv.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.id=#{id}")
    Adv get(@Param("id") long id);

    @ResultType(Adv.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.id=#{id} for update")
    Adv lock(@Param("id") long id);

    @ResultType(Adv.class)
    @Select("<script>" //
            + "select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1" //
            + " <if test='null!=id'>and t1.id=#{id}</if>" //
            + " <if test='null!=title'>and t1.title=#{title}</if>" //
            + " <if test='null!=showBtn'>and t1.show_btn=#{showBtn}</if>" //
            + " <if test='null!=seats'><foreach collection='seats' item='item' separator=', ' open=' and t1.seat in (' close=')'>#{item}</foreach></if>"
            + " <if test='null!=actions'><foreach collection='actions' item='item' separator=', ' open=' and t1.action in (' close=')'>#{item}</foreach></if>"
            + " <if test='null!=statuss'><foreach collection='statuss' item='item' separator=', ' open=' and t1.status in (' close=')'>#{item}</foreach></if></script>")
    Page<Adv> query(AdvQueryVo queryVo);

    @ResultType(Boolean.class)
    @Insert("insert ignore into " + TABLE //
            + " ( id, title, cover, seat, action, show_btn, action_conf, sequence, remark, status, ctime, mtime, valid )" //
            + " values ( #{id}, #{title}, #{cover}, #{seat}, #{action}, #{showBtn}, #{actionConf}, #{sequence}, #{remark}, #{status}, #{ctime}, #{mtime}, 1)")
    boolean create(Adv entity);

    @ResultType(Boolean.class)
    @Update("update " + TABLE //
            + " t1 set t1.title=#{title}, t1.cover=#{cover}, t1.seat=#{seat}, t1.action=#{action}, t1.show_btn=#{showBtn}"
            + ", t1.action_conf=#{actionConf}, t1.sequence=#{sequence}, t1.remark=#{remark}, t1.status=#{status}, t1.mtime=#{mtime} where t1.valid=1 and t1.id=#{id}")
    boolean modify(Adv entity);

    @ResultType(Integer.class)
    @Update("<script>" //
            + "update " + TABLE + " t1 set t1.valid=0, t1.mtime=#{mtime} where t1.valid=1" //
            + " <if test='null!=ids'><foreach collection='ids' item='item' separator=', ' open=' and t1.id in (' close=')'>#{item}</foreach></if></script>")
    int delete(DeleteVo deleteVo);

}
