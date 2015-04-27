/* <p>文件名称: SecurityServiceImpl.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-6-3</p>
 * <p>完成日期：2013-6-3</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-6-3 上午11:03:26
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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.security.entity.RaCategory;
import com.proper.uip.api.security.entity.RegistrationAuthority;
import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.RoleUserRelation;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.entity.User.ActiveStatus;
import com.proper.uip.api.security.entity.User.UseStatus;
import com.proper.uip.api.security.entity.UserCategory;
import com.proper.uip.api.security.service.RaCategoryService;
import com.proper.uip.api.security.service.SecurityService;
import com.proper.uip.api.security.service.UserCategoryService;
import com.proper.uip.security.dao.RegistrationAuthorityDao;
import com.proper.uip.security.dao.ResourcesDao;
import com.proper.uip.security.dao.RoleResourceRelationDao;
import com.proper.uip.security.dao.RoleUserRelationDao;
import com.proper.uip.security.dao.UsersDao;
import com.proper.uip.security.utils.PasswordEncodingUtil;

@Service("securityService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
public class SecurityServiceImpl implements SecurityService {
	@Autowired
	private ResourcesDao resourcesDao;

	@Autowired
	private UsersDao usersDao;

	@Autowired
	private RoleResourceRelationDao roleResourceRelationDao;
	
	@Autowired
	private RegistrationAuthorityDao raDao;
	
	@Autowired(required = false)
	private String defaultPassword;
	
	@Autowired
	private UserCategoryService userCategoryService;
	
	@Autowired
	private RaCategoryService raCategoryService;
	
	@Autowired
	private RoleUserRelationDao ruDao;
	
	@Autowired(required = false)
	@Qualifier("organizationAdminCategoryCode")
	private String organizationAdminCategoryCode; 

	@Autowired(required = false)
	@Qualifier("resourceCategoris")
	private String resourceCategoris;//逗号分隔
	
	@Override
	public boolean checkPermission(User user, String url) {
		/**
		 * select count(*) from security_resource_c re where re.url = ? and
		 * exists (select 1 from security_roles_users_c ru,
		 * security_roles_resources_c rr where ru.role_id = rr.role_id and
		 * ru.user_id = ? and rr.resource_id = re.id);
		 */
		StringBuffer sql = new StringBuffer("select count(*) ")
				.append(" from security_resource_c re")
				.append(" where")
				.append(" re.url = ")
				.append("'")
				.append(url)
				.append("'")
				.append(" and ")
				.append(" exists")
				.append(" (")
				.append(" select 1")
				.append(" from security_roles_users_c ru, security_roles_resources_c rr")
				.append(" where").append(" ru.role_id = rr.role_id")
				.append(" and").append(" rr.resource_id = re.id")
				.append(" and").append(" ru.user_id = ").append("'")
				.append(user.getId()).append("'");

		String countStr = roleResourceRelationDao
				.createSQLQuery(sql.toString()).uniqueResult().toString();
		long count = Long.parseLong(countStr);

		boolean hasPermission = false;
		if (count > 0) {
			hasPermission = true;
		}

		return hasPermission;
	}

	@Override
	public boolean checkPermissionOfCurrentUser(HttpServletRequest request,
			String url) {
		User currentUser = this.getCurrentUser(request);

		return checkPermission(currentUser, url);
	}

	//@SuppressWarnings("unchecked")
	@Override
	public List<Resource> getResourcesOfCurrentUser(HttpServletRequest request,
			String parent, String moc) {
		List<Resource> resourceList = new ArrayList<Resource>();
		// 应该关联角色表查询当前用户拥有权限的业务子系统
		//取当前用户
		User currentUser = this.getCurrentUser(request);
		//取当前用户下有多少角色
		List<RoleUserRelation> roleUserRelationList = ruDao.findBy("userId", currentUser.getId());
		if(roleUserRelationList == null || roleUserRelationList.isEmpty() == true){
			return resourceList;
		}
		Set<String> roleIds = new HashSet<String>();
		for(RoleUserRelation ru : roleUserRelationList){
			roleIds.add(ru.getRoleId());
		}
		if(roleIds.isEmpty()){
			return resourceList;
		}
		Map<String,Object> map = new HashMap<String,Object>(); 
		map.put("roleIds", roleIds);
		//再查角色下的资源
		resourceList = this.resourcesDao.find("select r from Resource r, RoleResourceRelation rrr where r.id=rrr.resourceId and rrr.roleId in(:roleIds) and r.parent = ? and r.moc = ? order by r.sequenceNumber asc",parent,moc, map);
		// 角色和用户暂时来不及做，先直接从资源表中获取
//
//		Criteria criteria = resourcesDao.getSession().createCriteria(
//				Resource.class);
//		criteria.add(Restrictions.eq("parent", parent))
//				.add(Restrictions.eq("moc", moc))
//				.addOrder(Order.asc("sequenceNumber"));
//
//		resourceList = criteria.list();

		return resourceList;
	}

	//@SuppressWarnings("unchecked")
	@Override
	public List<Resource> getResourcesOfCurrentUserDesc(
			HttpServletRequest request, String parent, String moc) {
		List<Resource> resourceList = new ArrayList<Resource>();
		// 应该关联角色表查询当前用户拥有权限的业务子系统
		//取当前用户
		User currentUser = this.getCurrentUser(request);
		//取当前用户下有多少角色
		List<RoleUserRelation> roleUserRelationList = ruDao.findBy("userId", currentUser.getId());
		if(roleUserRelationList == null || roleUserRelationList.isEmpty() == true){
			return resourceList;
		}
		Set<String> roleIds = new HashSet<String>();
		for(RoleUserRelation ru : roleUserRelationList){
			roleIds.add(ru.getRoleId());
		}
		if(roleIds.isEmpty()){
			return resourceList;
		}
		Map<String,Object> map = new HashMap<String,Object>(); 
		map.put("roleIds", roleIds);
		//再查角色下的资源
		resourceList = this.resourcesDao.find("select r from Resource r, RoleResourceRelation rrr where r.id=rrr.resourceId and rrr.roleId in(:roleIds) and r.parent = ? and r.moc = ? order by r.sequenceNumber asc",parent,moc, map);
//		// 角色和用户暂时来不及做，先直接从资源表中获取
//		Criteria criteria = resourcesDao.getSession().createCriteria(
//				Resource.class);
//		criteria.add(Restrictions.eq("parent", parent))
//				.add(Restrictions.eq("moc", moc))
//				.addOrder(Order.desc("sequenceNumber"));
//
//		resourceList = criteria.list();

		return resourceList;
	}

	@Override
	public User getCurrentUser(HttpServletRequest request) {
		CasAuthenticationToken principal = (CasAuthenticationToken)request.getUserPrincipal();   
		String userName = principal.getName();   
		
		//User user = usersDao.findUniqueBy("loginName", userName);
		User user = usersDao.findUniqueBy("account", userName);
		return user;
	}
	

	@Override
	public Resource getResourceByCode(String code) {
		Resource resouce = this.resourcesDao.findUniqueBy("code", code);
		
		return resouce;
	}

	@Override
	public User buildUser(String userCategoryCode,
			String registrationAuthorityId, String defautLoginName,
			String name, String code, String extendId, Map<String, String> extendProperties) {
		if(defautLoginName == null || defautLoginName.trim().equals("")){
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
		criteria.add(Restrictions.like("loginName",defautLoginName + "%"));
		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		String loginName = defautLoginName;
		if(totalCount > 0){
			loginName = loginName + "_" + totalCount;
		}
		
		PasswordEncodingUtil passwordEncodingUtil = new PasswordEncodingUtil(PasswordEncodingUtil.ENCODING_ALGORITHM_MD5);
		
		User user = new User();
		user.setActiveStatus(ActiveStatus.ACTIVE);
		user.setCategoryCode(userCategory.getCode());
		user.setCategoryName(userCategory.getName());
		user.setCategoryId(userCategory.getId());
		user.setCode(code);

		user.setLoginName(loginName);
		user.setName(name);
		user.setNeverExpired(true);
		user.setPassword(passwordEncodingUtil.encode(defaultPassword));
		user.setUseStatus(UseStatus.STOP);
		user.putExtendProperty(extendProperties);
		user.setExtendId(extendId);
		user.setCreateTime(new Date());
		
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
	public void unregisterUserByExtendId(String extendId) {
		List<User> userList = usersDao.findBy("extendId", extendId);
		
		if(userList != null && userList.isEmpty() == false){
			usersDao.delete(userList);
		}
	}

	@Override
	public void unregisterUserById(String id) {
		List<User> userList = usersDao.findBy("id", id);
		
		if(userList != null && userList.isEmpty() == false){
			usersDao.delete(userList);
		}
	}

	@Override
	public RegistrationAuthority BuilderRa(RegistrationAuthority newRa) {
		newRa.setId(null);
		RegistrationAuthority ra = raDao.save(newRa);
		
		return ra;
	}
	
	@Override
	public RegistrationAuthority saveRa(RegistrationAuthority ra) {
		RegistrationAuthority newRa = raDao.save(ra);
		
		return newRa;
	}

	@Override
	public void unregisterRaByExtendId(String extendId) {
		List<RegistrationAuthority> raList = raDao.findBy("extendId", extendId);
		
		if(raList != null && raList.isEmpty() == false){
			raDao.delete(raList);
		}
	}

	@Override
	public void unregisterRaById(String id) {
		List<RegistrationAuthority> raList = raDao.findBy("id", id);
		
		if(raList != null && raList.isEmpty() == false){
			raDao.delete(raList);
		}
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
	public RegistrationAuthority createRegistrationAuthority(User currentUser,
			RegistrationAuthority raEntity) {
		Map<String, String> userExtendProperties = new HashMap<String, String>();
		//userExtendProperties.put("organizationId", raEntity.getId());
		
		//创建机构管理员
		User adminitrator = this.buildUser(organizationAdminCategoryCode, raEntity.getId(), raEntity.getCode(), raEntity.getCode(), raEntity.getCode(),null,userExtendProperties);
		raEntity.setAdminAccount(adminitrator.getLoginName());
		raEntity.setId(null);
		raEntity.setCreatePerson(currentUser.getName());
		raEntity.setLastChangePerson(currentUser.getName());
		raEntity.setChangeTime(new Date());
		raEntity.setCreateTime(new Date());
		
		RaCategory dataItem = raCategoryService.getByCode(raEntity.getCategoryCode());
		raEntity.setCategoryName(dataItem.getName());
		raEntity = raDao.save(raEntity);
		//Map<String, String> userExtendProperties = new HashMap<String, String>();
		//userExtendProperties.put("organizationId", raEntity.getId());
		adminitrator.putExtendProperty("organizationId", raEntity.getId());
		
		adminitrator.setRaCode(raEntity.getCode());
		adminitrator.setRaId(raEntity.getId());
		adminitrator.setRaName(raEntity.getName());
		usersDao.save(adminitrator);
		
		return raEntity;
	}

	@Override
	public List<Resource> getModuleResourcesOfCurrentUser(
			HttpServletRequest request) {
		String moc = "module";
		
		List<Resource> resourceList = this.getResourcesOfCurrentUser(request,  moc);
		return resourceList;
	}
	@Override
	public List<Resource> getResourcesOfCurrentUser(HttpServletRequest request, String moc) {
		List<String> resourceCategoryList = Arrays.asList(resourceCategoris.split(","));
		
		List<Resource> resourceList = new ArrayList<Resource>();
		// 应该关联角色表查询当前用户拥有权限的业务子系统
		//取当前用户
		User currentUser = this.getCurrentUser(request);
		//取当前用户下有多少角色
		List<RoleUserRelation> roleUserRelationList = ruDao.findBy("userId", currentUser.getId());
		if(roleUserRelationList == null || roleUserRelationList.isEmpty() == true){
			return resourceList;
		}
		Set<String> roleIds = new HashSet<String>();
		for(RoleUserRelation ru : roleUserRelationList){
			roleIds.add(ru.getRoleId());
		}
		if(roleIds.isEmpty()){
			return resourceList;
		}
		Map<String,Object> map = new HashMap<String,Object>(); 
		map.put("roleIds", roleIds);
		map.put("resourceCategoryList", resourceCategoryList);
		map.put("moc", moc);
		//再查角色下的资源
		resourceList = this.resourcesDao.find("select r from Resource r, RoleResourceRelation rrr where r.id=rrr.resourceId and r.moc=:moc and rrr.roleId in(:roleIds) and r.categoryCode in(:resourceCategoryList) order by r.sequenceNumber asc", map);
		// 角色和用户暂时来不及做，先直接从资源表中获取


		return resourceList;
	}

	@Override
	public User getAccount(String account) {
		String sql = "select c from User c where c.account = ? ";
		User user = usersDao.findUnique(sql, account);
		return user;
	}
	

}
