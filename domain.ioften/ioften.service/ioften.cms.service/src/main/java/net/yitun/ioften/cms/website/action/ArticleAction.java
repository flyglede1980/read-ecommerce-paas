package net.yitun.ioften.cms.website.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.yitun.ioften.cms.entity.aritcles.*;
import net.yitun.ioften.cms.model.article.detail.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.Code;
import net.yitun.basic.Util;
import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.SetUtil;
import net.yitun.comon.CobjsApi;
import net.yitun.ioften.Roles;
import net.yitun.ioften.cms.ArticleApi;
import net.yitun.ioften.cms.conf.Constant;
import net.yitun.ioften.cms.dicts.ArticleStatus;
import net.yitun.ioften.cms.dicts.Type;
import net.yitun.ioften.cms.entity.Category;
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
import net.yitun.ioften.cms.model.category.ColumnClassesDetail;
import net.yitun.ioften.cms.service.ArticleService;
import net.yitun.ioften.cms.service.CategoryService;
import net.yitun.ioften.cms.util.RegExpHtml;
import net.yitun.ioften.crm.UserApi;
import net.yitun.ioften.crm.model.user.UserDetail;

@Api(tags = "资讯 文章管理")
@RestController("cms.ArticleAction")
@SuppressWarnings("unchecked")
public class ArticleAction implements ArticleApi {
    @Autowired
    protected UserApi userApi;
    @Autowired
    protected CobjsApi cobjsApi;
    @Autowired
    protected ArticleService service;
    @Autowired
    protected CategoryService categoryService;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','USER_EW')")
    @ApiOperation(value = "分页查询")
    public PageResult<ArticleDetail> query(@Validated ArticleQuery query) {
        Long uid = SecurityUtil.getId();

        // 判断是否为长鉴号用户, 是的话只搜索自己的资讯
        if (SecurityUtil.hasAnyRole("USER_EW") && !Constant.OFFICIAL_ACCOUNT.contains(uid))
            query.setUid(SetUtil.asSet(uid));

        Page<ArticlesBO> page = this.service.query(query);
        List<ArticlesBO> articlesBOS;
        if (null == page || (articlesBOS = page.getResult()).isEmpty())
            return PageResult.OK;

        List<ArticleDetail> articleDetails = new ArrayList<>();
        ArticleDetail articleDetail;
        //循环获取文章对应的类目ID， 并获取类目信息
        Result<UserDetail> userDetail = null;
        for (ArticlesBO articlesBO : articlesBOS) {
            Long articleId = articlesBO.getArticleId();
            if (SecurityUtil.hasAnyRole("ADMIN")) {
                userDetail = this.userApi.info(articlesBO.getUsersId());
            }
            //拼装返回数据
            articleDetail = new ArticleDetail(articleId, articlesBO.getParentId(), null == userDetail ? null : userDetail.getData(),
                    articlesBO.getMainTitle(), articlesBO.getBeansNum(), articlesBO.getShareSwitch(), articlesBO.getReadSwitch(),
                    articlesBO.getPraiseSwitch(), articlesBO.getReadLevel(), articlesBO.getCtime(), articlesBO.getMtime(),
                    articlesBO.getInvestStatus(), articlesBO.getIssueTime(), articlesBO.getIssueStatus(),
                    articlesBO.getVerifyTime(), articlesBO.getType());
            //获取文章对应类目
            List<Category> categories = this.categoryService.getClasses(articleId);
            List<ColumnClassesDetail> columnClassesDetails = new ArrayList<>();
            if (null != categories && !categories.isEmpty()) {
                //获取类目信息
                for (Category cate : categories) {
                    columnClassesDetails.add(new ColumnClassesDetail(cate.getClassId(), cate.getParentId(), cate.getCode(),
                            cate.getName(), cate.getIcon(), cate.getDescription(), cate.getSortId()));
                }
            }
            articleDetail.setClasses(columnClassesDetails);
            articleDetails.add(articleDetail);
        }

        return new PageResult<>(page.getTotal(), page.getPages(), articleDetails);
    }

