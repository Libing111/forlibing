/* <p>文件名称: BpmDefinitionsController.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-7-22</p>
 * <p>完成日期：2013-7-22</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-7-22 下午5:31:52
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.bpm.definitions.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionResponse;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.web.BaseController;



@Controller
@RequestMapping(value = "/definitions")
public class BpmDefinitionsController extends BaseController{
	@Autowired
	private RepositoryService repositoryService;
	
	@RequestMapping("/index")
	public String index(ModelMap model) {
		return "/definitions/index";
	}
	
	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, ModelMap model) {
		//初始化返回值Map
		Map<String, Object> map = new HashMap<String, Object>();
		
		String name = request.getParameter("name");
		
		//初始化Page
		PageConfig pageConfig = super.createPageConfig(request);
		Page<ProcessDefinitionResponse> page = new Page<ProcessDefinitionResponse>(pageConfig);
		
		
		//取分页数据
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		if(name != null && name.trim().equals("") == false){
			query.processDefinitionNameLike("%" + name + "%");
		}
		//按照名称升序
		query.orderByProcessDefinitionName().asc();

		int pageNo = pageConfig.pageNo;
		int size = pageConfig.pageSize;
		int start = (pageNo - 1) * size;
		
		List<ProcessDefinition> processDefinitionList = query.listPage(start, size);
		
		List<ProcessDefinitionResponse> processResponseList = new ArrayList<ProcessDefinitionResponse>();
		for (ProcessDefinition processDefinition : processDefinitionList) {
			processResponseList.add(new ProcessDefinitionResponse((ProcessDefinitionEntity) processDefinition));
		}
		
		page.setRows(processResponseList);
		page.setTotal(query.count());
		
		//分页数据转换成Map（QUI只识别Map）
		map.put("pager.pageNo", page.getPageNo());
		map.put("pager.totalRows", page.getTotal());
		map.put("rows", page.getRows());
		
		return map;
	}

}
