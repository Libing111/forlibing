/* <p>文件名称: BpmService.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-7-26</p>
 * <p>完成日期：2013-7-26</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-7-26 上午9:00:08
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.api.bpm.service;

import java.util.Set;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;

public interface BpmService {
	/**
	 * 获取候选人列表
	 * @param taskDefinitionKey
	 * @param processDefinitionId
	 * @param scenarioItem
	 * @return 用户登录名列表
	 */
	Set<String> getCandiateUsers(String processDefinitionId, String taskDefinitionKey, String scenarioItem);
	
	/**
	 * 获取候选人列表
	 * @param processDefinitionId
	 * @param taskDefinitionKey
	 * @return 用户登录名列表
	 */
	Set<String> getCandiateUsers(String processDefinitionId, String taskDefinitionKey);
	
	
	/**
	 * 获取候选组列表
	 * @param processDefinitionId
	 * @param taskDefinitionKey
	 * @return 用户组列表
	 */
	Set<String> getCandiateGroups(String processDefinitionId, String taskDefinitionKey);
	
	
	/**
	 * 获取候选组列表
	 * @param task
	 * @param inititor
	 * @return 用户组列表
	 */
	public Set<String> getCandiateUsersNew(DelegateExecution  execution, String taskDefinitionKey);
	
	/**
	 * 获取候选人列表（在流程发起之前）
	 * @param processDefinitionId
	 * @param taskDefinitionKey
	 * @return
	 */
	public Set<String> getCandiateUsersNotStart(String processDefinitionId, String taskDefinitionKey);
	
	/**
	 * 获取流程定义key
	 * @param organizationCode
	 * @param resourceCode
	 * @return
	 */
	String getProcessDefinitionKey(String organizationCode, String resourceCode);
	
	/**
	 * 获取流程定义Id
	 * @param organizationCode
	 * @param resourceCode
	 * @return
	 */
	String getProcessDefinitionId(String organizationCode, String resourceCode);
}
