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

@Activate(group = CommonConstants.CONSUMER)
public class AuthConsumerFilter implements Filter {
	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) {
		// 从当前线程获取租户ID
//		Long tenantId = TenantContextHolder.getTenantId();

		UserInfo userInfo = UserHolder.get();
		if (userInfo != null) {
			RpcContextAttachment.getClientAttachment().setAttachment(Constant.USER_INFO, JSON.toJSONString(userInfo));
		}
		return invoker.invoke(invocation);
	}
}
