package com.proper.uip.security.registrationAuthority.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.security.entity.RaResourceSetRelation;
import com.proper.uip.api.security.entity.RegistrationAuthority;
import com.proper.uip.api.security.entity.ResourceSet;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.dao.RaResourceSetRelationDao;
import com.proper.uip.security.dao.RegistrationAuthorityDao;
import com.proper.uip.security.dao.ResourceSetDao;
import com.proper.uip.security.registrationAuthority.service.RaResourceSetRelationService;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class RaResourceSetRelationServiceImpl implements
		RaResourceSetRelationService {
	@Autowired
	private RegistrationAuthorityDao registrationAuthorityDao;
	
	@Autowired
	private ResourceSetDao resourceSetDao;
	
	@Autowired
	private RaResourceSetRelationDao raResourceSetRelationDao;
	
	@Override
	public void saveraResourceSet(String raId, String resourceSetId) {
		List<RegistrationAuthority> raList = registrationAuthorityDao.findBy("id", raId);
		ResourceSet resourceSet = resourceSetDao.findUniqueBy("code", resourceSetId);
		
		List<RaResourceSetRelation> raResourceSetRelationList = new ArrayList<RaResourceSetRelation>();
		RaResourceSetRelation raResourceSetRelation = null;
		
		for (RegistrationAuthority ra : raList) {
			raResourceSetRelation = new RaResourceSetRelation(ra, resourceSet);
			raResourceSetRelationList.add(raResourceSetRelation);
		}
		raResourceSetRelationDao.save(raResourceSetRelationList);
	}

	@Override
	public Page<RaResourceSetRelation> findAllRaResourceSet(
			PageConfig pageConfig, String raId) {
		String hql = "select r from RaResourceSetRelation r where r.raId = ?";
		Page<RaResourceSetRelation> RaResourceSetRelation = raResourceSetRelationDao.findPage(pageConfig, hql, raId);
		return RaResourceSetRelation;
	}

	@Override
	public void delete(String id) {
		raResourceSetRelationDao.delete(id);		
	}

	@Override
	public List<RaResourceSetRelation> getByResourceSetId(String resourceSetId) {
		List<RaResourceSetRelation> raResourceSetRelationList = this.raResourceSetRelationDao.findBy("resourcesetId", resourceSetId);
		return raResourceSetRelationList;
	}

	@Override
	public RaResourceSetRelation getByRaIdAndResourceId(String raId,
			String resourceSetId) {
		String sql = "select r from RaResourceSetRelation r where r.raId = ? and r.resourcesetId = ?";
		ResourceSet resourceSet = resourceSetDao.findUniqueBy("code", resourceSetId);
		RaResourceSetRelation raResourceSetRelation = raResourceSetRelationDao.findUnique(sql,raId,resourceSet.getId());
		return raResourceSetRelation;
	}

}
