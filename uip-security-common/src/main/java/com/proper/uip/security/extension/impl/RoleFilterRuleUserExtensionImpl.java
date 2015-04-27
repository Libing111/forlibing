package com.proper.uip.security.extension.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.proper.uip.api.security.entity.RoleRuleRelation;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.extension.RoleFilterRuleExtension;
import com.proper.uip.security.dao.RoleRuleRelationDao;
import com.proper.uip.security.dao.UsersDao;

public class RoleFilterRuleUserExtensionImpl extends RoleFilterRuleExtension {
	@Autowired
	private RoleRuleRelationDao roleRuleRelationDao;
	
	@Autowired
	private UsersDao usersDao;
	
	@Override
	public boolean checkUser(String userId, String roleId) {
		String ruleId = this.getId();
		User user = usersDao.findUniqueBy("id", userId);
		//账号也有此权限
		if(user.getExtendId() == null || user.getExtendId().trim().isEmpty()){
			return false;
		}
		String searchTextMode;
		Criteria criteria = this.roleRuleRelationDao.getSession().createCriteria(RoleRuleRelation.class);
		criteria.add(Restrictions.eq("ruleId", ruleId))
				.add(Restrictions.eq("roleId", roleId));
		if(user.getExtendId() != null && user.getExtendId().trim().isEmpty() == false){
			searchTextMode = "%" + user.getExtendId() + "%";
			criteria.add(Restrictions.like("ruleKey",searchTextMode));
		}
		
		@SuppressWarnings("unchecked")
		List<RoleRuleRelation> roleRuleRelationList = criteria.list();
		if(roleRuleRelationList.isEmpty() == false){
			return true;
		}
		return false;
	}

	@Override
	public boolean checkDeparment(String departmentId, String roleId) {
		return false;
	}

	
}
