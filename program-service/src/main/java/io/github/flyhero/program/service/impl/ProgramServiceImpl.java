package io.github.flyhero.program.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.github.flyhero.cache.MultiLevelCacheManager;
import io.github.flyhero.program.entity.Program;
import io.github.flyhero.program.mapper.ProgramMapper;
import io.github.flyhero.program.service.IProgramService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author flyhero
 * @since 2025-11-26
 */
@Service
public class ProgramServiceImpl extends ServiceImpl<ProgramMapper, Program> implements IProgramService {
	// 注入多级缓存管理器
//	@Autowired
//	private MultiLevelCacheManager multiLevelCacheManager;
//
//	// 注入Caffeine缓存管理器
	@Autowired
	private CaffeineCacheManager caffeineCacheManager;

	@Cacheable(value = "config", key = "#key", cacheManager = "caffeineCacheManager")
	public String getConfig(String key) {
		System.out.println("db查询成功");
		return "配置";
	}

	@Cacheable(value = "program", key = "#id", cacheManager = "multiLevelCacheManager")
	@Override
	public Program getById(Long id) {
		Program program = new Program();
		program.setId(id);
		program.setName("程序");
		program.setStartTime(LocalDateTime.now());
		program.setEndTime(LocalDateTime.now());
		program.setStatus(1);
		program.setIntroduction("程序介绍");
		program.setChooseSeat(true);
		program.setCreateTime(LocalDateTime.now());
		program.setUpdateTime(LocalDateTime.now());
		program.setIsDeleted(false);
		program.setTenantId(1L);
		System.out.println("db查询成功");
		return program;
	}

	@CacheEvict(value = "program", key = "#id")
	@Override
	public Program delete(Long id) {
		return null;
	}

	@Cacheable(value = "program::hot", key = "#tenantId", cacheManager = "multiLevelCacheManager")
	@Override
	public List<Program> hotPrograms(Long tenantId) {
		Program program = new Program();
		program.setId(1L);
		program.setName("程序");
		program.setStartTime(LocalDateTime.now());
		program.setEndTime(LocalDateTime.now());
		program.setStatus(1);
		program.setIntroduction("程序介绍");
		program.setChooseSeat(true);
		program.setCreateTime(LocalDateTime.now());
		program.setUpdateTime(LocalDateTime.now());
		program.setIsDeleted(false);
		program.setTenantId(1L);
		System.out.println("db查询成功");
		ArrayList<Program> programs = new ArrayList<>();
		programs.add(program);
		return programs;
	}
}
