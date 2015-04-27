/* <p>文件名称: BpmDefinitionsAssignmentController.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-7-25</p>
 * <p>完成日期：2013-7-25</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-7-25 下午1:10:31
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.bpm.definitions.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.hr.job.entity.Job;
import com.proper.hr.job.service.IJobService;
import com.proper.hr.job.utils.enums.JobStatus;
import com.proper.hr.organization.entity.Organization;
import com.proper.hr.organization.service.IOrganizationService;
import com.proper.hr.organization.utils.enums.OrganizationStatus;
import com.proper.hr.personnel.entity.Person;
import com.proper.hr.personnel.entity.UserPositionInfo;
import com.proper.hr.personnel.service.IPersonQueryService;
import com.proper.hr.personnel.service.IPersonService;
import com.proper.hr.personnel.service.IUserPositionService;
import com.proper.uip.api.bpm.definitions.entity.AssignmentEntity;
import com.proper.uip.api.bpm.definitions.entity.ScenarioItem;
import com.proper.uip.api.bpm.definitions.entity.TaskDefinitionEntity;
import com.proper.uip.api.bpm.extension.BpmAutoRuleExtension;
import com.proper.uip.api.bpm.extension.BpmFilterRuleExtension;
import com.proper.uip.api.bpm.service.BpmScenarioStrategy;
import com.proper.uip.bpm.definitions.entity.IdentityLinkEntity;
import com.proper.uip.bpm.definitions.entity.UserTreeNode;
import com.proper.uip.bpm.definitions.service.BpmDefinitionService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.web.BaseController;


@Controller
@RequestMapping(value = "/definitions/assignment")
public class BpmAssignmentController extends BaseController{

    @Autowired
    private IPersonQueryService personQueryService;
    
	@Autowired
	private BpmDefinitionService bpmDefinitionService;
	
	@Autowired
	private  RepositoryService repositoryService;
	
	
	@Autowired
	private List<BpmScenarioStrategy> bpmScenarioStrategyList;
	
	@Autowired
	private Map<String, BpmScenarioStrategy> bpmScenarioStrategyMap;
	
	@Autowired
	private IPersonService personService;
	
	@Autowired
	private IJobService jobService;
	
	@Autowired
	private IOrganizationService organizationService;
	
	@Autowired
	private IUserPositionService userPositionService;
	
	@Autowired
	private List<BpmFilterRuleExtension> bpmFilterRuleExtensionList;
	
	@Autowired
	private List<BpmAutoRuleExtension> bpmAutoRuleExtensionList;
	
	@RequestMapping("/index")
	public String index(ModelMap model) {
		return "/definitions/assignment/index";
	}

	@RequestMapping(value = "/taskDefinitions/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, ModelMap model) {
		//初始化返回值Map
		Map<String, Object> map = new HashMap<String, Object>();
		
		String processDefinitionId = request.getParameter("processDefinitionId");
		String taskDefinitionName = request.getParameter("taskDefinitionName");
		String scenarioItemName = request.getParameter("scenarioItemName");
		String identityLinkNames = request.getParameter("identityLinkNames");
		
		if(processDefinitionId == null){
			return map;
		}
		
		List<TaskDefinitionEntity> entityList = bpmDefinitionService.getTaskDefinitionListNew(processDefinitionId, taskDefinitionName,scenarioItemName,identityLinkNames);
		
		//分页数据转换成Map（QUI只识别Map）
		map.put("pager.pageNo", 1);
		map.put("pager.totalRows", entityList.size());
		map.put("rows", entityList);
				
		return map;
	}
	
	/**
	 * 添加步骤
	 */
	@RequestMapping(value = "/taskDefinitions/add")
	public String add(HttpServletRequest request, ModelMap model) {
		model.put("bpmScenarioStrategyList", bpmScenarioStrategyList);
		String processDefinitionId = request.getParameter("processDefinitionId");
		model.put("processDefinitionId", processDefinitionId);
		return "/definitions/assignment/newTaskDefinition";
	}
	
	/**
	 * 修改步骤
	 */
	@RequestMapping(value = "/taskDefinitions/update")
	public String update(HttpServletRequest request,String id, ModelMap model) {
		
		TaskDefinitionEntity taskDefinition = bpmDefinitionService.getbybpmTaskDefinitionId(id);
		BpmScenarioStrategy bpmScenarioStrategy = bpmScenarioStrategyMap.get(taskDefinition.getStrategyId());
		List<ScenarioItem> scenarioItemList = bpmScenarioStrategy.getScenarioItems();

		String processDefinitionId = request.getParameter("processDefinitionId");
		String scenarioItemId = taskDefinition.getScenarioItemId();
		
		model.put("processDefinitionId", processDefinitionId);
		model.put("scenarioItemList", scenarioItemList);
		model.put("bpmScenarioStrategyList", bpmScenarioStrategyList);
		model.put("taskDefinition", taskDefinition);
		model.put("scenarioItemId", scenarioItemId);
		
		return "/definitions/assignment/updateTaskDefinition";
	}
	
	/**
	 * 删除
	 */

	@RequestMapping(value = "/taskDefinitions/del")
	public String del(String id, String processDefinitionId,String taskDefinitionKey,String strategyId,String scenarioItemId, ModelMap model) {
		try {
			if (StringUtils.isNotEmpty(id)) {
				AssignmentEntity assignment = bpmDefinitionService.getAssignmentEntity(processDefinitionId,taskDefinitionKey,strategyId,scenarioItemId);
				if(assignment != null){
					bpmDefinitionService.deleteAssignmentById(assignment.getId());	
				}
				
				bpmDefinitionService.deleteTaskDefinitionsById(id);
			}
		} catch (Exception e) {
			model.put("status", -1);
			this.setError(model, "删除失败,请联系管理员");
		}
		
		model.put("status", 1);
		
		return SUCCESS;
	}

	/**
	 * 保存步骤
	 */
	@RequestMapping(value = "/taskDefinitions/save")
	@ResponseBody
	public String save(HttpServletRequest request, TaskDefinitionEntity taskDefinitionEntity, ModelMap model) {
		String processDefinitionId = request.getParameter("processDefinitionId");
		String taskDefinitionKey = taskDefinitionEntity.getTaskDefinitionKey();
		String strategyId = taskDefinitionEntity.getStrategyId();
		String scenarioItemId = taskDefinitionEntity.getScenarioItemId();
		TaskDefinitionEntity taskDefinition = bpmDefinitionService.getTaskDefinition(taskDefinitionKey,strategyId,processDefinitionId,scenarioItemId);
		if(taskDefinition != null){
			return "此场景已经设置，请重新选择！";	
		}
		//新增
		if (taskDefinitionEntity.getId() == null || taskDefinitionEntity.getId().equals("")) {
			taskDefinitionEntity.setId(null);
		}
		
		taskDefinitionEntity.setProcessDefinitionId(processDefinitionId);
		bpmDefinitionService.saveTaskDefinition(taskDefinitionEntity);
		return SUCCESS;

	}
	
	/**
	 * 设置经办人用户
	 */
	@RequestMapping(value = "/addUser")
	public String addIdentityLink(HttpServletRequest request, ModelMap model) {
		String processDefinitionId = request.getParameter("processDefinitionId");
		String taskDefinitionKey = request.getParameter("taskDefinitionKey");
		String strategyId = request.getParameter("strategyId");
		String scenarioItemId = request.getParameter("scenarioItemId");
		List<AssignmentEntity> assignmentEntityList = bpmDefinitionService.getAssignmentEntityList(processDefinitionId, taskDefinitionKey, strategyId, scenarioItemId);
		AssignmentEntity entity = new AssignmentEntity();
		if(null != assignmentEntityList && assignmentEntityList.size()!=0){
			entity = assignmentEntityList.get(0);
			List<IdentityLinkEntity> identityLinkEntityList = bpmDefinitionService.tranLinkToJson(entity);
			net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(identityLinkEntityList);
			model.put("linkData", jsonArray.toString());
		}
		List<Organization> orgList = this.organizationService.getAllOrganization(getCurrentUser(), OrganizationStatus.ACTIVE, null);
	    List orgs = new ArrayList();
	    Map<String,Object> map = null;
	    for (int i = 0; i < orgList.size(); i++) {
	      map = new HashMap();
	      map.put("id", ((Organization)orgList.get(i)).getId());
	      map.put("parentId", ((Organization)orgList.get(i)).getParentId());
	      map.put("name", ((Organization)orgList.get(i)).getName());
	      map.put("open", Boolean.valueOf(true));
	      orgs.add(map);
	    }
	    model.put("orgs", orgs);
		model.put("bpmScenarioStrategyList", bpmScenarioStrategyList);
		model.put("processDefinitionId", processDefinitionId);
		model.put("strategyId", strategyId);
		model.put("scenarioItemId", scenarioItemId);
		model.put("taskDefinitionKey", taskDefinitionKey);
		model.put("userId", getCurrentUser().getId());
		model.put("entity", entity);
		
		return "/definitions/assignment/identitylink";
//		return "/definitions/assignment/CopyaddUser";
	}

	/**
	 * 保存用户经办人
	 */
	@RequestMapping(value = "/saveUser")
	@ResponseBody
	public String saveUser(HttpServletRequest request,  ModelMap model) {
		String personIds = request.getParameter("personIds");
		String loginNames = request.getParameter("loginNames");
		String taskDefinitionKey = request.getParameter("taskDefinitionKey");
		String processDefinitionId = request.getParameter("processDefinitionId");
		String strategyId = request.getParameter("strategyId");
		String scenarioItemId = request.getParameter("scenarioItemId");
		bpmDefinitionService.saveIdentityLink(processDefinitionId,taskDefinitionKey, personIds, strategyId,scenarioItemId);
		return SUCCESS;

	}
	/**
	 * 保存经办人(新)
	 */
	@RequestMapping(value = "/saveAssignment")
	@ResponseBody
	public String saveAssignment(HttpServletRequest request,  ModelMap model,AssignmentEntity assignmentEntity,String linkData) {
//		String linkPerson = request.getParameter("linkPerson");
//		String linkOrganization = request.getParameter("linkOrganization");
//		String linkJob = request.getParameter("linkJob");
//		String taskDefinitionKey = request.getParameter("taskDefinitionKey");
//		String processDefinitionId = request.getParameter("processDefinitionId");
//		String strategyId = request.getParameter("strategyId");
//		String scenarioItemId = request.getParameter("scenarioItemId");
		bpmDefinitionService.saveIdentityLink(assignmentEntity,linkData);
		return SUCCESS;

	}

	
	@RequestMapping(value = "/usertree/query")
	@ResponseBody
	public Map<String, List<UserTreeNode>> queryTree(HttpServletRequest request, ModelMap model) {
		String raName = request.getParameter("raName");
		String userName = request.getParameter("userName");
		
		String processDefinitionId = request.getParameter("processDefinitionId");
		String taskDefinitionKey = request.getParameter("taskDefinitionKey");
		String scenarioItemId = request.getParameter("scenarioItemId");
		
		Map<String, List<UserTreeNode>> userTreeMap = bpmDefinitionService.buildUserTree(request, processDefinitionId, taskDefinitionKey,scenarioItemId, raName, userName);
		
		return userTreeMap;
	}
	

	/**
	 * 设置经办人人员
	 */
	@RequestMapping(value = "/addPersonnel")
	public String addPersonnel(HttpServletRequest request, ModelMap model) {
		String processDefinitionId = request.getParameter("processDefinitionId");
		String taskDefinitionKey = request.getParameter("taskDefinitionKey");
		
		model.put("processDefinitionId", processDefinitionId);
		model.put("taskDefinitionKey", taskDefinitionKey);
		
		return "/definitions/assignment/addPersonnel";
	}
	/**
	 * @throws IOException 
	 * 
	 */
	@RequestMapping(value = "/scenarioQuery")
	@ResponseBody
	public List<ScenarioItem> scenarioQuery(HttpServletRequest request, ModelMap model) throws IOException {
		
		String strategyId = request.getParameter("strategyId");
		List<ScenarioItem> scenarioItemList = new ArrayList<ScenarioItem>();
		for(int i=0;i< bpmScenarioStrategyList.size();i++){
			if(strategyId.equals(((BpmScenarioStrategy)(bpmScenarioStrategyList.get(i))).getId())){
				scenarioItemList = bpmScenarioStrategyList.get(i).getScenarioItems();
			}
		}
		
		//List<ScenarioItem> list = scenarioItemList.subList(0, 5);
		return scenarioItemList;
		
	}
	
	/**
	 * 查看票据申请流程图
	 */
	@RequestMapping(value = "/bpm")//发起流程前的图
	public void getPicture(HttpServletRequest request, HttpServletResponse response){
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		String processDefinitionId = request.getParameter("processDefinitionId");
		
		ProcessDefinition processDefinition = processDefinitionQuery.processDefinitionId(processDefinitionId).singleResult();  
		
		String diagramResourceName = processDefinition.getDiagramResourceName();
		String deploymentId = processDefinition.getDeploymentId();
		InputStream imageStream = repositoryService.getResourceAsStream(deploymentId,diagramResourceName);
		try {
			byte[] bytes = readInputStream(imageStream);
			response.getOutputStream().write(bytes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@RequestMapping(value = "/personQuery")
//	@ResponseBody
//	public Map<String,List<Object>> personQuery(HttpServletRequest request, ModelMap model){
//		List<Person> personList = (List<Person>) this.personQueryService.getAllPersons(Status.ACTIVE, "");
//		List<PersonTreeNode> personTreeNodes = new ArrayList<PersonTreeNode>();
//		PersonTreeNode personTreeNode = null;
//		for(Person person:personList){
//			personTreeNode = new PersonTreeNode(person);
//			personTreeNodes.add(personTreeNode);
//		}
//		Map map = new HashMap();
//	    map.put("treeNodes", personTreeNodes);
//	    return map; 
//	}
	//InputStream转换为byte
	public  byte[] readInputStream(InputStream inStream) throws Exception{    
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();    
        byte[] buffer = new byte[1024];    
        int len = 0;    
        while( (len=inStream.read(buffer)) != -1 ){    
            outStream.write(buffer, 0, len);    
        }    
        inStream.close();    
        return outStream.toByteArray();    
	}
	/*
	 * 职务选择器
	 */
	@RequestMapping("/range/job")
	@ResponseBody
	public Map<String,Object> candidateJobs(HttpServletRequest request){
//		Map<String, Object> params = new HashMap<String, Object>();
		String name = request.getParameter("name");
//		if(name != null && !name.equals("")){
//			params.put("name", "%"+name+"%");
//		}
//		params.put("status", Status.ACTIVE);
		Page<Job> page = jobService.getJobPage(this.createPageConfig(request), null, name, JobStatus.ACTIVE);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pager.totalRows", page.getTotal());
		map.put("rows", page.getRows());
		return map;
	}
	/*
	 * 职务选择器
	 */
	@RequestMapping("/range/job/selected")
	@ResponseBody
	public Map<String,Object> candidateJobsOfSelected(@RequestParam("ids") String idstr){
		List<Job> jobList = new ArrayList<Job>();
		String[] ids = idstr.split(",");
		Set<String> idSet = new HashSet<String>();
		for(String id : ids){
			idSet.add(id);
		}
		jobList = jobService.getJobByIds(idSet);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pager.totalRows", jobList.size());
		map.put("rows", jobList);
		return map;
	}
	/*
	 * 机构树
	 */
	@RequestMapping("/org/tree")
	@ResponseBody
	public Map<String,Object> getOrgTree(ModelMap model){

	    List<Organization> orgList = this.organizationService.getAllOrganization(getCurrentUser(), OrganizationStatus.ACTIVE, null);
	    List orgs = new ArrayList();
	    Map<String,Object> map = null;
	    for (int i = 0; i < orgList.size(); i++) {
	      map = new HashMap();
	      map.put("id", ((Organization)orgList.get(i)).getId());
	      map.put("parentId", ((Organization)orgList.get(i)).getParentId());
	      map.put("name", ((Organization)orgList.get(i)).getName());
	      map.put("open", Boolean.valueOf(true));
	      orgs.add(map);
	    }
	    model.put("orgs", orgs);
	    return model;
	    
	}
	/*
	 * 职位树
	 */
	@RequestMapping("/job/tree")
	@ResponseBody
	public Map<String,Object> getjobTree(HttpServletRequest request,ModelMap model){
		String orgId = request.getParameter("orgId");
		List<UserPositionInfo> userPositionInfoList = userPositionService.getOrganizationUserPositionInfoByDates(getCurrentUser(), orgId, new Date(), new Date());
	    List<String> jobIdList = new ArrayList<String>();
	    List<Job> jobList  = new ArrayList<Job>();
	    if(null!=userPositionInfoList&&userPositionInfoList.size()!=0)
	    {
			for(UserPositionInfo userPositionInfo:userPositionInfoList)
		    {
				jobIdList.add(userPositionInfo.getJobId());
		    }
			Set<String> jobIdset = new HashSet<String>(jobIdList);
			jobList = jobService.getJobByIds(jobIdset);

			List jobs = new ArrayList();
			Map<String,Object> map = null;
			for(int i=0; i < jobList.size();i++)
			{
				map = new HashMap<String, Object>();
				map.put("id", jobList.get(i).getId());
			      map.put("parentId", "-1");
			      map.put("orgId", orgId);
			      map.put("name", jobList.get(i).getName());
			      map.put("open", Boolean.valueOf(true));
			      jobs.add(map);
			}
		    model.put("jobs", jobs);
	    }
	    return model;
	}
	/*
	 * 根据职位获取人
	 */
	@RequestMapping("/person/tree")
	@ResponseBody
	public Map<String,Object> getPersonTree(HttpServletRequest request,ModelMap model){
		String orgId = request.getParameter("orgId");
		String jobId = request.getParameter("jobId");
		List<UserPositionInfo> userPositionInfoList = userPositionService.getOrganizationUserPositionInfoByJobIdAndDates(getCurrentUser(), orgId, jobId, new Date(), new Date());
	    List<String> personIdList = new ArrayList<String>();
	    List<Person> personList  = new ArrayList<Person>();
	    if(null!=userPositionInfoList&&userPositionInfoList.size()!=0)
	    {
			for(UserPositionInfo userPositionInfo:userPositionInfoList)
		    {
				personIdList.add(userPositionInfo.getUserPosition().getPersonId());
		    }
			Set<String> jobIdset = new HashSet<String>(personIdList);
			personList = personService.getByPersonIds(jobIdset);

			List persons = new ArrayList();
			Map<String,Object> map = null;
			for(int i=0; i < personList.size();i++)
			{
				map = new HashMap<String, Object>();
				map.put("id", personList.get(i).getPersonId());
			      map.put("parentId", "-1");
			      map.put("name", personList.get(i).getName());
			      map.put("open", Boolean.valueOf(true));
			      persons.add(map);
			}
		    model.put("persons", persons);
	    }
	    return model;
	}


	/**
	 * 设置经办人人员
	 */
	@RequestMapping(value = "/positionlink")
	public String positionlink(HttpServletRequest request, ModelMap model) {
//		String processDefinitionId = request.getParameter("processDefinitionId");
//		String taskDefinitionKey = request.getParameter("taskDefinitionKey");
//		
//		model.put("processDefinitionId", processDefinitionId);
//		model.put("taskDefinitionKey", taskDefinitionKey);
		
		return "/definitions/assignment/positionlink";
	}
	@RequestMapping(value = "/getOrgName")
	@ResponseBody
	public Map<String,Object> getOrgName(HttpServletRequest request, ModelMap model) {
//		String processDefinitionId = request.getParameter("processDefinitionId");
//		String taskDefinitionKey = request.getParameter("taskDefinitionKey");
//		
//		model.put("processDefinitionId", processDefinitionId);
//		model.put("taskDefinitionKey", taskDefinitionKey);
		String orgId = request.getParameter("orgId");
		Organization org = organizationService.getOrganizationById(orgId);
		if(null!=org)
		{
			model.put("orgName", org.getName());
		}
		return model;
	}
	
	/*
	 * 获取过滤规则
	 */
	@RequestMapping(value = "/getFilterRules")
	@ResponseBody
	public Map<String,Object> getFilterRules(HttpServletRequest request, ModelMap model) {
		Collections.sort(bpmFilterRuleExtensionList);
		List dataList = new ArrayList();
		for(BpmFilterRuleExtension BpmFilterRuleExtension:bpmFilterRuleExtensionList){
			Map<String,String> data = new HashMap<String, String>();
			data.put("key", BpmFilterRuleExtension.getName());
			data.put("value", BpmFilterRuleExtension.getId());
			dataList.add(data);
		}
		model.put("list", dataList);
		return model;
	}
	
	/*
	 * 获取自动选人规则
	 */
	@RequestMapping(value = "/getAutoRules")
	@ResponseBody
	public Map<String,Object> getAutoRules(HttpServletRequest request, ModelMap model) {
		Collections.sort(bpmAutoRuleExtensionList);
		List dataList = new ArrayList();
		for(BpmAutoRuleExtension bpmAutoRuleExtension:bpmAutoRuleExtensionList){
			Map<String,String> data = new HashMap<String, String>();
			data.put("key", bpmAutoRuleExtension.getName());
			data.put("value", bpmAutoRuleExtension.getId());
			dataList.add(data);
		}
		model.put("list", dataList);
		return model;
	}
	
	
}
