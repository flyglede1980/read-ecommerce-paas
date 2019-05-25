package net.yitun.ioften.cms.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import net.yitun.ioften.cms.dicts.StockStatus;
import net.yitun.ioften.cms.entity.aritcles.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

import net.yitun.ioften.cms.dicts.ArticleStatus;
import net.yitun.ioften.cms.entity.UserTotal;
import net.yitun.ioften.cms.repository.vo.ArticlesQueryVo;
import net.yitun.ioften.cms.support.task.ArticleTask;

/**
 * 文章信息数据处理
 * @since 1.0.0
 * @author Flyglede
 */
@Repository
public interface ArticleRepository {
    /**文章信息表*/
    static final String T_ARTICLES = "cms_articles";
    /**图文文章表*/
    static final String T_TEXTARTICLES = "cms_text_articles";
    /**音频视频文章表*/
    static final String T_MEDIAARTICLES = "cms_media_articles";
    /**纯图文章表*/
    static final String T_PICARTICLES = "cms_pic_articles";
    /**封面图表*/
    static final String T_COVERPICTURES = "cms_cover_pictures";
    /**图片文件表*/
    static final String T_PICFILES = "cms_pic_files";
    /**文章统计表*/
    static final String T_ARTICLEINDICATORS = "cms_article_indicators";
    /**文章栏目表*/
    static final String T_ARTICLECOLUMNCLASSES = "cms_article_column_classes";

    /** 栏目分类表 */
    static final String T_CATEGORY = "cms_column_classes";

    /**文章信息表查询字段*/
    static final String T_ARTICLES_COLUMNS = "articleid, parentid, usersid, maintitle, subtitle, summary, keyword, sortid, beansnum, isenabled," +
            "ishot, isrecommend, istop, isauthorized, iscommented, shareswitch, readswitch, praiseswitch, readlevel, cdate, mdate, " +
            "rechargeratio, addinvest, investstatus, issuetime, issuestatus, verifyid, verifytime, type, applytime, reason";
    /**图文文章表查询字段*/
    static final String T_TEXTARTICLES_COLUMNS = "articleid, details, covernum ";
    /**音频视频表查询字段*/
    static final String T_MEDIAARTICLES_COLUMNS = "articleid, linkurl, filesize, minutes, description ";
    /**纯图文章表查询字段*/
    static final String T_PICARTICLES_COLUMNS = "articleid, covernum";
    /**封面图表查询字段*/
    static final String T_COVERPICTURES_COLUMNS = "pictureid, articleid, icon, sortid, isenabled";
    /**图片文件表查询字段*/
    static final String T_PICFILES_COLUMNS = "fileid, articleid, linkurl, description, sortid, isenabled";
    /**文章统计表查询字段*/
    static final String T_ARTICLEINDICATORS_COLUMNS = "articleid, sharenum, browsenum, commentnum, praisenum, collectionnum, reward, rewardprofit";
    /**文章栏目表查询字段*/
    static final String T_ARTICLECOLUMNCLASSES_COLUMNS = "relationid, classid, articleid";

    /**
     * 在未加锁情况下根据文章ID查询并返回已启用(isenabled=1)的文章信息
     * @param id--文章ID
     * @return 文章信息
     */
    @ResultType(Articles.class)
    @Select("select " + T_ARTICLES_COLUMNS + " from " + T_ARTICLES + " where isenabled=1 and articleid=#{id}")
    Articles findWithUnLock(@Param("id") Long id);

    /**
     * 在已加锁情况下根据文章ID查询并返回已启用(isenabled=1)的文章信息
     * @param id--文章ID
     * @return 文章信息
     */
    @ResultType(Articles.class)
    @Select("select " + T_ARTICLES_COLUMNS + " from " + T_ARTICLES + " where isenabled=1 and articleid=#{id} for update")
    Articles findWithLock(@Param("id") Long id);

    /**
     * 在未加锁情况下根据文章ID查询并返回图文文章信息
     * @param id--文章ID
     * @return 图文文章信息
     */
    @ResultType(TextArticles.class)
    @Select("select " + T_TEXTARTICLES_COLUMNS + " from " + T_TEXTARTICLES + " where articleid=#{id}")
    TextArticles findTextArticlesWithUnLock(@Param("id") Long id);

    /**
     * 在已加锁情况下根据文章ID查询并返回图文文章信息
     * @param id--文章ID
     * @return 图文文章信息
     */
    @ResultType(TextArticles.class)
    @Select("select " + T_TEXTARTICLES_COLUMNS + " from " + T_TEXTARTICLES + " where articleid=#{id} for update")
    TextArticles findTextArticlesWithLock(@Param("id") Long id);

    /**
     * 在未加锁情况下根据文章ID查询并返回音视频文章信息
     * @param id--文章ID
     * @return 音视频文章信息
     */
    @ResultType(MediaArticles.class)
    @Select("select " + T_MEDIAARTICLES_COLUMNS + " from " + T_MEDIAARTICLES + " where articleid=#{id}")
    MediaArticles findMediaArticlesWithUnLock(@Param("id") Long id);

    /**
     * 在已加锁情况下根据文章ID查询并返回音视频文章信息
     * @param id--文章ID
     * @return 音视频文章信息
     */
    @ResultType(MediaArticles.class)
    @Select("select " + T_MEDIAARTICLES_COLUMNS + " from " + T_MEDIAARTICLES + " where articleid=#{id} for update")
    MediaArticles findMediaArticlesWithLock(@Param("id") Long id);

