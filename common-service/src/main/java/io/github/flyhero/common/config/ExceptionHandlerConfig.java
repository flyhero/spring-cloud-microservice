package io.github.flyhero.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DefaultExceptionHandler.class)
public class ExceptionHandlerConfig {
}
