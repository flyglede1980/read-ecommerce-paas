package net.yitun.ioften.cms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.Code;
import net.yitun.basic.dict.Status;
import net.yitun.basic.dict.YesNo;
import net.yitun.basic.exp.BizException;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.IdUtil;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.ioften.cms.conf.Constant;
import net.yitun.ioften.cms.entity.Category;
import net.yitun.ioften.cms.entity.aritcles.ArticleColumnClasses;
import net.yitun.ioften.cms.model.category.CategoryCreate;
import net.yitun.ioften.cms.model.category.CategoryDetail;
import net.yitun.ioften.cms.model.category.CategoryModify;
import net.yitun.ioften.cms.repository.ArticleRepository;
import net.yitun.ioften.cms.repository.CategoryRepository;
import net.yitun.ioften.cms.service.CategoryService;

/**
 * 文章类目处理服务实现
 *
 * @author Flyglede
 * @since 1.0.0
 */
@Slf4j
@Service("cms.CategoryService")
@CacheConfig(cacheNames = Constant.CKEY_CATE)
public class CategoryServiceImpl implements CategoryService {
    /**
     * 类目管理底层仓库服务
     */
    @Autowired
    protected CategoryRepository repository;
    /**
     * 文章管理底层仓库服务
     */
    @Autowired
    protected ArticleRepository articleRepository;

    /**
     * 类目树
     *
     * @param parentId 类目父ID
     * @return Result<List   <   CategoryDetail>>
     */
    @Override
    public Result<List<CategoryDetail>> treeView(Long parentId) {
        // 查询条件
        List<Category> categories = this.repository.treeView(parentId);
        // 将所有类目组织成一棵树
        Long _parentId = null;
        CategoryDetail child = null, parent = null;

        List<CategoryDetail> nodes = new ArrayList<>();
        Map<Long, CategoryDetail> mapping = new HashMap<>();

        for (Category category : categories) {
            _parentId = category.getParentId();
            // 如果当前非顶级类目且无对应父级节点，则直接忽略当前节点
            if (0L != _parentId //
                    && null == (parent = mapping.get(_parentId)) && !_parentId.equals(parentId))
                continue;
            // 对有效的节点建立映射，方便遍历节点时匹配对应的父级节点
            mapping.put(category.getClassId(), //
                    child = new CategoryDetail(category.getClassId(), category.getParentId(), category.getName(), category.getLevel(),
                            category.getIcon(), category.getDescription(), category.getSortId(), category.getIsEnabled(),
                            category.getCDate(), category.getMDate()));

            if (0L == _parentId || _parentId.equals(parentId))
                nodes.add(child);
            else
                parent.addChild(child);
        }

        return new Result<>(nodes);
    }

    /**
     * 类目详情
     *
     * @param id 类目ID
     * @return Category
     */
    @Override
    public Category detail(Long id) {
        return this.repository.get(id);
    }

    /**
     * 创建文章类目信息
     *
     * @param model--文章类目信息
     * @return Result<?>--创建操作结果
     */
    @Override
    @Transactional
    public Result<?> create(CategoryCreate model) {
        Category parent = null;
        Long usersId = SecurityUtil.getId();
        Integer sortId = 1;
        //判断parentId不为0时，是否有效
        Long parentId = model.getParentId();
        if (0L != parentId && null == (parent = this.repository.get(parentId)))
            return new Result<>(Code.NOT_FOUND, "父级文章类目信息不存在");
        //如果父级ID为0, 则level=1, 其他则再父级的级别上+1
        Integer level = (null == parent) ? 1 : (parent.getLevel() + 1);
        // 生成批量待添加类目的记录
        Set<String> names = model.getNames();
        Date nowTime = new Date(System.currentTimeMillis());
        for (String name : names) {
            long classId = IdUtil.id();
            /**分类代码,后续增加code的生成算法*/
            String code = "";
            /**层级关系*/
            String relation = "";
            /**分类图标*/
            String icon = "";
            /**分类描述*/
            String description = "";
            Status isEnabled = Status.ENABLE;
            YesNo isHot = YesNo.YES;
            YesNo isRecommend = YesNo.YES;
            YesNo isTop = YesNo.YES;
            YesNo isAuthorized = YesNo.YES;
            /**创建分类信息*/
            if (!this.repository.create(new Category(classId, usersId, parentId, code, name, level, relation, icon, description, sortId,
                    isEnabled, isHot, isRecommend, isTop, isAuthorized, nowTime, nowTime)))
                throw new BizException(Code.BIZ_ERROR, "文章类目信息创建失败");
            if (log.isInfoEnabled())
                log.info("<<< {}.create() classId:{}", this.getClass().getName(), classId);
        }
        return Result.OK;
    }

