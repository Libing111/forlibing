package com.proper.uip.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.security.entity.RoleUserRelation;
import com.proper.uip.api.security.service.RoleUserRelationService;
import com.proper.uip.security.dao.RoleUserRelationDao;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class RoleUserRelationServiceImpl implements RoleUserRelationService{

	@Autowired
	private RoleUserRelationDao roleUserRelationDao;
	
	@Override
	public List<RoleUserRelation> getRoleId(String id) {
		String sql = "select c from RoleUserRelation c where c.roleId = ?";
		List<RoleUserRelation> uName = roleUserRelationDao.find(sql, id);
		return uName;
	}
	
	@Override
	public List<RoleUserRelation> getUserId(String id) {
		String sql = "select c from RoleUserRelation c where c.userId = ?";
		List<RoleUserRelation> uName = roleUserRelationDao.find(sql, id);
		return uName;
	}
}
