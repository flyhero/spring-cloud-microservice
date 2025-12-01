package io.github.flyhero.common.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//	private AuthIntercepter authIntercepter;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getAuthIntercepter())
				.addPathPatterns("/**") // 拦截所有路径
				.excludePathPatterns("/public/**"); // 排除某些路径（可选）
	}

	@Bean
	public AuthIntercepter getAuthIntercepter() {
		return new AuthIntercepter();
	}
}
