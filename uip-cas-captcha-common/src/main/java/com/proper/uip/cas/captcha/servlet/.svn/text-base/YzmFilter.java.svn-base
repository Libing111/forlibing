/* <p>文件名称: YzmFilter.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-1-13</p>
 * <p>完成日期：2014-1-13</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-1-13下午1:21:04
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.cas.captcha.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.proper.uip.cas.captcha.CaptchaServiceSingleton;

public class YzmFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		boolean flag = false;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		StringBuffer servletPathBuff = new StringBuffer().append(httpRequest
				.getServletPath());
		if (httpRequest.getQueryString() != null) {
			servletPathBuff.append("?").append(httpRequest.getQueryString());
		}

		String servletPathInfo = servletPathBuff.toString();
		
		if(servletPathInfo.startsWith("/login") == false){
			chain.doFilter(request, response);
			return;
		}
		
		if(httpRequest.getMethod().equals("POST") == false){
			chain.doFilter(request, response);
			return;
		}
		
		String yzm = request.getParameter("yzm");
		if (yzm == null) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
			System.out.println("yzm 为空");
			chain.doFilter(request, response);
			return;
		} 

		System.out.println("yzm 不为空");
		String captchaId = httpRequest.getSession().getId();
		flag = CaptchaServiceSingleton.getInstance().validateResponseForID(captchaId, yzm).booleanValue();

		if (!flag) {
			httpResponse.sendRedirect(httpRequest.getContextPath()
					+ "/login");
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
