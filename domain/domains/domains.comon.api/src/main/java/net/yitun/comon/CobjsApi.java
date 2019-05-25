package net.yitun.comon;

import java.util.Collection;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import net.yitun.basic.model.Result;
import net.yitun.comon.conf.Conf;
import net.yitun.comon.model.cobj.Cobj;
import net.yitun.comon.model.cobj.CobjDetail;
import net.yitun.comon.model.cobj.CobjModify;
import net.yitun.comon.model.cobj.CobjStream;

/**
 * <pre>
 * <b>通用 云储存储.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月20日 上午11:26:12 LH
 *         new file.
 * </pre>
 */
public interface CobjsApi {

	/**
	 * 获取域名
	 * 
	 * @param path
	 * @return String
	 */
	String dns(String path);

	/**
	 * 获取路径
	 * 
	 * @param path
	 * @return String
	 */
	String view(String path);

	/**
	 * 存储确认
	 * 
	 * @param cobjs 待确认对象
	 * @return Result<Collection<CobjDetail>> 确认结果
	 */
//    @PutMapping(value = Conf.ROUTE //
//            + "/cobjs", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	Result<Collection<CobjDetail>> sure(@RequestBody Cobj... cobjs);

	/**
	 * 存储确认
	 * 
	 * @param cobjs 待确认对象
	 * @return Result<Collection<CobjDetail>> 确认结果
	 */
	Result<Collection<CobjDetail>> sure(Collection<Cobj> cobjs);

	/**
	 * 资源显示
	 * 
	 * @param path 资源路径
	 * @return Result<?> or ModelAndView
	 */
	@GetMapping(value = Conf.ROUTE //
			+ "/cobjs", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	Object show(@RequestParam("path") String path);

	/**
	 * 资源解析
	 * 
	 * @param paths 资源对象
	 * @return Result<Collection<CobjDetail>>
	 */
	@GetMapping(value = Conf.ROUTE //
			+ "/cobjs/parse", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	Result<Collection<CobjDetail>> parse(@RequestParam("path") String... paths);

	/**
	 * 资源解析
	 * 
	 * @param paths 资源对象
	 * @return Result<Collection<CobjDetail>>
	 */
	Result<Collection<CobjDetail>> parse(Set<String> paths);

	/**
	 * 资源存储
	 * 
	 * @param streams 资源对象
	 * @return Result<Collection<CobjDetail>>
	 */
	Result<Collection<CobjDetail>> store(CobjStream... streams);

	/**
	 * 资源存储
	 * 
	 * @param streams 资源对象
	 * @return Result<Collection<CobjDetail>>
	 */
	Result<Collection<CobjDetail>> store(Collection<CobjStream> streams);

	/**
	 * 资源删除
	 * 
	 * @param paths 资源对象
	 * @return Result<Collection<CobjDetail>>
	 */
//    @DeleteMapping(value = Conf.ROUTE //
//            + "/cobjs", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	Result<Collection<CobjDetail>> delete(@RequestParam("path") String... paths);

	/**
	 * 资源删除
	 * 
	 * @param paths 资源对象
	 * @return Result<Collection<CobjDetail>>
	 */
	Result<Collection<CobjDetail>> delete(Collection<String> paths);

	/**
	 * 资源编辑
	 * 
	 * @param model 待编辑对象
	 * @return Result<Collection<CobjDetail>>
	 */
	Result<Collection<CobjDetail>> modify(CobjModify model);

}
