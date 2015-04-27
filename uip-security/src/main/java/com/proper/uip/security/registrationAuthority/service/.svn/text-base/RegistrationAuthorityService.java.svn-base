package com.proper.uip.security.registrationAuthority.service;

import java.util.List;

import com.proper.uip.api.security.entity.RegistrationAuthority;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;

public interface RegistrationAuthorityService {
	
	Page<RegistrationAuthority> findAllRa(PageConfig pageConfig, String name, String category);

	void saveRa(User currentUser, RegistrationAuthority raEntity);

	RegistrationAuthority getRaById(String id);

	void deleteRaById(String id);

	void saveUpdateRa(User currentUser, RegistrationAuthority raEntity);

	List<RegistrationAuthority> getAll();

	String getDepartmentId(String id);

	String getCode();
	/**
	 * 获取注册机构
	 * @param extendId
	 * @return
	 */
	RegistrationAuthority getByExtendId(String extendId);
	
	/**
	 * 获取注册机构
	 * @param extendId
	 * @return
	 */
	RegistrationAuthority getByCode(String code);
	
	/**
	 * 获取注册机构
	 * @param extendId
	 * @return
	 */
	RegistrationAuthority getByName(String name);
	
	/**
	 * 获取注册机构
	 * @param extendId
	 * @return
	 */
	List<RegistrationAuthority> getByCodeList(String code);

}
