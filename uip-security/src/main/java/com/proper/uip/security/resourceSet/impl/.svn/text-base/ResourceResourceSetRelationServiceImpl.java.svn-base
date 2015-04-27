package com.proper.uip.security.resourceSet.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.ResourceResourceSetRelation;
import com.proper.uip.api.security.entity.ResourceSet;
import com.proper.uip.api.security.entity.RoleResourceRelation;
import com.proper.uip.security.dao.ResourceResourceSetRelationDao;
import com.proper.uip.security.dao.ResourceSetDao;
import com.proper.uip.security.dao.ResourcesDao;
import com.proper.uip.security.dao.RoleResourceRelationDao;
import com.proper.uip.security.resourceSet.service.ResourceResourceSetRelationService;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class ResourceResourceSetRelationServiceImpl implements
		ResourceResourceSetRelationService {
	
	@Autowired
	private ResourceSetDao resourceSetDao;
	
	@Autowired
	private ResourcesDao resourcesDao;
	
	@Autowired
	private RoleResourceRelationDao roleResourceRelationDao;
	
	@Autowired
	private ResourceResourceSetRelationDao resourceResourceSetRelationDao;
	
	@Override
	public void updateTree(String resourcesetId, String resourceSequenceNumbers) {
		
		updateResourceResourceSetRelations(resourcesetId,resourceSequenceNumbers);
		
		updateRoleResource();
		//deleteRole(resourcesetId);
		
	}
	@Override
	public void updateRoleResource() {
		StringBuffer sql = new StringBuffer("select ro.name as roleName");
		sql.append(", null as id");
		sql.append(", re.name as resourceName");
		sql.append(", re.id as resourceId");
		sql.append(", ro.id as roleId");
		sql.append(", '' as description");
		
			
		sql.append(" from security_resources_c re, security_roles_c ro, security_resset_res_c rereset, security_ra_resset_c rareset");
			
		sql.append(" where re.anonymously = 'N' and re.id = rereset.resource_id");
		sql.append(" and rereset.resourceset_id = rareset.resourceset_id and rareset.ra_id = ro.ra_id and ro.category_code = 'role.category.organization.full' and");
		sql.append(" not exists (select 1 from security_roles_resources_c rores where re.id = rores.resource_id and ro.id = rores.role_id)");
		
		Query query = roleResourceRelationDao.getSession()
					.createSQLQuery(sql.toString())
					.addScalar("id")
					.addScalar("roleName")
					.addScalar("resourceName")
					.addScalar("resourceId")
					.addScalar("roleId")
					.addScalar("description")
					.setResultTransformer(
						Transformers.aliasToBean(RoleResourceRelation.class));
		
		@SuppressWarnings("unchecked")
		List<RoleResourceRelation> roleResourceList = query.list();
		roleResourceRelationDao.save(roleResourceList);
	}
	@Override
	public void updateResourceResourceSetRelations(String resourcesetId,String resourceSequenceNumbers) {
		ResourceSet resourceSet = resourceSetDao.findUniqueBy("id", resourcesetId);
		List<String> resourceSequenceNumberList = Arrays.asList(resourceSequenceNumbers.split(","));
		
		Criteria criteria = this.resourcesDao.getSession().createCriteria(Resource.class);
		criteria.add(Restrictions.in("code", resourceSequenceNumberList));
		
		@SuppressWarnings("unchecked")
		List<Resource> resourceList = criteria.list();
		
		
		List<ResourceResourceSetRelation> resourceResourceSetRelationList = new ArrayList<ResourceResourceSetRelation>();
		ResourceResourceSetRelation resourceSetResource = null;
		for (Resource resource : resourceList) {
			resourceSetResource = new ResourceResourceSetRelation(resource, resourceSet);
			
			resourceResourceSetRelationList.add(resourceSetResource);
		}
		
		
		List<ResourceResourceSetRelation> oldResourceResourceSetRelationList = resourceResourceSetRelationDao.findBy("resourcesetId", resourcesetId);
		if(oldResourceResourceSetRelationList != null && oldResourceResourceSetRelationList.isEmpty() == false){
			resourceResourceSetRelationDao.delete(oldResourceResourceSetRelationList);
		}
		
		if(resourceResourceSetRelationList != null && resourceResourceSetRelationList.isEmpty() == false){
			resourceResourceSetRelationDao.save(resourceResourceSetRelationList);
		}
	}
	
	
	@Override
	public void deleteRoleResource(String resourceSetId) {
		String sql = "select * from security_roles_c t where t.category_code = 'role.category.organization.full' and  exists (select 1 from   security_ra_resset_c w where  t.ra_id = w.ra_id and w.resourceset_id = '"+resourceSetId+"')";
		roleResourceRelationDao.getSession().createSQLQuery(sql).executeUpdate();
		
	}
	@Override
	public List<ResourceResourceSetRelation> getByResourceSetId(String resourceSetId) {
		List<ResourceResourceSetRelation> resourceResourceSetRelationList = this.resourceResourceSetRelationDao.findBy("resourcesetId", resourceSetId);
		return resourceResourceSetRelationList;
	}
	@Override
	public void deleteRelation(String resourceSetId) {
		List<ResourceResourceSetRelation> resourceResourceSetRelationList = this.resourceResourceSetRelationDao.findBy("resourcesetId", resourceSetId);
		resourceResourceSetRelationDao.delete(resourceResourceSetRelationList);
	}

}
