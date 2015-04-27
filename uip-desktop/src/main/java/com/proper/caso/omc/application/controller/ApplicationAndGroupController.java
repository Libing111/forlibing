package com.proper.caso.omc.application.controller;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.caso.omc.systemPreferences.entity.SystemPreferences;
import com.proper.caso.omc.systemPreferences.service.SystemPreferencesService;
import com.proper.uip.api.desktop.entity.ApplicationAndGroup;
import com.proper.uip.api.desktop.entity.ApplicationAndGroupTreeNode;
import com.proper.uip.api.desktop.service.ApplicationAndGroupService;
import com.proper.uip.api.desktop.service.SystemCategoryStrategy;
import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.service.ResourceService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.web.BaseController;


@Controller
@RequestMapping(value = "/desktop-configuration")
public class ApplicationAndGroupController extends BaseController{
	@Autowired
	private ApplicationAndGroupService applicationAndGroupService;
	
	@Autowired
	private SystemPreferencesService systemPreferencesService;
	
	@Autowired
	private ResourceService resourceService;
	
	private String group = "group";
	
	@Autowired(required=false)
	Map<String,SystemCategoryStrategy>  systemCategoryStrategyMap;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request, ModelMap model) {
		String systemCategory = request.getParameter("systemCategory");
		
		model.put("systemCategory", systemCategory);
		
		return "/applicationAndGroup/index";
	}
	
	@RequestMapping(value = "/tree/query")
	@ResponseBody
	public List<ApplicationAndGroupTreeNode> queryTree(HttpServletRequest request, ModelMap model) {
		String systemCategory = request.getParameter("systemCategory");
		List<ApplicationAndGroupTreeNode> treeNodeList = applicationAndGroupService.buildTree(systemCategory);
		
		return treeNodeList;
	}
	
	/**
	 * 增加
	 */

	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, String parent, String id, ModelMap model) {
		String parentExtendId = request.getParameter("parentExtendId");
		String systemCategory = request.getParameter("systemCategory");
		
		model.put("parent", parent);
		model.put("parentExtendId", parentExtendId);
		model.put("group", group);
		model.put("systemCategory", systemCategory);
		return "/applicationAndGroup/newStandard";
	}
	/**
	 * 增加
	 */

	@RequestMapping(value = "/addResource")
	public String addResource(HttpServletRequest request, String parent, String id, ModelMap model) {
		String parentExtendId = request.getParameter("parentExtendId");
		String moc = "module";
		String systemCategory = request.getParameter("systemCategory");
		
		SystemCategoryStrategy strategy = getSystemCategoryStrategy(systemCategory);
		if(strategy == null){
			model.put("systemCategory", systemCategory);
			model.put("parent", parent);
			model.put("parentExtendId", parentExtendId);
			return "/applicationAndGroup/newStandardResource";
		}
		List<String> resourceCategoryList = strategy.getResouceCategoryCodeList();
		
		if(resourceCategoryList == null){
			model.put("systemCategory", systemCategory);
			model.put("parent", parent);
			model.put("parentExtendId", parentExtendId);
			return "/applicationAndGroup/newStandardResource";	
		}
		
		List<Resource> resourceList = resourceService.getResourcesByMoc(moc, resourceCategoryList);
		
		model.put("systemCategory", systemCategory);
		model.put("resourceList", resourceList);
		model.put("parent", parent);
		model.put("parentExtendId", parentExtendId);
		return "/applicationAndGroup/newStandardResource";
	}

	/**
	 * 修改
	 */

	@RequestMapping(value = "/updateResource")
	public String updateResource(HttpServletRequest request, String parent, String id, ModelMap model) {
		String parentExtendId = request.getParameter("parentExtendId");
		ApplicationAndGroup applicationAndGroup = applicationAndGroupService.getBId(id);
		String moc = "module";
		String systemCategory = request.getParameter("systemCategory");
		
		SystemCategoryStrategy strategy = getSystemCategoryStrategy(systemCategory);
		if(strategy == null){
			model.put("systemCategory", systemCategory);
			model.put("applicationAndGroup", applicationAndGroup);
			model.put("parent", parent);
			model.put("parentExtendId", parentExtendId);
			model.put("group", group);
			
			return "/applicationAndGroup/newStandardResource";
		}
		List<String> resourceCategoryList = strategy.getResouceCategoryCodeList();
		
		if(resourceCategoryList == null){
			model.put("systemCategory", systemCategory);
			model.put("applicationAndGroup", applicationAndGroup);
			model.put("parent", parent);
			model.put("parentExtendId", parentExtendId);
			model.put("group", group);
			
			return "/applicationAndGroup/newStandardResource";
		}
		
		List<Resource> resourceList = resourceService.getResourcesByMoc(moc, resourceCategoryList);
		
		model.put("systemCategory", systemCategory);
		model.put("resourceList", resourceList);
		model.put("applicationAndGroup", applicationAndGroup);
		model.put("parent", parent);
		model.put("parentExtendId", parentExtendId);
		model.put("group", group);
		
		return "/applicationAndGroup/newStandardResource";
	}
	
	/**
	 * 修改
	 */

	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request, String parent, String id, ModelMap model) {
		String parentExtendId = request.getParameter("parentExtendId");
		ApplicationAndGroup applicationAndGroup = applicationAndGroupService.getBId(id);
		String systemCategory = request.getParameter("systemCategory");
		
		model.put("systemCategory", systemCategory);
		model.put("applicationAndGroup", applicationAndGroup);
		model.put("parent", parent);
		model.put("parentExtendId", parentExtendId);
		model.put("group", group);
		
		return "/applicationAndGroup/newStandard";
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(HttpServletRequest request, ApplicationAndGroup applicationAndGroupEntity,String parentExtendId, ModelMap model) {
		String resourceId = request.getParameter("resourceId");
		String systemCategory = request.getParameter("systemCategory");

		String moc = applicationAndGroupService.getChildMocByMoc(null,resourceId);
		ApplicationAndGroup applicationAndGroupOld = applicationAndGroupService.getByParentAndSesequenceNumber(parentExtendId, applicationAndGroupEntity.getSequenceNumber(),systemCategory, moc);
		
		if(parentExtendId != null && parentExtendId.trim().equals("") == false){
				ApplicationAndGroup parentApplicationAndGroup = applicationAndGroupService.getBId(parentExtendId);
				
				moc = applicationAndGroupService.getChildMocByMoc(parentApplicationAndGroup.getMoc(),resourceId);
				applicationAndGroupOld = applicationAndGroupService.getByParentAndSesequenceNumber(parentExtendId, applicationAndGroupEntity.getSequenceNumber(),systemCategory, moc);
		}
		if (applicationAndGroupOld != null && applicationAndGroupOld.getId().equals(applicationAndGroupEntity.getId()) == false) {
			return "此序号已经被使用！请重新填写！";
		}
		applicationAndGroupEntity.setMoc(moc);
		applicationAndGroupEntity.setParent(null);
		applicationAndGroupEntity.setSystemCategory(systemCategory);
		
		if(parentExtendId != null && parentExtendId.trim().equals("") == false){
			Resource resource = resourceService.getResourceById(resourceId);
			if(resource != null){
				ApplicationAndGroup parentApplicationAndGroup = applicationAndGroupService.getBId(parentExtendId);
				
				moc = applicationAndGroupService.getChildMocByMoc(parentApplicationAndGroup.getMoc(),resourceId);
				applicationAndGroupEntity.setMoc(moc);
				applicationAndGroupEntity.setParent(parentApplicationAndGroup.getId());
				applicationAndGroupEntity.setResourceName(resource.getSimpleName());
				applicationAndGroupEntity.setResourceCode(resource.getCode());
				applicationAndGroupEntity.setName(resource.getSimpleName());	
			}
			ApplicationAndGroup parentApplicationAndGroup = applicationAndGroupService.getBId(parentExtendId);
			
			moc = applicationAndGroupService.getChildMocByMoc(parentApplicationAndGroup.getMoc(),resourceId);
			applicationAndGroupEntity.setMoc(moc);
			applicationAndGroupEntity.setParent(parentApplicationAndGroup.getId());
			applicationAndGroupEntity.setSystemCategory(systemCategory);
		}
			
		//新增
		if (applicationAndGroupEntity.getId() == null || applicationAndGroupEntity.getId().equals("")) {
			applicationAndGroupEntity.setId(null);
		}

		applicationAndGroupService.saveApplicationAndGroup(applicationAndGroupEntity);

		return SUCCESS;
	}

	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, String parent) {
		String name = request.getParameter("name");
		PageConfig pageConfig = this.createPageConfig(request);
		String systemCategory = request.getParameter("systemCategory");
		if (parent == null || parent.trim().equals("")) {
			String moc = "paging";
			Page<ApplicationAndGroup> applicationAndGroup = applicationAndGroupService.getPaging(pageConfig,moc,systemCategory,name);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pager.pageNo", applicationAndGroup.getPageNo());
			map.put("pager.totalRows", applicationAndGroup.getTotal());
			map.put("rows", applicationAndGroup.getRows());
			
			return map;
		}
		ApplicationAndGroup applicationAndGroup = applicationAndGroupService.getByParent(parent, systemCategory);
		Page<ApplicationAndGroup> applicationAndGroupList = applicationAndGroupService.getApplicationAndGroup(pageConfig, applicationAndGroup.getId(), systemCategory,name);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pager.pageNo", applicationAndGroupList.getPageNo());
		map.put("pager.totalRows", applicationAndGroupList.getTotal());
		map.put("rows", applicationAndGroupList.getRows());
		
		return map;
	}
	
	/**
	 * 资源删除
	 */

	@RequestMapping(value = "/del")
	@ResponseBody
	public String del(HttpServletRequest request,String id, ModelMap model) {
		String systemCategory = request.getParameter("systemCategory");
		ApplicationAndGroup applicationAndGroup = applicationAndGroupService.getByParent(id,systemCategory);
		if(applicationAndGroup != null){
			applicationAndGroupService.deleteById(id);	
		}
		applicationAndGroupService.deleteById(id);
	
		return SUCCESS;
	}
	private SystemCategoryStrategy getSystemCategoryStrategy(
			String systemCategory) {
		if(systemCategoryStrategyMap != null){
			SystemCategoryStrategy strategy = systemCategoryStrategyMap.get(systemCategory);
			
			return strategy;	
		}
		SystemCategoryStrategy strategy = new SystemCategoryStrategy();
		String key = "resouceCategoryList";
		SystemPreferences systemPreferences = systemPreferencesService.getSp(systemCategory,key);
		
		String resouceCategoryCode = systemPreferences.getValue();
		String [] resouceCategoryList = resouceCategoryCode.split(",");
		
		List<String> resouceCategoryCodeList = Arrays.asList(resouceCategoryList);
		
		strategy.setSystemCategoryCode(systemPreferences.getSystemCategory());
		strategy.setResouceCategoryCodeList(resouceCategoryCodeList);

		return strategy;
	}
	
}
