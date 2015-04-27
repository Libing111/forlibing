/* <p>文件名称: ResourceCategoryServiceImpl.java </p>
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

import com.proper.uip.api.security.entity.ResourceCategory;
import com.proper.uip.api.security.service.ResourceCategoryService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.dao.ResourceCategoryDao;
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class ResourceCategoryServiceImpl implements ResourceCategoryService {
	@Autowired
	private ResourceCategoryDao resourceCategoryDao;
	
	@Override
	public List<ResourceCategory> getAllResourceCategorys() {
		String hql = "select c from ResourceCategory c where c.stop = false";
		List<ResourceCategory> resourceCategoryList = resourceCategoryDao.find(hql);
		
		return resourceCategoryList;
	}

	@Override
	public void saveResourceCategory(
			ResourceCategory resourceCategory) {
		resourceCategoryDao.save(resourceCategory);
		
	}

	@Override
	public void updateSaveResourceCategory(
			ResourceCategory resourceCategory) {
		resourceCategoryDao.save(resourceCategory);
	}

	@Override
	public ResourceCategory getById(String id) {
		ResourceCategory resourceCategory = resourceCategoryDao.findUniqueBy("id", id);
		return resourceCategory;
	}

	@Override
	public void delete(String id) {
		resourceCategoryDao.delete(id);
	}

	public Page<ResourceCategory> findAll(PageConfig config, String name, String stop) {
		Criteria criteria = createCriteria(name, stop);
		
		Page<ResourceCategory> resourceCategoryPage = new Page<ResourceCategory>(config);

		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		resourceCategoryPage.setTotal(totalCount);
		
		criteria = createCriteria(name, stop);


		int fromIndex = resourceCategoryPage.getOffset();
		if(fromIndex < 0){
			System.out.println("Error! fromIndex = " + fromIndex);
			System.out.println("Error! Class=" + this.getClass().getName());
			System.out.println("Error! fromIndex = " + fromIndex);
			fromIndex = 0;
		}
		criteria.setFirstResult(fromIndex);
		criteria.setMaxResults(resourceCategoryPage.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<ResourceCategory> modelList = criteria.list();
		resourceCategoryPage.setRows(modelList);
	
		return resourceCategoryPage;
	}
	
	private Criteria createCriteria(String name, String stop) {
		Criteria criteria = resourceCategoryDao.getSession().createCriteria(ResourceCategory.class);
		
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
	public ResourceCategory getByCode(String code) {
		ResourceCategory resourceCategory = resourceCategoryDao.findUniqueBy("code",code);
		return resourceCategory;
	}

	@Override
	public ResourceCategory getByName(String name) {
		ResourceCategory resourceCategory = resourceCategoryDao.findUniqueBy("name",name);
		return resourceCategory;
	}

	@Override
	public ResourceCategory getBySequenceNumber(int sequenceNumber) {
		ResourceCategory resourceCategory = resourceCategoryDao.findUniqueBy("sequenceNumber",sequenceNumber);
		return resourceCategory;
	}
}
