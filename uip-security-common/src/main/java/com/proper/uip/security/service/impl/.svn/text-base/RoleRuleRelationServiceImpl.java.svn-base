package com.proper.uip.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.security.entity.RoleRuleRelation;
import com.proper.uip.api.security.service.RoleRuleRelationService;
import com.proper.uip.security.dao.RoleRuleRelationDao;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class RoleRuleRelationServiceImpl implements RoleRuleRelationService{
	@Autowired
	private RoleRuleRelationDao roleRuleRelationDao;

	@Override
	public RoleRuleRelation getRoleRuleRelationByRoleId(String roleId) {
		RoleRuleRelation roleRuleRelation = roleRuleRelationDao.findUniqueBy("roleId", roleId);
		return roleRuleRelation;
	}

	@Override
	public List<RoleRuleRelation> getAll() {
		List<RoleRuleRelation> roleRuleRelationList = roleRuleRelationDao.getAll();

		return roleRuleRelationList;
	}
}
