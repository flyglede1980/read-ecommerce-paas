package net.yitun.ioften.cms.service;

import java.util.List;
import java.util.Set;

import net.yitun.basic.model.Result;
import net.yitun.ioften.cms.entity.Category;
import net.yitun.ioften.cms.model.category.CategoryCreate;
import net.yitun.ioften.cms.model.category.CategoryDetail;
import net.yitun.ioften.cms.model.category.CategoryModify;

/**
 * 文章类目处理服务定义
 *
 * @author Flyglede
 * @see CategoryCreate
 * @since 1.0.0
 */
public interface CategoryService {

    /**
     * 类目树
     *
     * @param parentId 类目父ID
     * @return Result<List   <   CategoryDetail>>
     */
    Result<List<CategoryDetail>> treeView(Long parentId);

    /**
     * 创建文章类目
     *
     * @param model--文章类目信息
     * @return Result<?> 创建操作结果
     */
    Result<?> create(CategoryCreate model);

    /**
     * 变更文章类目
     *
     * @param modify--文章类目信息
     * @return Result<?> 变更操作结果
     */
    Result<?> edit(CategoryModify modify);

    /**
     * 根据类目ID删除文章类目信息
     *
     * @param ids--文章类目ID
     * @return Result<?> 删除操作结果
     */
    Result<?> delete(Set<Long> ids);

    /**
     * 根据父级类目ID获取并返回所有文章类目列表信息
     *
     * @param parentId--父级类目ID
     * @return Result<List   <   CategoryDetail>> 文章类目列表信息
     */
    Result<List<CategoryDetail>> getCategoryList(Long parentId);

    /**
     * 类目详情
     *
     * @param id 类目ID
     * @return Category
     */
    Category detail(Long id);

    /**
     * 根据文章ID获取类目
     *
     * @param articleId 文章ID
     * @return List<Category>
     */
    List<Category> getClasses(Long articleId);

}