package net.yitun.ioften.cms.service;

import java.util.List;
import java.util.Set;

import com.github.pagehelper.Page;

import net.yitun.basic.model.Result;
import net.yitun.ioften.cms.dicts.ArticleStatus;
import net.yitun.ioften.cms.entity.UserTotal;
import net.yitun.ioften.cms.entity.aritcles.*;
import net.yitun.ioften.cms.model.article.ArticleQuery;
import net.yitun.ioften.cms.model.article.ArticleReviewQuery;
import net.yitun.ioften.cms.model.article.create.MediaArticlesCreate;
import net.yitun.ioften.cms.model.article.create.PicArticlesCreate;
import net.yitun.ioften.cms.model.article.create.TextArticlesCreate;
import net.yitun.ioften.cms.model.article.modify.ArticleIndicatorsModify;
import net.yitun.ioften.cms.model.article.modify.MediaArticlesModify;
import net.yitun.ioften.cms.model.article.modify.PicArticlesModify;
import net.yitun.ioften.cms.model.article.modify.TextArticlesModify;
import net.yitun.ioften.cms.model.article.setting.ArticleReview;
import net.yitun.ioften.cms.model.article.setting.ArticleSetting;
import net.yitun.ioften.cms.model.article.setting.ArticleStock;

/**
 * 文章的服务接口定义
 *
 * @author Flyglede
 * @see Articles
 * @since 1.0.0
 */
public interface ArticleService {

    /**
     * 分页查询(后台)
     *
     * @param query 查询参数
     * @return Page<Articles>查询结果
     */
    Page<ArticlesBO> query(ArticleQuery query);

    /**
     * 文章审核分页
     *
     * @param query 查询参数
     * @return Page<ArticlesBO>
     */
    Page<ArticlesBO> query(ArticleReviewQuery query);

    /**
     * 根据文章ID获取文章信息
     *
     * @param id--文章编号
     * @return 文章信息
     */
    Articles getArticles(Long id);

    /**
     * 根据文章ID获取纯图文章信息
     *
     * @param id--文章编号
     * @return 纯图文章信息
     */
    PicArticles getPicArticles(Long id);

    /**
     * 根据文章ID获取图文文章信息
     *
     * @param id--文章编号
     * @return 图文文章信息
     */
    TextArticles getTextArticles(Long id);

    /**
     * 根据文章ID获取音频视频文章信息
     *
     * @param id--文章编号
     * @return 音视频文章信息
     */
    MediaArticles getMediaArticles(Long id);

    /**
     * 发布图文文章
     *
     * @param model 新增模型
     * @return 新增结果
     */
    Result<?> createText(TextArticlesCreate model);

    /**
     * 发布纯图文章
     *
     * @param model 新增模型
     * @return 新增结果
     */
    Result<?> createPic(PicArticlesCreate model);

    /**
     * 发布媒体文章
     *
     * @param model 新增模型
     * @return 新增结果
     */
    Result<?> createMedia(MediaArticlesCreate model);

    /**
     * 修改图文文章
     *
     * @param model 修改模型
     * @return 修改结果
     */
    Result<?> modifyText(TextArticlesModify model);

    /**
     * 修改纯图文章
     *
     * @param model 修改模型
     * @return 修改结果
     */
    Result<?> modifyPic(PicArticlesModify model);

    /**
     * 修改媒体文章
     *
     * @param model 修改模型
     * @return 修改结果
     */
    Result<?> modifyMedia(MediaArticlesModify model);

    /**
     * 删除文章
     *
     * @param ids 文章id集合
     * @return 删除结果
     */
    Result<?> delete(Set<Long> ids);

    /**
     * 查询文章分类
     * @param id 文章ID
     * @return List<ArticleColumnClasses>
     */
    List<ArticleColumnClasses> findColClaById(Long id);
    /**
     * 查询封面图片
     *
     * @param id 文章ID
     * @return 查询结果
     */
    List<CoverPictures> getCoverPic(Long id);

    /**
     * 查询纯图文章
     *
     * @param id 文章ID
     * @return List<PicFiles> 查询结果
     */
    List<PicFiles> getPicFiles(Long id);

    /**
     * 查询文章统计
     *
     * @param id 文章ID
     * @return ArticleIndicators 查询结果
     */
    ArticleIndicators getArtIndicators(Long id);

    /**
     * 资讯审核
     *
     * @param model 修改模型
     * @return Result<?> 审核结果
     */
    Result<?> review(ArticleReview model);

    /**
     * 投放服豆
     *
     * @param model 修改参数
     * @return 修改结果
     */
    Result<?> stock(ArticleStock model);

    /**
     * 文章设置
     *
     * @param model 修改模型
     * @return 修改结果
     */
    Result<?> setting(ArticleSetting model);

    /**
     * 用户文章统计
     *
     * @param uid 用户ID
     * @return UserTotal查询结果
     */
    UserTotal countByUid(Long uid, ArticleStatus articleStatus);

    /**
     * 收藏、阅读历史
     *
     * @param ids 文章ID集合
     * @return List<ArticlesBO>
     */
    List<ArticlesBO> base(Set<Long> ids);

    /**
     * 取消发表/下线
     * @param id 文章ID
     * @return  Result<?>
     */
    Result<?> cancel(Long id);
    /**
     * 文章KPI操作
     *
     * @param model 修改参数
     * @return Result<?> 修改结果
     */
    Result<?> indicators(ArticleIndicatorsModify model);
    /**
     * 根据文章ID扣除服豆库存
     * @param articleId--文章ID
     * @param beansNum--扣除服豆数
     * @return 操作结果
     */
    Result<?> reduceStock(Long articleId, Long beansNum);

    /**
     * 获取文章(所有状态)
     * @param id
     * @return
     */
    Articles getArt(Long id);

    UserArticleCount countArticle(Long uid);
}