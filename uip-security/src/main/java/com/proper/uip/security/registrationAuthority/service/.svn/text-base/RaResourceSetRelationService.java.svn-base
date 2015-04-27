package com.proper.uip.security.registrationAuthority.service;

import java.util.List;

import com.proper.uip.api.security.entity.RaResourceSetRelation;
import com.proper.uip.api.security.entity.ResourceResourceSetRelation;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;

public interface RaResourceSetRelationService {

	void saveraResourceSet(String raId, String resourceSetId);

	Page<RaResourceSetRelation> findAllRaResourceSet(PageConfig pageConfig,
			String raId);
	
	void delete(String id);
	
	List<RaResourceSetRelation> getByResourceSetId(String resourceSetId);

	RaResourceSetRelation getByRaIdAndResourceId(String raId,
			String resourceSetId);

}
