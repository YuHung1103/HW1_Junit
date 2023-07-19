package com.example.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;



@Configuration
public class SwaggerConfig {

	@Bean
    public OpenAPI serviceOpenAPI() {
        return new OpenAPI()
        		//設定OpenAPI規格中的基本資訊
                .info(new Info().title("圖書商店後台管理系統")
                                 .description("Homework1")
                                 .version("1.0"))
                //設定OpenAPI規格中的安全機制
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")))
                //創建了一個安全需求，要求使用 "bearerAuth" 安全機制進行驗證
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
