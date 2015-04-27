package com.proper.uip.security.user.service;

import java.util.List;

import com.proper.uip.api.security.entity.Role;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;

public interface UserService {

	
	Page<User> findAllUser(PageConfig config, String name, String loginName,String raName, String raId);

	void saveUser(User userEntity);

	User getUserById(String id);

	void deleteUserById(String id);

	List<User> getAll();

	User getUserByLoginName(String LoginName);

	void saveRole(String selectIds, String userId);

	Page<Role> getRole(PageConfig pageConfig, String userId);

	void delRoleUserRelationById(String id);

	User getLogin(String loginName);

	/**
	 * 
	 * @param roleIds 逗号分隔
	 * @param userId
	 */
	void delRoleUserRelation(String roleIds, String userId);

	Page<User> findAll(PageConfig pageConfig);

	Page<User> findAll(PageConfig pageConfig, String name, String loginName,String raName);

	List<User> getByCategoryId(String id);

    /**
     * 获取用户
     * @param extendId
     * @return
     */
	User getByExtendId(String extendId);

}
