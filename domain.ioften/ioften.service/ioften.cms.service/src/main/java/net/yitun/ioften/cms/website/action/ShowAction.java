package net.yitun.ioften.cms.website.action;

import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.Code;
import net.yitun.basic.Util;
import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.basic.utils.IdUtil;
import net.yitun.basic.utils.SetUtil;
import net.yitun.ioften.cms.ConfApi;
import net.yitun.ioften.cms.ShowApi;
import net.yitun.ioften.cms.dicts.ArticleStatus;
import net.yitun.ioften.cms.dicts.Type;
import net.yitun.ioften.cms.entity.Category;
import net.yitun.ioften.cms.entity.UserTotal;
import net.yitun.ioften.cms.entity.aritcles.*;
import net.yitun.ioften.cms.model.article.ArticleQuery;
import net.yitun.ioften.cms.model.article.detail.CoverPicturesDetail;
import net.yitun.ioften.cms.model.article.detail.MediaArticlesDetail;
import net.yitun.ioften.cms.model.conf.TotopItem;
import net.yitun.ioften.cms.model.show.NewsDetail;
import net.yitun.ioften.cms.model.show.NewsList;
import net.yitun.ioften.cms.model.show.ShowQuery;
import net.yitun.ioften.cms.model.show.UserTotalDetail;
import net.yitun.ioften.cms.service.CategoryService;
import net.yitun.ioften.cms.service.ShowService;
import net.yitun.ioften.crm.UserApi;
import net.yitun.ioften.crm.model.user.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Api(tags = "资讯 显示接口")
@RestController("cms.ShowApi")
@SuppressWarnings("unchecked")
public class ShowAction implements ShowApi {

    @Autowired
    protected UserApi userApi;

    @Autowired
    protected ShowService service;

    @Autowired
    protected CategoryService categoryService;

    @Autowired
    protected ConfApi confApi;

    @Override
    @ApiOperation(value = "资讯内容")
    @ApiImplicitParam(name = "id", value = "ID, 资讯ID", required = true)
    public Result<NewsDetail> detail(@PathVariable("id") Long id) {
        if (null == id || Util.MIN_ID > id)
            return new Result<>(Code.BAD_REQ, "ID无效");

        return this.service.detail(id);
    }

    @Override
    @ApiOperation(value = "推荐资讯")
    @ApiImplicitParam(name = "id", value = "ID, 资讯ID", required = true)
    public PageResult<NewsList> hots(@PathVariable("id") Long id) {
        NewsDetail newsDetail;
        Result<NewsDetail> article = this.service.getArticle(id);
        if (!article.ok() || null == (newsDetail = article.getData()))
            return new PageResult<>(Code.BIZ_ERROR, "未查询到资讯信息");
        // 获取查询size
        Type type = newsDetail.getType();
        int cmsShowSize = this.confApi.getCmsShowSize(type, true);
        // 获取类目ID
        List<Category> classes = this.categoryService.getClasses(id);
        if (null == classes || classes.isEmpty())
            return PageResult.OK;
        Set<Long> classIds = new HashSet<>();
        for (Category category : classes) {
            classIds.add(category.getClassId());
        }
        // 设置查询条件和size
        ArticleQuery query = new ArticleQuery(type, null, classIds, id, null, null, null,
                SetUtil.asSet(ArticleStatus.ISSUE), null, null);
        query.setPageSize(cmsShowSize);
        query.setSortBy("verifytime desc");
        return this.service.query(query);

    }

