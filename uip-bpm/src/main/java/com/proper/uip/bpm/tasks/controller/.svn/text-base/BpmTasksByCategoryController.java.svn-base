/* <p>文件名称: BpmTasksAssigneeController.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-7-22</p>
 * <p>完成日期：2013-7-22</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-7-22 下午3:13:00
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.bpm.tasks.controller;

import java.text.SimpleDateFormat;
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

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionManagementEntity;
import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionsCategoryEntity;
import com.proper.uip.api.bpm.extension.BpmTasksByCategoryExtension;
import com.proper.uip.api.bpm.tasks.entity.HistoricTaskResponse;
import com.proper.uip.api.bpm.tasks.entity.TaskResponse;
import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.service.ResourceService;
import com.proper.uip.api.security.service.SecurityService;
import com.proper.uip.api.security.service.UserService;
import com.proper.uip.bpm.definitions.entity.StatisticsEntity;
import com.proper.uip.bpm.definitions.service.BpmDefinitionsCategoryService;
import com.proper.uip.bpm.definitions.service.ProcessDefinitionManagementService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.web.BaseController;


@Controller
@RequestMapping(value = "/tasks/category")
public class BpmTasksByCategoryController extends BaseController {
	private static Log debugPrn = LogFactory.getLog(BpmTasksByCategoryController.class);

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private BpmDefinitionsCategoryService procDefCategoryService;

	@Autowired
	private ProcessDefinitionManagementService processDefinitionManagementService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private FormService formService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ResourceService resourceService;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
//	@Autowired(required=false)
//	@Qualifier("taskResourceCode")
//	private String taskResourceCode;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request, ModelMap model) {
		String code = "definitions.category"; 
		List<ProcessDefinitionsCategoryEntity> definitionsCategoryList = procDefCategoryService.getSubcategoryList(code);
		
		model.put("definitionsCategoryList", definitionsCategoryList);
		
		return "/tasks/assignee/category";
	}
	@RequestMapping("/home")
	public String home(HttpServletRequest request, ModelMap model) {
		String rebackUrl = "/bpm/tasks/category/home";
		String categoryCode = request.getParameter("categoryCode");
		
		//业务写死待办工作地址，取不到categoryCode
		if(categoryCode == null){
			model.put("rebackUrl", rebackUrl);
			return "/tasks/assignee/home";
		}
		
		rebackUrl = rebackUrl + "?categoryCode=" + categoryCode;
		model.put("rebackUrl", rebackUrl);
		model.put("categoryCode", categoryCode);
		return "/tasks/assignee/home";
	}
	
	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, ModelMap model) {
		debugPrn.info("TASKQUERY begin");
		String categoryCode = request.getParameter("categoryCode");
		String processInstanceInitiatorName = request.getParameter("processInstanceInitiatorName");
		String processInstanceName = request.getParameter("processInstanceName");
		
		Set<String> processDefinitionIdSet = new HashSet<String>();
		Map<String, ProcessDefinitionManagementEntity> procDefMap = new HashMap<String, ProcessDefinitionManagementEntity>();
		
		List<ProcessDefinitionManagementEntity> processDefinitionManagementList = processDefinitionManagementService.getByCategoryCode(categoryCode);
		for (ProcessDefinitionManagementEntity processDefinitionManagement : processDefinitionManagementList) {
			processDefinitionIdSet.add(processDefinitionManagement.getId());
			procDefMap.put(processDefinitionManagement.getId(), processDefinitionManagement);
		}
		
		//初始化返回值Map
		Map<String, Object> map = new HashMap<String, Object>();
		
		//初始化Page
		PageConfig pageConfig = super.createPageConfig(request);
		Page<TaskResponse> page = new Page<TaskResponse>(pageConfig);
		
		//取当前用户，若不到当前用户，返回空Map
		User user = super.getCurrentUser();
		if(user == null){
			map.put("pager.pageNo", page.getPageNo());
			map.put("pager.totalRows", 0);
			map.put("rows", new ArrayList<TaskResponse>());
			return map;
		}
		
		//取待办工作分页数据
		TaskQuery taskQuery = taskService.createTaskQuery();
		if(processInstanceInitiatorName != null && processInstanceInitiatorName.trim().equals("") == false){
			String nameLike = "%" + processInstanceInitiatorName + "%";
			List<User> userList = userService.getByNameLike(nameLike);
			
			Set<String> userLoginNameSet = new HashSet<String>();
			for(User theUser : userList){
				userLoginNameSet.add(theUser.getExtendId());
			}
			
			//无用户占位符，处理不存在用户的情况
			userLoginNameSet.add("no-user-placeholder");
			
			taskQuery.startUserIds(userLoginNameSet);
		}
		
		if(processInstanceName != null && processInstanceName.trim().equals("") == false){
			taskQuery.processVariableValueLike("processInstanceName", "%" + processInstanceName + "%");
		}
		
		List<TaskResponse> taskEntityList = new ArrayList<TaskResponse>();
		
		if(processDefinitionIdSet.isEmpty()){
			page.setRows(taskEntityList);
			page.setTotal(taskQuery.count());
			
			//分页数据转换成Map（QUI只识别Map）
			map.put("pager.pageNo", page.getPageNo());
			map.put("pager.totalRows", page.getTotal());
			map.put("rows", page.getRows());
			
			return map;
		}
		
		taskQuery.processDefinitionIds(processDefinitionIdSet);
		
		//经办人或者候选人是当前用户
		taskQuery.taskCandidateUser(user.getExtendId())
		         .taskAssignee(user.getExtendId())
		         .orderByTaskCreateTime()
		         .desc();
		
		int pageNo = pageConfig.pageNo;
		int size = pageConfig.pageSize;
		int start = (pageNo - 1) * size;
		
		//用户idSet，避免循环中多次DBIO
		Set<String> userIdSet = new HashSet<String>();
		
		List<Task> taskList = taskQuery.listPage(start, size);
		
		HistoricProcessInstance processInstance = null;
		TaskResponse taskResponse = null;
		Map<String, Object> variables = null;
		for (Task task : taskList) {
			processInstance = null;
			if(task.getProcessInstanceId() != null && task.getProcessInstanceId().trim().equals("") == false){
				processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
			}
			taskResponse = new TaskResponse((TaskEntity) task, processInstance);
			
			variables = runtimeService.getVariables(task.getExecutionId());
			taskResponse.setVariables(variables);
			
			if(taskResponse.getAssignee() != null && taskResponse.getAssignee().trim().equals("") == false){
				taskResponse.setAssigneeName(user.getName());
			}
			taskEntityList.add(taskResponse);
			
			if(task.getProcessDefinitionId() == null){
				continue;
			}
			
			taskResponse.getVariables().put("processDefinitionCategoryId", procDefMap.get(task.getProcessDefinitionId()).getCategoryId());
			taskResponse.getVariables().put("processDefinitinCategoryCode", procDefMap.get(task.getProcessDefinitionId()).getCategoryCode());
			String  categoryName =  procDefMap.get(task.getProcessDefinitionId()).getCategoryName();
			if(categoryName != null){
				categoryName = categoryName.replaceAll("(（|\\().*", "");
			}
			taskResponse.getVariables().put("processDefinitinCategoryName", categoryName);
			
			if(processInstance != null && processInstance.getStartUserId() != null){
				userIdSet.add(processInstance.getStartUserId());
			}
		}
		
		Map<String, User> userMap = new HashMap<String, User>();
		List<User> userList = new ArrayList<User>();
		for(String userId:userIdSet){
			User user1 = this.userService.getByExtendId(userId);
			if(null != user1)
			{
				userList.add(user1);
			}
		}
//		List<User> userList = this.userService.getByExtendId(extendId)(userIdSet);
		for(User auser: userList){
			userMap.put(auser.getExtendId(), auser);
		}
		
		String initiatorUserId = null;
		User initiator = null;
		for(TaskResponse task : taskEntityList){
			//task.setAssigneeName(user.getName());
			initiatorUserId =  task.getProcessInstanceInitiator();
			if(initiatorUserId == null){
				continue;
			}
			if(userMap.containsKey(initiatorUserId) == false){
				continue;
			}
			
			initiator = userMap.get(initiatorUserId);
			if(initiator == null){
				continue;
			}
			task.setProcessInstanceInitiatorName(initiator.getName());
		}
		
		page.setRows(taskEntityList);
		page.setTotal(taskQuery.count());
		
		//分页数据转换成Map（QUI只识别Map）
		map.put("pager.pageNo", page.getPageNo());
		map.put("pager.totalRows", page.getTotal());
		map.put("rows", page.getRows());
		
		debugPrn.info("TASKQUERY end");
		return map;
	}
	
	@RequestMapping("/anticlaim")
	public String claim(HttpServletRequest request, ModelMap model) {
		//取选中工作任务
		String taskIds = request.getParameter("taskIds");
		String[] taskIdArray = taskIds.split(",");
		if(taskIdArray == null || taskIdArray.length == 0){
			model.put("status", -1);
			this.setError(model, "没有选中待办工作，请先选中待办工作");
		}
		
		//取当前用户
		User user = securityService.getCurrentUser(request);
		if(user == null){
			model.put("status", -1);
			this.setError(model, "获取当前用户失败");
		}	
		
		//执行反签收操作：经办人设置null,添加当前用户为候选人
		for(int i = 0; i < taskIdArray.length; i++){
			taskService.setAssignee(taskIdArray[i], null);
			taskService.addCandidateUser(taskIdArray[i], user.getExtendId());
		}
		
		model.put("status", 1);
		return SUCCESS;
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
		User user = super.getCurrentUser();
		long total = 0;
		
		for (ProcessDefinitionsCategoryEntity category : definitionsCategoryList) {
			StatisticsEntity statistic = new StatisticsEntity();
			List<ProcessDefinitionManagementEntity> processDefinitionManagementList = processDefinitionManagementService.getByCategoryCode(category.getCode());
			Set<String> processDefinitionIdSet = new HashSet<String>();
			
			for (ProcessDefinitionManagementEntity processDefinitionManagement : processDefinitionManagementList) {
				processDefinitionIdSet.add(processDefinitionManagement.getId());
			}
			long count = 0;
			if(processDefinitionIdSet.isEmpty() == false){
				TaskQuery taskQuery = taskService.createTaskQuery();
				
				taskQuery.taskCandidateUser(user.getExtendId())
				         .taskAssignee(user.getExtendId())
				         .processDefinitionIds(processDefinitionIdSet);
				count = taskQuery.count();
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
		TaskQuery currentTaskQuery = taskService.createTaskQuery();
		currentTaskQuery.taskCandidateUser(user.getExtendId())
        		 .taskAssignee(user.getExtendId())
        		 .taskCreatedAfter(currentDate.getTime());
		
		long currentDateCount = currentTaskQuery.count();
		
		//本周的开始时间
		Calendar weekDate = new GregorianCalendar();   
		weekDate.setFirstDayOfWeek(Calendar.MONDAY);  
		weekDate.set(Calendar.HOUR_OF_DAY, 0);  
		weekDate.set(Calendar.MINUTE, 0);  
		weekDate.set(Calendar.SECOND, 0);  
		weekDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); 
		
		TaskQuery weekDateTaskQuery = taskService.createTaskQuery();
		weekDateTaskQuery.taskCandidateUser(user.getExtendId())
        		 .taskAssignee(user.getExtendId())
        		 .taskCreatedAfter(weekDate.getTime());
		
		long weekDateCount = weekDateTaskQuery.count();
		//本月的开始时间
		Calendar cal = Calendar.getInstance();   
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);   
        Date beginTime=cal.getTime(); 
        
        TaskQuery monthDateTaskQuery = taskService.createTaskQuery();
        monthDateTaskQuery.taskCandidateUser(user.getExtendId())
        		 .taskAssignee(user.getExtendId())
        		 .taskCreatedAfter(beginTime);
		
		long monthDateCount = monthDateTaskQuery.count();
		//月前数据
		TaskQuery monthBeforeDateTaskQuery = taskService.createTaskQuery();
		monthBeforeDateTaskQuery.taskCandidateUser(user.getExtendId())
        		 .taskAssignee(user.getExtendId())
        		 .taskCreatedBefore(beginTime);
		
		long monthBeforeDateCount = monthBeforeDateTaskQuery.count();
		
		long dateTotal = monthBeforeDateCount + monthDateCount + currentDateCount + weekDateCount;
		model.put("monthBeforeDateCount", monthBeforeDateCount);
		model.put("monthDateCount", monthDateCount);
		model.put("currentDateCount", currentDateCount);
		model.put("weekDateCount", weekDateCount);
		model.put("statisticsList", statisticsList);
		model.put("total", total);
		model.put("dateTotal", dateTotal);
		return "/tasks/assignee/statistics";
	}
	/**
	 * 详细
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/onView")
	public String onView(HttpServletRequest request, ModelMap model) {
		String taskId = request.getParameter("taskId");
		String executionId = request.getParameter("executionId");
		String processDefinitionId = request.getParameter("processDefinitionId");
		String processInstanceId = request.getParameter("processInstanceId");
		String taskDefinitionKey = request.getParameter("taskDefinitionKey");
		String categoryCode = request.getParameter("categoryCode");
		String rebackUrl = request.getParameter("rebackUrl");
		//处理任务办理U
		String taskUrl = null;
		String formKey = formService.getTaskFormKey(processDefinitionId, taskDefinitionKey);
				
		if(formKey != null){
			Resource resouce = this.resourceService.getResourceByCode(formKey);
			if(resouce != null){
				taskUrl = resouce.getUrl();
			}
		}		
		if(taskUrl != null){
			model.put("taskUrl", taskUrl);
		}
		model.put("taskId", taskId);
		model.put("executionId", executionId);
		model.put("processDefinitionId", processDefinitionId);
		model.put("processInstanceId", processInstanceId);
		model.put("taskDefinitionKey", taskDefinitionKey);
		model.put("categoryCode", categoryCode);
		model.put("rebackUrl", rebackUrl);
		return "/tasks/assignee/onView";
	}
	
	@RequestMapping(value = "/getTaskUrl")
	@ResponseBody
	public Map<String, Object> getTaskUrl(HttpServletRequest request, ModelMap model) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		String processDefinitionId = request.getParameter("processDefinitionId");
		String taskDefinitionKey = request.getParameter("taskDefinitionKey");
		String taskUrl = null;
		String formKey = formService.getTaskFormKey(processDefinitionId, taskDefinitionKey);
				
		if(formKey != null){
			Resource resouce = this.resourceService.getResourceByCode(formKey);
			if(resouce != null){
				taskUrl = resouce.getUrl();
			}
		}		
		if(taskUrl != null){
			map.put("taskUrl", taskUrl);
		}
		
		return map;
	}
	@RequestMapping(value = "/taskQuery")
	@ResponseBody
	public Map<String, Object> taskQuery(HttpServletRequest request, ModelMap model) {
		String executionId = request.getParameter("executionId");
		String processDefinitionId = request.getParameter("processDefinitionId");
		String processInstanceId = request.getParameter("processInstanceId");
		
		Map<String, Object> variables = new HashMap<String, Object>();
		
		//事项（流程分类）
		ProcessDefinitionManagementEntity processDefinition = processDefinitionManagementService.getById(processDefinitionId);
		String processDefinitionCategoryCode = processDefinition.getCategoryCode();
		String processDefinitionCategoryId = processDefinition.getCategoryId();
		String processDefinitionCategoryName = processDefinition.getCategoryName();
		
		//工作名称
		Map<String, Object> executionVariables = runtimeService.getVariables(executionId);
		Object processInstanceName = executionVariables.get("processInstanceName");
		
		//流程实例（申请人，申请时间）
		HistoricProcessInstanceQuery procInstQuery = historyService.createHistoricProcessInstanceQuery();
		HistoricProcessInstance processInstance = procInstQuery.processInstanceId(processInstanceId).list().get(0);
		
		String processInstanceStartTime = dateFormat.format(processInstance.getStartTime());
		String processInstanceInitiatorName = "";
		String loginName = processInstance.getStartUserId();
		if(null!=loginName&&!"".equals(loginName)){
			User user = userService.getUserByLoginName(loginName);
			if(null == user||"".equals(user)){
				user = userService.getByExtendId(loginName);
			}
			
			processInstanceInitiatorName = user.getName();
		}
		
		
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
	
}
