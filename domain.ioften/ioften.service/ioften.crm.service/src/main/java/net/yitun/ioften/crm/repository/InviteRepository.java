package net.yitun.ioften.crm.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

import net.yitun.ioften.crm.dicts.InviteStatus;
import net.yitun.ioften.crm.entity.Invite;
import net.yitun.ioften.crm.entity.vo.invite.InviteQueryVo;
import net.yitun.ioften.crm.entity.vo.invite.MyInviteTotal;

/**
 * <pre>
 * <b>用户 邀请持久化.</b>
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
public interface InviteRepository {

    // 表名
    static final String TABLE = "crm_invite";
    static final String TABLE_USER = "crm_user";

    // 查询字段
    static final String COLUMNS = "t1.id, t1.uid, t1.fuid, t1.phone, t1.award, t1.status, t1.ctime, t1.mtime";

    @Results(id = "InviteMap", value = { //
            @Result(column = "id", property = "id"), //
            @Result(column = "uid", property = "uid"), //
            @Result(column = "fuid", property = "fuid"), //
            @Result(column = "phone", property = "phone"), //
            @Result(column = "award", property = "award"), //
            @Result(column = "status", property = "status"), //
            @Result(column = "ctime", property = "ctime"), //
            @Result(column = "mtime", property = "mtime"), //
            @Result(column = "inviteNick", property = "inviteUser.nick"), //
            @Result(column = "inviteType", property = "inviteUser.type"), //
            @Result(column = "inviteLevel", property = "inviteUser.level"), //
    })
    @Select("<script>" //
            + "select " + COLUMNS + ", t2.nick inviteNick, t2.type inviteType, t2.level inviteLevel" //
            + " from " + TABLE + " t1 inner join " + TABLE_USER + " t2 on t2.id=t1.uid and t2.valid=1" //
            + " where t1.valid=1" //
            + " <if test='null!=id'>and t1.id=#{id}</if>" //
            + " <if test='null!=uid'>and t1.uid=#{uid}</if>" //
            + " <if test='null!=fuid'>and t1.fuid=#{fuid}</if>" //
            + " <if test='null!=phone'>and t1.phone like #{phone}</if>" //
            + " <if test='null!=inviteNick'>and t2.nick like #{inviteNick}</if>" //
            + " <if test='null!=statuss'><foreach collection='statuss' item='item' separator=', ' open=' and t1.status in (' close=')'>#{item}</foreach></if></script>")
    Page<Invite> query(InviteQueryVo queryVo);

    @ResultType(MyInviteTotal.class)
    @Select("select count(1) as total, sum(t1.award) as award_sum from " + TABLE
            + " t1 where t1.valid=1 and t1.fuid=#{fuid} and t1.status=#{status}")
    MyInviteTotal myTotal(@Param("fuid") long fuid, @Param("status") InviteStatus status);

    @ResultType(Boolean.class)
    @Insert("insert ignore into " + TABLE //
            + " ( id, uid, fuid, phone, award, status, ctime, mtime, valid )" //
            + " select  #{id}, #{uid}, #{fuid}, #{phone}, #{award}, #{status}, #{ctime}, #{mtime}, 1" //
            + "    from dual where not exists ( select 1 from " + TABLE
            + " t1 where t1.valid=1 and t1.fuid=#{fuid} and t1.phone=#{phone} )")
    boolean create(Invite entity);

}
