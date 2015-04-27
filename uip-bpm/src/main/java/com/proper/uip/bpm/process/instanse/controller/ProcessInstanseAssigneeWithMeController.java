/* <p>文件名称: ProcessInstanseAssigneeWithMeController.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-4-7</p>
 * <p>完成日期：2013-4-7</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author lichunyan
 */
package com.proper.uip.bpm.process.instanse.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.history.HistoricVariableInstanceQuery;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionManagementEntity;
import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionsCategoryEntity;
import com.proper.uip.api.bpm.extension.BpmTasksByCategoryExtension;
import com.proper.uip.api.bpm.tasks.entity.HistoricTaskResponse;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.service.UserService;
import com.proper.uip.bpm.definitions.entity.StatisticsEntity;
import com.proper.uip.bpm.definitions.service.BpmDefinitionsCategoryService;
import com.proper.uip.bpm.definitions.service.ProcessDefinitionManagementService;
import com.proper.uip.bpm.process.instanse.entity.ProcessInstancesResponse;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.web.BaseController;


@Controller
@RequestMapping(value = "/process/instanse/assigneeWithMe")
public class ProcessInstanseAssigneeWithMeController extends BaseController {


	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private BpmDefinitionsCategoryService procDefCategoryService;

	@Autowired
	private ProcessDefinitionManagementService processDefinitionManagementService;
	
