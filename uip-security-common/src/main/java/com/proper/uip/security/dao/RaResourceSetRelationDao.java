package com.proper.uip.security.dao;

import org.springframework.stereotype.Repository;

import com.proper.uip.common.core.dao.HibernateDao;
import com.proper.uip.api.security.entity.RaResourceSetRelation;

@Repository("raResourceSetRelationDao")
public class RaResourceSetRelationDao extends HibernateDao<RaResourceSetRelation, String>{

}
