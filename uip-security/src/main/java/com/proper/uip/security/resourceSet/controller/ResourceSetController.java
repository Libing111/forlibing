package com.proper.uip.security.resourceSet.controller;


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

import com.proper.uip.api.security.entity.RaResourceSetRelation;
import com.proper.uip.api.security.entity.ResourceSet;
import com.proper.uip.api.security.entity.ResourceTreeNode;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.registrationAuthority.service.RaResourceSetRelationService;
import com.proper.uip.security.resourceSet.service.ResourceResourceSetRelationService;
import com.proper.uip.security.resourceSet.service.ResourceSetService;
import com.proper.uip.web.BaseController;

@Controller
@RequestMapping(value = "/resourceSet")
public class ResourceSetController extends BaseController{
	
	
	@Autowired
	private ResourceSetService resourceSetService;
	
	@Autowired
	private ResourceResourceSetRelationService resourceResourceSetRelationService;
	
	@Autowired
	private RaResourceSetRelationService raResourceSetRelationService;
	
	@RequestMapping("/index")
	public String index(ModelMap model) {
		
		return "/resourceSet/index";
	}
	
	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, String name, ModelMap model) {
		PageConfig pageConfig = this.createPageConfig(request);
		Page<ResourceSet> roleList = resourceSetService.findAllResourceSet(pageConfig, name);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pager.pageNo", roleList.getPageNo());
		map.put("pager.totalRows", roleList.getTotal());
		map.put("rows", roleList.getRows());
		
		return map;
	}
	/**
	 * 增加
	 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request,ModelMap model) {
		
		return "/resourceSet/add";
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value = "/update")
	public String update(String id, ModelMap model) {
		ResourceSet resourceSetEntity = resourceSetService.getById(id);
		
		model.addAttribute("resourceSetEntity", resourceSetEntity);
		
		return "/resourceSet/update";
	}
	/**
	 * 保存资源库
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(ResourceSet resourceSetEntity, ModelMap model) {
		//新增
		if (resourceSetEntity.getId() == null || resourceSetEntity.getId().equals("")) {
			resourceSetEntity.setId(null);
		}
		resourceSetService.saveResourceSet(resourceSetEntity);

		return SUCCESS;

	}
	/**
	 * 保存
	 */
	@RequestMapping(value = "/updateSave")
	@ResponseBody
	public String updateSave(HttpServletRequest request,ResourceSet resourceSet, String id, ModelMap model) {
		ResourceSet resourceSetEntity = resourceSetService.getById(id);
		
		resourceSetEntity.setName(resourceSet.getName());
		resourceSetEntity.setCode(resourceSet.getCode());
		resourceSetEntity.setDescription(resourceSet.getDescription());
		
		resourceSetService.saveResourceSet(resourceSetEntity);
		
		return SUCCESS;

	}
	@RequestMapping(value = "/resourceTree/query")
	@ResponseBody
	public List<ResourceTreeNode> queryTree(String resourceSetId, boolean chkDisabled, HttpServletRequest request, ModelMap model) {
		List<ResourceTreeNode> resourceTreeNodeList = resourceSetService.buildResourceTree(resourceSetId, chkDisabled);
		return resourceTreeNodeList;
	}
	/**
	 * 添加角色树管理
	 */
	@RequestMapping(value = "/tree/update")
	@ResponseBody
	public String  updateTree(String resourceSetId, String resourceSequenceNumbers, ModelMap model) {
		
		resourceResourceSetRelationService.updateResourceResourceSetRelations(resourceSetId, resourceSequenceNumbers);
		resourceResourceSetRelationService.updateRoleResource();
		resourceResourceSetRelationService.deleteRoleResource(resourceSetId);
		return SUCCESS;
	}
	
	
	/**
	 * 删除
	 */

	@RequestMapping(value = "/del")
	public String del(String ids, ModelMap model) {
		try {
			if (StringUtils.isNotEmpty(ids)) {
				for (String id : ids.split(",")) {
					List<RaResourceSetRelation> raResourceSetRelation = raResourceSetRelationService.getByResourceSetId(id);
					if(raResourceSetRelation.size() > 0){
						model.put("status", -1);
						this.setError(model, "删除失败,此资源集正在使用，不可以删除！");
						return SUCCESS;
					}
					
					resourceSetService.deleteResourceSetById(id);
					resourceResourceSetRelationService.deleteRelation(id);
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
