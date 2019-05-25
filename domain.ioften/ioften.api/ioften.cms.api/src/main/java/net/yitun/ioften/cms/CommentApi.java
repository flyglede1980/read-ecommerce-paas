package net.yitun.ioften.cms;

import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.ioften.cms.conf.Conf;
import net.yitun.ioften.cms.model.comment.CommentAnswerQuery;
import net.yitun.ioften.cms.model.comment.CommentCreate;
import net.yitun.ioften.cms.model.comment.CommentDetail;
import net.yitun.ioften.cms.model.comment.CommentHotQuery;
import net.yitun.ioften.cms.model.comment.CommentNewQuery;
import net.yitun.ioften.cms.model.comment.CommentReplyDetail;

/**
 * <pre>
 * <b>资讯 评论接口.</b>
 * <b>Description:</b>
 *
 * <b>Author:</b> ZH
 * <b>Date:</b> 2018-12-16 11:17:00.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月12日 上午11:17:06 ZH
 *         new file.
 * </pre>
 */
public interface CommentApi {

    /**
     * 热门评论分页查询
     * 权重值排序
     *
     * @param query 查询参数
     * @return PageResult<CommentDetail> 查询结果
     */
    @GetMapping(value = Conf.ROUTE //
            + "/comments", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageResult<CommentDetail> hotQuery(CommentHotQuery query);

    /**
     * 最新评论分页查询
     *
     * @param query
     * @return
     */
    @GetMapping(value = Conf.ROUTE //
            + "/commenting", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageResult<CommentDetail> newQuery(CommentNewQuery query);

    /**
     * 回复分页查询
     * 我的评论显示在最前
     *
     * @param query
     * @return
     */
    @GetMapping(value = Conf.ROUTE //
            + "/answer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageResult<CommentReplyDetail> answerQuery(CommentAnswerQuery query);


    /**
     * 发布评论
     *
     * @param model 新建参数
     * @return Result<?> 新建结果
     */
    @PostMapping(value = Conf.ROUTE //
            + "/comment", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> create(@RequestBody CommentCreate model);


    /**
     * 删除评论
     *
     * @param ids 评论ID
     * @return Result<?> 删除结果
     */
    @DeleteMapping(value = Conf.ROUTE //
            + "/comment", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> delete(@RequestParam("id") Set<Long> ids);

}
