package com.UnifyApp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Unify - Gestor Financiero API")
                        .description("API REST para la gestión de finanzas personales. Permite administrar usuarios, cuentas bancarias, movimientos financieros y categorías de gastos.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Unify Support")
                                .email("support@unify.com")
                                .url("https://unify.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desarrollo"),
                        new Server()
                                .url("https://api.unify.com")
                                .description("Servidor de Producción")
                ));
    }
}