    /**
     * 在未加锁情况下根据文章ID查询并返回纯图文章信息
     * @param id--文章ID
     * @return 纯图文章信息
     */
    @ResultType(PicArticles.class)
    @Select("select " + T_PICARTICLES_COLUMNS + " from " + T_PICARTICLES + " where articleid=#{id}")
    PicArticles findPicArticlesWithUnLock(@Param("id") Long id);

    /**
     * 在已加锁情况下根据文章ID查询并返回纯图文章信息
     * @param id--文章ID
     * @return 纯图文章信息
     */
    @ResultType(PicArticles.class)
    @Select("select " + T_PICARTICLES_COLUMNS + " from " + T_PICARTICLES + " where articleid=#{id} for update")
    PicArticles findPicArticlesWithLock(@Param("id") Long id);

    /**
     * 根据文章ID获取已启用(isenabled=1)的文章封面图列表
     * @param id--文章ID
     * @return 封面图列表
     */
    @ResultType(CoverPictures.class)
    @Select("select " + T_COVERPICTURES_COLUMNS + " from " + T_COVERPICTURES + " where isenabled=1 and articleid=#{id} " +
            " order by sortid asc, pictureid asc, articleid asc")
    List<CoverPictures> queryCoverPictures(@Param("id") Long id);

    /**
     * 根据文章ID获取已启用(isenabled=1)的图片文件列表
     * @param id--文章ID
     * @return 图片文件列表
     */
    @ResultType(PicFiles.class)
    @Select("select " + T_PICFILES_COLUMNS + " from " + T_PICFILES + " where isenabled=1 and articleid=#{id}")
    List<PicFiles> queryPicFiles(@Param("id") Long id);


    /**
     * 根据文章ID获取文章统计信息
     * @param id--文章ID
     * @return 文章统计信息
     */
    @ResultType(ArticleIndicators.class)
    @Select("select " + T_ARTICLEINDICATORS_COLUMNS + " from " + T_ARTICLEINDICATORS + " where articleid=#{id}")
    ArticleIndicators queryArticleIndicators(@Param("id") Long id);

    /**
     * 根据栏目ID获取文章栏目列表信息
     * @param classId--栏目ID
     * @return 文章栏目列表信息
     */
    @ResultType(ArticleIndicators.class)
    @Select("select " + T_ARTICLECOLUMNCLASSES_COLUMNS + " from " + T_ARTICLECOLUMNCLASSES + " where classid=#{classId}")
    List<ArticleColumnClasses> queryClasses(@Param("classId") Long classId);

    /**
     * 创建文章信息
     * @param articles--文章信息
     * @return true--创建成功;false--创建失败
     */
    @ResultType(Boolean.class)
    @Insert("insert into " + T_ARTICLES + "( " + T_ARTICLES_COLUMNS + ")"
            + "values(#{articleId}, #{parentId}, #{usersId}, #{mainTitle}, #{subTitle}, #{summary}, #{keyWord}, #{sortId}, #{beansNum}, #{isEnabled},"
            + "#{isHot}, #{isRecommend}, #{isTop}, #{isAuthorized}, #{isCommented}, #{shareSwitch}, #{readSwitch}, #{praiseSwitch}, #{readLevel}, #{ctime}, #{mtime},"
            + "#{rechargeRatio}, #{addInvest}, #{investStatus}, #{issueTime}, #{issueStatus}, #{verifyId}, #{verifyTime}, #{type}, #{applyTime}, #{reason})")
    boolean createArticles(Articles articles);

    /**
     * 创建图文文章信息
     * @param textArticles--图文文章信息
     * @return true--创建成功;false--创建失败
     */
    @ResultType(Boolean.class)
    @Insert("insert into " + T_TEXTARTICLES + "( " + T_TEXTARTICLES_COLUMNS + ")"
            + "values(#{articleId}, #{details}, #{coverNum})")
    boolean createTextArticles(TextArticles textArticles);

    /**
     * 创建音视频文章信息
     * @param mediaArticles--音视频文章信息
     * @return true--创建成功;false--创建失败
     */
    @ResultType(Boolean.class)
    @Insert("insert into " + T_MEDIAARTICLES + "( " + T_MEDIAARTICLES_COLUMNS + ")"
            + "values(#{articleId}, #{linkUrl}, #{fileSize}, #{minutes}, #{description})")
    boolean createMediaArticles(MediaArticles mediaArticles);

    /**
     * 创建纯图文章信息
     * @param picArticles--纯图文章信息
     * @return true--创建成功;false--创建失败
     */
    @ResultType(Boolean.class)
    @Insert("insert into " + T_PICARTICLES + "( " + T_PICARTICLES_COLUMNS + ")"
            + "values(#{articleId}, #{coverNum})")
    boolean createPicArticles(PicArticles picArticles);

    /**
     * 创建封面图片信息
     * @param coverPictures--封面图片信息
     * @return true--创建成功;false--创建失败
     */
    @ResultType(Boolean.class)
    @Insert("insert into " + T_COVERPICTURES + "( " + T_COVERPICTURES_COLUMNS + ")"
            + "values(#{pictureId}, #{articleId}, #{icon}, #{sortId}, #{isEnabled})")
    boolean createCoverPictures(CoverPictures coverPictures);