	@Autowired
	private UserService userService;
	/**
	 * 流程办理首页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(ModelMap model) {
		String code = "definitions.category"; 
		List<ProcessDefinitionsCategoryEntity> definitionsCategoryList = procDefCategoryService.getSubcategoryList(code);
		
		model.put("definitionsCategoryList", definitionsCategoryList);
		
		return "/assigneeWithMe/category";
	}
	
	@RequestMapping("/home")
	public String home(HttpServletRequest request, ModelMap model) {
		String rebackUrl = "/process/instanse/assigneeWithMe/home";
		String categoryCode = request.getParameter("categoryCode");
		
		//业务写死待办工作地址，取不到categoryCode
		if(categoryCode == null){
			model.put("rebackUrl", rebackUrl);
			return "/assigneeWithMe/home";
		}
		
		rebackUrl = rebackUrl + "?categoryCode=" + categoryCode;
		model.put("rebackUrl", rebackUrl);
		model.put("categoryCode", categoryCode);
		return "/assigneeWithMe/home";
	}
	/**
	 * 流程实例查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/instance/query")
	@ResponseBody
	public Map<String, Object> processInstanceQuery(HttpServletRequest request) {
		String categoryCode = request.getParameter("categoryCode");
		String processInstanceInitiatorName = request.getParameter("processInstanceInitiatorName");
		String assigneeName = request.getParameter("assigneeName");
		String processInstanceName = request.getParameter("processInstanceName");
		//初始化返回值Map
		Map<String, Object> map = new HashMap<String, Object>();

		//初始化Page
		PageConfig pageConfig = super.createPageConfig(request);
		Page<ProcessInstancesResponse> page = new Page<ProcessInstancesResponse>(
				pageConfig);
		
		//获取当前分类下的流程定义
		List<ProcessDefinitionManagementEntity> processDefinitionList = processDefinitionManagementService.getByCategoryCode(categoryCode);
		Set<String> processDefinitionIdSet = new HashSet<String>();
		for(ProcessDefinitionManagementEntity processDefinition : processDefinitionList){
			processDefinitionIdSet.add(processDefinition.getId());
		}
		
		if(processDefinitionIdSet.isEmpty() == true){
			return map;
		}
		Date taskEndDate = new Date(2014, 1, 3);
				
		// 根据流程定义的Id 去查询流程实例
		// 根据HistoricProcessInstanceEntity来查询流程实例名称（工作名称），发起人，发起时间，结束时间
		HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();
		query.assigneeWith(super.getCurrentUser().getExtendId())
		     .processDefinitionIds(processDefinitionIdSet)
		     .validByTaskEndDate(taskEndDate);
		
		Set<String> processInstanceIdSet = new HashSet<String>();
		String processInstanceNameLike = "%" + processInstanceName + "%";
		if(processInstanceName != null && processInstanceName.trim().equals("") == false){
			
			HistoricVariableInstanceQuery historicVariableInstanceQuery = historyService.createHistoricVariableInstanceQuery().variableValueLike("processInstanceName", processInstanceNameLike);
			
//			ExecutionQuery executionQuery = runtimeService.createExecutionQuery()
//					                                      .variableValueLike("processInstanceName", processInstanceNameLike);
			List<HistoricVariableInstance> historicVariableInstanceList = historicVariableInstanceQuery.list();
			for (HistoricVariableInstance historicVariableInstance : historicVariableInstanceList) {
				processInstanceIdSet.add(historicVariableInstance.getProcessInstanceId());
			}
			
			query.processInstanceIds(processInstanceIdSet);
		}
		
		if(processInstanceInitiatorName != null && processInstanceInitiatorName.trim().equals("") == false){
			String nameLike = "%" + processInstanceInitiatorName + "%";
			List<User> userList = userService.getByNameLike(nameLike);
			
			Set<String> userLoginNameSet = new HashSet<String>();
			for(User theUser : userList){
				userLoginNameSet.add(theUser.getExtendId());
			}
			
			//无用户占位符，处理不存在用户的情况
			userLoginNameSet.add("no-user-placeholder");
			
			query.startedByUserIds(userLoginNameSet);
		}

		if(assigneeName != null && assigneeName.trim().equals("") == false){
			String nameLike = "%" + assigneeName + "%";
			List<User> userList = userService.getByNameLike(nameLike);
			
			Set<String> userLoginNameSet = new HashSet<String>();
			for(User theUser : userList){
				userLoginNameSet.add(theUser.getExtendId());
			}
			
			//无用户占位符，处理不存在用户的情况
			userLoginNameSet.add("no-user-placeholder");
			
			query.AssignedBy(userLoginNameSet);
		}
		
		query.orderByProcessInstanceStartTime().asc();

		int pageNo = pageConfig.pageNo;
		int size = pageConfig.pageSize;
		int start = (pageNo - 1) * size;
		List<HistoricProcessInstance> processInstanceList = query.listPage(
				start, size);

		//流程变量
		Map<String, Object> variables = null;
		List<ProcessInstancesResponse> processResponseList = new ArrayList<ProcessInstancesResponse>();
		ProcessInstancesResponse response = null;
		User initiatorUser = null;
		ProcessDefinitionManagementEntity processDefinitionManagement = null;
		
		for (HistoricProcessInstance processInstance : processInstanceList) {
			variables = this.getProcessVariables(processInstance.getId());
			
			processDefinitionManagement = processDefinitionManagementService.getById(processInstance.getProcessDefinitionId());
			if(processDefinitionManagement != null){
				variables.put("processDefinitinCategoryName", processDefinitionManagement.getCategoryName());
			}
			initiatorUser = this.userService.getByExtendId(processInstance.getStartUserId());
			if(initiatorUser != null){
				variables.put("processInstanceInitiatorName", initiatorUser.getName());
			}
			
			response = new ProcessInstancesResponse((HistoricProcessInstanceEntity)processInstance, variables);
			
			processResponseList.add(response);
		}

		page.setRows(processResponseList);
		page.setTotal(query.count());

		// 分页数据转换成Map（QUI只识别Map）
		map.put("pager.pageNo", page.getPageNo());
		map.put("pager.totalRows", page.getTotal());
		map.put("rows", page.getRows());
		return map;
	}
	
	private Map<String, Object> getProcessVariables(String processInstanceId){
		Map<String, Object> variables = new HashMap<String, Object>();
		HistoricVariableInstanceQuery query = historyService.createHistoricVariableInstanceQuery()
				                                            .processInstanceId(processInstanceId);
		List<HistoricVariableInstance> variableInstanceList = query.list();
		
		for (HistoricVariableInstance variableInstance : variableInstanceList) {
			variables.put(variableInstance.getVariableName(), variableInstance.getValue());
		}
		
//		List<Execution> executionList = runtimeService
//				.createExecutionQuery()
//				.processInstanceId(processInstanceId).list();
//		
//		Map<String, Object> currentVariables;
//		for (Execution execution : executionList) {
//			if(execution == null){
//				continue;
//			}
//			currentVariables = runtimeService.getVariables(execution.getId());
//			if(currentVariables == null){
//				continue;
//			}
//			variables.putAll(currentVariables);
//		}
		
		return variables;
	}
	
	/**
	 * 流程实例任务查询
	 * 
	 * @param processInstanceId
	 * @return
	 */
	@RequestMapping("/instance/task/query")
	@ResponseBody
	public Map<String, Object> processInstanceTasksQuery(String processInstanceId,
			HttpServletRequest request) {
		String processDefinitionId = request.getParameter("processDefinitionId");
		
		Map<String, Object> variables = new HashMap<String, Object>();
		
		//事项（流程分类）
		ProcessDefinitionManagementEntity processDefinition = processDefinitionManagementService.getById(processDefinitionId);
		String processDefinitionCategoryCode = processDefinition.getCategoryCode();
		String processDefinitionCategoryId = processDefinition.getCategoryId();
		String processDefinitionCategoryName = processDefinition.getCategoryName();
		
		//工作名称
		HistoricVariableInstanceQuery query = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).variableName("processInstanceName");
		String processInstanceName = (String) query.list().get(0).getValue();
		//流程实例（申请人，申请时间）
		HistoricProcessInstanceQuery procInstQuery = historyService.createHistoricProcessInstanceQuery();
		HistoricProcessInstance processInstance = procInstQuery.processInstanceId(processInstanceId).list().get(0);
		
