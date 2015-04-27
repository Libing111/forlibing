package com.proper.uip.bpm.definitions.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionManagementEntity;
import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionsCategoryEntity;
import com.proper.uip.bpm.definitions.service.BpmDefinitionsCategoryService;
import com.proper.uip.bpm.definitions.service.ProcessDefinitionManagementService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.web.BaseController;


@Controller
@RequestMapping(value = "/definitions/management")
public class ProcessDefinitionManagementController extends BaseController{

	@Autowired
	private ProcessDefinitionManagementService processDefinitionManagementService;
	
	@Autowired
	private BpmDefinitionsCategoryService bpmDefinitionsCategoryService;
	
	
	@Autowired
	private RepositoryService repositoryService;
	
	@RequestMapping("/index")
	public String index(ModelMap model) {
		return "/definitions/management/index";
	}
	
	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, ModelMap model) {
		String name = request.getParameter("name");
		String categoryName = request.getParameter("categoryName");
		PageConfig pageConfig = this.createPageConfig(request);
		
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		List<ProcessDefinition> processDefinitionList = query.list();
		
		processDefinitionManagementService.getCreateProcessDef(processDefinitionList);
		
		Page<ProcessDefinitionManagementEntity> processDefinitionsManagementPage = processDefinitionManagementService.getDefinitionsManagement(pageConfig,name,categoryName);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pager.pageNo", processDefinitionsManagementPage.getPageNo());
		map.put("pager.totalRows", processDefinitionsManagementPage.getTotal());
		map.put("rows", processDefinitionsManagementPage.getRows());
		
		return map;
	}
	/**
	 * 增加
	 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, String id, ModelMap model) {
		ProcessDefinitionManagementEntity processDefinitionManagement = processDefinitionManagementService.getById(id);
		List<ProcessDefinitionsCategoryEntity> definitionsCategoryList = bpmDefinitionsCategoryService.getLeafCategoryList();
		
		model.put("definitionsCategoryList", definitionsCategoryList);
		model.put("processDefinitionManagement", processDefinitionManagement);

		return "/definitions/management/add";
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(HttpServletRequest request, ProcessDefinitionManagementEntity processDefinitionManagementEntity, ModelMap model) {
		//新增
		if (processDefinitionManagementEntity.getId() == null || processDefinitionManagementEntity.getId().equals("")) {
			processDefinitionManagementEntity.setId(null);
		}
		ProcessDefinitionsCategoryEntity processDefinitionsCategory = bpmDefinitionsCategoryService.getById(processDefinitionManagementEntity.getCategoryId());
		processDefinitionManagementEntity.setCategoryCode(processDefinitionsCategory.getCode());
		processDefinitionManagementEntity.setCategoryName(processDefinitionsCategory.getName());
		processDefinitionManagementEntity.setCategorySequence(processDefinitionsCategory.getSequence());
		
		processDefinitionManagementService.save(processDefinitionManagementEntity);

		return SUCCESS;
	}
	/**
	 * 查看流程图
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

	
}
