package io.github.flyhero.order.controller;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.github.flyhero.common.controller.BaseController;
import io.github.flyhero.order.entity.Test;
import io.github.flyhero.order.service.ITestService;
import lombok.AllArgsConstructor;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author flyhero
 * @since 2025-04-29
 */
@AllArgsConstructor
@RestController
public class TestController extends BaseController {

	private ITestService testService;
	@GetMapping("/order/test/test3")
	public String getOrder() {
		List<Test> list = testService.list();
		System.out.println(list);
		return "success";
	}
	@GetMapping("/order/test/test/save")
	public String save() {
		Test order01 = new Test();
		order01.setData("测试");
		testService.save(order01);
		return "success";
	}

	@GetMapping("/order/test/test/update")
	public String update() {
		LambdaUpdateWrapper<Test> wrapper = Wrappers.<Test>lambdaUpdate().set(Test::getIsDeleted, true).eq(Test::getId, 1914593915587985410L);
		testService.update(wrapper);
		return "success";
	}
}
