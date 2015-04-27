/* <p>文件名称: ProcessInstanceResponse.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-1-6</p>
 * <p>完成日期：2014-1-6</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-1-6下午1:56:31
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.api.bpm.process.instance.entity;

import java.io.Serializable;
import java.util.Map;

import org.activiti.engine.history.HistoricProcessInstance;

import com.proper.uip.api.bpm.util.RequestUtil;

public class ProcessInstanceResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 流程实例id
	 */
	private String id;
	
	/**
	 * 流程业务Key
	 */
	private String businessKey;
	
	/**
	 * 流程定义id
	 */
	private String processDefinitionId;
	
	/**
	 * 流程开始时间（申请时间）
	 */
	private String startTime;
	
	/**
	 * 流程发起人登录名（申请人登录名）
	 */
	private String startUserId;
	
	/**
	 * 流程实例变量
	 */
	Map<String, Object> variables;
	
	/**
	 * 流程发起人姓名
	 */
	String processInstanceInitiatorName;
	
	/**
	 * 流程实例名称（工作名称）
	 */
	String processInstanceName;
	
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

	public ProcessInstanceResponse(HistoricProcessInstance processInstance, Map<String, Object> variables) {
		this.setId(processInstance.getId());
		this.setBusinessKey(processInstance.getBusinessKey());
		this.setStartTime(RequestUtil.dateToString(processInstance.getStartTime()));
		this.setProcessDefinitionId(processInstance.getProcessDefinitionId());
		this.setStartUserId(processInstance.getStartUserId());
		
		this.processDefinitionCategoryCode = (String) variables.get("processDefinitionCategoryCode");
		this.processDefinitionCategoryId = (String) variables.get("processDefinitionCategoryId");
		this.processDefinitionCategoryName = (String) variables.get("processDefinitionCategoryName");
		this.processInstanceInitiatorName = (String) variables.get("processInstanceInitiatorName");
		this.processInstanceName = (String) variables.get("processInstanceName");
		
		this.variables = variables;
	}
	
	public ProcessInstanceResponse(){
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getStartUserId() {
		return startUserId;
	}

	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}
	
	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}


	public String getProcessInstanceInitiatorName() {
		return processInstanceInitiatorName;
	}

	public void setProcessInstanceInitiatorName(String processInstanceInitiatorName) {
		this.processInstanceInitiatorName = processInstanceInitiatorName;
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

	public void setProcessDefinitionCategoryId(String processDefinitionCategoryId) {
		this.processDefinitionCategoryId = processDefinitionCategoryId;
	}

	public String getProcessDefinitionCategoryCode() {
		return processDefinitionCategoryCode;
	}

	public void setProcessDefinitionCategoryCode(
			String processDefinitionCategoryCode) {
		this.processDefinitionCategoryCode = processDefinitionCategoryCode;
	}
}
