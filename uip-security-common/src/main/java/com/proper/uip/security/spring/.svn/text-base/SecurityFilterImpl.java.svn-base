/* <p>文件名称: SecurityFilterImpl.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-5-9</p>
 * <p>完成日期：2013-5-9</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-5-9 上午11:56:07
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.security.spring;

import java.io.IOException;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletResponse;

public class SecurityFilterImpl extends AbstractSecurityInterceptor implements Filter{
	//与applicationContext-security.xml里的myFilter的属性securityMetadataSource对应，  
    //其他的两个组件，已经在AbstractSecurityInterceptor定义  
    private FilterInvocationSecurityMetadataSource securityMetadataSource;  
  
    @Override  
    public SecurityMetadataSource obtainSecurityMetadataSource() {  
        return this.securityMetadataSource;
    }  
  
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {  
//    	if(response instanceof HttpServletResponse){
//        	HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//        	httpServletResponse.setHeader("P3P","CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
//        }
    	
    	FilterInvocation fi = new FilterInvocation(request, response, chain);  
        invoke(fi);  
        
    }  
      
    private void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {  
        // object为FilterInvocation对象  
        //super.beforeInvocation(fi);源码  
        //1.获取请求资源的权限  
        //执行Collection<ConfigAttribute> attributes = SecurityMetadataSource.getAttributes(object);  
        //2.是否拥有权限  
        //this.accessDecisionManager.decide(authenticated, object, attributes);  
        InterceptorStatusToken token = super.beforeInvocation(filterInvocation);  
        try {  
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());  
        } catch(IOException exception){
        	throw exception;
        } catch(ServletException exception){
        	throw exception;
        } finally {  
            super.afterInvocation(token, null);  
        }  
    }  
  
    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {  
        return securityMetadataSource;  
    }  
  
    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {  
        this.securityMetadataSource = securityMetadataSource;  
    }  
       
  
    @Override  
    public Class<? extends Object> getSecureObjectClass() {  
        //下面的MyAccessDecisionManager的supports方面必须放回true,否则会提醒类型错误  
        return FilterInvocation.class;  
    }

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}  
}  