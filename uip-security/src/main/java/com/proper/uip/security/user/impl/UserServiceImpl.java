package com.proper.uip.security.user.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.entity.Role;
import com.proper.uip.api.security.entity.RoleUserRelation;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.dao.RoleUserRelationDao;
import com.proper.uip.security.dao.RolesDao;
import com.proper.uip.security.dao.UsersDao;
import com.proper.uip.security.user.service.UserService;


@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserServiceImpl implements UserService{
	@Autowired
	private RoleUserRelationDao roleUserRelationDao;
	
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private RolesDao rolesDao;
	
	@Override
	public Page<User> findAllUser(PageConfig config, String name, String loginName, String raName, String raId) {
		Criteria criteria = createCriteria(name,loginName, raName, raId);
		
		Page<User> userPage = new Page<User>(config);

		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		userPage.setTotal(totalCount);
		
		criteria = createCriteria(name, loginName, raName,raId);


		int fromIndex = userPage.getOffset();
		if(fromIndex < 0){
			System.out.println("Error! fromIndex = " + fromIndex);
			System.out.println("Error! Class=" + this.getClass().getName());
			System.out.println("Error! fromIndex = " + fromIndex);
			fromIndex = 0;
		}
		criteria.setFirstResult(fromIndex);
		criteria.setMaxResults(userPage.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<User> modelList = criteria.list();
		userPage.setRows(modelList);
	
		return userPage;
	}

	private Criteria createCriteria(String name,String loginName,String raName, String raId) {
		Criteria criteria = usersDao.getSession().createCriteria(User.class);
		
		String searchTextMode;
		if(name != null && name.trim().isEmpty() == false){
			searchTextMode = "%" + name + "%";
			criteria.add(Restrictions.like("name",searchTextMode));
		}
		if(loginName != null && loginName.trim().isEmpty() == false){
			searchTextMode = "%" + loginName + "%";
			criteria.add(Restrictions.like("loginName",searchTextMode));
		}
		if(raName != null && raName.trim().isEmpty() == false){
			searchTextMode = "%" + raName + "%";
			criteria.add(Restrictions.like("raName",searchTextMode));
		}
		if(raId != null && raId.trim().isEmpty() == false){
			criteria.add(Restrictions.eq("raId", raId));
		}
		return criteria;
	}
	
	
	@Override
	public void saveUser(User userEntity) {
		usersDao.save(userEntity);		
	}

	@Override
	public User getUserById(String id) {
		return usersDao.get(id);
	}

	@Override
	public void deleteUserById(String id) {
		usersDao.delete(id);		
	}


	@Override
	public List<User> getAll() {
		return usersDao.getAll();
	}


	@Override
	public User getUserByLoginName(String loginName) {
		String sql = "select c from User c where c.loginName = ?";
		User uName = usersDao.findUnique(sql, loginName);
		return uName;
	}


	@Override
	public void saveRole(String selectIds, String userId) {
		User user = this.getUserById(userId);
		
		//String[] roleArray = selectIds.split(",");
		
		String roleIds = selectIds.replaceAll(",", "','");
		roleIds = "'" + roleIds + "'";
		
		/**
		 * select r 
		 * from Role r 
		 * where r.id in (?) 
		 *   and not exists (select 1 
		 *                   from RoleUserRelation ru 
		 *                   where r.id=ru.roleId 
		 *                     and ru.userId=?)"
		 */
		StringBuffer hql = new StringBuffer("select r from Role r where").append(" r.id in")
																		 .append("(")
				                                                         .append(roleIds)
				                                                         .append(" )")
				                                                         .append(" and id not in")
				                                                         .append(" (")
				                                                         .append(" select roleId from RoleUserRelation ru")
				                                                         .append(" where")
//				                                                         .append(" r.id=ru.roleId")
//				                                                         .append(" and")
				                                                         .append(" ru.userId=?")
				                                                         .append(" )");
		List<Role> roleList = rolesDao.find(hql.toString(), userId);
		List<RoleUserRelation>  roleUserRelationList =  new ArrayList<RoleUserRelation>();
		RoleUserRelation roleUserRelationEntity = null;
		for (Role roleEntity : roleList) {
			roleUserRelationEntity = new RoleUserRelation();
			
			roleUserRelationEntity.setUseName(user.getName());
			roleUserRelationEntity.setId(null);
			roleUserRelationEntity.setLoginName(user.getLoginName());
			roleUserRelationEntity.setUserId(user.getId());
			roleUserRelationEntity.setRoleId(roleEntity.getId());
			roleUserRelationEntity.setRoleName(roleEntity.getName());
			
			roleUserRelationList.add(roleUserRelationEntity);
			
		}
		
		roleUserRelationDao.save(roleUserRelationList);		
	}


	@Override
	public Page<Role> getRole(PageConfig pageConfig, String userId) {
		String sql = "select r from RoleUserRelation ru, Role r where ru.userId = ? and ru.roleId = r.id";
		Page<Role> roleList = rolesDao.findPage(pageConfig, sql,userId);
		return roleList;
	}


	@Override
	public void delRoleUserRelationById(String id) {
		roleUserRelationDao.delete(id);		
	}

	@Override
	public User getLogin(String account) {
		String sql = "select c from User c where c.account = ? ";
		User user = usersDao.findUnique(sql, account);
		return user;
	}

	@Override
	public void delRoleUserRelation(String roleIds, String userId) {
		Criteria criteria = roleUserRelationDao.getSession().createCriteria(RoleUserRelation.class);
		
		criteria.add(Restrictions.in("roleId", roleIds.split(",")));
		criteria.add(Restrictions.eq("userId", userId));
		
		@SuppressWarnings("unchecked")
		List<RoleUserRelation> relationList = criteria.list();
		
		roleUserRelationDao.delete(relationList);
	}

	@Override
	public Page<User> findAll(PageConfig pageConfig) {
		String sql = "select c from User c";
		Page<User> userList = usersDao.findAllPage(pageConfig, sql);;
		return userList;
	}

	@Override
	public Page<User> findAll(PageConfig pageConfig, String name,String loginName, String raName) {
		Criteria criteria = createCriteria(name,loginName, raName);
		

		Page<User> userPage = new Page<User>(pageConfig);

		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		userPage.setTotal(totalCount);
		
		criteria = createCriteria(name, loginName,raName);


		int fromIndex = userPage.getOffset();
		if(fromIndex < 0){
			System.out.println("Error! fromIndex = " + fromIndex);
			System.out.println("Error! Class=" + this.getClass().getName());
			System.out.println("Error! fromIndex = " + fromIndex);
			fromIndex = 0;
		}
		criteria.setFirstResult(fromIndex);
		criteria.setMaxResults(userPage.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<User> modelList = criteria.list();
		userPage.setRows(modelList);
	
		return userPage;
	}
	
	private Criteria createCriteria(String name,String loginName,String raName) {
		Criteria criteria = usersDao.getSession().createCriteria(User.class);
		
		String searchTextMode;
		if(name != null && name.trim().isEmpty() == false){
			searchTextMode = "%" + name + "%";
			criteria.add(Restrictions.like("name",searchTextMode));
		}
		if(loginName != null && loginName.trim().isEmpty() == false){
			searchTextMode = "%" + loginName + "%";
			criteria.add(Restrictions.like("loginName",searchTextMode));
		}
		if(raName != null && raName.trim().isEmpty() == false){
			searchTextMode = "%" + raName + "%";
			criteria.add(Restrictions.like("raName",searchTextMode));
		}
		
		return criteria;
	}

	@Override
	public List<User> getByCategoryId(String userCategoryId) {
		List<User> userList = usersDao.findBy("categoryId", userCategoryId);
		return userList;
	}
	
	@Override
	public User getByExtendId(String extendId) {
		User user = this.usersDao.findUniqueBy("extendId", extendId);
		return user;
	}

}
