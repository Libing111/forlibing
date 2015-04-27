package com.proper.uip.security.operation.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.security.operation.service.OperationService;
import com.proper.uip.security.resource.service.ResourceService;
import com.proper.uip.web.BaseController;

@Controller
@RequestMapping(value = "/operation")
public class OperationController extends BaseController{
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	private ResourceService resourceSerivce;
	
	private String moduleMoc = "module"; 
	/**
	 * 模块修改
	 */

	@RequestMapping(value = "/update")
	public String update(String parent, String id, ModelMap model) {
		model.put("parent", parent);
		model.put("moduleMoc", moduleMoc);
		Resource operationEntity = operationService.getOperationById(id);
		model.addAttribute("operationEntity", operationEntity);

		return "/operation/updateForm";
	}
	
	/**
	 * 添加资源模块
	 */
	@RequestMapping(value = "/add")
	public String add(String parent, ModelMap model) {
		model.put("parent", parent);
		model.put("moduleMoc", moduleMoc);
		return "/operation/newStandard";
	}
	
	/**
	 * 保存模块
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(Resource operationEntity, ModelMap model) {
		
		if (operationEntity.getId().trim().equals("") == true) {
			Resource Name =resourceSerivce.getUserByName(operationEntity.getName());
			if(Name!=null){
				return "此名称已经占用,请重新填写！";
			}
			operationEntity.setId(null);
		}
		resourceSerivce.saveResource(operationEntity);

		return SUCCESS;

	}
	
	

	
}
