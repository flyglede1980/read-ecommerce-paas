package net.yitun.ioften.sys.repository;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

import net.yitun.basic.model.vo.DeleteVo;
import net.yitun.ioften.sys.entity.Issue;
import net.yitun.ioften.sys.entity.vo.IssueQueryVo;

/**
 * <pre>
 * <b>系统 QA持久化.</b>
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
@Repository("sys.IssueRepository")
public interface IssueRepository {

    // 表名
    static final String TABLE = "sys_issue";

    // 查询字段
    static final String COLUMNS = "t1.id, t1.title, t1.answer, t1.sequence, t1.ctime, t1.mtime";

    @ResultType(Issue.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.id=#{id}")
    Issue get(@Param("id") long id);

    @ResultType(Issue.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.id=#{id} for update")
    Issue lock(@Param("id") long id);

    @ResultType(Issue.class)
    @Select("<script>" //
            + "select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1" //
            + " <if test='null!=id'>and t1.id=#{id}</if></script>")
    Page<Issue> query(IssueQueryVo queryVo);

    @ResultType(Integer.class)
    @Update("<script>" //
            + "update " + TABLE + " t1 set t1.valid=0, t1.mtime=#{mtime}" //
            + " where t1.valid=1 <if test='null!=ids'><foreach collection='ids' item='item' separator=', ' open=' and t1.id in (' close=')'>#{item}</foreach></if></script>")
    int delete(DeleteVo deleteVo);

}
