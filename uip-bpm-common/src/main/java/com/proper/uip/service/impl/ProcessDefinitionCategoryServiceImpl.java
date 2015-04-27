/* <p>文件名称: ProcessDefinitionCategoryServiceImpl.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-12-27</p>
 * <p>完成日期：2013-12-27</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-12-27上午11:49:47
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionManagementEntity;
import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionsCategoryEntity;
import com.proper.uip.api.bpm.extension.BpmTasksByCategoryExtension;
import com.proper.uip.api.bpm.service.ProcessDefinitionCategoryService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.definitions.dao.ProcessDefinitionManagementDao;
import com.proper.uip.definitions.dao.ProcessDefinitionsCategoryDao;

@Service
public class ProcessDefinitionCategoryServiceImpl implements ProcessDefinitionCategoryService {
	@Autowired
	private ProcessDefinitionsCategoryDao categoryDao;
	
	@Autowired(required=false)
	private Map<String, BpmTasksByCategoryExtension> bpmTasksByCategoryExtensionMap;
	
	@Autowired
    private ProcessDefinitionManagementDao processDefinitionManagementDao;

	@Override
	public Page<ProcessDefinitionsCategoryEntity> getDefinitionCategoryPage(PageConfig pageConfig, String parentId, String name) {
		Criteria criteria = createCriteria(parentId, name);

		Page<ProcessDefinitionsCategoryEntity> processDefinitionsCategoryPage = new Page<ProcessDefinitionsCategoryEntity>(pageConfig);

		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		processDefinitionsCategoryPage.setTotal(totalCount);
		
		criteria = createCriteria(parentId, name);


		int fromIndex = processDefinitionsCategoryPage.getOffset();
		if(fromIndex < 0){
			System.out.println("Error! fromIndex = " + fromIndex);
			System.out.println("Error! Class=" + this.getClass().getName());
			System.out.println("Error! fromIndex = " + fromIndex);
			fromIndex = 0;
		}
		criteria.setFirstResult(fromIndex);
		criteria.setMaxResults(processDefinitionsCategoryPage.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<ProcessDefinitionsCategoryEntity> processDefinitionsCategoryList = criteria.list();
		processDefinitionsCategoryPage.setRows(processDefinitionsCategoryList);
	
		return processDefinitionsCategoryPage;
	}

	@Override
	public ProcessDefinitionsCategoryEntity getById(String id) {
		ProcessDefinitionsCategoryEntity entity = categoryDao.get(id);
		return entity;
	}

	@Override
	public List<ProcessDefinitionsCategoryEntity> getChildren(String id) {
		List<ProcessDefinitionsCategoryEntity> processDefinitionsCategoryList = categoryDao.findBy("parentId", id);
		return processDefinitionsCategoryList;
	}

	@Override
	public List<ProcessDefinitionsCategoryEntity> getSubcategoryList(String code) {
		Criteria criteria = this.categoryDao.getSession().createCriteria(ProcessDefinitionsCategoryEntity.class);
		criteria.add(Restrictions.eq("parentCode", code)).addOrder(Order.asc("sequence"));
		
		@SuppressWarnings("unchecked")
		List<ProcessDefinitionsCategoryEntity> categoryList = criteria.list();
		
		return categoryList;
	}

	@Override
	public List<ProcessDefinitionsCategoryEntity> getLeafCategoryList() {
		List<ProcessDefinitionsCategoryEntity> categoryList = categoryDao.getAll();
		
		Map<String, ProcessDefinitionsCategoryEntity> categoryMap = new HashMap<String, ProcessDefinitionsCategoryEntity>();
		for(ProcessDefinitionsCategoryEntity category : categoryList){
			categoryMap.put(category.getId(), category);
		}
		
		//获取所有叶子节点
		List<ProcessDefinitionsCategoryEntity> leafCategoryList = new ArrayList<ProcessDefinitionsCategoryEntity>();
		leafCategoryList.addAll(categoryList);
		
		ProcessDefinitionsCategoryEntity parentCategory = null;
		for(ProcessDefinitionsCategoryEntity category : categoryList){
			if(categoryMap.containsKey(category.getParentId()) == false){
				continue;
			}
			
			parentCategory = categoryMap.get(category.getParentId());
			leafCategoryList.remove(parentCategory);
		}
		
		return leafCategoryList;
	}

	@Override
	public List<ProcessDefinitionsCategoryEntity> getLeafCategoryList(String code) {
		List<ProcessDefinitionsCategoryEntity> categoryList = categoryDao.getAll();
		
		Map<String, ProcessDefinitionsCategoryEntity> categoryMap = new HashMap<String, ProcessDefinitionsCategoryEntity>();
		for(ProcessDefinitionsCategoryEntity category : categoryList){
			categoryMap.put(category.getId(), category);
		}
		
		//获取所有叶子节点
		List<ProcessDefinitionsCategoryEntity> leafCategoryList = new ArrayList<ProcessDefinitionsCategoryEntity>();
		leafCategoryList.addAll(categoryList);
		
		ProcessDefinitionsCategoryEntity parentCategory = null;
		for(ProcessDefinitionsCategoryEntity category : categoryList){
			if(categoryMap.containsKey(category.getParentId()) == false){
				continue;
			}
			
			parentCategory = categoryMap.get(category.getParentId());
			leafCategoryList.remove(parentCategory);
		}
		
		//过滤非后继叶子节点
		List<ProcessDefinitionsCategoryEntity> successorLeafCategoryList = new ArrayList<ProcessDefinitionsCategoryEntity>();
		successorLeafCategoryList.addAll(leafCategoryList);
		
		for(ProcessDefinitionsCategoryEntity category : leafCategoryList){
			if(this.checkSuccessor(code, category, categoryMap) == true){
				continue;
			}
			
			successorLeafCategoryList.remove(category);
		}
		
		return successorLeafCategoryList;
	}

	@Override
	public BpmTasksByCategoryExtension getTasksByCategoryExtension(String processDefinitionCategoryCode) {
		if(bpmTasksByCategoryExtensionMap == null){
			return null;
		}
		if(processDefinitionCategoryCode == null){
			return null;
		}
		
		BpmTasksByCategoryExtension applicationExtension = bpmTasksByCategoryExtensionMap.get(processDefinitionCategoryCode);
		if(applicationExtension != null){
			return applicationExtension;
		}
		
		ProcessDefinitionsCategoryEntity category = this.categoryDao.findUniqueBy("code", processDefinitionCategoryCode);
		if(category == null){
			return null;
		}
		
		return this.getTasksByCategoryExtension(category.getParentCode());
	}
	
	/*
	 * 检查是否后继节点
	 */
	private boolean checkSuccessor(String ancestorCategoryCode, ProcessDefinitionsCategoryEntity successorCategory, Map<String, ProcessDefinitionsCategoryEntity> categoryMap){
		if(ancestorCategoryCode == null){
			return false;
		}
		
		if(successorCategory == null){
			return false;
		}
		
		if(successorCategory.getCode().equals(ancestorCategoryCode) == true){
			return true;
		}
		
		if(successorCategory.getParentCode() == null){
			return false;
		}
		
		if(successorCategory.getParentCode().equals(ancestorCategoryCode) == true){
			return true;
		}
		
		ProcessDefinitionsCategoryEntity parentCategory = categoryMap.get(successorCategory.getParentCode());
		
		return checkSuccessor(ancestorCategoryCode, parentCategory, categoryMap);
	}
	
	private Criteria createCriteria(String parent,String name) {
		Criteria criteria = categoryDao.getSession().createCriteria(ProcessDefinitionsCategoryEntity.class);
		
		String searchTextMode;
		if(parent == null){
			searchTextMode = "%" + parent + "%";
			criteria.add(Restrictions.like("parentId",searchTextMode));
		}
		if(parent != null && parent.trim().isEmpty() == false){
			searchTextMode = "%" + parent + "%";
			criteria.add(Restrictions.like("parentId",searchTextMode));
		}
		if(name != null && name.trim().isEmpty() == false){
			searchTextMode = "%" + name + "%";
			criteria.add(Restrictions.like("name",searchTextMode));
		}
		criteria.addOrder(Order.asc("sequence"));
		return criteria;
	}

	@Override
	public Map<String, ProcessDefinitionManagementEntity> getProcessDefinitionMapByCategoryCode(
			List<String> processDefinitionCategoryCodeList) {
		List<ProcessDefinitionManagementEntity> processDefinitionManagementList = this.getProcessDefinitionListByCategoryCode(processDefinitionCategoryCodeList);
		
		Map<String, ProcessDefinitionManagementEntity> procDefMap = new HashMap<String, ProcessDefinitionManagementEntity>();
		for(ProcessDefinitionManagementEntity processDefinition : processDefinitionManagementList){
			procDefMap.put(processDefinition.getId(), processDefinition);
		}

		return procDefMap;
	}
	

	/*
	 * 获取流程分类及其子分类下所有流程定义列表
	 * 约定流程定义的分类只能指定分类树上的叶子分类
	 * 所以此处取得实际上是叶子后继分类下的流程定义列表
	 */
	@Override
	public List<ProcessDefinitionManagementEntity> getProcessDefinitionListByCategoryCode(
			List<String> processDefinitionCategoryCodeList) {
		List<ProcessDefinitionsCategoryEntity> categoryList = new ArrayList<ProcessDefinitionsCategoryEntity>();
		for(String processDefinitionCategoryCode : processDefinitionCategoryCodeList){
			categoryList.addAll(this.getLeafCategoryList(processDefinitionCategoryCode));
		}
		
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

}

