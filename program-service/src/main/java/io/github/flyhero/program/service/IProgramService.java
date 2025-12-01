package io.github.flyhero.program.service;

import java.util.List;

import io.github.flyhero.program.entity.Program;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author flyhero
 * @since 2025-11-26
 */
public interface IProgramService extends IService<Program> {
	String getConfig(String key);
	Program getById(Long id);
	Program delete(Long id);
	List<Program> hotPrograms(Long tenantId);
}
