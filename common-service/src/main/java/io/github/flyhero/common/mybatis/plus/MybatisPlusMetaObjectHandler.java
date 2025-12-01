package io.github.flyhero.common.mybatis.plus;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import org.apache.ibatis.reflection.MetaObject;

import io.github.flyhero.common.model.UserHolder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		log.info("start insert fill ....");
		this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
		this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
		this.strictInsertFill(metaObject, "tenantId", UserHolder::getTenantId, Long.class);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		log.info("start update fill ....");
		this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
	}
}