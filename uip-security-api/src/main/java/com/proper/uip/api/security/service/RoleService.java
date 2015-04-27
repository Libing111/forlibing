package com.proper.uip.api.security.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.ResourceChildNode;
import com.proper.uip.api.security.entity.ResourceTreeNode;
import com.proper.uip.api.security.entity.Role;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;

public interface RoleService {

	Page<Role> findAllRole(PageConfig config, String name, String categoryName, String raId,String raName);

	void saveRole(Role roleEntity);
	
	void saveRole(Role roleEntity,String ruleId, String principalIds,String principalValues);

	Role getRoleById(String id);

	void deleteRoleById(String id);

	Role getByName(String name);

	//List<TreeNode> buildAuthorityTree(String roleId);
	List<Role> getByRaId(String id);

	String getName(String roleId);

	Role getCode(String code);
	
	List<Role> getByCode(String code);

	Role getNameUnique(String name,String userRa);

	List<ResourceTreeNode> buildResourceTree(String roleId, boolean chkDisabled);
	
	List<ResourceTreeNode> buildResourceTree(String roleId);
	
	List<ResourceTreeNode> buildResourceTree(User user);

	Page<Role> findAll(PageConfig pageConfig, String name, String categoryName,String raName);

	List<Role> getByCategoryId(String id);

	List<Resource> getAllResourceByRa(User user, String moc);
	
	List<ResourceChildNode> findChildResource(User user, String parentChildCode);
	
	Map<String, String> getFilterResource(String roleId);
	
	Map<String, String> getChildResource(HttpServletRequest request,User user, String pagingId);
	
	List<Resource> findResource(User user, String parent);

}
