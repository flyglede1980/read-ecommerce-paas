package net.yitun.ioften.cms;

import java.util.Map;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.ioften.cms.conf.Conf;
import net.yitun.ioften.cms.model.show.NewsDetail;
import net.yitun.ioften.cms.model.show.NewsList;
import net.yitun.ioften.cms.model.show.ShowQuery;
import net.yitun.ioften.cms.model.show.UserTotalDetail;

/**
 * <pre>
 * <b>资讯 显示接口.</b>
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
public interface ShowApi {

    /**
     * 资讯内容
     *
     * @param id 资讯ID
     * @return Result<NewsDetail> 内容信息
     */
    @GetMapping(value = Conf.ROUTE //
            + "/show/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<NewsDetail> detail(@PathVariable("id") Long id);

    /**
     * 详情页推荐文章
     *
     * @param id 资讯ID
     * @return Result<NewsDetail> 内容信息
     */
    @GetMapping(value = Conf.ROUTE //
            + "/show/hots/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageResult<NewsList> hots(@PathVariable("id") Long id);

    /**
     * 分页查询
     *
     * @param query 查询参数
     * @return PageResult<NewsList> 查询结果
     */
    @PostMapping(value = Conf.ROUTE //
            + "/shows", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageResult<NewsList> query(@RequestBody ShowQuery query);

    /**
     * 作者资料
     *
     * @param id 作者ID
     * @return Result<UserTotalDetail> 查询结果
     */
    @GetMapping(value = Conf.ROUTE //
            + "/show/author/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<UserTotalDetail> author(@PathVariable("id") Long id);

    /**
     * 简要简要<br/>
     * 适用分享时获取资讯的信息
     *
     * @param id 资讯ID
     * @return Result<NewsList>简要信息
     */
    Result<NewsList> basic(@RequestParam("id") Long id);

    /**
     * 扼要信息
     *
     * @param id 资讯ID
     * @return Result<NewsDetail> 扼要内容
     */
    Result<NewsDetail> major(@PathVariable("id") Long id);

    /**
     * 收藏、阅读记录
     *
     * @param ids 资讯ID集合
     * @return Result<Map<Long, NewsList>> 查询结果
     */
    Result<Map<Long, NewsList>> basics(@RequestParam("ids") Set<Long> ids);

}
