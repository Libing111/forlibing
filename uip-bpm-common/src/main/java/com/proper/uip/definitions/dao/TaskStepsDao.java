package com.proper.uip.definitions.dao;

import org.springframework.stereotype.Repository;

import com.proper.uip.api.bpm.definitions.entity.TaskStepsEntity;
import com.proper.uip.common.core.dao.HibernateDao;

@Repository
public class TaskStepsDao extends HibernateDao<TaskStepsEntity, String>{

}
