/* <p>文件名称: QuickModuleService.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-11-24</p>
 * <p>完成日期：2014-11-24</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-11-24下午3:18:20
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.desktop.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.proper.uip.api.desktop.entity.QuickApplicationEntity;
import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.User;

public interface QuickModuleService {
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
	List<QuickApplicationEntity> getQuickModules(String userId);
	
	/**
	 * 获取指定用户的快捷应用列表
	 * @param userId
	 * @param resourceId
	 * @return
	 */
	QuickApplicationEntity getQuickModuleByResourceId(String userId, String resourceId);

	/**
	 * 获取当前人的资源列表
	 * @param currentUser
	 * @param string
	 * @param resourceCategoryList
	 * @return
	 */
	List<Resource> getResourcesOfCurrentUser(User currentUser, String string,
			List<String> resourceCategoryList);
}