    /**
     * 根据父级类目ID获取并返回文章类目列表信息
     *
     * @param parentId--父级类目ID
     * @return Result<List               <               CategoryDetail>>--文章类目列表信息
     */
    @Override
    public Result<List<CategoryDetail>> getCategoryList(Long parentId) {
        List<Category> lstCategories = null;
        List<CategoryDetail> lstCategoryDetails = new ArrayList<>();
        lstCategories = this.repository.find(parentId);
        if (null != lstCategories && lstCategories.size() > 0) {
            for (Category category : lstCategories) {
                lstCategoryDetails.add(new CategoryDetail(category.getClassId(), category.getParentId(), category.getName(),
                        category.getLevel(), category.getIcon(), category.getDescription(), category.getSortId(), category.getIsEnabled(),
                        category.getCDate(), category.getMDate()));
            }
        }
        if (log.isInfoEnabled())
            log.info("<<< {}.getCategoryList() parentId:{}", this.getClass().getName(), parentId);
        return new Result<>(lstCategoryDetails);
    }

    /**
     * 变更文章类目信息
     *
     * @param modify--文章类目信息
     * @return Result<?>--变更操作结果
     */
    @Override
    @Transactional
    public Result<?> edit(CategoryModify modify) {
        Category category = null;
        Date nowTime = new Date(System.currentTimeMillis());
        Long classId = modify.getClassId();
        Long parentId = modify.getParentId();
        category = this.repository.get(classId);
        if (null == category)
            return new Result<>(Code.NOT_FOUND, "文章类目信息不存在");
        if (0L != parentId && null == this.repository.get(parentId))
            return new Result<>(Code.NOT_FOUND, "父级文章类目信息不存在");
        category.setName(modify.getName());
        category.setParentId(parentId);
        category.setMDate(nowTime);
        if (!this.repository.edit(category))
            throw new BizException(Code.BIZ_ERROR, "文章类目信息变更失败");
        if (log.isInfoEnabled())
            log.info("<<< {}.edit() classId:{}", this.getClass().getName(), classId);
        return Result.OK;
    }

    /**
     * 删除文章类目信息:当该类目下存在文章,先转移;当该类目下有子类目,先转移
     *
     * @param ids--文章类目ID集合
     * @return Result<?>--删除操作结果
     */
    @Override
    @Transactional
    public Result<?> delete(Set<Long> ids) {
        List<ArticleColumnClasses> lstArticleClass = null;
        List<Category> lstCategory = null;
        for (Long classId : ids) {
            lstArticleClass = this.articleRepository.queryClasses(classId);
            lstCategory = this.repository.find(classId);
            if (null != lstArticleClass && lstCategory.size() > 0)
                throw new BizException(Code.BIZ_ERROR, "文章类目信息不能被删除,先将该类目下的所有文章转移后再进行");
            if (null != lstCategory && lstCategory.size() > 0)
                throw new BizException(Code.BIZ_ERROR, "文章类目信息不能被删除,先将该类目下的所有文章子类目转移后再进行");
            if (!this.repository.delete(classId))
                throw new BizException(Code.BIZ_ERROR, "文章类目信息删除失败");
        }
        if (log.isInfoEnabled())
            log.info("<<< {}.delete() classId:{}", this.getClass().getName(), JsonUtil.toJson(ids));
        return Result.OK;
    }

    /**
     * 根据文章ID获取类目
     *
     * @param articleId 文章ID
     * @return List<Category>
     */
    @Override
    public List<Category> getClasses(Long articleId) {
        return this.repository.query(articleId);
    }

}