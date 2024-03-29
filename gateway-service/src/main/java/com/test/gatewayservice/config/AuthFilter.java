package com.test.gatewayservice.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.test.gatewayservice.dto.RequestDto;
import com.test.gatewayservice.dto.TokenDTO;
import com.test.gatewayservice.exception.UnauthorizedException;

import reactor.core.publisher.Mono;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

	private WebClient.Builder webClient;
	
	public AuthFilter(WebClient.Builder webClient) {
		super(Config.class);
		this.webClient = webClient;
	}
	
	@Override
	public GatewayFilter apply(Config config) {
		return (((exchange, chain) -> {
			if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				throw new UnauthorizedException();
			}
		    String tokenHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
		    String [] chunks = tokenHeader.split(" ");
		    if(chunks.length!=2 || !chunks[0].equals("Bearer")) {
		    	throw new UnauthorizedException();
		    }
		    return webClient.build()
		    		.post()
		    		.uri("http://auth-service/api/v1/auths/validate?token="+chunks[1])
		    		.bodyValue(new RequestDto(exchange.getRequest().getPath().toString(),exchange.getRequest().getMethod().toString()))
		    		.retrieve().bodyToMono(TokenDTO.class)
		    		.map(t -> {
		    			t.getToken();
		    			return exchange;
		    		}).flatMap(chain::filter);
		}));
	}
	
	public Mono<Void> onError(ServerWebExchange exchange, HttpStatus status){
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(status);
		return response.setComplete();
	}
	
	public static class Config {}
}
