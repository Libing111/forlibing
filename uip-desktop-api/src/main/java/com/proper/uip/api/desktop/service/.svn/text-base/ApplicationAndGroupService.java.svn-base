package com.proper.uip.api.desktop.service;

import java.util.List;

import com.proper.uip.api.desktop.entity.ApplicationAndGroup;
import com.proper.uip.api.desktop.entity.ApplicationAndGroupTreeNode;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;


public interface ApplicationAndGroupService {


	List<ApplicationAndGroupTreeNode> buildTree(String systemCategory);
	/**
	 * 构造页面元素树（分屏、栏目和应用）
	 * 用于桌面设置
	 * @return
	 */
	//List<ApplicationAndGroupTreeNode> buildTree();


	/**
	 * 获取页面元素
	 * @param parentSequenceNumber
	 * @return
	 */
	ApplicationAndGroup getBySequenceNumber(int parentSequenceNumber);
	
	/**
	 * 获取页面元素
	 * @param parent
	 * @param parentSequenceNumber
	 * @return
	 */
	ApplicationAndGroup getByParentAndSesequenceNumber(String parent, int sequenceNumber,String systemCategory,String moc);

	/**
	 * 通过Moc获取子Moc
	 * @param moc
	 * @return
	 */
	String getChildMocByMoc(String moc,String resourceId);

	/**
	 * 保存页面元素
	 * @param applicationAndGroupEntity
	 */
	void saveApplicationAndGroup(ApplicationAndGroup applicationAndGroupEntity);

	/**
	 * 获取页面元素分页数据
	 * @param pageConfig
	 * @param parent
	 * @return
	 */
	Page<ApplicationAndGroup> getApplicationAndGroup(PageConfig pageConfig,String parent,String systemCategory,String name);

	/**
	 * 获取父页面元素
	 * @param parent
	 * @return
	 */
	ApplicationAndGroup getByParent(String parent, String systemCategory);

	/**
	 * 获取页面元素
	 * @param parentExtendId
	 * @return
	 */
	ApplicationAndGroup getBId(String parentExtendId);

	/**
	 * 获取分屏分页数据
	 * @param pageConfig
	 * @param moc
	 * @return
	 */
	Page<ApplicationAndGroup> getPaging(PageConfig pageConfig, String moc,String systemCategory,String name);

	/**
	 * 删除页面元素（递归删除）
	 * @param id
	 */
	void deleteById(String id);
	
}
