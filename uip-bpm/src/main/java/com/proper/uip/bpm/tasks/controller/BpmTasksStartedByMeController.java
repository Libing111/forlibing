package com.proper.uip.bpm.tasks.controller;
/* <p>文件名称: BpmTasksStartedByMeController.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-3-18</p>
 * <p>完成日期：2013-3-18</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author zhanghuafeng
 */

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.history.HistoricVariableInstanceQuery;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.service.ResourceService;
import com.proper.uip.api.security.service.UserService;
import com.proper.uip.bpm.process.instanse.entity.ProcessInstancesResponse;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.web.BaseController;


@Controller
@RequestMapping(value = "/process/instanse/startedByMe")
public class BpmTasksStartedByMeController extends BaseController {
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private UserService userService;
	/**
	 * 我的申请首页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String indexStartedByMe(ModelMap model) {
		return "/startedByMe/index";
	}

	/**
	 * 流程实例查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/instance/query")
	@ResponseBody
	public Map<String, Object> processInstanceQuery(HttpServletRequest request) {

        String processInstanceName = request.getParameter("processInstanceName");
		//0.0  初始化返回值Map
		Map<String, Object> map = new HashMap<String, Object>();

		//1.0 取当前用户发起的历史流程实例
		User user = super.getCurrentUser();

		HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();
		query.startedBy(user.getExtendId());
		if(processInstanceName != null && processInstanceName.trim().equals("") == false){

	        String processInstanceNameLike = "%" + processInstanceName + "%";
		    query.variableValueLike("processInstanceName", processInstanceNameLike);
            
        }

		// 处理流程的发起时间
		String searchBigenDate = request.getParameter("searchBigenDate");

		if (searchBigenDate != null
				&& searchBigenDate.trim().equals("") == false) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date date = formatter.parse(searchBigenDate);
				query.startedAfter(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		// 处理流程的结束时间
		String searchEndDate = request.getParameter("searchEndDate");

		if (searchEndDate != null && searchEndDate.trim().equals("") == false) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date date = formatter.parse(searchEndDate);
				query.startedBefore(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		List<HistoricProcessInstance> list = query.list();
		Set<String> processInstanceIdSet = new HashSet<String>();
		for(HistoricProcessInstance processInstance : list){
			processInstanceIdSet.add(processInstance.getId());   
		}
		
		//2.0 取当前用户发起的运行中的流程实例
		List<ProcessInstance> processInstanceList = new ArrayList<ProcessInstance>();
		if(processInstanceIdSet.isEmpty() == false){
			processInstanceList = runtimeService.createProcessInstanceQuery().processInstanceIds(processInstanceIdSet).list();
		}
		
		Map<String, ProcessInstance> processInstanceMap = new HashMap<String, ProcessInstance>();
		processInstanceIdSet.clear();
		for(ProcessInstance processInstance : processInstanceList){
			processInstanceMap.put(processInstance.getId(), processInstance);
			processInstanceIdSet.add(processInstance.getId());
		}
		
	
		
		//3.0 取当前用户发起的历史流程实例
		PageConfig pageConfig = super.createPageConfig(request);
		int pageNo = pageConfig.pageNo;
		int size = pageConfig.pageSize;

		int start = (pageNo - 1) * size;
		
		list = new ArrayList<HistoricProcessInstance>();
		if(processInstanceIdSet.isEmpty() == false){
			list = historyService.createHistoricProcessInstanceQuery().processInstanceIds(processInstanceIdSet).listPage(start, size);
		}
		
		Map<String, Object> variables = null;
		List<ProcessInstancesResponse> processResponseList = new ArrayList<ProcessInstancesResponse>();
		ProcessInstancesResponse response = null;
		processInstanceName = null;
		for (HistoricProcessInstance processInstance : list) {
			variables = this.getProcessVariables(processInstance.getId());
			
			if(variables.containsKey("processInstanceName") == false){
				processInstanceName = repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId()).singleResult().getName();
				variables.put("processInstanceName", processInstanceName); 
			}
			variables.put("processInstanceInitiatorName", user.getName());
			
			response = new ProcessInstancesResponse((HistoricProcessInstanceEntity)processInstance, variables);
			response.setSuspended(processInstanceMap.get(processInstance.getId()).isSuspended());
			
			processResponseList.add(response);
		}

		Page<ProcessInstancesResponse> page = new Page<ProcessInstancesResponse>();

		page.setRows(processResponseList);
		page.setTotal(processInstanceIdSet.size());

		// 分页数据转换成Map（QUI只识别Map）
		map.put("pager.pageNo", page.getPageNo());
		map.put("pager.totalRows", page.getTotal());
		map.put("rows", page.getRows());
	
		return map;
	}
	

	/**
	 * 流程实例任务查询
	 * 
	 * @param processInstanceId
	 * @return
	 */
	@RequestMapping("/instance/task/query")
	@ResponseBody
	public Map<String, Object> processInstanceTasksQuery(String processInstanceId,
			HttpServletRequest request) {
		
		// 初始化返回值Map
		Map<String, Object> map = new HashMap<String, Object>();
		if(processInstanceId == null){
			return map;
		}
		// 初始化Page
		PageConfig pageConfig = super.createPageConfig(request);
		Page<HistoricTaskInstanceEntity> page = new Page<HistoricTaskInstanceEntity>(pageConfig);
		HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();

		query.processInstanceId(processInstanceId);
		query.orderByHistoricTaskInstanceStartTime();
		query.asc();

		List<HistoricTaskInstance> list = query.list();

		List<HistoricTaskInstanceEntity> taskResponseList = new ArrayList<HistoricTaskInstanceEntity>();
		for (HistoricTaskInstance instance : list) {
		    String personId = instance.getAssignee();
            HistoricTaskInstanceEntity instanceEntity = (HistoricTaskInstanceEntity) instance;
		    if(null != personId && !"".equals(personId))
		    {
	            instanceEntity.setAssignee(userService.getByExtendId(personId).getName());
		    }
			taskResponseList.add(instanceEntity);
		}

		page.setRows(taskResponseList);
		page.setTotal(query.count());

		// 分页数据转换成Map（QUI只识别Map）
		map.put("pager.pageNo", page.getPageNo());
		map.put("pager.totalRows", page.getTotal());
		map.put("rows", page.getRows());
		return map;
	}
	
