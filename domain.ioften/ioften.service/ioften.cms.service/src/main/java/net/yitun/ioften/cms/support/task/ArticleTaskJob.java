package net.yitun.ioften.cms.support.task;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.Code;
import net.yitun.basic.exp.BizException;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.basic.utils.SetUtil;
import net.yitun.ioften.cms.dicts.ArticleStatus;
import net.yitun.ioften.cms.entity.aritcles.Articles;
import net.yitun.ioften.cms.entity.aritcles.ArticlesBO;
import net.yitun.ioften.cms.model.article.ArticleQuery;
import net.yitun.ioften.cms.repository.ArticleRepository;
import net.yitun.ioften.cms.service.ArticleService;

@Slf4j
@Component
public class ArticleTaskJob {

    @Autowired
    protected ArticleService articleService;

    @Autowired
    protected ArticleRepository repository;

    @Scheduled(cron = "0 0/5 * * * ? ")
    @Transactional
    public void articleIssue() {
        final Date mtime = new Date(System.currentTimeMillis());
        ArticleQuery articleQuery = new ArticleQuery();
        articleQuery.setPageSize(10);
        articleQuery.setStatus(SetUtil.asSet(ArticleStatus.WAIT_ISSUE));
        Integer no = 0, pages;
        List<ArticlesBO> articles;
        Page<ArticlesBO> page;
        Set<Long> articleIds = new HashSet<>();
        do {
            articleQuery.setPageNo(++no);
            page = this.articleService.query(articleQuery);
            if (null == page || (articles = page.getResult()).isEmpty())
                return;
            for (Articles article : articles) {
                Date issueTime = article.getIssueTime();
                if (issueTime.before(mtime))
                    articleIds.add(article.getArticleId());
            }
            pages = page.getPages();
        } while (no < pages);

        if (SetUtil.isNotEmpty(articleIds)) {
            ArticleTask articleTask;
            for (Long articleId : articleIds) {
                try {
                    articleTask = new ArticleTask(articleId, ArticleStatus.ISSUE, mtime);
                    if (!this.repository.artilceIssue(articleTask))
                        throw new BizException(Code.BIZ_ERROR, "定时发表文章失败：articleId :" + JsonUtil.toJson(articleId));
                } catch (Exception e) {
                    log.error(this.getClass().getName() + ": article issue error :" + e.getMessage());
                }
            }
        }
        log.info("<<< {}.articleIssue() ids:{}", this.getClass().getName(), JsonUtil.toJson(articleIds));
    }
}
