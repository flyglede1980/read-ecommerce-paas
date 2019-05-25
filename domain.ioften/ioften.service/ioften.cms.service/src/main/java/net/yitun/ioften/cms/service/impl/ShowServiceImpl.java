package net.yitun.ioften.cms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

import net.yitun.basic.Code;
import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.basic.utils.SetUtil;
import net.yitun.comon.CobjsApi;
import net.yitun.ioften.cms.dicts.ArticleStatus;
import net.yitun.ioften.cms.dicts.Type;
import net.yitun.ioften.cms.entity.UserTotal;
import net.yitun.ioften.cms.entity.aritcles.ArticleColumnClasses;
import net.yitun.ioften.cms.entity.aritcles.ArticleIndicators;
import net.yitun.ioften.cms.entity.aritcles.Articles;
import net.yitun.ioften.cms.entity.aritcles.ArticlesBO;
import net.yitun.ioften.cms.entity.aritcles.CoverPictures;
import net.yitun.ioften.cms.entity.aritcles.MediaArticles;
import net.yitun.ioften.cms.entity.aritcles.PicFiles;
import net.yitun.ioften.cms.entity.aritcles.TextArticles;
import net.yitun.ioften.cms.model.article.ArticleQuery;
import net.yitun.ioften.cms.model.article.detail.ArticleIndicatorsDetail;
import net.yitun.ioften.cms.model.article.detail.CoverPicturesDetail;
import net.yitun.ioften.cms.model.article.detail.MediaArticlesDetail;
import net.yitun.ioften.cms.model.article.detail.PicFilesDetail;
import net.yitun.ioften.cms.model.article.detail.TextArticlesDetail;
import net.yitun.ioften.cms.model.category.ColumnClassesDetail;
import net.yitun.ioften.cms.model.show.NewsDetail;
import net.yitun.ioften.cms.model.show.NewsList;
import net.yitun.ioften.cms.model.show.ShowQuery;
import net.yitun.ioften.cms.service.ArticleService;
import net.yitun.ioften.cms.service.CategoryService;
import net.yitun.ioften.cms.service.ShowService;
import net.yitun.ioften.cms.util.RegExpHtml;
import net.yitun.ioften.crm.UserApi;
import net.yitun.ioften.crm.model.user.UserDetail;

@Service("cms.ShowService")
@SuppressWarnings("unchecked")
public class ShowServiceImpl implements ShowService {

    @Autowired
    protected UserApi userApi;

    @Autowired
    protected CobjsApi cobjsApi;

    @Autowired
    protected ArticleService service;

    @Autowired
    protected CategoryService categoryService;

    @Override
    public Result<NewsDetail> detail(Long id) {
        Articles article; // ID对应的文章不存在或者其状态不为已发布时都显示不存在
        if (null == (article = this.service.getArticles(id)) //
                || ArticleStatus.ISSUE != article.getIssueStatus())
            return Result.UNEXIST;

        // 作者信息
        Result<UserDetail> author = this.userApi.nick(article.getUsersId());

        // 文章统计指标
        ArticleIndicators articleIndicators = this.service.getArtIndicators(id);
        if (null == articleIndicators)
            return new Result<>(Code.BIZ_ERROR, "获取统计信息失败");
        ArticleIndicatorsDetail indicators = new ArticleIndicatorsDetail(articleIndicators.getArticleId(), articleIndicators.getShareNum(),
                articleIndicators.getBrowseNum(), articleIndicators.getCommentNum(), articleIndicators.getPraiseNum(),
                articleIndicators.getCollectionNum(), articleIndicators.getReward(), articleIndicators.getRewardProfit());

        Type type = article.getType();
        NewsDetail newsDetail = new NewsDetail(article.getArticleId(), article.getUsersId(), article.getParentId(),
                article.getMainTitle(), article.getSummary(), article.getKeyWord(), article.getBeansNum(),
                article.getRechargeRatio(), article.getInvestStatus(), type, article.getIsHot(), article.getIsRecommend(),
                article.getIsTop(), article.getIsAuthorized(), indicators, article.getIsCommented(),
                article.getShareSwitch(), article.getReadSwitch(), article.getPraiseSwitch(), article.getReadLevel(),
                article.getIssueTime(), article.getIssueStatus(), null, null, null, null, author.getData());

        // 文章关联栏目
        List<ArticleColumnClasses> colClaByIds = this.service.findColClaById(id);
        if (null != colClaByIds && !colClaByIds.isEmpty())
            colClaByIds.stream().forEach(classes -> newsDetail.addClasses(new ColumnClassesDetail(classes.getClassId())));

        if (Type.IMAGE == type) {// 纯图文章
            // 返回纯图文章信息
            List<PicFiles> picFiles = this.service.getPicFiles(id);
            if (!picFiles.isEmpty())
                picFiles.stream().forEach(picFile -> newsDetail.addPic(new PicFilesDetail(picFile.getFileId(),
                        picFile.getArticleId(), picFile.getLinkUrl(), picFile.getDescription(), picFile.getSortId())));

        } else if (Type.TEXT == type) {// 图文文章
            TextArticles textArticles = this.service.getTextArticles(id);
            // 设置返回的文章类型和封面图片数量
            String details = textArticles.getDetails();
            List<String> imageList = RegExpHtml.pickObs(details);
            // 获取图片前缀并拼接
            if (null != imageList && !imageList.isEmpty())
                for (String str : imageList) {
                    String newStr = this.cobjsApi.view(str);
                    details = details.replace(str, newStr);
                }

            newsDetail.setText(new TextArticlesDetail(textArticles.getArticleId(), details, textArticles.getCoverNum()));

        } else if (Type.VIDEO == type || Type.AUDIO == type) {// 媒体文章
            // 设置媒体文件列表
            MediaArticles media = this.service.getMediaArticles(id);
            if (null != media)
                newsDetail.setMedias(new MediaArticlesDetail(media.getArticleId(), media.getLinkUrl(),
                        media.getFileSize(), media.getMinutes(), media.getDescription()));
        }

        return new Result<>(newsDetail);
    }

