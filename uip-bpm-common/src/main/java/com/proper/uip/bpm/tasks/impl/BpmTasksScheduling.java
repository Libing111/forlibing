package com.proper.uip.bpm.tasks.impl;

import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.proper.uip.api.message.reminding.MessageRemindingException;
import com.proper.uip.api.message.reminding.service.SmsRemindingService;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.service.UserService;



public class BpmTasksScheduling {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired(required=true)
	@Qualifier("httpSmsRemindingService")
	private SmsRemindingService smsRemindingService;
	
	private List<String> loginNameList;
	
	private String shortMessageTemplate;
	
	private String taskCountPlaceholder;
	
	@Autowired
	private UserService userService;
	
	public void execute() {
		for (String loginName : loginNameList) {
			this.sendShortMessage(loginName);
		}
	}

	public List<String> getLoginNameList() {
		return loginNameList;
	}

	public void setLoginNameList(List<String> loginNameList) {
		this.loginNameList = loginNameList;
	}

	private void sendShortMessage(String loginName){
		User user = userService.getUserByLoginName(loginName);
		if(user == null){
			return;
		}
		String mobileNumber = user.getExtendProperty("mobileNumber");
		if(mobileNumber == null || mobileNumber.trim().equals("") == true){
			return;
		}
		TaskQuery taskQuery = taskService.createTaskQuery();
		Long count = taskQuery.taskCandidateUser(user.getLoginName())
        		  			  .taskAssignee(user.getLoginName())
        		  			  .count();
		if(count == 0){
			return;
		}
		String content = this.shortMessageTemplate.replaceAll(this.taskCountPlaceholder, count.toString());
		try {
			smsRemindingService.sendShortMessage(mobileNumber, content, null);
		} catch (MessageRemindingException e) {
			e.printStackTrace();
		}
	}

	public String getShortMessageTemplate() {
		return shortMessageTemplate;
	}

	public void setShortMessageTemplate(String shortMessageTemplate) {
		this.shortMessageTemplate = shortMessageTemplate;
	}

	public String getTaskCountPlaceholder() {
		return taskCountPlaceholder;
	}

	public void setTaskCountPlaceholder(String taskCountPlaceholder) {
		this.taskCountPlaceholder = taskCountPlaceholder;
	}
	
	
}