	private Map<String, Object> getProcessVariables(String processInstanceId){
		Map<String, Object> variables = new HashMap<String, Object>();
		List<HistoricDetail> variableList = historyService
				.createHistoricDetailQuery()
				.processInstanceId(processInstanceId).variableUpdates()
				.orderByTime().asc().list();
		
		for (HistoricDetail historicDetail : variableList) {
			HistoricVariableUpdate variableUpdate = (HistoricVariableUpdate) historicDetail;
			variables.put(variableUpdate.getVariableName(), variableUpdate
					.getValue());
		}
		List<Execution> executionList = runtimeService
				.createExecutionQuery()
				.processInstanceId(processInstanceId).list();
		
		Map<String, Object> currentVariables;
		for (Execution execution : executionList) {
			if(execution == null){
				continue;
			}
			currentVariables = runtimeService.getVariables(execution.getId());
			if(currentVariables == null){
				continue;
			}
			variables.putAll(currentVariables);
		}
		
		return variables;
	}
	
	/**
	 * 挂起流程
	 * 
	 * @param processInstanceId
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/instance/suspend")
	public void processInstanceSuspend(String processInstanceId,HttpServletResponse response)throws IOException{
		
		HistoricProcessInstance query = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		if(query.getEndTime() != null ){
			runtimeService.suspendProcessInstanceById(processInstanceId);
		}
		
		
		
	}
	
	
	
	
	
	/**
	 * 继续流程
	 * @param processInstanceId
	 * @param response
	 */
	@RequestMapping("/instance/activate")
	public void processInstanceActivate(String processInstanceId,HttpServletResponse response)throws IOException{
		if (processInstanceId == null) {
			throw new ActivitiException("No process instance id provided");
		}
		runtimeService.activateProcessInstanceById(processInstanceId);
	}
	/**
	 * 撤销
	 * @param processInstanceId
	 * @param response
	 */
	@RequestMapping("/instance/cancel")
	@ResponseBody
	public String processInstanceCancel(String processInstanceId,HttpServletResponse response)throws IOException{
		Map<String, Object> variables = new HashMap<String, Object>();
		variables = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult().getProcessVariables();
		
		@SuppressWarnings("unchecked")
		Map<String,String> processInstanceOperationMap = (Map<String, String>) variables.get("processInstanceOperationMap");
		if(processInstanceOperationMap == null){
			return null;
		}
		String resouceCode = processInstanceOperationMap.get("takeBack");
		if(resouceCode == null){
			return null;
		}
		Resource resource = resourceService.getResourceByCode(resouceCode);
		
		String url = resource.getUrl();
		//runtimeService.activateProcessInstanceById(processInstanceId);
		return url;
	}
}
