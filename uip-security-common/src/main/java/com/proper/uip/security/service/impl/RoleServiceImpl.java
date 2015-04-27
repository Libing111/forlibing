package com.proper.uip.security.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.security.entity.RaResourceSetRelation;
import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.ResourceChildNode;
import com.proper.uip.api.security.entity.ResourceSet;
import com.proper.uip.api.security.entity.ResourceTreeNode;
import com.proper.uip.api.security.entity.Role;
import com.proper.uip.api.security.entity.RoleResourceRelation;
import com.proper.uip.api.security.entity.RoleRuleRelation;
import com.proper.uip.api.security.entity.RoleUserRelation;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.service.RoleService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.dao.RaResourceSetRelationDao;
import com.proper.uip.security.dao.ResourceSetDao;
import com.proper.uip.security.dao.ResourcesDao;
import com.proper.uip.security.dao.RoleResourceRelationDao;
import com.proper.uip.security.dao.RoleRuleRelationDao;
import com.proper.uip.security.dao.RoleUserRelationDao;
import com.proper.uip.security.dao.RolesDao;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RolesDao rolesDao;
	
	@Autowired
	private ResourcesDao resourcesDao;
	
	@Autowired
	private RoleResourceRelationDao authorityDao;
	
	@Autowired
	private RaResourceSetRelationDao raResourceSetRelationDao;
	
	@Autowired
	private ResourceSetDao resourceSetDao;
	
	@Autowired
	private RoleUserRelationDao roleUserRelationDao;
	
	@Autowired
	private RoleRuleRelationDao roleRuleRelationDao;
	
	//private String systemCode = "somp";
	
	@Override
	public Page<Role> findAllRole(PageConfig config, String name, String categoryName, String raId, String raName) {
		Page<Role> roleList = rolesDao.findRolePage(config, name, categoryName,raId, raName);
		return roleList;
	}


	@Override
	public void saveRole(Role roleEntity) {
		rolesDao.save(roleEntity);
	}

	@Override
	public Role getRoleById(String id) {
		return rolesDao.get(id);
	}

	@Override
	public void deleteRoleById(String id) {
		
		RoleRuleRelation roleRuleRelationOld =  roleRuleRelationDao.findUniqueBy("roleId", id);
		if(roleRuleRelationOld != null){
			roleRuleRelationDao.delete(roleRuleRelationOld);
		}
		
		rolesDao.delete(id);
	}


	@Override
	public Role getByName(String name) {
		String sql = "select c from Role c where c.name = ?";
		Role uName = rolesDao.findUnique(sql, name);
		return uName;
	}


