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

import java.util.ArrayList;
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
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.uip.api.bpm.tasks.entity.TaskResponse;
import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.service.ResourceService;
import com.proper.uip.api.security.service.SecurityService;
import com.proper.uip.api.security.service.UserService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.web.BaseController;



@Controller
@RequestMapping(value = "/tasks/assignee")
public class BpmTasksAssigneeController extends BaseController {
	@Autowired
	private TaskService taskService;
	
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
	
	@Autowired(required=false)
	@Qualifier("taskResourceCode")
	private String taskResourceCode;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request, ModelMap model) {
		String rebackUrl = resourceService.getResourceByCode(taskResourceCode).getUrl();
		model.put("rebackUrl", rebackUrl);
		return "/tasks/assignee/index";
	}
	
	@RequestMapping("/home")
	public String home(HttpServletRequest request, ModelMap model) {
		String rebackUrl = resourceService.getResourceByCode(taskResourceCode).getUrl();
		model.put("rebackUrl", rebackUrl);
		return "/tasks/assignee/home";
	}
	
	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, ModelMap model) {
		String processInstanceInitiatorName = request.getParameter("processInstanceInitiatorName");
		String processInstanceName = request.getParameter("processInstanceName");
		//初始化返回值Map
		Map<String, Object> map = new HashMap<String, Object>();
		
		//初始化Page
		PageConfig pageConfig = super.createPageConfig(request);
		Page<TaskResponse> page = new Page<TaskResponse>(pageConfig);
		
		//取当前用户，若不到当前用户，返回空Map
		User user = securityService.getCurrentUser(request);
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
				userLoginNameSet.add(theUser.getLoginName());
			}
            
			//无用户占位符，处理不存在用户的情况
			userLoginNameSet.add("no-user-placeholder");
			
			taskQuery.startUserIds(userLoginNameSet);
		}
		
		if(processInstanceName != null && processInstanceName.trim().equals("") == false){
			taskQuery.processVariableValueLike("processInstanceName", "%" + processInstanceName + "%");
		}
		
		//经办人或者候选人是当前用户
		taskQuery.taskCandidateUser(user.getLoginName())
		         .taskAssignee(user.getLoginName())
		         .orderByTaskCreateTime()
		         .desc();
		
		int pageNo = pageConfig.pageNo;
		int size = pageConfig.pageSize;
		int start = (pageNo - 1) * size;
		
		List<Task> taskList = taskQuery.listPage(start, size);
		
		Map<String, User> userMap = new HashMap<String, User>();
		
		List<User> userList = this.userService.getAllUsers();
		for(User auser: userList){
			userMap.put(auser.getLoginName(), auser);
		}
		
		List<TaskResponse> taskEntityList = new ArrayList<TaskResponse>();
		HistoricProcessInstance processInstance = null;
		String formKey = null;
		Resource resouce = null;
		TaskResponse taskResponse = null;
		Map<String, Object> variables = null;
		for (Task task : taskList) {
			processInstance = null;
			if(task.getProcessInstanceId() != null && task.getProcessInstanceId().trim().equals("") == false){
				processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
			}
			taskResponse = new TaskResponse((TaskEntity) task, processInstance, userMap);
			
			variables = runtimeService.getVariables(task.getExecutionId());
			taskResponse.setVariables(variables);
			
			if(taskResponse.getAssignee() != null && taskResponse.getAssignee().trim().equals("") == false){
				taskResponse.setAssigneeName(user.getName());
			}
			taskEntityList.add(taskResponse);
			
			if(task.getProcessDefinitionId() == null){
				continue;
			}
			
			formKey = formService.getTaskFormKey(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
			if(formKey == null){
				continue;
			}
			
			taskResponse.setFormResourceKey(formKey);
			
			resouce = this.resourceService.getResourceByCode(formKey);
			if(resouce == null){
				continue;
			}
			
			taskResponse.setTaskUrl(resouce.getUrl());
		}
		
		page.setRows(taskEntityList);
		page.setTotal(taskQuery.count());
		
		//分页数据转换成Map（QUI只识别Map）
		map.put("pager.pageNo", page.getPageNo());
		map.put("pager.totalRows", page.getTotal());
		map.put("rows", page.getRows());
		
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
			taskService.addCandidateUser(taskIdArray[i], user.getLoginName());
		}
		
		model.put("status", 1);
		return SUCCESS;
	}
}
