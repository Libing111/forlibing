package com.proper.uip.security.dao;

import org.springframework.stereotype.Repository;

import com.proper.uip.api.security.entity.UsercateRole;
import com.proper.uip.common.core.dao.HibernateDao;

@Repository("usercateRoleDao")
public class UsercateRoleDao extends HibernateDao<UsercateRole, String>{

}
