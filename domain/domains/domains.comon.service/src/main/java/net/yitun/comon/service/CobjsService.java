package net.yitun.comon.service;

import java.util.Collection;

import net.yitun.basic.model.Result;
import net.yitun.comon.model.cobj.Cobj;
import net.yitun.comon.model.cobj.CobjDetail;
import net.yitun.comon.model.cobj.CobjModify;
import net.yitun.comon.model.cobj.CobjStream;

/**
 * <pre>
 * <b>云存储服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月20日 上午11:31:04 LH
 *         new file.
 * </pre>
 */
public interface CobjsService {

    /**
     * 获取域名, 根据PATH的路径分析其存储的域名
     * 
     * @param path
     * @return String
     */
    String dns(String path);

    /**
     * 获取路径, 根据PATH的路径分析其存储的域名+PATH
     * 
     * @param path
     * @return String
     */
    String view(String path);

    /**
     * 确认对象
     * 
     * @param objs 待确认对象
     * @return Result<Collection<CobjDetail>> 确认结果
     */
    Result<Collection<CobjDetail>> sure(Collection<Cobj> objs);

    /**
     * 上传对象
     * 
     * @param streams 待上传对象
     * @return Result<Collection<CobjDetail>> 确认结果
     */
    Result<Collection<CobjDetail>> store(Collection<CobjStream> streams);

    /**
     * 解析对象
     * 
     * @param keys 待解析对象
     * @return Result<Collection<CobjDetail>> 解析结果
     */
    Result<Collection<CobjDetail>> parse(Collection<String> keys);

    /**
     * 删除对象
     * 
     * @param keys 待删除对象
     * @return Result<Collection<CobjDetail>> 删除结果
     */
    Result<Collection<CobjDetail>> delete(Collection<String> keys);

    /**
     * 资源编辑
     * 
     * @param model 待编辑对象
     * @return Result<Collection<CobjDetail>> 编辑结果
     */
    Result<Collection<CobjDetail>> modify(CobjModify model);
}
