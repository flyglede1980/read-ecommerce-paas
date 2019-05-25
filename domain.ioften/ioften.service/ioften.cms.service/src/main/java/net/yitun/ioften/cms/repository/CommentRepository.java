package net.yitun.ioften.cms.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

import net.yitun.ioften.cms.entity.comment.Comment;
import net.yitun.ioften.cms.entity.comment.CommentEnjoy;
import net.yitun.ioften.cms.entity.comment.CommentReply;
import net.yitun.ioften.cms.entity.vo.CommentHotQueryVo;

@Repository
public interface CommentRepository {

    // 表名
    static final String TABLE = "cms_comment";

    // 查询字段
    static final String COLUMNS = "t1.id, t1.uid, t1.level, t1.article_id, t1.root_id, t1.parent_id, " +
            "t1.content, t1.enjoys, t1.comments, t1.weight,t1.status, t1.ctime, t1.mtime";

    /**
     * 查看评论用户详情
     *
     * @param parentId
     * @return
     */
    @ResultType(Comment.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where valid=1 and id=#{parentId}")
    Comment find(@Param("parentId") Long parentId);

    /**
     * 查看评论用户详情包括被删除的评论回复
     *
     * @param parentId
     * @return
     */
    @ResultType(Comment.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where  id=#{parentId}")
    Comment get(@Param("parentId") Long parentId);

    /**
     * 查看评论用户详情 加锁
     *
     * @param parentId
     * @return
     */
    @ResultType(Comment.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where valid=1 and id=#{parentId} for update")
    Comment lock(@Param("parentId") Long parentId);

    /**
     * 评论批量查询
     *
     * @return
     */
    @ResultType(Comment.class)
    @Select("<script>" +
            "select " + COLUMNS + " from " + TABLE + "  t1 where t1.valid=1 and t1.uid=#{uid} and t1.id in" +
            "<foreach item='item' collection='ids' open='(' separator=',' close=')'>" +
            " #{item} " +
            "</foreach>" +
            "</script>")
    List<Comment> queryAllById(@Param("ids") Collection<Long> ids, @Param("uid") Long uid);

    /**
     * 评论分页查询
     *
     * @param hotQueryVo
     * @return
     */
    @ResultType(Comment.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.level=0 and t1.article_id=#{articleId}")
    Page<Comment> query(CommentHotQueryVo hotQueryVo);

    /**
     * 热门回复分页查询
     * 自己的回复总数在最前
     *
     * @return
     */

    /*
select * from (
select *, 0 as o1, UNIX_TIMESTAMP(ctime) o2  from cms_comment where uid=1041794429293166592
union
select *, 1 as o1, enjoys o2 from cms_comment where uid<>1041794429293166592
) tt order by o1 asc, o2 desc limit 2, 6
    * */
    @Select("<script>" +
            "select  id,uid,level,root_id,parent_id,content,enjoys,comments,ctime from (" +
            "<if test=\"uid!=null\"> " +
            "(select id,uid,level,root_id,parent_id,content,enjoys,comments,ctime,0 as o1, UNIX_TIMESTAMP(ctime) o2 " +
            "from cms_comment where valid=1 and level!=0 and root_id=#{rootId} and uid=#{uid}) " +
            "union </if>" +
            "(select id,uid,level,root_id,parent_id,content,enjoys,comments,ctime,1 as o1, enjoys o2 from cms_comment " +
            "where valid=1 and level!=0 and root_id=#{rootId} <if test=\"uid!=null\"> and uid!=#{uid} </if> ) " +
            ") as temp order by o1 asc, o2 desc" +
            "</script>")
    Page<CommentReply> answer(@Param("rootId") Long rootId, @Param("uid") Long uid);

    /**
     * 添加评论
     *
     * @param comment
     * @return
     */
    @ResultType(Boolean.class)
    @Insert("insert into " + TABLE + " (id, uid, level, article_id, root_id, parent_id, content, enjoys, comments,weight, status, "
            + "ctime, mtime, valid) "
            + "values (#{id}, #{uid}, #{level}, #{articleId}, #{rootId}, #{parentId}, #{content}, #{enjoys}, #{comments},#{weight}, #{status}, "
            + "#{ctime}, #{mtime}, 1)")
    boolean create(Comment comment);

    /**
     * 设置评论数和点赞次数
     *
     * @param comment
     * @return
     */
    @ResultType(Boolean.class)
    @Update("update " + TABLE + " set enjoys=#{enjoys}, comments=#{comments}, weight=#{weight}, mtime=#{mtime}  " +
            "where valid=1 and id=#{id}")
    boolean modify(Comment comment);


    @ResultType(Boolean.class)
    @Insert("insert into cms_comment_enjoy (id, uid, comment_id, ctime, mtime, valid)"
            + "value (#{id}, #{uid}, #{commentId}, #{ctime}, #{mtime}, 1)")
    boolean enjoy(CommentEnjoy commentEnjoy);


    /**
     * 删除回复
     *
     * @param ids
     * @return
     */
    @Update("<script>" +
            "update " + TABLE + " set valid=0 where id in " +
            "<foreach item='item' collection='ids' open='(' separator=',' close=')'>#{item}</foreach></script>")
    boolean delete(@Param("ids") Set<Long> ids);


    /**
     * 删除评论,评论删除所有回复
     *
     * @param rootIds
     * @return
     */
    @Update("<script>" +
            "update " + TABLE + " set valid=0 where root_id in" +
            "<foreach item='item' collection='rootIds' open='(' separator=',' close=')'>#{item}</foreach></script>")
    boolean deleteByRootId(@Param("rootIds") Set<Long> rootIds);
}