    /**
     * 创建图片文件信息
     * @param picFiles--图片文件信息
     * @return true--创建成功;false--创建失败
     */
    @ResultType(Boolean.class)
    @Insert("insert into " + T_PICFILES + "( " + T_PICFILES_COLUMNS + ")"
            + "values(#{fileId}, #{articleId}, #{linkUrl}, #{description}, #{sortId}, #{isEnabled})")
    boolean createPicFiles(PicFiles picFiles);

    /**
     * 创建文章栏目信息
     * @param articleColumnClasses--文章栏目信息
     * @return true--创建成功;false--创建失败
     */
    @ResultType(Boolean.class)
    @Insert("insert into " + T_ARTICLECOLUMNCLASSES + "(relationid, classid, articleid)"
            + "values(#{relationId}, #{classId}, #{articleId})")
    boolean createArticleColumnClasses(ArticleColumnClasses articleColumnClasses);

    /**
     * 创建文章统计信息
     * @param articleIndicators--文章统计信息
     * @return true--创建成功;false--创建失败
     */
    @ResultType(Boolean.class)
    @Insert("insert into " + T_ARTICLEINDICATORS + "(articleid, sharenum, browsenum, commentnum, praisenum, collectionnum, " +
            " reward, rewardprofit) values "+
            "(#{articleId}, #{shareNum}, #{browseNum}, #{commentNum}, #{praiseNum}, #{collectionNum}, #{reward}, #{rewardProfit})")
    boolean createArticleIndicators(ArticleIndicators articleIndicators);

    /**
     * 更新文章信息
     * @param articles--文章信息
     * @return true--更新成功;false--更新失败
     */
    @ResultType(Boolean.class)
    @Update("update " + T_ARTICLES + " set parentid=#{parentId}, usersid=#{usersId}, maintitle=#{mainTitle}, subtitle=#{subTitle}, " +
            "summary=#{summary}, keyword=#{keyWord}, sortid=#{sortId}, beansnum=#{beansNum}, isenabled=#{isEnabled}, ishot=#{isHot}, " +
            "isrecommend=#{isRecommend}, istop=#{isTop}, isauthorized=#{isAuthorized}, iscommented=#{isCommented}, " +
            "shareswitch=#{shareSwitch}, readswitch=#{readSwitch}, praiseswitch=#{praiseSwitch}, readlevel=#{readLevel}, " +
            "cdate=#{ctime}, mdate=#{mtime}, rechargeratio=#{rechargeRatio}, addinvest=#{addInvest}, investstatus=#{investStatus}, " +
            "issuetime=#{issueTime}, issuestatus=#{issueStatus}, verifyid=#{verifyId}, verifytime=#{verifyTime}, type=#{type}, applytime=#{applyTime}, reason=#{reason} "  +
            " where articleid=#{articleId}")
    boolean editArticles(Articles articles);

    /**
     * 更新图文文章信息
     * @param textArticles--图文文章信息
     * @return true--更新成功;false--更新失败
     */
    @ResultType(Boolean.class)
    @Update("update " + T_TEXTARTICLES + " set details=#{details}, covernum=#{coverNum} where articleid=#{articleId}")
    boolean editTextArticles(TextArticles textArticles);

    /**
     * 更新纯图文章信息
     * @param picArticles--纯图文章信息
     * @return true--更新成功;false--更新失败
     */
    @ResultType(Boolean.class)
    @Update("update " + T_PICARTICLES + " set covernum=#{coverNum} where articleid=#{articleId}")
    boolean editPicArticles(PicArticles picArticles);

    /**
     * 更新音视频文章信息
     * @param mediaArticles--音视频文章信息
     * @return true--更新成功;false--更新失败
     */
    @ResultType(Boolean.class)
    @Update("update " + T_MEDIAARTICLES + " set linkurl=#{linkUrl}, filesize=#{fileSize}, minutes=#{minutes}, description=#{description} where articleid=#{articleId}")
    boolean editMediaArticles(MediaArticles mediaArticles);

    /**
     * 更新封面图信息
     * @param coverPictures--封面图信息
     * @return true--更新成功;false--更新失败
     */
    @ResultType(Boolean.class)
    @Update("update " + T_COVERPICTURES + " set articleid=#{articleId}, icon=#{icon}, sortid=#{sortId}, isenabled=#{isEnabled} where pictureid=#{pictureId}")
    boolean editCoverPictures(CoverPictures coverPictures);

//    /**
//     * 更新纯图文章图片文件信息
//     * @param picFiles--图片文件信息
//     * @return true--更新成功;false--更新失败
//     */
//    @Update("update " + T_ARTICLEINDICATORS + " set sharenum=#{shareNum}, browsenum=#{browseNum}, rewardprofit=#{shareNum},praisenum=#{shareNum},collectionnum=#{shareNum},commentnum=#{shareNum},reward=#{shareNum},reward=#{shareNum}  where articleid=#{articleId}")
//    boolean editArticleIndicators(ArticleIndicators articleIndicators);

    /**
     * 查询文章统计指标
     *
     * @param id--文章ID
     * @return ArticleIndicators
     */
    @ResultType(ArticleIndicators.class)
    @Select("select " + T_ARTICLEINDICATORS_COLUMNS + " from " + T_ARTICLEINDICATORS + " where articleid=#{id}")
    ArticleIndicators findArticleIndicators(@Param("id") Long id);

