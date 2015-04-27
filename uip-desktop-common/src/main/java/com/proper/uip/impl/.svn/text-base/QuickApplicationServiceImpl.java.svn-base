/* <p>文件名称: QuickApplicationServiceImpl.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-11-3</p>
 * <p>完成日期：2014-11-3</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-11-3上午11:43:09
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.desktop.entity.QuickApplicationEntity;
import com.proper.uip.api.desktop.service.QuickApplicationService;
import com.proper.uip.dao.QuickApplicationDao;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class QuickApplicationServiceImpl implements QuickApplicationService {
	@Autowired
	private QuickApplicationDao quickApplicationDao;

	@Override
	public QuickApplicationEntity save(QuickApplicationEntity entity) {
		if(entity == null){
			return null;
		}
		
		QuickApplicationEntity newEntity = quickApplicationDao.save(entity);
		return newEntity;
	}

	@Override
	public void save(List<QuickApplicationEntity> entityList) {
		quickApplicationDao.save(entityList);
	}

	@Override
	public void delete(String id) {
		if(id == null || id.trim().equals("")){
			return;
		}
		
		quickApplicationDao.delete(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(List<String> idList) {
		if(idList == null || idList.isEmpty() == true){
			return;
		}
		
		Criteria criteria = quickApplicationDao.getSession().createCriteria(QuickApplicationEntity.class);
		criteria.add(Restrictions.in("id", idList));
		List<QuickApplicationEntity> entityList = criteria.list();
		
		if(entityList == null || entityList.isEmpty() == true){
			return;
		}
		quickApplicationDao.delete(entityList);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<QuickApplicationEntity> getQuickApplications(String userId) {
		List<QuickApplicationEntity> quickApplications = new ArrayList<QuickApplicationEntity>();
		if(userId == null || userId.trim().equals("") == true){
			return quickApplications;
		}
		
		Criteria criteria = quickApplicationDao.getSession().createCriteria(QuickApplicationEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		quickApplications = criteria.list();
		
		return quickApplications;
	}

	@Override
	public QuickApplicationEntity getQuickApplication(String userId, String resourceId) {
		Criteria criteria = quickApplicationDao.getSession().createCriteria(QuickApplicationEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("resourceId", resourceId));
		
		QuickApplicationEntity entity = (QuickApplicationEntity) criteria.uniqueResult();
		
		return entity;
	}
}

