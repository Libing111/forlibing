package com.proper.caso.home.service.impl;

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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.caso.home.application.model.MyDesktopEntity;
import com.proper.caso.home.dao.MyDesktopDao;
import com.proper.caso.home.service.MyDesktopService;
import com.proper.uip.api.desktop.entity.ApplicationAndGroup;
import com.proper.uip.api.desktop.service.SystemCategoryStrategy;
import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.ResourceChildNode;
import com.proper.uip.api.security.entity.RoleResourceRelation;
import com.proper.uip.api.security.entity.RoleUserRelation;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.service.ResourceService;
import com.proper.uip.api.security.service.SecurityService;
import com.proper.uip.dao.ApplicationAndGroupDao;
import com.proper.uip.security.dao.ResourcesDao;
import com.proper.uip.security.dao.RoleUserRelationDao;
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class MyDesktopServiceImpl implements MyDesktopService {
	@Autowired
	@Qualifier("systemCategory")
	private String systemCategory;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private MyDesktopDao myDesktopDao;
	
	@Autowired(required=false)
	private Map<String,SystemCategoryStrategy>  systemCategoryStrategyMap;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private ResourcesDao resourcesDao;
	
	@Autowired
	private RoleUserRelationDao ruDao;
	
	@Autowired
	private ApplicationAndGroupDao applicationAndGroupDao;
	
	@Override
	public List<MyDesktopEntity> getMyDesktopModules(HttpServletRequest request) {
		User currentUser = securityService.getCurrentUser(request);
		String userId = currentUser.getId();
		
		List<MyDesktopEntity> myDesktopModules = this.getMyDesktopApplications(request,userId);
		
		return myDesktopModules;
	}

	@Override
	public List<Resource> getMyDesktopModuleCandidates(
			HttpServletRequest request) {
		User currentUser = securityService.getCurrentUser(request);
		String moc = "subsystem";
		
		SystemCategoryStrategy systemCategoryStrategy = systemCategoryStrategyMap.get(systemCategory);
		List<String> resourceCategoryList = systemCategoryStrategy.getResouceCategoryCodeList();
		
		List<Resource> applicationList = this.getResourcesOfCurrentUser(currentUser, moc, resourceCategoryList);
		
		
		List<MyDesktopEntity> myDesktopApplicationEntityList = this.getMyDesktopApplications(request,currentUser.getId());
		Map<String,Resource> resourceIdMap = new HashMap<String, Resource>();
		Resource quickResource = null;
		for (MyDesktopEntity quickApplication : myDesktopApplicationEntityList) {
			quickResource = resourceService.getResourceById(quickApplication.getResourceId());
			resourceIdMap.put(quickApplication.getResourceId(), quickResource);
		}
		//用户没有的资源菜单
		List<Resource> resourceList = new ArrayList<Resource>();
		for (Resource resource : applicationList) {
			if(resourceIdMap.containsKey(resource.getId())){
				continue;
			}
			resourceList.add(resource);
		}
		
		return resourceList;
	}

	private List<Resource> getResourcesOfCurrentUser(User currentUser,
			String moc, List<String> resourceCategoryList) {
		List<Resource> resourceChildNodeList = new ArrayList<Resource>();
		
		List<RoleUserRelation> roleUserRelationList = ruDao.findBy("userId", currentUser.getId());
		
		if(roleUserRelationList == null || roleUserRelationList.isEmpty() == true){
			return resourceChildNodeList;
		}
		
		Set<String> roleIds = new HashSet<String>();
		for(RoleUserRelation ru : roleUserRelationList){
			roleIds.add(ru.getRoleId());
		}
		
		if(roleIds.isEmpty()){
			return resourceChildNodeList;
		}
		
		Criteria criteria = this.resourcesDao.getSession().createCriteria(RoleResourceRelation.class);
		
		@SuppressWarnings("unchecked")
		List<RoleResourceRelation> roleResourceRelationList = criteria.add(Restrictions.in("roleId", roleIds)).list();
		
		if(roleResourceRelationList == null || roleResourceRelationList.isEmpty() == true){
			return resourceChildNodeList;
		}
		
		Set<String> resouceIdSet = new HashSet<String>();
		for(RoleResourceRelation relation : roleResourceRelationList){
			resouceIdSet.add(relation.getResourceId());
		}
		
		criteria = this.resourcesDao.getSession().createCriteria(Resource.class);
		
		if(moc != null && moc.trim().equals("") == false){
			criteria.add(Restrictions.eq("moc", moc));
		}
		
		if(resourceCategoryList != null && resourceCategoryList.isEmpty() == false){
			criteria.add(Restrictions.in("categoryCode", resourceCategoryList));
		}
		criteria.add(Restrictions.eq("anonymously", false));
		
		criteria.add(Restrictions.in("id", resouceIdSet));
		
		criteria.addOrder(Order.asc("sequenceNumber"));
		
		@SuppressWarnings("unchecked")
		List<Resource> applicationList = criteria.list();
		return applicationList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MyDesktopEntity> getMyDesktopApplications(HttpServletRequest request,String userId) {
		List<MyDesktopEntity> mydesktopApplication = new ArrayList<MyDesktopEntity>();
		if(userId == null || userId.trim().equals("") == true){
			return mydesktopApplication;
		}
		List<Resource> resourceList = resourceService.getModuleResourcesOfCurrentUser(request);
		
		Set<String> resourceIdSet = new HashSet<String>();
		for (Resource resource : resourceList) {
			resourceIdSet.add(resource.getId());
		}
		
		Criteria criteria = myDesktopDao.getSession().createCriteria(MyDesktopEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.in("resourceId", resourceIdSet));
		
		mydesktopApplication = criteria.list();
		
		return mydesktopApplication;
	}

	@Override
	public List<ResourceChildNode> getMyDesktopModuleCandidates(HttpServletRequest request,User user,
			String parentChildCode) {
		String moc = "module";
		
		SystemCategoryStrategy systemCategoryStrategy = systemCategoryStrategyMap.get(systemCategory);
		List<String> resourceCategoryList = systemCategoryStrategy.getResouceCategoryCodeList();
		
		List<Resource> applicationList = this.getResourcesOfCurrentUser(user, moc, resourceCategoryList);
		
		List<MyDesktopEntity> myDesktopApplicationEntityList = this.getMyDesktopApplications(request,user.getId());
		Map<String,Resource> resourceIdMap = new HashMap<String, Resource>();
		Resource quickResource = null;
		for (MyDesktopEntity quickApplication : myDesktopApplicationEntityList) {
			quickResource = resourceService.getResourceById(quickApplication.getResourceId());
			resourceIdMap.put(quickApplication.getResourceId(), quickResource);
		}
		//用户没有的资源菜单
		List<Resource> resourceList = new ArrayList<Resource>();
		for (Resource resource : applicationList) {
			if(resourceIdMap.containsKey(resource.getId())){
				continue;
			}
			resourceList.add(resource);
		}
		//return resourceList;
		
		List<ResourceChildNode> resourceChildNodesList = new ArrayList<ResourceChildNode>();
		
		ResourceChildNode resourceChildNode = null;
		for (Resource resource : resourceList) {
			resourceChildNode = new ResourceChildNode(resource);
			resourceChildNodesList.add(resourceChildNode);
		}
		return resourceChildNodesList;
	}

	@Override
	public Map<String, String> clickParentResource(User user,
			String parentChildCode) {
		Map<String, String> allResourceMap = new HashMap<String, String>();
		if(user.getRaId() == null){
			
			return allResourceMap;
		}
		String childResourceId = "";
		
		SystemCategoryStrategy systemCategoryStrategy = systemCategoryStrategyMap.get(systemCategory);
		List<String> resourceCategoryList = systemCategoryStrategy.getResouceCategoryCodeList();
		
		List<Resource> applicationList = this.getResourcesOfCurrentUser(user, null, resourceCategoryList);
		for (Resource resource : applicationList) {
			if(resource.getMoc().equals("module") == false){
				continue;
			}
			if(allResourceMap.containsKey(resource.getParent()) == false){
				allResourceMap.put(resource.getParent(), resource.getId());
				continue;
			}
			childResourceId = allResourceMap.get(resource.getParent());
			
			childResourceId = childResourceId + "," + resource.getId();
			
			allResourceMap.put(resource.getParent(), childResourceId);
			
		}
		return allResourceMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MyDesktopEntity> addMyDesktopModule(List<String> applicationAndGroupIdList,
			HttpServletRequest request) {
		User currentUser = securityService.getCurrentUser(request);
		
		List<ApplicationAndGroup> applicationAndGroupList = new ArrayList<ApplicationAndGroup>();
		Criteria criteria = applicationAndGroupDao.getSession().createCriteria(ApplicationAndGroup.class);
		criteria.add(Restrictions.in("id", applicationAndGroupIdList));
		
		applicationAndGroupList = criteria.list();
		
		List<String> resourceIdList = new ArrayList<String>();
		for (ApplicationAndGroup applicationAndGroup : applicationAndGroupList) {
			resourceIdList.add(applicationAndGroup.getResourceId());
		}
		List<Resource> resourceList = new ArrayList<Resource>();
		criteria = resourcesDao.getSession().createCriteria(Resource.class);
		criteria.add(Restrictions.in("id", resourceIdList));
		
		resourceList = criteria.list();
		
		
		List<MyDesktopEntity> myDesktopEntityList = new ArrayList<MyDesktopEntity>();
		MyDesktopEntity myDesktopEntity = null;
		for (Resource resource : resourceList) {
			myDesktopEntity = new MyDesktopEntity();
			myDesktopEntity.setId(null);
			myDesktopEntity.setResourceId(resource.getId());
			myDesktopEntity.setTitle(resource.getName());
			myDesktopEntity.setIcon("demo");
			myDesktopEntity.setUrl(resource.getUrl());
			myDesktopEntity.setUserId(currentUser.getId());
			myDesktopEntity.setCloseable(true);
			myDesktopEntityList.add(myDesktopEntity);
		}
		
		myDesktopDao.save(myDesktopEntityList);
		
		return myDesktopEntityList;
	}

	@Override
	public void deleteQuickModule(HttpServletRequest request, String modelId) {
		myDesktopDao.delete(modelId);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApplicationAndGroup> getApplicationsByResourceId(
			Set<String> resourceIdSet) {
		List<ApplicationAndGroup> applicationAndGroupList = new ArrayList<ApplicationAndGroup>();
		if(resourceIdSet.isEmpty()){
			return applicationAndGroupList;
		}
		
		Criteria criteria = applicationAndGroupDao.getSession().createCriteria(ApplicationAndGroup.class);
		criteria.add(Restrictions.in("resourceId", resourceIdSet));
		
		applicationAndGroupList = criteria.list();
		
		return applicationAndGroupList;
	}

}