		String processInstanceStartTime = dateFormat.format(processInstance.getStartTime());
		String personId = processInstance.getStartUserId();
		String processInstanceInitiatorName = userService.getByExtendId(personId).getName();
		
		
		variables.put("processDefinitionCategoryCode", processDefinitionCategoryCode);
		variables.put("processDefinitionCategoryId", processDefinitionCategoryId);
		variables.put("processDefinitionCategoryName", processDefinitionCategoryName);
		variables.put("processInstanceName", processInstanceName);
		variables.put("processInstanceStartTime", processInstanceStartTime);
		variables.put("processInstanceInitiatorName", processInstanceInitiatorName);
		
		//初始化Page
		PageConfig pageConfig = this.createPageConfig(request);
		Page<HistoricTaskResponse> page = new Page<HistoricTaskResponse>(pageConfig);
		
		HistoricTaskInstanceQuery taskQuery = historyService.createHistoricTaskInstanceQuery();
		taskQuery.processInstanceId(processInstanceId).orderByHistoricTaskInstanceStartTime().asc();
		
		int pageNo = pageConfig.pageNo;
		int size = pageConfig.pageSize;
		int start = (pageNo - 1) * size;
		
		List<HistoricTaskResponse> historicTaskResponseList = new ArrayList<HistoricTaskResponse>();
		List<HistoricTaskInstance> taskList = taskQuery.listPage(start, size);
		HistoricTaskResponse taskResponse = null;
		User assigneeUser = null;
		for(HistoricTaskInstance task : taskList){
			taskResponse = new HistoricTaskResponse(task, variables);
			
			//处理办理人
			if(taskResponse.getAssignee() != null && taskResponse.getAssignee().equals("") == false){
				assigneeUser = userService.getByExtendId(taskResponse.getAssignee());
				
				taskResponse.setAssigneeName(assigneeUser != null ? assigneeUser.getName() : taskResponse.getAssignee());
			}
			
			historicTaskResponseList.add(taskResponse);
		}
		
		//处理审批意见
		BpmTasksByCategoryExtension applicationExtension = procDefCategoryService.getTasksByCategoryExtension(processDefinitionCategoryCode);

		if(applicationExtension != null){
			historicTaskResponseList = applicationExtension.extend(historicTaskResponseList);
		}
		
		page.setRows(historicTaskResponseList);
		page.setTotal(taskQuery.count());
		
