package com.proper.uip.security.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.proper.uip.api.security.entity.ResourceSet;
import com.proper.uip.common.core.dao.HibernateDao;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;

@Repository("resourceSetDao")
public class ResourceSetDao extends HibernateDao<ResourceSet, String>{
	public Page<ResourceSet> findResourceSetPage(PageConfig config, String name) {
		Criteria criteria = createCriteria(name);
	
		Page<ResourceSet> resourceSetPage = new Page<ResourceSet>(config);
	
		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		resourceSetPage.setTotal(totalCount);
		
		criteria = createCriteria(name);
	
	
		int fromIndex = resourceSetPage.getOffset();
		if(fromIndex < 0){
			System.out.println("Error! fromIndex = " + fromIndex);
			System.out.println("Error! Class=" + this.getClass().getName());
			System.out.println("Error! fromIndex = " + fromIndex);
			fromIndex = 0;
		}
		criteria.setFirstResult(fromIndex);
		criteria.setMaxResults(resourceSetPage.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<ResourceSet> personList = criteria.list();
		resourceSetPage.setRows(personList);
	
		return resourceSetPage;
	}
	
	private Criteria createCriteria(String name) {
		Criteria criteria = this.getSession().createCriteria(ResourceSet.class);
		
		String searchTextMode;
		if(name != null && name.trim().isEmpty() == false){
			searchTextMode = "%" + name + "%";
			criteria.add(Restrictions.like("name",searchTextMode));
		}
		return criteria;
	}
}