    /**
     * 文章审核
     *
     * @param articles 审核模型
     * @return true--更新成功;false--更新失败
     */
    @ResultType(Boolean.class)
    @Update("update " + T_ARTICLES + " set issuetime=#{issueTime}, verifyid=#{verifyId}, verifytime=#{verifyTime}, mdate=#{mtime}, issuestatus=#{issueStatus}, reason=#{reason} " +
            "where articleid=#{articleId} and isenabled=1")
    boolean review(Articles articles);

    /**
     * 投放服豆
     * @param articles 修改模型
     * @return true--更新成功;false--更新失败
     */
    @ResultType(Boolean.class)
    @Update("<script> update " + T_ARTICLES + " set mdate=#{mtime} "
            + "<if test=\"null!=beansNum and 0!=beansNum\"> , beansnum=#{beansNum} </if>"
            + "<if test=\"null!=shareSwitch\"> , shareswitch=#{shareSwitch} </if>"
            + "<if test=\"null!=readSwitch\"> , readswitch=#{readSwitch} </if>"
            + "<if test=\"null!=praiseSwitch\"> , praiseswitch=#{praiseSwitch} </if>"
            + "<if test=\"null!=rechargeRatio and 0!=rechargeRatio\"> , rechargeratio=#{rechargeRatio} </if>"
            + "<if test=\"null!=addInvest\"> , addinvest=#{addInvest} </if>"
            + "<if test=\"null!=investStatus\"> , investstatus=#{investStatus} </if>"
            + " where articleid=#{articleId} and isenabled = 1"
            + "</script>")
    boolean stock(Articles articles);

    /**
     * 查询文章分类
     * @param articleId 文章ID
     * @return ArticleColumnClasses
     */
    @ResultType(ArticleColumnClasses.class)
    @Select("select relationid, classid, articleid from " + T_ARTICLECOLUMNCLASSES + " where articleid=#{articleId}")
    List<ArticleColumnClasses> findColClaById(@Param("articleId") Long articleId);

    /**
     * 文章设置
     * @param newArticles 修改模型
     * @return true--更新成功;false--更新失败
     */
    @ResultType(Boolean.class)
    @Update("<script> update "+ T_ARTICLES +" set mdate=#{mtime} " +
            "<if test=\"null!=isHot\"> , ishot = #{isHot} </if>" +
            "<if test=\"null!=isRecommend\"> , isrecommend = #{isRecommend} </if>" +
            "<if test=\"null!=isTop\"> , istop = #{isTop} </if>" +
            "<if test=\"null!=isAuthorized\"> , isauthorized = #{isAuthorized} </if>" +
            "<if test=\"null!=readLevel\"> , readlevel = #{readLevel} </if>" +
            "<if test=\"null!=issueTime\"> , issuetime = #{issueTime} </if>" +
            "<if test=\"null!=issueStatus\"> , issuestatus = #{issueStatus} </if>" +
            "<if test=\"null!=applyTime\"> , applytime = #{applyTime} </if>" +
            " where articleid=#{articleId}" +
            "</script>")
    boolean ArticleSetting(Articles newArticles);

    /**
     * 根据uid个文章id集合查询文章
     *
     * @param uid 用户ID
     * @param ids 文章ID集合
     * @return List<Articles>
     */
    @ResultType(Articles.class)
    @Select("<script> select "+ T_ARTICLES_COLUMNS +" from "+ T_ARTICLES +" where isenabled=1 and usersid =#{uid} " +
            "<foreach collection=\"ids\" item=\"item\" separator=\", \" open=\" and articleid in (\" close=\")\">#{item}</foreach>" +
            "</script>")
    List<Articles> findByIds(@Param("uid") Long uid, @Param("ids") Set<Long> ids);

    /**
     * 批量删除文章统计
     *
     * @param articleIds 文章ID集合
     * @return true: 成功; false: 失败
     */
    @ResultType(Boolean.class)
    @Delete("<script> delete from "+ T_ARTICLEINDICATORS +" where articleid in " +
            "<foreach collection=\"articleIds\" item=\"item\" separator=\", \" open=\"(\" close=\")\">#{item}</foreach>" +
            "</script>")
    boolean deleteCount(@Param("articleIds") List<Long> articleIds);

    /**
     * 批量删除文章封面图片
     *
     * @param articleIds 文章ID集合
     * @return true: 成功; false: 失败
     */
    @ResultType(Boolean.class)
    @Update("<script> update "+ T_COVERPICTURES +" set isenabled=0  where isenabled=1 " +
            "<foreach collection=\"articleIds\" item=\"item\" separator=\", \" open=\" and articleid in (\" close=\")\">#{item}</foreach>" +
            "</script>")
    boolean deleteCover(@Param("articleIds") Collection<Long> articleIds);

    /**
     * 删除图文文章
     *
     * @param articleId 文章ID
     * @return true: 成功; false: 失败
     */
    @ResultType(Boolean.class)
    @Delete("delete from "+ T_TEXTARTICLES +" where articleid =#{articleId}")
    boolean deleteText(@Param("articleId") Long articleId);

    /**
     * 删除纯图文章
     *
     * @param articleId 文章ID
     * @return true: 成功; false: 失败
     */
    @ResultType(Boolean.class)
    @Delete("delete from "+ T_PICARTICLES +" where articleid =#{articleId}")
    boolean deletePicAritcle(@Param("articleId") Long articleId);

