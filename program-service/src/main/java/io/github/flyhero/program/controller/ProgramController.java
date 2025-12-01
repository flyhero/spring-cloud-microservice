package io.github.flyhero.program.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.github.flyhero.common.controller.BaseController;
import io.github.flyhero.common.model.JsonResult;
import io.github.flyhero.program.service.IProgramService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author flyhero
 * @since 2025-11-26
 */
@RestController
@RequestMapping("/program/")
public class ProgramController extends BaseController {

	@Autowired
	private IProgramService programService;

	@GetMapping("getById")
	public JsonResult getById(Long id) {
		return JsonResult.ok(programService.getById(id));
	}
	@DeleteMapping("delete")
	public JsonResult delete(Long id) {
		return JsonResult.ok(programService.delete(id));
	}
	@GetMapping("getConfig")
	public JsonResult getConfig(String key) {
		return JsonResult.ok(programService.getConfig(key));
	}

	@GetMapping("hots")
	public JsonResult hots() {
		return JsonResult.ok(programService.hotPrograms(1L));
	}
}
