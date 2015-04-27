/* <p>文件名称: UserServiceImpl.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-8-24</p>
 * <p>完成日期：2013-8-24</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-8-24 下午12:22:52
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.security.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.security.entity.RegistrationAuthority;
import com.proper.uip.api.security.entity.Role;
import com.proper.uip.api.security.entity.RoleUserRelation;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.entity.User.ActiveStatus;
import com.proper.uip.api.security.entity.User.UseStatus;
import com.proper.uip.api.security.entity.UserCategory;
import com.proper.uip.api.security.service.UserCategoryService;
import com.proper.uip.api.security.service.UserService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.dao.RegistrationAuthorityDao;
import com.proper.uip.security.dao.RoleUserRelationDao;
import com.proper.uip.security.dao.UsersDao;
import com.proper.uip.security.utils.PasswordEncodingUtil;

@Service("UserService_Public")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
public class UserServiceImpl implements UserService {
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private RoleUserRelationDao roleUserRelationDao;

	@Autowired
	private RegistrationAuthorityDao raDao;
	
	@Autowired
	private String defaultPassword;
	
	@Autowired
	private UserCategoryService userCategoryService;
	
	@Override
	public User buildUser(String userCategoryCode,
			String registrationAuthorityId, String defautAccountName,
			String name, String code, String extendId, Map<String, String> extendProperties) {
		boolean randomPassword = false;
		
		User user = this.buildUser(userCategoryCode, registrationAuthorityId, defautAccountName, name, code, extendId, extendProperties, randomPassword);
		
		return user;
	}
	
	@Override
	public User buildUser(String userCategoryCode,
			String registrationAuthorityId, String defautAccountName,
			String name, String code, String extendId,
			Map<String, String> extendProperties, boolean randomPassword) {
		if(defautAccountName == null || defautAccountName.trim().equals("")){
			return null;
		}
		if(name == null || name.trim().equals("")){
			return null;
		}
		if(code == null || code.trim().equals("")){
			return null;
		}
		
		
		UserCategory userCategory = userCategoryService.getByCode(userCategoryCode);
		if(userCategory == null){
			return null;
		}
		
		Criteria criteria = this.usersDao.getSession().createCriteria(User.class);
		criteria.add(Restrictions.like("loginName",code + "%"));
		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		String loginName = code;
		if(totalCount > 0){
			loginName = loginName + "_" + totalCount;
		}
		
		criteria = this.usersDao.getSession().createCriteria(User.class);
		criteria.add(Restrictions.like("account", defautAccountName + "%"));
		totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		String account = defautAccountName;
		if(totalCount > 0){
			account = account + "_" + totalCount;
		}
		
		PasswordEncodingUtil passwordEncodingUtil = new PasswordEncodingUtil(PasswordEncodingUtil.ENCODING_ALGORITHM_MD5);
		
		User user = new User();
		user.setActiveStatus(ActiveStatus.ACTIVE);
		user.setCategoryCode(userCategory.getCode());
		user.setCategoryName(userCategory.getName());
		user.setCode(code);

		user.setAccount(account);
		user.setLoginName(loginName);
		user.setName(name);
		user.setNeverExpired(true);
		user.setUseStatus(UseStatus.STOP);
		user.putExtendProperty(extendProperties);
		user.setExtendId(extendId);
		
		String password = defaultPassword;
		if(randomPassword == true){
			password = Long.toString(Math.round(Math.random()*899999 + 100000));
		}
		user.setPassword(passwordEncodingUtil.encode(password));
		
		RegistrationAuthority registrationAuthority = null;
		if(registrationAuthorityId == null || registrationAuthorityId.trim().equals("")){
			registrationAuthority = raDao.findUniqueBy("id", registrationAuthorityId);
		}
		
		if(registrationAuthority != null){
			user.setRaCode(registrationAuthority.getCode());
			user.setRaId(registrationAuthority.getId());
			user.setRaName(registrationAuthority.getName());
		}
		
		
		user = usersDao.save(user);
		
		
		return user;
	}
	
	
	@Override
	public User buildUser(User newUser) {
		String defautLoginName = newUser.getLoginName();
		if(defautLoginName == null || defautLoginName.trim().equals("")){
			return null;
		}
		
		String name = newUser.getName();
		if(name == null || name.trim().equals("")){
			return null;
		}
		
		String code = newUser.getCode();
		if(code == null || code.trim().equals("")){
			return null;
		}
		
		String userCategoryCode = newUser.getCategoryCode();
		UserCategory userCategory = userCategoryService.getByCode(userCategoryCode);
		if(userCategory == null){
			return null;
		}
		
		Criteria criteria = this.usersDao.getSession().createCriteria(User.class);
		criteria.add(Restrictions.like("loginName",defautLoginName + "%"));
		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		String loginName = defautLoginName;
		if(totalCount > 0){
			loginName = loginName + "_" + totalCount;
		}
		
		PasswordEncodingUtil passwordEncodingUtil = new PasswordEncodingUtil(PasswordEncodingUtil.ENCODING_ALGORITHM_MD5);
		
		User user = newUser;
		user.setActiveStatus(ActiveStatus.ACTIVE);
		user.setCategoryCode(userCategory.getCode());
		user.setCategoryName(userCategory.getName());
		user.setCode(code);

		user.setLoginName(loginName);
		user.setName(name);
		user.setNeverExpired(true);
		user.setPassword(passwordEncodingUtil.encode(defaultPassword));
		user.setUseStatus(UseStatus.STOP);
		
		user = usersDao.save(user);
		
		return user;
	}

	@Override
	public void unregisterUserByExtendId(String extendId) {
		if(extendId == null){
			return;
		}
		
		User user = usersDao.findUniqueBy("extendId", extendId);
		
		this.unregisterUser(user);
	}

	@Override
	public void unregisterUserById(String id) {
		if(id == null){
			return;
		}
		
		User user = usersDao.findUniqueBy("id", id);
		
		this.unregisterUser(user);
	}
	
	private void unregisterUser(User user){
		if(user == null){
			return;
		}
		
		//删除用户和角色关系
		List<RoleUserRelation> roleUserRelationList = roleUserRelationDao.findBy("userId", user.getId());
		if(roleUserRelationList.isEmpty() == false){
			roleUserRelationDao.delete(roleUserRelationList);
		}
		
		//删除用户
		usersDao.delete(user);
	}


	@Override
	public User saveUser(User user) {
		User newUser = usersDao.save(user);
		
		return newUser;
	}

	@Override
	public User getUserByLoginName(String loginName) {
		User user = usersDao.findUniqueBy("loginName", loginName);
		return user;
	}
	
	@Override
	public User getUserByAccount(String account) {
		User user = usersDao.findUniqueBy("account", account);
		return user;
	}

	@Override
	public void setRoleForUser(User user, Role role) {
		RoleUserRelation roleUserRelation = new RoleUserRelation(user, role);
		roleUserRelationDao.save(roleUserRelation);
	}

	@Override
	public User getById(String id) {
		User user = this.usersDao.findUniqueBy("id", id);
		
		return user;
	}
	
	@Override
	public List<User> getByIds(Collection<String> ids) {
		if(ids == null || ids.isEmpty() == true){
			return new ArrayList<User>();
		}
		
		Criteria criteria = usersDao.getSession().createCriteria(User.class);
		criteria.add(Restrictions.in("id", ids));
		
		@SuppressWarnings("unchecked")
		List<User> userList = criteria.list();
		
		return userList;
	}
	
	@Override
	public List<User> getUserByLoginName(Collection<String> loginNameSet) {
		if(loginNameSet == null || loginNameSet.isEmpty() == true){
			return new ArrayList<User>();
		}
		
		Criteria criteria = usersDao.getSession().createCriteria(User.class);
		criteria.add(Restrictions.in("loginName", loginNameSet));
		
		@SuppressWarnings("unchecked")
		List<User> userList = criteria.list();
		
		return userList;
	}

	@Override
	public User getByExtendId(String extendId) {
		User user = this.usersDao.findUniqueBy("extendId", extendId);
		
		return user;
	}

	@Override
	public List<User> getByNameLike(String nameLike) {
		List<User> userList = this.usersDao.find(Restrictions.like("name", nameLike));
				
		return userList;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> userList = this.usersDao.getAll();
		
		return userList;
	}

	@Override
	public List<User> getByRaId(String raId) {
		
		List<User> userList = this.usersDao.findBy("raId", raId);
		
		return userList;
	}
	@Override
	public Page<User> findAllUser(PageConfig config, String raId) {
		Criteria criteria = createCriteria(raId);
		

		Page<User> userPage = new Page<User>(config);

		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		userPage.setTotal(totalCount);
		
		criteria = createCriteria(raId);


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
		List<User> userList = criteria.list();
		userPage.setRows(userList);
	
		return userPage;
	}

	private Criteria createCriteria(String raId) {
		Criteria criteria = usersDao.getSession().createCriteria(User.class);
		
		if(raId != null && raId.trim().isEmpty() == false){
			criteria.add(Restrictions.eq("raId", raId));
		}
		return criteria;
	}

	@Override
	public Page<User> findAllUser(PageConfig config, String userCategoryCode,
			String name, String loginName, String raName, String raId) {
		Criteria criteria = createCriteria(userCategoryCode,name,loginName, raName, raId);
		

		Page<User> userPage = new Page<User>(config);

		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		userPage.setTotal(totalCount);
		
		criteria = createCriteria(userCategoryCode,name, loginName, raName,raId);


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

	private Criteria createCriteria(String userCategoryCode, String name,String loginName,String raName, String raId) {
		Criteria criteria = usersDao.getSession().createCriteria(User.class);
		
		String searchTextMode;
		if(userCategoryCode != null && userCategoryCode.trim().isEmpty() == false){
			criteria.add(Restrictions.eq("categoryCode", userCategoryCode));
		}
		if(name != null && name.trim().isEmpty() == false){
			searchTextMode = "%" + name + "%";
			criteria.add(Restrictions.like("name",searchTextMode));
		}
		if(loginName != null && loginName.trim().isEmpty() == false){
			searchTextMode = "%" + loginName + "%";
			criteria.add(Restrictions.like("account",searchTextMode));
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
	public Page<User> findAllUser(PageConfig config, String userCategoryCode,
			String name, String loginName, String raName,
			RegistrationAuthority ra, String resetStatus) {
		Page<User> userPage = new Page<User>(config);

		String hql = "select ra from RegistrationAuthority ra,ChecknameInf ck where ra.extendId = ck.id and ck.checkOrganization = ?";
		List<RegistrationAuthority> raList = raDao.find(hql, ra.getExtendId());
		if(raList.isEmpty() == true){
			userPage.setTotal(0);
			userPage.setRows(new ArrayList<User>());
			
			return userPage;
		}
			
		Criteria criteria = createCriteria(userCategoryCode, raList,name, loginName, raName, resetStatus);
		
		
		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		userPage.setTotal(totalCount);
		
		criteria = createCriteria(userCategoryCode, raList, name, loginName, raName, resetStatus);
		
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

	private Criteria createCriteria(String userCategoryCode,
			List<RegistrationAuthority> raList,String name, String loginName, String raName, String resetStatus) {
		Criteria criteria = usersDao.getSession().createCriteria(User.class);
		
		Criterion criterion = null;
		Set<String> raIds = new HashSet<String>();
		for (RegistrationAuthority registrationAuthority : raList) {
			raIds.add(registrationAuthority.getId());
			
			if(raIds.size() < 1000){
				continue;
			}
			
			if(criterion == null){
				criterion = Restrictions.in("raId", raIds);
			}else{
				criterion = Restrictions.or(criterion, Restrictions.in("raId", raIds));
			}
			raIds = new HashSet<String>();
		}
		
		if(criterion == null && raIds.isEmpty() == false){
			criterion = Restrictions.in("raId", raIds);;
		}else if(raIds.isEmpty() == false){
			criterion = Restrictions.or(criterion, Restrictions.in("raId", raIds));
		}
		
		criteria.add(criterion).add(Restrictions.eq("categoryCode", userCategoryCode));
		
		String searchTextMode;
		if(name != null && name.trim().isEmpty() == false){
			searchTextMode = "%" + name + "%";
			criteria.add(Restrictions.like("name",searchTextMode));
		}
		if(loginName != null && loginName.trim().isEmpty() == false){
			searchTextMode = "%" + loginName + "%";
			criteria.add(Restrictions.like("account",searchTextMode));
		}
		if(raName != null && raName.trim().isEmpty() == false){
			searchTextMode = "%" + raName + "%";
			criteria.add(Restrictions.like("raName",searchTextMode));
		}
		if(resetStatus != null && resetStatus.trim().equals("true") == true){
			searchTextMode = "%\"reset\":\"true\"%";
			criteria.add(Restrictions.like("extendPropertiesText",searchTextMode));
		}
		if(resetStatus != null && resetStatus.trim().equals("false") == true){
			searchTextMode = "%\"reset\":\"true\"%";
			criteria.add(Restrictions.not(Restrictions.like("extendPropertiesText",searchTextMode)));
		}
		return criteria;
	}

	@Override
	public User updateAccount(String userId, String newAccount) {
		if(userId == null || userId.trim().equals("") == true){
			return null;
		}
		
		User user = this.getById(userId);
		
		user = this.updateAccount(user, newAccount);
		
		return user;
	}

	@Override
	public User updateAccount(User user, String newAccount) {
		if(user == null || user.getId() == null || user.getId().trim().equals("") == true){
			return null;
		}
		if(newAccount == null || newAccount.trim().equals("") == true){
			return null;
		}
		
		Criteria criteria = this.usersDao.getSession().createCriteria(User.class);
		criteria.add(Restrictions.like("account", newAccount + "%"));
		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		
		String account = newAccount;
		if(totalCount > 0){
			account = account + "_" + totalCount;
		}
		
		user.setAccount(account);
		user.setName(account);
		
		User updatedUser = this.saveUser(user);
		return updatedUser;
	}

	@Override
	public Page<User> findAllUser(PageConfig config, String name,
			String loginName, String raCode) {
		Criteria criteria = createCriteria(name,loginName,raCode);
		

		Page<User> userPage = new Page<User>(config);

		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		userPage.setTotal(totalCount);
		
		criteria = createCriteria(name,loginName,raCode);


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
	
	private Criteria createCriteria(String name,String loginName,String raCode) {
		Criteria criteria = usersDao.getSession().createCriteria(User.class);
		
		String searchTextMode;
		if(name != null && name.trim().isEmpty() == false){
			searchTextMode = "%" + name + "%";
			criteria.add(Restrictions.like("name",searchTextMode));
		}
		if(loginName != null && loginName.trim().isEmpty() == false){
			searchTextMode = "%" + loginName + "%";
			criteria.add(Restrictions.like("account",searchTextMode));
		}
		if(raCode != null && raCode.trim().isEmpty() == false){
			searchTextMode = "%" + raCode + "%";
			criteria.add(Restrictions.like("raCode",searchTextMode));
		}
		return criteria;
	}

}
