package com.proper.uip.security.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.proper.uip.common.core.dao.HibernateDao;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.api.security.entity.RacateResset;
import com.proper.uip.api.security.entity.RegistrationAuthority;
import com.proper.uip.api.security.service.RaResouceSetStrategy;


@Repository
public class RegistrationAuthorityDao extends HibernateDao<RegistrationAuthority, String>{
	@Autowired(required = false)
    private Map<String, RaResouceSetStrategy>  raResouceSetStrategyMap;
	
	
	@Autowired
	private RaResourceSetRelationDao raResourceSetRelationDao;
	
	@Autowired
	private RacateRessetDao racateRessetDao;
	
//	@SuppressWarnings("unchecked")
//	@Override
//	public RegistrationAuthority save(final RegistrationAuthority entity){
//		if(entity.getId() != null || entity.getCategoryCode() == null){
//			return super.save(entity);
//		}
//		
//		RegistrationAuthority ra = super.save(entity);
//		
////		if(raResouceSetStrategyMap == null){
////			return ra;
////		}
////		
//		RaResouceSetStrategy strategy = getRaResourceSetStrategy(entity);
//		if(strategy == null){
//			return ra;
//		}
//		
//		Criteria criteria = this.getSession().createCriteria(ResourceSet.class);
//		criteria.add(Restrictions.in("code",strategy.getResouceSetCodeList()));
//		List<ResourceSet> resourceSetList = criteria.list();
//		if(resourceSetList == null || resourceSetList.isEmpty() == true){
//			return ra;
//		}
//		RaResourceSetRelation raResourceSetRelation = null;
//		
//		List<RaResourceSetRelation> raResourceSetRelationList = new ArrayList<RaResourceSetRelation>();
//		
//		for (ResourceSet resourceSet : resourceSetList) {
//			raResourceSetRelation = new RaResourceSetRelation(ra, resourceSet);
//			raResourceSetRelationList.add(raResourceSetRelation);
//		}
//		
//		if(raResourceSetRelationList.isEmpty() == false){
//			raResourceSetRelationDao.save(raResourceSetRelationList);
//		}
//		
//		return ra;
//	}



	
	@Override
	public void save(List<RegistrationAuthority> entities) {
		// TODO Auto-generated method stub
		super.save(entities);
	}



	@Override
	public void delete(RegistrationAuthority entity) {
		// TODO Auto-generated method stub
		super.delete(entity);
	}



	@Override
	public void delete(List<RegistrationAuthority> entitys) {
		// TODO Auto-generated method stub
		super.delete(entitys);
	}



	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		super.delete(id);
	}



	public Page<RegistrationAuthority> findAllByNamePage(PageConfig pageConfig, String name, String category) {
		Criteria criteria = createCriteria(name, category);

		Page<RegistrationAuthority> organizationPage = new Page<RegistrationAuthority>(pageConfig);

		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		organizationPage.setTotal(totalCount);
		
		criteria = createCriteria(name, category);


		int fromIndex = organizationPage.getOffset();
		if(fromIndex < 0){
			System.out.println("Error! fromIndex = " + fromIndex);
			System.out.println("Error! Class=" + this.getClass().getName());
			System.out.println("Error! fromIndex = " + fromIndex);
			fromIndex = 0;
		}
		criteria.setFirstResult(fromIndex);
		criteria.setMaxResults(organizationPage.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<RegistrationAuthority> personList = criteria.list();
		organizationPage.setRows(personList);
	
		return organizationPage;
	}

	private Criteria createCriteria(String name, String category) {
		Criteria criteria = this.getSession().createCriteria(RegistrationAuthority.class);
		
		String searchTextMode;
		if(name != null && name.trim().isEmpty() == false){
			searchTextMode = "%" + name + "%";
			criteria.add(Restrictions.like("name",searchTextMode));
		}
		if(category != null && category.trim().isEmpty() == false){
			searchTextMode = "%" + category + "%";
			criteria.add(Restrictions.like("categoryName",searchTextMode));
		}
		return criteria;
	}


	private RaResouceSetStrategy getRaResourceSetStrategy(final RegistrationAuthority entity) {
		
		if(raResouceSetStrategyMap != null){
			RaResouceSetStrategy strategy = raResouceSetStrategyMap.get(entity.getCategoryCode());
			return strategy;
		}
		RaResouceSetStrategy strategy = new RaResouceSetStrategy();
		List<String> resouceSetCodeList = new ArrayList<String>();
		
		List<RacateResset> racateRessetList = racateRessetDao.findBy("raCategoryCode", entity.getCategoryCode());
		if(racateRessetList == null || racateRessetList.isEmpty() == true ){
			return strategy;
		}
		
		for (RacateResset racateResset : racateRessetList) {
			resouceSetCodeList.add(racateResset.getResourceSetCode());
		}
		String raCategoryCode = entity.getCategoryCode();
		
		strategy.setRaCategoryCode(raCategoryCode);
		strategy.setResouceSetCodeList(resouceSetCodeList);
		
		return strategy;
	}
	
	
	
	
}
