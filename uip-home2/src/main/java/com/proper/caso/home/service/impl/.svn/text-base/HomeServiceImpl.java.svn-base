/* <p>文件名称: HomeServiceImpl.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-8-3</p>
 * <p>完成日期：2013-8-3</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-8-3 上午9:56:24
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.caso.home.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.TaskQuery;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.caso.home.application.model.ApplicationNode;
import com.proper.caso.home.application.model.GroupNode;
import com.proper.caso.home.application.model.MenuNode;
import com.proper.caso.home.application.model.PagingNode;
import com.proper.caso.home.service.HomeService;
import com.proper.platform.utils.time.TimeUtils;
import com.proper.uip.api.desktop.entity.ApplicationAndGroup;
import com.proper.uip.api.desktop.entity.HomeMessageEntity;
import com.proper.uip.api.desktop.entity.QuickApplicationEntity;
import com.proper.uip.api.desktop.extension.HomeMessageExtension;
import com.proper.uip.api.desktop.service.QuickApplicationService;
import com.proper.uip.api.desktop.service.SystemCategoryStrategy;
import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.RoleResourceRelation;
import com.proper.uip.api.security.entity.RoleUserRelation;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.service.ResourceService;
import com.proper.uip.api.security.service.SecurityService;
import com.proper.uip.dao.ApplicationAndGroupDao;
import com.proper.uip.security.dao.ResourcesDao;
import com.proper.uip.security.dao.RoleUserRelationDao;

@Service("HomeServiceImpl-API")
@Transactional(rollbackFor = RuntimeException.class)
public class HomeServiceImpl implements HomeService{
	@Autowired
	private ApplicationAndGroupDao applicationAndGroupDao;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	@Qualifier("systemCategory")
	private String systemCategory;
	
	@Autowired
	@Qualifier("personalIssueId")
	private String personalIssueId;
	
	
	@Autowired(required=false)
	private Map<String,SystemCategoryStrategy>  systemCategoryStrategyMap;
	
	@Autowired
	private QuickApplicationService quickApplicationService;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ResourcesDao resourcesDao;
	
	@Autowired
	private RoleUserRelationDao ruDao;
	
	@Autowired
	List<HomeMessageExtension> homeMessageExtensionList;

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuNode> getTopMenus(HttpServletRequest request) {
		List<Resource> resourceList = resourceService.getModuleResourcesOfCurrentUser(request);
		Map<String, Resource> applicationMap = new HashMap<String, Resource>();
		
		for(Resource application : resourceList){
			applicationMap.put(application.getId(), application);
		}
		
		//处理ApplicationNode
		//key:groupId value:List<ApplicationNode>
		Map<String, List<ApplicationNode>> applicationNodeListMap = new HashMap<String, List<ApplicationNode>>();
		
		Criteria createria = applicationAndGroupDao.getSession().createCriteria(ApplicationAndGroup.class);
		createria.add(Restrictions.eq("systemCategory", systemCategory))
		         .add(Restrictions.eq("moc", "resource"))
		         .addOrder(Order.asc("sequenceNumber"));
		
		List<ApplicationAndGroup> applicationList = createria.list();
		
		List<ApplicationNode> applicationNodeList = null;
		ApplicationNode applicationNode = null;
		Resource applicationResouce = null;
		for(ApplicationAndGroup entity : applicationList){
			applicationResouce = applicationMap.get(entity.getResourceId());
			if(applicationResouce == null){
				continue;
			}
			
			applicationNodeList = applicationNodeListMap.get(entity.getParent());
			if(applicationNodeList == null){
				applicationNodeList = new ArrayList<ApplicationNode>();
				
				applicationNodeListMap.put(entity.getParent(), applicationNodeList);
			}
			
			applicationNode = new ApplicationNode(applicationResouce);
			applicationNodeList.add(applicationNode);
		}
		
		if(applicationNodeListMap.isEmpty()){
			return new ArrayList<MenuNode>();
		}
		
		//处理GroupNode
		//key:pagingId value:List<GroupNode>
		Map<String, List<GroupNode>> groupNodeListMap = new HashMap<String, List<GroupNode>>();
		
		createria = applicationAndGroupDao.getSession().createCriteria(ApplicationAndGroup.class);
		createria.add(Restrictions.eq("systemCategory", systemCategory))
		         .add(Restrictions.eq("moc", "group"))
		         .addOrder(Order.asc("sequenceNumber"));
		
		List<ApplicationAndGroup> relationList = createria.list();
		
		List<GroupNode> groupNodeList = null;
		GroupNode groupNode = null;
		for(ApplicationAndGroup entity : relationList){
			if(applicationNodeListMap.containsKey(entity.getId()) == false){
				continue;
			}
			applicationNodeList = applicationNodeListMap.get(entity.getId());
			groupNode = new GroupNode(entity.getId(), entity.getName(), applicationNodeList);
			
			groupNodeList = groupNodeListMap.get(entity.getParent());
			if(groupNodeList == null){
				groupNodeList = new ArrayList<GroupNode>();
				groupNodeListMap.put(entity.getParent(), groupNodeList);
			}
			
			groupNodeList.add(groupNode);
		}
		if(groupNodeListMap.isEmpty()){
			return new ArrayList<MenuNode>();
		}
		
		//处理PagingNode
		createria = applicationAndGroupDao.getSession().createCriteria(ApplicationAndGroup.class);
		createria.add(Restrictions.eq("systemCategory", systemCategory))
		         .add(Restrictions.eq("moc", "paging"))
		         .addOrder(Order.asc("sequenceNumber"));
		
		List<ApplicationAndGroup> pagingList = createria.list();
		
        
		List<MenuNode> pagingNodeList = new ArrayList<MenuNode>();
		
		MenuNode pagingNode = null;
		for(ApplicationAndGroup entity : pagingList){
			if(groupNodeListMap.containsKey(entity.getId()) == false){
				continue;
			}
			groupNodeList = groupNodeListMap.get(entity.getId());
			pagingNode = new MenuNode(entity, "./subpage/index?menuId=" + entity.getId());
			
			pagingNodeList.add(pagingNode);
		}
				
		return pagingNodeList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<MenuNode> getModules(HttpServletRequest request, String pagingId) {
		//获取当前分屏下的栏目
		Criteria createria = applicationAndGroupDao.getSession().createCriteria(ApplicationAndGroup.class);
		createria.add(Restrictions.eq("systemCategory", systemCategory))
		         .add(Restrictions.eq("moc", "group"))
		         .add(Restrictions.eq("parent", pagingId))
		         .addOrder(Order.asc("sequenceNumber"));
		
		List<ApplicationAndGroup> relationList = createria.list();
		
		List<MenuNode> menuNodeList = new ArrayList<MenuNode>();
		Map<String, MenuNode> groupMap = new HashMap<String, MenuNode>();
		MenuNode groupNode = null;
		for(ApplicationAndGroup entity : relationList){
			groupNode = new MenuNode(entity, "");
			menuNodeList.add(groupNode);
			groupMap.put(groupNode.getId(), groupNode);
		}
		//无分屏则返回空列表
		if(groupMap.isEmpty() == true){
			return new ArrayList<MenuNode>();
		}
		
		//获取当前用户有权限的应用列表
		List<Resource> resourceList = resourceService.getModuleResourcesOfCurrentUser(request);
		//若应用列表为空，则返回空列表
		if(resourceList.isEmpty() == true){
			return new ArrayList<MenuNode>();
		}
		
		Map<String, Resource> applicationMap = new HashMap<String, Resource>();
		for(Resource application : resourceList){
			applicationMap.put(application.getId(), application);
		}
		
		createria = applicationAndGroupDao.getSession().createCriteria(ApplicationAndGroup.class);
		createria.add(Restrictions.eq("systemCategory", systemCategory))
		         .add(Restrictions.eq("moc", "resource"))
		         .add(Restrictions.in("parent", groupMap.keySet()))
		         .addOrder(Order.asc("sequenceNumber"));
		
		List<ApplicationAndGroup> applicationList = createria.list();
		Resource applicationResource = null;
		
		StringBuffer url = new StringBuffer();
		url.append(request.getContextPath())
		   .append("/")
		   .append("proxyUrl?resourceId=");
		
		for(ApplicationAndGroup entity : applicationList){
			applicationResource = applicationMap.get(entity.getResourceId());
			if(applicationResource == null){
				continue;
			}
			
			if(groupMap.containsKey(entity.getParent()) == true){
				groupMap.remove(entity.getParent());
			}
			
			menuNodeList.add(new MenuNode(entity, url.toString() + applicationResource.getId()));
		}
		
		createria = applicationAndGroupDao.getSession().createCriteria(ApplicationAndGroup.class);
		createria.add(Restrictions.eq("systemCategory", systemCategory))
		         .add(Restrictions.eq("moc", "resource"))
		         .add(Restrictions.eq("parent", pagingId))
		         .addOrder(Order.asc("sequenceNumber"));
		List<ApplicationAndGroup> relationResourceList = createria.list();
		List<MenuNode> menuNodeAppList = new ArrayList<MenuNode>();
		for(ApplicationAndGroup entity : relationResourceList){
			applicationResource = applicationMap.get(entity.getResourceId());
			if(applicationResource == null){
				continue;
			}
			groupNode = new MenuNode(entity, url.toString() + applicationResource.getId());
			menuNodeAppList.add(groupNode);
		}
		
		menuNodeList.removeAll(groupMap.values());
		menuNodeList.addAll(menuNodeAppList);
		
		return menuNodeList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MenuNode> getModulesWithoutGroup(HttpServletRequest request, String pagingId) {
		//获取当前分屏下的栏目
		Criteria createria = applicationAndGroupDao.getSession().createCriteria(ApplicationAndGroup.class);
		createria.add(Restrictions.eq("systemCategory", systemCategory))
		         .add(Restrictions.eq("moc", "group"))
		         .add(Restrictions.eq("parent", pagingId))
		         .addOrder(Order.asc("sequenceNumber"));
		
		List<ApplicationAndGroup> relationList = createria.list();
		
		List<MenuNode> menuNodeList = new ArrayList<MenuNode>();
		Map<String, MenuNode> groupMap = new HashMap<String, MenuNode>();
		MenuNode groupNode = null;
		for(ApplicationAndGroup entity : relationList){
			groupNode = new MenuNode(entity, "");
			//menuNodeList.add(groupNode);
			groupMap.put(groupNode.getId(), groupNode);
		}
		//无分屏则返回空列表
		if(groupMap.isEmpty() == true){
			return new ArrayList<MenuNode>();
		}
		
		//获取当前用户有权限的应用列表
		List<Resource> resourceList = resourceService.getModuleResourcesOfCurrentUser(request);
		//若应用列表为空，则返回空列表
		if(resourceList.isEmpty() == true){
			return new ArrayList<MenuNode>();
		}
		
		Map<String, Resource> applicationMap = new HashMap<String, Resource>();
		for(Resource application : resourceList){
			applicationMap.put(application.getId(), application);
		}
		
		createria = applicationAndGroupDao.getSession().createCriteria(ApplicationAndGroup.class);
		createria.add(Restrictions.eq("systemCategory", systemCategory))
		         .add(Restrictions.eq("moc", "resource"))
		         .add(Restrictions.in("parent", groupMap.keySet()))
		         .addOrder(Order.asc("sequenceNumber"));
		
		List<ApplicationAndGroup> applicationList = createria.list();
		Resource applicationResource = null;
		
		StringBuffer url = new StringBuffer();
		url.append(request.getContextPath())
		   .append("/")
		   .append("proxyUrl?resourceId=");
		
		for(ApplicationAndGroup entity : applicationList){
			applicationResource = applicationMap.get(entity.getResourceId());
			if(applicationResource == null){
				continue;
			}
			
			if(groupMap.containsKey(entity.getParent()) == true){
				groupMap.remove(entity.getParent());
			}
			
			menuNodeList.add(new MenuNode(entity, url.toString() + applicationResource.getId()));
		}
		
		createria = applicationAndGroupDao.getSession().createCriteria(ApplicationAndGroup.class);
		createria.add(Restrictions.eq("systemCategory", systemCategory))
		         .add(Restrictions.eq("moc", "resource"))
		         .add(Restrictions.eq("parent", pagingId))
		         .addOrder(Order.asc("sequenceNumber"));
		List<ApplicationAndGroup> relationResourceList = createria.list();
		List<MenuNode> menuNodeAppList = new ArrayList<MenuNode>();
		for(ApplicationAndGroup entity : relationResourceList){
			applicationResource = applicationMap.get(entity.getResourceId());
			if(applicationResource == null){
				continue;
			}
			groupNode = new MenuNode(entity, url.toString() + applicationResource.getId());
			menuNodeAppList.add(groupNode);
		}
		
		menuNodeList.removeAll(groupMap.values());
		menuNodeList.addAll(menuNodeAppList);
		
		return menuNodeList;
	}
	@SuppressWarnings("unchecked")
	public List<PagingNode> buildApplicationTree(HttpServletRequest request) {
		List<Resource> resourceList = resourceService.getModuleResourcesOfCurrentUser(request);
		Map<String, Resource> applicationMap = new HashMap<String, Resource>();
		
		for(Resource application : resourceList){
			applicationMap.put(application.getId(), application);
		}
		
		//处理ApplicationNode
		//key:groupId value:List<ApplicationNode>
		Map<String, List<ApplicationNode>> applicationNodeListMap = new HashMap<String, List<ApplicationNode>>();
		
		Criteria createria = applicationAndGroupDao.getSession().createCriteria(ApplicationAndGroup.class);
		createria.add(Restrictions.eq("systemCategory", systemCategory))
		         .add(Restrictions.eq("moc", "resource"))
		         .addOrder(Order.asc("sequenceNumber"));
		
		List<ApplicationAndGroup> applicationList = createria.list();
		
		List<ApplicationNode> applicationNodeList = null;
		ApplicationNode applicationNode = null;
		Resource applicationResouce = null;
		for(ApplicationAndGroup entity : applicationList){
			applicationResouce = applicationMap.get(entity.getResourceId());
			if(applicationResouce == null){
				continue;
			}
			
			applicationNodeList = applicationNodeListMap.get(entity.getParent());
			if(applicationNodeList == null){
				applicationNodeList = new ArrayList<ApplicationNode>();
				
				applicationNodeListMap.put(entity.getParent(), applicationNodeList);
			}
			
			applicationNode = new ApplicationNode(applicationResouce);
			applicationNodeList.add(applicationNode);
		}
		
		if(applicationNodeListMap.isEmpty()){
			return new ArrayList<PagingNode>();
		}
		
		//处理GroupNode
		//key:pagingId value:List<GroupNode>
		Map<String, List<GroupNode>> groupNodeListMap = new HashMap<String, List<GroupNode>>();
		
		createria = applicationAndGroupDao.getSession().createCriteria(ApplicationAndGroup.class);
		createria.add(Restrictions.eq("systemCategory", systemCategory))
		         .add(Restrictions.eq("moc", "group"))
		         .addOrder(Order.asc("sequenceNumber"));
		
		List<ApplicationAndGroup> relationList = createria.list();
		
		List<GroupNode> groupNodeList = null;
		GroupNode groupNode = null;
		for(ApplicationAndGroup entity : relationList){
			if(applicationNodeListMap.containsKey(entity.getId()) == false){
				continue;
			}
			applicationNodeList = applicationNodeListMap.get(entity.getId());
			groupNode = new GroupNode(entity.getId(), entity.getName(), applicationNodeList);
			
			groupNodeList = groupNodeListMap.get(entity.getParent());
			if(groupNodeList == null){
				groupNodeList = new ArrayList<GroupNode>();
				groupNodeListMap.put(entity.getParent(), groupNodeList);
			}
			
			groupNodeList.add(groupNode);
		}
		if(groupNodeListMap.isEmpty()){
			return new ArrayList<PagingNode>();
		}
		
		//处理PagingNode
		createria = applicationAndGroupDao.getSession().createCriteria(ApplicationAndGroup.class);
		createria.add(Restrictions.eq("systemCategory", systemCategory))
		         .add(Restrictions.eq("moc", "paging"))
		         .addOrder(Order.asc("sequenceNumber"));
		
		List<ApplicationAndGroup> pagingList = createria.list();
		
        
		List<PagingNode> pagingNodeList = new ArrayList<PagingNode>();
		
		PagingNode pagingNode = null;
		for(ApplicationAndGroup entity : pagingList){
			if(groupNodeListMap.containsKey(entity.getId()) == false){
				continue;
			}
			groupNodeList = groupNodeListMap.get(entity.getId());
			pagingNode = new PagingNode(entity.getName(), groupNodeList);
			
			pagingNodeList.add(pagingNode);
		}
		
		return pagingNodeList;
	}


	//@Override
	public long queryTaskCount(User currentUser) {
		if(currentUser == null || currentUser.getLoginName() == null || currentUser.getLoginName().trim().equals("")){
			return 0;
		}
		
		TaskQuery taskQuery = taskService.createTaskQuery();
		
		//经办人或者候选人是当前用户
		taskQuery.taskCandidateUser(currentUser.getLoginName()).taskAssignee(currentUser.getLoginName());
		long taskCount = taskQuery.count();
		
		return taskCount;
	}


	@Override
	public List<Resource> getQuickModuleCandidates(HttpServletRequest request){
		User currentUser = securityService.getCurrentUser(request);
		String moc = "module";
		
		SystemCategoryStrategy systemCategoryStrategy = systemCategoryStrategyMap.get(systemCategory);
		List<String> resourceCategoryList = systemCategoryStrategy.getResouceCategoryCodeList();
		
		List<Resource> resourceList = new ArrayList<Resource>();
		
		List<RoleUserRelation> roleUserRelationList = ruDao.findBy("userId", currentUser.getId());
		
		if(roleUserRelationList == null || roleUserRelationList.isEmpty() == true){
			return resourceList;
		}
		
		Set<String> roleIds = new HashSet<String>();
		for(RoleUserRelation ru : roleUserRelationList){
			roleIds.add(ru.getRoleId());
		}
		
		if(roleIds.isEmpty()){
			return resourceList;
		}
		
		Criteria criteria = this.resourcesDao.getSession().createCriteria(RoleResourceRelation.class);
		
		List<RoleResourceRelation> roleResourceRelationList = criteria.add(Restrictions.in("roleId", roleIds)).list();
		
		if(roleResourceRelationList == null || roleResourceRelationList.isEmpty() == true){
			return resourceList;
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
		
		List<Resource> applicationList = criteria.list();
		
		List<QuickApplicationEntity> quickApplicationEntityList = quickApplicationService.getQuickApplications(currentUser.getId());
		Map<String,Resource> resourceIdMap = new HashMap<String, Resource>();
		Resource quickResource = null;
		for (QuickApplicationEntity quickApplication : quickApplicationEntityList) {
			quickResource = resourceService.getResourceById(quickApplication.getResourceId());
			resourceIdMap.put(quickApplication.getResourceId(), quickResource);
		}
		//用户没有的资源菜单
		List<Resource> resourcesList = new ArrayList<Resource>();
		for (Resource resource : applicationList) {
			if(resourceIdMap.containsKey(resource.getId())){
				continue;
			}
			resourcesList.add(resource);
		}
		return resourcesList;
	}


	@Override
	public List<QuickApplicationEntity> getQuickModules(
			HttpServletRequest request) throws UnsupportedEncodingException {
		User currentUser = securityService.getCurrentUser(request);
		String userId = currentUser.getId();
		
		List<QuickApplicationEntity> quickModules = this.quickApplicationService.getQuickApplications(userId);
		
		return quickModules;
	}


	@Override
	public QuickApplicationEntity getQuickModuleById(
			HttpServletRequest request, String quickModuleId)
			throws UnsupportedEncodingException {
		return null;
	}

	@Override
	public QuickApplicationEntity addQuickModule(HttpServletRequest request,
			QuickApplicationEntity quickModule)
			throws UnsupportedEncodingException {
		QuickApplicationEntity theQuickModule = quickApplicationService.save(quickModule);
		return theQuickModule;
	}


	@Override
	public void deleteQuickModule(HttpServletRequest request,
			String quickModuleId) throws UnsupportedEncodingException {
		quickApplicationService.delete(quickModuleId);
	}


	@Override
	public QuickApplicationEntity getQuickModuleByResourceId(
			HttpServletRequest request, String resourceId)
			throws UnsupportedEncodingException {
		User currentUser = securityService.getCurrentUser(request);
		String userId = currentUser.getId();
		
		QuickApplicationEntity entity = this.quickApplicationService.getQuickApplication(userId, resourceId);
		return entity;
	}


	@Override
	public List<MenuNode> getPersonalIssueModules(HttpServletRequest request,
			String personalIssue) {
		//获取当前分屏下的栏目
				Criteria createria = applicationAndGroupDao.getSession().createCriteria(ApplicationAndGroup.class);
				createria.add(Restrictions.eq("systemCategory", systemCategory))
				         .add(Restrictions.eq("moc", "group"))
				         .add(Restrictions.eq("parent", personalIssue))
				         .add(Restrictions.eq("id", personalIssueId))
				         .addOrder(Order.asc("sequenceNumber"));
				
				List<ApplicationAndGroup> relationList = createria.list();
				
				List<MenuNode> menuNodeList = new ArrayList<MenuNode>();
				Map<String, MenuNode> groupMap = new HashMap<String, MenuNode>();
				MenuNode groupNode = null;
				for(ApplicationAndGroup entity : relationList){
					groupNode = new MenuNode(entity, "");
					//menuNodeList.add(groupNode);
					groupMap.put(groupNode.getId(), groupNode);
				}
				//无分屏则返回空列表
				if(groupMap.isEmpty() == true){
					return new ArrayList<MenuNode>();
				}
				
				//获取当前用户有权限的应用列表
				List<Resource> resourceList = resourceService.getModuleResourcesOfCurrentUser(request);
				//若应用列表为空，则返回空列表
				if(resourceList.isEmpty() == true){
					return new ArrayList<MenuNode>();
				}
				
				Map<String, Resource> applicationMap = new HashMap<String, Resource>();
				for(Resource application : resourceList){
					applicationMap.put(application.getId(), application);
				}
				
				createria = applicationAndGroupDao.getSession().createCriteria(ApplicationAndGroup.class);
				createria.add(Restrictions.eq("systemCategory", systemCategory))
				         .add(Restrictions.eq("moc", "resource"))
				         .add(Restrictions.in("parent", groupMap.keySet()))
				         .addOrder(Order.asc("sequenceNumber"));
				
				List<ApplicationAndGroup> applicationList = createria.list();
				Resource applicationResource = null;
				
				StringBuffer url = new StringBuffer();
				url.append(request.getContextPath())
				   .append("/")
				   .append("proxyUrl?resourceId=");
				
				for(ApplicationAndGroup entity : applicationList){
					applicationResource = applicationMap.get(entity.getResourceId());
					if(applicationResource == null){
						continue;
					}
					
					if(groupMap.containsKey(entity.getParent()) == true){
						groupMap.remove(entity.getParent());
					}
					
					menuNodeList.add(new MenuNode(entity, url.toString() + applicationResource.getId()));
				}
				
				createria = applicationAndGroupDao.getSession().createCriteria(ApplicationAndGroup.class);
				createria.add(Restrictions.eq("systemCategory", systemCategory))
				         .add(Restrictions.eq("moc", "resource"))
				         .add(Restrictions.eq("parent", personalIssue))
				         .add(Restrictions.eq("id", personalIssueId))
				         .addOrder(Order.asc("sequenceNumber"));
				List<ApplicationAndGroup> relationResourceList = createria.list();
				List<MenuNode> menuNodeAppList = new ArrayList<MenuNode>();
				for(ApplicationAndGroup entity : relationResourceList){
					applicationResource = applicationMap.get(entity.getResourceId());
					if(applicationResource == null){
						continue;
					}
					groupNode = new MenuNode(entity, url.toString() + applicationResource.getId());
					menuNodeAppList.add(groupNode);
				}
				
				menuNodeList.removeAll(groupMap.values());
				menuNodeList.addAll(menuNodeAppList);
				
				return menuNodeList;
	}


	@Override
	public List<HomeMessageEntity> getTodayMaessageList(User user) {
		List<HomeMessageEntity> homeMessageEntityList = new ArrayList<HomeMessageEntity>();
		List<HomeMessageEntity> dutyHomeMessageEntityList = new ArrayList<HomeMessageEntity>();
		List<HomeMessageEntity> officialHomeMessageEntityList = new ArrayList<HomeMessageEntity>();
		List<HomeMessageEntity> clinicHomeMessageEntityList = new ArrayList<HomeMessageEntity>();
		List<HomeMessageEntity> nurseHomeMessageEntityList = new ArrayList<HomeMessageEntity>();
		List<HomeMessageEntity> conferenceHomeMessageEntityList = new ArrayList<HomeMessageEntity>();
		//总值班
		String messageCode ="home.message.duty.scheduling";
		HomeMessageExtension  messageExtension = null;
		for(HomeMessageExtension extension: homeMessageExtensionList){
			if(extension.getMessageCode() == null){
				continue;
			}
			if(extension.getMessageCode().equals(messageCode) == true){
				messageExtension = extension;
				break;
			}
		}
		Calendar onedateStart = new GregorianCalendar(); 
		onedateStart.set(Calendar.HOUR_OF_DAY, 0);  
		onedateStart.set(Calendar.MINUTE, 0);  
		onedateStart.set(Calendar.SECOND, 0); 
		Date onedateStartDate  =  TimeUtils.parseTimeStamp(TimeUtils.formatTimeStamp(onedateStart.getTime()));
		
		Calendar onedateStop = new GregorianCalendar();   
		onedateStop.set(Calendar.HOUR_OF_DAY, 23);  
		onedateStop.set(Calendar.MINUTE, 59);  
		onedateStop.set(Calendar.SECOND, 59); 
		
		Date onedateStopDate  =  TimeUtils.parseTimeStamp(TimeUtils.formatTimeStamp(onedateStop.getTime()));
		
		if(messageExtension != null){
			dutyHomeMessageEntityList = messageExtension.getMessageListByTime(user, (Date)onedateStart.getTime().clone(), (Date)onedateStop.getTime().clone());
		}
		//行政人员排班
		messageCode ="home.message.official.scheduling";
		for(HomeMessageExtension extension: homeMessageExtensionList){
			if(extension.getMessageCode() == null){
				continue;
			}
			if(extension.getMessageCode().equals(messageCode) == true){
				messageExtension = extension;
				break;
			}
		}
		if(messageExtension != null){
			officialHomeMessageEntityList = messageExtension.getMessageListByTime(user, (Date)onedateStart.getTime().clone(), (Date)onedateStop.getTime().clone());
		}
		//门诊部排班
		messageCode ="home.message.clinic.scheduling";
		for(HomeMessageExtension extension: homeMessageExtensionList){
			if(extension.getMessageCode() == null){
				continue;
			}
			if(extension.getMessageCode().equals(messageCode) == true){
				messageExtension = extension;
				break;
			}
		}
		if(messageExtension != null){
			clinicHomeMessageEntityList = messageExtension.getMessageListByTime(user, onedateStartDate, onedateStopDate);
		}
		//护士排班
		messageCode ="home.message.nurse.scheduling";
		for(HomeMessageExtension extension: homeMessageExtensionList){
			if(extension.getMessageCode() == null){
				continue;
			}
			if(extension.getMessageCode().equals(messageCode) == true){
				messageExtension = extension;
				break;
			}
		}
		if(messageExtension != null){
			nurseHomeMessageEntityList = messageExtension.getMessageListByTime(user, (Date)onedateStart.getTime().clone(), (Date)onedateStop.getTime().clone());
		}
		
		//会议提醒
		messageCode ="home.message.conference";
		for(HomeMessageExtension extension: homeMessageExtensionList){
			if(extension.getMessageCode() == null){
				continue;
			}
			if(extension.getMessageCode().equals(messageCode) == true){
				messageExtension = extension;
				break;
			}
		}
		if(messageExtension != null){
			conferenceHomeMessageEntityList = messageExtension.getMessageListByTime(user, (Date)onedateStart.getTime().clone(), (Date)onedateStop.getTime().clone());
		}
		if(conferenceHomeMessageEntityList==null){
			conferenceHomeMessageEntityList=new ArrayList<HomeMessageEntity>();
		}
		
		homeMessageEntityList.addAll(officialHomeMessageEntityList);
		homeMessageEntityList.addAll(clinicHomeMessageEntityList);
		homeMessageEntityList.addAll(dutyHomeMessageEntityList);
		homeMessageEntityList.addAll(nurseHomeMessageEntityList);
		homeMessageEntityList.addAll(conferenceHomeMessageEntityList);
		return homeMessageEntityList;
		
	}

}