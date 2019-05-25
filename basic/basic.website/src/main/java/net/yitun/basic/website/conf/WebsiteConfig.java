package net.yitun.basic.website.conf;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.apache.catalina.core.AprLifecycleListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * <b>Web 配置</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018-02-02 15:51:20.766
 * <b>Copyright:</b> Copyright ©2017 tb.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018-02-02 15:51:20.766 LH
 *         new file.
 * </pre>
 */
@Slf4j
@Order(65)
@Configuration("basic.website.WebConfig")
public class WebsiteConfig implements WebMvcConfigurer {

    @Value("${server.tomcat.apr: false}")
    protected boolean enableApr;

    protected AprLifecycleListener aprLifecycleListener = null;

    @PostConstruct
    protected void init() {
        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());
    }

    // @Bean
    // public TomcatEmbeddedServletContainerFactory containerFactory() {
    // TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
    // if (enableApr) {
    // arpLifecycle = new AprLifecycleListener();
    // tomcat.setProtocol("org.apache.coyote.http11.Http11AprProtocol");
    // }
    // return tomcat;
    // }

    // /**
    // * 多个拦截器组成一个拦截器链
    // */
    // @Override
    // public void addInterceptors(InterceptorRegistry registry) {
    // logger.info("{}.addInterceptors, attach api interceptor", this.getClass().getName());
    // registry.addInterceptor(this.apiInterceptor).addPathPatterns(this.apiInterceptor.PathPatterns);
    // }

    /**
     * 定制HTTP消息转换器，指定使用fastjson进行转换<br/>
     * 初次方法外还可以用启动Application类继承extends WebMvcConfigurerAdapter，且覆盖configureMessageConverters
     */
    @Bean
    public HttpMessageConverters jsonHttpMessageConverter() {
        // 定义一个convert转换消息的对象并添加配置信息
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        // 附加：支持的响应媒体类型，以便处理中文乱码 text/html, application/json;charset=UTF-8
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON_UTF8));

        // 建议使用这种方式，小范围指定白名单
        ParserConfig.getGlobalInstance().addAccept("net.yitun.");
        // ParserConfig.getGlobalInstance().setAutoTypeSupport(true); // 全局开启AutoType，不建议使用

        // 设置fastjson的配置信息，比如字符编码、时间格式化、取消循环依赖输出
        // converter.getFastJsonConfig().setCharset(Charset.forName("UTF-8")); // 默认字符集UTF-8
        // converter.getFastJsonConfig().setDateFormat("yyyy-MM-dd HH:mm:ss"); // 时间转换的格式： yyyy-MM-dd HH:mm:ss
        converter.getFastJsonConfig().setSerializerFeatures(SerializerFeature.WriteBigDecimalAsPlain,
                SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect);

        return new HttpMessageConverters(converter);
    }

//    /*
//     * Configure View Resolver
//     */
//    @Bean
//    public ViewResolver viewResolver() {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setViewClass(JstlView.class);
//        viewResolver.setPrefix("/WEB-INF/views/");
//        viewResolver.setSuffix(".jsp");
//        return viewResolver;
//    }
//
//    /*
//     * Configure ResourceHandlers to serve static resources like CSS/ Javascript etc...
//     *
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
//    }
//
//    /*
//     * Configure MessageSource to provide internationalized messages
//     *
//     */
//    @Bean
//    public MessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasename("messages");
//        return messageSource;
//    }
}
