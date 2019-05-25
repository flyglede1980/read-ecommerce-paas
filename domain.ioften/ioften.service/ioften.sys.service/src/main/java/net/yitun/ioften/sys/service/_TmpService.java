package net.yitun.ioften.sys.service;

import java.util.Set;

import com.github.pagehelper.Page;

import net.yitun.basic.model.Result;
import net.yitun.ioften.sys.entity._Tmp;
import net.yitun.ioften.sys.model._tmp._TmpCreate;
import net.yitun.ioften.sys.model._tmp._TmpModify;
import net.yitun.ioften.sys.model._tmp._TmpQuery;

/**
 * <pre>
 * <b>Tmp 服务.</b>
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
public interface _TmpService {

    /**
     * 详情信息
     * 
     * @param id ID
     * @return _TmpDetail 详情信息
     */
    _Tmp get(Long id);

    /**
     * 分页查询
     * 
     * @param query 筛选参数
     * @return Page<_TmpDetail> 分页结果
     */
    Page<_Tmp> query(_TmpQuery query);

    /**
     * 新建Tmp
     * 
     * @param model 修建模型
     * @return Result<?> 新建结果
     */
    Result<?> create(_TmpCreate model);

    /**
     * 修改Tmp
     * 
     * @param model 修改模型
     * @return Result<?> 修改结果
     */
    Result<?> modify(_TmpModify model);

    /**
     * 删除Tmp
     * 
     * @param ids ID清单
     * @return Result<?> 删除结果
     */
    Result<?> delete(Set<Long> ids);

}
