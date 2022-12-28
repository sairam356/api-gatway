package com.apigatway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.resolver.DefaultAddressResolverGroup;
import reactor.netty.http.client.HttpClient;

@SpringBootApplication
@EnableEurekaClient
public class GatwayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatwayServiceApplication.class, args);
	}

	@Bean
	@Primary
	public WebClient webClient() {
		HttpClient httpClient = HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
		return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient)).build();
	}

	@Bean
	public RouteLocator gateway(RouteLocatorBuilder rlb) {
		return rlb.routes().route(route -> route.path("/debit").uri("lb://DEBIT-SERVICE/")).build();
	}

}
