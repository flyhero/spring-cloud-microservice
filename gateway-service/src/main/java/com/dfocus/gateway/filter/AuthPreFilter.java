package com.dfocus.gateway.filter;

import com.auth0.jwt.interfaces.Claim;
import com.dfocus.gateway.auth.UcenterAuth;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.DEBUG_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class AuthPreFilter extends ZuulFilter{

    private Logger logger = LoggerFactory.getLogger(AuthPreFilter.class);
    @Autowired
    private UcenterAuth ucenterAuth;
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return DEBUG_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getHeader("Authorization");
        if (token == null || token.equals("")) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token can not null");
                logger.info("token can not null");
                return null;
            }catch (Exception e){

            }
        }
        try{
            Map<String, Claim> map= ucenterAuth.verify(token);
            if (map == null) {
                responseHandler(ctx,"token 无效");
                return null;
            }

        }catch (Exception e){

        }

        return null;
    }

    private void responseHandler(RequestContext ctx,String msg) throws IOException {
        ctx.setSendZuulResponse(false);//zuul过滤请求，false为不路由，true为路由
        HttpServletResponse httpResponse = ctx.getResponse();
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        httpResponse.getWriter().write(msg);

        ctx.setResponse(httpResponse);//返回response信息
    }
}
