package io.github.flyhero.common.model;

import lombok.Data;

@Data
public class UserInfo {
	private String userName;
	private Long tenantId;
	private Long userId;

}
