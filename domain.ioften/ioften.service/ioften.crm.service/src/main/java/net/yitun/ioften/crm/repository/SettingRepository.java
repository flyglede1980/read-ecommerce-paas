package net.yitun.ioften.crm.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import net.yitun.basic.model.vo.DeleteVo;
import net.yitun.ioften.crm.entity.Setting;

/**
 * <pre>
 * <b>用户 我的设置持久化.</b>
 * <b>Description:</b>
 *
 * <b>Author:</b> XJN
 * <b>Date:</b> 2018-11-12 11:17:00.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月12日 上午11:17:06 XJN
 *         new file.
 * </pre>
 */
@Repository
public interface SettingRepository {

    // 表名
    static final String TABLE = "crm_setting";

    // 查询字段
    static final String COLUMNS = "t1.id, t1.uid, t1.code, t1.value, t1.ctime, t1.mtime";

    @ResultType(Setting.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.id=#{id}")
    Setting get(Long id);

    @ResultType(Setting.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.id=#{id} for update")
    Setting lock(Long id);

    @ResultType(Setting.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.uid=#{uid} and t1.code=#{code}")
    Setting getByUid(Long uid, String code);

    @ResultType(Boolean.class)
    @Insert("insert into " + TABLE + " ( id, uid, code, value, ctime, mtime, valid )" //
            + " select  #{id}, #{uid}, #{code}, #{value}, #{ctime}, #{mtime}, 1 from dual" //
            + "     where not exists ( select 1 from " + TABLE
            + "          t1 where t1.valid=1 and t1.uid=#{uid}  and t1.code=#{code} )")
    boolean create(Setting setting);

    @ResultType(Boolean.class)
    @Update("update " + TABLE + " t1" //
            + " set t1.value=#{value}, t1.mtime=#{mtime} where t1.valid=1 and t1.uid=#{uid} and t1.code=#{code}")
    boolean modify(Setting setting);

    @ResultType(Integer.class)
    @Update("<script>" //
            + "update " + TABLE + " t1 set t1.valid=0, t1.mtime=#{mtime}" //
            + " where t1.valid=1 <foreach collection='ids' item='item' separator=', ' open=' and t1.id in (' close=')'>#{item}</foreach></script>")
    int delete(DeleteVo deleVo);

}
