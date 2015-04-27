package com.proper.uip.security.extension.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.proper.hr.personnel.entity.BasicPerson;
import com.proper.hr.personnel.service.IPersonQueryService;
import com.proper.uip.api.security.entity.RoleRuleRelation;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.extension.RoleFilterRuleExtension;
import com.proper.uip.security.dao.RoleRuleRelationDao;
import com.proper.uip.security.dao.UsersDao;

public class RoleFilterRuleDeparmentExtensionImpl extends RoleFilterRuleExtension {

	@Autowired
	private RoleRuleRelationDao roleRuleRelationDao;
	
	@Autowired
	private UsersDao userDao;
	
	@Autowired
	private IPersonQueryService personQueryService;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkUser(String userId, String roleId) {
		User user = this.getCurrentUser();
		String extendId = user.getExtendId();
		if(extendId == null || extendId.equals("")){
			return false;
		}
		String ruleId = this.getId();
		Criteria criteria = this.roleRuleRelationDao.getSession().createCriteria(RoleRuleRelation.class);
		criteria.add(Restrictions.eq("ruleId", ruleId))
				.add(Restrictions.eq("roleId", roleId));
		RoleRuleRelation roleRuleRelation = (RoleRuleRelation) criteria.uniqueResult();
		if(roleRuleRelation == null){
			return false;
		}
		String ruleKey = roleRuleRelation.getRuleKey();
		if(ruleKey == null || ruleKey.equals("")){
			return false;
		}
		List<String> departmentIdList = Arrays.asList(ruleKey.split(","));

		List<BasicPerson> personList = null;
		for (String departmentId : departmentIdList) {
			personList =  (List<BasicPerson>) personQueryService.getOrgLeader(user, departmentId, new Date(), new Date());
			for (BasicPerson basicPerson : personList) {
				if(basicPerson.getPersonId().equals(extendId)){
					return true;
				}
			}
		
		}
		
		return false;
	}

	@Override
	public boolean checkDeparment(String departmentId, String roleId) {
		String ruleId = this.getId();
		Criteria criteria = this.roleRuleRelationDao.getSession().createCriteria(RoleRuleRelation.class);
		criteria.add(Restrictions.eq("ruleId", ruleId))
				.add(Restrictions.eq("roleId", roleId))
				.add(Restrictions.like("ruleKey", departmentId));
		
		@SuppressWarnings("unchecked")
		List<RoleRuleRelation> roleRuleRelationList = criteria.list();
		if(roleRuleRelationList.isEmpty() == false){
			return true;
		}
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
