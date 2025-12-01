package io.github.flyhero.gateway.filter;

import java.net.URI;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;

import com.alibaba.nacos.api.common.Constants;

import io.github.flyhero.common.constants.Constant;
import io.github.flyhero.common.model.UserInfo;
import io.github.flyhero.gateway.config.GatewayProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements GlobalFilter, Ordered {

	@Autowired
	private GatewayProperty gatewayProperty;
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		putUserInfoToHeader(request);
		if (checkToken(request)) {
			return chain.filter(exchange);
		}

		return chain.filter(exchange);
	}

	/**
	 * 检查token
	 * @param request
	 * @return
	 */
	private boolean checkToken(ServerHttpRequest request) {
		String token = request.getHeaders().getFirst("Authorization");
		String url = request.getPath().value();
		URI uri = request.getURI();
		PathMatcher matcher = new AntPathMatcher();
		String[] skipTokenPaths = gatewayProperty.getSkipTokenPaths();
		if (skipTokenPaths == null || skipTokenPaths.length == 0){
			return true;
		}
		for (String skipTokenPath : skipTokenPaths) {
			if (matcher.match(skipTokenPath, url)) {
				return true;
			}
		}
		if (!StringUtils.hasText(token)){
			return false;
		}
		return JWTUtil.verify(token, "123456".getBytes());
	}

	/**
	 * 将token中的用户信息放入header中
	 * @param request
	 */
	private void putUserInfoToHeader(ServerHttpRequest request) {
		String token = request.getHeaders().getFirst(Constant.AUTHORIZATION);
		if (!StringUtils.hasText(token)){
			// TODO 查询租户表，根据域名查询租户id
			UserInfo userInfo = new UserInfo();
			userInfo.setTenantId(1L);
			request.mutate().header(Constant.USER_INFO, JSONUtil.toJsonStr(userInfo)).build();
			return;
		}
		JWT jwt = JWTUtil.parseToken(token);
		JSONObject payloads = jwt.getPayloads();
		request.mutate().header(Constant.USER_INFO, payloads.toString()).build();
	}

	@Override
	public int getOrder() {
		return -1000;
	}
}
