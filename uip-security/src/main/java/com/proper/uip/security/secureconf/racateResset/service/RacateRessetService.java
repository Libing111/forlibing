package com.proper.uip.security.secureconf.racateResset.service;

import com.proper.uip.api.security.entity.RacateResset;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;

public interface RacateRessetService {

	void save(String raCategoryId, String resourceSetId);

	Page<RacateResset> findAllResourceSet(PageConfig pageConfig,
			String raCategoryId);

	void del(String resourceSetId, String raCategoryId);

}
