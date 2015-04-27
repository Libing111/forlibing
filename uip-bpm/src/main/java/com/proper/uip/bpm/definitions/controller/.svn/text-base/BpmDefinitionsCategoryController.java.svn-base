package com.proper.uip.bpm.definitions.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionManagementEntity;
import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionsCategoryEntity;
import com.proper.uip.bpm.definitions.entity.DefinitionsCategoryNode;
import com.proper.uip.bpm.definitions.service.BpmDefinitionsCategoryService;
import com.proper.uip.bpm.definitions.service.ProcessDefinitionManagementService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.web.BaseController;

@Controller
@RequestMapping(value = "/definitions/category")
public class BpmDefinitionsCategoryController extends BaseController{
	
	@Autowired
	private BpmDefinitionsCategoryService bpmDefinitionsCategoryService;
	
	@Autowired
	private ProcessDefinitionManagementService processDefinitionManagementService;
	
	@RequestMapping("/index")
	public String index(ModelMap model) {
		return "/definitions/category/index";
	}
	
	@RequestMapping(value = "/tree/query")
	@ResponseBody
	public List<DefinitionsCategoryNode> queryTree(HttpServletRequest request, ModelMap model) {
		
		List<DefinitionsCategoryNode> nodeList = bpmDefinitionsCategoryService.buildTree();
		
		return nodeList;
	}
	
	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, String parent) {
		String name = request.getParameter("name");
		PageConfig pageConfig = this.createPageConfig(request);
		
		Page<ProcessDefinitionsCategoryEntity> processDefinitionsCategoryPage = bpmDefinitionsCategoryService.getDefinitionsCategory(pageConfig, parent,name);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pager.pageNo", processDefinitionsCategoryPage.getPageNo());
		map.put("pager.totalRows", processDefinitionsCategoryPage.getTotal());
		map.put("rows", processDefinitionsCategoryPage.getRows());
		
		return map;
	}
	/**
	 * 增加
	 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, ModelMap model) {
		String parentExtendId = request.getParameter("parentExtendId");

		model.put("parentExtendId", parentExtendId);
	
		return "/definitions/category/add";
	}
	/**
	 * 修改
	 */
	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request,String parentExtendId, String id, ModelMap model) {
		ProcessDefinitionsCategoryEntity definitionsCategory = bpmDefinitionsCategoryService.getById(id);
		

		model.put("definitionsCategory", definitionsCategory);
		model.put("parentExtendId", definitionsCategory.getParentId());
		
		return "/definitions/category/add";
	}
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(HttpServletRequest request, ProcessDefinitionsCategoryEntity processDefinitionsCategoryEntity,String parentExtendId, ModelMap model) {
		//新增
		if (processDefinitionsCategoryEntity.getId() == null || processDefinitionsCategoryEntity.getId().equals("")) {
			processDefinitionsCategoryEntity.setId(null);
		}
		if(parentExtendId != null && parentExtendId.equals("") == false){
			ProcessDefinitionsCategoryEntity processDefinitionsCategory = bpmDefinitionsCategoryService.getById(parentExtendId);
			processDefinitionsCategoryEntity.setParentId(parentExtendId);
			processDefinitionsCategoryEntity.setParentName(processDefinitionsCategory.getName());
			processDefinitionsCategoryEntity.setParentCode(processDefinitionsCategory.getCode());
		}
		bpmDefinitionsCategoryService.save(processDefinitionsCategoryEntity);

		return SUCCESS;
	}
	/**
	 * 删除
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public String del(HttpServletRequest request,String id, ModelMap model) {
		List<ProcessDefinitionsCategoryEntity> definitionsCategoryList = bpmDefinitionsCategoryService.getChildren(id);
		if(definitionsCategoryList != null){
			bpmDefinitionsCategoryService.deleteChild(id);
			bpmDefinitionsCategoryService.deleteById(id);
			processDefinitionManagementService.deleteByCategoryId(id);
		}
		bpmDefinitionsCategoryService.deleteById(id);
		processDefinitionManagementService.deleteByCategoryId(id);
	
		return SUCCESS;
	}
	/**
	 * 删除验证
	 */
	@RequestMapping(value = "/validateDel")
	@ResponseBody
	public String validateDel(HttpServletRequest request,String id, ModelMap model) {
		List<ProcessDefinitionManagementEntity> managementList = processDefinitionManagementService.getByCategoryId(id);
		if(managementList != null){
			return "此数据已经被使用，确定要删除吗！";
		}
	
		return SUCCESS;
	}
	
	/**
	 * 验证序号
	 */
	@RequestMapping(value = "/validateSequence")
	@ResponseBody
	public String validateSequence(HttpServletRequest request, String sequence,String parentExtendId,ModelMap model) {
		
		ProcessDefinitionsCategoryEntity processDefinitionsCategory = bpmDefinitionsCategoryService.getBySequence(Integer.parseInt(sequence),parentExtendId);
		if(processDefinitionsCategory != null){
			return "ERROR";
			
		}
		return SUCCESS;
	}
	
	/**
	 * 验证名称
	 */
	@RequestMapping(value = "/validateName")
	@ResponseBody
	public String validateName(HttpServletRequest request, String name, ModelMap model) {
		
		ProcessDefinitionsCategoryEntity processDefinitionsCategory = bpmDefinitionsCategoryService.validateName(name);
		if(processDefinitionsCategory != null){
			return "ERROR";
			
		}
		return SUCCESS;
	}
	/**
	 * 验证编号
	 */
	@RequestMapping(value = "/validateCode")
	@ResponseBody
	public String validateCode(HttpServletRequest request, String code, String parentExtendId, ModelMap model) {
		
		ProcessDefinitionsCategoryEntity processDefinitionsCategory = bpmDefinitionsCategoryService.validateCode(code);
		if(processDefinitionsCategory != null){
			return "ERROR";
			
		}
		return SUCCESS;
	}
}
