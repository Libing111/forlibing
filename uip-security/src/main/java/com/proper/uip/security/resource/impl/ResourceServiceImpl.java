package com.proper.uip.security.resource.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.ResourceResourceSetRelation;
import com.proper.uip.api.security.entity.ResourceTreeNode;
import com.proper.uip.api.security.entity.RoleResourceRelation;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.dao.ResourceResourceSetRelationDao;
import com.proper.uip.security.dao.ResourcesDao;
import com.proper.uip.security.dao.RoleResourceRelationDao;
import com.proper.uip.security.resource.service.ResourceService;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class ResourceServiceImpl implements ResourceService {
	@Autowired
	private ResourcesDao resourcesDao;
	
	@Autowired
	private ResourceResourceSetRelationDao resourceResourceSetRelationDao;
	
	@Autowired
	private RoleResourceRelationDao roleResourceRelationDao;
	
	@Override
	public Page<Resource> findAllResource(PageConfig config) {
		String sql = "select c from Resource c where c.moc = 'subsystem' order by sequenceNumber asc";
		Page<Resource> resourceList = resourcesDao.findPage(config, sql);
		return resourceList;
	}

	@Override
	public Resource saveResource(Resource resourceEntity) {
		Resource resource = resourcesDao.save(resourceEntity);
		if(resource.isAnonymously() == false){
			return resource;
		}
		String sql = "select c from ResourceResourceSetRelation c where c.resourceId = ?";
		List<ResourceResourceSetRelation>  rrs = resourceResourceSetRelationDao.find(sql, resource.getId());
		resourceResourceSetRelationDao.delete(rrs);
		String hql = "select c from RoleResourceRelation c where c.resourceId = ?";
		List<RoleResourceRelation> rr = roleResourceRelationDao.find(hql, resource.getId());
		roleResourceRelationDao.delete(rr);
		return resource;
	}

	@Override
	public Resource getResourceById(String id) {
		return resourcesDao.get(id);
	}

	@Override
	public void deleteResourceById(String id) {
		Resource resource = resourcesDao.findUniqueBy("id", id);
		List<Resource> resourceList = resourcesDao.findBy("parent", resource.getCode());
		if(resourceList.isEmpty() == false){
			resourcesDao.delete(resourceList);	
		}
		resourcesDao.delete(id);
	}

	@Override
	public List<Resource> getall() {
		return resourcesDao.getAll();
	}

	@Override
	public Resource getUserByName(String name) {
		String sql = "select c from Resource c where c.name = ?";
		Resource rName = resourcesDao.findUnique(sql, name);
		return rName;
	}

	@Override
	public String getName(String resourceId) {
		String sql = "select c.name from Resource c where c.id = ?";
		String name = resourcesDao.findUnique(sql, resourceId);
		return name;
	}

	@Override
	public List<ResourceTreeNode> buildResourceTree() {
		List<Resource> resourceList = resourcesDao.getAll();
		
		Map<String, Resource> resouceMap = new HashMap<String, Resource>();
		
		for(Resource resource : resourceList) {
			resouceMap.put(resource.getCode(), resource);
		}
		
		List<ResourceTreeNode> resourceTreeNodeList = new ArrayList<ResourceTreeNode>();
		
		ResourceTreeNode resourceTreeNode = null;
		Resource parentResource = null;
		for(Resource resource : resourceList) {
			parentResource = resouceMap.get(resource.getParent());
			resourceTreeNode = new ResourceTreeNode(resource, parentResource, false,true);
			
			resourceTreeNodeList.add(resourceTreeNode);
		}
		
		return resourceTreeNodeList;
	}
	

	@Override
	public Resource getResourceByCode(String code) {
		Resource resource = resourcesDao.findUniqueBy("code", code); 
		return resource;
	}

	@Override
	public Page<Resource> getResourcesOfModel(PageConfig pageConfig,String parentId, String name, String categoryName) {
		Resource resource = resourcesDao.findUniqueBy("code", parentId); 
		
		Criteria criteria = createCriteria(resource.getCode(), name, categoryName);
		

		Page<Resource> organizationPage = new Page<Resource>(pageConfig);

		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		organizationPage.setTotal(totalCount);
		
		criteria = createCriteria(resource.getCode(), name,categoryName);


		int fromIndex = organizationPage.getOffset();
		if(fromIndex < 0){
			System.out.println("Error! fromIndex = " + fromIndex);
			System.out.println("Error! Class=" + this.getClass().getName());
			System.out.println("Error! fromIndex = " + fromIndex);
			fromIndex = 0;
		}
		criteria.setFirstResult(fromIndex);
		criteria.setMaxResults(organizationPage.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<Resource> modelList = criteria.list();
		organizationPage.setRows(modelList);
	
		return organizationPage;
	}

	private Criteria createCriteria(String parent, String name, String categoryName) {
		Criteria criteria = resourcesDao.getSession().createCriteria(Resource.class);
		
		if(parent != null && parent.trim().equals("") == false){
			criteria.add(Restrictions.eq("parent",parent));
		}
		String searchTextMode;
		if(name != null && name.trim().isEmpty() == false){
			searchTextMode = "%" + name + "%";
			criteria.add(Restrictions.like("name",searchTextMode));
		}
		if(categoryName != null && categoryName.trim().isEmpty() == false){
			searchTextMode = "%" + categoryName + "%";
			criteria.add(Restrictions.like("categoryName",searchTextMode));
		}
		criteria.addOrder(Order.asc("sequenceNumber"));
		
		return criteria;
	}

	@Override
	public Resource getResourceByParent(String parent) {
		Resource resource =  resourcesDao.findUniqueBy("code", parent);
		return resource;
	}

	@Override
	public String getChildResourceMocByMoc(String moc) {
		if(moc.equals("system")){
			return "subsystem";
		}
		
		if(moc.equals("subsystem")){
			return "module";
		}
		
		if(moc.equals("module")){
			return "operation";
		}
		
		return "";
	}

	@Override
	public Resource getResource(int sequenceNumber, String parent) {
		String sql = "select c from Resource c where c.sequenceNumber = ? and c.parent = ?";
		List<Resource> resourceList = resourcesDao.find(sql, sequenceNumber,parent);
		if(resourceList.isEmpty() == true){
			return null;
		}
		return resourceList.get(0);
	}

	@Override
	public Resource getCode(String code) {
		String sql = "select c from Resource c where c.code = ? ";
		List<Resource> resourceList = resourcesDao.find(sql, code);
		if(resourceList.isEmpty() == true){
			return null;
		}
		return resourceList.get(0);
	}

	@Override
	public Resource getByName(String name) {
		String sql = "select c from Resource c where c.name = ? ";
		List<Resource> resourceList = resourcesDao.find(sql, name);
		if(resourceList.isEmpty() == true){
			return null;
		}
		return resourceList.get(0);
	}

	@Override
	public List<Resource> getByCategoryId(String resourceCategoryId) {
		List<Resource> resourceList = resourcesDao.findBy("categoryId", resourceCategoryId);
		return resourceList;
	}

	

}