    @Override
    @ApiOperation(value = "分页查询")
    public PageResult<NewsList> query(@Validated @RequestBody ShowQuery query) {
        List<ArticlesBO> result;
        // 设置query的size
        List<NewsList> newsLists = new ArrayList<>();
        int cmsShowSize = this.confApi.getCmsShowSize(null, false);
        query.setPageSize(cmsShowSize);
        int pageNo = query.getPageNo();
        Map<Long, UserDetail> users = new HashMap<>();
        if (null == query.getClassId() || 0L == query.getClassId()) {
            query.setClassId(null);

            if (1 == pageNo) {
                Collection<TotopItem> totopItems //
                        = this.confApi.cmsConfInfo().getTotopNews();
                //如果置顶不为空
                if (null != totopItems && !totopItems.isEmpty()) {
                    for (TotopItem top : totopItems) {
                        Long id = top.getId();
                        Articles articles;
                        if (null == (articles = this.service.findById(id)))
                            continue;
                        // 获取user信息
                        Long uid = articles.getUsersId();
                        if (!users.containsKey(uid))
                            users.put(uid, this.userApi.nick(uid).getData());
                        // 获取文章封面图片
                        List<CoverPicturesDetail> coverPicturesDetails = new ArrayList<>();
                        List<CoverPictures> coverPictures = this.service.getCover(id);
                        if (null != coverPictures && !coverPictures.isEmpty()) {
                            for (CoverPictures pictures : coverPictures) {
                                coverPicturesDetails.add(new CoverPicturesDetail(pictures.getPictureId(), pictures.getArticleId(),
                                        pictures.getIcon(), pictures.getSortId()));
                            }
                        }
                        // 如果是媒体，查询出媒体信息
                        Type type = articles.getType();
                        MediaArticlesDetail mediaArticlesDetail = null;
                        if (Type.VIDEO == type || Type.AUDIO == type) {
                            MediaArticles media = this.service.getMedia(id);
                            mediaArticlesDetail = new MediaArticlesDetail(media.getArticleId(), media.getLinkUrl(),
                                    media.getFileSize(), media.getMinutes(), media.getDescription());
                        }
                        //获取统计信息
                        ArticleIndicators indicators = this.service.getIndicators(id);

                        newsLists.add(new NewsList(id, uid, articles.getMainTitle(), null, type, articles.getReadLevel(),
                                articles.getIssueTime(), articles.getIssueStatus(), coverPicturesDetails, mediaArticlesDetail,
                                articles.getBeansNum(), indicators.getPraiseNum(), indicators.getCommentNum(), users.get(uid),
                                articles.getVerifyTime(), articles.getIsTop()));
                    }
                }
            }
        }

        // 根据条件查询
        Page<ArticlesBO> page = this.service.query(query);
        if (null == page || null == (result = page.getResult()))
            return PageResult.OK;

        // 拼装数据
        for (ArticlesBO articlesBO : result) {
            Long uid = articlesBO.getUsersId();
            // 获取user信息
            if (!users.containsKey(uid))
                users.put(uid, this.userApi.nick(uid).getData());
            // 获取文章封面图片
            Long articleId = articlesBO.getArticleId();
            List<CoverPicturesDetail> coverPicturesDetails = new ArrayList<>();
            List<CoverPictures> coverPictures = this.service.getCover(articleId);
            if (null != coverPictures && !coverPictures.isEmpty()) {
                for (CoverPictures pictures : coverPictures) {
                    coverPicturesDetails.add(new CoverPicturesDetail(pictures.getPictureId(), pictures.getArticleId(),
                            pictures.getIcon(), pictures.getSortId()));
                }
            }
            // 根据如果是媒体，查询出媒体信息
            Type type = articlesBO.getType();
            MediaArticlesDetail mediaArticlesDetail = null;
            if (Type.VIDEO == type || Type.AUDIO == type) {
                MediaArticles media = this.service.getMedia(articleId);
                mediaArticlesDetail = new MediaArticlesDetail(media.getArticleId(), media.getLinkUrl(),
                        media.getFileSize(), media.getMinutes(), media.getDescription());
            }

            newsLists.add(new NewsList(articleId, uid, articlesBO.getMainTitle(), null, type, articlesBO.getReadLevel(),
                    articlesBO.getIssueTime(), articlesBO.getIssueStatus(), coverPicturesDetails, mediaArticlesDetail,
                    articlesBO.getBeansNum(), articlesBO.getPraiseNum(), articlesBO.getCommentNum(), users.get(uid),
                    articlesBO.getVerifyTime(), articlesBO.getIsTop()));
        }
        return new PageResult<>(page.getTotal(), page.getPages(), pageNo, cmsShowSize, newsLists);
    }

