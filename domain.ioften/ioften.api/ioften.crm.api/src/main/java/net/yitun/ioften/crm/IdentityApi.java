package net.yitun.ioften.crm;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.ioften.crm.conf.Conf;
import net.yitun.ioften.crm.model.identity.IdentityApply;
import net.yitun.ioften.crm.model.identity.IdentityDetail;
import net.yitun.ioften.crm.model.identity.IdentityQuery;
import net.yitun.ioften.crm.model.identity.IdentityReview;

/**
 * <pre>
 * <b>用户 认证接口.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月10日 下午4:13:01 LH
 *         new file.
 * </pre>
 */
public interface IdentityApi {

    /**
     * 简要信息
     *
     * @return Result<IdentityDetail> 简要信息
     */
    @GetMapping(value = Conf.ROUTE //
            + "/identity", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<IdentityDetail> info();

    /**
     * 详细信息
     *
     * @param id ID
     * @return Result<IdentityDetail> 详细信息
     */
    @GetMapping(value = Conf.ROUTE //
            + "/identity/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<IdentityDetail> detail(@PathVariable("id") Long id);

    /**
     * 分页查询
     * 
     * @param query 查询参数
     * @return PageResult<IdentityDetail> 分页结果
     */
    @GetMapping(value = Conf.ROUTE //
            + "/identitys", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageResult<IdentityDetail> query(IdentityQuery query);

    /**
     * 申请认证
     * 
     * @param apply 认证申请
     * @return Result<?> 申请结果
     */
    @PostMapping(value = Conf.ROUTE //
            + "/identity", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> apply(@RequestBody IdentityApply apply);

    /**
     * 申请审批
     * 
     * @param apply 认证申请
     * @return Result<?> 审批结果
     */
    @PutMapping(value = Conf.ROUTE //
            + "/identity/review", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> review(@RequestBody IdentityReview review);

}
