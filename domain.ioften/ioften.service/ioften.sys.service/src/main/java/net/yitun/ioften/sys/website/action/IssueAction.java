package net.yitun.ioften.sys.website.action;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.Code;
import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.ioften.sys.IssueApi;
import net.yitun.ioften.sys.entity.Issue;
import net.yitun.ioften.sys.model.issue.IssueDetail;
import net.yitun.ioften.sys.model.issue.IssueQuery;
import net.yitun.ioften.sys.service.IssueService;

@Api(tags = "系统 QA问题")
@RestController("sys.IssueApi")
public class IssueAction implements IssueApi {

    @Autowired
    protected IssueService service;

    @Override
    @ApiOperation(value = "分页查询")
    public PageResult<IssueDetail> query(@Validated IssueQuery query) {
        Page<Issue> page = this.service.query(query);
        return new PageResult<>(page.getTotal(), page.getPages(),
                page.stream().map(issue -> new IssueDetail(issue.getId(), issue.getTitle(), issue.getAnswer(),
                        issue.getSequence(), issue.getCtime(), issue.getMtime())).collect(Collectors.toList()));
    }

    @Override
    @ApiOperation(value = "删除QA")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParam(name = "id", value = "ID, 限制1~1024个", required = true, allowMultiple = true)
    public Result<Integer> delete(@RequestParam("id") Set<Long> ids) {
        if (null == ids || ids.isEmpty() || 1024 < ids.size())
            return new Result<>(Code.BAD_REQ, "ID无效");

        return this.service.delete(ids);
    }

}
