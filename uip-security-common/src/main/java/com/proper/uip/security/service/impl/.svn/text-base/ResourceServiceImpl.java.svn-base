/* <p>文件名称: ResourceServiceImpl.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-8-24</p>
 * <p>完成日期：2013-8-24</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-8-24 下午12:16:20
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.security.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.RoleResourceRelation;
import com.proper.uip.api.security.entity.RoleRuleRelation;
import com.proper.uip.api.security.entity.RoleUserRelation;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.extension.RoleFilterRuleExtension;
import com.proper.uip.api.security.service.ResourceService;
import com.proper.uip.api.security.service.RoleRuleRelationService;
import com.proper.uip.api.security.service.SecurityService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.dao.ResourcesDao;
import com.proper.uip.security.dao.RoleUserRelationDao;

@Service("ResourceService_Public")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
public class ResourceServiceImpl implements ResourceService {
	@Autowired
	private ResourcesDao resourcesDao;
	
	@Autowired
	private RoleUserRelationDao ruDao;

	@Autowired(required=false)
	@Qualifier("resourceCategoris")
	private String resourceCategoris;//逗号分隔
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private RoleRuleRelationService roleRuleRelationService;
	
	@Autowired
	private List<RoleFilterRuleExtension> roleFilterRuleExtensionList;
	
	@Override
	public List<Resource> getResourcesOfCurrentUser(HttpServletRequest request, String moc) {
		List<String> resourceCategoryList = new ArrayList<String>();
		if(resourceCategoris != null){
			resourceCategoryList = Arrays.asList(resourceCategoris.split(","));
		}
		
		User currentUser = securityService.getCurrentUser(request);
		Boolean anonymously = false;
		String parent = null;
		
		List<Resource> resourceList = this.getResourcesOfCurrentUser(currentUser, resourceCategoryList, parent, moc, anonymously);
		
		return resourceList;
	}
	
	@Override
	public List<Resource> getResourcesOfCurrentUser(HttpServletRequest request, String parent, String moc) {
		List<String> resourceCategoryList = new ArrayList<String>();
		if(resourceCategoris != null){
			resourceCategoryList = Arrays.asList(resourceCategoris.split(","));
		}
		
		User currentUser = securityService.getCurrentUser(request);
		Boolean anonymously = false;
		
		List<Resource> resourceList = this.getResourcesOfCurrentUser(currentUser, resourceCategoryList, parent, moc, anonymously);
		
		return resourceList;
	}
	
	@Override
	public List<Resource> getResourcesOfCurrentUserDesc(HttpServletRequest request, String parent, String moc) {
		List<String> resourceCategoryList = new ArrayList<String>();
		if(resourceCategoris != null){
			resourceCategoryList = Arrays.asList(resourceCategoris.split(","));
		}
		
		User currentUser = securityService.getCurrentUser(request);
		Boolean anonymously = false;
		List<Resource> resourceList = this.getResourcesOfCurrentUser(currentUser, resourceCategoryList, parent, moc, anonymously);
		
		return resourceList;
	}
	

	@Override
	public Resource getResourceByCode(String code) {
		Resource resouce = this.resourcesDao.findUniqueBy("code", code);
		
		return resouce;
	}

	@Override
	public List<Resource> getModuleResourcesOfCurrentUser(
			HttpServletRequest request) {
		String moc = "module";
		
		List<Resource> resourceList = this.getResourcesOfCurrentUser(request,  moc);
		return resourceList;
	}

	@Override
	public List<Resource> getResourcesByMoc(String moc) {
		List<String> resourceCategoryList = new ArrayList<String>();
		if(resourceCategoris != null){
			resourceCategoryList = Arrays.asList(resourceCategoris.split(","));
		}
		
		List<Resource> resourceList = this.getResourcesByMoc(moc, resourceCategoryList);
		
		return resourceList;
	}

	@Override
	public List<Resource> getResourcesByMoc(String moc, List<String> resourceCategoryList) {
		boolean anonymously = false;
		List<Resource> resourceList = this.getResources(moc, resourceCategoryList, anonymously);
		
		return resourceList;
	}
	
	@SuppressWarnings("unchecked")
	private List<Resource> getResourcesOfCurrentUser(User currentUser, List<String> resourceCategoryList, String parent, String moc, Boolean anonymously){
		List<Resource> resourceList = new ArrayList<Resource>();
		Set<String> roleIds = new HashSet<String>();
		
		List<RoleUserRelation> roleUserRelationList = ruDao.findBy("userId", currentUser.getId());
		if(roleUserRelationList != null && roleUserRelationList.isEmpty() == false){
			//为个人角色
			for(RoleUserRelation ru : roleUserRelationList){
				roleIds.add(ru.getRoleId());
			}
		}


		//获取规则角色
		Map<String,RoleFilterRuleExtension> roleFilterRuleExtensionMap = new HashMap<String, RoleFilterRuleExtension>();
		for (RoleFilterRuleExtension roleFilterRuleExtension : roleFilterRuleExtensionList) {
			roleFilterRuleExtensionMap.put(roleFilterRuleExtension.getId(), roleFilterRuleExtension);
		}
		List<RoleRuleRelation> roleRuleRelationList = roleRuleRelationService.getAll();
		RoleFilterRuleExtension rule = null;
		for (RoleRuleRelation roleRuleRelation : roleRuleRelationList) {
			rule = roleFilterRuleExtensionMap.get(roleRuleRelation.getRuleId());
			if(rule == null){
				continue;
			}
			boolean flag = rule.checkUser(currentUser.getId(), roleRuleRelation.getRoleId());
			if(flag == true){
				roleIds.add(roleRuleRelation.getRoleId());
			}
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

		if(parent != null && parent.trim().equals("") == false){
			criteria.add(Restrictions.eq("parent", parent));
		}
		
		if(moc != null && moc.trim().equals("") == false){
			criteria.add(Restrictions.eq("moc", moc));
		}
		
		if(resourceCategoryList != null && resourceCategoryList.isEmpty() == false){
			criteria.add(Restrictions.in("categoryCode", resourceCategoryList));
		}
		
		if(anonymously != null){
			criteria.add(Restrictions.eq("anonymously", anonymously));
		}
		
		criteria.add(Restrictions.in("id", resouceIdSet));
		
		criteria.addOrder(Order.asc("sequenceNumber"));
		
		resourceList = criteria.list();
		
		return resourceList;
	}
	
	@Override
	public Page<Resource> getResourcePageByMoc(PageConfig pageConfig, String moc) {
		Boolean anonymously = false;
		
		List<String> resourceCategoryList = new ArrayList<String>();
		if(resourceCategoris != null){
			resourceCategoryList = Arrays.asList(resourceCategoris.split(","));
		}
		Page<Resource> resourcePage = this.getResources(pageConfig, moc, resourceCategoryList, anonymously);
		return resourcePage;
	}
	
	private List<Resource> getResources(String moc, List<String> resourceCategoryList, Boolean anonymously) {
		Criteria criteria = resourcesDao.getSession().createCriteria(Resource.class);
		
		if(moc != null && moc.trim().equals("") == false){
			criteria.add(Restrictions.eq("moc", moc));
		}
		
		if(resourceCategoryList != null && resourceCategoryList.isEmpty() == false){
			criteria.add(Restrictions.in("categoryCode", resourceCategoryList));
		}
		
		if(anonymously != null){
			criteria.add(Restrictions.eq("anonymously", anonymously));
		}
		
		@SuppressWarnings("unchecked")
		List<Resource> resourceList = criteria.list();
		
		return resourceList;
	}

	private Page<Resource> getResources(PageConfig pageConfig, String moc, List<String> resourceCategoryList, Boolean anonymously) {
		Criteria criteria = createCriteria(moc,resourceCategoryList, anonymously);

		Page<Resource> resourcePage = new Page<Resource>(pageConfig);

		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		resourcePage.setTotal(totalCount);
		
		criteria = createCriteria(moc,resourceCategoryList,anonymously);


		int fromIndex = resourcePage.getOffset();
		if(fromIndex < 0){
			System.out.println("Error! fromIndex = " + fromIndex);
			System.out.println("Error! Class=" + this.getClass().getName());
			System.out.println("Error! fromIndex = " + fromIndex);
			fromIndex = 0;
		}
		criteria.setFirstResult(fromIndex);
		criteria.setMaxResults(resourcePage.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<Resource> resourceList = criteria.list();
		resourcePage.setRows(resourceList);
	
		return resourcePage;
	}

	private Criteria createCriteria( String moc, List<String> resourceCategoryList, Boolean anonymously) {
		Criteria criteria = resourcesDao.getSession().createCriteria(Resource.class);
		
		if(moc != null && moc.trim().equals("") == false){
			criteria.add(Restrictions.eq("moc", moc));
		}
		
		if(resourceCategoryList != null && resourceCategoryList.isEmpty() == false){
			criteria.add(Restrictions.in("categoryCode", resourceCategoryList));
		}
		
		if(anonymously != null){
			criteria.add(Restrictions.eq("anonymously", anonymously));
		}
		return criteria;
	}

	
	@Override
	public Resource getResourceById(String id) {
		Resource resouce = this.resourcesDao.findUniqueBy("id", id);
		
		return resouce;
	}

	

}