    /**
     * 删除纯图文章详情
     *
     * @param articleId 文章ID
     * @return true: 成功; false: 失败
     */
    @ResultType(Boolean.class)
    @Update("update "+ T_PICFILES +" set isenabled = 0 where isenabled=1 and articleid=#{articleId}")
    boolean deletePicFiles(@Param("articleId") Long articleId);

    /**
     * 删除媒体文章
     *
     * @param articleId 文章ID
     * @return true: 成功; false: 失败
     */
    @ResultType(Boolean.class)
    @Delete("delete from "+ T_MEDIAARTICLES +" where articleid =#{articleId}")
    boolean deleteMediaArticle(@Param("articleId") Long articleId);

/*
    @ResultType(Boolean.class)
    @Update("update " + T_PICFILES + " set articleid=#{articleId}, linkurl=#{linkUrl}, description=#{description}, sortid=#{sortId}, isenabled=#{isEnabled} where fileid=#{pictureId}")
    boolean editPicFiles(PicFiles picFiles);

    /**
     * 更新媒体文件信息
     * @param mediaFiles--媒体文件信息
     * @return true--更新成功;false--更新失败
     */
//    @ResultType(Boolean.class)
//    @Update("update " + T_COVERPICTURES + " set articleid=#{articleId}, linkurl=#{linkUrl}, filesize=#{fileSize}, minutes=#{minutes}, description=#{description}, sortid=#{sortId}, isenabled=#{isEnabled} where fileid=#{fileId}")
//    boolean editMediaFiles(MediaFiles mediaFiles);

    /**
     * 更新文章统计信息
     * @param articleIndicators--文章统计信息
     * @return true--更新成功;false--更新失败
     */
    @ResultType(Boolean.class)
    @Update("update " + T_ARTICLEINDICATORS + " set sharenum=#{shareNum}, browsenum=#{browseNum}, commentnum=#{commentNum}, praisenum=#{praiseNum},collectionnum=#{collectionNum}, reward=#{reward}, rewardprofit=#{rewardProfit}  where articleid=#{articleId}")
    boolean editArticleIndicators(ArticleIndicators articleIndicators);