    @Override
    @ApiOperation(value = "作者资料")
    public Result<UserTotalDetail> author(@PathVariable("id") Long id) {
        if (id < IdUtil.MIN)
            return new Result<>(Code.BAD_REQ, "用户ID无效");

        // 统计用户记录(资讯总数、关注、被关注、总获赏)
//        ArticleIndicators articleIndicators = this.service.getIndicators(id);
//        if (null == articleIndicators)
//            return new Result<>(Code.BIZ_ERROR, "获取用户统计失败");
        // 文章总数、总获赏
        UserTotal userTotal = this.service.countArt(id);
        // 获取用户基本信息
        Result<UserDetail> detail = userApi.info(id); // id、昵称(nick)

        // 获取总粉丝、关注数
        UserTotalDetail userTotalDetail = new UserTotalDetail(detail.getData(), userTotal.getArticleTotal(), 0, 0,
                userTotal.getRewardTotal(), null);

        // 如果不是自己查看，查询是否已关注
//        Long self = SecurityUtil.getId();
//        if (null != id && id.equals(self)) {
//            // TODO
//        }

        return new Result<>(userTotalDetail);
    }

    @Override
    @ApiOperation(value = "扼要信息")
    @ApiImplicitParam(name = "id", value = "ID, 资讯ID", required = true)
    public Result<NewsDetail> major(@PathVariable("id") Long id) {
        if (null == id || Util.MIN_ID > id)
            return new Result<>(Code.BAD_REQ, "ID无效");

        return this.service.getArticle(id);
    }

    @Override
    @ApiOperation(value = "收藏、历史记录")
    @ApiImplicitParam(name = "ids", value = "资讯ID集合", required = true)
    public Result<Map<Long, NewsList>> basics(@RequestParam("ids") Set<Long> ids) {
        List<ArticlesBO> result = this.service.base(ids);
        if (null == result || 0 == result.size())
            return Result.OK;

        Map<Long, NewsList> newsLists = new HashMap<>();
        // 遍历result组装数据
        for (ArticlesBO articlesBO : result) {
            // 获取作者作者
            Long uid = articlesBO.getUsersId();
            // 获取资讯封面图片
            Long articleId = articlesBO.getArticleId();
            List<CoverPicturesDetail> coverPicturesDetails = null;
            List<CoverPictures> coverPictures = this.service.getCover(articleId);
            if (null != coverPictures && !coverPictures.isEmpty()) {
                coverPicturesDetails = new ArrayList<>();
                for (CoverPictures pictures : coverPictures) {
                    coverPicturesDetails.add(new CoverPicturesDetail(pictures.getPictureId(), pictures.getArticleId(),
                            pictures.getIcon(), pictures.getSortId()));
                }
            }
            // 如果资讯是音视频，获取音视频文件
            Type type = articlesBO.getType();
            MediaArticlesDetail mediaArticlesDetail = null;
            if (Type.VIDEO == type) {
                MediaArticles media = this.service.getMedia(articleId);
                mediaArticlesDetail = new MediaArticlesDetail(media.getArticleId(), media.getLinkUrl(),
                        media.getFileSize(), media.getMinutes(), media.getDescription());
            }
            newsLists.put(articleId,
                    new NewsList(articleId, uid, articlesBO.getMainTitle(), type, articlesBO.getIssueTime(),
                            articlesBO.getIssueStatus(), coverPicturesDetails, mediaArticlesDetail,
                            this.userApi.nick(uid).getData()));
        }
        return new Result<>(newsLists);
    }

    @Override
    public Result<NewsList> basic(@RequestParam("id") Long id) {
        if (null == id || Util.MIN_ID > id)
            return new Result<>(Code.BAD_REQ, "ID无效");
        // 获取资讯基本信息
        Result<NewsDetail> article = this.service.getArt(id);
        NewsDetail newsDetail;
        if (!article.ok() || null == (newsDetail = article.getData()))
            return Result.UNEXIST;
        // 获取封面图片
        List<CoverPictures> covers = this.service.getCover(id);
        List<CoverPicturesDetail> picturesDetail = null;
        if (null != covers && !covers.isEmpty()) {
            picturesDetail = new ArrayList<>();
            for (CoverPictures cover : covers) {
                picturesDetail
                        .add(new CoverPicturesDetail(cover.getPictureId(), null, cover.getIcon(), cover.getSortId()));
            }
        }
        NewsList newsList = new NewsList(newsDetail.getArticleId(), newsDetail.getMainTitle(), newsDetail.getSumMary(),
                picturesDetail);
        return new Result<>(newsList);
    }
}
