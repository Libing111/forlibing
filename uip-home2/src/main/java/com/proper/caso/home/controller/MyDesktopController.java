/* <p>文件名称: HomePageController.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-11-4</p>
 * <p>完成日期：2014-11-4</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-11-4下午3:29:06
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.caso.home.application.model.MenuNode;
import com.proper.caso.home.application.model.MyDesktopEntity;
import com.proper.caso.home.application.model.MyDesktopNode;
import com.proper.caso.home.service.HomeService;
import com.proper.caso.home.service.MyDesktopService;
import com.proper.uip.api.desktop.entity.ApplicationAndGroup;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.web.BaseController;

@Controller
@RequestMapping(value = "/mydesktop")
public class MyDesktopController extends BaseController{
	@Autowired
	@Qualifier("HomeServiceImpl-API")
	private HomeService homeService;
	
	@Autowired
	private MyDesktopService myDesktopService;

	/**
	 * 首页
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String home(HttpServletRequest request, ModelMap modelMap) {
		return "mydesktop";
	}
	
	
	@RequestMapping(value = "/getMyDesktop")
	@ResponseBody
	public List<MyDesktopEntity> getMyDesktop(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		List<MyDesktopEntity> myDesktopList = new ArrayList<MyDesktopEntity>();
		
		User user = super.getCurrentUser();
		if(user == null){
			return myDesktopList;
		}
		
		myDesktopList = myDesktopService.getMyDesktopModules(request);
		
        return myDesktopList;
	}
	
	@RequestMapping(value = "/add/index")
	public String addIndex(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		List<MenuNode> pagingList = homeService.getTopMenus(request);
		
		modelMap.put("pagingList", pagingList);
		return "mydesktop/add";
	}
	@RequestMapping(value = "/queryChildResource")
	@ResponseBody
	public Map<String,List<MyDesktopNode>> queryChildResource(HttpServletRequest request, ModelMap model) throws UnsupportedEncodingException {
		String pagingId = request.getParameter("pagingId");
		User user = this.getCurrentUser();
		List<MyDesktopEntity> myDesktopEntityList = myDesktopService.getMyDesktopApplications(request,user.getId());
		Set<String> resourceIdSet = new HashSet<String>();
		for (MyDesktopEntity myDesktopEntity : myDesktopEntityList) {
			resourceIdSet.add(myDesktopEntity.getResourceId());
		}
		
		List<ApplicationAndGroup> applicationAndGroupList = myDesktopService.getApplicationsByResourceId(resourceIdSet);
		Set<String> applicationAndGroupIdSet = new HashSet<String>();
		for (ApplicationAndGroup applicationAndGroup : applicationAndGroupList) {
			applicationAndGroupIdSet.add(applicationAndGroup.getId());
		}
		Map<String,List<MyDesktopNode>> myDesktopNodeMap = new HashMap<String, List<MyDesktopNode>>();
		
		List<MenuNode> menuNodeList = homeService.getModulesWithoutGroup(request, pagingId);
		
		List<MyDesktopNode> myDesktopList = new ArrayList<MyDesktopNode>();
		
		MyDesktopNode myDesktopNode = null;
		for (MenuNode menuNode : menuNodeList) {
			if(applicationAndGroupIdSet.contains(menuNode.getId())){
				continue;
			}
			myDesktopNode = new MyDesktopNode(menuNode);
			myDesktopList.add(myDesktopNode);
		}
		

		
		myDesktopNodeMap.put("list", myDesktopList);
		
		
		return myDesktopNodeMap;
		
	}
	/**
	 * 全选一行
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/clickParentResource")
	@ResponseBody
	public Map<String,String>  clickParentResource(HttpServletRequest request, ModelMap model) {
		Map<String, String> allResourceMap = new HashMap<String, String>();
		
		String pagingId = request.getParameter("pagingId");
		StringBuffer childResourceId = new StringBuffer();
		List<MenuNode> menuNodeList = homeService.getModulesWithoutGroup(request, pagingId);
		for (MenuNode menuNode : menuNodeList) {
			childResourceId.append(",").append(menuNode.getId());
		}
		
		allResourceMap.put(pagingId, childResourceId.toString());
		
		return allResourceMap;
	}
	
	@RequestMapping(value = "/add")
	@ResponseBody
	public List<MyDesktopEntity> add(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		String resourceIds = request.getParameter("resourceIds");
		List<String> resourceList = Arrays.asList(resourceIds.split(","));
		
		
		List<MyDesktopEntity> myDesktopEntityApplicationList = myDesktopService.addMyDesktopModule(resourceList,request);
		
		return myDesktopEntityApplicationList;
	}
	@RequestMapping(value = "/delete")
	@ResponseBody
	public void deleteModel(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		String modelId = request.getParameter("id");
		if(modelId == null){
			throw new IllegalArgumentException();
		}
		
		User user = super.getCurrentUser();
		if(user == null){
			throw new IllegalArgumentException();
		}
		
		myDesktopService.deleteQuickModule(request, modelId);
	}
	
	@RequestMapping(value = "/allResource")
	@ResponseBody
	public Map<String,String>  allResource(HttpServletRequest request, ModelMap model) throws UnsupportedEncodingException {
		Map<String, String> allResourceMap = new HashMap<String, String>();
		List<MenuNode> pagingList = homeService.getTopMenus(request);
		
		for (MenuNode pagingNode : pagingList) {
			StringBuffer childResourceId = new StringBuffer();
			List<MenuNode> menuNodeList = homeService.getModulesWithoutGroup(request, pagingNode.getId());
			for (MenuNode menuNode : menuNodeList) {
				childResourceId.append(",").append(menuNode.getId());
			}
			
			allResourceMap.put(pagingNode.getId(), childResourceId.toString());
		}
		return allResourceMap;
	}
}

