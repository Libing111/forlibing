/* <p>文件名称: ModuleServiceImpl.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-11-24</p>
 * <p>完成日期：2014-11-24</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-11-24下午4:34:42
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.desktop.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.desktop.entity.ApplicationAndGroup;
import com.proper.uip.desktop.dao.ModuleDao;
import com.proper.uip.desktop.service.ModuleService;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class ModuleServiceImpl implements ModuleService {
	@Autowired
	private ModuleDao moduleDao;

	@SuppressWarnings("unchecked")
	@Override
	public List<ApplicationAndGroup> getChildModulesByPagingId(String systemCategory, String pagingId, Set<String> resourceIdFilterSet) {
		Criteria criteria = moduleDao.getSession().createCriteria(ApplicationAndGroup.class);

		criteria = criteria.add(Restrictions.eq("systemCategory", systemCategory))
				           .add(Restrictions.in("resourceId", resourceIdFilterSet));
		
		List<ApplicationAndGroup> applicationList = criteria.list();
		
		Set<String> parentIdSet = new HashSet<String>();
		for(ApplicationAndGroup app : applicationList){
			parentIdSet.add(app.getParent());
		}
		
		criteria = moduleDao.getSession().createCriteria(ApplicationAndGroup.class);
		criteria = criteria.add(Restrictions.eq("systemCategory", systemCategory))
		                   .add(Restrictions.eq("parent", pagingId))
		                   .add(Restrictions.or(Restrictions.and(Restrictions.eq("moc", ModuleService.DESKTOP_MOC_APPLICATION),
		                		                		         Restrictions.in("resourceId", resourceIdFilterSet)),
		                		                Restrictions.and(Restrictions.eq("moc", ModuleService.DESKTOP_MOC_GROUP),
				                		                		 Restrictions.in("id", parentIdSet)))); 

		List<ApplicationAndGroup> moduleList = criteria.list();
		Set<String> groupIdSet = new HashSet<String>();
		for(ApplicationAndGroup module : moduleList){
			if(module.getMoc().equals(ModuleService.DESKTOP_MOC_GROUP) == false){
				continue;
			}
			groupIdSet.add(module.getId());
		}

		for(ApplicationAndGroup app : applicationList){
			if(groupIdSet.contains(app.getParent()) == false){
				continue;
			}
			
			moduleList.add(app);
		}
		
		
		return moduleList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApplicationAndGroup> getModules(String systemCategory,
			Set<String> resourceIdFilterSet, String moc) {
		//应用
		Criteria criteria = moduleDao.getSession().createCriteria(ApplicationAndGroup.class);

		criteria = criteria.add(Restrictions.eq("systemCategory", systemCategory))
				           .add(Restrictions.in("resourceId", resourceIdFilterSet))
						   .addOrder(Order.asc("sequenceNumber"));
		List<ApplicationAndGroup> applicationList = criteria.list();
		
		if(moc.equals(ModuleService.DESKTOP_MOC_APPLICATION)){
			return applicationList;
		}
		
		//分组
		Set<String> parentIdSet = new HashSet<String>();
		for(ApplicationAndGroup app : applicationList){
			parentIdSet.add(app.getParent());
		}
		
		criteria = moduleDao.getSession().createCriteria(ApplicationAndGroup.class);
		criteria = criteria.add(Restrictions.eq("systemCategory", systemCategory))
				           .add(Restrictions.in("id", parentIdSet))
				           .add(Restrictions.eq("moc", ModuleService.DESKTOP_MOC_GROUP))
						   .addOrder(Order.asc("sequenceNumber"));
		List<ApplicationAndGroup> groupList = criteria.list();
		
		if(moc.equals(ModuleService.DESKTOP_MOC_GROUP)){
			return groupList;
		}
		
		Set<String> pagingIdSet = new HashSet<String>();
		pagingIdSet.addAll(parentIdSet);
		for(ApplicationAndGroup group : groupList){
			pagingIdSet.remove(group.getId());
			pagingIdSet.add(group.getParent());
		}
		
		//分屏
		criteria = moduleDao.getSession().createCriteria(ApplicationAndGroup.class);

		criteria = criteria.add(Restrictions.eq("systemCategory", systemCategory))
				           .add(Restrictions.in("id", pagingIdSet))
				           .add(Restrictions.eq("moc", ModuleService.DESKTOP_MOC_PAGING))
						   .addOrder(Order.asc("sequenceNumber"));
		
		List<ApplicationAndGroup> pingList = criteria.list();
		return pingList;
	}

}

