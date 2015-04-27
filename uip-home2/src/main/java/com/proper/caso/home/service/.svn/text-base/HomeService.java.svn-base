/* <p>文件名称: HomeService.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-8-3</p>
 * <p>完成日期：2013-8-3</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-8-3 上午9:54:26
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.caso.home.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.proper.caso.home.application.model.MenuNode;
import com.proper.uip.api.desktop.entity.HomeMessageEntity;
import com.proper.uip.api.desktop.entity.QuickApplicationEntity;
import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.User;


public interface HomeService {
	
	/**
	 * 获取顶级菜单
	 * @param currentUser
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	List<MenuNode> getTopMenus(HttpServletRequest request) throws UnsupportedEncodingException;
	
	
	/**
	 * 获取模块（包含栏目）
	 * @param request
	 * @param pagingId
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	List<MenuNode> getModules(HttpServletRequest request, String pagingId) throws UnsupportedEncodingException;
	
	/**
	 * 获取快捷菜单列表
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	List<QuickApplicationEntity> getQuickModules(HttpServletRequest request) throws UnsupportedEncodingException;
	
	
	/**
	 * 获取快捷菜单
	 * @param request
	 * @param quickModuleId
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	QuickApplicationEntity getQuickModuleById(HttpServletRequest request, String quickModuleId) throws UnsupportedEncodingException;
	
	/**
	 * 获取当前登录人可以添加的快捷菜单
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	List<Resource>  getQuickModuleCandidates(HttpServletRequest request) throws UnsupportedEncodingException;
	
	/**
	 * 添加快捷菜单
	 * @param request
	 * @param quickModule
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	QuickApplicationEntity addQuickModule(HttpServletRequest request, QuickApplicationEntity quickModule) throws UnsupportedEncodingException;
	
	/**
	 * 删除快捷菜单
	 * @param request
	 * @param quickModuleId
	 * @throws UnsupportedEncodingException
	 */
	void deleteQuickModule(HttpServletRequest request, String quickModuleId) throws UnsupportedEncodingException;


	/**
	 * 获取快捷菜单
	 * @param request
	 * @param resourceId
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	QuickApplicationEntity getQuickModuleByResourceId(HttpServletRequest request, String resourceId) throws UnsupportedEncodingException;


	List<MenuNode> getModulesWithoutGroup(HttpServletRequest request, String pagingId);


	List<MenuNode> getPersonalIssueModules(HttpServletRequest request, String personalIssue);


	List<HomeMessageEntity> getTodayMaessageList(User user);
	
}