    /**
     * 分页查询并获取所有文章信息
     *
     * @param queryVo 筛选条件
     * @return Page<ArticlesBO> 分页列表
     */
    @ResultType(ArticlesBO.class)
/*    @Select("<script>"
            + "SELECT articleid, parentid, usersid, maintitle, subtitle, summary, keyword, sortid, beansnum," //
            + " isenabled, ishot, isrecommend, istop, isauthorized, iscommented, shareswitch, readswitch, praiseswitch, readlevel,"
            + " cdate, mdate, rechargeratio, addinvest, investstatus, issuetime, issuestatus, verifyid, verifytime, articletype,"
            + " browsenum, collectionnum, commentnum, praisenum, reward, rewardprofit, sharenum"
            + " FROM ("

            + "SELECT null articleid,null parentid,null usersid,null maintitle,null subtitle,null summary,NULL keyword,null sortid,null beansnum," //
            + "null isenabled,null ishot,null isrecommend,null istop,null isauthorized,null iscommented,null shareswitch,null readswitch,null praiseswitch,null readlevel,"
            + "null cdate,null mdate,null rechargeratio,null addinvest,null investstatus,null issuetime,null issuestatus,null verifyid,null verifytime,null articletype,"
            + "null browsenum,null collectionnum,null commentnum,null praisenum,null reward,null rewardprofit,null sharenum from dual "
            *//**选择指定文章类型的文章信息查询*//*
            + " <if test=\"null!=articleType and 0&lt;articleType\">"
            + " UNION ALL "
            *//**图文文章信息查询*//*
            + " <if test=\"1==articleType\">"
            + " SELECT t1.articleid,t1.parentid,t1.usersid,t1.maintitle,t1.subtitle,t1.summary,t1.keyword,t1.sortid,t1.beansnum,"
            + "t1.isenabled,t1.ishot,t1.isrecommend,t1.istop,t1.isauthorized,t1.iscommented,t1.shareswitch,t1.readswitch,t1.praiseswitch,t1.readlevel,"
            + "t1.cdate,t1.mdate,t1.rechargeratio,t1.addinvest,t1.investstatus,t1.issuetime,t1.issuestatus,t1.verifyid,t1.verifytime,1 articletype,"
            + "a.browsenum,a.collectionnum,a.commentnum,a.praisenum,a.reward,a.rewardprofit,a.sharenum"
            + " FROM articles t1,articleindicators a,textarticles t2"
            + " WHERE t1.articleid = a.articleid AND t1.articleid = t2.articleid"
            + " </if>"
            *//**纯图文章信息查询*//*
            + " <if test=\"2==articleType\">"
            + " SELECT t3.articleid,t3.parentid,t3.usersid,t3.maintitle,t3.subtitle,t3.summary,t3.keyword,t3.sortid,t3.beansnum,"
            + "t3.isenabled,t3.ishot,t3.isrecommend,t3.istop,t3.isauthorized,t3.iscommented,t3.shareswitch,t3.readswitch,t3.praiseswitch,t3.readlevel,"
            + "t3.cdate,t3.mdate,t3.rechargeratio,t3.addinvest,t3.investstatus,t3.issuetime,t3.issuestatus,t3.verifyid,t3.verifytime,2 articletype,"
            + "b.browsenum,b.collectionnum,b.commentnum,b.praisenum,b.reward,b.rewardprofit,b.sharenum"
            + " FROM articles t3,articleindicators b,picarticles t4"
            + " WHERE t3.articleid = b.articleid AND t3.articleid = t4.articleid"
            + " </if>"
            *//**媒体文章信息查询*//*
            + " <if test=\"3==articleType\">"
            + " SELECT t5.articleid,t5.parentid,t5.usersid,t5.maintitle,t5.subtitle,t5.summary,t5.keyword,t5.sortid,t5.beansnum,"
            + "t5.isenabled,t5.ishot,t5.isrecommend,t5.istop,t5.isauthorized,t5.iscommented,t5.shareswitch,t5.readswitch,t5.praiseswitch,t5.readlevel,"
            + "t5.cdate,t5.mdate,t5.rechargeratio,t5.addinvest,t5.investstatus,t5.issuetime,t5.issuestatus,t5.verifyid,t5.verifytime,3 articletype,"
            + "c.browsenum,c.collectionnum,c.commentnum,c.praisenum,c.reward,c.rewardprofit,c.sharenum"
            + " FROM articles t5,articleindicators c,mediaarticles t6"
            + " WHERE t5.articleid = c.articleid AND t5.articleid = t6.articleid"
            + " </if>"
            + " </if>"
            *//**未选择任何文章类型查询所有文章*//*
            + " <if test=\"null==articleType or 0==articleType\">"
            + " UNION ALL "
            + "SELECT t1.articleid,t1.parentid,t1.usersid,t1.maintitle,t1.subtitle,t1.summary,t1.keyword,t1.sortid,t1.beansnum,"
            + "t1.isenabled,t1.ishot,t1.isrecommend,t1.istop,t1.isauthorized,t1.iscommented,t1.shareswitch,t1.readswitch,t1.praiseswitch,t1.readlevel,"
            + "t1.cdate,t1.mdate,t1.rechargeratio,t1.addinvest,t1.investstatus,t1.issuetime,t1.issuestatus,t1.verifyid,t1.verifytime,1 articletype,"
            + "a.browsenum,a.collectionnum,a.commentnum,a.praisenum,a.reward,a.rewardprofit,a.sharenum"
            + " FROM articles t1,articleindicators a,textarticles t2"
            + " WHERE t1.articleid = a.articleid AND t1.articleid = t2.articleid"

            + " UNION ALL "
            + "SELECT t3.articleid,t3.parentid,t3.usersid,t3.maintitle,t3.subtitle,t3.summary,t3.keyword,t3.sortid,t3.beansnum,"
            + "t3.isenabled,t3.ishot,t3.isrecommend,t3.istop,t3.isauthorized,t3.iscommented,t3.shareswitch,t3.readswitch,t3.praiseswitch,t3.readlevel,"
            + "t3.cdate,t3.mdate,t3.rechargeratio,t3.addinvest,t3.investstatus,t3.issuetime,t3.issuestatus,t3.verifyid,t3.verifytime,2 articletype,"
            + "b.browsenum,b.collectionnum,b.commentnum,b.praisenum,b.reward,b.rewardprofit,b.sharenum"
            + " FROM articles t3,articleindicators b,picarticles t4"
            + " WHERE t3.articleid = b.articleid AND t3.articleid = t4.articleid"

            + " UNION ALL "
            + "SELECT t5.articleid,t5.parentid,t5.usersid,t5.maintitle,t5.subtitle,t5.summary,t5.keyword,t5.sortid,t5.beansnum,"
            + "t5.isenabled,t5.ishot,t5.isrecommend,t5.istop,t5.isauthorized,t5.iscommented,t5.shareswitch,t5.readswitch,t5.praiseswitch,t5.readlevel,"
            + "t5.cdate,t5.mdate,t5.rechargeratio,t5.addinvest,t5.investstatus,t5.issuetime,t5.issuestatus,t5.verifyid,t5.verifytime,3 articletype,"
            + "c.browsenum,c.collectionnum,c.commentnum,c.praisenum,c.reward,c.rewardprofit,c.sharenum"
            + " FROM articles t5,articleindicators c,mediaarticles t6"
            + " WHERE t5.articleid = c.articleid AND t5.articleid = t6.articleid"
            + " </if>"

            + " ) t  where isenabled=1 "
            + " <if test=\"null!=articleId and 0&lt;articleId\">and articleid=#{articleId} </if>" //
            + " <if test=\"null!=mainTitle and ''!=mainTitle\">and maintitle like #{mainTitle}</if>" //
            + " <if test=\"null!=stime and ''!=stime\">and verifytime>&gt;{stime}</if>" //
            + " <if test=\"null!=etime and ''!=etime\">and verifytime&lt;{etime}</if>" //
            + " <if test=\"null!=aIds and 0!=aIds.size()\">"
            + "    <foreach collection=\"aIds\" item=\"item\" separator=\", \" open=\"and articleid in (\" close=\")\">#{item}</foreach></if>"
            + " <if test=\"null!=uids and 0!=uids.size()\">"
            + "    <foreach collection=\"uids\" item=\"item\" separator=\", \" open=\"and usersid in (\" close=\")\">#{item}</foreach></if>"
            + " <if test=\"null!=stockStatus and 0!=stockStatus.size()\">" //
            + "    <foreach collection=\"stockStatus\" item=\"item\" separator=\", \" open=\"and investstatus in (\" close=\")\">#{item}</foreach></if> </script>")*/
//    @Select("<script>"
//            + "SELECT articleid, parentid, usersid, maintitle, subtitle, summary, keyword, sortid, beansnum," //
//            + " isenabled, ishot, isrecommend, istop, isauthorized, iscommented, shareswitch, readswitch, praiseswitch, readlevel,"
//            + " cdate, mdate, rechargeratio, addinvest, investstatus, issuetime, issuestatus, verifyid, verifytime, articletype,"
//            + " browsenum, collectionnum, commentnum, praisenum, reward, rewardprofit, sharenum"
//            + " FROM ("
//
//
//            + " SELECT t1.articleid,t1.parentid,t1.usersid,t1.maintitle,t1.subtitle,t1.summary,t1.keyword,t1.sortid,t1.beansnum,"
//            + "t1.isenabled,t1.ishot,t1.isrecommend,t1.istop,t1.isauthorized,t1.iscommented,t1.shareswitch,t1.readswitch,t1.praiseswitch,t1.readlevel,"
//            + "t1.cdate,t1.mdate,t1.rechargeratio,t1.addinvest,t1.investstatus,t1.issuetime,t1.issuestatus,t1.verifyid,t1.verifytime, t1.type,"
//            + "a.browsenum,a.collectionnum,a.commentnum,a.praisenum,a.reward,a.rewardprofit,a.sharenum"
//            + " FROM articles t1,articleindicators a"
//            + " WHERE t1.articleid = a.articleid"
//
//            + " ) t  where isenabled=1 "
//
//            + " <if test=\"null!=type and 0&lt;type\"> and type=#{type} </if>"
//            + " <if test=\"null!=articleId and 0&lt;articleId\">and articleid=#{articleId} </if>" //
//            + " <if test=\"null!=mainTitle and ''!=mainTitle\">and maintitle like #{mainTitle}</if>" //
//            + " <if test=\"null!=stime and ''!=stime\">and verifytime>&gt;{stime}</if>" //
//            + " <if test=\"null!=etime and ''!=etime\">and verifytime&lt;{etime}</if>" //
//            + " <if test=\"null!=aIds and 0!=aIds.size()\">"
//            + "    <foreach collection=\"aIds\" item=\"item\" separator=\", \" open=\"and articleid in (\" close=\")\">#{item}</foreach></if>"
//            + " <if test=\"null!=uids and 0!=uids.size()\">"
//            + "    <foreach collection=\"uids\" item=\"item\" separator=\", \" open=\"and usersid in (\" close=\")\">#{item}</foreach></if>"
//            + " <if test=\"null!=stockStatus and 0!=stockStatus.size()\">" //
//            + "    <foreach collection=\"stockStatus\" item=\"item\" separator=\", \" open=\"and investstatus in (\" close=\")\">#{item}</foreach></if> </script>")
//    Page<ArticlesBO> query(ArticlesQueryVo queryVo);

