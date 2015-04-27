package com.proper.uip.security.resourceSet.service;

import java.util.List;

import com.proper.uip.api.security.entity.ResourceResourceSetRelation;

public interface ResourceResourceSetRelationService {

	
	void updateTree(String resourcesetId, String resourceSequenceNumbers);

	void updateRoleResource();
	
	void deleteRoleResource(String resourceSetId);
	
	void deleteRelation(String resourceSetId);
	void updateResourceResourceSetRelations(String resourcesetId,
			String resourceSequenceNumbers);
	List<ResourceResourceSetRelation> getByResourceSetId(String resourceSetId);
	
}
