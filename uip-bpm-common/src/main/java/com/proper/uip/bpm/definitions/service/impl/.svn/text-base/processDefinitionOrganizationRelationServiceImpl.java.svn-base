package com.proper.uip.bpm.definitions.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionOrganizationRelation;
import com.proper.uip.bpm.definitions.service.processDefinitionOrganizationRelationService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.definitions.dao.ProcessDefinitionOrganizationRelationDao;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class processDefinitionOrganizationRelationServiceImpl implements processDefinitionOrganizationRelationService {

	@Autowired
	private ProcessDefinitionOrganizationRelationDao processDefinitionOrganizationRelationDao;
	
	@Override
	public Page<ProcessDefinitionOrganizationRelation> findAllOrg(PageConfig pageConfig,
			String organizationName, String resourceName,
			String processDefinitionName) {
		Criteria criteria = createCriteria(organizationName,resourceName, processDefinitionName);
		

		Page<ProcessDefinitionOrganizationRelation> orgPage = new Page<ProcessDefinitionOrganizationRelation>(pageConfig);

		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		orgPage.setTotal(totalCount);
		
		criteria = createCriteria(organizationName,resourceName, processDefinitionName);


		int fromIndex = orgPage.getOffset();
		if(fromIndex < 0){
			System.out.println("Error! fromIndex = " + fromIndex);
			System.out.println("Error! Class=" + this.getClass().getName());
			System.out.println("Error! fromIndex = " + fromIndex);
			fromIndex = 0;
		}
		criteria.setFirstResult(fromIndex);
		criteria.setMaxResults(orgPage.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<ProcessDefinitionOrganizationRelation> modelList = criteria.list();
		orgPage.setRows(modelList);
	
		return orgPage;
	}
	
	private Criteria createCriteria(String organizationName,String resourceName,String processDefinitionName) {
		Criteria criteria = processDefinitionOrganizationRelationDao.getSession().createCriteria(ProcessDefinitionOrganizationRelation.class);
		
		String searchTextMode;
		if(organizationName != null && organizationName.trim().isEmpty() == false){
			searchTextMode = "%" + organizationName + "%";
			criteria.add(Restrictions.like("organizationName",searchTextMode));
		}
		if(resourceName != null && resourceName.trim().isEmpty() == false){
			searchTextMode = "%" + resourceName + "%";
			criteria.add(Restrictions.like("resourceName",searchTextMode));
		}
		if(processDefinitionName != null && processDefinitionName.trim().isEmpty() == false){
			searchTextMode = "%" + processDefinitionName + "%";
			criteria.add(Restrictions.like("processDefinitionName",searchTextMode));
		}
		
		return criteria;
	}

	@Override
	public ProcessDefinitionOrganizationRelation saveOrganization(
			ProcessDefinitionOrganizationRelation organizationEntity) {
		ProcessDefinitionOrganizationRelation orgEntity =	processDefinitionOrganizationRelationDao.save(organizationEntity);
		return orgEntity;
	}

	@Override
	public ProcessDefinitionOrganizationRelation getById(String id) {
		ProcessDefinitionOrganizationRelation organizationEntity = processDefinitionOrganizationRelationDao.get(id);
		return organizationEntity;
	}

	@Override
	public void deleteById(String id) {
		processDefinitionOrganizationRelationDao.delete(id);		
	}


}
