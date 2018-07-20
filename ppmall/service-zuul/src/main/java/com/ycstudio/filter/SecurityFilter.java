package com.ycstudio.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.ppmall.common.Const;
import com.ppmall.common.ResponseCode;
import com.ppmall.common.ServerResponse;
import com.ppmall.pojo.User;
import com.ppmall.util.JsonUtil;
import com.ppmall.util.TokenUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;

public class SecurityFilter extends ZuulFilter {

	/**
	 * 过滤器的具体逻辑。
	 */
	@Override
	public Object run() throws ZuulException {
		// TODO Auto-generated method stub
		RequestContext ctx = RequestContext.getCurrentContext();

		HttpServletRequest httpServletRequest = (HttpServletRequest) ctx.getRequest();
		HttpServletResponse httpServletResponse = (HttpServletResponse) ctx.getResponse();
		/**
		 * 設置返回格式
		 */
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setContentType("application/json; charset=utf-8");

		HttpSession httpSession = httpServletRequest.getSession();

		String requestUrl = httpServletRequest.getRequestURI();
		String resultString = ServerResponse.createErrorStatus(ResponseCode.NOT_LOGIN).toString();
		String token = httpServletRequest.getHeader("Authentication");

		User currentUser = (User) httpSession.getAttribute(Const.CURRENT_USER);
		if (token != null) {
			try {
				Claims claims = TokenUtil.parseToken(token);
				currentUser = JsonUtil.jsonStringToObject(JsonUtil.objectToJsonString(claims.get(Const.CURRENT_USER)),
						User.class);
				httpSession.setAttribute(Const.CURRENT_USER, currentUser);
			} catch (ExpiredJwtException e) {
				httpSession.removeAttribute(Const.CURRENT_USER);
				resultString = ServerResponse.createErrorStatus(ResponseCode.TOKEN_EXPIRED.getCode(), "token已过期")
						.toString();
			} catch (InvalidClaimException e) {
				httpSession.removeAttribute(Const.CURRENT_USER);
				resultString = ServerResponse.createErrorStatus(ResponseCode.TOKEN_INVALID.getCode(), "token不合法")
						.toString();
			} catch (Exception e) {
				httpSession.removeAttribute(Const.CURRENT_USER);
				e.printStackTrace();
				resultString = ServerResponse.createErrorStatus(ResponseCode.TOKEN_ERROR.getCode(), "token错误")
						.toString();
			}
		}

		currentUser = (User) httpSession.getAttribute(Const.CURRENT_USER);
		/**
		 * 包含login为登陆url不过滤，不包含login,register將进入以下
		 */
		if (!requestUrl.contains("login.do") && !requestUrl.contains("register.do")
				&& !requestUrl.contains("alipay_callback.do") 
				&& !requestUrl.contains("check_valid.do")
				&& !requestUrl.contains("kill") 
				&& !requestUrl.contains("product/list.do")
				&& !requestUrl.contains("product/detail.do") 
				&& !requestUrl.contains("category")
				&& !requestUrl.contains("index")) {
			if (currentUser == null) {
				ctx.setSendZuulResponse(false);
				try {
					httpServletResponse.getWriter().print(resultString);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				/**
				 * 已经登陆，转发请求
				 */
				return null;
			}
		} else if (requestUrl.contains("manage") && currentUser != null) {
			/**
			 * 如果url包含manage即为后台管理，需要验证管理员权限
			 */
			
			if ( currentUser.getRole() == Const.Role.ROLE_ADMIN ) {
				resultString = ServerResponse.createErrorMessage("需要管理员权限").toString();
				ctx.setSendZuulResponse(false);
				try {
					httpServletResponse.getWriter().print(resultString);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				return null;
			}
		} else {
			return null;
		}

		return null;
	}

	/**
	 * 返回一个boolean类型来判断该过滤器是否要执行， 所以通过此函数可实现过滤器的开关。
	 */
	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 启动顺序
	 */
	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * pre：可以在请求被路由之前调用 route：在路由请求时候被调用 post：在route和error过滤器之后被调用
	 * error：处理请求时发生错误时被调用
	 */
	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

}
