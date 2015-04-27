package com.proper.uip.security.resourceMocDefinition.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.uip.api.security.entity.ResourceMocDefinition;
import com.proper.uip.common.utils.Page;
import com.proper.uip.security.resourceMocDefinition.service.ResourceMocDefinitionService;
import com.proper.uip.web.BaseController;

@Controller
@RequestMapping(value = "/resourceMocDefinition")
public class ResourceMocDefinitionController extends BaseController {
	
	
	@Autowired
	private ResourceMocDefinitionService resourceMocDefinitionService;
	
	@RequestMapping("/index")
	public String index(ModelMap model) {
		return "/resourceMocDefinition/index";
	}
	
	@RequestMapping(value = "/query")
	@ResponseBody
	public Page<ResourceMocDefinition> query(HttpServletRequest request, ModelMap model) {
		Page<ResourceMocDefinition> resourceMocDefinitionList = resourceMocDefinitionService.findAllResourceMocDefinition(this.createPageConfig(request));
		return resourceMocDefinitionList;
	}

	/**
	 * 添加资源管理
	 */
	@RequestMapping(value = "/add")
	public String add(ModelMap model) {
		return "/resourceMocDefinition/newStandard";
	}

	/**
	 * 保存资源
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(ResourceMocDefinition resourceMocDefinitionEntity, ModelMap model) {
		
		if (resourceMocDefinitionEntity.getId().trim().equals("") == true) {
			ResourceMocDefinition Name =resourceMocDefinitionService.getByName(resourceMocDefinitionEntity.getName());
			if(Name!=null){
				return "此名称已经占用,请重新填写！";
			}
			resourceMocDefinitionEntity.setId(null);
		}

		resourceMocDefinitionService.saveResourceMocDefinition(resourceMocDefinitionEntity);

		return SUCCESS;

	}

	/**
	 * 资源修改
	 */

	@RequestMapping(value = "/update")
	public String update(String id, ModelMap model) {
		ResourceMocDefinition resourceMocDefinitionEntity = resourceMocDefinitionService.getResourceMocDefinitionById(id);
		model.addAttribute("resourceMocDefinitionEntity", resourceMocDefinitionEntity);

		return "/resourceMocDefinition/updateForm";
	}

	/**
	 * 资源删除
	 */

	@RequestMapping(value = "/del")
	public String del(String ids, ModelMap model) {
		try {
			if (StringUtils.isNotEmpty(ids)) {
				for (String id : ids.split(",")) {
					resourceMocDefinitionService.deleteResourceMocDefinitionById(id);
				}
			}
		} catch (Exception e) {
			return "删除失败,请联系管理员";
		}
		return SUCCESS;
	}
}
