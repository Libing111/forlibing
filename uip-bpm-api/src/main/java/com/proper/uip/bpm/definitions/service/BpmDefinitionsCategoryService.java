package com.proper.uip.bpm.definitions.service;

import java.util.List;

import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionsCategoryEntity;
import com.proper.uip.api.bpm.extension.BpmTasksByCategoryExtension;
import com.proper.uip.bpm.definitions.entity.DefinitionsCategoryNode;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;


public interface BpmDefinitionsCategoryService {

	List<DefinitionsCategoryNode> buildTree();

	ProcessDefinitionsCategoryEntity getById(String parentExtendId);

	void save(ProcessDefinitionsCategoryEntity processDefinitionsCategoryEntity);

	Page<ProcessDefinitionsCategoryEntity> getDefinitionsCategory(PageConfig pageConfig, String parent, String name);

	List<ProcessDefinitionsCategoryEntity> getChildren(String id);

	void deleteChild(String id);

	void deleteById(String id);

	ProcessDefinitionsCategoryEntity getBySequence(int parseInt,String parentExtendId);

	ProcessDefinitionsCategoryEntity validateName(String name);

	ProcessDefinitionsCategoryEntity validateCode(String code);
	
	
	/**
	 * 获取自分类列表
	 * @param code
	 * @return
	 */
	List<ProcessDefinitionsCategoryEntity> getSubcategoryList(String code);
	
	/**
	 * 获取流程定义分类树中所有叶子分类（事项）
	 * @param code
	 * @return
	 */
	List<ProcessDefinitionsCategoryEntity> getLeafCategoryList();
	
	
	/**
	 * 获取流程定义分类子树中所有叶子分类（事项）
	 * @param code
	 * @return
	 */
	List<ProcessDefinitionsCategoryEntity> getLeafCategoryList(String code);
	
	/**
	 * 获取任务处理扩展点
	 * @param code
	 * @return
	 */
	BpmTasksByCategoryExtension	getTasksByCategoryExtension(String processDefinitionCategoryCode);

}
