package io.github.flyhero.common.controller;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BaseController {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

}
