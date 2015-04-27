/* <p>文件名称: ProcessInstancesResponse.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-7-25</p>
 * <p>完成日期：2013-7-25</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-7-25 下午1:10:31
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.bpm.process.instanse.entity;

import java.io.Serializable;
import java.util.Map;

import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;

import com.proper.uip.api.bpm.util.RequestUtil;



public class ProcessInstancesResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	String id;
	String businessKey;
	String processDefinitionId;
	String startTime;
	String startUserId;
	String endTime;
	Map<String, Object> variables;
	String processInstanceInitiatorName;
	String processInstanceName;
	String completed;
	boolean isSuspended;

	public ProcessInstancesResponse(HistoricProcessInstanceEntity processInstance, Map<String, Object> variables) {
		this.setId(processInstance.getId());
		this.setBusinessKey(processInstance.getBusinessKey());
		this.setStartTime(RequestUtil.dateToString(processInstance.getStartTime()));
		this.setEndTime(processInstance.getEndTime() != null ? RequestUtil.dateToString(processInstance.getEndTime()) : null);
		this.setProcessDefinitionId(processInstance.getProcessDefinitionId());
		this.setStartUserId(processInstance.getStartUserId());

		this.variables = variables;
		
		this.processInstanceInitiatorName =  (String) variables.get("processInstanceInitiatorName");
		processInstanceName = (String) variables.get("processInstanceName");
	}
	public ProcessInstancesResponse(){
		
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

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public boolean isSuspended() {
		return isSuspended;
	}

	public void setSuspended(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}

	public String getCompleted() {
		return completed;
	}

	public void setCompleted(String completed) {
		this.completed = completed;
	}
	
	
}