    @Override
    public Result<NewsDetail> getArticle(Long id) {
        Articles articles = this.service.getArticles(id);
        if (null == articles)
            return Result.UNEXIST;

        return new Result<>(new NewsDetail(articles.getArticleId(), articles.getUsersId(), articles.getMainTitle(),
                articles.getBeansNum(), articles.getRechargeRatio(), articles.getShareSwitch(), articles.getReadSwitch(),
                articles.getPraiseSwitch()));
    }

    @Override
    public Page<ArticlesBO> query(ShowQuery query) {
        ArticleQuery _query = new ArticleQuery(query.getType(), null,
                (null == query.getClassId()) ? null : SetUtil.asSet(query.getClassId()),
                null,
                (null == query.getUid()) ? null : SetUtil.asSet(query.getUid()),
                query.getKeyword(), null, SetUtil.asSet(ArticleStatus.ISSUE), null, null);
        _query.setPageSize(query.getPageSize());
        _query.setPageNo(query.getPageNo());
        _query.setSortBy(query.getSortBy());
        return this.service.query(_query);
    }

    @Override
    public List<ArticlesBO> base(Set<Long> ids) {
        return this.service.base(ids);
    }

    @Override
    public MediaArticles getMedia(Long articleId) {
        return this.service.getMediaArticles(articleId);
    }

    @Override
    public List<CoverPictures> getCover(Long articleId) {
        return this.service.getCoverPic(articleId);
    }

    @Override
    public ArticleIndicators getIndicators(Long articleId) {
        return this.service.getArtIndicators(articleId);
    }

    @Override
    public UserTotal countArt(Long uid) {
        return this.service.countByUid(uid, ArticleStatus.ISSUE);
    }

    @Override
    public PageResult<NewsList> query(ArticleQuery query) {
        Page<ArticlesBO> page = this.service.query(query);
        List<ArticlesBO> articlesBOS;
        if (null == page || null == (articlesBOS = page.getResult()))
            return PageResult.OK;

        List<NewsList> newsLists = new ArrayList<>();
        for (ArticlesBO articlesBO : articlesBOS) {
            Long uid = articlesBO.getUsersId();
            Long aid = articlesBO.getArticleId();

            UserDetail user = this.userApi.nick(uid).getData();

            List<CoverPicturesDetail> coverPicturesDetails = new ArrayList<>();
            List<CoverPictures> coverPictures = this.getCover(aid);
            if (null != coverPictures && !coverPictures.isEmpty()) {
                for (CoverPictures pictures : coverPictures) {
                    coverPicturesDetails.add(new CoverPicturesDetail(pictures.getPictureId(), pictures.getArticleId(),
                            pictures.getIcon(), pictures.getSortId()));
                }
            }

            Type type = articlesBO.getType();
            MediaArticles media;
            MediaArticlesDetail mediaArticlesDetail = null;
            if (Type.VIDEO == type || Type.AUDIO == type) {
                media = this.service.getMediaArticles(aid);
                if (null != media) {
                    mediaArticlesDetail = new MediaArticlesDetail(media.getArticleId(), media.getLinkUrl(),
                            media.getFileSize(), media.getMinutes(), media.getDescription());
                }
            }
            newsLists.add(new NewsList(aid, uid, articlesBO.getMainTitle(), null, type, articlesBO.getReadLevel(),
                    articlesBO.getIssueTime(), articlesBO.getIssueStatus(), coverPicturesDetails, mediaArticlesDetail,
                    articlesBO.getBeansNum(), articlesBO.getPraiseNum(), articlesBO.getCommentNum(), user, articlesBO.getVerifyTime(),
                    articlesBO.getIsTop()));
        }

        return new PageResult<>(page.getTotal(), page.getPages(), page.getPageNum(), page.getPageSize(), newsLists);

    }

    @Override
    public Result<NewsDetail> getArt(Long id) {
        Articles articles = this.service.getArt(id);
        if (null == articles)
            return Result.UNEXIST;

        return new Result<>(new NewsDetail(articles.getArticleId(), articles.getUsersId(), articles.getMainTitle(),
                articles.getBeansNum(), articles.getRechargeRatio(), articles.getShareSwitch(), articles.getReadSwitch(),
                articles.getPraiseSwitch()));

    }

    @Override
    public Articles findById(Long id) {
        return this.service.getArticles(id);
    }
}
