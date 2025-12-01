package io.github.flyhero.common.config;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.NumberSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
@Slf4j
public class JacksonAutoConfiguration {


	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		// 1.1 创建 SimpleModule 对象
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule simpleModule = new SimpleModule();
		simpleModule
				// 新增 Long 类型序列化规则，数值超过 2^53-1，在 JS 会出现精度丢失问题，因此 Long 自动序列化为字符串类型
				.addSerializer(Long.class, NumberSerializer.instance)
				.addSerializer(Long.TYPE, NumberSerializer.instance)
				.addSerializer(LocalDate.class, LocalDateSerializer.INSTANCE)
				.addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE)
				.addSerializer(LocalTime.class, LocalTimeSerializer.INSTANCE)
				.addDeserializer(LocalTime.class, LocalTimeDeserializer.INSTANCE)
				// 新增 LocalDateTime 序列化、反序列化规则
				.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

		// 1.2 注册到 objectMapper
		mapper.registerModule(simpleModule);
		log.info("[init][初始化 JsonUtils 成功]");
		return mapper;
	}

}