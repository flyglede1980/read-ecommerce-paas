package net.yitun.ioften.cms;

import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.ioften.cms.conf.Conf;
import net.yitun.ioften.cms.model.browse.BrowseDetail;
import net.yitun.ioften.cms.model.browse.BrowseQuery;

/**
 * <pre>
 * <b>资讯 浏览记录.</b>
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
public interface BrowseApi {

    /**
     * 浏览信息
     * 
     * @param aid 文章ID
     * @return Result<BrowseDetail> 浏览信息
     */
    @GetMapping(value = { Conf.ROUTE + "/browse/{aid}", Conf.ROUTE + "/browse/byarticle/{aid}" } //
            , consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<BrowseDetail> state(@PathVariable("aid") Long aid);

    /**
     * 浏览历史
     *
     * @param query 查询参数
     * @return PageResult<BrowseDetail> 查询结果
     */
    @GetMapping(value = Conf.ROUTE //
            + "/browses", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageResult<BrowseDetail> query(BrowseQuery query);

    /**
     * 删除记录
     * 
     * @param ids 记录ID
     * @return Result<?> 删除结果
     */
    @DeleteMapping(value = Conf.ROUTE //
            + "/browse", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> delete(@RequestParam("id") Set<Long> ids);

}
