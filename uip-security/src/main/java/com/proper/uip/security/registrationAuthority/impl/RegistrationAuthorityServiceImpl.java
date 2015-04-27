package com.proper.uip.security.registrationAuthority.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.security.entity.RaCategory;
import com.proper.uip.api.security.entity.RegistrationAuthority;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.service.RaCategoryService;
import com.proper.uip.api.security.service.SecurityService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.dao.RegistrationAuthorityDao;
import com.proper.uip.security.registrationAuthority.service.RegistrationAuthorityService;
import com.proper.uip.security.user.service.UserService;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class RegistrationAuthorityServiceImpl implements RegistrationAuthorityService {
	
	
	@Autowired
	private RegistrationAuthorityDao registrationAuthorityDao;

	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RaCategoryService raCategoryService;
	
	@Autowired
	@Qualifier("organizationAdminCategoryCode")
	private String organizationAdminCategoryCode; 
	
	
	@Override
	public Page<RegistrationAuthority> findAllRa(PageConfig pageConfig, String name, String category) {
		Page<RegistrationAuthority> raList = registrationAuthorityDao.findAllByNamePage(pageConfig, name, category);
		return raList;
	}

	@Override
	public void saveRa(User currentUser, RegistrationAuthority raEntity) {
		Map<String, String> userExtendProperties = new HashMap<String, String>();
		//userExtendProperties.put("organizationId", raEntity.getId());
		
		//创建机构管理员
		User adminitrator = securityService.buildUser(organizationAdminCategoryCode, raEntity.getId(), raEntity.getCode(), raEntity.getCode(), raEntity.getCode(),null,userExtendProperties);
		raEntity.setAdminAccount(adminitrator.getLoginName());
		raEntity.setId(null);
		raEntity.setCreatePerson(currentUser.getName());
		raEntity.setLastChangePerson(currentUser.getName());
		raEntity.setChangeTime(new Date());
		raEntity.setCreateTime(new Date());
		
		RaCategory dataItem = raCategoryService.getById(raEntity.getCategoryCode());
		raEntity.setCategoryName(dataItem.getName());
		raEntity.setCategoryId(dataItem.getId());
		raEntity.setCategoryCode(dataItem.getCode());
		raEntity = registrationAuthorityDao.save(raEntity);
		//Map<String, String> userExtendProperties = new HashMap<String, String>();
		//userExtendProperties.put("organizationId", raEntity.getId());
		adminitrator.putExtendProperty("organizationId", raEntity.getId());
		
		adminitrator.setRaCode(raEntity.getCode());
		adminitrator.setRaId(raEntity.getId());
		adminitrator.setRaName(raEntity.getName());
		userService.saveUser(adminitrator);
		
	}

	@Override
	public RegistrationAuthority getRaById(String id) {
		return registrationAuthorityDao.get(id);
	}

	@Override
	public void deleteRaById(String id) {
		registrationAuthorityDao.delete(id);		
	}

	@Override
	public void saveUpdateRa(User currentUser, RegistrationAuthority raEntity) {
		if (raEntity.getId() == null || raEntity.getId().equals("")) {
			this.saveRa(currentUser, raEntity);
		}
		raEntity.setAdminAccount(raEntity.getAdminAccount());
		raEntity.setCreatePerson(currentUser.getName());
		raEntity.setLastChangePerson(currentUser.getName());
		raEntity.setChangeTime(new Date());
		raEntity.setCreateTime(new Date());
		
		RaCategory dataItem = raCategoryService.getById(raEntity.getCategoryCode());
		raEntity.setCategoryName(dataItem.getName());
		raEntity.setCategoryCode(dataItem.getCode());
		raEntity.setCategoryId(dataItem.getId());
		registrationAuthorityDao.save(raEntity);
	}

	@Override
	public List<RegistrationAuthority> getAll() {
		return registrationAuthorityDao.getAll();
	}

	@Override
	public String getDepartmentId(String id) {
		String sql = "select c.deparmentId from Organization c where c.id = ?";
		String deparmentId = registrationAuthorityDao.findUnique(sql, id);
		return deparmentId;
	}

	@Override
	public String getCode() {
		String sql = "select c.code from RegistrationAuthority c ";
		String code = registrationAuthorityDao.findUnique(sql);
		return code;
	}


	@Override
	public RegistrationAuthority getByExtendId(String extendId) {
		RegistrationAuthority ra = this.registrationAuthorityDao
				.findUniqueBy("extendId", extendId);

		return ra;
	}

	@Override
	public RegistrationAuthority getByCode(String code) {
		RegistrationAuthority ra = this.registrationAuthorityDao.findUniqueBy("code", code);

		return ra;
	}

	@Override
	public RegistrationAuthority getByName(String name) {
		RegistrationAuthority ra = this.registrationAuthorityDao.findUniqueBy("name", name);
		return ra;
	}

	@Override
	public List<RegistrationAuthority> getByCodeList(String code) {
		List<RegistrationAuthority> raList = registrationAuthorityDao.findBy("code", code);
		return raList;
	}
}
