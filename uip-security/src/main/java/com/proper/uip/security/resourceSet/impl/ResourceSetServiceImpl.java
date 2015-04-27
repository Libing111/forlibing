package com.proper.uip.security.resourceSet.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.ResourceResourceSetRelation;
import com.proper.uip.api.security.entity.ResourceSet;
import com.proper.uip.api.security.entity.ResourceTreeNode;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.dao.ResourceResourceSetRelationDao;
import com.proper.uip.security.dao.ResourceSetDao;
import com.proper.uip.security.dao.ResourcesDao;
import com.proper.uip.security.resourceSet.service.ResourceSetService;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class ResourceSetServiceImpl implements ResourceSetService {
	
	@Autowired
	private ResourceSetDao resourceSetDao;
	
	@Autowired
	private ResourceResourceSetRelationDao authorityDao;
	
	@Autowired
	private ResourcesDao resourcesDao;
	
	
	@Override
	public Page<ResourceSet> findAllResourceSet(PageConfig pageConfig,
			String name) {
		Page<ResourceSet> resourceSetList = resourceSetDao.findResourceSetPage(pageConfig,name);
		return resourceSetList;
	}

	@Override
	public void saveResourceSet(ResourceSet resourceSetEntity) {
		resourceSetDao.save(resourceSetEntity);
	}

	@Override
	public List<ResourceTreeNode> buildResourceTree(String resourceSetId,
			boolean chkDisabled) {
		List<ResourceResourceSetRelation> authorityList = authorityDao.findBy("resourcesetId", resourceSetId);
		Map<String, ResourceResourceSetRelation> authorityMap = new HashMap<String, ResourceResourceSetRelation>();
		
		for(ResourceResourceSetRelation authority : authorityList){
			authorityMap.put(authority.getResourceId(), authority);
		}
		
		List<Resource> resourceList = resourcesDao.findBy("anonymously", false);
		Map<String, Resource> resouceMap = new HashMap<String, Resource>();
		
		for(Resource resource : resourceList) {
			resouceMap.put(resource.getCode(), resource);
		}
		
		List<ResourceTreeNode> resourceTreeNodeList = new ArrayList<ResourceTreeNode>();
		
		ResourceTreeNode resourceTreeNode = null;
		Resource parentResource = null;
		boolean checked = false;
		for(Resource resource : resourceList) {
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

	@Override
	public void deleteResourceSetById(String id) {
		resourceSetDao.delete(id);
	}

	@Override
	public List<ResourceSet> getAllResourceSet() {
		String sql = "select c from ResourceSet c";
		List<ResourceSet>  resourceSetList = resourceSetDao.find(sql);
		return resourceSetList;
//		List<ResourceSet> resourceSetList = resourceSetDao.getAll();
//		Map<String, ResourceSet> resouceMap = new HashMap<String, ResourceSet>();
//		
//		for(ResourceSet resourceSet : resourceSetList) {
//			resouceMap.put(resourceSet.getCode(), resourceSet);
//		}
//		
//		List<ResourceSetNode> resourceSetNodeList = new ArrayList<ResourceSetNode>();
//		
//		ResourceSetNode resourceSetNode = null;
//		int id = 1;
//		for(ResourceSet resourceSet : resourceSetList) {
//			id = id++;
//			resourceSetNode = new ResourceSetNode(id, resourceSet);
//			
//			resourceSetNodeList.add(resourceSetNode);
//		}
//		
//		return resourceSetNodeList;
	}

	@Override
	public ResourceSet getById(String id) {
		ResourceSet resourceSet = resourceSetDao.findUniqueBy("id", id);
		return resourceSet;
	}

	
}
