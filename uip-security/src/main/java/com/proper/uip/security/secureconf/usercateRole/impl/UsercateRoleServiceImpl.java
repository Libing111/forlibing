package com.proper.uip.security.secureconf.usercateRole.impl;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.security.entity.Role;
import com.proper.uip.api.security.entity.UserCategory;
import com.proper.uip.api.security.entity.UsercateRole;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.dao.RolesDao;
import com.proper.uip.security.dao.UsercateRoleDao;
import com.proper.uip.security.secureconf.usercateRole.service.UsercateRoleService;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UsercateRoleServiceImpl implements UsercateRoleService {
	@Autowired
	private UsercateRoleDao usercateRoleDao;
	
	@Autowired
	private RolesDao rolesDao;
	@Override
	public void save(String ids, UserCategory userCategory) {
		String roleIds = ids.replaceAll(",", "','");
		roleIds = "'" + roleIds + "'";
		
		StringBuffer hql = new StringBuffer("select r from Role r where").append(" r.id in")
																		 .append("(")
				                                                         .append(roleIds)
				                                                         .append(" )")
				                                                         .append(" and id not in")
				                                                         .append(" (")
				                                                         .append(" select roleId from UsercateRole ru")
				                                                         .append(" where")
				                                                         .append(" ru.userCategoryId=?")
				                                                         .append(" )");
		List<Role> roleList = rolesDao.find(hql.toString(), userCategory.getId());
		List<UsercateRole>  usercateRoleList =  new ArrayList<UsercateRole>();
		UsercateRole usercateRole = null;
		for (Role roleEntity : roleList) {
			usercateRole = new UsercateRole();
			
			usercateRole.setId(null);
			usercateRole.setRoleCode(roleEntity.getCode());
			usercateRole.setRoleId(roleEntity.getId());
			usercateRole.setRoleName(roleEntity.getName());
			usercateRole.setUserCategoryCode(userCategory.getCode());
			usercateRole.setUserCategoryId(userCategory.getId());
			usercateRole.setUserCategoryName(userCategory.getName());
			
			usercateRoleList.add(usercateRole);
			
		}
		
		usercateRoleDao.save(usercateRoleList);
	}

	@Override
	public Page<UsercateRole> findRole(PageConfig pageConfig,
			String userCategoryId) {
		String sql = "select r from UsercateRole ru, Role r where ru.userCategoryId = ? and ru.roleId = r.id";
		Page<UsercateRole> usercateRoleList = usercateRoleDao.findPage(pageConfig, sql,userCategoryId);
		return usercateRoleList;
	}

	@Override
	public void delRole(String roleIds, String userCategoryId) {
		Criteria criteria = usercateRoleDao.getSession().createCriteria(UsercateRole.class);
		
		criteria.add(Restrictions.in("roleId", roleIds.split(",")));
		criteria.add(Restrictions.eq("userCategoryId", userCategoryId));
		
		@SuppressWarnings("unchecked")
		List<UsercateRole> usercateRoleList = criteria.list();
		
		usercateRoleDao.delete(usercateRoleList);
	}

}
