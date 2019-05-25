package net.yitun.ioften.cms.service;

import java.util.Set;

import com.github.pagehelper.Page;

import net.yitun.basic.model.Result;
import net.yitun.ioften.cms.entity.comment.Comment;
import net.yitun.ioften.cms.entity.comment.CommentReply;
import net.yitun.ioften.cms.model.comment.CommentAnswerQuery;
import net.yitun.ioften.cms.model.comment.CommentCreate;
import net.yitun.ioften.cms.model.comment.CommentHotQuery;

/**
 * <pre>
 * <b>资讯 评论服务.</b>
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
public interface CommentService {

    /**
     * 评论详细
     *
     * @param id 评论ID
     * @return Comment
     */
    Comment get(Long id);

    /**
     * 热门评论分页查询
     * 权重值排序
     *
     * @param query 查询参数
     * @return
     */
    Page<Comment> query(CommentHotQuery query);

    /**
     * 回复分页查询,我的评论显示在最前
     *
     * @param query
     * @return
     */
    Page<CommentReply> query(CommentAnswerQuery query);

    /**
     * 发布评论
     *
     * @param model 新建模型
     * @return Result<?> 新建结果
     */
    Result<?> create(CommentCreate model);


    /**
     * 删除评论或者回复
     *
     * @param commentIds 评论的ID集合
     * @param uid        用户ID
     * @return
     */
    Result<?> deleteComment(Set<Long> commentIds, Long uid);


    /**
     * 评论点赞
     *
     * @param id
     * @return
     */
    Result<?> EnjoyCreate(Long id);
}
