package com.proper.uip.bpm.scenario.category.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.bpm.definitions.entity.ScenarioCategory;
import com.proper.uip.bpm.scenario.category.service.BpmScenarioStrategyService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.definitions.dao.ScenarioCategoryDao;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class BpmScenarioStrategyServiceImpl implements
		BpmScenarioStrategyService {
	@Autowired
	private ScenarioCategoryDao scenarioStrategyDao;
	
	@Override
	public Page<ScenarioCategory> findAll(PageConfig pageConfig, String name) {
		Criteria criteria = createCriteria(name);
		
		Page<ScenarioCategory> page = new Page<ScenarioCategory>(pageConfig);

		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		page.setTotal(totalCount);
		
		criteria = createCriteria(name);


		int fromIndex = page.getOffset();
		if(fromIndex < 0){
			System.out.println("Error! fromIndex = " + fromIndex);
			System.out.println("Error! Class=" + this.getClass().getName());
			System.out.println("Error! fromIndex = " + fromIndex);
			fromIndex = 0;
		}
		criteria.setFirstResult(fromIndex);
		criteria.setMaxResults(page.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<ScenarioCategory> modelList = criteria.list();
		page.setRows(modelList);
	
		return page;
	}

	private Criteria createCriteria(String name) {
		Criteria criteria = scenarioStrategyDao.getSession().createCriteria(ScenarioCategory.class);
		
		String searchTextMode;
		if(name != null && name.trim().isEmpty() == false){
			searchTextMode = "%" + name + "%";
			criteria.add(Restrictions.like("name",searchTextMode));
		}
		return criteria;
	}

	@Override
	public void saveScenarioStrategy(ScenarioCategory entity) {
		scenarioStrategyDao.save(entity);		
	}

	@Override
	public List<ScenarioCategory> getAll() {
		List<ScenarioCategory> scenarioCategoryList = scenarioStrategyDao.getAll();
		
		return scenarioCategoryList;
	}

}
