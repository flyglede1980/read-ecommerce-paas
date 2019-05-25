package net.yitun.ioften.cms.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;

import net.yitun.basic.Code;
import net.yitun.basic.dict.Status;
import net.yitun.basic.dict.YesNo;
import net.yitun.basic.exp.BizException;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.IdUtil;
import net.yitun.ioften.cms.ShowApi;
import net.yitun.ioften.cms.entity.comment.Comment;
import net.yitun.ioften.cms.entity.comment.CommentReply;
import net.yitun.ioften.cms.entity.vo.CommentHotQueryVo;
import net.yitun.ioften.cms.model.comment.CommentAnswerQuery;
import net.yitun.ioften.cms.model.comment.CommentCreate;
import net.yitun.ioften.cms.model.comment.CommentHotQuery;
import net.yitun.ioften.cms.model.comment.CommentUser;
import net.yitun.ioften.cms.model.show.NewsDetail;
import net.yitun.ioften.cms.repository.CommentRepository;
import net.yitun.ioften.cms.service.CommentService;
import net.yitun.ioften.crm.UserApi;
import net.yitun.ioften.crm.model.user.UserDetail;

@Service("cms.CommentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    protected ShowApi showApi;

    @Autowired
    protected CommentRepository repository;

    @Autowired
    protected UserApi userApi;

    @Override
    //@Cacheable(key = "#id" )
    public Comment get(Long id) {
        return this.repository.get(id);
    }

    /**
     * 热门评论分页查询
     * 最新评论分页查询
     * 权重值排序
     *
     * @param query 查询参数
     * @return
     */
    @Override
    public Page<Comment> query(CommentHotQuery query) {
        CommentHotQueryVo commentHotQueryVo = new CommentHotQueryVo(query.getArticleId());
        Page<Comment> comments = repository.query(commentHotQueryVo);
        for (Comment comment : comments) {
            Result<UserDetail> result = null;
            if (null != (result = this.userApi.info(comment.getUid())) && result.ok())
                comment.setUser(result.getData());
        }

        return comments;
    }

    /**
     * 回复分页查询,我的评论显示在最前
     *
     * @param query
     * @return
     */
    @Override
    public Page<CommentReply> query(CommentAnswerQuery query) {
        Long uid = query.getUid();
        Long rootId = query.getRootId();
        Page<CommentReply> page = repository.answer(rootId, uid);
        for (CommentReply commentReply : page) {
            UserDetail userDetail = null;
            //回复用户个人信息
            if (null != (userDetail = userApi.info(commentReply.getUid()).getData())) {
                commentReply.setNick(userDetail.getNick());
                commentReply.setType(userDetail.getType());
                commentReply.setUserLevel(userDetail.getLevel());
            }
            CommentUser commentUser = null;
            if (commentReply.getLevel() > 1) {
                //查看评论用户详情包括被删除的评论回复
                Comment comment = repository.get(commentReply.getParentId());
                UserDetail userDetail1 = null;
                if (null != (userDetail1 = userApi.info(comment.getUid()).getData())) {
                    commentUser = new CommentUser(comment.getId(), comment.getUid(), comment.getContent(),
                            userDetail1.getNick(), userDetail1.getType(), userDetail1.getLevel());
                }
            }
            commentReply.setCommentUser(commentUser);
        }
        return page;
    }


    /**
     * 发布评论
     *
     * @param model
     * @return
     */
    @Override
    @Transactional
    //@CacheEvict(key = "#model.parentId")
    public Result<?> create(CommentCreate model) {
        //判断资讯ID是否有效
        Long articleId = model.getArticleId();
        Result<NewsDetail> article = null;
        if (null == (article = this.showApi.major(articleId)) //
                || null == article.getData())
            return new Result<>(Code.BAD_REQ, "资讯ID无效");
        if (YesNo.NO == article.getData().getIsCommented())
            return new Result<>(Code.BIZ_ERROR, "资讯不允许评论");

        Integer level = 0;
        Long parentId = 0L;
        Comment parent = null;
        Long id = IdUtil.id(), rootId = id;
        if (null != model.getParentId()) {
            parentId = model.getParentId();
            //判断父级评论ID的有效性
            if (null == (parent = repository.lock(parentId)))
                return new Result<>(Code.BAD_REQ, "父级ID无效");

            rootId = parent.getRootId();  //一级评论ID
            level = parent.getLevel() + 1; // 回复的层级
        }

        Long uid = SecurityUtil.getId();
        Date nowTime = new Date(System.currentTimeMillis());

        Comment comment = new Comment(id, uid, level, articleId, rootId, parentId, model.getContent(), 0, 0,
                0, Status.ENABLE, nowTime, nowTime, null);
        //添加评论
        if (!this.repository.create(comment))
            return new Result<>(Code.BIZ_ERROR, "发布评论失败");

        if (null != parent) {
            //父级评论数+1 设置权重值
            parent.setMtime(nowTime);
            parent.setWeight(parent.getWeight() + 10);
            parent.setComments(parent.getComments() + 1);
            if (!repository.modify(parent))
                throw new BizException(Code.BIZ_ERROR, "发布评论失败");
        }

        return Result.OK;
    }

    /**
     * 删除评论或者回复
     *
     * @param commentIds 评论或者回复的集合
     * @return
     */
    @Override
    @Transactional
    public Result<?> deleteComment(Set<Long> commentIds, Long uid) {
        //批量查询评论信息
        List<Comment> page = repository.queryAllById(commentIds, uid);
        //如果删除的评论为主评论，保存rootId
        Set<Long> rootIds = new HashSet<Long>();
        if (!page.isEmpty()) {
            for (Comment comment : page) {
                if (comment.getLevel() == 0) {
                    rootIds.add(comment.getRootId());
                }
            }
        }
        commentIds.removeAll(rootIds);

        if (!commentIds.isEmpty())//其他评论删除
            repository.delete(commentIds);

        if (!rootIds.isEmpty())//主评论删除
            repository.deleteByRootId(rootIds);

        return new Result<>(Code.OK);
    }

    /**
     * 评论点赞
     *
     * @param id
     * @return
     */
    @Override
    //@CacheEvict(key = "#id")
    public Result<?> EnjoyCreate(Long id) {
//        Comment comment;
//        if (null == (comment = this.repository.find(id)))
//            return new Result<>(Code.BAD_REQ, "评论ID无效");
//        Date notTime = new Date(System.currentTimeMillis());
//        Long _id = IdUtil.id();
//        Long uid = SecurityUtil.getId();
//        CommentEnjoy commentEnjoy = new CommentEnjoy(_id, uid, id, notTime, notTime);
//
//        comment.setEnjoys(comment.getEnjoys() + 1);
//        comment.setMtime(notTime);
//
//        if (!this.repository.enjoy(commentEnjoy))
//            return new Result<>(Code.BIZ_ERROR, "评论点赞失败");
//
//        if (!this.repository.modifyEnjoys(comment))
//            throw new BizException(Code.BIZ_ERROR, "评论点赞失败");

        return Result.OK;
    }
}
