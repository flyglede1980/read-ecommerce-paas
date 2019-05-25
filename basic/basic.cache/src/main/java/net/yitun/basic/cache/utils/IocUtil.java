package net.yitun.basic.cache.utils;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * <b>Ioc 辅助工具.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-15 10:35:08.823
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2017-09-15 10:35:08.823 LH
 *         new file.
 * </pre>
 */
@Slf4j
@Order(0)
@Configuration
public class IocUtil implements ApplicationContextAware {

    /** IOC的 配置 */
    private static Environment ENV = null;

    /** IOC的 上下文 */
    private static ApplicationContext CTX = null;

    /** IOC的 Bean工厂 */
    private static AutowireCapableBeanFactory FACTORY = null;

    /**
     * 构造器 (受保护)
     */
    protected IocUtil() {
        super();
    }

    /**
     * 初始化
     */
    @PostConstruct
    protected IocUtil init() {
        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());
        return this;
    }

    /**
     * 当前是开发模式?
     * 
     * @return boolean true:是; false:否
     */
    public static boolean isDev() {
        String mode = getProfile();
        return null == mode || "dev".equalsIgnoreCase(mode);
    }

    /**
     * 当前是测试模式?
     * 
     * @return boolean true:是; false:否
     */
    public static boolean isTest() {
        String mode = getProfile();
        return null == mode || "test".equalsIgnoreCase(mode);
    }

    /**
     * 当前是演示模式(含开发、测试)?
     * 
     * @return boolean true:是; false:否
     */
    public static boolean isDebug() {
        return isDev() || isTest();
    }

    /**
     * 获取 Context. <br/>
     * ApplicationContext CTX = ContextLoader.getCurrentWebApplicationContext(); // 尝试下这个方法
     * 
     * @return ApplicationContext
     */
    public static ApplicationContext getCtx() {
        asserted();
        return IocUtil.CTX;
    }

    /**
     * 获取系统变量
     * 
     * @param key
     * @return String
     */
    public static String getEnv(String key) {
        return getEnv(key, null);
    }

    /**
     * 获取系统变量
     * 
     * @param key
     * @param value 默认值
     * @return String
     */
    public static String getEnv(String key, String value) {
        if (null == IocUtil.ENV)
            return value;

        return IocUtil.ENV.getProperty(key, value);
    }

    /**
     * 从 Context 获取指定名称的 Bean
     * 
     * @param name 名称
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        asserted();
        return (T) IocUtil.CTX.getBean(name);
    }

    /**
     * 从 Context 获取指定类型的 Bean
     * 
     * @param type 类型
     * @return T
     */
    public static <T> T getBean(Class<T> type) {
        asserted();
        return IocUtil.CTX.getBean(type); // 根据class获取bean对象, 是单个对象, 当有多个是需要使用@Primary
    }

    /**
     * 从 Context 获取指定类型的 Beans
     * 
     * @param type 类型
     * @return <T> Map<String, T>
     */
    public static <T> Map<String, T> getBeans(Class<T> type) {
        asserted();
        return IocUtil.CTX.getBeansOfType(type);
    }

    /**
     * 获取当前Spring 计划的Profile
     * 
     * @return String dev:开发; test:测试
     */
    public static String getProfile() {
        asserted();
        String[] profiles = null;
        Environment env = null;
        if (null == (env = CTX.getEnvironment()) //
                || null == (profiles = env.getActiveProfiles()) || 0 == profiles.length)
            return null;

        return profiles[0];
    }

    /**
     * 从 Context 获取指定属性配置
     * 
     * @param key 属性Key
     * @return String
     */
    public static String getProperty(String key) {
        asserted();
        return IocUtil.CTX.getEnvironment().getProperty(key);
    }

    /**
     * 创建指定class的实例
     * 
     * @param beanClass
     * @param autowireMode
     * @param dependencyCheck
     * @return Object
     */
    public static Object createBean(Class<?> beanClass, int autowireMode, boolean dependencyCheck) {
        if (null == beanClass)
            return null;

        try {
            return IocUtil.FACTORY.createBean(beanClass, autowireMode, dependencyCheck);
        } catch (BeansException e) {
            log.error("<< {} create bean error by {}, {}", IocUtil.class.getName(), beanClass, e.getMessage(), e);
        }

        return null;
    }

    public static void setContext(ApplicationContext context) {
        if (null == IocUtil.CTX)
            log.info(">> {} inject CTX:{}", IocUtil.class.getName(), context.getId());
        else
            log.warn("{} inject new CTX:{}", IocUtil.class.getName(), context.getId());

        IocUtil.CTX = context; // NOSONAR
        IocUtil.ENV = IocUtil.CTX.getEnvironment(); // 获取上下文全局配置
        IocUtil.FACTORY = IocUtil.CTX.getAutowireCapableBeanFactory(); // 获取Bean工厂
    }

    /**
     * 实现 ApplicationContextAware 接口, 将 Context 进行全局持有.
     */
    @Override
    public void setApplicationContext(ApplicationContext context) {
        IocUtil.setContext(context);
    }

    /**
     * 移除对 Context 的全局持有.
     */
    @PreDestroy
    public static void release() {
        IocUtil.CTX = null;
        IocUtil.ENV = null;
        IocUtil.FACTORY = null;
        log.info("{} remove CTX : {}", IocUtil.class.getName(), IocUtil.CTX.getId());
    }

    /**
     * 检查 Context 不为空.
     */
    private static void asserted() {
        Assert.notNull(IocUtil.CTX, "CTX invalid, Please check IocUtil.CTX has inited");
    }

}