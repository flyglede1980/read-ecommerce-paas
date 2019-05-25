package net.yitun.ioften.cms.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import net.yitun.ioften.cms.entity.Share;

/**
 * <pre>
 * <b>资讯 分享记录持久化.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月19日 上午10:23:02
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月19日 上午10:23:02 LH
 *         new file.
 * </pre>
 */
@Repository(value = "cms.ShareRepository")
public interface ShareRepository {

    // 表名
    static final String TABLE = "cms_share";

    // 查询字段
    static final String COLUMNS = "t1.id, t1.uid, t1.aid, t1.way, t1.award, t1.views, t1.ctime, t1.mtime";

    /** 记录详情 */
    @ResultType(Share.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.id=#{id}")
    Share get(Long id);

    /** 锁定记录 */
    @ResultType(Share.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.id=#{id} for update")
    Share lock(Long id);

    /** 锁定查找 */
    @ResultType(Share.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.uid=#{uid} and t1.aid=#{aid} for update")
    Share lockFind(Long uid, Long aid);

    /** 创建记录 */
    @ResultType(Boolean.class)
    @Insert("insert ignore into " + TABLE //
            + " (id, uid, aid, way, award, views, ctime, mtime, valid) "
            + " select #{id}, #{uid}, #{aid}, #{way}, #{award}, #{views}, #{ctime}, #{mtime}, 1"
            + "    from dual where not exists ( select 1 from " + TABLE
            + "        t1 where t1.valid=1 and t1.uid=#{uid} and t1.aid=#{aid} )")
    boolean create(Share entity);

    /** 更新记录 */
    @ResultType(Boolean.class)
    @Update("<script>" //
            + "update " + TABLE + " t1 set t1.views=#{views}," //
            + " <if test='null!=award'> t1.award=#{award},</if>" //
            + " t1.mtime=#{mtime} where t1.valid=1 and t1.id=#{id}</script>")
    boolean modify(Share entity);

}
