package com.wesdell.shop.gateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;

@Configuration
public class Route {

    @Bean
    public RouterFunction<ServerResponse> callProductServiceRoute() {
        return GatewayRouterFunctions
                .route("product_service")
                .route(
                        RequestPredicates.path("/api/products"),
                        HandlerFunctions.http()
                )
                .before(uri("http://localhost:8080"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> callOrderServiceRoute() {
        return GatewayRouterFunctions
                .route("order_service")
                .route(
                        RequestPredicates.path("/api/orders"),
                        HandlerFunctions.http()
                )
                .before(uri("http://localhost:8081"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> callInventoryServiceRoute() {
        return GatewayRouterFunctions
                .route("inventory_service")
                .route(
                        RequestPredicates.path("/api/inventory"),
                        HandlerFunctions.http()
                )
                .before(uri("http://localhost:8082"))
                .build();
    }

}
