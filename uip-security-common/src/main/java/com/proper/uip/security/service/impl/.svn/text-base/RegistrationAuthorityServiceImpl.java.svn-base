/* <p>文件名称: RegistrationAuthorityServiceImpl.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-8-24</p>
 * <p>完成日期：2013-8-24</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-8-24 下午12:11:04
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.security.entity.RaResourceSetRelation;
import com.proper.uip.api.security.entity.RegistrationAuthority;
import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.ResourceSet;
import com.proper.uip.api.security.entity.Role;
import com.proper.uip.api.security.entity.RoleCategory;
import com.proper.uip.api.security.entity.RoleResourceRelation;
import com.proper.uip.api.security.entity.RoleUserRelation;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.entity.User.UseStatus;
import com.proper.uip.api.security.service.RegistrationAuthorityService;
import com.proper.uip.api.security.service.RoleCategoryService;
import com.proper.uip.api.security.service.UserService;
import com.proper.uip.security.dao.RaResourceSetRelationDao;
import com.proper.uip.security.dao.RegistrationAuthorityDao;
import com.proper.uip.security.dao.ResourceSetDao;
import com.proper.uip.security.dao.ResourcesDao;
import com.proper.uip.security.dao.RoleResourceRelationDao;
import com.proper.uip.security.dao.RoleUserRelationDao;
import com.proper.uip.security.dao.RolesDao;
import com.proper.uip.security.dao.UsersDao;
import com.proper.uip.security.utils.PasswordEncodingUtil;

@Service("RegistrationAuthorityService_Public")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
public class RegistrationAuthorityServiceImpl implements
		RegistrationAuthorityService {

	@Autowired
	private ResourcesDao resourcesDao;

	@Autowired
	private RoleResourceRelationDao roleResouceDao;

	@Autowired
	private ResourceSetDao resourceSetDao;

	@Autowired
	private RaResourceSetRelationDao raResourceSetRelationDao;

	@Autowired
	private UserService userService;

	@Autowired
	private RegistrationAuthorityDao raDao;

	@Autowired
	private RolesDao rolesDao;
	
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private RoleUserRelationDao roleUserRelationDao;

	@Autowired(required = false)
	@Qualifier("organizationAdminCategoryCode")
	private String organizationAdminCategoryCode;

	@Autowired(required = false)
	@Qualifier("organizaitonFullAthorityRoleCategory")
	private String organizaitonFullAthorityRoleCategory = "role.category.organization.full";

	@Autowired
	private RoleCategoryService roleCategoryService;

	@Autowired(required = false)
	private String defaultPassword;

	@Override
	public RegistrationAuthority builderRa(RegistrationAuthority newRa) {
		String adminUserCategoryCode = this.organizationAdminCategoryCode;
		User fullAuthorityUser = null;

		RegistrationAuthority ra = this.builderRa(newRa, adminUserCategoryCode,
				fullAuthorityUser);

		return ra;
	}

	@Override
	public RegistrationAuthority builderRa(RegistrationAuthority newRa,
			User fullAuthorityUser) {
		String adminUserCategoryCode = this.organizationAdminCategoryCode;

		RegistrationAuthority ra = this.builderRa(newRa, adminUserCategoryCode,
				fullAuthorityUser);

		return ra;
	}

	@Override
	public RegistrationAuthority builderRa(RegistrationAuthority newRa,
			String adminUserCategoryCode, User fullAuthorityUser) {
		boolean randomPassword = false;
		
		RegistrationAuthority ra = this.builderRa(newRa, adminUserCategoryCode, fullAuthorityUser, randomPassword);
		
		return ra;
	}
	

	@Override
	public RegistrationAuthority builderRa(RegistrationAuthority newRa,
			String adminUserCategoryCode, User fullAuthorityUser,
			boolean randomPassword) {
		if (newRa.getId() != null) {
			newRa.setId(null);
		}

		RegistrationAuthority ra = raDao.save(newRa);

		if (adminUserCategoryCode == null) {
			adminUserCategoryCode = organizationAdminCategoryCode;
		}

		User adminUser = this.buildRaAdministrator(ra, adminUserCategoryCode);
		ra.setAdminAccount(adminUser.getLoginName());
		if (fullAuthorityUser != null) {
			User user = this.buildRaUserWithFullAuthority(ra, fullAuthorityUser);
			fullAuthorityUser.setId(user.getId());
		}

		return ra;
	}

	@Override
	public RegistrationAuthority builderRa(RegistrationAuthority newRa,
			User adminUser, User fullAuthorityUser) {
		if (newRa.getId() != null) {
			newRa.setId(null);
		}

		RegistrationAuthority ra = raDao.save(newRa);

		adminUser.setRaCode(ra.getCode());
		adminUser.setRaId(ra.getId());
		adminUser.setRaName(ra.getName());

		User theAdminUser = userService.buildUser(adminUser);

		adminUser.setId(theAdminUser.getId());
		adminUser.setLoginName(theAdminUser.getLoginName());
		adminUser.setPassword(theAdminUser.getPassword());

		ra.setAdminAccount(theAdminUser.getLoginName());

		if (fullAuthorityUser != null) {
			User user = this
					.buildRaUserWithFullAuthority(ra, fullAuthorityUser);
			fullAuthorityUser.setId(user.getId());
		}

		return ra;
	}

	@Override
	public User buildRaAdministrator(RegistrationAuthority ra, String userCategoryCode) {
		boolean randomPassword = false;
		User user = this.buildRaAdministrator(ra, userCategoryCode, randomPassword);
		
		return user;
	}
	
	@Override
	public User buildRaAdministrator(RegistrationAuthority ra,
			String userCategoryCode, boolean randomPassword) {
		Map<String, String> userExtendProperties = new HashMap<String, String>();
		String extendId = null;
		// 创建机构管理员
		User adminitrator = userService.buildUser(userCategoryCode, ra.getId(),
				ra.getAdminAccount(), ra.getAdminAccount(), ra.getCode(), extendId,
				userExtendProperties, randomPassword);

		if (adminitrator.getRaId() == null
				|| adminitrator.getRaId().trim().equals("") == true) {
			adminitrator.setRaCode(ra.getCode());
			adminitrator.setRaId(ra.getId());
			adminitrator.setRaName(ra.getName());

			userService.saveUser(adminitrator);
		}

		return adminitrator;
	}

	@Override
	public RegistrationAuthority saveRa(RegistrationAuthority ra) {
		RegistrationAuthority newRa = raDao.save(ra);

		return newRa;
	}

	@Override
	public void unregisterRaByExtendId(String extendId) {
		RegistrationAuthority ra = raDao.findUniqueBy("extendId", extendId);

		this.unregisterRa(ra);
	}

	@Override
	public void unregisterRaByExtendIds(List<String> extendIds) {
		
		for (String extendId : extendIds) {
			RegistrationAuthority ra = raDao.findUniqueBy("extendId", extendId);

			this.unregisterRa(ra);
		}
		
	}
	
	@Override
	public void unregisterRaById(String id) {
		RegistrationAuthority ra = raDao.findUniqueBy("id", id);

		this.unregisterRa(ra);
	}
	
	@SuppressWarnings("unchecked")
	private void unregisterRa(RegistrationAuthority ra){
		if(ra == null){
			return;
		}
		
		//删除机构下用户
		List<User> userList = usersDao.findBy("raId", ra.getId());
		List<String> userIdList = new ArrayList<String>();
		for(User user : userList){
			userIdList.add(user.getId());
		}
		if(userList.isEmpty() == false){
			usersDao.delete(userList);
		}
		
		//删除机构下角色
		List<Role> roleList = rolesDao.findBy("raId", ra.getId());
		
		List<String>  roleIdList = new ArrayList<String>();
		for(Role role : roleList){
			roleIdList.add(role.getId());
		}
		
		if(roleList.isEmpty() == false){
			rolesDao.delete(roleList);
		}
		
		//删除用户和角色关系
		Criteria criteria = roleUserRelationDao.getSession().createCriteria(RoleUserRelation.class);
		Criterion criterion = null;
		if(userIdList.isEmpty() == false){
			criterion = Restrictions.in("userId", userIdList);
		}
		if(roleIdList.isEmpty() == false){
			criterion = criterion== null ? Restrictions.in("roleId", userIdList) : Restrictions.or(criterion, Restrictions.in("roleId", userIdList));
		}
		
		List<RoleUserRelation> roleUserRelationList = new ArrayList<RoleUserRelation>();
		if(criterion != null){
			criteria.add(criterion);

			roleUserRelationList = criteria.list();
		}
		
		if(roleUserRelationList.isEmpty() == false){
			roleUserRelationDao.delete(roleUserRelationList);
		}
		
		//删除机构和资源集关系
		List<RaResourceSetRelation> raResourceSetRelationList = raResourceSetRelationDao.findBy("raId", ra.getId());
		if(raResourceSetRelationList.isEmpty() == false){
			raResourceSetRelationDao.delete(raResourceSetRelationList);
		}
		
		//删除机构
		raDao.delete(ra);
	}

	@Override
	public User buildRaUserWithFullAuthority(RegistrationAuthority raEntity,
			User user) {
		boolean randomPassword = false;
		User userWithFullAuthority = this.buildRaUserWithFullAuthority(raEntity, user, randomPassword);
		
		return userWithFullAuthority;
	}
	
	@Override
	public User buildRaUserWithFullAuthority(RegistrationAuthority raEntity,
			User user, boolean randomPassword) {
		RoleCategory roleCategory = roleCategoryService.getByCode(organizaitonFullAthorityRoleCategory);

		if (roleCategory == null) {
			roleCategory = new RoleCategory();
			roleCategory.setCode(organizaitonFullAthorityRoleCategory);
			roleCategory.setName("机构最大业务权限角色");
		}

		// 1、获取机构最大业务权限角色，若不存在，则创建一个
		Role role = rolesDao.findUnique(
				"from Role where raId=? and categoryCode=?", raEntity.getId(),
				organizaitonFullAthorityRoleCategory);
		if (role == null) {
			role = new Role();
			role.setCategoryCode(organizaitonFullAthorityRoleCategory);
			role.setCategoryName(roleCategory.getName());

			role.setCode(raEntity.getCode());
			role.setDecription(raEntity.getName() + roleCategory.getName());
			role.setId(null);
			role.setName(raEntity.getName() + roleCategory.getName());
			role.setRaCode(raEntity.getCode());
			role.setRaId(raEntity.getId());
			role.setRaName(raEntity.getName());
			role.setUseStatus(UseStatus.STOP);

			rolesDao.save(role);

			String hql = "select r from Resource r, ResourceResourceSetRelation rrs, RaResourceSetRelation rar where r.id=rrs.resourceId and rrs.resourcesetId=rar.resourcesetId and rar.raId=?";
			List<Resource> resourceList = this.resourcesDao.find(hql,
					raEntity.getId());

			List<RoleResourceRelation> relationList = new ArrayList<RoleResourceRelation>();
			for (Resource resource : resourceList) {
				relationList.add(new RoleResourceRelation(resource, role));
			}
			if (relationList.isEmpty() == false) {
				roleResouceDao.save(relationList);
			}
		}

		// 2、创建用户
		if (user.getId() != null && user.getId().trim().equals("") == true) {
			user.setId(null);
		}

		if (user.getRaId() == null || user.getRaId().trim().equals("") == true) {
			user.setRaId(raEntity.getId());
			user.setRaCode(raEntity.getCode());
			user.setRaName(raEntity.getName());

			// 处理登录名和默认密码
			Criteria criteria = this.raDao.getSession().createCriteria(
					User.class);
			criteria.add(Restrictions.like("loginName", user.getLoginName()
					+ "%"));
			long totalCount = ((Long) criteria.setProjection(
					Projections.rowCount()).uniqueResult()).longValue();
			String loginName = user.getLoginName();
			if (totalCount > 0) {
				loginName = loginName + "_" + totalCount;
			}

			PasswordEncodingUtil passwordEncodingUtil = new PasswordEncodingUtil(
					PasswordEncodingUtil.ENCODING_ALGORITHM_MD5);

			user.setLoginName(loginName);
			String password = defaultPassword;
			if(randomPassword == true){
				password = Long.toString(Math.round(Math.random()*899999 + 100000));
			}
			user.setPassword(passwordEncodingUtil.encode(password));
		}

		user = userService.saveUser(user);

		userService.setRoleForUser(user, role);

		return user;
	}

	@Override
	public RegistrationAuthority getById(String id) {
		RegistrationAuthority ra = this.raDao.findUniqueBy("id", id);

		return ra;
	}

	@Override
	public RegistrationAuthority getByExtendId(String extendId) {
		RegistrationAuthority ra = this.raDao
				.findUniqueBy("extendId", extendId);

		return ra;
	}

	@Override
	public void resetRaResouceSets(String raId, List<String> resouceSetCodes) {
		boolean needResetRaFullAuthority = false;

		this.resetRaResouceSets(raId, resouceSetCodes, needResetRaFullAuthority);
	}

	@Override
	public void resetRaResouceSets(String raId, List<String> resouceSetCodes,
			boolean needResetRaFullAuthority) {
		if (raId == null || raId.trim().equals("") == true) {
			return;
		}

		RegistrationAuthority ra = this.getById(raId);
		if (ra == null) {
			return;
		}

		Map<String, Object> values = new HashMap<String, Object>();
		values.put("resouceSetCodes", resouceSetCodes);

		List<ResourceSet> resourceSets = resourceSetDao.find(
				"from ResourceSet where code in (:resouceSetCodes)", values);

		// 机构和资源集关系入库：删除老资源集，添加新资源集
		List<RaResourceSetRelation> relationList = new ArrayList<RaResourceSetRelation>();
		RaResourceSetRelation relation = null;
		for (ResourceSet resourceSet : resourceSets) {
			relation = new RaResourceSetRelation(ra, resourceSet);
			relationList.add(relation);
		}

		List<RaResourceSetRelation> oldRelations = raResourceSetRelationDao
				.findBy("raId", raId);

		if (oldRelations != null && oldRelations.isEmpty() == false) {
			raResourceSetRelationDao.delete(oldRelations);
		}

		if (relationList.isEmpty() == false) {
			raResourceSetRelationDao.save(relationList);
		}

		// 处理最大业务权限
		if (needResetRaFullAuthority == false) {
			return;
		}

		this.resetRaFullAuthority(ra, resourceSets);
	}

	@Override
	public Role resetRaFullAuthorityByExtendId(String extendId) {
		RegistrationAuthority ra = this.getByExtendId(extendId);

		if (ra == null) {
			return null;
		}

		Role fullAuthorityRole = this.resetRaFullAuthorityByRaId(ra.getId());

		return fullAuthorityRole;
	}

	@Override
	public Role resetRaFullAuthorityByRaId(String raId) {
		if (raId == null || raId.trim().equals("") == true) {
			return null;
		}

		RegistrationAuthority ra = this.getById(raId);

		Role role = this.resetRaFullAuthority(ra);

		return role;
	}

	private Role resetRaFullAuthority(RegistrationAuthority ra) {
		if (ra == null) {
			return null;
		}

		List<ResourceSet> resouceSets = resourceSetDao
				.find("select distinct rs from ResourceSet rs, RaResourceSetRelation rrs where rs.id=rrs.resourcesetId and rrs.raId=?",
						ra.getId());

		Role role = this.resetRaFullAuthority(ra, resouceSets);

		return role;
	}

	private Role resetRaFullAuthority(RegistrationAuthority ra,
			List<ResourceSet> resourceSets) {
		
		RoleCategory roleCategory = roleCategoryService.getByCode(organizaitonFullAthorityRoleCategory);

		if (roleCategory == null) {
			roleCategory = new RoleCategory();
			roleCategory.setCode(organizaitonFullAthorityRoleCategory);
			roleCategory.setName("机构最大业务权限角色");
		}
		
		// 1、获取机构最大业务权限角色，若不存在，则创建一个
		Role role = rolesDao.findUnique("from Role where raId=? and categoryCode=?", ra.getId(), organizaitonFullAthorityRoleCategory);
		
		if (role == null) {
			role = new Role();
			role.setCategoryCode(organizaitonFullAthorityRoleCategory);
			role.setCategoryName(roleCategory.getName());

			role.setCode(ra.getCode());
			role.setDecription(ra.getName() + roleCategory.getName());
			role.setId(null);
			role.setName(ra.getName() + roleCategory.getName());
			role.setRaCode(ra.getCode());
			role.setRaId(ra.getId());
			role.setRaName(ra.getName());
			role.setUseStatus(UseStatus.STOP);
			role.setCreateTime(new Date());

			rolesDao.save(role);
		}
		
		List<String> resourceSetIds = new ArrayList<String>();
		for(ResourceSet resourceSet : resourceSets){
			resourceSetIds.add(resourceSet.getId());
		}
		
		String hql = "select r from Resource r, ResourceResourceSetRelation rrs where r.id=rrs.resourceId and rrs.resourcesetId in (:resourceSetIds)";
		
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("resourceSetIds", resourceSetIds);
		
		List<Resource> resourceList = this.resourcesDao.find(hql, values);
		
		List<RoleResourceRelation> relationList = new ArrayList<RoleResourceRelation>();
		for (Resource resource : resourceList) {
			relationList.add(new RoleResourceRelation(resource, role));
		}
		
		//处理旧资源
		List<RoleResourceRelation> oldRelationList = roleResouceDao.findBy("roleId", role.getId());
		if(oldRelationList.isEmpty() == false){
			roleResouceDao.delete(oldRelationList);
		}
		
		//处理新资源
		if (relationList.isEmpty() == false) {
			roleResouceDao.save(relationList);
		}
		
		return role;
	}


	@Override
	public Role resetRaFullAuthorityByResourceSetId(String resourceSetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RegistrationAuthority> getAllRa() {
		List<RegistrationAuthority> raList = raDao.getAll();
		return raList;
	}

	@Override
	public List<RegistrationAuthority> getRaListByExtendId(String extendId) {
		String hql = "select ra from RegistrationAuthority ra,ChecknameInf ck where ra.extendId = ck.id and ck.checkOrganization = ?";
		List<RegistrationAuthority> raList = raDao.find(hql, extendId);
		
		return raList;
	}

	@Override
	public List<RegistrationAuthority> getByCategoryId(String categoryId) {
		List<RegistrationAuthority> raList = raDao.findBy("categoryId", categoryId);
		return raList;
	}

	@Override
	public RegistrationAuthority getByCode(String code) {
		RegistrationAuthority ra = this.raDao.findUniqueBy("code", code);

		return ra;
	}
}
