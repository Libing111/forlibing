package com.proper.uip.extension.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.proper.hr.personnel.entity.UserPositionInfo;
import com.proper.hr.personnel.service.IUserPositionNoTransactionalService;
import com.proper.hr.personnel.service.IUserPositionService;
import com.proper.uip.api.bpm.extension.BpmFilterRuleExtension;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.security.dao.UsersDao;

public class BpmFilterRuleByOrgExtension extends BpmFilterRuleExtension {

	@Autowired
	private IUserPositionNoTransactionalService userPositionService;

	@Autowired
	private UsersDao usersDao;
	
	@Override
	public Set<String> parse(Set<String> personIdSet, String initiatorId) {
		//根据流程发起人的personId查询其职位信息
		List<UserPositionInfo> userPositionInfoList = userPositionService.getPersonUserPositionInfoByDates(getCurrentUser(), initiatorId, new Date(), new Date(), null);

//		List<UserPositionInfo> userPositionInfoList = userPositionService.getPersonUserPositionInfoByDates(usersDao.findUniqueBy("extendId", initiatorId), initiatorId, new Date(), new Date());

		//用于最终返回的筛选后的本部门personId
		Set<String> finalSet = new HashSet<String>();
		//用于存放流程发起人所在的机构Id
		Set<String> orgIdSet = new HashSet<String>();
		//用于存放流程发起人所在机构的所有personID
		Set<String > orgPersonIdSet = new HashSet<String>();
		//根据流程发起人的职位信息查询出所有机构ID
		for(UserPositionInfo userPositionInfo:userPositionInfoList){
			orgIdSet.add(userPositionInfo.getOrgId());
		}
		//根据机构Id查询出机构下的所有personId
		List<UserPositionInfo> userPositionInfoByOrgIdsList = userPositionService.getOrganizationUserPositionInfoByDates(getCurrentUser(), orgIdSet, new Date(), new Date());
		for(UserPositionInfo userPositionInfo:userPositionInfoByOrgIdsList){
			orgPersonIdSet.add(userPositionInfo.getUserPosition().getPersonId());
		}
		//筛选
		for(String orgPersonId:orgPersonIdSet){
			if(personIdSet.contains(orgPersonId)){
				finalSet.add(orgPersonId);
			}
		}
		return finalSet;
	}


	protected User getCurrentUser(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(principal instanceof org.springframework.security.core.userdetails.User == false){
			return null;
		}
		
		org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) principal;   
		String userName = springUser.getUsername();   
		
		//User user = userService.getUserByLoginName(userName);
		User user = usersDao.findUniqueBy("account", userName);
//		User user = userService.getUserByAccount(userName);
		
		return user;
	}
}
