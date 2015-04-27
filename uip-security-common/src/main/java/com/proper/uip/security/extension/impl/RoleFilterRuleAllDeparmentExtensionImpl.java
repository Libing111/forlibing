package com.proper.uip.security.extension.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.proper.hr.personnel.entity.BasicPerson;
import com.proper.hr.personnel.service.IPersonQueryService;
import com.proper.uip.api.security.entity.Role;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.extension.RoleFilterRuleExtension;
import com.proper.uip.security.dao.RolesDao;
import com.proper.uip.security.dao.UsersDao;

public class RoleFilterRuleAllDeparmentExtensionImpl extends RoleFilterRuleExtension {
	@Autowired
	private RolesDao roleDao;
	
	@Autowired
	private UsersDao userDao;
	
	@Autowired
	private IPersonQueryService personQueryService;
	
	@Override
	public boolean checkUser(String userId, String roleId) {
		User user = this.getCurrentUser();
		String extendId = user.getExtendId();
		if(extendId == null || extendId.equals("")){
			return false;
		}
		
		//判断这个人是不是科室负责人（是：return true）
		boolean falg = personQueryService.checkPersonIsLeader(user, extendId);
		if(falg){
			return true;
		}
		return false;
	}

	@Override
	public boolean checkDeparment(String departmentId, String roleId) {
			return true;
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
