package com.proper.uip.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.desktop.entity.ApplicationAndGroup;
import com.proper.uip.api.desktop.entity.ApplicationAndGroupTreeNode;
import com.proper.uip.api.desktop.service.ApplicationAndGroupService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.dao.ApplicationAndGroupDao;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class ApplicationAndGroupServiceImpl implements
		ApplicationAndGroupService {

	@Autowired
	private ApplicationAndGroupDao applicationAndGroupDao;

	@Override
	public List<ApplicationAndGroupTreeNode> buildTree(String systemCategory) {
		// 把所有节点变成node
		List<ApplicationAndGroup> applicationAndGroupList = applicationAndGroupDao
				.findBy("systemCategory", systemCategory);

		List<ApplicationAndGroupTreeNode> applicationAndGroupTreeNodeList = new ArrayList<ApplicationAndGroupTreeNode>();

		ApplicationAndGroupTreeNode applicationAndGroupTreeNode = null;
		int id = 0;
		int parentId = -1;
		boolean checked = false;
		boolean chkDisabled = true;
		// key applicationAndGroup.id value = applicationAndGroupTreeNode
		Map<String, ApplicationAndGroupTreeNode> nodeMap = new HashMap<String, ApplicationAndGroupTreeNode>();
		for (ApplicationAndGroup applicationAndGroup : applicationAndGroupList) {
			id = id + 1;
			applicationAndGroupTreeNode = new ApplicationAndGroupTreeNode(
					applicationAndGroup, checked, chkDisabled, id, parentId);
			nodeMap.put(applicationAndGroup.getId(),
					applicationAndGroupTreeNode);

			applicationAndGroupTreeNodeList.add(applicationAndGroupTreeNode);
		}
		// 根据node来找parentId
		for (ApplicationAndGroupTreeNode node : applicationAndGroupTreeNodeList) {
			if (node.getParentExtendId() == null) {
				continue;
			}
			if (nodeMap.containsKey(node.getParentExtendId()) == false) {
				continue;
			}
			node.setParentId(nodeMap.get(node.getParentExtendId()).getId());
		}

		return applicationAndGroupTreeNodeList;
	}

	@Override
	public ApplicationAndGroup getBySequenceNumber(int sequenceNumber) {
		ApplicationAndGroup applicationAndGroup = applicationAndGroupDao
				.findUniqueBy("sequenceNumber", sequenceNumber);
		return applicationAndGroup;
	}

	@Override
	public String getChildMocByMoc(String moc,String resourceId) {
		if (moc == null) {
			return "paging";
		}
		if (moc.equals("paging")) {
			if(resourceId != null){
				return "resource";
			}else{
				return "group";
			}
			
		}
		if (moc.equals("group")) {
			return "resource";
		}

		return "";
	}

	@Override
	public void saveApplicationAndGroup(
			ApplicationAndGroup applicationAndGroupEntity) {
		applicationAndGroupDao.save(applicationAndGroupEntity);
	}

	@Override
	public Page<ApplicationAndGroup> getApplicationAndGroup(
			PageConfig pageConfig, String parent, String systemCategory,
			String name) {
		if (name == null) {
			String hql = "select c from ApplicationAndGroup c where c.parent = ? and c.systemCategory = ? order by c.sequenceNumber asc";
			Page<ApplicationAndGroup> applicationAndGroup = applicationAndGroupDao
					.findPage(pageConfig, hql, parent, systemCategory);
			return applicationAndGroup;
		}
		String hql = "select c from ApplicationAndGroup c where c.parent = ? and c.systemCategory = ? and c.name like ? order by c.sequenceNumber asc";
		String nameLike = "%" + name + "%";

		Page<ApplicationAndGroup> applicationAndGroup = applicationAndGroupDao
				.findPage(pageConfig, hql, parent, systemCategory, nameLike);
		return applicationAndGroup;
	}

	@Override
	public ApplicationAndGroup getByParent(String parent, String systemCategory) {
		String hql = "select c from ApplicationAndGroup c where c.id = ? and c.systemCategory = ?";

		ApplicationAndGroup applicationAndGroup = applicationAndGroupDao
				.findUnique(hql, parent, systemCategory);
		return applicationAndGroup;
	}

	@Override
	public ApplicationAndGroup getBId(String parentExtendId) {
		ApplicationAndGroup applicationAndGroup = applicationAndGroupDao
				.get(parentExtendId);
		return applicationAndGroup;
	}

	@Override
	public Page<ApplicationAndGroup> getPaging(PageConfig pageConfig,
			String moc, String systemCategory, String name) {
		if (name == null) {
			String hql = "select c from ApplicationAndGroup c where c.moc = ? and c.systemCategory = ? order by c.sequenceNumber asc";
			Page<ApplicationAndGroup> applicationAndGroup = applicationAndGroupDao
					.findPage(pageConfig, hql, moc, systemCategory);
			return applicationAndGroup;
		}
		String hql = "select c from ApplicationAndGroup c where c.moc = ? and c.systemCategory = ? and c.name like ? order by c.sequenceNumber asc";
		String nameLike = "%" + name + "%";
		Page<ApplicationAndGroup> applicationAndGroup = applicationAndGroupDao
				.findPage(pageConfig, hql, moc, systemCategory, nameLike);
		return applicationAndGroup;

	}

	@Override
	public void deleteById(String id) {
		List<ApplicationAndGroup> applicationAndGroupList = applicationAndGroupDao.findBy("parent", id);
		for (ApplicationAndGroup applicationAndGroup : applicationAndGroupList) {
			if(applicationAndGroup == null){
				continue;
			}
			deleteById(applicationAndGroup.getId());
		}
		applicationAndGroupDao.delete(id);
	}
	
	
	public List<ApplicationAndGroup> getApplication(String systemCategory,String moc){
		String hql = "select c from ApplicationAndGroup c where c.moc = ? and c.systemCategory = ?  order by c.sequenceNumber asc";
		
		List<ApplicationAndGroup> applicationAndGroupList = applicationAndGroupDao.find(hql,moc, systemCategory);
		return applicationAndGroupList;
	}

	@Override
	public ApplicationAndGroup getByParentAndSesequenceNumber(String parent,
			int sequenceNumber,String systemCategory,String moc) {
		String hql = "select c from ApplicationAndGroup c where c.parent = ? and c.systemCategory = ? and c.sequenceNumber = ?";
		if(parent == null || parent.equals("")){
			hql = "select c from ApplicationAndGroup c where c.parent is null and c.systemCategory = ? and c.sequenceNumber = ? and c.moc =?";
			ApplicationAndGroup applicationAndGroup = applicationAndGroupDao
					.findUnique(hql, systemCategory,sequenceNumber,moc);
			return applicationAndGroup;
		}
		

		ApplicationAndGroup applicationAndGroup = applicationAndGroupDao
				.findUnique(hql, parent, systemCategory,sequenceNumber);
		return applicationAndGroup;
	}

}
