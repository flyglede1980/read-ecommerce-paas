package net.yitun.ioften.crm.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

import net.yitun.basic.model.vo.DeleteVo;
import net.yitun.ioften.crm.entity.Shield;
import net.yitun.ioften.crm.entity.vo.ShieldQueryVo;

/**
 * <pre>
 * <b>用户 拉黑持久化.</b>
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
public interface ShieldRepository {

    // 表名
    static final String TABLE = "crm_shield";
    static final String TABLE_USER = "crm_user";

    // 查询字段
    static final String COLUMNS = "t1.id, t1.uid, t1.side, t1.ctime, t1.mtime";

    @Results(id = "ShieldMap", value = { //
            @Result(column = "id", property = "id"), //
            @Result(column = "uid", property = "uid"), //
            @Result(column = "side", property = "side"), //
            @Result(column = "ctime", property = "ctime"), //
            @Result(column = "mtime", property = "mtime"), //
            @Result(column = "sideNick", property = "sideUser.nick"), //
            @Result(column = "sideType", property = "sideUser.type"), //
            @Result(column = "sideLevel", property = "sideUser.level") //
    })
    @Select("<script>" //
            + "select " + COLUMNS + ", t2.nick sideNick, t2.type sideType, t2.level sideLevel" //
            + " from " + TABLE + " t1 inner join " + TABLE_USER + " t2 on t2.id=t1.side" //
            + " where t1.valid=1 and t2.valid=1" //
            + " <if test='null!=id'>and t1.id=#{id}</if>" //
            + " <if test='null!=uid'>and t1.uid=#{uid}</if>" //
            + " <if test='null!=side'>and t1.side=#{side}</if>" //
            + " <if test='null!=sideNick'>and t2.nick=#{sideNick}</if>" //
            + "</script>")
    Page<Shield> query(ShieldQueryVo queryVo);

    @ResultType(Boolean.class)
    @Insert("insert ignore into " + TABLE + " ( id, uid, side, ctime, mtime, valid )" //
            + " select  #{id}, #{uid}, #{side}, #{ctime}, #{mtime}, 1" //
            + "    from dual where not exists ( select 1 from " + TABLE
            + "        t1 where t1.valid=1 and t1.uid=#{uid} and t1.side=#{side} )")
    boolean create(Shield entity);

    @ResultType(Integer.class)
    @Update("<script>" //
            + "update " + TABLE + " t1 set t1.valid=0, t1.mtime=#{mtime}" //
            + " where t1.valid=1 and t1.uid=#{uid}" //
            + "    <if test='null!=ids'><foreach collection='ids' item='item' separator=', ' open=' and t1.id in (' close=')'>#{item}</foreach></if></script>")
    int delete(DeleteVo deleteVo);

}
