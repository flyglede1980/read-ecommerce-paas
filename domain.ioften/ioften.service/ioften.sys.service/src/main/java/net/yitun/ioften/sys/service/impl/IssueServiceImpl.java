package net.yitun.ioften.sys.service.impl;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.model.Result;
import net.yitun.basic.model.vo.DeleteVo;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.ioften.sys.conf.Constant;
import net.yitun.ioften.sys.entity.Issue;
import net.yitun.ioften.sys.entity.vo.IssueQueryVo;
import net.yitun.ioften.sys.model.issue.IssueQuery;
import net.yitun.ioften.sys.repository.IssueRepository;
import net.yitun.ioften.sys.service.IssueService;

@Slf4j
@Service("sys.IssueService")
@CacheConfig(cacheNames = Constant.CK_ISSUE)
public class IssueServiceImpl implements IssueService {

    @Autowired
    protected IssueRepository repository;

    @Override
    @Cacheable(key = "'page'+#query.pageNo")
    public Page<Issue> query(IssueQuery query) {
        IssueQueryVo queryVo = new IssueQueryVo(query.getId());
        return this.repository.query(queryVo);
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true) // 删除任意一条QA时，缓存全部清空
    public Result<Integer> delete(Set<Long> ids) {
        DeleteVo delVo //
                = new DeleteVo(ids, new Date(System.currentTimeMillis()));

        int count = this.repository.delete(delVo);
        if (log.isInfoEnabled())
            log.info("<<< {}.delete() ids:{}, count:{}", this.getClass().getName(), JsonUtil.toJson(ids), count);

        return new Result<>(count);
    }

}
