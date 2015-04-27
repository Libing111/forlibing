/* <p>文件名称: TaskResponse.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-7-24</p>
 * <p>完成日期：2013-7-24</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-7-24 下午1:07:52
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.api.bpm.tasks.entity;

import java.io.Serializable;
import java.util.Map;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.DelegationState;

import com.proper.uip.api.bpm.util.RequestUtil;
import com.proper.uip.api.security.entity.User;


public class TaskResponse implements Serializable{
	private static final long serialVersionUID = -4464753503640311370L;
	
	private String assignee;
	private String createTime;
	private DelegationState delegationState;
	private String description;
	private String dueDate;
	private String executionId;
	private String id;
	private String name;
	private String owner;
	private String parentTaskId;
	private int priority;
	private String processDefinitionId;
	private String processInstanceId;
	private String taskDefinitionKey;
	private String formResourceKey;
	
	private String taskUrl;

	private String processInstanceInitiator;
	private String processInstanceInitiatorName;
	private String processInstanceStartTime;
	
	private String processInstanceName;
	private String taskInstanceName;
	private String assigneeName;
	
	private Map<String, Object> variables;

	@Deprecated
	public TaskResponse(TaskEntity task, HistoricProcessInstance processInstance, Map<String, User> userMap) {
		this.assignee = task.getAssignee();
		//this.assigneeName = task.getAssignee();
		this.createTime = RequestUtil.dateToString(task.getCreateTime());
		this.delegationState = task.getDelegationState();
		this.description = task.getDescription();
		this.dueDate = RequestUtil.dateToString(task.getDueDate());
		this.executionId = task.getExecutionId();
		this.id = task.getId();
		this.name = task.getName();
		this.owner = task.getOwner();
		this.parentTaskId = task.getParentTaskId();
		this.priority = task.getPriority();
		this.processDefinitionId = task.getProcessDefinitionId();
		this.processInstanceId = task.getProcessInstanceId();
		if(processInstance != null){
			this.processInstanceInitiator = processInstance.getStartUserId();
			this.processInstanceInitiatorName = processInstance.getStartUserId();
			if(userMap.containsKey(processInstanceInitiator) == true){
				this.processInstanceInitiatorName = userMap.get(processInstanceInitiator).getName();
			}
			this.processInstanceStartTime = RequestUtil.dateToString(processInstance.getStartTime());
		}
		
		this.taskDefinitionKey = task.getTaskDefinitionKey();
		
	}
	
	public TaskResponse(TaskEntity task, HistoricProcessInstance processInstance) {
		this.assignee = task.getAssignee();
		this.createTime = RequestUtil.dateToString(task.getCreateTime());
		this.delegationState = task.getDelegationState();
		this.description = task.getDescription();
		this.dueDate = RequestUtil.dateToString(task.getDueDate());
		this.executionId = task.getExecutionId();
		this.id = task.getId();
		this.name = task.getName();
		this.owner = task.getOwner();
		this.parentTaskId = task.getParentTaskId();
		this.priority = task.getPriority();
		this.processDefinitionId = task.getProcessDefinitionId();
		this.processInstanceId = task.getProcessInstanceId();
		if(processInstance != null){
			this.processInstanceInitiator = processInstance.getStartUserId();
			this.processInstanceInitiatorName = processInstance.getStartUserId();
			this.processInstanceStartTime = RequestUtil.dateToString(processInstance.getStartTime());
		}
		
		this.taskDefinitionKey = task.getTaskDefinitionKey();
		
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public DelegationState getDelegationState() {
		return delegationState;
	}

	public void setDelegationState(DelegationState delegationState) {
		this.delegationState = delegationState;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getParentTaskId() {
		return parentTaskId;
	}

	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}

	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	public String getFormResourceKey() {
		return formResourceKey;
	}

	public void setFormResourceKey(String formResourceKey) {
		this.formResourceKey = formResourceKey;
	}

	public String getProcessInstanceInitiator() {
		return processInstanceInitiator;
	}

	public void setProcessInstanceInitiator(String processInstanceInitiator) {
		this.processInstanceInitiator = processInstanceInitiator;
	}

	public String getProcessInstanceStartTime() {
		return processInstanceStartTime;
	}

	public void setProcessInstanceStartTime(String processInstanceStartTime) {
		this.processInstanceStartTime = processInstanceStartTime;
	}

	public String getProcessInstanceName() {
		return processInstanceName;
	}

	public void setProcessInstanceName(String processInstanceName) {
		this.processInstanceName = processInstanceName;
	}

	public String getTaskInstanceName() {
		return taskInstanceName;
	}

	public void setTaskInstanceName(String taskInstanceName) {
		this.taskInstanceName = taskInstanceName;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public String getProcessInstanceInitiatorName() {
		return processInstanceInitiatorName;
	}

	public void setProcessInstanceInitiatorName(String processInstanceInitiatorName) {
		this.processInstanceInitiatorName = processInstanceInitiatorName;
	}
	
	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
		
		this.processInstanceName = (String)variables.get("processInstanceName");
		this.taskInstanceName = (String)variables.get("taskInstanceName");
	}

	public String getTaskUrl() {
		return taskUrl;
	}

	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}

}
