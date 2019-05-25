package net.yitun.ioften.cms.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

import net.yitun.ioften.cms.entity.Browse;
import net.yitun.ioften.cms.entity.vo.browse.BrowseQueryVo;
import net.yitun.ioften.cms.entity.vo.browse.DeleteVo;

/**
 * <pre>
 * <b>资讯 浏览记录持久化.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月19日 上午10:22:40
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月19日 上午10:22:40 LH
 *         new file.
 * </pre>
 */
@Repository(value = "cms.BrowseRepository")
public interface BrowseRepository {

    // 表名
    static final String TABLE = "cms_browse";

    // 查询字段
    static final String COLUMNS = "t1.id, t1.uid, t1.aid, t1.`view`, t1.enjoy, t1.award, t1.view_award, t1.enjoy_time, t1.enjoy_award, t1.reward, t1.status, t1.ctime, t1.mtime";

    /** 获取 */
    @ResultType(Browse.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.uid=#{uid} and t1.aid=#{aid}")
    Browse get(Long uid, Long aid);

    /** 锁定查找 */
    @ResultType(Browse.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.uid=#{uid} and t1.aid=#{aid} for update")
    Browse lock(Long uid, Long aid);

    /** 分页查询 */
    @ResultType(Browse.class)
    @Select("<script>" //
            + "select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1" //
            + " <if test='null!=uid'>and t1.uid=#{uid}</if>" //
            + " <if test='null!=status'>and t1.status=#{status}</if></script>")
    Page<Browse> query(BrowseQueryVo queryVo);

    /** 创建记录 */
    @ResultType(Boolean.class)
    @Insert("insert ignore into " + TABLE //
            + " (id, uid, aid, `view`, enjoy, award, view_award, enjoy_time, enjoy_award, reward, status, ctime, mtime, valid) "
            + " select #{id}, #{uid}, #{aid}, #{view}, #{enjoy}, #{award}, #{viewAward}, #{enjoyTime}, #{enjoyAward}, #{reward}, #{status}, #{ctime}, #{mtime}, 1"
            + "    from dual where not exists ( select 1 from " + TABLE
            + "        t1 where t1.valid=1 and t1.uid=#{uid} and t1.aid=#{aid} )")
    boolean create(Browse entity);

    /** 更新记录 */
    @ResultType(Boolean.class)
    @Update("update " + TABLE + " t1 set t1.`view`=#{view}, t1.enjoy=#{enjoy}, t1.award=#{award},"
            + "    t1.view_award=#{viewAward}, t1.enjoy_time=#{enjoyTime},  t1.enjoy_award=#{enjoyAward}, t1.status=#{status}, t1.mtime=#{mtime}"
            + " where t1.valid=1 and t1.id=#{id}") // and t1.view&lt;&gt;#{view}
    boolean modify(Browse entity);

    /** 删除记录 */
    @ResultType(Integer.class)
    @Update("<script>" //
            + "update " + TABLE + " t1 set t1.status=#{status}, t1.mtime=#{mtime}" //
            + " where t1.valid=1 and t1.uid=#{uid}" //
            + "    <if test='null!=ids'><foreach collection='ids' item='item' separator=', ' open=' and t1.id in (' close=')'>#{item}</foreach></if></script>")
    int delete(DeleteVo deleteVo);

}
