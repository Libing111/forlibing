/* <p>文件名称: RegistrationAuthorityService.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-8-24</p>
 * <p>完成日期：2013-8-24</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-8-24 上午11:57:15
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

import com.proper.uip.api.security.entity.RegistrationAuthority;
import com.proper.uip.api.security.entity.Role;
import com.proper.uip.api.security.entity.User;

public interface RegistrationAuthorityService {
	
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
	RegistrationAuthority builderRa(RegistrationAuthority newRa);
	
	/**
	 * 创建注册机构
	 * 必填信息：
	 * 注册机构名称
	 * 编号：机构码 
	 * 机构分类编号：存储统一词汇Code
	 * extendId：业务机构Id
	 * @param newRa
	 * @param adminUserCategoryCode
	 * @param fullAuthorityUser
	 * @return
	 */
	RegistrationAuthority builderRa(RegistrationAuthority newRa, User fullAuthorityUser);

	
	/**
	 * 创建注册机构
	 * 必填信息：
	 * 注册机构名称
	 * 编号：机构码 
	 * 机构分类编号：存储统一词汇Code
	 * extendId：业务机构Id
	 * @param newRa
	 * @param adminUserCategoryCode
	 * @param fullAuthorityUser
	 * @return
	 */
	RegistrationAuthority builderRa(RegistrationAuthority newRa, String adminUserCategoryCode, User fullAuthorityUser);
	
	/**
	 * 创建注册机构
	 * 必填信息：
	 * 注册机构名称
	 * 编号：机构码 
	 * 机构分类编号：存储统一词汇Code
	 * extendId：业务机构Id
	 * @param newRa
	 * @param adminUserCategoryCode
	 * @param fullAuthorityUser
	 * @param randomPassword
	 * @return
	 */
	RegistrationAuthority builderRa(RegistrationAuthority newRa, String adminUserCategoryCode, User fullAuthorityUser, boolean randomPassword);
	
	/**
	 * 创建注册机构
	 * 必填信息：
	 * 注册机构名称
	 * 编号：机构码 
	 * 机构分类编号：存储统一词汇Code
	 * extendId：业务机构Id
	 * @param newRa
	 * @param adminUser 机构管理员用户
	 * @param fullAuthorityUser 机构最大权限用户
	 * @return
	 */
	RegistrationAuthority builderRa(RegistrationAuthority newRa, User adminUser, User fullAuthorityUser);
	
	
	/**
	 * 创建注册机构管理员
	 * @param ra
	 * @param userCategoryCode
	 * @return
	 */
	User buildRaAdministrator(RegistrationAuthority ra, String userCategoryCode);
	
	/**
	 * 创建注册机构管理员
	 * @param ra
	 * @param userCategoryCode
	 * @param randomPassword
	 * @return
	 */
	User buildRaAdministrator(RegistrationAuthority ra, String userCategoryCode, boolean randomPassword);
	
	/**
	 * 创建注册机构最大业务权限用户
	 * @param ra
	 * @param user
	 * @return
	 */
	User buildRaUserWithFullAuthority(RegistrationAuthority ra, User user);
	
	/**
	 * 创建注册机构最大业务权限用户
	 * @param ra
	 * @param user
	 * @param randomPassword
	 * @return
	 */
	User buildRaUserWithFullAuthority(RegistrationAuthority ra, User user, boolean randomPassword);
	
	/**
	 * 重置机构资源集
	 * @param resouceSets
	 * @param needResetRaFullAuthority：true表示需要重置最大权限角色；false表示不需要重置最大权限角色
	 */
	void resetRaResouceSets(String raId, List<String> resouceSetCodes, boolean needResetRaFullAuthority);
	
	/**
	 * 重置机构资源集
	 * 删除机构原先的资源集，增加新指定的资源集
	 * 不重置最大权限角色
	 * @param resouceSets
	 */
	void resetRaResouceSets(String raId, List<String> resouceSetCodes);
	
	/**
	 * 重置最大角色权限
	 * 当机构资源集变化时，需要重置最大资源权限
	 * @param raId
	 * @return
	 */
	Role resetRaFullAuthorityByRaId(String raId);
	
	/**
	 * 重置最大角色权限
	 * 当机构资源集变化时，需要重置最大资源权限
	 * @param resourceSetId
	 * @return
	 */
	Role resetRaFullAuthorityByResourceSetId(String resourceSetId);
	
	/**
	 * 重置机构最大角色权限
	 * 当机构资源集变化时，需要重置最大资源权限
	 * @param extendId
	 * @return
	 */
	Role resetRaFullAuthorityByExtendId(String extendId);
	
	
	/**
	 * 保存注册机构
	 * @param newRa
	 * @return
	 */
	RegistrationAuthority saveRa(RegistrationAuthority ra);
	
	/**
	 * 获取注册机构
	 * @param id
	 * @return
	 */
	RegistrationAuthority getById(String id);
	/**
	 * 获取所有的注册机构
	 * @return
	 */
	List<RegistrationAuthority> getAllRa();
	
	/**
	 * 获取注册机构
	 * @param extendId
	 * @return
	 */
	RegistrationAuthority getByExtendId(String extendId);
	
	/**
	 * 获取注册机构
	 * @param extendId
	 * @return
	 */
	RegistrationAuthority getByCode(String code);
	
	
	/**
	 * 注销注册机构
	 * @param extendId：业务机构Id
	 */
	void unregisterRaByExtendId(String extendId);
	
	/**
	 * 注销注册机构
	 * @param extendIds：业务机构Id
	 */
	void unregisterRaByExtendIds(List<String> extendIds);
	
	/**
	 * 注销注册机构
	 * @param id：注册机构Id
	 */
	void unregisterRaById(String id);
	/**
	 * 获取本机构下核名的信息
	 * @param extendId
	 * @return
	 */
	List<RegistrationAuthority> getRaListByExtendId(String extendId);
	/**
	 * 根据分类来查询注册机构
	 * @param categoryId
	 * @return
	 */
	List<RegistrationAuthority> getByCategoryId(String categoryId);
	
}
