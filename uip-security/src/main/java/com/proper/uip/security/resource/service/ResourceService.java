package com.proper.uip.security.resource.service;

import java.util.List;

import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.ResourceTreeNode;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;

public interface ResourceService {
	Page<Resource> findAllResource(PageConfig config);

	
	Resource saveResource(Resource resourceEntity);

	Resource getResourceById(String id);

	void deleteResourceById(String id);

	List<Resource> getall();

	Resource getUserByName(String name);

	String getName(String resourceId);

	List<ResourceTreeNode> buildResourceTree();

	Resource getResourceByCode(String code);

	Page<Resource> getResourcesOfModel(PageConfig pageConfig, String parentId, String name, String categoryName);

	Resource getResourceByParent(String parent);
	
	String getChildResourceMocByMoc(String moc);

	Resource getResource(int sequenceNumber,String parent);

	Resource getCode(String code);

	List<Resource> getByCategoryId(String resourceCategoryId);

	Resource getByName(String name);

}
