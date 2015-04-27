package com.proper.uip.bpm.definitions.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.activiti.engine.repository.ProcessDefinition;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionManagementEntity;
import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionsCategoryEntity;
import com.proper.uip.bpm.definitions.service.BpmDefinitionsCategoryService;
import com.proper.uip.bpm.definitions.service.ProcessDefinitionManagementService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.definitions.dao.ProcessDefinitionManagementDao;
import com.proper.uip.definitions.dao.ProcessDefinitionsCategoryDao;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class ProcessDefinitionManagementServiceImpl implements ProcessDefinitionManagementService {
	@Autowired
	private ProcessDefinitionManagementDao processDefinitionManagementDao;
	
	@Autowired
	private ProcessDefinitionsCategoryDao processDefinitionsCategoryDao;
	
	@Autowired
	private BpmDefinitionsCategoryService categoryService;

	@Override
	public void getCreateProcessDef(List<ProcessDefinition> processDefinitionList) {
		
		List<ProcessDefinitionManagementEntity> processDefinitionManagementList = processDefinitionManagementDao.getAll();
		Set<String> processDefinitionIdSet = new HashSet<String>();
		for (ProcessDefinitionManagementEntity processDefinitionManagementEntity : processDefinitionManagementList) {
			processDefinitionIdSet.add(processDefinitionManagementEntity.getId());
		}
		
		List<ProcessDefinitionManagementEntity> processDefinitionManagementLists = new ArrayList<ProcessDefinitionManagementEntity>();
		ProcessDefinitionManagementEntity processDefinitionManagement = null;
		
		ProcessDefinitionsCategoryEntity processDefinitionsCategory = processDefinitionsCategoryDao.findUniqueBy("code", "QT");
		
		for (ProcessDefinition processDefinition : processDefinitionList) {
			if(processDefinitionIdSet.contains(processDefinition.getId()) == true){
				continue;
			}
			processDefinitionManagement = new ProcessDefinitionManagementEntity(processDefinition, processDefinitionsCategory);
			processDefinitionManagementLists.add(processDefinitionManagement);
		}
		processDefinitionManagementDao.save(processDefinitionManagementLists);
	}

	@Override
	public List<ProcessDefinitionManagementEntity> getAll() {

		List<ProcessDefinitionManagementEntity> processDefinitionManagementList = processDefinitionManagementDao.getAll();
		return processDefinitionManagementList;
	}

	@Override
	public Page<ProcessDefinitionManagementEntity> getDefinitionsManagement(PageConfig pageConfig, String name,String categoryName) {
		Criteria criteria = createCriteria(name,categoryName);

		Page<ProcessDefinitionManagementEntity> processDefinitionsManagementPage = new Page<ProcessDefinitionManagementEntity>(pageConfig);

		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		processDefinitionsManagementPage.setTotal(totalCount);
		
		criteria = createCriteria(name,categoryName);


		int fromIndex = processDefinitionsManagementPage.getOffset();
		if(fromIndex < 0){
			System.out.println("Error! fromIndex = " + fromIndex);
			System.out.println("Error! Class=" + this.getClass().getName());
			System.out.println("Error! fromIndex = " + fromIndex);
			fromIndex = 0;
		}
		criteria.setFirstResult(fromIndex);
		criteria.setMaxResults(processDefinitionsManagementPage.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<ProcessDefinitionManagementEntity> processDefinitionsManagementList = criteria.list();
		processDefinitionsManagementPage.setRows(processDefinitionsManagementList);
	
		return processDefinitionsManagementPage;
	}

	private Criteria createCriteria(String name,String categoryName) {
		Criteria criteria = processDefinitionManagementDao.getSession().createCriteria(ProcessDefinitionManagementEntity.class);
		
		String searchTextMode;
		if(name != null && name.trim().isEmpty() == false){
			searchTextMode = "%" + name + "%";
			criteria.add(Restrictions.like("name",searchTextMode));
		}
		if(categoryName != null && categoryName.trim().isEmpty() == false){
			searchTextMode = "%" + categoryName + "%";
			criteria.add(Restrictions.like("categoryName",searchTextMode));
		}
		return criteria;
	}

	@Override
	public ProcessDefinitionManagementEntity getById(String id) {
		ProcessDefinitionManagementEntity processDefinitionManagement = processDefinitionManagementDao.get(id);
		return processDefinitionManagement;
	}

	@Override
	public void save(ProcessDefinitionManagementEntity processDefinitionManagementEntity) {
		processDefinitionManagementDao.save(processDefinitionManagementEntity);
		
	}

	@Override
	public List<ProcessDefinitionManagementEntity> getByCategoryCode(String categoryCode) {
		List<ProcessDefinitionsCategoryEntity> categoryList = categoryService.getLeafCategoryList(categoryCode);
		List<String> categoryIdList = new ArrayList<String>();
		for(ProcessDefinitionsCategoryEntity category : categoryList){
			categoryIdList.add(category.getId());
		}
		
		if(categoryIdList.isEmpty() == true){
			return new ArrayList<ProcessDefinitionManagementEntity>();
		}
		
		Criteria criteria = processDefinitionManagementDao.getSession().createCriteria(ProcessDefinitionManagementEntity.class);
		criteria.add(Restrictions.in("categoryId", categoryIdList));
		
		@SuppressWarnings("unchecked")
		List<ProcessDefinitionManagementEntity> processDefinitionManagementList = criteria.list();
		
		return processDefinitionManagementList;
	}

	@Override
	public List<ProcessDefinitionManagementEntity> getByCategoryId(String id) {
		List<ProcessDefinitionManagementEntity> managementList = processDefinitionManagementDao.findBy("categoryId", id);
		return managementList;
	}

	@Override
	public void deleteByCategoryId(String id) {
		String hql = "delete from ProcessDefinitionManagementEntity c where c.categoryId = ?";
		processDefinitionManagementDao.batchExecute(hql, id);
	}
}
