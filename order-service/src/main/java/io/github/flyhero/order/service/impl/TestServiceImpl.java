package io.github.flyhero.order.service.impl;

import io.github.flyhero.order.entity.Test;
import io.github.flyhero.order.mapper.TestMapper;
import io.github.flyhero.order.service.ITestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author flyhero
 * @since 2025-04-29
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {

}
