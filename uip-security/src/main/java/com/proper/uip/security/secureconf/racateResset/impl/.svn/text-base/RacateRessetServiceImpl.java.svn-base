package com.proper.uip.security.secureconf.racateResset.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.security.entity.RaCategory;
import com.proper.uip.api.security.entity.RacateResset;
import com.proper.uip.api.security.entity.ResourceSet;
import com.proper.uip.api.security.service.RaCategoryService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.dao.RacateRessetDao;
import com.proper.uip.security.dao.ResourceSetDao;
import com.proper.uip.security.secureconf.racateResset.service.RacateRessetService;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class RacateRessetServiceImpl implements RacateRessetService {
	@Autowired
	private RacateRessetDao racateRessetDao;

	@Autowired
	private RaCategoryService raCategoryService;
	
	@Autowired
	private ResourceSetDao resourceSetDao;
	
	@Override
	public void save(String raCategoryId, String resourceSetId) {
		List<RaCategory> raCategoryList = raCategoryService.getAllRaCategorys();
		ResourceSet resourceSet = resourceSetDao.findUniqueBy("code", resourceSetId);
		
		List<RacateResset> racateRessetList = new ArrayList<RacateResset>();
		RacateResset racateResset = null;
		
		for (RaCategory raCategory : raCategoryList) {
			racateResset = new RacateResset();
			
			racateResset.setId(null);
			racateResset.setRaCategoryCode(raCategory.getCode());
			racateResset.setRaCategoryId(raCategory.getId());
			racateResset.setRaCategoryName(raCategory.getName());
			racateResset.setResourceSetCode(resourceSet.getCode());
			racateResset.setResourceSetId(resourceSet.getId());
			racateResset.setResourceSetName(resourceSet.getName());
			
			racateRessetList.add(racateResset);
		}
		racateRessetDao.save(racateRessetList);
	}

	@Override
	public Page<RacateResset> findAllResourceSet(PageConfig pageConfig,
			String raCategoryId) {
		String hql = "select r from RacateResset r where r.raCategoryId = ?";
		Page<RacateResset> racateResset = racateRessetDao.findPage(pageConfig, hql, raCategoryId);
		return racateResset;
	}

	@Override
	public void del(String resourceSetIds, String raCategoryId) {
		Criteria criteria = racateRessetDao.getSession().createCriteria(RacateResset.class);
		
		criteria.add(Restrictions.in("resourceSetId", resourceSetIds.split(",")));
		criteria.add(Restrictions.eq("raCategoryId", raCategoryId));
		
		@SuppressWarnings("unchecked")
		List<RacateResset> racateRessetList = criteria.list();
		
		racateRessetDao.delete(racateRessetList);
		
	}

}
