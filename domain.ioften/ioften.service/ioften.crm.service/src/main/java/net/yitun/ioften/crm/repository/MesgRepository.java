package net.yitun.ioften.crm.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

import net.yitun.basic.dict.YesNo;
import net.yitun.ioften.crm.dicts.MesgType;
import net.yitun.ioften.crm.entity.Mesg;
import net.yitun.ioften.crm.entity.vo.mesg.MesgQueryVo;
import net.yitun.ioften.crm.entity.vo.mesg.MesgStatusModifyVo;

/**
 * <pre>
 * <b>用户 消息持久化.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月17日 下午4:40:51
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月17日 下午4:40:51 LH
 *         new file.
 * </pre>
 */
@Repository
public interface MesgRepository {

    // 表名
    static final String TABLE = "crm_mesg";

    // 查询字段
    static final String COLUMNS = "t1.id, t1.uid, t1.type, t1.actor, t1.target, t1.content, t1.times, t1.status, t1.ctime, t1.mtime";

    @ResultType(Mesg.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.id=#{id}")
    Mesg get(@Param("id") long id);

    @ResultType(Mesg.class)
    @Select("<script>" //
            + "select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1" //
            + " <if test='null!=uid'>and t1.uid=#{uid}</if>" //
            + " <if test='null!=type'>and t1.type=#{type}</if></script>")
    Page<Mesg> query(MesgQueryVo queryVo);

    @ResultType(Mesg.class)
    @Select("select " + COLUMNS + " from " + TABLE
            + " t1 where t1.valid=1 and t1.type=#{type} and t1.actor=#{actor} and t1.target=#{target} limit 1")
    Mesg findByTypeActorTarget(@Param("type") MesgType type, @Param("actor") Long actor, @Param("target") Long target);

    @ResultType(Mesg.class)
    @Select("select " + COLUMNS + " from " + TABLE
            + " t1 where t1.valid=1 and t1.type=#{type} and t1.actor=#{actor} and t1.target=#{target} limit 1 for update")
    Mesg lockByTypeActorTarget(@Param("type") MesgType type, @Param("actor") Long actor, @Param("target") Long target);

    @ResultType(Integer.class)
    @Select("select count(1) _count" //
            + " from " + TABLE + " t1 where t1.valid=1 and t1.uid=#{uid} and t1.status=#{status}")
    int countByStatus(@Param("uid") Long uid, @Param("status") YesNo status);

    @ResultType(Boolean.class)
    @Insert("insert ignore into " + TABLE //
            + " ( id, uid, type, actor, target, content, times, status, ctime, mtime, valid )" //
            + " values ( #{id}, #{uid}, #{type}, #{actor}, #{target}, #{content}, #{times}, #{status}, #{ctime}, #{mtime}, 1 )")
    boolean create(Mesg entity);

    @ResultType(Boolean.class)
    @Insert("update " + TABLE + " t1 set t1.times=#{times}, t1.mtime=#{mtime} where t1.valid=1 and t1.id=#{id}")
    boolean modify(Mesg entity);

    @ResultType(Integer.class)
    @Update("<script>" //
            + "update " + TABLE + " t1 set t1.status=#{status}, t1.mtime=#{mtime}" //
            + " where t1.valid=1 and t1.uid=#{uid} and t1.status&lt;&gt;#{status}" //
            + "    <if test='null!=ids'><foreach collection='ids' item='item' separator=', ' open=' and t1.id in (' close=')'>#{item}</foreach></if></script>")
    int viewed(MesgStatusModifyVo modifyVo);

}
