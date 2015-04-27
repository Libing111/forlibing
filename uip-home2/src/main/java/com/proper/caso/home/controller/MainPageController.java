/* <p>文件名称: MainPageController.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-5-31</p>
 * <p>完成日期：2013-5-31</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-5-31 下午3:29:39
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.caso.home.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.caso.home.application.model.MenuNode;
import com.proper.caso.home.service.HomeService;
import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.service.ResourceService;
import com.proper.uip.api.security.service.UserService;
import com.proper.uip.web.BaseController;


@Controller
public class MainPageController extends BaseController{
	@Autowired
	@Qualifier("systemCategory")
	private String systemCategory;
	
	@Autowired
	@Qualifier("logoutUrl")
	private String logoutUrl;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	@Qualifier("HomeServiceImpl-API")
	private HomeService homeService;
	
	@RequestMapping(value = "/")
	public String root(HttpServletRequest request, ModelMap modelMap) {
		return "redirect:index";
	}

	/**
	 * 桌面页面
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, ModelMap modelMap) {
		User user = super.getCurrentUser();
		modelMap.put("currentUser", user);
	
		String themeColor = user.getExtendProperty("themeColor");
		if(themeColor == null){
			themeColor = "red";
		}
		modelMap.put("theme", themeColor);
		modelMap.put("systemCategory", systemCategory);
		
		return "main";
	}
	
	@RequestMapping(value = "/menus")
	@ResponseBody
	public List<MenuNode> menus(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		List<MenuNode> menuNodeList = homeService.getTopMenus(request);
        List<MenuNode> mlist1 = new ArrayList<MenuNode>();
        mlist1.add(new MenuNode("1","我的事务","0","./home/index","demo"));
        mlist1.add(new MenuNode("2","我的桌面","0","./mydesktop/index","demo"));
        mlist1.addAll(menuNodeList);
           
        return mlist1;
	}
	
	@RequestMapping(value = "/getLogoutUrls")
	@ResponseBody
	public List<String> getLogoutUrls(HttpServletRequest request, ModelMap modelMap) {
		Set<String> logoutUrlSet = new HashSet<String>();
		
		List<Resource> appResouceList = resourceService.getModuleResourcesOfCurrentUser(request);
		
		String appUrl = null;
		for(Resource appResouce: appResouceList){
			appUrl = appResouce.getUrl();
			if(appUrl == null || appUrl.trim().equals("")){
				continue;
			}
			appUrl = appUrl.replaceAll(".*//", "");
			appUrl = appUrl.substring(appUrl.indexOf("/") + 1);
			appUrl = appUrl.replaceAll("/.*", "");
			
			if(appUrl == null || appUrl.trim().equals("") || appUrl.equals("home")){
				continue;
			}
			appUrl = new StringBuffer("/").append(appUrl).append(this.logoutUrl).toString();
			logoutUrlSet.add(appUrl);
		}
		
		List<String> logoutUrls = new ArrayList<String>();
		logoutUrls.addAll(logoutUrlSet);
		
		return logoutUrls;
	}
	
	@RequestMapping(value = "/proxyUrl")
	public String getProxyUrl(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		String resourceId = request.getParameter("resourceId");
		Resource resource = resourceService.getResourceById(resourceId);
		
		String targetUrl = resource.getUrl();
		
		if(targetUrl == null || targetUrl.trim().equals("") == true){
			return "";
		}
		
		if(targetUrl.startsWith("http") == false){
			targetUrl = request.getRequestURL().toString().replaceAll("/home[0-9]{0,1}/proxyUrl", targetUrl);
		}
		final CasAuthenticationToken token = (CasAuthenticationToken) request.getUserPrincipal();
		// proxyTicket could be reused to make calls to the CAS service even if the
		// target url differs
		//final String proxyTicket = token.getAssertion().getPrincipal().getProxyTicketFor(targetUrl);
		// Make a remote call using the proxy ticket
		
		//String serviceUrl = targetUrl+"?ticket=" + URLEncoder.encode(proxyTicket, "UTF-8");
		//if(targetUrl.contains("?") == true){
		//	serviceUrl = targetUrl+"&ticket=" + URLEncoder.encode(proxyTicket, "UTF-8");
		//}
		
		return "redirect:" + targetUrl;
	}
	
//	@RequestMapping(value = "/proxyUrl")
//	public String getProxyUrl(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
//		String resourceId = request.getParameter("resourceId");
//		Resource resource = resourceService.getResourceById(resourceId);
//		
//		String targetUrl = resource.getUrl();
//		
//		if(targetUrl == null || targetUrl.trim().equals("") == true){
//			return "";
//		}
//		
//		if(targetUrl.startsWith("http") == false){
//			targetUrl = request.getRequestURL().toString().replaceAll("/home[0-9]*/proxyUrl", targetUrl);
//		}
//		
//		return "redirect:" + targetUrl;
//	}
	
	@RequestMapping(value = "/session-timeout")
	@ResponseBody
	public String sessionTimeout(HttpServletRequest request, ModelMap modelMap) {
		User currentUser = super.getCurrentUser();
		
		if(currentUser != null){
			return SUCCESS;
		}
		
		return "ERROR";
	}
	@RequestMapping(value = "/saveThemeColor")
	@ResponseBody
	public String saveThemecolor(HttpServletRequest request, ModelMap modelMap) {
		User currentUser = super.getCurrentUser();
		String themeColor = request.getParameter("themeColor");
		currentUser.putExtendProperty("themeColor", themeColor);
		userService.saveUser(currentUser);
		
		return SUCCESS;
	}
}
