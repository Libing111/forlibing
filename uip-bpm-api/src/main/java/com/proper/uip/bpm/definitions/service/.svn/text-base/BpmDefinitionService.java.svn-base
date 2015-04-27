/* <p>文件名称: BpmDefinitionService.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-7-25</p>
 * <p>完成日期：2013-7-25</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-7-25 下午3:22:15
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.bpm.definitions.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.proper.uip.api.bpm.definitions.entity.AssignmentEntity;
import com.proper.uip.api.bpm.definitions.entity.MonitorEntity;
import com.proper.uip.api.bpm.definitions.entity.TaskDefinitionEntity;
import com.proper.uip.bpm.definitions.entity.IdentityLinkEntity;
import com.proper.uip.bpm.definitions.entity.UserTreeNode;

public interface BpmDefinitionService {
	String ASSIGN_TYPE_USER = "identitylink.category.user";
	/**
	 * 获取任务定义列表
	 * @param processDefinitionId
	 * @return
	 */
	List<TaskDefinitionEntity> getTaskDefinitionList(String processDefinitionId, String taskDefinitionName,String scenarioItemName, String identityLinkName);
	/**
	 * 获取任务定义列表
	 * @param processDefinitionId
	 * @return
	 */
	List<TaskDefinitionEntity> getTaskDefinitionListNew(String processDefinitionId, String taskDefinitionName,String scenarioItemName, String identityLinkName);

	/**
	 * 获取任务定义
	 * @param processDefinitionId
	 * @return
	 */
	TaskDefinitionEntity getTaskDefinitionKey(String processDefinitionId);
	
	/**
	 * 保存任务定义
	 * @param taskDefinitionEntity
	 */
	void saveTaskDefinition(TaskDefinitionEntity taskDefinitionEntity);

	/**
	 * 保存经办人
	 * @param assignmentEntity
	 */
	void saveIdentityLink(String processDefinitionId,String taskDefinitionKey, String loginNames, String strategyId,String scenarioItemId);

	/**
	 * 保存经办人
	 * @param assignmentEntity
	 */
	void saveIdentityLink(AssignmentEntity assignmentEntity,String linkData);
	
	/**
	 * 构造用户树
	 * @return
	 */
	Map<String, List<UserTreeNode>> buildUserTree(HttpServletRequest request, String processDefinitionId, String taskDefinitionKey,String scenarioItemId, String raName, String UserName);
	/**
	 * 查询监控人设置
	 * @param processDefinitionId
	 * @return
	 */
	List<MonitorEntity> getMonitorList(String processDefinitionId);
	/**
	 * 保存监控人
	 * @param MonitorEntity
	 */
	void saveMonitorIdentityLink(String processDefinitionId, String loginNames);
	/**
	 * 监控人树
	 * @param MonitorEntity
	 */
	Map<String, List<UserTreeNode>> buildMonitorUserTree(
			String processDefinitionId);

	void deleteMonitorById(String id);

	/**
	 * 根据登录名获取流程定义
	 * @param loginName
	 * @return
	 */
	Set<String> getProcessDefinitionIdList(String loginName,String categoryCode);
	/**
	 * 根据注册机构 或 姓名 模糊查询
	 * @param raName
	 * @param userName
	 */
	//void queryRaAndUser(String raName, String userName);

	TaskDefinitionEntity getTaskDefinition(String taskDefinitionKey,
			String strategyId, String processDefinitionId,String scenarioItemId);

	TaskDefinitionEntity getbybpmTaskDefinitionId(String id);

	void deleteTaskDefinitionsById(String id);

	AssignmentEntity getAssignmentEntity(String processDefinitionId,
			String taskDefinitionKey, String strategyId, String scenarioItemId);

	List<AssignmentEntity> getAssignmentEntityList(String processDefinitionId,
			String taskDefinitionKey, String strategyId, String scenarioItemId);

	void deleteAssignmentById(String id);
	
	List<IdentityLinkEntity> tranLinkToJson(AssignmentEntity assignmentEntity);
}
