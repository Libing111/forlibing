/* <p>文件名称: TaskDefinitionEntity.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-7-25</p>
 * <p>完成日期：2013-7-25</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-7-25 下午4:39:35
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.api.bpm.definitions.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "bpm_taskdef_c")
public class TaskDefinitionEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	/**
	 * 流程定义id
	 */
	private String processDefinitionId;
	
	/**
	 *任务定义Key
	 */
	private String taskDefinitionKey;
	
	/**
	 * 任务定义名称
	 */
	private String taskDefinitionName;
	
	/**
	 * 序号
	 */
	private int sequenceNumber;
	
	/**
	 * 经办人主题，多个时，逗号分隔
	 */
	private String identityLinkNames;
	
	/**
	 * 策略id
	 */
	private String strategyId;
	/**
	 * 策略name
	 */
	private String strategyName;
	/**
	 * 场景id
	 */
	private String scenarioItemId;
	/**
	 * 场景name
	 */
	private String scenarioItemName;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}

	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	public String getTaskDefinitionName() {
		return taskDefinitionName;
	}

	public void setTaskDefinitionName(String taskDefinitionName) {
		this.taskDefinitionName = taskDefinitionName;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getIdentityLinkNames() {
		return identityLinkNames;
	}

	public void setIdentityLinkNames(String identityLinkNames) {
		this.identityLinkNames = identityLinkNames;
	}

	public String getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(String strategyId) {
		this.strategyId = strategyId;
	}

	public String getStrategyName() {
		return strategyName;
	}

	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}

	public String getScenarioItemId() {
		return scenarioItemId;
	}

	public void setScenarioItemId(String scenarioItemId) {
		this.scenarioItemId = scenarioItemId;
	}

	public String getScenarioItemName() {
		return scenarioItemName;
	}

	public void setScenarioItemName(String scenarioItemName) {
		this.scenarioItemName = scenarioItemName;
	}


	
	
	
}

