/* <p>文件名称: BpmServiceImpl.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-7-27</p>
 * <p>完成日期：2013-7-27</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-7-27 上午4:50:17
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.proper.hr.personnel.entity.UserPositionInfo;
import com.proper.hr.personnel.service.IUserPositionNoTransactionalService;
import com.proper.uip.api.bpm.definitions.entity.AssignmentEntity;
import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionOrganizationRelation;
import com.proper.uip.api.bpm.extension.BpmAutoRuleExtension;
import com.proper.uip.api.bpm.extension.BpmFilterRuleExtension;
import com.proper.uip.api.bpm.service.BpmScenarioStrategy;
import com.proper.uip.api.bpm.service.BpmService;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.definitions.dao.BpmAssignmentDao;
import com.proper.uip.definitions.dao.ProcessDefinitionOrganizationRelationDao;
import com.proper.uip.security.dao.UsersDao;


@Service("bpmService")
public class BpmServiceImpl implements BpmService {
	@Autowired 
	private BpmAssignmentDao assignDao;
	
	@Autowired 
	private ProcessDefinitionOrganizationRelationDao processDefinitionOrganizationRelationDao;

	@Autowired
	private IUserPositionNoTransactionalService userPositionService;

	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private List<BpmFilterRuleExtension> bpmFilterRuleExtensionList;
	
	@Autowired
	private List<BpmAutoRuleExtension> bpmAutoRuleExtensionList;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private HistoryService historyService;

	
	//需注入
	@SuppressWarnings("serial")
	private List<String> assignmentTypesOfCadidateUser = new ArrayList<String>(){{
		super.add("identitylink.category.user");
		super.add("identitylink.category.person");
	}};
	//需注入
	@SuppressWarnings("serial")
	private List<String> assignmentTypesOfCadidateGroup = new ArrayList<String>(){{
		super.add("identitylink.category.persition");
	}};
	
	@Autowired
	private Map<String, BpmScenarioStrategy> scenarioStrategyMap;
	

	@Override
	public Set<String> getCandiateUsers(String processDefinitionId, String taskDefinitionKey, String scenarioItem) {
		Criteria criteria = this.assignDao.getSession().createCriteria(AssignmentEntity.class);
		criteria.add(Restrictions.eq("processDefinitionId", processDefinitionId))
		        .add(Restrictions.in("typeId", assignmentTypesOfCadidateUser))
		        .add(Restrictions.eq("taskDefinitionKey", taskDefinitionKey));
				
		@SuppressWarnings("unchecked")
		List<AssignmentEntity> entityList = criteria.list();
		
		Set<String> candidateUsers = new HashSet<String>();
		
		BpmScenarioStrategy scenarioStrategy = null;
		boolean checked = true;
		for(AssignmentEntity entity : entityList){
			checked = true;
			scenarioStrategy = this.scenarioStrategyMap.get(entity.getStrategyId());
			if(scenarioStrategy != null){
				checked = scenarioStrategy.check(scenarioItem, entity);
			}
			
			if(checked == true){
				candidateUsers.add(entity.getIdentityLinkId());
			}
		}
		
		
		return candidateUsers;
	}
	
	@Override
	public Set<String> getCandiateUsers(String processDefinitionId,
			String taskDefinitionKey) {
		Criteria criteria = this.assignDao.getSession().createCriteria(AssignmentEntity.class);
		criteria.add(Restrictions.eq("processDefinitionId", processDefinitionId))
		        .add(Restrictions.in("typeId", assignmentTypesOfCadidateUser))
		        .add(Restrictions.eq("taskDefinitionKey", taskDefinitionKey));
				
		@SuppressWarnings("unchecked")
		List<AssignmentEntity> entityList = criteria.list();
		
		Set<String> candidateUsers = new HashSet<String>();
		
		for(AssignmentEntity entity : entityList){
			candidateUsers.add(entity.getIdentityLinkId());
		}
		
		
		return candidateUsers;
	}
	
	@Override
	public Set<String> getCandiateUsersNew(DelegateExecution  execution,
			String taskDefinitionKey) {
		Criteria criteria = this.assignDao.getSession().createCriteria(AssignmentEntity.class);
		criteria.add(Restrictions.eq("processDefinitionId", execution.getProcessDefinitionId()))
//		        .add(Restrictions.in("typeId", assignmentTypesOfCadidateUser))
		        .add(Restrictions.eq("taskDefinitionKey", taskDefinitionKey));
		
		Set<String> candidateUsers = new HashSet<String>();
		
		@SuppressWarnings("unchecked")
		List<AssignmentEntity> entityList = criteria.list();
		
		if(null!=entityList&&entityList.size()!=0){
			String authenticatedUserId = (String)execution.getVariables().get("initiator");
//			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(execution.getProcessInstanceId()).singleResult();
			
//			List<HistoricProcessInstance> historicProcessInstanceList = historyService.createHistoricProcessInstanceQuery().processInstanceId(execution.getProcessInstanceId()).list();
//			if(historicProcessInstanceList.size()!=0){
//				authenticatedUserId = historicProcessInstanceList.get(0).getStartUserId();
//			}
			
			AssignmentEntity assignmentEntity = entityList.get(0);
			String mode = assignmentEntity.getMode();
			String autoRule = assignmentEntity.getAutoRule();
			if(isNotEmpty(mode)&&mode.equals("autoMode")){
				for(BpmAutoRuleExtension bpmAutoRuleExtension:bpmAutoRuleExtensionList){
					if(bpmAutoRuleExtension.getId().equals(autoRule)){
						candidateUsers = bpmAutoRuleExtension.parse(authenticatedUserId);
					}
				}
			}
			else{
				candidateUsers = getCandiateIds(assignmentEntity);
				//获取此条记录的选人过滤规则，并对已选中的人进行过滤
				String filterRule = assignmentEntity.getFilterRule();
				if(isNotEmpty(filterRule)){
					for(BpmFilterRuleExtension bpmFilterRuleExtension:bpmFilterRuleExtensionList){
						if(bpmFilterRuleExtension.getId().equals(filterRule)){
							candidateUsers = bpmFilterRuleExtension.parse(candidateUsers,authenticatedUserId);
						}
					}
				}
			}
		}
		
//		
//		for(AssignmentEntity entity : entityList){
//			candidateUsers.add(entity.getIdentityLinkId());
//		}
		
		
		return candidateUsers;
	}
	public Set<String> getCandiateUsersNotStart(String processDefinitionId, String taskDefinitionKey){
		
		Criteria criteria = this.assignDao.getSession().createCriteria(AssignmentEntity.class);
		criteria.add(Restrictions.eq("processDefinitionId", processDefinitionId))
//		        .add(Restrictions.in("typeId", assignmentTypesOfCadidateUser))
		        .add(Restrictions.eq("taskDefinitionKey", taskDefinitionKey));
		
		Set<String> candidateUsers = new HashSet<String>();
		
		@SuppressWarnings("unchecked")
		List<AssignmentEntity> entityList = criteria.list();
		
		if(null!=entityList&&entityList.size()!=0){
			
			AssignmentEntity assignmentEntity = entityList.get(0);
			candidateUsers = getCandiateIds(assignmentEntity);
				//获取此条记录的选人过滤规则，并对已选中的人进行过滤
		}
		
//		
//		for(AssignmentEntity entity : entityList){
//			candidateUsers.add(entity.getIdentityLinkId());
//		}
		
		
		return candidateUsers;
	}
	
	private Set<String> getCandiateIds(AssignmentEntity assignmentEntity){
	    List<String> personIdList = new ArrayList<String>();
	    List<UserPositionInfo> userPositionInfoList = new ArrayList<UserPositionInfo>();
		String linkOrg = assignmentEntity.getLinkOrganization();
		String linkJob = assignmentEntity.getLinkJob();
		String linkPerson = assignmentEntity.getLinkPerson();
		String linkPosition = assignmentEntity.getLinkPosition();
		if(isNotEmpty(linkPosition)){
			String[] positionIds = linkPosition.split(";");
			for(int i=0;i<positionIds.length;i++){
				String[] ids = positionIds[i].split(",");
				List<UserPositionInfo> list = userPositionService.getOrganizationUserPositionInfoByJobIdAndDates(getCurrentUser(), ids[0], ids[1], new Date(), new Date());
				userPositionInfoList.addAll(list);
			}
		}
		if(isNotEmpty(linkOrg)){
			String[] orgIds = linkOrg.split(";");
			for(String orgId:orgIds)
			{
				userPositionInfoList.addAll(userPositionService.getOrganizationUserPositionInfoByDates(getCurrentUser(), orgId, new Date(), new Date()));
			    
			}
		}
		if(isNotEmpty(linkJob)){
			String[] jobIds = linkJob.split(";");
			for(String jobId:jobIds){
				userPositionInfoList.addAll(userPositionService.getOrganizationUserPositionInfoByJobIdAndDates(getCurrentUser(), null, jobId, new Date(), new Date()));
			}
		}
		if(null!=userPositionInfoList&&userPositionInfoList.size()!=0)
	    {
			for(UserPositionInfo userPositionInfo:userPositionInfoList)
		    {
				personIdList.add(userPositionInfo.getUserPosition().getPersonId());
		    }
	    }
		if(isNotEmpty(linkPerson)){
			String[] personIds = linkPerson.split(";");
			personIdList.addAll(Arrays.asList(personIds));
		}
		Set<String> personIdset=new HashSet<String>();         
		personIdset.addAll(personIdList);//给set填充         
//		personIdList.clear();//清空list，不然下次把set元素加入此list的时候是在原来的基础上追加元素的         
//		personIdList.addAll(personIdset);//把set的     
		return personIdset;
	}

	protected User getCurrentUser(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(principal instanceof org.springframework.security.core.userdetails.User == false){
			return null;
		}
		
		org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) principal;   
		String userName = springUser.getUsername();   
		
		//User user = userService.getUserByLoginName(userName);
		User user = usersDao.findUniqueBy("account", userName);
//		User user = userService.getUserByAccount(userName);
		
		return user;
	}
	private boolean isNotEmpty(String data){
		boolean flag = false;
		if(null!=data && !"".equals(data)){
			flag = true;
		}
		return flag;
	}
	
	@Override
	public Set<String> getCandiateGroups(String processDefinitionId, String taskDefinitionKey) {
		Criteria criteria = this.assignDao.getSession().createCriteria(AssignmentEntity.class);
		criteria.add(Restrictions.eq("processDefinitionId", processDefinitionId))
		        .add(Restrictions.in("typeId", assignmentTypesOfCadidateGroup));
		
		@SuppressWarnings("unchecked")
		List<AssignmentEntity> entityList = criteria.list();
		
		Set<String> candidateGroups = new HashSet<String>();
		for(AssignmentEntity entity : entityList){
			candidateGroups.add(entity.getIdentityLinkId());
		}
		
		return candidateGroups;
	}

	@Override
	public String getProcessDefinitionKey(String organizationCode,
			String resourceCode) {
		ProcessDefinitionOrganizationRelation processDefinitionOrganizationRelation = this.getProcessDefinition(organizationCode, resourceCode);
		
		if(processDefinitionOrganizationRelation == null){
			return null;
		}
		
		return processDefinitionOrganizationRelation.getProcessDefinitionKey();
	}

	@Override
	public String getProcessDefinitionId(String organizationCode,
			String resourceCode) {
		ProcessDefinitionOrganizationRelation processDefinitionOrganizationRelation = this.getProcessDefinition(organizationCode, resourceCode);
		
		if(processDefinitionOrganizationRelation == null){
			return null;
		}
		
		return processDefinitionOrganizationRelation.getProcessDefinitionId();
	}
	
	private ProcessDefinitionOrganizationRelation getProcessDefinition(String organizationCode,
			String resourceCode) {
		if(organizationCode == null || organizationCode.trim().equals("") == true){
			return null;
		}
		if(resourceCode == null || resourceCode.trim().equals("") == true){
			return null;
		}
		String hql = "select c from ProcessDefinitionOrganizationRelation c where c.organizationCode =? and c.resourceCode =?";
		ProcessDefinitionOrganizationRelation ProcessDefinitionOrganizationRelation = processDefinitionOrganizationRelationDao.findUnique(hql, organizationCode, resourceCode);
		
		return ProcessDefinitionOrganizationRelation;
	}

}
