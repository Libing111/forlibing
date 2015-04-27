/* <p>文件名称: RaCategoryServiceImpl.java </p>
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

import com.proper.uip.api.security.entity.RaCategory;
import com.proper.uip.api.security.entity.RoleCategory;
import com.proper.uip.api.security.service.RaCategoryService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.dao.RaCategoryDao;
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class RaCategoryServiceImpl implements RaCategoryService {
	@Autowired
	private RaCategoryDao raCategoryDao;
	
	@Override
	public List<RaCategory> getAllRaCategorys() {
		String hql = "select c from RaCategory c where c.stop = false";
		List<RaCategory> raCategoryList = raCategoryDao.find(hql);
		
		return raCategoryList;
	}

	@Override
	public void saveRaCategory(
			RaCategory raCategory) {
		raCategoryDao.save(raCategory);
		
	}

	@Override
	public void updateSaveRaCategory(
			RaCategory raCategory) {
		raCategoryDao.save(raCategory);
	}

	@Override
	public RaCategory getById(String id) {
		RaCategory raCategory = raCategoryDao.findUniqueBy("id", id);
		return raCategory;
	}

	@Override
	public void delete(String id) {
		raCategoryDao.delete(id);
	}

	public Page<RaCategory> findAll(PageConfig config, String name, String stop) {
		Criteria criteria = createCriteria(name, stop);
		
		Page<RaCategory> raCategoryPage = new Page<RaCategory>(config);

		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		raCategoryPage.setTotal(totalCount);
		
		criteria = createCriteria(name, stop);


		int fromIndex = raCategoryPage.getOffset();
		if(fromIndex < 0){
			System.out.println("Error! fromIndex = " + fromIndex);
			System.out.println("Error! Class=" + this.getClass().getName());
			System.out.println("Error! fromIndex = " + fromIndex);
			fromIndex = 0;
		}
		criteria.setFirstResult(fromIndex);
		criteria.setMaxResults(raCategoryPage.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<RaCategory> modelList = criteria.list();
		raCategoryPage.setRows(modelList);
	
		return raCategoryPage;
	}
	
	private Criteria createCriteria(String name, String stop) {
		Criteria criteria = raCategoryDao.getSession().createCriteria(RaCategory.class);
		
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
	public RaCategory getByCode(String code) {
		RaCategory raCategory = raCategoryDao.findUniqueBy("code",code);
		return raCategory;
	}
	
	@Override
	public RaCategory getBySequenceNumber(int sequenceNumber) {
		RaCategory raCategory = raCategoryDao.findUniqueBy("sequenceNumber",sequenceNumber);
		return raCategory;
	}

	@Override
	public RaCategory getByName(String name) {
		RaCategory raCategory = raCategoryDao.findUniqueBy("name",name);
		return raCategory;
	}
}
