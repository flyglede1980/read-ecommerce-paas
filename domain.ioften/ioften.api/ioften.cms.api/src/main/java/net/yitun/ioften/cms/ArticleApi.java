package net.yitun.ioften.cms;

import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.ioften.cms.conf.Conf;
import net.yitun.ioften.cms.model.article.ArticleQuery;
import net.yitun.ioften.cms.model.article.ArticleReviewQuery;
import net.yitun.ioften.cms.model.article.create.MediaArticlesCreate;
import net.yitun.ioften.cms.model.article.create.PicArticlesCreate;
import net.yitun.ioften.cms.model.article.create.TextArticlesCreate;
import net.yitun.ioften.cms.model.article.detail.ArticleDetail;
import net.yitun.ioften.cms.model.article.detail.UserArticleCountDetail;
import net.yitun.ioften.cms.model.article.modify.ArticleIndicatorsModify;
import net.yitun.ioften.cms.model.article.modify.MediaArticlesModify;
import net.yitun.ioften.cms.model.article.modify.PicArticlesModify;
import net.yitun.ioften.cms.model.article.modify.TextArticlesModify;
import net.yitun.ioften.cms.model.article.setting.ArticleReview;
import net.yitun.ioften.cms.model.article.setting.ArticleSetting;
import net.yitun.ioften.cms.model.article.setting.ArticleStock;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * <pre>
 * <b>资讯 文章管理.</b>
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
public interface ArticleApi {


    /**
     * 文章详情
     *
     * @param id ID
     * @return Result<ArticleDetail> 详情信息
     */
    @GetMapping(value = Conf.ROUTE //
            + "/article/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<ArticleDetail> detail(@PathVariable("id") Long id);

    /**
     * 分页查询
     *
     * @param query 筛选参数
     * @return PageResult<ArticleDetail> 分页列表
     */
    @GetMapping(value = Conf.ROUTE //
            + "/articles", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageResult<ArticleDetail> query(ArticleQuery query);

    /**
     * 分页查询(审核)
     *
     * @param query 筛选参数
     * @return PageResult<ArticleDetail> 分页列表
     */
    @GetMapping(value = Conf.ROUTE //
            + "/articles/review", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageResult<ArticleDetail> query(ArticleReviewQuery query);


    /**
     * 新增图文文章
     *
     * @param model 新增模型
     * @return Result<?> 新增结果
     */
    @PostMapping(value = Conf.ROUTE //
            + "/article/text", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> create(@RequestBody TextArticlesCreate model);

    /**
     * 新增纯图文章
     *
     * @param model 新增模型
     * @return Result<?> 新增结果
     */
    @PostMapping(value = Conf.ROUTE //
            + "/article/pic", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> create(@RequestBody PicArticlesCreate model);

    /**
     * 新增媒体文章
     *
     * @param model 新增模型
     * @return Result<?> 新增结果
     */
    @PostMapping(value = Conf.ROUTE //
            + "/article/media", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> create(@RequestBody MediaArticlesCreate model);

    /**
     * 修改图文文章
     *
     * @param model 修改模型
     * @return Result<?> 修改结果
     */
    @PutMapping(value = Conf.ROUTE //
            + "/article/text", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> modify(@RequestBody TextArticlesModify model);

    /**
     * 修改纯图文章
     *
     * @param model 新增模型
     * @return Result<?> 新增结果
     */
    @PutMapping(value = Conf.ROUTE //
            + "/article/pic", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> modify(@RequestBody PicArticlesModify model);

    /**
     * 修改媒体文章
     *
     * @param model 修改模型
     * @return Result<?> 修改结果
     */
    @PutMapping(value = Conf.ROUTE //
            + "/article/media", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> modify(@RequestBody MediaArticlesModify model);

    /**
     * 审核文章
     *
     * @param model 审核模型
     * @return Result<?> 审核结果
     */
    @PutMapping(value = Conf.ROUTE //
            + "/article/review", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> review(@RequestBody ArticleReview model);

    /**
     * 删除文章
     *
     * @param ids ID清单
     * @return Result<?> 删除结果
     */
    @DeleteMapping(value = Conf.ROUTE //
            + "/article", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> delete(@RequestParam("ids") Set<Long> ids);

    /**
     * 投放服豆
     *
     * @param model 修改参数
     * @return Result<?> 修改结果
     */
    @PutMapping(value = Conf.ROUTE //
            + "/article/stock", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> stock(@RequestBody ArticleStock model);

    /**
     * 取消发表/下线
     *
     * @param id 文章ID
     * @return Result<?> 修改结果
     */
    @PutMapping(value = Conf.ROUTE //
            + "/article/cancel/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> cancel(@PathVariable("id") Long id);

    /**
     * 文章设置
     *
     * @param model 修改参数
     * @return Result<?> 修改结果
     */
    @PutMapping(value = Conf.ROUTE //
            + "/article/setting", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> setting(@RequestBody ArticleSetting model);

    /**
     * 后台文章统计
     *
     * @return Result<UserArticleCountDetail> 查询结果
     */
    @GetMapping(value = Conf.ROUTE //
            + "/article/setting", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<UserArticleCountDetail> total();

    /**
     * 文章KPI累加
     *
     * @param model 修改参数
     * @return Result<?> 修改结果
     */
    Result<?> indicators(@RequestBody ArticleIndicatorsModify model);

    /**
     * 根据文章ID扣除服豆库存
     *
     * @param articleId--文章ID
     * @param beansNum--扣除服豆数
     * @return Result<?> 操作结果
     */
    Result<?> reduceStock(@PathVariable("articleId") Long articleId, @PathVariable("beansNum") Long beansNum);

}