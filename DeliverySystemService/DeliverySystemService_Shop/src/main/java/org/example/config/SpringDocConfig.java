package org.example.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 刘文轩
 * @Date 2023/10/6 2:04
 */
@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(info())
/*添加对JWT对token的支持(本步骤可选) 在添加OpenApiConfig类上添加Components信息：然后在OpenApi中注册Components:*/
                .components(components())
                .externalDocs(externalDocumentation());
    }

    private License license() {
        return new License()
                .name("MIT")
                .url("https://opensource.org/licenses/MIT");
    }

    private Info info() {
        return new Info()
                .title("Open API")
                .description("")
                .version("v0.0.1")
                .license(license());
    }

    private ExternalDocumentation externalDocumentation() {
        return new ExternalDocumentation()
                .description("")
                .url("");
    }

    /* 添加对JWT对token的支持(本步骤可选)
     在添加OpenApiConfig类上添加Components信息：*/
    private Components components() {
        return new Components()
                .addSecuritySchemes("bearer-key", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"));
    }
    /*然后在OpenApi中注册Components:*/
/*    @Bean
    public OpenAPI springShopOpenAPI2() {
        return new OpenAPI()
                .info(info())
                .components(components())
                .externalDocs(externalDocumentation());
    }
    */
//    /*在需要使用Authorization的接口上添加：*/
//    @Operation(security = { @SecurityRequirement(name = "bearer-key") })

}
