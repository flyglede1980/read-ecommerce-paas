package net.yitun.ioften.cms.service.impl;

import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.Code;
import net.yitun.basic.dict.Status;
import net.yitun.basic.dict.YesNo;
import net.yitun.basic.exp.BizException;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.CalcUtil;
import net.yitun.basic.utils.IdUtil;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.basic.utils.SetUtil;
import net.yitun.comon.CobjsApi;
import net.yitun.comon.model.cobj.Cobj;
import net.yitun.ioften.cms.conf.Constant;
import net.yitun.ioften.cms.dicts.ArticleStatus;
import net.yitun.ioften.cms.dicts.CoverNum;
import net.yitun.ioften.cms.dicts.StockStatus;
import net.yitun.ioften.cms.dicts.Type;
import net.yitun.ioften.cms.entity.UserTotal;
import net.yitun.ioften.cms.entity.aritcles.*;
import net.yitun.ioften.cms.model.article.ArticleQuery;
import net.yitun.ioften.cms.model.article.ArticleReviewQuery;
import net.yitun.ioften.cms.model.article.CoverPicturesCreate;
import net.yitun.ioften.cms.model.article.create.MediaArticlesCreate;
import net.yitun.ioften.cms.model.article.create.PicArticlesCreate;
import net.yitun.ioften.cms.model.article.create.PicFilesCreate;
import net.yitun.ioften.cms.model.article.create.TextArticlesCreate;
import net.yitun.ioften.cms.model.article.modify.ArticleIndicatorsModify;
import net.yitun.ioften.cms.model.article.modify.MediaArticlesModify;
import net.yitun.ioften.cms.model.article.modify.PicArticlesModify;
import net.yitun.ioften.cms.model.article.modify.TextArticlesModify;
import net.yitun.ioften.cms.model.article.setting.ArticleReview;
import net.yitun.ioften.cms.model.article.setting.ArticleSetting;
import net.yitun.ioften.cms.model.article.setting.ArticleStock;
import net.yitun.ioften.cms.repository.ArticleRepository;
import net.yitun.ioften.cms.repository.vo.ArticlesQueryVo;
import net.yitun.ioften.cms.service.ArticleService;
import net.yitun.ioften.cms.util.RegExpHtml;
import net.yitun.ioften.crm.UserApi;
import net.yitun.ioften.crm.dicts.Level;
import net.yitun.ioften.pay.ConfApi;
import net.yitun.ioften.pay.WalletApi;
import net.yitun.ioften.pay.dicts.Channel;
import net.yitun.ioften.pay.dicts.Currency;
import net.yitun.ioften.pay.dicts.Way;
import net.yitun.ioften.pay.model.flows.FlowsDetail;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service("cms.ArticleService")
@SuppressWarnings("deprecation")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    protected UserApi userApi;

    @Autowired
    protected ConfApi confApi;

    @Autowired
    protected CobjsApi cobjsApi;

    @Autowired
    protected WalletApi walletApi;

    @Autowired
    protected ArticleRepository repository;

    /**
     * 分页查询(后台)
     *
     * @param query 查询参数
     * @return Page<Articles>查询结果
     */
    @Override
    public Page<ArticlesBO> query(ArticleQuery query) {
        String byType = null;
        Type type = query.getType();
        if (null != type)
            byType = String.valueOf(type.val());

        ArticlesQueryVo queryVo = new ArticlesQueryVo(byType,
                (null == query.getArticleId()) ? null : SetUtil.asSet(query.getArticleId()), query.getClassIds(),
                query.getAid(), query.getUid(), query.getMainTitle(), query.getStockStatus(), query.getStatus(),
                query.getStime(), query.getEtime(), Status.ENABLE);
        return this.repository.query(queryVo);
    }

    /**
     * 文章审核分页
     *
     * @param query 查询参数
     * @return Page<ArticlesBO>
     */
    @Override
    public Page<ArticlesBO> query(ArticleReviewQuery query) {
        Type type = query.getType();
        String byType = null;
        if (null != type)
            byType = String.valueOf(type.val());
        ArticlesQueryVo queryVo //
                = new ArticlesQueryVo(byType, null, null, null, query.getUid(), null, null, query.getArticleStatus(),
                null, null, Status.ENABLE);
        return this.repository.query(queryVo);
    }

    /**
     * 根据文章ID获取文章信息
     *
     * @param id--文章编号
     * @return 文章信息
     */
    @Override
    public Articles getArticles(Long id) {
        return this.repository.findWithUnLock(id);
    }

    /**
     * 根据文章ID获取纯图文章信息
     *
     * @param id--文章编号
     * @return 纯图文章信息
     */
    @Override
    public PicArticles getPicArticles(Long id) {
        return this.repository.findPicArticlesWithUnLock(id);
    }

    /**
     * 根据文章ID获取图文文章信息
     *
     * @param id--文章编号
     * @return 图文文章信息
     */
    public TextArticles getTextArticles(Long id) {
        return this.repository.findTextArticlesWithUnLock(id);
    }

    /**
     * 查询封面图片
     *
     * @param id 文章ID
     * @return 查询结果
     */
    @Override
    public List<CoverPictures> getCoverPic(Long id) {
        return this.repository.queryCoverPictures(id);
    }

    /**
     * 查询纯图文章
     *
     * @param id 文章ID
     * @return List<PicFiles> 查询结果
     */
    @Override
    public List<PicFiles> getPicFiles(Long id) {
        return this.repository.queryPicFiles(id);
    }

    /**
     * 查询媒体文章
     *
     * @param id 文章ID
     * @return List<MediaArticles> 查询结果
     */
    @Override
    public MediaArticles getMediaArticles(Long id) {
        return this.repository.findMediaArticlesWithUnLock(id);
    }

    /**
     * 查询文章统计
     *
     * @param id 文章ID
     * @return ArticleIndicators 查询结果
     */
    @Override
    public ArticleIndicators getArtIndicators(Long id) {
        return this.repository.findArticleIndicators(id);
    }

    /**
     * 发布图文文章
     *
     * @param model 新增模型
     * @return 新增结果
     */
    @Override
    @Transactional
    public Result<?> createText(TextArticlesCreate model) {
        Long id = IdUtil.id(), uid = SecurityUtil.getId();
        Date now = new Date(System.currentTimeMillis());
        Date issueTime = model.getIssueTime();
        ArticleStatus status = model.getStatus();
        Date applyTime = null;
        if (ArticleStatus.CHECK == status)
            applyTime = now;
        // 保存文章基本信息articles
        Articles articles = new Articles(id, 0L, uid, model.getMainTitle(), model.getSubTitle(), model.getSummary(),
                model.getKeyWord(), model.getSortId(), 0L, YesNo.NO, YesNo.NO, Status.ENABLE, YesNo.NO, YesNo.NO,
                YesNo.NO, YesNo.NO, YesNo.NO, YesNo.NO, Level.N, now, now, 0F, 0L, StockStatus.NONE, issueTime, status,
                null, null, Type.TEXT, applyTime, null);

        if (Constant.OFFICIAL_ACCOUNT.contains(uid)) {
            if (null != issueTime && now.before(issueTime)) {
                articles.setIssueStatus(ArticleStatus.WAIT_ISSUE);
                articles.setVerifyTime(issueTime);
            } else {
                articles.setIssueStatus(ArticleStatus.ISSUE);
                articles.setVerifyTime(now);
                articles.setIssueTime(now);
            }
        }
        if (!this.repository.createArticles(articles))
            throw new BizException(Code.BIZ_ERROR, "文章发布失败");

        // 格式化富文本的图片
        String details = model.getDetails();
        // 构造存储模板
        Set<Cobj> cobjs = new LinkedHashSet<>();
        long index = System.currentTimeMillis();
        String newPath = "news/" + id + "/", newKey; // 归档新路径: adv/id/0
        // 判断是否有图片
        List<CoverPicturesCreate> coverPictures = model.getCoverPictures();
        CoverNum coverNum = model.getCoverNum();
        //反转义富文本，获取img标签内容
        String html = StringEscapeUtils.unescapeHtml4(details);
        List<String> imageList = RegExpHtml.pick(html);
        Map<String, String> icon = new HashMap<>();
        //如果是自动获取图片
        if (CoverNum.AUTO == coverNum) {
            if (!imageList.isEmpty()) {
                String newCoverIcon = imageList.get(0);
                String hw = "myhwclouds.com/";
                if (newCoverIcon.contains(hw))
                    newCoverIcon = newCoverIcon.substring(hw.length());
                newKey = newCoverIcon;
                if (StringUtils.endsWith(newCoverIcon, "?cjobs=true")) {
                    newCoverIcon = newCoverIcon.substring(0, newCoverIcon.indexOf("?cjobs=true"));
                    newKey = newPath + (++index);
                    cobjs.add(new Cobj(newCoverIcon, newKey));
                    icon.put(newCoverIcon, newKey);
                }
                //保存封面图片
                if (!this.repository.createCoverPictures(
                        new CoverPictures(IdUtil.id(), id, newKey, 0, Status.ENABLE)))
                    throw new BizException(Code.BIZ_ERROR, "文章发布失败");
            }
        } else {
            if (null != coverPictures && !coverPictures.isEmpty()) {
                CoverPictures pic;
                for (CoverPicturesCreate cover : coverPictures) {
                    String coverIcon = cover.getIcon();
                    if (icon.containsKey(coverIcon))
                        newKey = icon.get(coverIcon);
                    else {
                        newKey = newPath + (++index);
                        cobjs.add(new Cobj(coverIcon, newKey));
                        icon.put(coverIcon, newKey);
                    }
                    pic = new CoverPictures(IdUtil.id(), id, newKey, cover.getSortId(), Status.ENABLE);
                    if (!this.repository.createCoverPictures(pic)) {
                        throw new BizException(Code.BIZ_ERROR, "封面图片保存失败");
                    }
                }
            }
        }
        //寻找富文本里需要保存的图片
        List<String> obsList = RegExpHtml.pickObs(html);
        for (String str : obsList) {
            String hw = "myhwclouds.com/";
            if (str.contains(hw))
                str = str.substring(hw.length());
            if (icon.containsKey(str)) {
                newKey = icon.get(str);
            } else {
                newKey = newPath + (++index);
                cobjs.add(new Cobj(str, newKey));
                icon.put(str, newKey);
            }
            details = details.replace(str, newKey);
        }

        // 保存文章详情textArticles
        TextArticles textArticles = new TextArticles(id, details, coverNum);
        if (!this.repository.createTextArticles(textArticles))
            throw new BizException(Code.BIZ_ERROR, "文章发布失败");

        // 保存articleindicators
        ArticleIndicators articleIndicators = new ArticleIndicators(id, 0, 0, 0, 0, 0, 0, 0);
        if (!this.repository.createArticleIndicators(articleIndicators))
            throw new BizException(Code.BIZ_ERROR, "文章发布失败");

        // 确认封面图片保存结果
        if (!cobjs.isEmpty()) {
            Result<?> result;
            if (null == (result = this.cobjsApi.sure(cobjs)) || !result.ok()) {
                if (log.isInfoEnabled())
                    log.info("<<< {}.create() cobj sure image faild, {}", this.getClass().getName(),
                            JsonUtil.toJson(result));
                throw new BizException(Code.BIZ_ERROR, "保存图片失败");
            }
        }
        if (log.isInfoEnabled())
            log.info("<<< {}.createText() id:{}", this.getClass().getName(), id);
        return Result.OK;
    }

    /**
     * 发布纯图文章
     *
     * @param model 新增模型
     * @return 新增结果
     */
    @Override
    @Transactional
    public Result<?> createPic(PicArticlesCreate model) {
        List<CoverPicturesCreate> coverPictures = model.getCoverPictures();
        Long id = IdUtil.id(), uid = SecurityUtil.getId();
        Date now = new Date(System.currentTimeMillis());
        Date issueTime = model.getIssueTime();
        ArticleStatus status = model.getStatus();
        Date applyTime = null;
        if (ArticleStatus.CHECK == status)
            applyTime = now;
        // 保存文章基本信息
        Articles articles = new Articles(id, 0L, uid, model.getMainTitle(), model.getSubTitle(), model.getSummary(),
                model.getKeyWord(), model.getSortId(), 0L, YesNo.NO, YesNo.NO, Status.ENABLE, YesNo.NO, YesNo.NO,
                YesNo.NO, YesNo.NO, YesNo.NO, YesNo.NO, Level.N, now, now, 0F, 0L, StockStatus.NONE, issueTime, status,
                null, null, Type.IMAGE, applyTime, null);
        if (Constant.OFFICIAL_ACCOUNT.contains(uid)) {
            if (null != issueTime && now.before(issueTime))
                articles.setIssueStatus(ArticleStatus.WAIT_ISSUE);
            else
                articles.setIssueStatus(ArticleStatus.ISSUE);
        }
        if (!this.repository.createArticles(articles))
            return new Result<>(Code.BIZ_ERROR, "发布文章失败");

        // 保存picarticles表
        PicArticles picArticles = new PicArticles(id, model.getCoverNum());
        if (!this.repository.createPicArticles(picArticles))
            throw new BizException(Code.BIZ_ERROR, "文章发布失败");

        // 保存picFiles
        List<PicFilesCreate> picFilesCreates = model.getPicFiles();
        PicFiles picFiles;
        // 构建存储模板
        Set<Cobj> cobjs = new LinkedHashSet<>();
        long index = System.currentTimeMillis();
        String newPath = "news/" + id + "/", newKey; // 归档新路径: adv/id/0
        Map<String, String> icon = new HashMap<>();
        for (PicFilesCreate pic : picFilesCreates) {
            // 更换图片路径
            String url = pic.getLinkUrl();
            if (icon.containsKey(url))
                newKey = icon.get(url);
            else {
                newKey = newPath + (++index);
                cobjs.add(new Cobj(url, newKey));
                icon.put(url, newKey);
            }
            picFiles = new PicFiles(IdUtil.id(), id, newKey, pic.getDescription(), pic.getSortId(), Status.ENABLE);
            if (!this.repository.createPicFiles(picFiles))
                throw new BizException(Code.BIZ_ERROR, "文章发布失败");
        }

        // 保存articleindicators
        ArticleIndicators articleIndicators = new ArticleIndicators(id, 0, 0, 0, 0, 0, 0, 0);
        if (!this.repository.createArticleIndicators(articleIndicators))
            throw new BizException(Code.BIZ_ERROR, "文章发布失败");

        // 构建封面图片存储路径， 保存封面图片保存实体
        if (null != coverPictures && !coverPictures.isEmpty()) {
            if (coverPic(cobjs, index, coverPictures, id, icon))
                throw new BizException(Code.BIZ_ERROR, "发布文章失败");
        }

        if (log.isInfoEnabled())
            log.info("<<< {}.createPic() id:{}", this.getClass().getName(), id);
        return Result.OK;
    }

    /**
     * 发布媒体文章
     *
     * @param model 新增模型
     * @return 新增结果
     */
    @Override
    @Transactional
    public Result<?> createMedia(MediaArticlesCreate model) {
        List<CoverPicturesCreate> coverPictures = model.getCoverPictures();
        Long id = IdUtil.id(), uid = SecurityUtil.getId();
        Date now = new Date(System.currentTimeMillis());
        Date issueTime = model.getIssueTime();
        ArticleStatus status = model.getStatus();
        Date applyTime = null;
        if (ArticleStatus.CHECK == status)
            applyTime = now;
        //保存文章基本信息
        Articles articles = new Articles(id, 0L, uid, model.getMainTitle(), model.getSubTitle(), model.getSummary(),
                model.getKeyWord(), model.getSortId(), 0L, YesNo.NO, YesNo.NO, Status.ENABLE, YesNo.NO, YesNo.NO,
                YesNo.NO, YesNo.NO, YesNo.NO, YesNo.NO, Level.N, now, now, 0F, 0L, StockStatus.NONE, issueTime,
                model.getStatus(), null, null, model.getType(), applyTime, null);
        if (Constant.OFFICIAL_ACCOUNT.contains(uid)) {
            if (null != issueTime && now.before(issueTime))
                articles.setIssueStatus(ArticleStatus.WAIT_ISSUE);
            else
                articles.setIssueStatus(ArticleStatus.ISSUE);
        }
        if (!this.repository.createArticles(articles))
            return new Result<>(Code.BIZ_ERROR, "发布文章失败");

        // 保存音视频文件
        MediaArticles mediaArticles = new MediaArticles(id, model.getLinkUrl(), 0, 0, model.getDescription());
        if (!this.repository.createMediaArticles(mediaArticles))
            throw new BizException(Code.BIZ_ERROR, "发布文章失败");

        // 保存articleindicators
        ArticleIndicators articleIndicators = new ArticleIndicators(id, 0, 0, 0, 0, 0, 0, 0);
        if (!this.repository.createArticleIndicators(articleIndicators))
            throw new BizException(Code.BIZ_ERROR, "文章发布失败");

        // 构建封面图片存储路径， 保存封面图片保存实体
        Set<Cobj> cobjs = new LinkedHashSet<>();
        long index = System.currentTimeMillis();
        if (null != coverPictures && !coverPictures.isEmpty()) {
            Map<String, String> icon = new HashMap<>();
            if (coverPic(cobjs, index, coverPictures, id, icon))
                throw new BizException(Code.BIZ_ERROR, "发布文章失败");
        }

        if (log.isInfoEnabled())
            log.info("<<< {}.createMedia() id:{}", this.getClass().getName(), id);
        return Result.OK;
    }

    /**
     * 修改图文文章
     *
     * @param model 修改模型
     * @return 修改结果
     */
    @Override
    @Transactional
    public Result<?> modifyText(TextArticlesModify model) {
        Long id = model.getArticleId();
        // 查询旧的textArticles
        Articles oldArticle;
        TextArticles oldTextArticle;
        if (null == (oldTextArticle = this.repository.findTextArticlesWithUnLock(id)))
            return new Result<>(Code.BIZ_ERROR, "文章已不存在");
        // 查询旧的Articles
        if (null == (oldArticle = this.repository.findWithUnLock(id)))// 取出之前的资讯
            return new Result<>(Code.BIZ_ERROR, "文章已不存在");

        // 查询旧的封面图片
        List<CoverPictures> oldPictures = this.repository.queryCoverPictures(id);
        Set<String> oldCovers = new HashSet<>();
        Map<String, CoverPictures> oldPicMaps = new HashMap<>();
        // 获取旧的封面图片路径
        getOldCoverIcon(oldPictures, oldCovers, oldPicMaps);

        // 获取新的封面图片
        List<CoverPicturesCreate> newPictures = model.getCoverPictures();
        if (null == newPictures)
            newPictures = new ArrayList<>();
        // 构建存储模版
        Set<Cobj> cobjs = new LinkedHashSet<>();
        long index = System.currentTimeMillis();
        String newPath = "news/" + id + "/", newKey; // 归档新路径: adv/id/0
        Map<String, String> saveIcon = new HashMap<>();

        // 判断富文本图片是否被修改;
        String newDetails = model.getDetails();
        String oldDetails = oldTextArticle.getDetails();
        // 获取富文本所有新旧图片路径
        String newHtml = StringEscapeUtils.unescapeHtml4(newDetails);
        String oldHtml = StringEscapeUtils.unescapeHtml4(oldDetails);
        List<String> imageList = RegExpHtml.pick(newHtml);
        List<String> oldImageList = RegExpHtml.pickObs(oldHtml);
        // 查找替换的封面图片
        CoverNum coverNum = model.getCoverNum();
        if (CoverNum.AUTO == coverNum) {
            if (!imageList.isEmpty()) {
                String newCoverIcon = imageList.get(0);
                //如果有
                String hw = "myhwclouds.com/";
                if (newCoverIcon.contains(hw))
                    newCoverIcon = newCoverIcon.substring(hw.length());
                newKey = newCoverIcon;
                if (StringUtils.endsWith(newCoverIcon, "?cjobs=true"))
                    newCoverIcon = newCoverIcon.substring(0, newCoverIcon.indexOf("?cjobs=true"));
                //是否更换封面图
                if (!oldCovers.contains(newCoverIcon) && !oldImageList.contains(newCoverIcon)) {
                    if (StringUtils.endsWith(newKey, "?cjobs=true")) {
                        newKey = newPath + (++index);
                        cobjs.add(new Cobj(newCoverIcon, newKey));
                        saveIcon.put(newCoverIcon, newKey);
                    }
                    //保存封面图片
                    if (!this.repository.createCoverPictures(
                            new CoverPictures(IdUtil.id(), id, newKey, 0, Status.ENABLE)))
                        return new Result<>(Code.BIZ_ERROR, "修改文章失败");
                }
                oldCovers.remove(newCoverIcon);
            }
        } else
            replaceCover(id, cobjs, index, newPath, saveIcon, oldCovers, oldPicMaps, newPictures);
        // 寻找富文本替换的图片
        List<String> newImageList = RegExpHtml.pickObs(newHtml);
        Set<String> newPic = new HashSet<>(newImageList);
        Set<String> oldPic = new HashSet<>(oldImageList);
        for (String image : newPic) {
            String newImage = image;
            String hw = "myhwclouds.com/";
            if (newImage.contains(hw))
                newImage = newImage.substring(hw.length());
            newKey = newImage;
            if (!oldPic.contains(newImage)) {
                if (saveIcon.containsKey(newImage))
                    newKey = saveIcon.get(newImage);
                else {
                    newKey = newPath + (++index);
                    cobjs.add(new Cobj(newImage, newKey));
                    saveIcon.put(newImage, newKey);
                }
            }
            newDetails = newDetails.replace(image, newKey);
            oldPic.remove(newImage);
        }

        Date now = new Date(System.currentTimeMillis());
        // 保存文章内容
        Articles articles = new Articles(id, model.getParentId(), oldArticle.getUsersId(), model.getMainTitle(),
                model.getSubTitle(), model.getSummary(), model.getKeyWord(), model.getSortId(),
                oldArticle.getBeansNum(), oldArticle.getIsHot(), oldArticle.getIsRecommend(), Status.ENABLE,
                oldArticle.getIsTop(), oldArticle.getIsAuthorized(), model.getIsCommented(),
                oldArticle.getShareSwitch(), oldArticle.getReadSwitch(), oldArticle.getPraiseSwitch(),
                oldArticle.getReadLevel(), oldArticle.getCtime(), now, oldArticle.getRechargeRatio(),
                oldArticle.getAddInvest(), oldArticle.getInvestStatus(), oldArticle.getIssueTime(), ArticleStatus.CHECK,
                oldArticle.getVerifyId(), oldArticle.getVerifyTime(), oldArticle.getType(), now,
                oldArticle.getReason());

        TextArticles textArticles = new TextArticles(id, newDetails, coverNum);
        if (!this.repository.editArticles(articles))
            throw new BizException(Code.BIZ_ERROR, "修改文章失败");
        if (!this.repository.editTextArticles(textArticles))
            throw new BizException(Code.BIZ_ERROR, "修改文章失败");

        // 确认封面图片保存结果
        Result<?> result;
        if (!cobjs.isEmpty())
            if (null == (result = this.cobjsApi.sure(cobjs)) || !result.ok()) {
                if (log.isInfoEnabled())
                    log.info("<<< {}.modifyText() cobj sure image faild, {}", this.getClass().getName(),
                            JsonUtil.toJson(result));
                throw new BizException(Code.BIZ_ERROR, "保存封面图片失败");
            }
        // 删除封面图片
        this.delCovers(oldCovers, oldPicMaps);
        // 删除details的图片
        oldCovers.addAll(oldPic);
        // 删除图片
        if (!oldCovers.isEmpty()) {
            if (null == (result = this.cobjsApi.delete(oldCovers)) || !result.ok()) {
                if (log.isInfoEnabled())
                    log.info("<<< {}.modifyText() cobj delete image faild, {}", this.getClass().getName(),
                            JsonUtil.toJson(result));
                throw new BizException(Code.BIZ_ERROR, "删除封面图片失败");
            }
        }
        return Result.OK;
    }

    private void delCovers(Set<String> oldCovers, Map<String, CoverPictures> oldPicMaps) {
        if (SetUtil.isNotEmpty(oldCovers)) {
            List<Long> picIds = new ArrayList<>();
            for (String str : oldCovers) {
                CoverPictures pictures = oldPicMaps.get(str);
                if (null != pictures)
                    picIds.add(pictures.getPictureId());
            }
            if (!this.repository.deleteCoverByPicId(picIds))
                throw new BizException(Code.BIZ_ERROR, "文章修改失败");
        }
    }

    /**
     * 修改纯图文章
     *
     * @param model 修改模型
     * @return 修改结果
     */
    @Override
    @Transactional
    public Result<?> modifyPic(PicArticlesModify model) {
        Long id = model.getArticleId();
        Articles articles;
        if (null == (articles = this.repository.findWithUnLock(id)))
            return new Result<>(Code.BAD_REQ, "ID无效");

        PicArticles picArticles;
        if (null == (picArticles = this.repository.findPicArticlesWithUnLock(id)))
            return new Result<>(Code.BAD_REQ, "ID无效");

        CoverNum coverNum = model.getCoverNum();
        if (picArticles.getCoverNum() != coverNum) {
            picArticles.setCoverNum(coverNum);
            if (!this.repository.editPicArticles(picArticles))
                return new Result<>(Code.BIZ_ERROR, "修改文章失败");
        }
        // 构建存储模版
        Set<Cobj> cobjs = new LinkedHashSet<>();
        long index = System.currentTimeMillis();
        String newPath = "news/" + id + "/", newKey; // 归档新路径: adv/id/0
        Map<String, String> saveIcon = new HashMap<>();
        // 获取新图片
        List<CoverPicturesCreate> newCoverPics = model.getCoverPictures();
        // 获取旧的封面图片路径
        List<CoverPictures> oldCoverPics = this.repository.queryCoverPictures(id);
        Set<String> oldCovers = new HashSet<>();
        Map<String, CoverPictures> oldPicMaps = new HashMap<>();
        getOldCoverIcon(oldCoverPics, oldCovers, oldPicMaps);
        // 避免空指针异常
        if (null == newCoverPics)
            newCoverPics = new ArrayList<>();

        // 获取旧的纯图文件
        List<PicFiles> oldPicFiles = this.repository.queryPicFiles(id);
        Set<String> oldPics = new HashSet<>();
        Map<String, PicFiles> oldPicFilesMap = new HashMap<>();
        if (null != oldPicFiles) {
            for (PicFiles picFiles : oldPicFiles) {
                String url = picFiles.getLinkUrl();
                oldPics.add(url);
                oldPicFilesMap.put(url, picFiles);
            }
        }
        // 获取新的纯图文件
        List<PicFilesCreate> newPicFiles = model.getPicFiles();
        if (null == newPicFiles)
            newPicFiles = new ArrayList<>();
        // 如果是自动获取封面, 从纯图文件中取第一张
        if (CoverNum.AUTO == coverNum) {
            if (!newPicFiles.isEmpty()) {
                PicFilesCreate picFilesCreate = newPicFiles.get(0);
                String url = picFilesCreate.getLinkUrl();
                newKey = url;
                if (!oldPics.contains(url)) {
                    newKey = newPath + (++index);
                    cobjs.add(new Cobj(url, newKey));
                    saveIcon.put(url, newKey);
                }
                // 保存封面图片
                if (!this.repository.createCoverPictures(
                        new CoverPictures(IdUtil.id(), id, newKey, picFilesCreate.getSortId(), Status.ENABLE)))
                    return new Result<>(Code.BIZ_ERROR, "修改文章失败");
            }
        } else
            replaceCover(id, cobjs, index, newPath, saveIcon, oldCovers, oldPicMaps, newCoverPics);
        // 循环查找修改的图片内容
        for (PicFilesCreate picCreate : newPicFiles) {
            String url = picCreate.getLinkUrl();
            // 如果旧的路径不包含新的路径，新增图片
            if (!oldPics.contains(url)) {
                if (saveIcon.containsKey(url))
                    newKey = saveIcon.get(url);
                else {
                    newKey = newPath + (++index);
                    cobjs.add(new Cobj(url, newKey));
                    saveIcon.put(url, newKey);
                }
                // 保存纯图文件
                if (!this.repository.createPicFiles(new PicFiles(IdUtil.id(), id, newKey, picCreate.getDescription(),
                        picCreate.getSortId(), Status.ENABLE)))
                    throw new BizException(Code.BIZ_ERROR, "修改文章失败");
            } else {// 保存修改的纯图文件
                PicFiles picFiles = oldPicFilesMap.get(url);
                picFiles.setSortId(picCreate.getSortId());// 相同设置顺序
                picFiles.setDescription(picCreate.getDescription());
                if (!this.repository.editPicFile(picFiles))// 修改封面图片
                    throw new BizException(Code.BIZ_ERROR, "修改文章失败");
            }
            oldPics.remove(url);
        }

        Date now = new Date(System.currentTimeMillis());
        Articles saveArt = new Articles(id, model.getParentId(), articles.getUsersId(), model.getMainTitle(),
                model.getSubTitle(), model.getSummary(), model.getKeyWord(), model.getSortId(), articles.getBeansNum(),
                articles.getIsHot(), articles.getIsRecommend(), Status.ENABLE, articles.getIsTop(),
                articles.getIsAuthorized(), model.getIsCommented(), articles.getShareSwitch(), articles.getReadSwitch(),
                articles.getPraiseSwitch(), articles.getReadLevel(), articles.getCtime(), now,
                articles.getRechargeRatio(), articles.getAddInvest(), articles.getInvestStatus(),
                articles.getIssueTime(), ArticleStatus.CHECK, articles.getVerifyId(), articles.getVerifyTime(),
                articles.getType(), now, articles.getReason());
        if (!this.repository.editArticles(saveArt))
            throw new BizException(Code.BIZ_ERROR, "修改文章失败");

        // 确认封面图片保存结果
        Result<?> result;
        if (!cobjs.isEmpty())
            if (null == (result = this.cobjsApi.sure(cobjs)) || !result.ok()) {
                if (log.isInfoEnabled())
                    log.info("<<< {}.modifyPic() cobj sure image faild, {}", this.getClass().getName(),
                            JsonUtil.toJson(result));
                throw new BizException(Code.BIZ_ERROR, "保存封面图片失败");
            }
        // 删除封面图片
        this.delCovers(oldCovers, oldPicMaps);

        if (SetUtil.isNotEmpty(oldPics)) {
            List<Long> fileIds = new ArrayList<>();
            for (String str : oldPics) {
                PicFiles picFiles = oldPicFilesMap.get(str);
                if (null != picFiles)
                    fileIds.add(picFiles.getFileId());
            }
            if (!this.repository.delPicFilesByFileIds(fileIds))
                throw new BizException(Code.BIZ_ERROR, "文章修改失败");
        }
        oldCovers.addAll(oldPics);
        if (SetUtil.isNotEmpty(oldCovers)) {
            if (null == (result = this.cobjsApi.delete(oldCovers)) || !result.ok()) {
                if (log.isInfoEnabled())
                    log.info("<<< {}.modifyPic() cobj delete image faild, {}", this.getClass().getName(),
                            JsonUtil.toJson(result));
                throw new BizException(Code.BIZ_ERROR, "删除封面图片失败");
            }
        }
        return Result.OK;
    }

    /**
     * 修改媒体文章
     *
     * @param model 修改模型
     * @return 修改结果
     */
    @Override
    @Transactional
    public Result<?> modifyMedia(MediaArticlesModify model) {
        Articles articles;
        Long id = model.getArticleId();
        if (null == (articles = this.repository.findWithUnLock(id)))
            return new Result<>(Code.BAD_REQ, "ID无效");

        if (null == (this.repository.findMediaArticlesWithUnLock(id)))
            return new Result<>(Code.BAD_REQ, "ID无效");

        // 构建存储模版
        Set<Cobj> cobjs = new LinkedHashSet<>();
        long index = System.currentTimeMillis();
        String newPath = "news/" + id + "/"; // 归档新路径: adv/id/0
        Map<String, String> saveIconMap = new HashMap<>();

        // 获取旧的封面图片
        List<CoverPictures> oldCoverPictures = this.repository.queryCoverPictures(id);
        Set<String> oldCovers = new HashSet<>();
        Map<String, CoverPictures> oldCoverPics = new HashMap<>();
        getOldCoverIcon(oldCoverPictures, oldCovers, oldCoverPics);

        // 获取新的封面图片
        List<CoverPicturesCreate> coverPictures = model.getCoverPictures();
        if (null == coverPictures)
            coverPictures = new ArrayList<>();

        replaceCover(id, cobjs, index, newPath, saveIconMap, oldCovers, oldCoverPics, coverPictures);
        // 修改文章
        Date now = new Date(System.currentTimeMillis());
        Articles saveArt = new Articles(id, model.getParentId(), articles.getUsersId(), model.getMainTitle(),
                model.getSubTitle(), model.getSummary(), model.getKeyWord(), model.getSortId(), articles.getBeansNum(),
                articles.getIsHot(), articles.getIsRecommend(), Status.ENABLE, articles.getIsTop(),
                articles.getIsAuthorized(), model.getIsCommented(), articles.getShareSwitch(), articles.getReadSwitch(),
                articles.getPraiseSwitch(), articles.getReadLevel(), articles.getCtime(), now,
                articles.getRechargeRatio(), articles.getAddInvest(), articles.getInvestStatus(),
                articles.getIssueTime(), ArticleStatus.CHECK, articles.getVerifyId(), articles.getVerifyTime(),
                model.getType(), now, articles.getReason());
        if (!this.repository.editArticles(saveArt))
            throw new BizException(Code.BIZ_ERROR, "修改文章失败");

        MediaArticles mediaArticles = new MediaArticles(id, model.getLinkUrl(), 0, 0, model.getDescription());
        if (!this.repository.editMediaArticles(mediaArticles))
            throw new BizException(Code.BIZ_ERROR, "修改文章失败");
        // 确认封面图片保存结果
        Result<?> result;
        if (!cobjs.isEmpty())
            if (null == (result = this.cobjsApi.sure(cobjs)) || !result.ok()) {
                if (log.isInfoEnabled())
                    log.info("<<< {}.modifyMedia() cobj sure image faild, {}", this.getClass().getName(),
                            JsonUtil.toJson(result));
                throw new BizException(Code.BIZ_ERROR, "保存封面图片失败");
            }
        // 删除图片
        if (SetUtil.isNotEmpty(oldCovers)) {
            this.delCovers(oldCovers, oldCoverPics);

            if (null == (result = this.cobjsApi.delete(oldCovers)) || !result.ok()) {
                if (log.isInfoEnabled())
                    log.info("<<< {}.modifyText() cobj delete image faild, {}", this.getClass().getName(),
                            JsonUtil.toJson(result));
                throw new BizException(Code.BIZ_ERROR, "删除封面图片失败");
            }
        }
        return Result.OK;
    }

    private void replaceCover(Long id, Set<Cobj> cobjs, long index, String newPath,
                              Map<String, String> saveIconMap, Set<String> oldCovers,
                              Map<String, CoverPictures> oldCoverPics, List<CoverPicturesCreate> coverPictures) {
        String newKey;
        for (CoverPicturesCreate pic : coverPictures) {
            String newIcon = pic.getIcon();
            if (newIcon.contains("com/"))
                newIcon = newIcon.substring(newIcon.indexOf("com/") + 4);
            // 如果旧的路径不包含新的路径，新增图片
            if (!oldCovers.contains(newIcon)) {
                if (saveIconMap.containsKey(newIcon))
                    newKey = saveIconMap.get(newIcon);
                else {
                    newKey = newPath + (++index);
                    cobjs.add(new Cobj(newIcon, newKey));
                    saveIconMap.put(newIcon, newKey);
                }
                // 保存封面图片
                if (!this.repository.createCoverPictures(
                        new CoverPictures(IdUtil.id(), id, newKey, pic.getSortId(), Status.ENABLE)))
                    throw new BizException(Code.BIZ_ERROR, "修改文章失败");
            } else {// 说明不是新增, 判断顺序
                CoverPictures pictures = oldCoverPics.get(newIcon);
                int sortId = pic.getSortId();
                if (pictures.getSortId() != sortId) {
                    pictures.setSortId(sortId);// 相同设置顺序
                    if (!this.repository.editCoverPictures(pictures))// 修改封面图片
                        throw new BizException(Code.BIZ_ERROR, "修改文章失败");
                }
            }
            oldCovers.remove(newIcon);
        }
    }

    /**
     * 获取旧的封面图片的路径
     *
     * @param oldCoverPics 旧封面图集合
     * @param oldCovers    旧封面图路径集合
     * @param oldPicMaps   旧封面图路径和就封面图实体
     */
    private void getOldCoverIcon(List<CoverPictures> oldCoverPics, Set<String> oldCovers,
                                 Map<String, CoverPictures> oldPicMaps) {
        if (null != oldCoverPics && !oldCoverPics.isEmpty()) {
            String icon;
            for (CoverPictures oldPic : oldCoverPics) {
                icon = oldPic.getIcon();
                oldCovers.add(icon);
                oldPicMaps.put(icon, oldPic);
            }
        }
    }

    /**
     * 删除文章
     *
     * @param ids 文章id集合
     * @return 删除结果
     */
    @Override
    @Transactional
    public Result<?> delete(Set<Long> ids) {
        Long uid = SecurityUtil.getId();
        // 获取要删除的封面图片
        List<Articles> articles = this.repository.findByIds(uid, ids);
        if (articles.isEmpty())
            return Result.OK;
        // 收集文章ID
//        List<Long> articleIds = new ArrayList<>();
        // 收集封面图片路径
//        List<String> icon = new ArrayList<>();
//        List<CoverPictures> pictures;
        // 获取与当前用户相匹配的文章ID
        for (Articles article : articles) {
//            Long articleId = article.getArticleId();
            article.setIsEnabled(Status.DISABLE);
//            articleIds.add(articleId);
//            //收集封面图片路径
//            if (null != (pictures = this.repository.queryCoverPictures(articleId))) {
//                for (CoverPictures picture : pictures) {
//                    icon.add(picture.getIcon());
//                }
//            }
            // 删除文章基本信息
//            Type type = article.getType();
            if (!this.repository.editArticles(article))
                throw new BizException(Code.BIZ_ERROR, "删除文章失败");
//            //删除文章详细
//            if (Type.TEXT == type) {
//                if (!this.repository.deleteText(articleId))
//                    throw new BizException(Code.BIZ_ERROR, "删除文章失败");
//            } else if (Type.IMAGE == type) {
//                if (!this.repository.deletePicAritcle(articleId))
//                    throw new BizException(Code.BIZ_ERROR, "删除文章失败");
//                if (!this.repository.deletePicFiles(articleId))
//                    throw new BizException(Code.BIZ_ERROR, "删除文章失败");
//            } else if (Type.VIDEO == type || Type.AUDIO == type) {
//                if (!this.repository.deleteMediaArticle(articleId))
//                    throw new BizException(Code.BIZ_ERROR, "删除文章失败");
//            }
        }
        // 删除文章统计
//        if (!this.repository.deleteCount(articleIds))
//            throw new BizException(Code.BIZ_ERROR, "删除文章失败");
//        //删除文章封面图片
//        if (!icon.isEmpty())
//            if (!this.repository.deleteCover(articleIds))
//                throw new BizException(Code.BIZ_ERROR, "删除文章失败");
        // 删除封面图片记录
        /*
         * if (!icon.isEmpty()) { Result<?> result;//删除图片 if (null == (result =
         * this.cobjsApi.delete(icon)) || !result.ok()) {
         * log.info("<<< {}.delete() cobj delete image faild",
         * this.getClass().getName(), JsonUtil.toJson(result)); throw new
         * BizException(Code.BIZ_ERROR, "删除资讯失败"); } }
         */
        if (log.isInfoEnabled())
            log.info("<<< {}.delete() ids:{}", this.getClass().getName(), SetUtil.toString(ids));
        return Result.OK;
    }

    /**
     * 查询文章分类
     *
     * @param id 文章ID
     * @return List<ArticleColumnClasses>
     */
    @Override
    public List<ArticleColumnClasses> findColClaById(Long id) {
        return this.repository.findColClaById(id);
    }

    /**
     * 资讯审核
     *
     * @param model 修改模型
     * @return Result<?> 审核结果
     */
    @Override
    @Transactional
    public Result<?> review(ArticleReview model) {
        // 取出资讯
        Articles articles;
        Long id = model.getId();
        if (null == (articles = this.repository.findWithUnLock(id)))
            return Result.UNEXIST;

        ArticleStatus status = model.getStatus();
        Type type = articles.getType();
        if (Type.AUDIO == type || Type.VIDEO == type) {
            Integer fileSize = model.getFileSize();
            Integer minutes = model.getMinutes();
            if ((null == fileSize || null == minutes) && ArticleStatus.ISSUE == status)
                return new Result<>(Code.BAD_REQ, "媒体参数无效");
            MediaArticles media = this.repository.findMediaArticlesWithUnLock(id);
            media.setFileSize(fileSize);
            media.setMinutes(minutes);

            if (!this.repository.editMediaArticles(media))
                return new Result<>(Code.BIZ_ERROR, "审核失败");
        }

        // 取出审核人的信息
        Date now = new Date(System.currentTimeMillis());
        ArticleStatus issueStatus = articles.getIssueStatus();
        // 文章状态不为审核中，并且审核状态不为下线， 审核无效
        if (ArticleStatus.CHECK != issueStatus && ArticleStatus.OFFLINE != status)
            return new Result<>(Code.BIZ_ERROR, "审核无效");

        Date verifyTime = articles.getVerifyTime();
        Date issueTime = articles.getIssueTime();
        // 审核时间为空，说明初次审核
        if (null == verifyTime) {
            // 判断发布时间，如果发布时间不为空，审核状态为通过并且当前时间在发布时间之前，设置状态为待发布
            if (null != issueTime && status == ArticleStatus.ISSUE && now.before(issueTime)) {
                status = ArticleStatus.WAIT_ISSUE;
            } else {// 设置发布时间和审核时间
                issueTime = now;
            }
        } else {// 审核时间不为空，说明为二次发布
            if (status == ArticleStatus.ISSUE && now.before(issueTime))
                status = ArticleStatus.WAIT_ISSUE;
        }
        verifyTime = now;
        articles.setVerifyId(SecurityUtil.getId());
        articles.setIssueStatus(status);
        articles.setVerifyTime(verifyTime);
        articles.setIssueTime(issueTime);
        articles.setMtime(now);
        articles.setReason(model.getReason());
        // 审核资讯
        if (!this.repository.review(articles))
            throw new BizException(Code.BIZ_ERROR, "资讯审核失败");

        if (log.isInfoEnabled())
            log.info("<<< {}.identity() id:{}", this.getClass().getName(), id);

        return new Result<>(Code.OK, status == ArticleStatus.REFUSE ? "拒绝操作成功" : "审核通过成功");
    }

    /**
     * 投放服豆
     *
     * @param model 修改参数
     * @return 修改结果
     */
    @Override
    @Transactional
    public Result<?> stock(ArticleStock model) {
        Articles articles;
        Long id = model.getArticleId();

        if (null == (articles = this.repository.findWithUnLock(id)))
            return new Result<>(Code.BIZ_ERROR, "资讯ID无效");

        long stock = model.getStock();
        if (stock > 0) {
            stock = stock * CalcUtil.SCALE;

            Long uid = SecurityUtil.getId();
            // 如果用户是不官方用户， 需要检验扣款
            if (!Constant.OFFICIAL_ACCOUNT.contains(uid)) {
                Result<FlowsDetail> outlay //
                        = this.walletApi.adjust(uid, Way.AWARDOUT, stock * -1, Currency.SDC, id, Channel.APP, null,
                        null);
                if (!outlay.ok())
                    return new Result<>(Code.BIZ_ERROR, outlay.getMessage());
            }
            // 如果全都正确 修改资讯服豆库存、状态和系数
            Result<Float> floatResult = confApi.gainConfRatio(stock);
            articles.setRechargeRatio(floatResult.getData());
            articles.setBeansNum(articles.getBeansNum() + stock);
            articles.setAddInvest(articles.getAddInvest() + stock);
            articles.setInvestStatus(StockStatus.PROCESS);
        }

        Date mtime = new Date(System.currentTimeMillis());
        articles.setMtime(mtime);
        // 设置挖矿开关
        articles.setPraiseSwitch(model.getPraiseSwitch());
        articles.setReadSwitch(model.getReadSwitch());
        articles.setShareSwitch(model.getShareSwitch());
        if (!this.repository.stock(articles))
            return new Result<>(Code.BIZ_ERROR, "投放服豆失败");

        return Result.OK;
    }

    /**
     * 文章设置
     *
     * @param model 修改模型
     * @return 修改结果
     */
    @Override
    @Transactional
    public Result<?> setting(ArticleSetting model) {
        Articles articles;
        Long id = model.getArticleId();
        // 获取原文章
        if (null == (articles = this.repository.findWithUnLock(id)))
            return new Result<>(Code.BIZ_ERROR, "文章ID无效");

        Date mtime = new Date(System.currentTimeMillis());
        Date oldTime = articles.getIssueTime();
        ArticleStatus issueStatus = articles.getIssueStatus();
        Date issueTime = model.getIssueTime();
        // 如果没有设置过发布时间， 那么不能再设置发布时间
        if (null == oldTime && null != issueTime)
            return new Result<>(Code.BAD_REQ, "文章无法修改发布时间");
        if (ArticleStatus.ISSUE == issueStatus && //
                (null != oldTime && null != issueTime && !issueTime.equals(oldTime)))
            return new Result<>(Code.BAD_REQ, "文章无法修改发布时间");

        Articles newArticles = new Articles(id, model.getIsHot(), model.getIsRecommend(), model.getIsTop(),
                model.getIsAuthorized(), model.getReadLevel(), mtime, null, null);
        // 修改分类
        List<ArticleColumnClasses> colClaById = this.repository.findColClaById(id);
        Map<Long, Long> relationId = new HashMap<>();
        // 获取原分类
        if (!colClaById.isEmpty()) {
            for (ArticleColumnClasses articleColumnClasses : colClaById) {
                relationId.put(articleColumnClasses.getClassId(), articleColumnClasses.getRelationId());
            }
        }
        // 判断有没有修改分类(修改分类之后需要重新审核)
        List<Long> modelClassIds = model.getClassIds();
        if (null == modelClassIds)
            modelClassIds = new ArrayList<>();

        for (Long classId : modelClassIds) {
            if (!relationId.containsKey(classId)) {
                if (!this.repository.createArticleColumnClasses(new ArticleColumnClasses(IdUtil.id(), classId, id)))
                    return new Result<>(Code.BIZ_ERROR, "修改失败");
                if (ArticleStatus.DRAFT != issueStatus)
                    newArticles.setIssueStatus(ArticleStatus.CHECK);
            }
            relationId.remove(classId);
        }
        // 如果旧的分类不为空
        if (!relationId.isEmpty()) {
            Collection<Long> relationIds = relationId.values();
            if (!this.repository.deleteArtCla(relationIds))
                throw new BizException(Code.BIZ_ERROR, "修改失败");
            // 如果状态不为草稿，重置状态为审核中
            if (ArticleStatus.DRAFT != issueStatus)
                newArticles.setIssueStatus(ArticleStatus.CHECK);
            newArticles.setApplyTime(mtime);
        }
        // 修改发布时间
        if (null == issueTime)
            newArticles.setIssueTime(oldTime);
        else
            newArticles.setIssueTime(issueTime);

        if (!this.repository.ArticleSetting(newArticles))
            throw new BizException(Code.BIZ_ERROR, "文章设置失败");
        return Result.OK;
    }

    private boolean coverPic(Set<Cobj> cobjs, long index, List<CoverPicturesCreate> coverPictures, Long id,
                             Map<String, String> icon) {
        String newPath = "news/" + id + "/", newKey; // 归档新路径: adv/id/0
        CoverPictures pic;
        for (CoverPicturesCreate cover : coverPictures) {
            String coverIcon = cover.getIcon();
            if (icon.containsKey(coverIcon))
                newKey = icon.get(coverIcon);
            else {
                newKey = newPath + (++index);
                cobjs.add(new Cobj(coverIcon, newKey));
                icon.put(coverIcon, newKey);
            }
            pic = new CoverPictures(IdUtil.id(), id, newKey, cover.getSortId(), Status.ENABLE);
            if (!this.repository.createCoverPictures(pic)) {
                return true;
            }
        }
        // 确认封面图片保存结果
        Result<?> result;
        if (null == (result = this.cobjsApi.sure(cobjs)) || !result.ok()) {
            if (log.isInfoEnabled())
                log.info("<<< {}.create() cobj sure image faild, {}", this.getClass().getName(),
                        JsonUtil.toJson(result));
            return true;
        }
        return false;
    }

    /**
     * 用户文章统计
     *
     * @param uid 用户ID
     * @return UserTotal查询结果
     */
    @Override
    public UserTotal countByUid(Long uid, ArticleStatus articleStatus) {
        return this.repository.countArt(uid, articleStatus);
    }

    /**
     * 收藏、阅读历史
     *
     * @param ids 文章ID集合
     * @return List<ArticlesBO>
     */
    @Override
    public List<ArticlesBO> base(Set<Long> ids) {
        ArticlesQueryVo queryVo //
                = new ArticlesQueryVo(null, ids, null, null, null, null, null, null, null, null, null);
        return this.repository.query(queryVo);
    }

    /**
     * 取消发表/下线
     *
     * @param id 文章ID
     * @return Result<?> 修改结果
     */
    @Override
    @Transactional
    public Result<?> cancel(Long id) {
        // 获取原文章信息
        Articles articles;
        if (null == (articles = this.repository.findWithUnLock(id)))
            return new Result<>(Code.BAD_REQ, "文章ID无效");
        ArticleStatus issueStatus = articles.getIssueStatus();
        // 判断状态
        if (ArticleStatus.CHECK == issueStatus)
            articles.setIssueStatus(ArticleStatus.DRAFT);
        if (ArticleStatus.ISSUE == issueStatus)
            articles.setIssueStatus(ArticleStatus.OFFLINE);
        Date date = new Date(System.currentTimeMillis());
        articles.setMtime(date);
        if (!this.repository.editArticles(articles))
            return new Result<>(Code.BIZ_ERROR, "修改文章失败");
        return Result.OK;
    }

    /**
     * 文章KPI
     *
     * @param model 修改模型
     * @return List<ArticlesBO>
     */
    @Override
    @Transactional
    public Result<?> indicators(ArticleIndicatorsModify model) {
        ArticleIndicators articleIndicators = new ArticleIndicators(model.getArticleId(), model.getShareNum(),
                model.getBrowseNum(), model.getCommentNum(), model.getPraiseNum(), model.getCollectionNum(),
                model.getReward(), model.getRewardProfit());
        if (!this.repository.editArticleIndicators(articleIndicators))
            return new Result<>(Code.BIZ_ERROR, "修改失败");
        return Result.OK;
    }

    /**
     * 根据文章ID扣除服豆库存
     *
     * @param articleId--文章ID
     * @param beansNum--扣除服豆数
     * @return Result<?> 操作结果
     */
    @Override
    @Transactional
    public Result<?> reduceStock(Long articleId, Long beansNum) {
        // 锁定该条数据
        Articles articles;
        if (null == (articles = this.repository.findWithLock(articleId)))
            return Result.UNEXIST;
        Long oldBeansNum = articles.getBeansNum();
        // 判断服豆数量
        if (beansNum > oldBeansNum)
            return new Result<>(Code.BIZ_ERROR, "服豆库存不足");

        if (!this.repository.reduceStock(articleId, beansNum))
            throw new BizException(Code.BIZ_ERROR, "服豆扣除失败");
        return Result.OK;
    }

    @Override
    public Articles getArt(Long id) {
        return this.repository.findArtAllStatus(id);
    }

    @Override
    public UserArticleCount countArticle(Long uid) {
        return this.repository.countArticle(uid);
    }
}
