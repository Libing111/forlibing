package com.proper.uip.bpm.definitions.service;

import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionOrganizationRelation;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;



public interface processDefinitionOrganizationRelationService {

	Page<ProcessDefinitionOrganizationRelation> findAllOrg(PageConfig pageConfig, String organizationName, String resourceName,
			String processDefinitionName);

	ProcessDefinitionOrganizationRelation saveOrganization(ProcessDefinitionOrganizationRelation organizationEntity);

	ProcessDefinitionOrganizationRelation getById(String id);

	void deleteById(String id);

}
