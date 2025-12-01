package io.github.flyhero.order.service.impl;

import io.github.flyhero.order.entity.Orders;
import io.github.flyhero.order.mapper.OrdersMapper;
import io.github.flyhero.order.service.IOrdersService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author flyhero
 * @since 2025-04-22
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

}
