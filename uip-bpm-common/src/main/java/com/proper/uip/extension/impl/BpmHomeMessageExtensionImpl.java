package com.proper.uip.extension.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.FormService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;

import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionManagementEntity;
import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionsCategoryEntity;
import com.proper.uip.api.desktop.entity.HomeMessageEntity;
import com.proper.uip.api.desktop.entity.MessageReportEntity;
import com.proper.uip.api.desktop.extension.HomeMessageExtension;
import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.service.ResourceService;
import com.proper.uip.bpm.definitions.service.ProcessDefinitionManagementService;
import com.proper.uip.definitions.dao.ProcessDefinitionManagementDao;
import com.proper.uip.definitions.dao.ProcessDefinitionsCategoryDao;

public class BpmHomeMessageExtensionImpl extends HomeMessageExtension {

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ProcessDefinitionManagementDao processDefinitionManagementDao;
	
	@Autowired
	private ProcessDefinitionManagementService processDefinitionManagementService;
	
	@Autowired
	private ProcessDefinitionsCategoryDao processDefinitionsCategoryDao;
	
	@Autowired
	private FormService formService;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Override
	public int getTotalCount(User user) {
		long totalCount = 0;
		if(null!=user.getExtendId()&&!"".equals(user.getExtendId()))
		{
			TaskQuery taskQuery = taskService.createTaskQuery();
			totalCount = taskQuery.taskCandidateUser(user.getExtendId())
			         .taskAssignee(user.getExtendId())
			         .count();
		}
		         
		return (int) totalCount;
	}

	@Override
	public int getMessageCount(User user, Date beginTime, Date endTime) {
		long messageCount = 0;
		if(null!=user.getExtendId()&&!"".equals(user.getExtendId()))
		{
			TaskQuery taskQuery = taskService.createTaskQuery();
			messageCount = taskQuery.taskCandidateUser(user.getExtendId())
			         .taskAssignee(user.getExtendId())
			         .taskCreatedAfter(beginTime)
			         .taskCreatedBefore(endTime)
			         .count();
		}
		         
		return (int) messageCount;
	}

	@Override
	public List<HomeMessageEntity> getMessageList(HttpServletRequest request,
			User user, int msgCount) {
		//返回值
		List<HomeMessageEntity> HomeMessageList = new ArrayList<HomeMessageEntity>();
		//流程定义列表
		List<ProcessDefinitionManagementEntity> processDefinitionManagementList = processDefinitionManagementDao.getAll();
		Map<String, ProcessDefinitionManagementEntity> procDefMap = new HashMap<String, ProcessDefinitionManagementEntity>();
		
		//为了显示待办工作名称
		for (ProcessDefinitionManagementEntity processDefinitionManagement : processDefinitionManagementList) {
//			processDefinitionIdSet.add(processDefinitionManagement.getId());
			procDefMap.put(processDefinitionManagement.getId(), processDefinitionManagement);
		}
	
		//通过当前登录人查询出需要显示的待办
		TaskQuery taskQuery = taskService.createTaskQuery();
		if(null!=user.getExtendId()&&!"".equals(user.getExtendId()))
		{

			taskQuery.taskCandidateUser(user.getExtendId())
	        .taskAssignee(user.getExtendId())
	        .orderByTaskCreateTime()
	        .desc();
			List<Task> taskList = taskQuery.listPage(0, msgCount);
			for (Task task : taskList) {
				HomeMessageEntity homeMessage = new HomeMessageEntity();
				homeMessage.setId(task.getId());
				homeMessage.setTitle("待办工作");
				homeMessage.setUrl(getUrl(request, task));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
				String time = sdf.format(task.getCreateTime());  
				homeMessage.setTime(time);
				
				homeMessage.setCategoryCode(procDefMap.get(task.getProcessDefinitionId()).getCategoryCode());
				String  categoryName =  procDefMap.get(task.getProcessDefinitionId()).getCategoryName();
				if(categoryName != null){
					categoryName = categoryName.replaceAll("(（|\\().*", "");
				}
				homeMessage.setCategoryName(categoryName);
				Map<String, Object> variables = runtimeService.getVariables(task.getExecutionId());
				homeMessage.setMsgText((String)variables.get("processInstanceName"));
				
				HomeMessageList.add(homeMessage);
			}
		}
		return HomeMessageList;
	}

	@Override
	public List<MessageReportEntity> getMessageReportList(
			HttpServletRequest request, User user) {
		List<MessageReportEntity> messageReportList = new ArrayList<MessageReportEntity>();
		if(null!=user.getExtendId()&&!"".equals(user.getExtendId()))
		{

			List<ProcessDefinitionsCategoryEntity> processDefinitionsCategoryList = processDefinitionsCategoryDao.getAll();
			
			for(ProcessDefinitionsCategoryEntity processDefinitionsCategory:processDefinitionsCategoryList){
				MessageReportEntity messageReportEntity = new MessageReportEntity();
				Set<String> processDefinitionIdSet = new HashSet<String>();
				List<ProcessDefinitionManagementEntity> processDefinitionManagementList = processDefinitionManagementService.getByCategoryCode(processDefinitionsCategory.getCode());
				Map<String, ProcessDefinitionManagementEntity> procDefMap = new HashMap<String, ProcessDefinitionManagementEntity>();
				for (ProcessDefinitionManagementEntity processDefinitionManagement : processDefinitionManagementList) {
					processDefinitionIdSet.add(processDefinitionManagement.getId());
					procDefMap.put(processDefinitionManagement.getId(), processDefinitionManagement);
				}
				TaskQuery taskQuery = taskService.createTaskQuery();
				long count = taskQuery.taskCandidateUser(user.getExtendId())
		        .taskAssignee(user.getExtendId())
		        .processDefinitionIds(processDefinitionIdSet)
		        .count();
				messageReportEntity.setValue((int) count);
				messageReportEntity.setName(processDefinitionsCategory.getName());
			}
		}
		
		
		
		return messageReportList;
	}
	
	public String getUrl(HttpServletRequest request, Task task){
		Map<String, Object> map = new HashMap<String, Object>();
		String taskUrl = null;
		String formKey = formService.getTaskFormKey(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
				
		if(formKey != null){
			Resource resouce = this.resourceService.getResourceByCode(formKey);
			if(resouce != null){
				taskUrl = resouce.getUrl();

			    String link = "?";
				if(taskUrl.indexOf("?") >= 0){
					link = "&";
				}
				String rebackUrl = "/home/custom/closewin";
				taskUrl = taskUrl + link + "taskId=" + task.getId() + "&processInstanceId="+task.getProcessInstanceId() +"&rebackUrl=" + rebackUrl;
			}
		}	
		return taskUrl;
	}

	@Override
	public List<HomeMessageEntity> getMessageListByTime(User user,
			Date beginTime, Date endTime) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
