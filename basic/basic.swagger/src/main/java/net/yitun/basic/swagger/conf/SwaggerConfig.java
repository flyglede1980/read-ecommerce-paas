package net.yitun.basic.swagger.conf;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <pre>
 * <b>WIKI 配置.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月12日 上午11:28:54 LH
 *         new file.
 * </pre>
 */
@Slf4j
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${info.app.name: < ?? >}")
    private String title;

    @Value("${info.app.version: < 0.0.0.1 >}")
    private String version;

    @Value("${info.app.profile: < dev >}")
    private String profile;

    @Value("${info.app.description: < ... >}")
    private String description;

    @Value("${basic.swagger.token: Bearer xxx.xxx.xxx}")
    private String token; // 绑定默认的测试令牌: Bearer xxx.xxx.xxx

    @PostConstruct
    protected void init() {
        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());
    }

    @Bean
    public Docket apiDocket() {
        ApiInfo apiInfo = new ApiInfoBuilder() //
                .title(this.title) //
                .version(this.version + " " + this.profile) //
                .description(this.description) //
                .contact(new Contact("Contact", "http://ytdev.org/contact.htm", "it@yitun.net")) //
//                .license("License - 1.0").licenseUrl("http://api.yitun.net/licenses/1.0.html") //
                .termsOfServiceUrl("http://ytdev.org") //
                .build();

        Parameter parameter = new ParameterBuilder() {
            {
                name("Authorization");
                description("JWT Token");
                defaultValue(token);
                modelRef(new ModelRef("string"));
                parameterType("header");
                required(false);
            }
        }.build();

        return new Docket(DocumentationType.SWAGGER_2) //
                .enable(true) //
                .apiInfo(apiInfo) //
                .globalOperationParameters(Arrays.asList(parameter)) //

                .select()
                // .apis(RequestHandlerSelectors.any()) // 任意Controller类都被解析成DOC文档
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)) // 仅被@Api注解的类可以生成DOC
//                .apis(new Predicate<RequestHandler>() {
//                    @Override
//                    public boolean apply(RequestHandler input) {
//                        Class<?> clazz = input.declaringClass();
//                        if (clazz == BasicErrorController.class) // 排除 BasicErrorController
//                            return false;
//                        if (clazz.isAnnotationPresent(Api.class))  // 被@Api注解的类可以生成DOC
//                            return true;
//                        return false;
//                    }
//                }) //
                // .paths(PathSelectors.ant("/**"))
                .paths(PathSelectors.any()).build();

//        return Docket(DocumentationType.SWAGGER_2)
//                .genericModelSubstitutes(ResponseEntity::class.java)
//                .useDefaultResponseMessages(true)
//                .forCodeGeneration(true)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.company.service.web"))
//                .paths(PathSelectors.any())
//                .build()
//                .pathMapping("/")
//                .apiInfo(apiInfo())
    }

}
