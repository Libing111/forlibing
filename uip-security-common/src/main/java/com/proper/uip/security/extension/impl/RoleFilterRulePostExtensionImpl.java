package com.proper.uip.security.extension.impl;


import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.proper.hr.personnel.entity.Person;
import com.proper.hr.personnel.entity.UserPositionInfo;
import com.proper.hr.personnel.service.IPersonQueryService;
import com.proper.hr.personnel.service.IUserPositionService;
import com.proper.uip.api.security.entity.RoleRuleRelation;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.extension.RoleFilterRuleExtension;
import com.proper.uip.security.dao.RoleRuleRelationDao;
import com.proper.uip.security.dao.UsersDao;

public class RoleFilterRulePostExtensionImpl extends RoleFilterRuleExtension {
	
	@Autowired
	private UsersDao userDao;
	
	@Autowired
	private IPersonQueryService personQueryService;
	
	@Autowired
	private IUserPositionService userPositionService;
	
	@Autowired
	private RoleRuleRelationDao roleRuleRelationDao;
	
	
	@Override
	public boolean checkUser(String userId, String roleId) {
		String ruleId = this.getId();
		User user = this.getCurrentUser();
		String extendId = user.getExtendId();
		if(extendId == null || extendId.equals("")){
			return false;
		}
		Person person = personQueryService.getPersonId(user.getExtendId());
		if(person == null){
			return false;
		}
		List<UserPositionInfo> userPositionlist = userPositionService.getPersonUserPositionInfoByDates(user,person.getPersonId(),new Date(),new Date());
		Criteria criteria = this.roleRuleRelationDao.getSession().createCriteria(RoleRuleRelation.class);
		criteria.add(Restrictions.eq("ruleId", ruleId))
				.add(Restrictions.eq("roleId", roleId)); 
		RoleRuleRelation roleRuleRelation = (RoleRuleRelation) criteria.uniqueResult();
		
		if(roleRuleRelation == null){
			return false;
		}
		String ruleKey = roleRuleRelation.getRuleKey();
		Set<String> ruleKeySet = new HashSet<String>();
		ruleKeySet.addAll(Arrays.asList(ruleKey.split(",")));
		
		for (UserPositionInfo userPositionInfo : userPositionlist) {
			if(ruleKeySet.contains(userPositionInfo.getJobId())){
				return true;
			}
		}
		
		return false;
	
	
	}

	@Override
	public boolean checkDeparment(String departmentId, String roleId) {
		return false;
	}
	protected User getCurrentUser(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(principal instanceof org.springframework.security.core.userdetails.User == false){
			return null;
		}
		
		org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) principal;   
		String userName = springUser.getUsername();   
		
		User user = userDao.findUniqueBy("account", userName);
		
		return user;
	}

	
}
