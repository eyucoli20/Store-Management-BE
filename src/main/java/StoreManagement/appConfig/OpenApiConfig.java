package StoreManagement.appConfig;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class OpenApiConfig {
    private static final String DESCRIPTION = "The Store Management Application is a comprehensive system designed to streamline the process of opening new stores," +
            " managing inventory, and optimizing store operations. " +
            "It offers a set of features for users with different roles, including Admin, Store Manager, and Store Staff. " +
            "Users can create and manage stores, items, and purchase orders, as well as monitor and track inventory levels in real-time." +
            " This API documentation outlines the endpoints and functionality of the Store Management Application, allowing users to interact with the system programmatically.";

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Store Management App APIs")
                        .description(DESCRIPTION)
                        .version("v1.0")
                )
                .addSecurityItem(new SecurityRequirement().addList("bearer-jwt", Arrays.asList("read", "write")))
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                        )
                )
                .servers(Collections.singletonList(
                        new Server()
                                .description("Test Server")
                ));
    }
}
