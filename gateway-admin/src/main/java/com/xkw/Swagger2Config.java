package com.xkw;

import com.xkw.gateway.domain.ApiGroup;
import com.xkw.gateway.service.ApiGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableSwagger2
@Primary
public class Swagger2Config implements SwaggerResourcesProvider {

    @Autowired
    ApiGroupService apiGroupService;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xkw.rbm"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("学科网接口文档")
                .description("提供学科网各个应用的接口")
                .termsOfServiceUrl("http://rbm.xkw.com/")
                .contact(new Contact("wangwei01", "http://rbm.xkw.com", "wangwei01@xkw.cn"))
                .version("1.0")
                .build();
    }

    @Override
    public List<SwaggerResource> get() {
//        List<SwaggerResource> resources = new ArrayList<>();
//        resources.add(swaggerResource("qbm", "http://api.xkw.com/qbm/v1/documentation", "v1"));
//        resources.add(swaggerResource("mdm", "http://api.xkw.com/master/v1/documentation", "v1"));
//        resources.add(swaggerResource("rbm", "http://rbm-pilot.xkw.com/api/v2/api-docs", "v1"));
//        resources.add(swaggerResource("preview", "http://rbm.xkw.com/api/preview/v2/api-docs", "v1"));

        List<ApiGroup> apiGroups = apiGroupService.getAll();
        List<SwaggerResource> resources = apiGroups.stream()
                .map(apiGroup -> swaggerResource(apiGroup.getName(), String.format("%s%s", apiGroup.getUrl(), apiGroup.getApiInfoUrl()), "v1"))
                .collect(Collectors.toList());

        return resources;
    }

    // 获取对应的路由资源
    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

}
