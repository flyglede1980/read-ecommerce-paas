package net.yitun.ioften.cms.website.action;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.Code;
import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.ioften.cms.CommentApi;
import net.yitun.ioften.cms.entity.comment.Comment;
import net.yitun.ioften.cms.entity.comment.CommentReply;
import net.yitun.ioften.cms.model.comment.CommentAnswerQuery;
import net.yitun.ioften.cms.model.comment.CommentCreate;
import net.yitun.ioften.cms.model.comment.CommentDetail;
import net.yitun.ioften.cms.model.comment.CommentHotQuery;
import net.yitun.ioften.cms.model.comment.CommentNewQuery;
import net.yitun.ioften.cms.model.comment.CommentReplyDetail;
import net.yitun.ioften.cms.service.CommentService;

@Api(tags = "资讯 评论接口")
@SuppressWarnings("serial")
@RestController("cms.CommentApi")
public class CommentAction implements CommentApi {

    @Autowired
    protected CommentService service;

    @Override
    @ApiOperation(value = "热门评论分页查询")
    public PageResult<CommentDetail> hotQuery(@Validated CommentHotQuery query) {
        Page<Comment> page = service.query(query);
        return new PageResult<>(page.getTotal(), page.getPages(),
                page.stream().map(comment -> new CommentDetail(comment.getId(), comment.getUid(), comment.getLevel(),
                        comment.getArticleId(), comment.getRootId(), comment.getParentId(), comment.getContent(),
                        comment.getEnjoys(), comment.getComments(), comment.getCtime(), comment.getMtime()
                        , comment.getUser())).collect(Collectors.toList()));
    }

    @Override
    @ApiOperation(value = "最新评论分页查询")
    public PageResult<CommentDetail> newQuery(@Validated CommentNewQuery query) {
        CommentHotQuery _query = new CommentHotQuery() {{
            setPageNo(query.getPageNo());
            setPageSize(query.getPageSize());
            setCount(query.getCount());
            setSortBy(query.getSortBy());
            setArticleId(query.getArticleId());
        }};
        Page<Comment> page = service.query(_query);
        return new PageResult<>(page.getTotal(), page.getPages(),
                page.stream().map(comment -> new CommentDetail(comment.getId(), comment.getUid(), comment.getLevel(),
                        comment.getArticleId(), comment.getRootId(), comment.getParentId(), comment.getContent(),
                        comment.getEnjoys(), comment.getComments(), comment.getCtime(), comment.getMtime()
                        , comment.getUser())).collect(Collectors.toList()));
    }

    @Override
    @ApiOperation(value = "回复分页查询,我的评论显示在最前")
    public PageResult<CommentReplyDetail> answerQuery(@Validated CommentAnswerQuery query) {
        Long uid = SecurityUtil.getId();
        query.setUid(uid);

        Page<CommentReply> page = service.query(query);

        return new PageResult<>(page.getTotal(), page.getPages(),
                page.stream().map(comment -> new CommentReplyDetail(comment.getId(), comment.getUid(), comment.getLevel(),
                        comment.getRootId(), comment.getParentId(), comment.getContent(), comment.getEnjoys(),
                        comment.getComments(), comment.getCtime(), comment.getNick(), comment.getType(), comment.getUserLevel()
                        , comment.getCommentUser())).collect(Collectors.toList()));

    }


    @Override
    @ApiOperation(value = "发布评论",notes = "不带parentId为一级评论")
    @PreAuthorize("hasRole('USER')")
    public Result<?> create(@Validated @RequestBody CommentCreate model) {
        return this.service.create(model);
    }


    @Override
    @ApiOperation(value = "删除评论或者回复")
    //@ApiImplicitParam(name = "commentIds",value = "评论或者回复的ID集合",required = true,type = "Long")
    @PreAuthorize("hasRole('USER')")
    public Result<?> delete(@RequestParam("commentIds") Set<Long> commentIds) {
        Long uid = SecurityUtil.getId();//用户ID
        if (commentIds.isEmpty())
            return new Result<>(Code.BAD_REQ, "请上传ID");
        return service.deleteComment(commentIds, uid);
    }


}
