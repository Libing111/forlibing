/* <p>文件名称: BpmAssignmentEntity.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-7-25</p>
 * <p>完成日期：2013-7-25</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-7-25 下午2:07:26
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import net.sf.json.JSONObject;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.StringUtils;

import com.proper.uip.common.utils.ReflectionUtils;

@Entity
@Table(name = "bpm_prodef_assign_c")
public class AssignmentEntity implements Serializable{
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
	 * IdentityLink的类型，枚举，人员、职务、职位、部门四种类型。
	 */
	private String typeName;
	
	/**
	 * IdentityLink的类型，枚举，人员、职务、职位、部门四种类型。
	 */
	private String typeId;

	/**
	 * IdentityLink Id，字符串，人员Id（UserId）、职务Id，职务Id，部门Id
	 */
	private String identityLinkId;
	
	/**
	 * 经办人   personID，分号分隔
	 */
	@Column(name = "link_person", length = 4000)
	private String linkPerson;
	
	/**
	 * 组织  OrganizationId，分号分隔
	 */
	@Column(name = "link_organization", length = 4000)
	private String linkOrganization;
	
	/**
	 * 职务  jobID，分号分隔
	 */
	@Column(name = "link_job", length = 4000)
	private String linkJob;
	
	/**
	 * 职位  存储为json串  通过组织而和职务两个ID来确定，分号分隔。同一职位下的组织和职务id用逗号分隔
	 */
	@Column(name = "link_position", length = 4000)
	private String linkPosition;
	
	/**
	 * 经办人   personname，分号分隔
	 */
	@Column(name = "link_person_name", length = 4000)
	private String linkPersonName;
	
	/**
	 * 组织  Organizationname，分号分隔
	 */
	@Column(name = "link_organization_name", length = 4000)
	private String linkOrgName;
	
	/**
	 * 职务  jobname，分号分隔
	 */
	@Column(name = "link_job_name", length = 4000)
	private String linkJobName;
	
	/**
	 * 职位  存储为json串  通过组织而和职务两个ID来确定，分号分隔。同一职位下的组织和职务id用逗号分隔
	 */
	@Column(name = "link_position_name", length = 4000)
	private String linkPositionName = "";
	/**
	 * IdentityLink 名字，字符串，人员姓名、职务名称，职务名称，部门名称
	 */
	@Column(name = "IDENTITY_LINK_NAME", length = 4000)
	private String identityLinkName;
	
	/**
	 * 选人过滤规则
	 */
	private String filterRule;
	
	/**
	 * 自动选人规则
	 */
	private String autoRule;
	
	/**
	 * 设置方式
	 */
	@Column(name = "mode_")
	private String mode;
	
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getIdentityLinkId() {
		return identityLinkId;
	}

	public void setIdentityLinkId(String identityLinkId) {
		this.identityLinkId = identityLinkId;
	}

	public String getIdentityLinkName() {
		return identityLinkName;
	}

	public void setIdentityLinkName(String identityLinkName) {
		this.identityLinkName = identityLinkName;
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

	public String getLinkPerson() {
		return linkPerson;
	}

	public void setLinkPerson(String linkPerson) {
		this.linkPerson = linkPerson;
	}

	public String getLinkOrganization() {
		return linkOrganization;
	}

	public void setLinkOrganization(String linkOrganization) {
		this.linkOrganization = linkOrganization;
	}

	public String getLinkJob() {
		return linkJob;
	}

	public void setLinkJob(String linkJob) {
		this.linkJob = linkJob;
	}

	public String getLinkPosition() {
		return linkPosition;
	}

	public void setLinkPosition(String linkPosition) {
		this.linkPosition = linkPosition;
	}

	public String getLinkPersonName() {
		return linkPersonName;
	}

	public void setLinkPersonName(String linkPersonName) {
		this.linkPersonName = linkPersonName;
	}


	public String getLinkOrgName() {
		return linkOrgName;
	}

	public void setLinkOrgName(String linkOrgName) {
		this.linkOrgName = linkOrgName;
	}

	public String getLinkJobName() {
		return linkJobName;
	}

	public void setLinkJobName(String linkJobName) {
		this.linkJobName = linkJobName;
	}

	public String getLinkPositionName() {
		return linkPositionName;
	}

	public void setLinkPositionName(String linkPositionName) {
		this.linkPositionName = linkPositionName;
	}

	public String getFilterRule() {
		return filterRule;
	}

	public void setFilterRule(String filterRule) {
		this.filterRule = filterRule;
	}

	public String getAutoRule() {
		return autoRule;
	}

	public void setAutoRule(String autoRule) {
		this.autoRule = autoRule;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getProperty(String fieldName, String key) {
		Object fieldValue = ReflectionUtils.getFieldValue(this, fieldName);
		if (fieldValue == null) {
			fieldValue = "{}";
		}
		if (fieldValue instanceof String && !StringUtils.hasText((String) fieldValue)) {
			fieldValue = "{}";
		}
		JSONObject jsonObject = JSONObject.fromObject(fieldValue);
		if (jsonObject.containsKey(key)) {
			return jsonObject.get(key).toString();
		}
		return null;
	}

	public void putProperty(String fieldName, String key, String value) {
		Object fieldValue = ReflectionUtils.getFieldValue(this, fieldName);
		if (fieldValue == null) {
			fieldValue = "{}";
		}
		if (fieldValue instanceof String && !StringUtils.hasText((String) fieldValue)) {
			fieldValue = "{}";
		}
		JSONObject jsonObject = JSONObject.fromObject(fieldValue);
		jsonObject.put(key, value);
		ReflectionUtils.setFieldValue(this, fieldName, jsonObject.toString());
	}
	
}
