package com.proper.uip.security.secureconf.usercateRole.service;

import com.proper.uip.api.security.entity.UserCategory;
import com.proper.uip.api.security.entity.UsercateRole;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;

public interface UsercateRoleService {


	void save(String ids, UserCategory userCategory);

	Page<UsercateRole> findRole(PageConfig pageConfig, String userCategoryId);

	void delRole(String roleId, String userCategoryId);

}
