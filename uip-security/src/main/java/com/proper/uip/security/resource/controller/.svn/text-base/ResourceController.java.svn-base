package com.proper.uip.security.resource.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.ResourceCategory;
import com.proper.uip.api.security.entity.ResourceTreeNode;
import com.proper.uip.api.security.service.ResourceCategoryService;
import com.proper.uip.api.security.service.SecurityService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.resource.service.ResourceService;
import com.proper.uip.web.BaseController;

@Controller
@RequestMapping(value = "/resource")
public class ResourceController extends BaseController {

	@Autowired
	private ResourceService resourceSerivce;

	@Autowired
	private SecurityService securityService;
	
	private String subsystem = "subsystem";

	@Autowired
	private ResourceCategoryService resourceCategoryService;
	

	
	@RequestMapping("/index")
	public String index(ModelMap model) {
		List<ResourceCategory> categoryList = resourceCategoryService.getAllResourceCategorys();
		model.put("categoryList", categoryList);
		List<ResourceTreeNode> resourceTreeNodeList = resourceSerivce.buildResourceTree();
		model.put("resourceTreeNodeList", resourceTreeNodeList);
		return "/resource/index";
	}
	
	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, ModelMap model) {
		Page<Resource> resourceList = resourceSerivce.findAllResource(this.createPageConfig(request));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pager.pageNo", resourceList.getPageNo());
		map.put("pager.totalRows", resourceList.getTotal());
		map.put("rows", resourceList.getRows());
		
		return map;
	}
	
	@RequestMapping(value = "/tree/query")
	@ResponseBody
	public List<ResourceTreeNode> queryTree(HttpServletRequest request, ModelMap model) {
		List<ResourceTreeNode> resourceTreeNodeList = resourceSerivce.buildResourceTree();
		return resourceTreeNodeList;
	}
	/**
	 * 模块列表查询
	 * @param parent
	 * @param moc
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/model/query")
	@ResponseBody
	public Map<String, Object> modelQuery(HttpServletRequest request, String parentId, String name, String categoryName) {
		PageConfig pageConfig = this.createPageConfig(request);
		if (parentId == null || parentId.trim().equals("")) {
			return new HashMap<String, Object>();
		}
		Page<Resource> resourceList = resourceSerivce.getResourcesOfModel(pageConfig, parentId,name, categoryName);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pager.pageNo", resourceList.getPageNo());
		map.put("pager.totalRows", resourceList.getTotal());
		map.put("rows", resourceList.getRows());
		
		return map;
	}
	
	/**
	 * 模块列表查询
	 * @param parent
	 * @param moc
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/operation/query")
	@ResponseBody
	public List<Resource> operationQuery(String parent, String moc, HttpServletRequest request, ModelMap model) {
		List<Resource> resourceList = securityService.getResourcesOfCurrentUser(request, parent, moc);
		return resourceList;
	}




	/**
	 * 保存资源
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(Resource resourceEntity,String result, String parentCode, ModelMap model) {
		boolean anonymously = Boolean.parseBoolean(result);
		Resource resource = resourceSerivce.getCode(resourceEntity.getCode());
		if (resource != null && resource.getId().equals(resourceEntity.getId()) == false) {
			return "此编号已经被使用！请重新填写！";
		}
		
		resource = resourceSerivce.getResource(resourceEntity.getSequenceNumber(),parentCode);
		
		if (resource != null && resource.getId().equals(resourceEntity.getId()) == false) {
			return "此序号已经被使用！请重新填写！";
		}
		resource = resourceSerivce.getByName(resourceEntity.getName());
		
		if (resource != null && resource.getId().equals(resourceEntity.getId()) == false) {
			return "此名称已经被使用！请重新填写！";
		}
		
		Resource parentResource = resourceSerivce.getResourceByParent(parentCode);
		String moc = resourceSerivce.getChildResourceMocByMoc(parentResource.getMoc());
		resourceEntity.setMoc(moc);
		resourceEntity.setParent(parentResource.getCode());
		ResourceCategory ResourceCategory = resourceCategoryService.getByCode(resourceEntity.getCategoryCode());
		resourceEntity.setCategoryId(ResourceCategory.getId());
		resourceEntity.setCategoryCode(ResourceCategory.getCode());
		resourceEntity.setCategoryName(ResourceCategory.getName());
		if(anonymously){
			
		}
		resourceEntity.setAnonymously(anonymously);
	
		//新增
		if (resourceEntity.getId() == null || resourceEntity.getId().equals("")) {
			resourceEntity.setId(null);
			
			resourceSerivce.saveResource(resourceEntity);
			
			return SUCCESS;
		}
		//修改
		if(resource == null){
			resource = resourceSerivce.getResourceById(resourceEntity.getId());
		}
		
		
		resource.setCategoryCode(resourceEntity.getCategoryCode());
		resource.setCategoryId(resourceEntity.getCategoryId());
		resource.setAnonymously(resourceEntity.isAnonymously());
		resource.setCategoryName(resourceEntity.getCategoryName());
		resource.setCode(resourceEntity.getCode());
		resource.setDescription(resourceEntity.getDescription());
		resource.setIcon(resourceEntity.getIcon());
		resource.setId(resourceEntity.getId());
		resource.setMoc(resourceEntity.getMoc());
		resource.setName(resourceEntity.getName());
		resource.setParent(resourceEntity.getParent());
		resource.setParentId(resourceEntity.getParentId());
		resource.setSequenceNumber(resourceEntity.getSequenceNumber());
		resource.setUrl(resourceEntity.getUrl());
		resource.setSimpleName(resourceEntity.getSimpleName());
		resourceSerivce.saveResource(resource);
		return SUCCESS;

	}
	

	/**
	 * 资源修改
	 */

	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request, String parent, String id, ModelMap model) {
		String parentCode = request.getParameter("parentCode");
		List<ResourceCategory> categoryList = resourceCategoryService.getAllResourceCategorys();
		Resource resourceEntity = resourceSerivce.getResourceById(id);
		
		model.put("parent", parent);
		model.put("subsystem", subsystem);
		model.addAttribute("resourceEntity", resourceEntity);
		model.put("parentCode", parentCode);
		model.put("categoryList", categoryList);
		
		return "/resource/updateForm";
	}
	/**
	 * 资源增加
	 */

	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, String parent, String id, ModelMap model) {
		List<ResourceCategory> categoryList = resourceCategoryService.getAllResourceCategorys();
		String parentCode = request.getParameter("parentCode");
		
		model.put("parent", parent);
		model.put("parentCode", parentCode);
		model.put("subsystem", subsystem);
		model.put("categoryList", categoryList);
		
		return "/resource/newStandard";
	}

	/**
	 * 资源删除
	 */

	@RequestMapping(value = "/del")
	public String del(String ids, ModelMap model) {
		try {
			if (StringUtils.isNotEmpty(ids)) {
				for (String id : ids.split(",")) {
					resourceSerivce.deleteResourceById(id);
				}
			}
		} catch (Exception e) {
			model.put("status", -1);
			this.setError(model, "删除失败,请联系管理员");
		}
		
		model.put("status", 1);
		
		return SUCCESS;
	}


}
