/* <p>文件名称: BpmDefinitionServiceImpl.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-7-25</p>
 * <p>完成日期：2013-7-25</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-7-25 下午3:25:25
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.bpm.definitions.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.hr.job.service.IJobService;
import com.proper.hr.organization.service.IOrganizationService;
import com.proper.hr.personnel.service.IPersonQueryService;
import com.proper.uip.api.bpm.definitions.entity.AssignmentEntity;
import com.proper.uip.api.bpm.definitions.entity.MonitorEntity;
import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionManagementEntity;
import com.proper.uip.api.bpm.definitions.entity.TaskDefinitionEntity;
import com.proper.uip.api.security.entity.RegistrationAuthority;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.service.SecurityService;
import com.proper.uip.bpm.definitions.entity.IdentityLinkEntity;
import com.proper.uip.bpm.definitions.entity.UserTreeNode;
import com.proper.uip.bpm.definitions.service.BpmDefinitionService;
import com.proper.uip.bpm.definitions.service.ProcessDefinitionManagementService;
import com.proper.uip.definitions.dao.BpmAssignmentDao;
import com.proper.uip.definitions.dao.BpmMonitorDao;
import com.proper.uip.definitions.dao.TaskDefinitionDao;
import com.proper.uip.security.dao.RegistrationAuthorityDao;
import com.proper.uip.security.dao.UsersDao;


@Service
@Transactional(rollbackFor = RuntimeException.class)
public class BpmDefinitionServiceImpl implements BpmDefinitionService{
    @Autowired
	private TaskDefinitionDao taskDefDao;
    
    @Autowired
	private BpmAssignmentDao assignDao;

    @Autowired
	private BpmMonitorDao monitorDao;
    
    @Autowired
    private UsersDao usersDao;
    
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private ProcessDefinitionManagementService ProcessDefinitionManagementService;
	
    @Autowired
    private RegistrationAuthorityDao raDao;
    
    @Autowired(required=true)
    private IPersonQueryService personQueryService;
    
    @Autowired(required=true)
    private IOrganizationService organizationService;
    
    @Autowired(required=true)
    private IJobService jobService;
    
    /**
     * 查询流程步骤
     */
	@Override
	public List<TaskDefinitionEntity> getTaskDefinitionList(String processDefinitionId, String taskDefinitionName,String scenarioItemName, String identityLinkName) {
		Criteria criteria = this.assignDao.getSession().createCriteria(AssignmentEntity.class);
		criteria.add(Restrictions.eq("processDefinitionId", processDefinitionId));
		@SuppressWarnings("unchecked")
		List<AssignmentEntity> assignList = criteria.list();
		
		//key:taskDefinationKey+strategyId value:identityLinkNames(经办人，以逗号分隔)
		Map<String, StringBuffer> assignMap = new HashMap<String, StringBuffer>();
		StringBuffer identityLinkNames = null;
		for(AssignmentEntity entity : assignList){
			identityLinkNames = assignMap.get(entity.getTaskDefinitionKey()+entity.getStrategyId()+entity.getScenarioItemId());
			if(identityLinkNames == null){
				identityLinkNames = new StringBuffer(entity.getIdentityLinkName());
				assignMap.put(entity.getTaskDefinitionKey()+entity.getStrategyId()+entity.getScenarioItemId(), identityLinkNames);
				continue;
			}
			
			identityLinkNames.append(",").append(entity.getIdentityLinkName());
		}
		
		criteria = this.taskDefDao.getSession().createCriteria(TaskDefinitionEntity.class);
		
		criteria.add(Restrictions.eq("processDefinitionId", processDefinitionId))
		        .addOrder(Order.asc("sequenceNumber"));
		
		String searchTextMode;
		if(taskDefinitionName != null && taskDefinitionName.trim().isEmpty() == false){
			searchTextMode = "%" + taskDefinitionName + "%";
			criteria.add(Restrictions.like("taskDefinitionName",searchTextMode));
		}
		if(scenarioItemName != null && scenarioItemName.trim().isEmpty() == false){
			searchTextMode = "%" + scenarioItemName + "%";
			criteria.add(Restrictions.like("scenarioItemName",searchTextMode));
		}
		if(identityLinkName != null && identityLinkName.trim().isEmpty() == false){
			searchTextMode = "%" + identityLinkName + "%";
			criteria.add(Restrictions.like("identityLinkNames",searchTextMode));
		}
		@SuppressWarnings("unchecked")
		List<TaskDefinitionEntity> entityList = criteria.list();
	
		for(TaskDefinitionEntity entity : entityList){
			entity.setIdentityLinkNames(null);
			
			identityLinkNames = assignMap.get(entity.getTaskDefinitionKey()+entity.getStrategyId()+entity.getScenarioItemId());
			if(identityLinkNames == null){
				continue;
			}
			
			entity.setIdentityLinkNames(identityLinkNames.toString());
		}
		
		return entityList;
	}

	@Override
	public List<TaskDefinitionEntity> getTaskDefinitionListNew(String processDefinitionId, String taskDefinitionName,String scenarioItemName, String identityLinkName){
		Criteria criteria = this.assignDao.getSession().createCriteria(AssignmentEntity.class);
		criteria.add(Restrictions.eq("processDefinitionId", processDefinitionId));
		@SuppressWarnings("unchecked")
		List<AssignmentEntity> assignList = criteria.list();
		
		//key:taskDefinationKey+strategyId value:identityLinkNames(经办人，以逗号分隔)
		Map<String, StringBuffer> assignMap = new HashMap<String, StringBuffer>();
		StringBuffer identityLinkNames = new StringBuffer();
		for(AssignmentEntity entity:assignList){
			
			identityLinkNames = new StringBuffer();
			String mode = entity.getMode();
			if(null!=mode&&"autoMode".equals(mode))
			{
				identityLinkNames.append("自动选人规则");
				assignMap.put(entity.getTaskDefinitionKey()+entity.getStrategyId()+entity.getScenarioItemId(),identityLinkNames);
				continue;
			}
			if(null!=entity.getLinkOrgName())
			{
				identityLinkNames.append(entity.getLinkOrgName());
			}
			if(null!=entity.getLinkJobName())
			{
				identityLinkNames.append(entity.getLinkJobName());
			}
			if(null!=entity.getLinkPositionName())
			{
				identityLinkNames.append(entity.getLinkPositionName());
			}
			if(null!=entity.getLinkPersonName())
			{
				identityLinkNames.append(entity.getLinkPersonName());
			}
			assignMap.put(entity.getTaskDefinitionKey()+entity.getStrategyId()+entity.getScenarioItemId(),identityLinkNames);
		}
//		if(null!=assignList&&assignList.size()!=0)
//		{
//			AssignmentEntity entity = assignList.get(0);
//			if(null!=entity.getLinkOrgName())
//			{
//				identityLinkNames.append(entity.getLinkOrgName());
//			}
//			if(null!=entity.getLinkJobName())
//			{
//				identityLinkNames.append(entity.getLinkJobName());
//			}
//			if(null!=entity.getLinkPositionName())
//			{
//				identityLinkNames.append(entity.getLinkPositionName());
//			}
//			if(null!=entity.getLinkPersonName())
//			{
//				identityLinkNames.append(entity.getLinkPersonName());
//			}
//			assignMap.put(entity.getTaskDefinitionKey()+entity.getStrategyId()+entity.getScenarioItemId(),identityLinkNames);
//		}
		criteria = this.taskDefDao.getSession().createCriteria(TaskDefinitionEntity.class);
		
		criteria.add(Restrictions.eq("processDefinitionId", processDefinitionId))
		        .addOrder(Order.asc("sequenceNumber"));
		
		String searchTextMode;
		if(taskDefinitionName != null && taskDefinitionName.trim().isEmpty() == false){
			searchTextMode = "%" + taskDefinitionName + "%";
			criteria.add(Restrictions.like("taskDefinitionName",searchTextMode));
		}
		if(scenarioItemName != null && scenarioItemName.trim().isEmpty() == false){
			searchTextMode = "%" + scenarioItemName + "%";
			criteria.add(Restrictions.like("scenarioItemName",searchTextMode));
		}
		if(identityLinkName != null && identityLinkName.trim().isEmpty() == false){
			searchTextMode = "%" + identityLinkName + "%";
			criteria.add(Restrictions.like("identityLinkNames",searchTextMode));
		}
		@SuppressWarnings("unchecked")
		List<TaskDefinitionEntity> entityList = criteria.list();
	
		for(TaskDefinitionEntity entity : entityList){
			entity.setIdentityLinkNames(null);
			
			identityLinkNames = assignMap.get(entity.getTaskDefinitionKey()+entity.getStrategyId()+entity.getScenarioItemId());
			if(identityLinkNames == null){
				continue;
			}
			
			entity.setIdentityLinkNames(identityLinkNames.toString());
		}
		
		return entityList;
	}

	@Override
	public List<MonitorEntity> getMonitorList(String processDefinitionId) {
		Criteria criteria = this.assignDao.getSession().createCriteria(MonitorEntity.class);
		criteria.add(Restrictions.eq("processDefinitionId", processDefinitionId));
		@SuppressWarnings("unchecked")
		List<MonitorEntity> monitorList = criteria.list();
		
		//key:processDefinitionId value:identityLinkNames(经办人，以逗号分隔)
		Map<String, StringBuffer> monitorMap = new HashMap<String, StringBuffer>();
		StringBuffer identityLinkNames = null;
		for(MonitorEntity entity : monitorList){
			identityLinkNames = monitorMap.get(entity.getProcessDefinitionId());
			if(identityLinkNames == null){
				identityLinkNames = new StringBuffer(entity.getIdentityLinkNames());
				monitorMap.put(entity.getProcessDefinitionId(), identityLinkNames);
				continue;
			}
			
			identityLinkNames.append(",").append(entity.getIdentityLinkNames());
		}
		
	
		return monitorList;
	}
	
	@Override
	public void saveTaskDefinition(TaskDefinitionEntity taskDefinitionEntity) {
		taskDefDao.save(taskDefinitionEntity);		
	}

	@Override
	public void saveIdentityLink(String processDefinitionId,String taskDefinitionKey, String personIds,String strategyId,String scenarioItemId) {
		Criteria criteriaa = this.taskDefDao.getSession().createCriteria(TaskDefinitionEntity.class);
		criteriaa.add(Restrictions.eq("taskDefinitionKey",taskDefinitionKey))
				 .add(Restrictions.eq("strategyId",strategyId))
				 .add(Restrictions.eq("processDefinitionId",processDefinitionId))
				 .add(Restrictions.eq("scenarioItemId",scenarioItemId));
		TaskDefinitionEntity taskDefinition = (TaskDefinitionEntity) criteriaa.uniqueResult();
		
//		String[] loginNameArray = loginNames.split(",");
		
		String[] personIdArray = personIds.split(",");
		
		Criteria criteria = this.usersDao.getSession().createCriteria(User.class);
//		criteria.add(Restrictions.in("loginName",loginNameArray));
		criteria.add(Restrictions.in("extendId",personIdArray));
		
		@SuppressWarnings("unchecked")
		List<User> userList = criteria.list();
		
		List<AssignmentEntity> assignmentList = new ArrayList<AssignmentEntity>();
		AssignmentEntity assignmentEntity = null;
		for (User user : userList) {
			assignmentEntity = new AssignmentEntity();
			assignmentEntity.setId(null);
//			assignmentEntity.setIdentityLinkId(user.getLoginName());
			assignmentEntity.setIdentityLinkId(user.getExtendId());
			assignmentEntity.setIdentityLinkName(user.getName());
			assignmentEntity.setProcessDefinitionId(processDefinitionId);
			assignmentEntity.setTaskDefinitionKey(taskDefinitionKey);
			assignmentEntity.setTypeId(BpmDefinitionService.ASSIGN_TYPE_USER);
			assignmentEntity.setTypeName("用户");
			assignmentEntity.setStrategyName(taskDefinition.getStrategyName());
			assignmentEntity.setStrategyId(taskDefinition.getStrategyId());
			assignmentEntity.setScenarioItemId(taskDefinition.getScenarioItemId());
			assignmentEntity.setScenarioItemName(taskDefinition.getScenarioItemName());
			
			assignmentList.add(assignmentEntity);
		}
		
		criteria = this.assignDao.getSession().createCriteria(AssignmentEntity.class);
		criteria.add(Restrictions.eq("processDefinitionId", processDefinitionId))
				.add(Restrictions.eq("taskDefinitionKey", taskDefinitionKey))
				.add(Restrictions.eq("strategyId", strategyId))
				.add(Restrictions.eq("scenarioItemId", scenarioItemId));
		
		@SuppressWarnings("unchecked")
		List<AssignmentEntity> assignmentListOld =  criteria.list();
		if(assignmentListOld != null && assignmentListOld.isEmpty() == false){
			assignDao.delete(assignmentListOld);
		}
		assignDao.save(assignmentList);		
	}
	public void saveIdentityLink(AssignmentEntity assignmentEntity,String linkData){

		
		Criteria criteriaa = this.taskDefDao.getSession().createCriteria(TaskDefinitionEntity.class);
		criteriaa.add(Restrictions.eq("taskDefinitionKey",assignmentEntity.getTaskDefinitionKey()))
				 .add(Restrictions.eq("strategyId",assignmentEntity.getStrategyId()))
				 .add(Restrictions.eq("processDefinitionId",assignmentEntity.getProcessDefinitionId()))
				 .add(Restrictions.eq("scenarioItemId",assignmentEntity.getScenarioItemId()));
		TaskDefinitionEntity taskDefinition = (TaskDefinitionEntity) criteriaa.uniqueResult();
		
		transactionLinkData(assignmentEntity, linkData);
//		@SuppressWarnings("unchecked")
//			assignmentEntity = new AssignmentEntity();
			assignmentEntity.setId(null);
//			assignmentEntity.setLinkPerson(linkPerson);
//			assignmentEntity.setLinkOrganization(linkOrganization);
//			assignmentEntity.setLinkJob(linkJob);
			assignmentEntity.setProcessDefinitionId(assignmentEntity.getProcessDefinitionId());
			assignmentEntity.setTaskDefinitionKey(assignmentEntity.getTaskDefinitionKey());
//			assignmentEntity.setTypeId(BpmDefinitionService.ASSIGN_TYPE_USER);
//			assignmentEntity.setTypeName("用户");
			assignmentEntity.setStrategyName(taskDefinition.getStrategyName());
			assignmentEntity.setStrategyId(taskDefinition.getStrategyId());
			assignmentEntity.setScenarioItemId(taskDefinition.getScenarioItemId());
			assignmentEntity.setScenarioItemName(taskDefinition.getScenarioItemName());
			
		
		Criteria criteria = this.assignDao.getSession().createCriteria(AssignmentEntity.class);
		criteria.add(Restrictions.eq("processDefinitionId", assignmentEntity.getProcessDefinitionId()))
				.add(Restrictions.eq("taskDefinitionKey", assignmentEntity.getTaskDefinitionKey()))
				.add(Restrictions.eq("strategyId", assignmentEntity.getStrategyId()))
				.add(Restrictions.eq("scenarioItemId", assignmentEntity.getScenarioItemId()));
		
		@SuppressWarnings("unchecked")
		List<AssignmentEntity> assignmentListOld =  criteria.list();
		if(assignmentListOld != null && assignmentListOld.isEmpty() == false){
			assignDao.delete(assignmentListOld);
		}
		assignDao.save(assignmentEntity);	
	}
	
	public AssignmentEntity transactionLinkData(AssignmentEntity assignmentEntity,String linkData){
		//将linkData json串转化为实体
		JSONArray json = JSONArray.fromObject(linkData);
        List<IdentityLinkEntity> identityLinkEntityList = new ArrayList<IdentityLinkEntity>(json.size());
        List<Map<String,Object>> mapListJson = (List)json;  
        for(int i = 0; i < mapListJson.size(); ++i){
        	Map<String,Object> obj = mapListJson.get(i);
        	IdentityLinkEntity identityLinkEntity = new IdentityLinkEntity((String)obj.get("personId"), (String)obj.get("orgId"),  (String)obj.get("jobId"),  (String)obj.get("type"),  (String)obj.get("name"));
        	identityLinkEntityList.add(identityLinkEntity);
        }
        
        StringBuilder linkPerson = new StringBuilder();
        StringBuilder linkPersonName = new StringBuilder();
        StringBuilder linkOrganization = new StringBuilder();
        StringBuilder linkOrgName = new StringBuilder();
        StringBuilder linkJob = new StringBuilder();
        StringBuilder linkJobName = new StringBuilder();
        StringBuilder linkPosition = new StringBuilder();
        StringBuilder linkPositionName = new StringBuilder();
        
        
        for(IdentityLinkEntity identityLinkEntity:identityLinkEntityList)
        {
        	if(IdentityLinkEntity.PERSON.equals(identityLinkEntity.getType()))
        	{
        		linkPerson.append(identityLinkEntity.getPersonId()).append(";");
        		linkPersonName.append(identityLinkEntity.getName()).append(";");
        	}
        	else if(IdentityLinkEntity.ORG.equals(identityLinkEntity.getType()))
        	{
        		linkOrganization.append(identityLinkEntity.getOrgId()).append(";");
        		linkOrgName.append(identityLinkEntity.getName()).append(";");
        	}
        	else if(IdentityLinkEntity.JOB.equals(identityLinkEntity.getType()))
        	{
        		linkJob.append(identityLinkEntity.getJobId()).append(";");
        		linkJobName.append(identityLinkEntity.getName()).append(";");
        	}
        	else
        	{
        		linkPosition.append(identityLinkEntity.getOrgId()).append(",").append(identityLinkEntity.getJobId()).append(";");
        		linkPositionName.append(identityLinkEntity.getName()).append(";");
        	}
        }
        assignmentEntity.setLinkPerson(linkPerson.toString());
        assignmentEntity.setLinkPersonName(linkPersonName.toString());
        assignmentEntity.setLinkOrganization(linkOrganization.toString());
        assignmentEntity.setLinkOrgName(linkOrgName.toString());
        assignmentEntity.setLinkJob(linkJob.toString());
        assignmentEntity.setLinkJobName(linkJobName.toString());
        assignmentEntity.setLinkPosition(linkPosition.toString());
        assignmentEntity.setLinkPositionName(linkPositionName.toString());
        
		return assignmentEntity;
	}

	@Override
	public TaskDefinitionEntity getTaskDefinitionKey(String processDefinitionId) {
		String sql = "select c from TaskDefinitionEntity c where c.processDefinitionId = ?";
		TaskDefinitionEntity key = assignDao.findUnique(sql, processDefinitionId);
		return key;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<UserTreeNode>> buildUserTree(HttpServletRequest request, String processDefinitionId, String taskDefinitionKey,String scenarioItemId,String raName, String userName) {
		//获取所有注册机构
		List<RegistrationAuthority> raList = raDao.getAll();
		if(raName != null){
			raList = raDao.find(Restrictions.like("name", "%"+raName+"%"));
		}
		
		
		List<UserTreeNode> fromList = new ArrayList<UserTreeNode>();
		List<UserTreeNode> toList = new ArrayList<UserTreeNode>();
		
		int id = 0;
		
		//构造fromList
		
		//构造注册机构用户
		User userRa = securityService.getCurrentUser(request);
		String userRaId = userRa.getRaId();
		if(userRaId != null){
			raList = new ArrayList<RegistrationAuthority>();
			RegistrationAuthority ra = raDao.findUniqueBy("id", userRaId);
			if(ra != null){
				raList.add(ra);
			}
		}
		
		Map<String, UserTreeNode> raNodeMap = new HashMap<String, UserTreeNode>();
		UserTreeNode node = null;
		for(RegistrationAuthority ra: raList){
			node = new UserTreeNode(ra, Integer.toString(id));
			//node = new UserTreeNode(ra, ra.getId());
			fromList.add(node);
			
			id = id + 1;
			
			raNodeMap.put(ra.getId(), node);
		}		
		
		//构造无注册机构用户节点
		Criteria criteria = this.usersDao.getSession().createCriteria(User.class);
		criteria.add(Restrictions.isNull("raId"));
		criteria.add(Restrictions.isNotNull("extendId"));
		//criteria.add(Restrictions.like("name","%"+userName+"%"));
		
		List<User> userList = criteria.list();
		Map<String, UserTreeNode> userNodeMap = new HashMap<String, UserTreeNode>();
		for(User user : userList){
			node = new UserTreeNode(user, Integer.toString(id), "-1");
			fromList.add(node);
			id = id + 1;
			
			userNodeMap.put(user.getExtendId(), node);
		}
		

		//构造有注册机构用户节点
		criteria = this.usersDao.getSession().createCriteria(User.class);
		criteria.add(Restrictions.isNotNull("raId"));
		criteria.add(Restrictions.isNotNull("extendId"));
		if(userName != null){
			criteria.add(Restrictions.like("name","%"+userName+"%"));
		}
		
		
		userList = criteria.list();
		UserTreeNode parentNode = null;
		for(User user : userList){
			parentNode = raNodeMap.get(user.getRaId());
			if(parentNode == null){
				continue;
			}
			//node = new UserTreeNode(user, user.getId(), parentNode.getId());
			node = new UserTreeNode(user, Integer.toString(id), parentNode.getId());
			
			fromList.add(node);
			
			id = id + 1;
			
			userNodeMap.put(user.getExtendId(), node);
		}
		//构造toList
		criteria = this.assignDao.getSession().createCriteria(AssignmentEntity.class);
		criteria.add(Restrictions.eq("processDefinitionId", processDefinitionId))
		        .add(Restrictions.eq("taskDefinitionKey", taskDefinitionKey))
		        .add(Restrictions.eq("typeId", BpmDefinitionService.ASSIGN_TYPE_USER));
		List<AssignmentEntity> assignList = criteria.list();
		for(AssignmentEntity assign : assignList){
			node = userNodeMap.get(assign.getIdentityLinkId());
			if(node == null){
				continue;
			}
			
			fromList.remove(node);
			node.setParentId("-1");
			toList.add(node);
		}
			
		//构造Map
		Map<String, List<UserTreeNode>> treeMap = new HashMap<String, List<UserTreeNode>>();
		treeMap.put("fromList", fromList);
		treeMap.put("toList", toList);
		
		return treeMap;
	}

	@Override
	public void saveMonitorIdentityLink(String processDefinitionId,
			String loginNames) {
		String[] loginNameArray = loginNames.split(",");
		
		Criteria criteria = this.usersDao.getSession().createCriteria(User.class);
		criteria.add(Restrictions.in("loginName",loginNameArray));
		
		@SuppressWarnings("unchecked")
		List<User> userList = criteria.list();
		
		List<MonitorEntity> monitorList = new ArrayList<MonitorEntity>();
		MonitorEntity monitorEntity = null;
		for (User user : userList) {
			monitorEntity = new MonitorEntity();
			monitorEntity.setId(null);
			monitorEntity.setIdentityLinkId(user.getLoginName());
			monitorEntity.setIdentityLinkNames(user.getName());
			monitorEntity.setRaId(user.getRaId());
			monitorEntity.setRaCode(user.getRaCode());
			monitorEntity.setRaName(user.getRaName());
			monitorEntity.setProcessDefinitionId(processDefinitionId);
			monitorEntity.setTypeId(BpmDefinitionService.ASSIGN_TYPE_USER);
			monitorEntity.setTypeName("用户");
			monitorList.add(monitorEntity);
		}
		
		criteria = this.monitorDao.getSession().createCriteria(MonitorEntity.class);
		criteria.add(Restrictions.eq("processDefinitionId", processDefinitionId));
		
//		@SuppressWarnings("unchecked")
//		List<MonitorEntity> monitorListOld =  criteria.list();
//		if(monitorListOld != null && monitorListOld.isEmpty() == false){
//			monitorDao.delete(monitorListOld);
//		}
		monitorDao.save(monitorList);	
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<UserTreeNode>> buildMonitorUserTree(String processDefinitionId) {
		List<UserTreeNode> fromList = new ArrayList<UserTreeNode>();
		List<UserTreeNode> toList = new ArrayList<UserTreeNode>();
		
		int id = 0;
		
		//构造fromList
		
		//构造注册机构用户
		List<RegistrationAuthority> raList = raDao.getAll();
		Map<String, UserTreeNode> raNodeMap = new HashMap<String, UserTreeNode>();
		UserTreeNode node = null;
		for(RegistrationAuthority ra: raList){
			node = new UserTreeNode(ra, Integer.toString(id));
			fromList.add(node);
			
			id = id + 1;
			
			raNodeMap.put(ra.getId(), node);
		}		
		
		//构造无注册机构用户节点
		Criteria criteria = this.usersDao.getSession().createCriteria(User.class);
		criteria.add(Restrictions.isNull("raId"));
		
		List<User> userList = criteria.list();
		Map<String, UserTreeNode> userNodeMap = new HashMap<String, UserTreeNode>();
		for(User user : userList){
			node = new UserTreeNode(user, Integer.toString(id), "-1");
			fromList.add(node);
			id = id + 1;
			
			userNodeMap.put(user.getLoginName(), node);
		}
		

		//构造有注册机构用户节点
		criteria = this.usersDao.getSession().createCriteria(User.class);
		criteria.add(Restrictions.isNotNull("raId"));
		
		
		userList = criteria.list();
		UserTreeNode parentNode = null;
		for(User user : userList){
			parentNode = raNodeMap.get(user.getRaId());
			if(parentNode == null){
				continue;
			}
			node = new UserTreeNode(user, Integer.toString(id), parentNode.getId());
			fromList.add(node);
			
			id = id + 1;
			
			userNodeMap.put(user.getLoginName(), node);
		}
		//构造toList
		criteria = this.monitorDao.getSession().createCriteria(MonitorEntity.class);
		criteria.add(Restrictions.eq("processDefinitionId", processDefinitionId))
		        .add(Restrictions.eq("typeId", BpmDefinitionService.ASSIGN_TYPE_USER));
		List<MonitorEntity> monitorList = criteria.list();
		for(MonitorEntity monitor : monitorList){
			node = userNodeMap.get(monitor.getIdentityLinkId());
			if(node == null){
				continue;
			}
			
			fromList.remove(node);
			node.setParentId("-1");
			toList.add(node);
		}
		//构造Map
		Map<String, List<UserTreeNode>> treeMap = new HashMap<String, List<UserTreeNode>>();
		treeMap.put("fromList", fromList);
		treeMap.put("toList", toList);
				
		return treeMap;
	}

	@Override
	public void deleteMonitorById(String id) {
		monitorDao.delete(id);		
	}

	@Override
	public Set<String> getProcessDefinitionIdList(String loginName,String categoryCode) {
		Criteria criteria = this.monitorDao.getSession().createCriteria(MonitorEntity.class);
		criteria.add(Restrictions.eq("identityLinkId", loginName))
				.add(Restrictions.eq("typeId", "identitylink.category.user"));
		
		@SuppressWarnings("unchecked")
		List<MonitorEntity> monitorList = criteria.list();
		List<ProcessDefinitionManagementEntity> processDefinitionIdList = ProcessDefinitionManagementService.getByCategoryCode(categoryCode);
		
		Set<String> processDefinitionIdSet = new HashSet<String>();
		for(MonitorEntity monitor : monitorList){
			if(monitor == null || monitor.getProcessDefinitionId() == null){
				continue;
			}
			for (ProcessDefinitionManagementEntity processDefinitionManagement : processDefinitionIdList) {
				if(processDefinitionManagement == null || processDefinitionManagement.getId() == null){
					continue;
				}
				if(monitor.getProcessDefinitionId().equals(processDefinitionManagement.getId()) == true){
					processDefinitionIdSet.add(monitor.getProcessDefinitionId());
				}
			}
			
		}
		return processDefinitionIdSet;
	}

	@Override
	public TaskDefinitionEntity getTaskDefinition(String taskDefinitionKey,
			String strategyId,String processDefinitionId,String scenarioItemId) {
		Criteria criteria = this.taskDefDao.getSession().createCriteria(TaskDefinitionEntity.class);
		
		TaskDefinitionEntity taskDefinition = (TaskDefinitionEntity) criteria.add(Restrictions.eq("taskDefinitionKey", taskDefinitionKey))
													  .add(Restrictions.eq("strategyId", strategyId))
													  .add(Restrictions.eq("processDefinitionId", processDefinitionId))
													  .add(Restrictions.eq("scenarioItemId", scenarioItemId))
													  .uniqueResult();
				
		return taskDefinition;
	}

	@Override
	public TaskDefinitionEntity getbybpmTaskDefinitionId(String id) {
		TaskDefinitionEntity taskDefinitionEntity = taskDefDao.get(id);
		return taskDefinitionEntity;
	}

	@Override
	public void deleteTaskDefinitionsById(String id) {
		taskDefDao.delete(id);		
	}

	@Override
	public AssignmentEntity getAssignmentEntity(String processDefinitionId,
			String taskDefinitionKey, String strategyId, String scenarioItemId) {
		Criteria criteriaa = this.assignDao.getSession().createCriteria(AssignmentEntity.class);
		
		AssignmentEntity assignment = (AssignmentEntity) criteriaa.add(Restrictions.eq("taskDefinitionKey", taskDefinitionKey))
													  .add(Restrictions.eq("strategyId", strategyId))
													  .add(Restrictions.eq("processDefinitionId", processDefinitionId))
													  .add(Restrictions.eq("scenarioItemId", scenarioItemId))
													  .uniqueResult();
				
		return assignment;
	}


	@Override
	public List<AssignmentEntity> getAssignmentEntityList(String processDefinitionId,
			String taskDefinitionKey, String strategyId, String scenarioItemId){

		Criteria criteriaa = this.assignDao.getSession().createCriteria(AssignmentEntity.class);
		
		@SuppressWarnings("unchecked")
		List<AssignmentEntity> assignmentList = (List<AssignmentEntity>) criteriaa.add(Restrictions.eq("taskDefinitionKey", taskDefinitionKey))
													  .add(Restrictions.eq("strategyId", strategyId))
													  .add(Restrictions.eq("processDefinitionId", processDefinitionId))
													  .add(Restrictions.eq("scenarioItemId", scenarioItemId)).list();
				
		return assignmentList;
	}
	
	@Override
	public void deleteAssignmentById(String id) {
		assignDao.delete(id);		
	}

	@Override
	public List<IdentityLinkEntity> tranLinkToJson(AssignmentEntity assignmentEntity) {
		List<IdentityLinkEntity> identityLinkEntityList = new ArrayList<IdentityLinkEntity>();
		IdentityLinkEntity identityLink = null;
		String linkOrganization = assignmentEntity.getLinkOrganization();
		String linkJob = assignmentEntity.getLinkJob();
		String linkPerson = assignmentEntity.getLinkPerson();
		String linkPosition = assignmentEntity.getLinkPosition();
		if(null!=linkOrganization&&!"".equals(linkOrganization))
		{
			String linkOrgName = assignmentEntity.getLinkOrgName();
			String[] orgIds = linkOrganization.split(";");
			String[] orgNames = linkOrgName.split(";");
			for(int i = 0;i<orgIds.length;i++){
				identityLink = new IdentityLinkEntity(null, orgIds[i], null, IdentityLinkEntity.ORG, orgNames[i]);
				identityLinkEntityList.add(identityLink);
			}
		}
		if(null!=linkJob&&!"".equals(linkJob)){
			String linkJobName = assignmentEntity.getLinkJobName();
			String[] jobIds = linkJob.split(";");
			String[] jobNames = linkJobName.split(";");
			for(int i = 0;i<jobIds.length;i++){
				identityLink = new IdentityLinkEntity(null, null, jobIds[i], IdentityLinkEntity.JOB, jobNames[i]);
				identityLinkEntityList.add(identityLink);
			}
			
		}
		if(null!=linkPerson&&!"".equals(linkPerson)){
			String linkPersonName = assignmentEntity.getLinkPersonName();
			String[] personIds = linkPerson.split(";");
			String[] personNames = linkPersonName.split(";");
			for(int i = 0;i<personIds.length;i++){
				identityLink = new IdentityLinkEntity(personIds[i], null, null, IdentityLinkEntity.PERSON, personNames[i]);
				identityLinkEntityList.add(identityLink);
			}
		}

		if(null!=linkPosition&&!"".equals(linkPosition)){
			String linkPositionName = assignmentEntity.getLinkPositionName();
			String[] positionIds = linkPosition.split(";");
			String[] positionNames = linkPositionName.split(";");
			for(int i = 0;i<positionIds.length;i++){
				String positionId = positionIds[i];
				String[] ids = positionId.split(",");

				identityLink = new IdentityLinkEntity(null, ids[0], ids[1], IdentityLinkEntity.POSITION, positionNames[i]);
				identityLinkEntityList.add(identityLink);
			}
		}
		return identityLinkEntityList;
	}

	

}
