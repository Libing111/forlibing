/* <p>文件名称: QuickApplicationService.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-11-3</p>
 * <p>完成日期：2014-11-3</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-11-3上午11:41:04
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.api.desktop.service;

import java.util.List;

import com.proper.uip.api.desktop.entity.QuickApplicationEntity;

public interface QuickApplicationService {
	/**
	 * 增加快捷功能
	 * @param entity
	 * @return
	 */
	QuickApplicationEntity save(QuickApplicationEntity entity);
	
	
	/**
	 * 增加快捷功能列表
	 * @param entityList
	 * @return
	 */
	void save(List<QuickApplicationEntity> entityList);
	
	
	/**
	 * 删除快捷功能
	 * @param id
	 * @return
	 */
	void delete(String id);
	
	
	/**
	 * 删除快捷功能列表
	 * @param idList
	 * @return
	 */
	void delete(List<String> idList);
	
	/**
	 * 获取指定用户的快捷应用列表
	 * @param userId
	 * @return
	 */
	List<QuickApplicationEntity> getQuickApplications(String userId);
	
	/**
	 * 获取指定用户的快捷应用列表
	 * @param userId
	 * @return
	 */
	QuickApplicationEntity getQuickApplication(String userId, String resourceId);
}

