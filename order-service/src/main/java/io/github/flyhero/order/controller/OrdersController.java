package io.github.flyhero.order.controller;

import java.util.List;
import java.util.Properties;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import org.apache.shardingsphere.infra.util.spi.type.typed.TypedSPILoader;
import org.apache.shardingsphere.sharding.spi.KeyGenerateAlgorithm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.github.flyhero.common.controller.BaseController;
import io.github.flyhero.order.entity.Orders;
import io.github.flyhero.order.service.IOrdersService;
import lombok.AllArgsConstructor;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author flyhero
 * @since 2025-04-22
 */
@AllArgsConstructor
@RestController
public class OrdersController extends BaseController {

	private IOrdersService orderService;

	@GetMapping("/order/test3")
	public String getOrder() {
		List<Orders> list = orderService.list();
		System.out.println(list);
		return "success";
	}
	@GetMapping("/order/test/save")
	public String save() {
		Orders orders = new Orders();
//		orders.setId(1914593915587985410L);
		orders.setProgramId(1L);
		orders.setUserId(1345634654645457L);
		orders.setOrderNumber(15464564523344L);
		orderService.save(orders);
		return "success";
	}

	@GetMapping("/order/test/update")
	public String update() {
		LambdaUpdateWrapper<Orders> wrapper = Wrappers.<Orders>lambdaUpdate().set(Orders::getIsDeleted, true).eq(Orders::getId, 1914593915587985410L);
		orderService.update(wrapper);
		return "success";
	}
	@GetMapping("/order/test4")
	public String test4(@RequestParam(name = "userId") Long userId) {
		LambdaQueryWrapper<Orders> eq = Wrappers.<Orders>lambdaQuery().eq(Orders::getUserId, userId);
		Orders orders = orderService.getOne(eq);
		return "success";
	}

	public static void main(String[] args) {

		// 通过 TypedSPILoader 加载雪花算法实例
		KeyGenerateAlgorithm snowflakeAlgorithm = TypedSPILoader.getService(KeyGenerateAlgorithm.class, "SNOWFLAKE");

		// 生成 ID
		Comparable<?> id = snowflakeAlgorithm.generateKey();
		System.out.println(id);
	}
}
