/* <p>文件名称: SecurityService.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-5-10</p>
 * <p>完成日期：2013-5-10</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-5-10 下午1:49:48
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
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.proper.uip.api.security.entity.RegistrationAuthority;
import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.User;

public interface SecurityService {
	/**
	 * 检查用户是否有url访问权限
	 * @param user
	 * @param url
	 * @return
	 */
	boolean checkPermission(User user, String url);
	
	/**
	 * 检查当前用户是否有url访问权限
	 * @param user
	 * @param url
	 * @return
	 */
	@Deprecated
	boolean checkPermissionOfCurrentUser(HttpServletRequest request, String url);
	
	/**
	 * 获取当前用户有权限的资源
	 * @param moc 资源类型
	 * @return
	 */
	@Deprecated
	List<Resource> getResourcesOfCurrentUser(HttpServletRequest request, String moc);
	
	/**
	 * 获取当前用户有权限的资源
	 * @param moc 资源类型
	 * @return
	 */
	@Deprecated
	List<Resource> getModuleResourcesOfCurrentUser(HttpServletRequest request);

	/**
	 * 获取当前用户资源树中某节点下某类型的资源
	 * @param parent 父亲节点code
	 * @param moc 资源类型
	 * @return
	 */
	@Deprecated
	List<Resource> getResourcesOfCurrentUser(HttpServletRequest request, String parent, String moc);
	
	

	/**
	 * 获取当前用户资源树中某节点下某类型的资源，倒序显示
	 * @param parent 父亲节点code
	 * @param moc 资源类型
	 * @return
	 */
	@Deprecated
	List<Resource> getResourcesOfCurrentUserDesc(HttpServletRequest request, String parent, String moc);
	
	/**
	 * 获取当前登录用户
	 * @param request
	 * @return
	 */
	User getCurrentUser(HttpServletRequest request);
	
	
	/**
	 * 生成账号
	 * @param userCategoryCode 用户分类代码，统一词汇表配置
	 * @param raId 注册机构Id
	 * @param defautLoginName 默认登录名，默认为用户编号
	 * @param name 姓名
	 * @param code 用户编号，工作人员存储工号，机构管理员存储机构编号
	 * @param extendId 业务人员Id
	 * @param extendProperties 扩展信息，存业务信息
	 * @return
	 */
	@Deprecated
	User buildUser(String userCategoryCode, String registrationAuthorityId, String defautLoginName, String name, String code, String extendId, Map<String, String> extendProperties);
	
	/**
	 * 保存用户
	 * @return
	 */
	@Deprecated
	User saveUser(User user);
	
	
	/**
	 * 注销用户
	 * @param extendId
	 */
	@Deprecated
	void unregisterUserByExtendId(String extendId);
	
	/**
	 * 注销用户
	 * @param extendId
	 */
	@Deprecated
	void unregisterUserById(String id);
	
	/**
	 * 创建注册机构
	 * 必填信息：
	 * 注册机构名称
	 * 管理员账号：登陆名
	 * 编号：机构码 
	 * 版本
	 * 机构分类名称：统一词汇名称
	 * 机构分类Id：存储统一词汇Code
	 * extendId：业务机构Id
	 * @param newRa
	 * @return
	 */
	@Deprecated
	RegistrationAuthority BuilderRa(RegistrationAuthority newRa);
	
	
	/**
	 * 保存注册机构
	 * @param newRa
	 * @return
	 */
	@Deprecated
	RegistrationAuthority saveRa(RegistrationAuthority ra);
	
	/**
	 * 注销注册机构
	 * @param extendId：业务机构Id
	 */
	@Deprecated
	void unregisterRaByExtendId(String extendId);
	
	/**
	 * 注销注册机构
	 * @param extendId：业务机构Id
	 */
	@Deprecated
	void unregisterRaById(String id);
	
	/**
	 * 获取资源
	 * @param code 资源编号（资源码）
	 * @return
	 */
	@Deprecated
	Resource getResourceByCode(String code);
	
	/**
	 * 获取用户
	 * @return
	 */
	@Deprecated
	User getUserByLoginName(String loginName);
	
	/**
	 * 创建注册机构
	 * @param currentUser 机构创建者，取当前用户
	 * @param raEntity 注册机构实体类
	 * raEntity中，name、code、categoryCode必须填写
	 * categoryCode取统一词汇表定义中注册机构类别中定义的注册机构分类代码
	 * @return
	 */
	@Deprecated
	RegistrationAuthority createRegistrationAuthority(User currentUser, RegistrationAuthority raEntity);
	
	
	User getAccount(String account);
	
}