		//分页数据转换成Map（QUI只识别Map）
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pager.pageNo", page.getPageNo());
		map.put("pager.totalRows", page.getTotal());
		map.put("rows", page.getRows());
	
		
		return map;
	}
	/**
	 * 统计
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/statistics")
	public String statistics(HttpServletRequest request, ModelMap model) {
		String code = "definitions.category"; 
		List<ProcessDefinitionsCategoryEntity> definitionsCategoryList = procDefCategoryService.getSubcategoryList(code);
		List<StatisticsEntity> statisticsList = new ArrayList<StatisticsEntity>();
		long total = 0;
		long currentDateCount = 0;
		long weekDateCount = 0;
		long monthDateCount = 0;
		long monthBeforeDateCount = 0;
		Date taskEndDate = new Date(2014, 1, 3);        
		for (ProcessDefinitionsCategoryEntity category : definitionsCategoryList) {
			StatisticsEntity statistic = new StatisticsEntity();
			//获取当前分类下的流程定义
			List<ProcessDefinitionManagementEntity> processDefinitionList = processDefinitionManagementService.getByCategoryCode(category.getCode());
			Set<String> processDefinitionIdSet = new HashSet<String>();
			for(ProcessDefinitionManagementEntity processDefinition : processDefinitionList){
				processDefinitionIdSet.add(processDefinition.getId());
			}
			long count = 0;
			if(null != processDefinitionIdSet &&  processDefinitionIdSet.size()!=0){

	            HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
	            historicProcessInstanceQuery.assigneeWith(super.getCurrentUser().getExtendId())
	                                        .processDefinitionIds(processDefinitionIdSet)
	                                        .validByTaskEndDate(taskEndDate);
	            count = historicProcessInstanceQuery.count();
			}

			
			
			total = total + count;
			statistic.setLabel(category.getName());
			statistic.setValue(count);
			
			statisticsList.add(statistic);
		}

		//当天开始时间
		Calendar currentDate = new GregorianCalendar();   
		currentDate.set(Calendar.HOUR_OF_DAY, 0);  
		currentDate.set(Calendar.MINUTE, 0);  
		currentDate.set(Calendar.SECOND, 0);  
		HistoricProcessInstanceQuery currentTaskQuery = historyService.createHistoricProcessInstanceQuery()
				                                                      .assigneeWith(super.getCurrentUser().getExtendId())
				                                                      .startedAfter(currentDate.getTime());
		currentDateCount = currentTaskQuery.count();

		
		
		//本周的开始时间
		Calendar weekDate = new GregorianCalendar();   
		weekDate.setFirstDayOfWeek(Calendar.MONDAY);  
		weekDate.set(Calendar.HOUR_OF_DAY, 0);  
		weekDate.set(Calendar.MINUTE, 0);  
		weekDate.set(Calendar.SECOND, 0);  
		weekDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); 
		HistoricProcessInstanceQuery weekDateTaskQuery = historyService.createHistoricProcessInstanceQuery()
                												       .assigneeWith(super.getCurrentUser().getExtendId())
                												       .startedAfter(weekDate.getTime());
		weekDateCount = weekDateTaskQuery.count();


		
		//本月的开始时间
		Calendar cal = Calendar.getInstance();   
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);   
        Date beginTime=cal.getTime(); 
        
        HistoricProcessInstanceQuery monthDateTaskQuery = historyService.createHistoricProcessInstanceQuery()
			       													    .assigneeWith(super.getCurrentUser().getExtendId())
			       													    .startedAfter(beginTime);
        monthDateCount = monthDateTaskQuery.count();

		//月前数据
        
		HistoricProcessInstanceQuery monthBeforeDateTaskQuery = historyService.createHistoricProcessInstanceQuery()
				    														  .assigneeWith(super.getCurrentUser().getExtendId())
				    														  .startedBefore(beginTime);
		
		monthBeforeDateCount = monthBeforeDateTaskQuery.count();
		
		model.put("monthBeforeDateCount", monthBeforeDateCount);
		model.put("monthDateCount", monthDateCount);
		model.put("currentDateCount", currentDateCount);
		model.put("weekDateCount", weekDateCount);
		model.put("statisticsList", statisticsList);
		model.put("total", total);
		return "/assigneeWithMe/statistics";
	}

	/**
	 * 详细
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/onView")
	public String onView(HttpServletRequest request, ModelMap model) {
		String processInstanceId = request.getParameter("processInstanceId");
		String processDefinitionId = request.getParameter("processDefinitionId");
		String categoryCode = request.getParameter("categoryCode");
		String rebackUrl = request.getParameter("rebackUrl");
		
		model.put("processInstanceId", processInstanceId);
		model.put("processDefinitionId", processDefinitionId);
		model.put("categoryCode", categoryCode);
		model.put("rebackUrl", rebackUrl);
		return "/assigneeWithMe/onView";
	}
}
