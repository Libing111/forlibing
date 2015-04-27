package com.proper.uip.bpm.definitions.service;

import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;

import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionManagementEntity;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;



public interface ProcessDefinitionManagementService {

	void getCreateProcessDef(List<ProcessDefinition> processDefinitionList);

	List<ProcessDefinitionManagementEntity> getAll();

	Page<ProcessDefinitionManagementEntity> getDefinitionsManagement(
			PageConfig pageConfig, String name,String categoryName);

	ProcessDefinitionManagementEntity getById(String id);

	void save(ProcessDefinitionManagementEntity processDefinitionManagementEntity);

	/**
	 * 获取流程定义列表（流程定义的分类是给定分类的叶子后继分类）
	 * @param categoryCode
	 * @return
	 */
	List<ProcessDefinitionManagementEntity> getByCategoryCode(String categoryCode);

	List<ProcessDefinitionManagementEntity> getByCategoryId(String id);

	void deleteByCategoryId(String id);

}
