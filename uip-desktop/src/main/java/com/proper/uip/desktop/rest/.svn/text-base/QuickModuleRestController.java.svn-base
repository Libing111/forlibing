/* <p>文件名称: QuickModuleRestController.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-11-24</p>
 * <p>完成日期：2014-11-24</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-11-24上午10:24:58
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.desktop.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.uip.api.desktop.entity.QuickApplicationEntity;
import com.proper.uip.api.desktop.service.SystemCategoryStrategy;
import com.proper.uip.api.desktop.wrapper.QuickModuleList;
import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.service.ResourceService;
import com.proper.uip.api.security.wrapper.ResourceList;
import com.proper.uip.desktop.service.QuickModuleService;
import com.proper.uip.web.BaseController;

@Controller
public class QuickModuleRestController extends BaseController {
	@Autowired
	private QuickModuleService quickModuleService;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired(required=false)
	private Map<String,SystemCategoryStrategy>  systemCategoryStrategyMap;
	
	@RequestMapping(method=RequestMethod.GET, value="/rest/quickModules", headers="Accept=application/json")
	@ResponseBody
	public QuickModuleList getGuickModules(HttpServletRequest request, ModelMap model) {
		String systemCategory = request.getParameter("systemCategory");
		SystemCategoryStrategy systemCategoryStrategy = systemCategoryStrategyMap.get(systemCategory);
		List<String> resourceCategoryList = systemCategoryStrategy.getResouceCategoryCodeList();
		
		User currentUser = super.getCurrentUser();
		String userId = currentUser.getId();
		Set<String> resourceId = new HashSet<String>();
		List<Resource> applicationList = quickModuleService.getResourcesOfCurrentUser(currentUser, "module", resourceCategoryList);
		
		for (Resource resource : applicationList) {
			resourceId.add(resource.getId());
		}
		List<QuickApplicationEntity> quickModules = quickModuleService.getQuickModules(userId);
		List<QuickApplicationEntity> quickModulesnews = new ArrayList<QuickApplicationEntity>();
		for (QuickApplicationEntity quickApplication : quickModules) {
			if(resourceId.contains(quickApplication.getResourceId()) == false){
				continue;
			}
			quickModulesnews.add(quickApplication);
		}
		
		QuickModuleList quickModuleList = new QuickModuleList(quickModulesnews);
		
		return quickModuleList;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/rest/quickModuleCandidates", headers="Accept=application/json")
	@ResponseBody
	public ResourceList getQuickModuleCandidates(HttpServletRequest request, ModelMap model) {
		String systemCategory = request.getParameter("systemCategory");
		SystemCategoryStrategy systemCategoryStrategy = systemCategoryStrategyMap.get(systemCategory);
		List<String> resourceCategoryList = systemCategoryStrategy.getResouceCategoryCodeList();
		
		User currentUser = super.getCurrentUser();
		
		List<Resource> applicationList = quickModuleService.getResourcesOfCurrentUser(currentUser, "module", resourceCategoryList);
		
		List<QuickApplicationEntity> quickApplicationEntityList = quickModuleService.getQuickModules(currentUser.getId());
		Map<String,Resource> resourceIdMap = new HashMap<String, Resource>();
		Resource quickResource = null;
		for (QuickApplicationEntity quickApplication : quickApplicationEntityList) {
			quickResource = resourceService.getResourceById(quickApplication.getResourceId());
			resourceIdMap.put(quickApplication.getResourceId(), quickResource);
		}
		//用户没有的资源菜单
		List<Resource> resourceList = new ArrayList<Resource>();
		for (Resource resource : applicationList) {
			if(resourceIdMap.containsKey(resource.getId())){
				continue;
			}
			resourceList.add(resource);
		}
		
		ResourceList theResourceList = new ResourceList(resourceList);
		
		return theResourceList;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/rest/quickModule/{resourceId}", headers="Accept=application/json")
	@ResponseBody
	public QuickApplicationEntity getQuickModuleByResourceId(HttpServletRequest request, ModelMap model, @PathVariable String resourceId) {	
		User currentUser = super.getCurrentUser();
		String userId = currentUser.getId();
		
		QuickApplicationEntity quickModule = quickModuleService.getQuickModuleByResourceId(userId, resourceId);
		
		return quickModule;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/rest/quickModule", headers="Accept=application/json")
	@ResponseBody
	public QuickApplicationEntity addQuickModule(HttpServletRequest request, ModelMap model, @RequestBody QuickApplicationEntity quickModule) {
		QuickApplicationEntity theQuickModule = quickModuleService.save(quickModule);
		
		return theQuickModule;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/rest/quickModule/{id}", headers="Accept=application/json")
	public void deleteQuickModule(HttpServletRequest request, ModelMap model, @PathVariable String id) {
		quickModuleService.delete(id);
	}
}

