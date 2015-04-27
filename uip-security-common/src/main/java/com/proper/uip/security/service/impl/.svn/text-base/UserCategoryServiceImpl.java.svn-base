/* <p>文件名称: UserCategoryServiceImpl.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-3-12</p>
 * <p>完成日期：2014-3-12</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-3-12下午3:34:17
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	lichunyan
 */
package com.proper.uip.security.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.security.entity.RoleCategory;
import com.proper.uip.api.security.entity.UserCategory;
import com.proper.uip.api.security.service.UserCategoryService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.dao.UserCategoryDao;
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserCategoryServiceImpl implements UserCategoryService {
	@Autowired
	private UserCategoryDao userCategoryDao;
	
	@Override
	public List<UserCategory> getAllUserCategorys() {
		String hql = "select c from UserCategory c where c.stop = false";
		List<UserCategory> userCategoryList = userCategoryDao.find(hql);
		
		return userCategoryList;
	}

	@Override
	public void saveUserCategory(
			UserCategory userCategory) {
		userCategoryDao.save(userCategory);
		
	}

	@Override
	public void updateSaveUserCategory(
			UserCategory userCategory) {
		userCategoryDao.save(userCategory);
	}

	@Override
	public UserCategory getById(String id) {
		UserCategory userCategory = userCategoryDao.findUniqueBy("id", id);
		return userCategory;
	}

	@Override
	public void delete(String id) {
		userCategoryDao.delete(id);
	}

	public Page<UserCategory> findAll(PageConfig config, String name, String stop) {
		Criteria criteria = createCriteria(name,stop);
		
		Page<UserCategory> userCategoryPage = new Page<UserCategory>(config);

		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		userCategoryPage.setTotal(totalCount);
		
		criteria = createCriteria(name,stop);


		int fromIndex = userCategoryPage.getOffset();
		if(fromIndex < 0){
			System.out.println("Error! fromIndex = " + fromIndex);
			System.out.println("Error! Class=" + this.getClass().getName());
			System.out.println("Error! fromIndex = " + fromIndex);
			fromIndex = 0;
		}
		criteria.setFirstResult(fromIndex);
		criteria.setMaxResults(userCategoryPage.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<UserCategory> modelList = criteria.list();
		userCategoryPage.setRows(modelList);
	
		return userCategoryPage;
	}
	
	private Criteria createCriteria(String name, String stop) {
		Criteria criteria = userCategoryDao.getSession().createCriteria(UserCategory.class);
		
		String searchTextMode;
		if(name != null && name.trim().isEmpty() == false){
			searchTextMode = "%" + name + "%";
			criteria.add(Restrictions.like("name",searchTextMode));
		}
		if(stop != null && stop.trim().isEmpty() == false){
			boolean inactive = Boolean.parseBoolean(stop);
			criteria.add(Restrictions.eq("stop", inactive));
		}
		criteria.addOrder(Order.asc("sequenceNumber"));
		return criteria;
	}

	@Override
	public UserCategory getByCode(String code) {
		UserCategory userCategory = userCategoryDao.findUniqueBy("code",code);
		return userCategory;
	}
	
	@Override
	public UserCategory getBySequenceNumber(int sequenceNumber) {
		UserCategory userCategory = userCategoryDao.findUniqueBy("sequenceNumber",sequenceNumber);
		return userCategory;
	}

	@Override
	public UserCategory getByName(String name) {
		Criteria criteria = userCategoryDao.getSession().createCriteria(UserCategory.class);
		
		String searchTextMode;
		if(name != null && name.trim().isEmpty() == false){
			searchTextMode = "%" + name + "%";
			criteria.add(Restrictions.like("name",searchTextMode));
		}
		UserCategory userCategory = (UserCategory) criteria.uniqueResult();
		return userCategory;
	}
}
