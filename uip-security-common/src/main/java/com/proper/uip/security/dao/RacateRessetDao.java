package com.proper.uip.security.dao;

import org.springframework.stereotype.Repository;

import com.proper.uip.api.security.entity.RacateResset;
import com.proper.uip.common.core.dao.HibernateDao;

@Repository("racateRessetDao")
public class RacateRessetDao extends HibernateDao<RacateResset, String>{

}
