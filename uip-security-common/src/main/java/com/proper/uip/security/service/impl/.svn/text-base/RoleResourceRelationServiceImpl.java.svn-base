package com.proper.uip.security.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.Role;
import com.proper.uip.api.security.entity.RoleResourceRelation;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.service.RoleResourceRelationService;
import com.proper.uip.security.dao.ResourcesDao;
import com.proper.uip.security.dao.RoleResourceRelationDao;
import com.proper.uip.security.dao.RolesDao;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class RoleResourceRelationServiceImpl implements
		RoleResourceRelationService {

	@Autowired
	private RolesDao rolesDao;
	
	@Autowired
	private ResourcesDao resourcesDao;

	@Autowired
	private RoleResourceRelationDao roleResourceRelationDao;
	
//	@Override
//	public void saveTree(String roleId, String resourceId) {
//		
//		String roleName = rolesDao.findUniqueBy("id", roleId).getName();
//		String resourceName = resourcesDao.findUniqueBy("id",resourceId).getName();
//		
//		RoleResourceRelation roleResourceRelationEntity = new RoleResourceRelation();
//		roleResourceRelationEntity.setId(null);
//		roleResourceRelationEntity.setRoleId(roleId);
//		roleResourceRelationEntity.setResourceId(resourceId);
//		roleResourceRelationEntity.setRoleName(roleName);
//		roleResourceRelationEntity.setResourceName(resourceName);
//		
//		RoleResourceRelation RoleResource = this.getRoleResource(roleId, resourceId);
//		if (RoleResource == null) {
//			roleResourceRelationDao.save(roleResourceRelationEntity);
//		}
//		
//		
//	}

	private RoleResourceRelation getRoleResource(String roleId,
			String resourceId) {
		String sql = "select c from RoleResourceRelation c where c.roleId = ? and c.resourceId = ?";
		RoleResourceRelation roleResourceRelation = roleResourceRelationDao.findUnique(sql, roleId, resourceId);
		return roleResourceRelation;
	}

	@Override
	public void deleteTree(String roleId, String resourceId) {
		RoleResourceRelation RoleResource = this.getRoleResource(roleId, resourceId);
		if (RoleResource != null) {
			roleResourceRelationDao.delete(RoleResource);
		}
	}

	
	@Override
	public void updateTree(String roleId, String childResourceIds) {
		Role role = rolesDao.findUniqueBy("id", roleId);
		//List<String> childResourceIdList = Arrays.asList(childResourceIds.split(","));
		JSONArray jsonArray = JSONArray.fromObject(childResourceIds);   
		Object[] childResourceIdObject = jsonArray.toArray();   
		List<String> childResourceIdList = new ArrayList<String>();
		for (Object string : childResourceIdObject) {
			childResourceIdList.add((String) string);
		}
		//所有模块（不包含子系统）
		Criteria criteria = this.resourcesDao.getSession().createCriteria(Resource.class);
		criteria.add(Restrictions.in("code", childResourceIdList));
		if(childResourceIdList.isEmpty()){
			List<RoleResourceRelation> oldRoleResourceRelationList = roleResourceRelationDao.findBy("roleId", role.getId());
			if(oldRoleResourceRelationList != null && oldRoleResourceRelationList.isEmpty() == false){
				roleResourceRelationDao.delete(oldRoleResourceRelationList);
			}
			return;
		}
		@SuppressWarnings("unchecked")
		List<Resource> resourceList = criteria.list();
		
		//根据模块查询子系统
		Set<String> parentCodeSet = new HashSet<String>();
		
		for (Resource resource : resourceList) {
			parentCodeSet.add(resource.getParent());
		}
		criteria = this.resourcesDao.getSession().createCriteria(Resource.class);
		criteria.add(Restrictions.in("code", parentCodeSet));
			
		@SuppressWarnings("unchecked")
		List<Resource> parentResourceList = criteria.list();
		
		
		
		List<RoleResourceRelation> roleResourceRelationList = new ArrayList<RoleResourceRelation>();
		RoleResourceRelation roleResource = null;
		resourceList.addAll(parentResourceList);
		
		for (Resource resource : resourceList) {
			roleResource = new RoleResourceRelation(resource, role);
			
			roleResourceRelationList.add(roleResource);
		}
		
		List<RoleResourceRelation> oldRoleResourceRelationList = roleResourceRelationDao.findBy("roleId", roleId);
		if(oldRoleResourceRelationList != null && oldRoleResourceRelationList.isEmpty() == false){
			roleResourceRelationDao.delete(oldRoleResourceRelationList);
		}
		if(roleResourceRelationList != null && roleResourceRelationList.isEmpty() == false){
			roleResourceRelationDao.save(roleResourceRelationList);
		}
		
	}

	@Override
	public List<RoleResourceRelation> getByRoleId(String roleId) {
		List<RoleResourceRelation> resourceList = roleResourceRelationDao.findBy("roleId", roleId);
		return resourceList;
	}

}
