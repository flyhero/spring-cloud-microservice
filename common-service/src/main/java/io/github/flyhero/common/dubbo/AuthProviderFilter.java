package io.github.flyhero.common.dubbo;


import com.alibaba.fastjson2.JSON;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContextAttachment;

import io.github.flyhero.common.constants.Constant;
import io.github.flyhero.common.model.UserHolder;
import io.github.flyhero.common.model.UserInfo;

@Activate(group = CommonConstants.PROVIDER)
public class AuthProviderFilter implements Filter {
	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) {
		String userInfoJson = RpcContextAttachment.getClientAttachment().getAttachment(Constant.USER_INFO);
		if (userInfoJson != null) {
			UserHolder.set(JSON.parseObject(userInfoJson, UserInfo.class));
		}
		try {
			return invoker.invoke(invocation);
		} finally {
			UserHolder.remove();
		}
	}
}

