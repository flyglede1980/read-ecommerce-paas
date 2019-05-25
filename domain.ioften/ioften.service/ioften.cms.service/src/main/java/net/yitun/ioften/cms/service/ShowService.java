package net.yitun.ioften.cms.service;

import java.util.List;
import java.util.Set;

import com.github.pagehelper.Page;

import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.ioften.cms.entity.UserTotal;
import net.yitun.ioften.cms.entity.aritcles.*;
import net.yitun.ioften.cms.model.article.ArticleQuery;
import net.yitun.ioften.cms.model.show.NewsDetail;
import net.yitun.ioften.cms.model.show.NewsList;
import net.yitun.ioften.cms.model.show.ShowQuery;

public interface ShowService {

    /**
     * 资讯内容
     *
     * @param id 资讯ID
     * @return Article 资讯内容
     */
    Result<NewsDetail> detail(Long id);

    /**
     * 文章基本信息
     *
     * @param id 文章ID
     * @return Result<NewsDetail>
     */
    Result<NewsDetail> getArticle(Long id);

    /**
     * 分页查询
     *
     * @param query 查询参数
     * @return Page<Article>查询结果
     */
    Page<ArticlesBO> query(ShowQuery query);

    /**
     * 收藏、历史记录
     *
     * @param ids 文章ID集合
     * @return Result<List<NewsList>>
     */
    List<ArticlesBO> base(Set<Long> ids);

    /**
     * 获取封面图片
     *
     * @param articleId 文章ID
     * @return List<CoverPictures>
     */
    List<CoverPictures> getCover(Long articleId);

    /**
     * 获取音视频
     *
     * @param articleId 文章ID
     * @return List<MediaFiles>
     */
    MediaArticles getMedia(Long articleId);


    /**
     * 获取统计指标
     * @param articleId
     * @return
     */
    ArticleIndicators getIndicators(Long articleId);

    /**
     * 统计用户文章数
     * @param uid
     * @return
     */
    UserTotal countArt(Long uid);

    /**
     * 文章推荐
     * @param query 搜索参数
     * @return Page<ArticlesBO>
     */
    PageResult<NewsList> query(ArticleQuery query);

    /**
     * 获取所有状态的文章
     * @param id
     * @return
     */
    Result<NewsDetail> getArt(Long id);

    /**
     * 获取文章信息
     * @param id
     * @return
     */
    Articles findById(Long id);
}
