/* <p>文件名称: BpmScenarioStrategy.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-10-31</p>
 * <p>完成日期：2013-10-31</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-10-31 下午1:50:14
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.api.bpm.service;

import java.util.List;

import org.activiti.engine.identity.User;

import com.proper.uip.api.bpm.definitions.entity.AssignmentEntity;
import com.proper.uip.api.bpm.definitions.entity.ScenarioItem;

public abstract class BpmScenarioStrategy {
	private String id;
	
	private String name;

	/**
	 * 经办人场景适应性判定
	 * @param scenarioItem
	 * @param entity
	 * @return
	 */
	public abstract boolean check(String scenarioItem, AssignmentEntity entity);
	
	/**
	 * 获取场景取值列表
	 * @return
	 */
	public abstract List<ScenarioItem> getScenarioItems();
	
	
	public boolean haveUserScope(ScenarioItem item){
		return false;
	}
	
	public List<User> getUserScope(ScenarioItem item){
		return null;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
