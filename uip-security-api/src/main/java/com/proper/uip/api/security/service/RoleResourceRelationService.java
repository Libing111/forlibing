package com.proper.uip.api.security.service;

import java.util.List;

import com.proper.uip.api.security.entity.RoleResourceRelation;

public interface RoleResourceRelationService {


	
	void deleteTree(String roleId, String resourceId);
	
	/**
	 * 修改权限
	 * @param roleId 选中的角色
	 * @param resourceSnoArray 选中的资源序号
	 */
	void updateTree(String roleId, String resourceSequenceNumbers);
	
	List<RoleResourceRelation> getByRoleId(String roleId);

}