//	@Override
//	public List<TreeNode> buildAuthorityTree(String roleId) {
//		List<RoleResourceRelation> authorityList = authorityDao.findBy("roleId", roleId);
//		Map<String, RoleResourceRelation> authorityMap = new HashMap<String, RoleResourceRelation>();
//		
//		for(RoleResourceRelation authority : authorityList){
//			authorityMap.put(authority.getResourceId(), authority);
//		}
//		
//		List<Resource> subsystemResourceList = resourcesDao.findBy("parent", systemCode);
//		
//		List<TreeNode> subsystemNodeList = new ArrayList<TreeNode>();
//		TreeNode subsystemNode = null;
//		boolean checked = false;
//		for(Resource subsystemResource : subsystemResourceList){
//			checked = false;
//			if(authorityMap.containsKey(subsystemResource.getId())){
//				checked = true;
//			}
//			subsystemNode = new TreeNode(roleId, subsystemResource, checked);
//			subsystemNodeList.add(subsystemNode);
//			
//			this.buildSubTree(subsystemNode, authorityMap);
//		}
//		
//		
//		return subsystemNodeList;
//	}
//	
//	private void buildSubTree(TreeNode node, Map<String, RoleResourceRelation> authorityMap) {
//		List<Resource> resourceList = resourcesDao.findBy("parent", node.getResourceCode());
//		
//		List<TreeNode> children = new ArrayList<TreeNode>();
//		TreeNode childNode = null;
//		boolean checked = false;
//		for(Resource resource : resourceList){
//			checked = false;
//			if(authorityMap.containsKey(resource.getId())){
//				checked = true;
//			}
//			childNode = new TreeNode(node.getRoleId(), resource, checked);
//			children.add(childNode);
//			
//			this.buildSubTree(childNode, authorityMap);
//		}
//		
//		node.setChildren(children);
//		
//	}


	@Override
	public String getName(String roleId) {
		String sql = "select c.name from Role c where c.id = ?";
		String name = rolesDao.findUnique(sql, roleId);
		return name;
	}


	@Override
	public Role getCode(String code) {
		Criteria criteria = this.rolesDao.getSession().createCriteria(Role.class);
		
		List<String> categoryCodeList = new ArrayList<String>();
		
		categoryCodeList.add("role.category.built-in");
		categoryCodeList.add("role.category.department.personnel");
		categoryCodeList.add("role.category.department");
		categoryCodeList.add("role.category.system.personnel");
		categoryCodeList.add("role.category.rule");

		if(code != null && code.trim().isEmpty() == false){
			criteria.add(Restrictions.eq("code", code));
		}
		criteria.add(Restrictions.not(Restrictions.in("categoryCode", categoryCodeList)));
		criteria.addOrder(Order.desc("createTime"));
		Role role = (Role) criteria.uniqueResult();
		return role;
	}


	@Override
	public Role getNameUnique(String name, String userRa) {
		String sql = "select c from Role c where c.name = ? and c.raId=?";
		Role role = resourcesDao.findUnique(sql, name,userRa);
		return role;
	}

	@Override
	public List<ResourceTreeNode> buildResourceTree(String roleId, boolean chkDisabled) {
		List<ResourceTreeNode> resourceTreeNodeList = new ArrayList<ResourceTreeNode>();
		
		
		List<RoleResourceRelation> authorityList = authorityDao.findBy("roleId", roleId);
		Map<String, RoleResourceRelation> authorityMap = new HashMap<String, RoleResourceRelation>();
		
		for(RoleResourceRelation authority : authorityList){
			authorityMap.put(authority.getResourceId(), authority);
		}
		
		if(roleId == null || roleId.trim().equals("") == true){
			return resourceTreeNodeList;
		}
		
		Role role = rolesDao.findUniqueBy("id", roleId);
		
		if(role == null){
			return resourceTreeNodeList;
		}
			
		List<Resource> resourceList = getResouceListByRa(role.getRaId());
		
		Map<String, Resource> resouceMap = new HashMap<String, Resource>();
		
		for(Resource resource : resourceList) {
			resouceMap.put(resource.getCode(), resource);
		}
		
		ResourceTreeNode resourceTreeNode = null;
		Resource parentResource = null;
		boolean checked = false;
		for(Resource resource : resourceList) {
			if(resouceMap.containsKey(resource.getCode()) == false){
				continue;
			}
			checked = false;
			if(authorityMap.containsKey(resource.getId())){
				checked = true;
			}
			
			parentResource = resouceMap.get(resource.getParent());
			resourceTreeNode = new ResourceTreeNode(resource, parentResource,checked,chkDisabled);
			
			resourceTreeNodeList.add(resourceTreeNode);
		}
		
		return resourceTreeNodeList;
	}


	private List<Resource> getResouceListByRa(String raId) {
		List<Resource> resourceList = new ArrayList<Resource>();
		if(raId == null || raId.trim().equals("") == true){
			resourceList = resourcesDao.findBy("anonymously", false);
			return resourceList;
		}
		List<RaResourceSetRelation> raResourceSetRelationList = raResourceSetRelationDao.findBy("raId", raId);
		Set<String> resouceSetIds = new HashSet<String>();
		for(RaResourceSetRelation relation : raResourceSetRelationList){
			resouceSetIds.add(relation.getResourcesetId());
		}
		
		if(resouceSetIds.isEmpty()){
			return resourceList;
		}
		Map<String,Object> map = new HashMap<String,Object>(); 
		map.put("resouceSetIds", resouceSetIds);
		//只取注册机构拥有的资源
		resourceList = this.resourcesDao.find("select r from Resource r, ResourceResourceSetRelation rrs where r.id=rrs.resourceId and r.anonymously = false and rrs.resourcesetId in(:resouceSetIds)", map);
		
		return resourceList;
	}


	@Override
	public Page<Role> findAll(PageConfig pageConfig, String name, String categoryName,String raName) {
		Page<Role> roleList = rolesDao.findAllPage(pageConfig, name, categoryName,raName);
		return roleList;
	}


	@Override
	public List<Role> getByRaId(String raId) {
		List<Role> roleList = this.rolesDao.findBy("raId", raId);
		
		return roleList;
	}


	@Override
	public List<Role> getByCategoryId(String roleCategoryId) {
		List<Role> roleList = rolesDao.findBy("categoryId", roleCategoryId);
		return roleList;
	}
	@Override
	public List<Resource> getAllResourceByRa(User user,String moc) {
		if(user.getRaId() == null){
//			//查询这个人所有角色对应的资源
//			List<RoleUserRelation> roleUserRelationList = roleUserRelationDao.findBy("userId", user.getId());
//				
//			List<String> roleIds = new ArrayList<String>();
//			
//			for (RoleUserRelation roleUserRelation : roleUserRelationList) {
//				roleIds.add(roleUserRelation.getRoleId());
//			}
//			String hqlRoleResource = "select distinct r from Resource r, RoleResourceRelation rrs where r.id=rrs.resourceId and rrs.roleId in (:roleIds) and r.moc=:moc";
//			
//			Map<String, Object> roleResourceValues = new HashMap<String, Object>();
//			roleResourceValues.put("roleIds", roleIds);
//			roleResourceValues.put("moc", moc);
//			
//			//本人所有角色对应的资源
			String hqlRoleResource = "select distinct r from Resource r where r.anonymously=:anonymously and r.moc=:moc";
			Map<String, Object> roleResourceValues = new HashMap<String, Object>();
			roleResourceValues.put("anonymously", false);
			roleResourceValues.put("moc", moc);
			List<Resource> roleResourceList = this.resourcesDao.find(hqlRoleResource, roleResourceValues);
			
			return roleResourceList;
		}
		//查询这个人所在注册机构的所有权限
		List<ResourceSet> resourceSets = resourceSetDao
				.find("select distinct rs from ResourceSet rs, RaResourceSetRelation rrs where rs.id=rrs.resourcesetId and rrs.raId=?",
						user.getRaId());
		
		List<String> resourceSetIds = new ArrayList<String>();
		for(ResourceSet resourceSet : resourceSets){
			resourceSetIds.add(resourceSet.getId());
		}
		String hql = "select distinct r from Resource r, ResourceResourceSetRelation rrs where r.id=rrs.resourceId and rrs.resourcesetId in (:resourceSetIds) and r.moc=:moc";
		
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("resourceSetIds", resourceSetIds);
		values.put("moc", moc);
		
		//注册机构所有资源
		List<Resource> resourceList = this.resourcesDao.find(hql, values);
		
		return resourceList;
	}
	
	@Override
	public List<ResourceChildNode> findChildResource(User user, String parent) {
		if(user.getRaId() == null){
			//查询这个人所有角色对应的资源
//			List<RoleUserRelation> roleUserRelationList = roleUserRelationDao.findBy("userId", user.getId());
//				
//			List<String> roleIds = new ArrayList<String>();
//			
//			for (RoleUserRelation roleUserRelation : roleUserRelationList) {
//				roleIds.add(roleUserRelation.getRoleId());
//			}
//			String hqlRoleResource = "select distinct r from Resource r, RoleResourceRelation rrs where r.id=rrs.resourceId and rrs.roleId in (:roleIds) and r.parent=:parent";
//			
//			Map<String, Object> roleResourceValues = new HashMap<String, Object>();
//			roleResourceValues.put("roleIds", roleIds);
//			roleResourceValues.put("parent", parent);
//			
//			//本人所有角色对应的资源
//			List<Resource> roleResourceList = this.resourcesDao.find(hqlRoleResource, roleResourceValues);
			String hqlRoleResource = "select distinct r from Resource r where r.anonymously=:anonymously and r.parent=:parent";
			Map<String, Object> roleResourceValues = new HashMap<String, Object>();
			roleResourceValues.put("anonymously", false);
			roleResourceValues.put("parent", parent);
			List<Resource> roleResourceList = this.resourcesDao.find(hqlRoleResource, roleResourceValues);
			
			
			List<ResourceChildNode> resourceChildNodeList = new ArrayList<ResourceChildNode>();
			
			ResourceChildNode resourceChildNode = null;
			for (Resource resource : roleResourceList) {
				resourceChildNode = new ResourceChildNode(resource);
				resourceChildNodeList.add(resourceChildNode);
			}
			return resourceChildNodeList;
		}
		//查询这个人所在注册机构的所有权限
		List<ResourceSet> resourceSets = resourceSetDao
				.find("select distinct rs from ResourceSet rs, RaResourceSetRelation rrs where rs.id=rrs.resourcesetId and rrs.raId=?",
						user.getRaId());
		
		List<String> resourceSetIds = new ArrayList<String>();
		for(ResourceSet resourceSet : resourceSets){
			resourceSetIds.add(resourceSet.getId());
		}
		
		//List<Resource> resourceList = resourcesDao.findBy("parent", parentChildCode);
		
		String hql = "select distinct r from Resource r, ResourceResourceSetRelation rrs where r.id=rrs.resourceId and rrs.resourcesetId in (:resourceSetIds) and r.parent=:parent";
		
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("resourceSetIds", resourceSetIds);
		values.put("parent", parent);
		//注册机构所有资源
		List<Resource> resourceList = this.resourcesDao.find(hql, values);
		
		List<ResourceChildNode> resourceChildNodeList = new ArrayList<ResourceChildNode>();
		
		ResourceChildNode resourceChildNode = null;
		for (Resource resource : resourceList) {
			resourceChildNode = new ResourceChildNode(resource);
			resourceChildNodeList.add(resourceChildNode);
		}
		return resourceChildNodeList;
	}
	
	@Override
	public List<Resource> findResource(User user, String parent) {
		if(user.getRaId() == null){
			//查询这个人所有角色对应的资源
//			List<RoleUserRelation> roleUserRelationList = roleUserRelationDao.findBy("userId", user.getId());
//				
//			List<String> roleIds = new ArrayList<String>();
//			
//			for (RoleUserRelation roleUserRelation : roleUserRelationList) {
//				roleIds.add(roleUserRelation.getRoleId());
//			}
//			String hqlRoleResource = "select distinct r from Resource r, RoleResourceRelation rrs where r.id=rrs.resourceId and rrs.roleId in (:roleIds) and r.parent=:parent";
//			
//			Map<String, Object> roleResourceValues = new HashMap<String, Object>();
//			roleResourceValues.put("roleIds", roleIds);
//			roleResourceValues.put("parent", parent);
//			
//			//本人所有角色对应的资源
//			List<Resource> roleResourceList = this.resourcesDao.find(hqlRoleResource, roleResourceValues);
			String hqlRoleResource = "select distinct r from Resource r where r.anonymously=:anonymously and r.parent=:parent";
			Map<String, Object> roleResourceValues = new HashMap<String, Object>();
			roleResourceValues.put("anonymously", false);
			roleResourceValues.put("parent", parent);
			List<Resource> roleResourceList = this.resourcesDao.find(hqlRoleResource, roleResourceValues);
			
			return roleResourceList;
		}
		//查询这个人所在注册机构的所有权限
		List<ResourceSet> resourceSets = resourceSetDao
				.find("select distinct rs from ResourceSet rs, RaResourceSetRelation rrs where rs.id=rrs.resourcesetId and rrs.raId=?",
						user.getRaId());
		
		List<String> resourceSetIds = new ArrayList<String>();
		for(ResourceSet resourceSet : resourceSets){
			resourceSetIds.add(resourceSet.getId());
		}
		
		//List<Resource> resourceList = resourcesDao.findBy("parent", parentChildCode);
		
		String hql = "select distinct r from Resource r, ResourceResourceSetRelation rrs where r.id=rrs.resourceId and rrs.resourcesetId in (:resourceSetIds) and r.parent=:parent";
		
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("resourceSetIds", resourceSetIds);
		values.put("parent", parent);
		//注册机构所有资源
		List<Resource> resourceList = this.resourcesDao.find(hql, values);
		return resourceList;
	}
	
	@Override
	public Map<String, String> getFilterResource(String roleId) {
		
		//key:资源父亲code value: 所有孩子的Id
		Map<String, String> filterResourceMap = new HashMap<String, String>();
		
		List<RoleResourceRelation> authorityList = authorityDao.findBy("roleId", roleId);
		
		List<String> resourceIdList = new ArrayList<String>();
		for(RoleResourceRelation authority : authorityList){
			resourceIdList.add(authority.getResourceId());
		}
		
		if(roleId == null || roleId.trim().equals("") == true){
			return filterResourceMap;
		}
		
		Role role = rolesDao.findUniqueBy("id", roleId);
		
		if(role == null){
			return filterResourceMap;
		}
		
		Criteria criteria = this.resourcesDao.getSession().createCriteria(Resource.class);
		criteria.add(Restrictions.in("id", resourceIdList));
		
		if(resourceIdList.isEmpty()){
			return filterResourceMap;
		}
		@SuppressWarnings("unchecked")
		List<Resource> resourceList = criteria.list();
		
		String childResourceId = "";
		
		for (Resource resource : resourceList) {
			if(resource.getMoc().equals("module") == false){
				continue;
			}
			if(filterResourceMap.containsKey(resource.getParent()) == false){
				filterResourceMap.put(resource.getParent(), resource.getCode());
				continue;
			}
			childResourceId = filterResourceMap.get(resource.getParent());
			
			childResourceId = childResourceId + "," + resource.getCode();
			
			filterResourceMap.put(resource.getParent(), childResourceId);
			
		}
		
		
		return filterResourceMap;
	}


	@Override
	public Map<String, String> getChildResource(HttpServletRequest request,User user, String pagingId) {
		Map<String, String> allResourceMap = new HashMap<String, String>();
		StringBuffer childResourceId = new StringBuffer();
		if(user.getRaId() == null){
			//查询这个人所有角色对应的资源
//			List<RoleUserRelation> roleUserRelationList = roleUserRelationDao.findBy("userId", user.getId());
//				
//			List<String> roleIds = new ArrayList<String>();
//			
//			for (RoleUserRelation roleUserRelation : roleUserRelationList) {
//				roleIds.add(roleUserRelation.getRoleId());
//			}
//			String hqlRoleResource = "select distinct r from Resource r, RoleResourceRelation rrs where r.id=rrs.resourceId and rrs.roleId in (:roleIds) and r.parent=:parent";
//			
//			Map<String, Object> roleResourceValues = new HashMap<String, Object>();
//			roleResourceValues.put("roleIds", roleIds);
//			roleResourceValues.put("parent", pagingId);
//			
//			//本人所有角色对应的资源
//			List<Resource> roleResourceList = this.resourcesDao.find(hqlRoleResource, roleResourceValues);
			String hqlRoleResource = "select distinct r from Resource r where r.anonymously=:anonymously and r.parent=:parent";
			Map<String, Object> roleResourceValues = new HashMap<String, Object>();
			roleResourceValues.put("anonymously", false);
			roleResourceValues.put("parent", pagingId);
			List<Resource> roleResourceList = this.resourcesDao.find(hqlRoleResource, roleResourceValues);
			for (Resource resource : roleResourceList) {
				childResourceId.append(",").append(resource.getCode());
			}
			allResourceMap.put(pagingId, childResourceId.toString());
			
			return allResourceMap;
		}
		//查询这个人所在注册机构的所有权限
		List<ResourceSet> resourceSets = resourceSetDao
				.find("select distinct rs from ResourceSet rs, RaResourceSetRelation rrs where rs.id=rrs.resourcesetId and rrs.raId=?",
						user.getRaId());
		
		List<String> resourceSetIds = new ArrayList<String>();
		for(ResourceSet resourceSet : resourceSets){
			resourceSetIds.add(resourceSet.getId());
		}
		
		//List<Resource> resourceList = resourcesDao.findBy("parent", parentChildCode);
		
		String hql = "select distinct r from Resource r, ResourceResourceSetRelation rrs where r.id=rrs.resourceId and rrs.resourcesetId in (:resourceSetIds) and r.parent=:parent";
		
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("resourceSetIds", resourceSetIds);
		values.put("parent", pagingId);
		//注册机构所有资源
		List<Resource> resourceList = this.resourcesDao.find(hql, values);
		for (Resource resource : resourceList) {
			childResourceId.append(",").append(resource.getCode());
		}
		allResourceMap.put(pagingId, childResourceId.toString());
		
		
		return allResourceMap;
	}


	@Override
	public void saveRole(Role roleEntity, String ruleId, String principalIds,String principalValues) {
		Role role = rolesDao.save(roleEntity);
		
		RoleRuleRelation roleRuleRelationOld =  roleRuleRelationDao.findUniqueBy("roleId", role.getId());
		if((ruleId.equals("") || ruleId == null) && roleRuleRelationOld != null ){
			roleRuleRelationDao.delete(roleRuleRelationOld);
			return;
		}
		if(roleRuleRelationOld != null){
			roleRuleRelationDao.delete(roleRuleRelationOld);
		}
		RoleRuleRelation roleRuleRelation = new RoleRuleRelation();
		roleRuleRelation.setId(null);
		roleRuleRelation.setRoleId(role.getId());
		roleRuleRelation.setRuleId(ruleId);
		roleRuleRelation.setRuleKey(principalIds);
		roleRuleRelation.setRuleValue(principalValues);
		roleRuleRelationDao.save(roleRuleRelation);
	}


	@Override
	public List<Role> getByCode(String code) {
		List<Role> roleList = rolesDao.findBy("code", code);
		return roleList;
	}


	@Override
	public List<ResourceTreeNode> buildResourceTree(User user) {
		List<ResourceTreeNode> resourceTreeNodeList = new ArrayList<ResourceTreeNode>();
		if(user.getRaId() == null){

			String hqlRoleResource = "select distinct r from Resource r where r.anonymously=:anonymously";
			Map<String, Object> roleResourceValues = new HashMap<String, Object>();
			roleResourceValues.put("anonymously", false);
			List<Resource> resourceList = this.resourcesDao.find(hqlRoleResource, roleResourceValues);
			
			
			Map<String, Resource> resouceMap = new HashMap<String, Resource>();
			
			for(Resource resource : resourceList) {
				resouceMap.put(resource.getCode(), resource);
			}
			
			ResourceTreeNode resourceTreeNode = null;
			Resource parentResource = null;
			boolean checked = false;
			for(Resource resource : resourceList) {
				if(resouceMap.containsKey(resource.getCode()) == false){
					continue;
				}
				
				parentResource = resouceMap.get(resource.getParent());
				resourceTreeNode = new ResourceTreeNode(resource, parentResource,checked,false);
				
				resourceTreeNodeList.add(resourceTreeNode);
			}
			
			return resourceTreeNodeList;
		}
		//查询这个人所在注册机构的所有权限
		List<ResourceSet> resourceSets = resourceSetDao
				.find("select distinct rs from ResourceSet rs, RaResourceSetRelation rrs where rs.id=rrs.resourcesetId and rrs.raId=?",
						user.getRaId());
		
		List<String> resourceSetIds = new ArrayList<String>();
		for(ResourceSet resourceSet : resourceSets){
			resourceSetIds.add(resourceSet.getId());
		}
		
		String hql = "select distinct r from Resource r, ResourceResourceSetRelation rrs where r.id=rrs.resourceId and rrs.resourcesetId in (:resourceSetIds)";
		
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("resourceSetIds", resourceSetIds);
		//注册机构所有资源
		List<Resource> resourceList = this.resourcesDao.find(hql, values);
		
		Map<String, Resource> resouceMap = new HashMap<String, Resource>();
		
		for(Resource resource : resourceList) {
			resouceMap.put(resource.getCode(), resource);
		}
		
		ResourceTreeNode resourceTreeNode = null;
		Resource parentResource = null;
		boolean checked = false;
		for(Resource resource : resourceList) {
			if(resouceMap.containsKey(resource.getCode()) == false){
				continue;
			}
			
			parentResource = resouceMap.get(resource.getParent());
			resourceTreeNode = new ResourceTreeNode(resource, parentResource,checked,false);
			
			resourceTreeNodeList.add(resourceTreeNode);
		}
		
		return resourceTreeNodeList;
	}


	@Override
	public List<ResourceTreeNode> buildResourceTree(String roleId) {
		List<ResourceTreeNode> resourceTreeNodeList = new ArrayList<ResourceTreeNode>();
		
		List<RoleResourceRelation> authorityList = authorityDao.findBy("roleId", roleId);
		
		List<String> resourceIdList = new ArrayList<String>();
		for(RoleResourceRelation authority : authorityList){
			resourceIdList.add(authority.getResourceId());
		}
		
		if(roleId == null || roleId.trim().equals("") == true){
			return resourceTreeNodeList;
		}
		
		Role role = rolesDao.findUniqueBy("id", roleId);
		
		if(role == null){
			return resourceTreeNodeList;
		}
		
		Criteria criteria = this.resourcesDao.getSession().createCriteria(Resource.class);
		criteria.add(Restrictions.in("id", resourceIdList));
		
		if(resourceIdList.isEmpty()){
			return resourceTreeNodeList;
		}
		@SuppressWarnings("unchecked")
		List<Resource> resourceList = criteria.list();
		
		Map<String, Resource> resouceMap = new HashMap<String, Resource>();
		
		for(Resource resource : resourceList) {
			resouceMap.put(resource.getCode(), resource);
		}
		
		ResourceTreeNode resourceTreeNode = null;
		Resource parentResource = null;
		boolean checked = false;
		for(Resource resource : resourceList) {
			if(resouceMap.containsKey(resource.getCode()) == false){
				continue;
			}
			parentResource = resouceMap.get(resource.getParent());
			resourceTreeNode = new ResourceTreeNode(resource, parentResource,checked,false);
			
			resourceTreeNodeList.add(resourceTreeNode);
		}
		
		return resourceTreeNodeList;
	}
}