    @Select("<script>"
            + "SELECT articleid, parentid, usersid, maintitle, subtitle, summary, keyword, sortid, beansnum," //
            + " isenabled, ishot, isrecommend, istop, isauthorized, iscommented, shareswitch, readswitch, praiseswitch, readlevel,"
            + " cdate, mdate, rechargeratio, addinvest, investstatus, issuetime, issuestatus, verifyid, verifytime, type, applytime, "
            + " browsenum, collectionnum, commentnum, praisenum, reward, rewardprofit, sharenum "
            + " FROM ("


            + " SELECT distinct t1.articleid,t1.parentid,t1.usersid,t1.maintitle,t1.subtitle,t1.summary,t1.keyword,t1.sortid,t1.beansnum,"
            + " t1.isenabled,t1.ishot,t1.isrecommend,t1.istop,t1.isauthorized,t1.iscommented,t1.shareswitch,t1.readswitch,t1.praiseswitch,t1.readlevel,"
            + " t1.cdate,t1.mdate,t1.rechargeratio,t1.addinvest,t1.investstatus,t1.issuetime,t1.issuestatus,t1.verifyid,t1.verifytime, t1.type, t1.applytime, "
            + " a.browsenum,a.collectionnum,a.commentnum,a.praisenum,a.reward,a.rewardprofit,a.sharenum "
            + " FROM " + T_ARTICLES + " t1, " + T_ARTICLEINDICATORS + " a " +
            "<if test=\"null!=classIds and 0!=classIds.size()\"> , "+ T_ARTICLECOLUMNCLASSES +" b ,"+ T_CATEGORY +" c </if>"
            + " WHERE t1.articleid = a.articleid "
            + " <if test=\"null!=classIds and 0!=classIds.size()\"> AND t1.articleid = b.articleid and b.classid = c.classid  "
            + "<foreach collection=\"classIds\" item=\"item\" separator=\", \" open=\" and b.classid in (\" close=\")\">#{item}</foreach>" +
            "   <if test=\"null!=aid and 0!=aid\"> and b.articleid!=#{aid} </if></if>"
            + " ) t where 1=1 <if test=\"null!=enable\"> and isenabled=#{enable} </if> "

            + " <if test=\"null!=type and ''!=type\"> and type=#{type} </if>"
//            + " <if test=\"null!=articleId and 0&lt;articleId\"> and articleid=#{articleId} </if>" //
            + " <if test=\"null!=articleIds and 0!=articleIds.size()\">"
            + "    <foreach collection=\"articleIds\" item=\"item\" separator=\", \" open=\" and articleid in (\" close=\")\">#{item}</foreach></if>"
            
            + " <if test=\"null!=mainTitle and ''!=mainTitle\"> and maintitle like #{mainTitle}</if>" //
            + " <if test=\"null!=stime\"> and issuetime&gt;#{stime} </if>" //
            + " <if test=\"null!=etime\"> and issuetime&lt;#{etime} </if>" //
            + " <if test=\"null!=uids and 0!=uids.size()\">"
            + "    <foreach collection=\"uids\" item=\"item\" separator=\", \" open=\" and usersid in (\" close=\")\">#{item}</foreach></if>"
            + " <if test=\"null!=stockStatus and 0!=stockStatus.size()\">" //
            + "    <foreach collection=\"stockStatus\" item=\"item\" separator=\", \" open=\" and investstatus in (\" close=\")\">#{item}</foreach></if>"
            + " <if test=\"null!=status and 0!=status.size()\">" //
            + "    <foreach collection=\"status\" item=\"item\" separator=\", \" open=\" and issuestatus in (\" close=\")\">#{item}</foreach></if>"
            + " </script>")
    Page<ArticlesBO> query(ArticlesQueryVo queryVo);



