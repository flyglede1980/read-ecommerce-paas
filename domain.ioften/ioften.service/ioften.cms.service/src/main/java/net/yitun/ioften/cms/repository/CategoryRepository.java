package net.yitun.ioften.cms.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import net.yitun.ioften.cms.entity.Category;

/**
 * 类目信息处理
 * 
 * @since 1.0.0
 * @author Flyglede
 */
@Repository
public interface CategoryRepository {

    /** 栏目分类表 */
    static final String T_CATEGORY = "cms_column_classes";

    /** 文章栏目表 */
    static final String T_ARTICLE_COLUMN_CLASSES = "cms_article_column_classes";

    /** 栏目分类查询字段 */
    static final String T_CATEGORY_COLUMNS = "t1.classid, t1.usersid, t1.parentid, t1.code, t1.name, t1.level, t1.relation, t1.icon, t1.description, t1.sortid, t1.isenabled, t1.ishot, t1.isrecommend, t1.istop, t1.isauthorized, t1.cdate, t1.mdate";

    /**
     * 在未锁定情况下根据分类编码获取栏目分类信息
     * 
     * @param classId--分类编码
     * @return 栏目分类信息
     */
    @ResultType(Category.class)
    @Select("select " + T_CATEGORY_COLUMNS + " from " + T_CATEGORY
            + " t1 where t1.isenabled=1 and t1.classid=#{classId}")
    Category get(Long classId);

    /**
     * 在已锁定情况下根据分类编码获取栏目分类信息
     * 
     * @param classId--分类编码
     * @return 栏目分类信息
     */
    @ResultType(Category.class)
    @Select("select " + T_CATEGORY_COLUMNS + " from " + T_CATEGORY
            + " t1 where t1.isenabled=1 and t1.classid=#{classId} for update")
    Category lock(Long classId);

    /**
     * 根据父级分类ID查询所有子分类列表
     * 
     * @param parentId--父级分类ID
     * @return 所有子分类列表
     */
    @ResultType(Category.class)
    @Select("select " + T_CATEGORY_COLUMNS + " from " + T_CATEGORY
            + " t1 where t1.isenabled=1 and t1.parentid=#{parentId}")
    List<Category> find(Long parentId);

    /**
     * 根据文章ID查询栏目分类列表信息
     * 
     * @param articleId--文章ID
     * @return 栏目分类列表信息
     */
    @ResultType(Category.class)
    @Select("select " + T_CATEGORY_COLUMNS + " from " + T_CATEGORY + " t1," + T_ARTICLE_COLUMN_CLASSES
            + " t2 where t1.classid=t2.classid and t2.articleid=#{articleId}")
    List<Category> query(Long articleId);

    /**
     * 类目树
     * 
     * @param parentId 类目父ID
     * @return List<Category>
     */
    @ResultType(Category.class)
    @Select("<script> select " + T_CATEGORY_COLUMNS + " from " + T_CATEGORY + " t1 where isenabled=1 "
            + "<if test=\"null!=parentId\"> and parentid=#{parentId} </if> "
            + " order by level asc, parentid asc, sortid asc, classid asc </script>")
    List<Category> treeView(@Param("parentId") Long parentId);

    /**
     * 创建栏目分类信息
     * 
     * @param category--栏目分类信息
     * @return true--创建成功;false--创建失败
     */
    @ResultType(Boolean.class)
    @Insert("insert into " + T_CATEGORY
            + "(classid, usersid, parentid, code, name, level, relation, icon, description, sortid, isenabled, ishot, isrecommend, istop, isauthorized, cdate, mdate)"
            + " values(#{classId}, #{usersId}, #{parentId}, #{code}, #{name}, #{level}, #{relation}, #{icon}, #{description}, #{sortId}, #{isEnabled},"
            + "#{isHot}, #{isRecommend}, #{isTop}, #{isAuthorized}, #{cDate}, #{mDate})")
    boolean create(Category category);

    /**
     * 变更栏目分类信息
     * 
     * @param category--栏目分类信息
     * @return true--变更成功;false--变更失败
     */
    @ResultType(Boolean.class)
    @Update("update " + T_CATEGORY
            + " set parentid=#{parentId}, code=#{code}, name=#{name}, level=#{level}, relation=#{relation}, icon=#{icon},"
            + " description=#{description}, mdate=#{mDate} where classid=#{classId}")
    boolean edit(Category category);

    /**
     * 移除栏目分类信息
     * 
     * @param classId--栏目分类编码
     * @return true--变更成功;false--变更失败
     */
    @ResultType(Boolean.class)
    @Update("update " + T_CATEGORY + " set isEnabled=0 where classid=#{classId}")
    boolean delete(Long classId);
}