package io.github.flyhero.common.config;

import cn.hutool.json.JSONUtil;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import io.github.flyhero.common.constants.Constant;
import io.github.flyhero.common.model.UserHolder;
import io.github.flyhero.common.model.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class AuthIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求头中的user_info信息
        String userInfo = request.getHeader(Constant.USER_INFO);
        if (StringUtils.hasText(userInfo)){
            UserInfo info = JSONUtil.toBean(userInfo, UserInfo.class);
            UserHolder.set(info);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.remove();
    }
}