    /**
     * 删除文章栏目
     * @param relationIds 关系编号集合
     * @return boolean
     */
    @ResultType(Boolean.class)
    @Delete("<script> delete from "+ T_ARTICLECOLUMNCLASSES +" where relationid in " +
            "<foreach collection=\"relationIds\" item=\"item\" separator=\", \" open=\" (\" close=\")\">#{item}</foreach>" +
            "</script>")
    boolean deleteArtCla(@Param("relationIds") Collection<Long> relationIds);

    /**
     * 统计文章数
     * @param uid 用户ID
     * @param articleStatus 文章状态
     * @return Integer
     */
    @ResultType(Integer.class)
    @Select("select count(t2.articleid) article_total, sum(t2.rewardprofit) reward_total " +
            "from "+ T_ARTICLES +" t1 inner join "+ T_ARTICLEINDICATORS +" t2 " +
            "where t2.articleid = t1.articleid and t1.issuestatus = #{articleStatus} and t1.usersid = #{uid}")
    UserTotal countArt(@Param("uid") Long uid, @Param("articleStatus") ArticleStatus articleStatus);

    /**
     * 根据图片ID删除封面图片
     * @param picIds 图片ID集合
     * @return boolean
     */
    @ResultType(Boolean.class)
    @Update("<script> update "+ T_COVERPICTURES +" set isenabled=0 where isenabled=1 and pictureid in " +
            "<foreach collection=\"picIds\" item=\"item\" separator=\",\" open=\" (\" close=\")\">#{item}</foreach>" +
            "</script>")
    boolean deleteCoverByPicId(@Param("picIds") List<Long> picIds);

    /**
     * 修改纯图文件
     * @param picFiles 修改模型
     * @return true——成功  false——失败
     */
    @ResultType(Boolean.class)
    @Update("update "+ T_PICFILES +" set description=#{description}, sortid=#{sortId} where fileid=#{fileId} and articleid=#{articleId} and isenabled=1")
    boolean editPicFile(PicFiles picFiles);

    /**
     * 批量删除图片文件
     * @param fileIds 纯图文件ID集合
     * @return true——成功  false——失败
     */
    @ResultType(Boolean.class)
    @Update("<script> update "+ T_PICFILES +" set isenabled=0 where isenabled=1 and fileid in " +
            "<foreach collection=\"fileIds\" item=\"item\" separator=\", \" open=\" (\" close=\")\">#{item}</foreach>" +
            "</script>")
    boolean delPicFilesByFileIds(@Param("fileIds") List<Long> fileIds);

    /**
     * 根据文章ID扣除服豆库存
     * @param articleId--文章ID
     * @param beansNum--扣除服豆数
     * @return 操作结果
     */
    @ResultType(Boolean.class)
    @Update(" update " + T_ARTICLES + " set beansnum=beansnum-#{beansNum} where isenabled=1 and articleid=#{articleId}")
    boolean reduceStock(@Param("articleId") Long articleId, @Param("beansNum") Long beansNum);

    @ResultType(Boolean.class)
    @Update("update " + T_ARTICLES + " set mdate=#{mtime}, issuestatus=#{status} where isenabled=1 and articleid=#{articleId}")
    boolean artilceIssue(ArticleTask articleTask);

    /**
     * 获取所有状态的文章
     * @param id 文章ID
     * @return Articles
     */
    @ResultType(Articles.class)
    @Select("select "+ T_ARTICLES_COLUMNS +" from "+ T_ARTICLES +" where articleid=#{id}")
    Articles findArtAllStatus(@Param("id") Long id);

    @ResultType(UserArticleCount.class)
    @Select("select count(b.articleid) articleTotal ,sum(b.sharenum) shareTotal ,sum(b.browsenum) browseTotal , " +
            "sum(b.commentnum) commentTotal ,sum(b.praisenum) praiseTotal ,sum(b.collectionnum) collectionTotal ," +
            "sum(b.reward) rewardTotal ,sum(b.rewardprofit) rewardProfitTotal from cms_articles t1 inner join cms_article_indicators b " +
            "where t1.usersid = #{uid} and t1.articleid = b.articleid ")
    UserArticleCount countArticle(@Param("uid") Long uid);
}