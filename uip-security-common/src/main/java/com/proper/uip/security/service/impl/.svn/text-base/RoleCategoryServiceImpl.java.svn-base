/* <p>文件名称: RoleCategoryServiceImpl.java </p>
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
import com.proper.uip.api.security.service.RoleCategoryService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.dao.RoleCategoryDao;
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class RoleCategoryServiceImpl implements RoleCategoryService {
	@Autowired
	private RoleCategoryDao roleCategoryDao;
	
	@Override
	public List<RoleCategory> getAllRoleCategorys() {
		String hql = "select c from RoleCategory c where c.stop = false";
		List<RoleCategory> roleCategoryList = roleCategoryDao.find(hql);
		
		return roleCategoryList;
	}

	@Override
	public void saveRoleCategory(
			RoleCategory roleCategory) {
		roleCategoryDao.save(roleCategory);
		
	}

	@Override
	public void updateSaveRoleCategory(
			RoleCategory roleCategory) {
		roleCategoryDao.save(roleCategory);
	}

	@Override
	public RoleCategory getById(String id) {
		RoleCategory roleCategory = roleCategoryDao.findUniqueBy("id", id);
		return roleCategory;
	}

	@Override
	public void delete(String id) {
		roleCategoryDao.delete(id);
	}

	public Page<RoleCategory> findAll(PageConfig config, String name, String stop) {
		Criteria criteria = createCriteria(name, stop);
		
		Page<RoleCategory> roleCategoryPage = new Page<RoleCategory>(config);

		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		roleCategoryPage.setTotal(totalCount);
		
		criteria = createCriteria(name, stop);


		int fromIndex = roleCategoryPage.getOffset();
		if(fromIndex < 0){
			System.out.println("Error! fromIndex = " + fromIndex);
			System.out.println("Error! Class=" + this.getClass().getName());
			System.out.println("Error! fromIndex = " + fromIndex);
			fromIndex = 0;
		}
		criteria.setFirstResult(fromIndex);
		criteria.setMaxResults(roleCategoryPage.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<RoleCategory> modelList = criteria.list();
		roleCategoryPage.setRows(modelList);
	
		return roleCategoryPage;
	}
	
	private Criteria createCriteria(String name, String stop) {
		Criteria criteria = roleCategoryDao.getSession().createCriteria(RoleCategory.class);
		
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
	public RoleCategory getByCode(String code) {
		RoleCategory roleCategory = roleCategoryDao.findUniqueBy("code",code);
		return roleCategory;
	}
	
	@Override
	public RoleCategory getBySequenceNumber(int sequenceNumber) {
		RoleCategory roleCategory = roleCategoryDao.findUniqueBy("sequenceNumber",sequenceNumber);
		return roleCategory;
	}

	@Override
	public RoleCategory getByName(String name) {
		Criteria criteria = roleCategoryDao.getSession().createCriteria(RoleCategory.class);
		
		String searchTextMode;
		if(name != null && name.trim().isEmpty() == false){
			searchTextMode = "%" + name + "%";
			criteria.add(Restrictions.like("name",searchTextMode));
		}
		RoleCategory roleCategory = (RoleCategory) criteria.uniqueResult();
		return roleCategory;
	}
}
