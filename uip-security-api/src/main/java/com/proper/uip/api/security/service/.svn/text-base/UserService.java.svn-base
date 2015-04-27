/* <p>文件名称: UserService.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-8-24</p>
 * <p>完成日期：2013-8-24</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-8-24 上午11:49:25
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.api.security.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.proper.uip.api.security.entity.RegistrationAuthority;
import com.proper.uip.api.security.entity.Role;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;

public interface UserService {
	/**
	 * 获取所有用户
	 * @return
	 */
	List<User> getAllUsers();
	
	/**
	 * 保存用户
	 * @return
	 */
	User saveUser(User user);
	
	
	/**
	 * 生成账号
	 * @param userCategoryCode 用户分类代码，统一词汇表配置
	 * @param raId 注册机构Id
	 * @param defautAccountName 默认登录名，默认为用户编号
	 * @param name 姓名
	 * @param code 用户编号，工作人员存储工号，机构管理员存储机构编号
	 * @param extendId 业务人员Id
	 * @param extendProperties 扩展信息，存业务信息
	 * @param randomPassword 是否采用随机密码
	 * @return
	 */
	User buildUser(String userCategoryCode, String registrationAuthorityId, String defautAccountName, String name, String code, String extendId, Map<String, String> extendProperties, boolean randomPassword);
	
	/**
	 * 生成账号
	 * @param userCategoryCode 用户分类代码，统一词汇表配置
	 * @param raId 注册机构Id
	 * @param defautAccountName 默认登录名，默认为用户编号
	 * @param name 姓名
	 * @param code 用户编号，工作人员存储工号，机构管理员存储机构编号
	 * @param extendId 业务人员Id
	 * @param extendProperties 扩展信息，存业务信息
	 * @return
	 */
	User buildUser(String userCategoryCode, String registrationAuthorityId, String defautAccountName, String name, String code, String extendId, Map<String, String> extendProperties);
	
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
	User buildUser(User newUser);
	
	
	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	User getById(String id);
	
	/**
	 * 获取用户
	 * @param ids
	 * @return
	 */
	List<User> getByIds(Collection<String> ids);
	/**
	 * 根据注册机构Id获取用户
	 * @param RaId
	 * @return
	 */
	List<User> getByRaId(String id);
	
    /**
     * 获取用户
     * @param extendId
     * @return
     */
	User getByExtendId(String extendId);
	
	/**
	 * 获取用户，使用SQL语句中Like运算符
	 * @param nameLike
	 * @return
	 */
	List<User> getByNameLike(String nameLike);
	
	/**
	 * 注销用户
	 * @param id
	 */
	void unregisterUserById(String id);
	
	/**
	 * 注销用户
	 * @param extendId
	 */
	void unregisterUserByExtendId(String extendId);
	
	/**
	 * 获取用户
	 * @param loginName登录名
	 * @return
	 */
	User getUserByLoginName(String loginName);
	
	/**
	 * 获取用户
	 * @param loginName登录名
	 * @return
	 */
	List<User> getUserByLoginName(Collection<String> loginNameSet);
	
	/**
	 * 获取用户
	 * @param account用户账号
	 * @return
	 */
	User getUserByAccount(String account);
	
	/**
	 * 为用户分配角色
	 * @param user
	 * @param role
	 */
	void setRoleForUser(User user, Role role);
	/**
	 * 分页获取用户
	 * @param config
	 * @param name
	 * @param loginName
	 * @param raName
	 * @param raId
	 * @return
	 */
	Page<User> findAllUser(PageConfig config,String raId);
	
	/**
	 * 分页获取用户
	 * @param config
	 * @param userCategoryCode
	 * @param name
	 * @param loginName
	 * @param raName
	 * @param raId
	 * @return
	 */
	Page<User> findAllUser(PageConfig config, String userCategoryCode, String name, String loginName,String raName, String raId);
	/**
	 * 分页获取用户
	 * @param config
	 * @param userCategoryCode
	 * @param name
	 * @param loginName
	 * @param raName
	 * @param ra
	 * @return
	 */
	Page<User> findAllUser(PageConfig config, String userCategoryCode, String name, String loginName,String raName, RegistrationAuthority ra,String resetStatus);
	/**
	 * 分页获取用户
	 * @param config
	 * @param name
	 * @param loginName
	 * @param raCode
	 * @return
	 */
	Page<User> findAllUser(PageConfig config,  String name, String loginName,String raCode);
	/**
	 * 更新账号
	 * 若账号已经存在，则在账号后添加“_序列号”，例如“XXX_01”，“YYY_03”等
	 * 参数不能为空，否则返回null
	 * @param userId
	 * @param account
	 * @return
	 */
	User updateAccount(String userId, String newAccount);
	
	
	/**
	 * 更新账号
	 * 若账号已经存在，则在账号后添加“_序列号”，例如“XXX_01”，“YYY_03”等
	 * 参数不能为空，否则返回null
	 * @param user
	 * @param account
	 * @return
	 */
	User updateAccount(User user, String newAccount);
}
