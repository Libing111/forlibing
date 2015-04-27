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
 * @author	lichunyan
 */
package com.proper.uip.api.bpm.tasks.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.activiti.engine.history.HistoricTaskInstance;

public class HistoricTaskResponse implements Serializable {
	private static final long serialVersionUID = -4464753503640311370L;
	/**
	 * 执行Id
	 */
	private String executionId;
	/**
	 * 流程定义Id
	 */
	private String processDefinitionId;
	/**
	 * 流程任务Id
	 */
	private String taskId;
	/**
	 * 流程实例Id
	 */
	private String processInstanceId;
	/**
	 * 工作名称
	 */
	private String processInstanceName;
	/**
	 * 流程实例分类Name
	 */
	private String processDefinitionCategoryName;
	/**
	 * 流程实例分类Id
	 */
	private String processDefinitionCategoryId;
	/**
	 * 流程实例分类Code
	 */
	private String processDefinitionCategoryCode;
	/**
	 * 申请人
	 */
	private String processInstanceInitiatorName;
	/**
	 * 申请时间
	 */
	private String processInstanceStartTime;
	/**
	 * 审批环节
	 */
	private String name;
	/**
	 * 审批环节key
	 */
	private String taskDefinitionKey;
	/**
	 * 审批意见
	 */
	private String reviewOpinion;
	/**
	 * 办理人Id
	 */
	private String assignee;
	/**
	 * 办理人
	 */
	private String assigneeName;
	/**
	 * 办结时间
	 */
	private Date endTime;

	private Map<String, Object> variables;
	
	private String taskUrl;

	public HistoricTaskResponse(HistoricTaskInstance historicTaskInstance, Map<String, Object> variables) {
		this.executionId = historicTaskInstance.getExecutionId();
		this.processDefinitionId = historicTaskInstance.getProcessDefinitionId();
		this.processInstanceId = historicTaskInstance.getProcessInstanceId();
		this.taskId = historicTaskInstance.getId();
		this.name = historicTaskInstance.getName();
		this.taskDefinitionKey = historicTaskInstance.getTaskDefinitionKey();
		this.assignee = historicTaskInstance.getAssignee();
		this.endTime = historicTaskInstance.getEndTime();
		
		this.processDefinitionCategoryCode = (String) variables.get("processDefinitionCategoryCode");
		this.processDefinitionCategoryId = (String) variables.get("processDefinitionCategoryId");
		this.processDefinitionCategoryName = (String) variables.get("processDefinitionCategoryName");
		this.processInstanceInitiatorName = (String) variables.get("processInstanceInitiatorName");
		this.processInstanceName = (String) variables.get("processInstanceName");
		this.processInstanceStartTime = (String) variables.get("processInstanceStartTime");
		//this.assigneeName = variables.get(assigneeName).toString();
		//this.reviewOpinion = (String) variables.get("reviewOpinion");
		
		this.variables = variables;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessInstanceName() {
		return processInstanceName;
	}

	public void setProcessInstanceName(String processInstanceName) {
		this.processInstanceName = processInstanceName;
	}

	public String getProcessDefinitionCategoryName() {
		return processDefinitionCategoryName;
	}

	public void setProcessDefinitionCategoryName(
			String processDefinitionCategoryName) {
		this.processDefinitionCategoryName = processDefinitionCategoryName;
	}

	public String getProcessDefinitionCategoryId() {
		return processDefinitionCategoryId;
	}

	public void setProcessDefinitionCategoryId(
			String processDefinitionCategoryId) {
		this.processDefinitionCategoryId = processDefinitionCategoryId;
	}

	public String getProcessDefinitionCategoryCode() {
		return processDefinitionCategoryCode;
	}

	public void setProcessDefinitionCategoryCode(
			String processDefinitionCategoryCode) {
		this.processDefinitionCategoryCode = processDefinitionCategoryCode;
	}

	public String getProcessInstanceInitiatorName() {
		return processInstanceInitiatorName;
	}

	public void setProcessInstanceInitiatorName(
			String processInstanceInitiatorName) {
		this.processInstanceInitiatorName = processInstanceInitiatorName;
	}

	public String getProcessInstanceStartTime() {
		return processInstanceStartTime;
	}

	public void setProcessInstanceStartTime(String processInstanceStartTime) {
		this.processInstanceStartTime = processInstanceStartTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReviewOpinion() {
		return reviewOpinion;
	}

	public void setReviewOpinion(String reviewOpinion) {
		this.reviewOpinion = reviewOpinion;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}

	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	public String getTaskUrl() {
		return taskUrl;
	}

	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}

}
