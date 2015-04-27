/* <p>文件名称: ProcessDefinitionCategoryService.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-12-27</p>
 * <p>完成日期：2013-12-27</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-12-27上午11:26:51
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.api.bpm.service;

import java.util.List;
import java.util.Map;

import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionManagementEntity;
import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionsCategoryEntity;
import com.proper.uip.api.bpm.extension.BpmTasksByCategoryExtension;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;


public interface ProcessDefinitionCategoryService {
	/**
	 * 查询流程定义分类分页数据
	 * @param pageConfig
	 * @param parentId
	 * @param name
	 * @return
	 */
	Page<ProcessDefinitionsCategoryEntity> getDefinitionCategoryPage(PageConfig pageConfig, String parentId, String name);
	
	/**
	 * 获取流程定义分类
	 * @param id
	 * @return
	 */
	ProcessDefinitionsCategoryEntity getById(String id);
	
	/**
	 * 获取子流程定义分类列表
	 * @param id
	 * @return
	 */
	List<ProcessDefinitionsCategoryEntity> getChildren(String id);
	
	
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
	
	/**
	 * 获取流程定义Map
	 * Key:流程定义Id
	 * Value:流程定义管理对象
	 * @param processDefinitionCategoryCode
	 * @return
	 */
	Map<String, ProcessDefinitionManagementEntity> getProcessDefinitionMapByCategoryCode(List<String> processDefinitionCategoryCodeList);
	
	/**
	 * 获取流程定义列表
	 * @param processDefinitionCategoryCode
	 * @return
	 */
	List<ProcessDefinitionManagementEntity> getProcessDefinitionListByCategoryCode(List<String> processDefinitionCategoryCodeList);

}

