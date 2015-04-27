package com.proper.uip.bpm.tasks.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionResponse;
import com.proper.uip.api.bpm.definitions.entity.TaskDefinitionEntity;
import com.proper.uip.bpm.tasks.service.BpmTasksStepManagementService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.web.BaseController;


@Controller
@RequestMapping(value = "/tasks/step/management")
public class BpmTasksStepManagementController extends BaseController{
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private BpmTasksStepManagementService bpmTasksStepManagementService;
	
	@RequestMapping("/index")
	public String index(ModelMap model) {
		return "/tasks/step/index";
	}
	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, ModelMap model) {
		//初始化返回值Map
		Map<String, Object> map = new HashMap<String, Object>();
		
		String name = request.getParameter("name");
		
		//初始化Page
		PageConfig pageConfig = super.createPageConfig(request);
		Page<ProcessDefinitionResponse> page = new Page<ProcessDefinitionResponse>(pageConfig);
		
		
		//取分页数据
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		if(name != null && name.trim().equals("") == false){
			query.processDefinitionNameLike("%" + name + "%");
		}
		//按照名称升序
		query.orderByProcessDefinitionName().asc();

		int pageNo = pageConfig.pageNo;
		int size = pageConfig.pageSize;
		int start = (pageNo - 1) * size;
		
		List<ProcessDefinition> processDefinitionList = query.listPage(start, size);
		
		List<ProcessDefinitionResponse> processResponseList = new ArrayList<ProcessDefinitionResponse>();
		for (ProcessDefinition processDefinition : processDefinitionList) {
			processResponseList.add(new ProcessDefinitionResponse((ProcessDefinitionEntity) processDefinition));
		}
		
		page.setRows(processResponseList);
		page.setTotal(query.count());
		
		//分页数据转换成Map（QUI只识别Map）
		map.put("pager.pageNo", page.getPageNo());
		map.put("pager.totalRows", page.getTotal());
		map.put("rows", page.getRows());
		
		return map;
	}
	
	/**
	 * 添加步骤
	 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, ModelMap model) {

		String processDefinitionId = request.getParameter("processDefinitionId");
		model.put("processDefinitionId", processDefinitionId);
		return "/definitions/assignment/newTaskDefinition";
	}
	
	/**
	 * 保存步骤
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(HttpServletRequest request, TaskDefinitionEntity taskDefinitionEntity, ModelMap model) {
		String processDefinitionId = request.getParameter("processDefinitionId");
		String taskDefinitionKey = taskDefinitionEntity.getTaskDefinitionKey();
		
		//新增
		if (taskDefinitionEntity.getId() == null || taskDefinitionEntity.getId().equals("")) {
			taskDefinitionEntity.setId(null);
		}
		
		taskDefinitionEntity.setProcessDefinitionId(processDefinitionId);
		bpmTasksStepManagementService.saveTaskDefinition(taskDefinitionEntity);
		return SUCCESS;

	}
	
}