    /**
     * 文章审核分页
     *
     * @param query 筛选参数
     * @return
     */
    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "文章审核分页查询")
    public PageResult<ArticleDetail> query(ArticleReviewQuery query) {
        String phone = query.getPhone();
        // 获取登录电话对应的联系人(认证审核资讯)
        if (null != phone) {
            UserDetail data;
            Result<UserDetail> byPhone = userApi.getByPhone(phone);
            if (!byPhone.ok() || null == (data = byPhone.getData()))
                return new PageResult<>(Code.NOT_FOUND, "未查询到登录手机号对应的用户");
            query.setUid(SetUtil.asSet(data.getId()));
        }
        //前端指定查询状态
        Page<ArticlesBO> page = this.service.query(query);
        List<ArticlesBO> articlesBOS;
        if (null == page || null == (articlesBOS = page.getResult()))
            return PageResult.OK;

        List<ArticleDetail> articleDetails = new ArrayList<>();
        for (ArticlesBO articlesBO : articlesBOS) {
            Long usersId = articlesBO.getUsersId();
            Result<UserDetail> detail = this.userApi.detail(usersId);

            articleDetails.add(new ArticleDetail(articlesBO.getArticleId(), articlesBO.getParentId(), articlesBO.getUsersId(),
                    detail.getData(), articlesBO.getMainTitle(), articlesBO.getIssueTime(), articlesBO.getIssueStatus(), articlesBO.getType(),
                    articlesBO.getApplyTime()));
        }
        return new PageResult<>(page.getTotal(), page.getPages(), articleDetails);
    }


    /**
     * 根据文章ID获取文章详情
     *
     * @param id--文章ID
     * @return 文章详情页面
     */
    @Override
    @ApiOperation(value = "文章详情")
    @PreAuthorize("hasAnyRole('USER_EW', 'ADMIN')")
    @ApiImplicitParam(name = "id", value = "ID, 资讯的ID", required = true)
    public Result<ArticleDetail> detail(@PathVariable("id") Long id) {
        if (null == id || Util.MIN_ID > id)
            return new Result<>(Code.BAD_REQ, "ID无效");

        Long uid = SecurityUtil.getId();
        //文章信息不存在
        Articles articles;
        if (null == (articles = this.service.getArticles(id)))
            return Result.UNEXIST;
        if (!Constant.OFFICIAL_ACCOUNT.contains(uid)) {
            // 如果非当前长鉴用户的资讯, 返回不存在
            if ((SecurityUtil.hasAnyRole(Roles.USER_EN.name()) && !articles.getUsersId().equals(uid)))
                return Result.UNEXIST;
        }

        UserDetail user = null;
        Result<UserDetail> userDetail;
        if (SecurityUtil.hasAnyRole("ADMIN")) {
            userDetail = this.userApi.detail(articles.getUsersId());
            if (!userDetail.ok() || null == (user = userDetail.getData()))
                return new Result<>(Code.BIZ_ERROR, "获取作者信息失败");
        }
        Type type = articles.getType();
        //构建返回数据
        ArticleDetail articleDetail = new ArticleDetail(articles.getArticleId(), articles.getParentId(), articles.getUsersId(),
                user, articles.getMainTitle(), articles.getKeyWord(), articles.getSortId(), articles.getBeansNum(),
                articles.getIsCommented(), articles.getShareSwitch(), articles.getReadSwitch(), articles.getPraiseSwitch(),
                articles.getReadLevel(), articles.getCtime(), articles.getMtime(), articles.getInvestStatus(),
                articles.getIssueTime(), articles.getIssueStatus(), articles.getVerifyId(), articles.getVerifyTime(), type,
                articles.getApplyTime(), articles.getReason());

        // 文章关联栏目
        List<Category> categories = this.categoryService.getClasses(id);
        if (null != categories && !categories.isEmpty()) {
            List<ColumnClassesDetail> classesDetails = new ArrayList<>();
            for (Category category : categories) {
                Long parentId = category.getParentId();
                ColumnClassesDetail classesDetail = new ColumnClassesDetail(category.getClassId(), parentId, category.getCode(),
                        category.getName(), category.getIcon(), category.getDescription(), category.getSortId());
                Integer level = category.getLevel();
                if (0 != parentId || 1 != level)
                    this.getClaParent(parentId, classesDetail);
                classesDetails.add(classesDetail);
            }
            articleDetail.setClasses(classesDetails);
        }
        if (Type.IMAGE == type) {// 纯图文章
            //获取封面图片
            getCovers(id, articleDetail);
            // 设置封面图片数量
            PicArticles picArticles = this.service.getPicArticles(id);
            articleDetail.setPicCoverNum(new PicArticlesDetail(id, picArticles.getCoverNum()));
            // 返回纯图文章信息
            List<PicFiles> picFiles = this.service.getPicFiles(id);
            if (!picFiles.isEmpty())
                picFiles.stream().forEach(picFile -> articleDetail.addPic(new PicFilesDetail(picFile.getFileId(),
                        picFile.getArticleId(), picFile.getLinkUrl(), picFile.getDescription(), picFile.getSortId())));

        } else if (Type.TEXT == type) {// 图文文章
            TextArticles textArticles = this.service.getTextArticles(id);
            //获取封面图片
            getCovers(id, articleDetail);
            String details = textArticles.getDetails();
            List<String> imageList = RegExpHtml.pickObs(details);
            // 获取图片前缀并拼接
            if (null != imageList && !imageList.isEmpty()) {
                Set<String> imageSet = new HashSet<>(imageList);
                for (String str : imageSet) {
                    String newStr = this.cobjsApi.view(str);
                    details = details.replace(str, newStr);
                }
            }
            articleDetail.setText(new TextArticlesDetail(textArticles.getArticleId(), details, textArticles.getCoverNum()));
        } else if (Type.VIDEO == type || Type.AUDIO == type) {// 媒体文章
            // 返回封面
            getCovers(id, articleDetail);
            // 设置媒体文件列表
            MediaArticles media = this.service.getMediaArticles(id);
            if (null != media)
                articleDetail.setMedias(new MediaArticlesDetail(media.getArticleId(), media.getLinkUrl(),
                        media.getFileSize(), media.getMinutes(), media.getDescription()));
        }

        return new Result<>(articleDetail);
    }

    private void getClaParent(Long classId, ColumnClassesDetail classesDetail) {
        Category detail = this.categoryService.detail(classId);
        if (null != detail) {
            Long parentId = detail.getParentId();
            Integer level = detail.getLevel();
            classesDetail.setParent(new ColumnClassesDetail(detail.getClassId(), parentId, detail.getCode(),
                    detail.getName(), detail.getIcon(), detail.getDescription(), detail.getSortId()));
            if (0 != parentId || 1 != level)
                this.getClaParent(classId, classesDetail);
        }
    }


    private void getCovers(Long id, ArticleDetail articleDetail) {
        List<CoverPicturesDetail> coverPicturesDetail;
        List<CoverPictures> picturesList = this.service.getCoverPic(id);
        if (!picturesList.isEmpty()) {
            coverPicturesDetail = new ArrayList<>();
            for (CoverPictures pictures : picturesList) {
                coverPicturesDetail.add(new CoverPicturesDetail(pictures.getPictureId(), pictures.getArticleId(), pictures.getIcon(), pictures.getSortId()));
            }
            articleDetail.setCovers(coverPicturesDetail);
        }
    }

    @Override
    @ApiOperation(value = "新增图文文章")
    @PreAuthorize("hasAnyRole('USER_EW', 'ADMIN')")
    public Result<?> create(@Validated @RequestBody TextArticlesCreate model) {
        ArticleStatus status;
        if (null == (status = model.getStatus()))
            return new Result<>(Code.BAD_REQ, "文章状态无效");
        //新建状态只能为 审核中和草稿
        if (ArticleStatus.CHECK != status && ArticleStatus.DRAFT != status)
            return new Result<>(Code.BAD_REQ, "资讯状态不正确");
        return this.service.createText(model);
    }

    @Override
    @ApiOperation(value = "新增纯图文章")
    @PreAuthorize("hasAnyRole('USER_EW', 'ADMIN')")
    public Result<?> create(@Validated @RequestBody PicArticlesCreate model) {
        ArticleStatus status;
        if (null == (status = model.getStatus()))
            return new Result<>(Code.BAD_REQ, "文章状态无效");
        //新建状态只能为 审核中和草稿
        if (ArticleStatus.CHECK != status && ArticleStatus.DRAFT != status)
            return new Result<>(Code.BAD_REQ, "资讯状态不正确");
        return this.service.createPic(model);
    }

    @Override
    @ApiOperation(value = "新增媒体文章")
    @PreAuthorize("hasAnyRole('USER_EW', 'ADMIN')")
    public Result<?> create(@Validated @RequestBody MediaArticlesCreate model) {
        ArticleStatus status;
        if (null == (status = model.getStatus()))
            return new Result<>(Code.BAD_REQ, "文章状态无效");
        //新建状态只能为 审核中和草稿
        if (ArticleStatus.CHECK != status && ArticleStatus.DRAFT != status)
            return new Result<>(Code.BAD_REQ, "资讯状态不正确");
        Type type = model.getType();
        if (Type.VIDEO != type && Type.AUDIO != type)
            return new Result<>(Code.BAD_REQ, "资讯类型不正确");
        return this.service.createMedia(model);
    }

    @Override
    @ApiOperation(value = "修改图文文章")
    @PreAuthorize("hasAnyRole('USER_EW', 'ADMIN')")
    public Result<?> modify(@Validated @RequestBody TextArticlesModify model) {
        return this.service.modifyText(model);
    }

    @Override
    @ApiOperation(value = "修改纯图文章")
    @PreAuthorize("hasAnyRole('USER_EW', 'ADMIN')")
    public Result<?> modify(@Validated @RequestBody PicArticlesModify model) {
        return this.service.modifyPic(model);
    }

    @Override
    @ApiOperation(value = "修改媒体文章")
    @PreAuthorize("hasAnyRole('USER_EW', 'ADMIN')")
    public Result<?> modify(@Validated @RequestBody MediaArticlesModify model) {
        return this.service.modifyMedia(model);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "资讯审核")
    public Result<?> review(@Validated @RequestBody ArticleReview model) {
        ArticleStatus status = model.getStatus();
        if (ArticleStatus.ISSUE != status && ArticleStatus.REFUSE != status//
                && ArticleStatus.OFFLINE != status)
            return new Result<>(Code.BAD_REQ, "审核状态不正确");
        String reason = model.getReason();
        if (ArticleStatus.REFUSE == status && (null == reason || "".equals(reason)))
            return new Result<>(Code.BAD_REQ, "拒绝原因无效");
        return this.service.review(model);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','USER_EW')")
    @ApiOperation(value = "删除资讯")
    @ApiImplicitParam(name = "ids", value = "ID, 限制1~1024个", required = true, allowMultiple = true)
    public Result<?> delete(@RequestParam("ids") Set<Long> ids) {
        if (null == ids || ids.isEmpty() || 1024 < ids.size())
            return new Result<>(Code.BAD_REQ, "ID无效");
        return this.service.delete(ids);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','USER_EW')")
    @ApiOperation(value = "投放服豆")
    public Result<?> stock(@Validated @RequestBody ArticleStock model) {
        return this.service.stock(model);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','USER_EW')")
    @ApiOperation(value = "取消发表")
    public Result<?> cancel(@PathVariable("id") Long id) {
        if (null == id || id < Util.MIN_ID)
            return new Result<>(Code.BAD_REQ, "ID无效");
        return this.service.cancel(id);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','USER_EW')")
    @ApiOperation(value = "文章设置")
    public Result<?> setting(@Validated @RequestBody ArticleSetting model) {
//        Date issueTime = model.getIssueTime();
//        Date date = new Date(System.currentTimeMillis());
//        if (null != issueTime && issueTime.before(date))
//            return new Result<>(Code.BAD_REQ, "发布时间无效");
        return this.service.setting(model);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','USER_EW')")
    @ApiOperation(value = "后台文章统计")
    public Result<UserArticleCountDetail> total() {
        Long uid = SecurityUtil.getId();
        if(null == uid)
            return new Result<>(Code.UNAUTHED, "用户无效");
        UserArticleCount userCount = this.service.countArticle(uid);

        return new Result<>(new UserArticleCountDetail(uid, userCount.getArticleTotal(), userCount.getShareTotal(),
                userCount.getBrowseTotal(), userCount.getCommentTotal(), userCount.getPraiseTotal(), userCount.getCollectionTotal(),
                userCount.getRewardTotal(), userCount.getRewardProfitTotal()));
    }

    @Override
    @ApiOperation(value = "文章KPI累加")
    public Result<?> indicators(ArticleIndicatorsModify model) {
        return this.service.indicators(model);
    }

    /**
     * 根据文章ID扣除服豆库存
     *
     * @param articleId--文章ID
     * @param beansNum--扣除服豆数
     * @return Result<?> 操作结果
     */
    @Override
    @ApiOperation(value = "扣除服豆")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "articleId", value = "文章ID", required = true),
            @ApiImplicitParam(name = "beansNum", value = "服豆数", required = true) })
    public Result<?> reduceStock(@PathVariable("articleId") Long articleId, @PathVariable("beansNum") Long beansNum) {
        if (null == articleId || Util.MIN_ID > articleId)
            return new Result<>(Code.BAD_REQ, "文章ID无效");

        if (null == beansNum || 0L == beansNum)
            return Result.OK;

        return this.service.reduceStock(articleId, beansNum);
    }
}