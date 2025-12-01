package io.github.flyhero.common.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class JsonResult<T> implements Serializable {
	private Integer code;
	private String msg;
	private T data;

	public JsonResult() {
	}

	public JsonResult(Integer code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public static <T> JsonResult<T> ok(T data) {
		return new JsonResult<T>(200, "success", data);
	}

	public static <T> JsonResult<T> error(Integer code, String msg) {
		return new JsonResult<T>(code, msg, null);
	}

	public static <T> JsonResult<T> errorData(Integer code, T data) {
		return new JsonResult<T>(code, "error", data);
	}

	public static <T> JsonResult<T> error() {
		JsonResult<T> apiResponse = new JsonResult<T>();
		apiResponse.code = -100;
		apiResponse.msg = "系统错误，请稍后重试!";
		return apiResponse;
	}
}
