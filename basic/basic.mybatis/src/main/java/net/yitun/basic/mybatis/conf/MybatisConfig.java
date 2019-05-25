package net.yitun.basic.mybatis.conf;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.mybatis.support.EnumAutoTypeHandler;

/**
 * <pre>
 * <b>DB 配置.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月12日 上午11:29:38 LH
 *         new file.
 * </pre>
 */
@Slf4j
@Configuration
public class MybatisConfig extends MybatisAutoConfiguration {

    @PostConstruct
    protected void init() {
        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());

//        org.springframework.transaction.interceptor.TransactionInterceptor
    }

    public MybatisConfig(MybatisProperties properties, ObjectProvider<Interceptor[]> interceptorsProvider,
            ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider,
            ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
        super(properties, interceptorsProvider, resourceLoader, databaseIdProvider, configurationCustomizersProvider);
    }

    @Bean
    @Primary
    // JSONArrayHandler, JSONObjectHandler
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactory factory = super.sqlSessionFactory(dataSource);

        // 取得类型转换注册器
        TypeHandlerRegistry typeHandlerRegistry = factory.getConfiguration().getTypeHandlerRegistry();
        // typeHandlerRegistry.register(Dict.class, EnumAutoTypeHandler.class);
        typeHandlerRegistry.setDefaultEnumTypeHandler(EnumAutoTypeHandler.class); // 注册默认枚举转换器

        return factory;
    }
}
