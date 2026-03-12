package com.wesdell.shop.gateway.routes;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;

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
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                        "product-service_circuit-breaker",
                        URI.create("forward:/fallback")
                ))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> callProductServiceSwaggerRoute() {
        return GatewayRouterFunctions
                .route("product_service_swagger")
                .route(
                        RequestPredicates.path("/aggregate/product-service/v3/api-docs"),
                        HandlerFunctions.http()
                )
                .before(uri("http://localhost:8080"))
                .filter(setPath("/api-docs"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                        "product-service-swagger_circuit-breaker",
                        URI.create("forward:/fallback")
                ))
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
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                        "order-service_circuit-breaker",
                        URI.create("forward:/fallback")
                ))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> callOrderServiceSwaggerRoute() {
        return GatewayRouterFunctions
                .route("order_service_swagger")
                .route(
                        RequestPredicates.path("/aggregate/order-service/v3/api-docs"),
                        HandlerFunctions.http()
                )
                .before(uri("http://localhost:8081"))
                .filter(setPath("/api-docs"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                        "order-service-swagger_circuit-breaker",
                        URI.create("forward:/fallback")
                ))
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
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                        "inventory-service_circuit-breaker",
                        URI.create("forward:/fallback")
                ))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> callInventoryServiceSwaggerRoute() {
        return GatewayRouterFunctions
                .route("inventory_service_swagger")
                .route(
                        RequestPredicates.path("/aggregate/inventory-service/v3/api-docs"),
                        HandlerFunctions.http()
                )
                .before(uri("http://localhost:8082"))
                .filter(setPath("/api-docs"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                        "inventory-service-swagger_circuit-breaker",
                        URI.create("forward:/fallback")
                ))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> callFallbackRoute() {
        return GatewayRouterFunctions
                .route("fallback_route")
                .GET(
                        "/fallback",
                        request -> ServerResponse
                                .status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body("Service unavailable. Please, try again later!")
                )
                .build();
    }
}
