/* <p>文件名称: QuickModuleServiceImpl.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-11-24</p>
 * <p>完成日期：2014-11-24</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-11-24下午3:56:24
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.desktop.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.desktop.entity.QuickApplicationEntity;
import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.RoleResourceRelation;
import com.proper.uip.api.security.entity.RoleUserRelation;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.desktop.dao.QuickModuleDao;
import com.proper.uip.desktop.service.QuickModuleService;
import com.proper.uip.security.dao.ResourcesDao;
import com.proper.uip.security.dao.RoleUserRelationDao;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class QuickModuleServiceImpl implements QuickModuleService {
	@Autowired
    private QuickModuleDao quickModuleDao;
	
	@Autowired
	private ResourcesDao resourcesDao;
	
	@Autowired
	private RoleUserRelationDao ruDao;
	
	@Override
	public QuickApplicationEntity save(QuickApplicationEntity entity) {
		QuickApplicationEntity theEntity = quickModuleDao.save(entity);
		
		return theEntity;
	}

	@Override
	public void save(List<QuickApplicationEntity> entityList) {
		quickModuleDao.save(entityList);
	}

	@Override
	public void delete(String id) {
		quickModuleDao.delete(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(List<String> idList) {
		if(idList == null || idList.isEmpty()){
			return;
		}
		
		Criteria criteria = quickModuleDao.getSession().createCriteria(QuickApplicationEntity.class);
		criteria.add(Restrictions.in("id", idList));
		
		List<QuickApplicationEntity> entityList = criteria.list();
		
		if(entityList == null || entityList.isEmpty()){
			return;
		}
		quickModuleDao.delete(entityList);
	}

	@Override
	public List<QuickApplicationEntity> getQuickModules(String userId) {
		List<QuickApplicationEntity> entityList = quickModuleDao.findBy("userId", userId);
		
		return entityList;
	}

	@Override
	public QuickApplicationEntity getQuickModuleByResourceId(String userId,
			String resourceId) {
		Criteria criteria = quickModuleDao.getSession().createCriteria(QuickApplicationEntity.class);
		criteria.add(Restrictions.eq("userId", userId))
				.add(Restrictions.eq("resourceId", resourceId));
		
		QuickApplicationEntity entity = (QuickApplicationEntity) criteria.uniqueResult();
		return entity;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Resource> getResourcesOfCurrentUser(User currentUser, String moc, List<String> resourceCategoryList) {
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
		
		resourceList = criteria.list();
		
		return resourceList;
	}

}

