package com.proper.uip.security.dao;

import org.springframework.stereotype.Repository;

import com.proper.uip.api.security.entity.ResourceResourceSetRelation;
import com.proper.uip.common.core.dao.HibernateDao;

@Repository("resourceResourceSetRelationDao")
public class ResourceResourceSetRelationDao extends HibernateDao<ResourceResourceSetRelation, String>{

}
