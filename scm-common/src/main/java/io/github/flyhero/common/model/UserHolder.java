package io.github.flyhero.common.model;

import java.util.Optional;

import com.alibaba.ttl.TransmittableThreadLocal;

public class UserHolder {
	private static final TransmittableThreadLocal<UserInfo> USER_INFO_THREAD_LOCAL = new TransmittableThreadLocal<>();
	public static void set(UserInfo userInfo) {
		USER_INFO_THREAD_LOCAL.set(userInfo);
	}
	public static UserInfo get() {
		return USER_INFO_THREAD_LOCAL.get();
	}

	public static Long getTenantId() {
		UserInfo userInfo = USER_INFO_THREAD_LOCAL.get();
		return userInfo.getTenantId();
	}
	public static void remove() {
		USER_INFO_THREAD_LOCAL.remove();
	}
}
