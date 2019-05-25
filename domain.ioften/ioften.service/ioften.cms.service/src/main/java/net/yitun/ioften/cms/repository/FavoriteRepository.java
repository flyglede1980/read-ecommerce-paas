package net.yitun.ioften.cms.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

import net.yitun.basic.model.vo.DeleteVo;
import net.yitun.ioften.cms.entity.Favorite;
import net.yitun.ioften.cms.entity.vo.FavoriteQueryVo;

/**
 * <pre>
 * <b>资讯 收藏资讯持久化.</b>
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
@Repository(value = "cms.FavoriteRepository")
public interface FavoriteRepository {

    // 表名
    static final String TABLE = "cms_favorite";

    // 查询字段
    static final String COLUMNS = "t1.id, t1.uid, t1.aid, t1.ctime, t1.mtime";

    /** 记录详情 */
    @ResultType(Favorite.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.id=#{id}")
    Favorite get(Long id);

    /** 锁定记录 */
    @ResultType(Favorite.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.id=#{id} for update")
    Favorite lock(Long id);

    /** 查找记录 */
    @ResultType(Favorite.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.uid=#{uid} and t1.aid=#{aid}")
    Favorite find(Long uid, Long aid);

    /** 锁定查找 */
    @ResultType(Favorite.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.uid=#{uid} and t1.aid=#{aid} for update")
    Favorite lockFind(Long uid, Long aid);

    /** 分页查询 */
    @ResultType(Favorite.class)
    @Select("<script>" //
            + "select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1" //
            + " <if test='null!=uid'>and t1.uid=#{uid}</if>" //
            + " <if test='null!=aid'>and t1.aid=#{aid}</if></script>")
    Page<Favorite> query(FavoriteQueryVo queryVo);

    /** 创建记录 */
    @ResultType(Boolean.class)
    @Insert("insert ignore into " + TABLE //
            + " (id, uid, aid, ctime, mtime, valid) " //
            + " select #{id}, #{uid}, #{aid}, #{ctime}, #{mtime}, 1" //
            + " from dual where not exists ( select 1 from " + TABLE
            + "        t1 where t1.valid=1 and t1.uid=#{uid} and t1.aid=#{aid} )")
    boolean create(Favorite entity);

    /** 删除记录 */
    @ResultType(Integer.class)
    @Update("<script>" //
            + "update " + TABLE + " t1 set t1.valid=0, t1.mtime=#{mtime}" //
            + " where t1.valid=1 and t1.uid=#{uid}" //
            + "    <if test='null!=ids'><foreach collection='ids' item='item' separator=', ' open=' and t1.id in (' close=')'>#{item}</foreach></if></script>")
    int delete(DeleteVo deleteVo);

}
