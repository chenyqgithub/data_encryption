package com.data.encryption.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * 接口说明:SWAGGER2配置
 *
 **/
@Configuration
@EnableSwagger2
public class Swagger2
 {
   @Bean
   public Docket swaggerSpringMvcPlugin()
   {
     return new Docket(DocumentationType.SWAGGER_2)
       .apiInfo(apiInfo())
       .select()
       .apis(RequestHandlerSelectors.basePackage("com.data.encryption.controller"))
       .paths(PathSelectors.any())
       .build();
   }

   private Predicate<String> paths()
   {
     return Predicates.or(new Predicate[] { PathSelectors.regex("/person.*") });
   }

   private List<ApiKey> securitySchemes() {
     return Lists.newArrayList(new ApiKey[] { new ApiKey("clientId", "客户端ID", "header"), new ApiKey("clientSecret", "客户端秘钥", "header"), new ApiKey("accessToken", "客户端访问标识", "header") });
   }

   private List<SecurityContext> securityContexts()
   {
     return Lists.newArrayList(new SecurityContext[] {
       SecurityContext.builder()
       .securityReferences(defaultAuth())
       .forPaths(PathSelectors.regex("/*.*"))
       .build() });
   }

   List<SecurityReference> defaultAuth()
   {
     AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
     AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
     authorizationScopes[0] = authorizationScope;
     return Lists.newArrayList(new SecurityReference[] { new SecurityReference("clientId", authorizationScopes), new SecurityReference("clientSecret", authorizationScopes), new SecurityReference("accessToken", authorizationScopes) });
   }

   private ApiInfo apiInfo()
   {
     return new ApiInfoBuilder()
       .title("集成Swagger文档")
       .termsOfServiceUrl("http://baidu.com")
       .description("集成Swagger文档用于测试各个API接口，更多详情和限流请访问<a href='#'>集成文档</a>。")
       .license("License Version 2.0")
       .licenseUrl("#")
       .contact(new Contact("yangzhuang", "", "ideacoding@163.com"))
       .version("2.0")
       .build();
   }
 }