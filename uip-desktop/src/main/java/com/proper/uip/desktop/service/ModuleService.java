/* <p>文件名称: ModuleService.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-11-24</p>
 * <p>完成日期：2014-11-24</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-11-24上午10:56:07
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
import java.util.Set;

import com.proper.uip.api.desktop.entity.ApplicationAndGroup;

public interface ModuleService {
	/**
	 * 分屏MOC常量
	 */
	String DESKTOP_MOC_PAGING = "paging";
	/**
	 * 分组MOC常量
	 */
	String DESKTOP_MOC_GROUP = "group";
	/**
	 * 应用MOC常量
	 */
	String DESKTOP_MOC_APPLICATION = "resource";
	
	/**
	 * 获取给定分屏所有子模块元素列表
	 * @param systemCategory
	 * @param parentId
	 * @param resourceIdFilterSet（存在子孙应用在Set中保留，否则去除）
	 * @param moc
	 * @return
	 */
	List<ApplicationAndGroup> getChildModulesByPagingId(String systemCategory, String pagingId, Set<String> resourceIdFilterSet);
	
	/**
	 * 获取模块元素列表
	 * @param systemCategory
	 * @param resourceIdFilterSet（存在子孙应用在Set中保留，否则去除）
	 * @param moc
	 * @return
	 */
	List<ApplicationAndGroup> getModules(String systemCategory, Set<String> resourceIdFilterSet, String moc);
}

