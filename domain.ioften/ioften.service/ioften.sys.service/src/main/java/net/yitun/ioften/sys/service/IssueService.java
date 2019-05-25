package net.yitun.ioften.sys.service;

import java.util.Set;

import com.github.pagehelper.Page;

import net.yitun.basic.model.Result;
import net.yitun.ioften.sys.entity.Issue;
import net.yitun.ioften.sys.model.issue.IssueQuery;

/**
 * <pre>
 * <b>系统 QA服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月6日 上午11:58:08 LH
 *         new file.
 * </pre>
 */
public interface IssueService {

    /**
     * 分页查询
     * 
     * @param query 筛选参数
     * @return Page<Issue> 分页结果
     */
    Page<Issue> query(IssueQuery query);

    /**
     * 删除QA
     * 
     * @param ids ID清单
     * @return Result<Integer> 删除结果
     */
    Result<Integer> delete(Set<Long> ids);

}
