/* <p>文件名称: ResourceService.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-8-24</p>
 * <p>完成日期：2013-8-24</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-8-24 下午12:01:07
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.api.security.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;

public interface ResourceService {
	/**
	 * 获取当前用户有权限的资源
	 * @param moc 资源类型
	 * @return
	 */
	List<Resource> getResourcesOfCurrentUser(HttpServletRequest request, String moc);
	
	/**
	 * 获取资源(非权限过滤)
	 * @param moc 资源类型
	 * @return
	 */
	List<Resource> getResourcesByMoc(String moc);
	
	/**
	 * 获取资源分页数据(非权限过滤)
	 * @param pageConfig
	 * @param moc
	 * @return
	 */
	Page<Resource> getResourcePageByMoc(PageConfig pageConfig, String moc);
	
	/**
	 * 获取资源(非权限过滤)
	 * @param moc 资源类型
	 * @return
	 */
	List<Resource> getResourcesByMoc(String moc, List<String> resourceCategoryList);
	/**
	 * 获取当前用户有权限的资源
	 * @param moc 资源类型
	 * @return
	 */
	List<Resource> getModuleResourcesOfCurrentUser(HttpServletRequest request);

	/**
	 * 获取当前用户资源树中某节点下某类型的资源
	 * @param parent 父亲节点code
	 * @param moc 资源类型
	 * @return
	 */
	List<Resource> getResourcesOfCurrentUser(HttpServletRequest request, String parent, String moc);
	
	

	/**
	 * 获取当前用户资源树中某节点下某类型的资源，倒序显示
	 * @param parent 父亲节点code
	 * @param moc 资源类型
	 * @return
	 */
	List<Resource> getResourcesOfCurrentUserDesc(HttpServletRequest request, String parent, String moc);
	
	/**
	 * 获取资源
	 * @param code 资源编号（资源码）
	 * @return
	 */
	Resource getResourceByCode(String code);
	
	/**
	 * 获取资源
	 * @param id 资源编号（资源码）
	 * @return
	 */
	Resource getResourceById(String id);
}
