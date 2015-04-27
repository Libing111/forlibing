package com.proper.uip.bpm.definitions.service.impl;

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
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionsCategoryEntity;
import com.proper.uip.api.bpm.extension.BpmTasksByCategoryExtension;
import com.proper.uip.bpm.definitions.entity.DefinitionsCategoryNode;
import com.proper.uip.bpm.definitions.service.BpmDefinitionsCategoryService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.definitions.dao.ProcessDefinitionsCategoryDao;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class BpmDefinitionsCategoryServiceImpl implements BpmDefinitionsCategoryService {

	@Autowired
	private ProcessDefinitionsCategoryDao categoryDao;
	
	@Autowired(required=false)
	private Map<String, BpmTasksByCategoryExtension> bpmTasksByCategoryExtensionMap;
	
	@Override
	public List<DefinitionsCategoryNode> buildTree() {
		//把所有节点变成node
		List<ProcessDefinitionsCategoryEntity> processDefinitionsCategoryList = categoryDao.getAll();
	
		List<DefinitionsCategoryNode> definitionsCategoryNodeList = new ArrayList<DefinitionsCategoryNode>();
		
		DefinitionsCategoryNode definitionsCategoryNode = null;
		int id = 0;
		int parentId = -1;
		boolean checked = false;
		boolean chkDisabled = true;
		//key ProcessDefinitionsCategoryEntity.id  value = DefinitionsCategoryNode
		Map<String,DefinitionsCategoryNode> nodeMap = new HashMap<String,DefinitionsCategoryNode>();
		for(ProcessDefinitionsCategoryEntity processDefinitionsCategory : processDefinitionsCategoryList) {
			id = id + 1;
			definitionsCategoryNode = new DefinitionsCategoryNode(processDefinitionsCategory,checked,chkDisabled, id,parentId);
			nodeMap.put(processDefinitionsCategory.getId(), definitionsCategoryNode);
			
			definitionsCategoryNodeList.add(definitionsCategoryNode);
		}
		//根据node来找parentId
		for (DefinitionsCategoryNode node : definitionsCategoryNodeList) {
			if(node.getParentExtendId() == null){
				continue;
			}
			if(nodeMap.containsKey(node.getParentExtendId()) == false ){
				continue;
			}
			node.setParentId(nodeMap.get(node.getParentExtendId()).getId());
		}
		
		return definitionsCategoryNodeList;
	}

	@Override
	public ProcessDefinitionsCategoryEntity getById(String id) {
		ProcessDefinitionsCategoryEntity processDefinitionsCategory = categoryDao.get(id);
		return processDefinitionsCategory;
	}

	@Override
	public void save(ProcessDefinitionsCategoryEntity processDefinitionsCategoryEntity) {
		categoryDao.save(processDefinitionsCategoryEntity);		
	}

	@Override
	public Page<ProcessDefinitionsCategoryEntity> getDefinitionsCategory(PageConfig pageConfig, String parent, String name) {
		Criteria criteria = createCriteria(parent, name);

		Page<ProcessDefinitionsCategoryEntity> processDefinitionsCategoryPage = new Page<ProcessDefinitionsCategoryEntity>(pageConfig);

		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		processDefinitionsCategoryPage.setTotal(totalCount);
		
		criteria = createCriteria(parent, name);


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
	public List<ProcessDefinitionsCategoryEntity> getChildren(String id) {
		List<ProcessDefinitionsCategoryEntity> processDefinitionsCategoryList = categoryDao.findBy("parentId", id);
		return processDefinitionsCategoryList;
	}

	@Override
	public void deleteChild(String id) {
		List<ProcessDefinitionsCategoryEntity> processDefinitionsCategoryList = categoryDao.findBy("parentId", id);
		categoryDao.delete(processDefinitionsCategoryList);
	}

	@Override
	public void deleteById(String id) {
		categoryDao.delete(id);		
	}

	@Override
	public ProcessDefinitionsCategoryEntity getBySequence(int sequence,String parentExtendId) {

		ProcessDefinitionsCategoryEntity processDefinitionsCategory = categoryDao.findUnique(Restrictions.and(Restrictions.eq("sequence", sequence),Restrictions.eq("parentId", parentExtendId)));
		
		return processDefinitionsCategory;
	}

	@Override
	public ProcessDefinitionsCategoryEntity validateName(String name) {
		ProcessDefinitionsCategoryEntity processDefinitionsCategory = categoryDao.findUnique(Restrictions.eq("name", name));
		
		return processDefinitionsCategory;
	}

	@Override
	public ProcessDefinitionsCategoryEntity validateCode(String code) {
		ProcessDefinitionsCategoryEntity processDefinitionsCategory = categoryDao.findUnique(Restrictions.eq("code", code));
		
		return processDefinitionsCategory;
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

}
