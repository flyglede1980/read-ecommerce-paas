package net.yitun.ioften.sys;

import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.ioften.sys.conf.Conf;
import net.yitun.ioften.sys.model.issue.IssueDetail;
import net.yitun.ioften.sys.model.issue.IssueQuery;

/**
 * <pre>
 * <b>系统 QA问题.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月6日 上午10:07:02 LH
 *         new file.
 * </pre>
 */
public interface IssueApi {

    /**
     * 分页查询
     * 
     * @param query 查询参数
     * @return PageResult<IssueDetail> 分页结果
     */
    @GetMapping(value = Conf.ROUTE //
            + "/issues", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageResult<IssueDetail> query(IssueQuery query);

    /**
     * 删除QA
     * 
     * @param ids ID清单
     * @return Result<Integer> 删除结果
     */
    @DeleteMapping(value = Conf.ROUTE //
            + "/issue", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<Integer> delete(@RequestParam("id") Set<Long> ids);

}
