package net.yitun.ioften.cms;

import java.util.List;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import net.yitun.basic.model.Result;
import net.yitun.ioften.cms.conf.Conf;
import net.yitun.ioften.cms.model.category.CategoryCreate;
import net.yitun.ioften.cms.model.category.CategoryDetail;
import net.yitun.ioften.cms.model.category.CategoryModify;

/**
 * <pre>
 * <b>资讯 类目接口.</b>
 * <b>Description:</b>
 *
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月10日 上午10:31:15 LH
 *         new file.
 * </pre>
 */
public interface CategoryApi {

    /**
     * 类目树
     *
     * @param parentId 类目父ID
     * @return Result<List < CategoryDetail>>
     */
    @GetMapping(value = Conf.ROUTE //
            + "/cate/tree", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<List<CategoryDetail>> treeView(@RequestParam(value = "parentId", required = false) Long parentId);

    /**
     * 详细信息
     *
     * @param id ID
     * @return Result<CategoryDetail>
     */
    @GetMapping(value = Conf.ROUTE //
            + "/cate/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<CategoryDetail> detail(@PathVariable("id") Long id);

    /**
     * 根据父ID查子类目列表
     *
     * @param parentId 父级类目ID
     * @return Result<List   <   CategoryDetail>>
     */
//    @GetMapping(value = Conf.ROUTE //
//            + "/cate/parent/{parentId}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<List<CategoryDetail>> list(@PathVariable("parentId") Long parentId);

    /**
     * 新增类目
     *
     * @param model 新建模型
     * @return Result<?> 新建结果
     */
    @PostMapping(value = Conf.ROUTE //
            + "/cate", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> create(@RequestBody CategoryCreate model);

    /**
     * 修改类目
     *
     * @param model 修改模型
     * @return Result<?> 修改结果
     */
    @PutMapping(value = Conf.ROUTE //
            + "/cate", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> modify(@RequestBody CategoryModify model);

    /**
     * 删除类目 </br>
     * 会联动删除子类目
     *
     * @param ids ID清单
     * @return Result<?> 删除结果
     */
    @DeleteMapping(value = Conf.ROUTE //
            + "/cate", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> delete(@RequestParam("ids") Set<Long> ids);

//    /**
//     * 列表查询
//     *
//     * @param query 查询参数
//     * @return Result<List<CategoryDetail>>
//     */
//    @GetMapping(value = Conf.ROUTE //
//            + "/cates", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    Result<List<CategoryDetail>> query(CategoryQuery query);

//    /**
//     * 类目树结构
//     *
//     * @param parentId 父级类目ID
//     * @return Result<List<CategoryDetail>>
//     */
//    @GetMapping(value = Conf.ROUTE //
//            + "/cate/tree", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    Result<List<CategoryDetail>> treeView(@RequestParam(value = "parentId", required = false) Long parentId);

}
