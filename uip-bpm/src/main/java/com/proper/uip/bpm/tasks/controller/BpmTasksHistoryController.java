/* <p>文件名称: BpmTasksHistoryController.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-7-22</p>
 * <p>完成日期：2013-7-22</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-7-22 下午3:45:47
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.bpm.tasks.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.service.SecurityService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.web.BaseController;



@Controller
@RequestMapping(value = "/tasks/history")
public class BpmTasksHistoryController extends BaseController {
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private SecurityService securityService;
	
	@RequestMapping("/index")
	public String index(ModelMap model) {
		return "/tasks/history/index";
	}  
	
	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, ModelMap model) {
		//初始化返回值Map
		Map<String, Object> map = new HashMap<String, Object>();
		
		//初始化Page
		PageConfig pageConfig = super.createPageConfig(request);
		Page<HistoricTaskInstanceEntity> page = new Page<HistoricTaskInstanceEntity>(pageConfig);
		
		//取当前用户，若不到当前用户，返回空Map
		User user = securityService.getCurrentUser(request);
		if(user == null){
			map.put("pager.pageNo", page.getPageNo());
			map.put("pager.totalRows", 0);
			map.put("rows", new ArrayList<HistoricTaskInstanceEntity>());
			return map;
		}
		
		
		//取待办工作分页数据
		HistoricTaskInstanceQuery historicTaskQuery = historyService.createHistoricTaskInstanceQuery();
		
		//经办人是当前用户
		historicTaskQuery.taskAssignee(user.getLoginName())
		         	     .orderByHistoricTaskInstanceEndTime()
		                 .desc();

		int pageNo = pageConfig.pageNo;
		int size = pageConfig.pageSize;
		int start = (pageNo - 1) * size;
		
		List<HistoricTaskInstance> taskList = historicTaskQuery.listPage(start, size);
		
		List<HistoricTaskInstanceEntity> taskEntityList = new ArrayList<HistoricTaskInstanceEntity>();
		for (HistoricTaskInstance task : taskList) {
			taskEntityList.add((HistoricTaskInstanceEntity) task);
		}
		
		page.setRows(taskEntityList);
		page.setTotal(historicTaskQuery.count());
		
		//分页数据转换成Map（QUI只识别Map）
		map.put("pager.pageNo", page.getPageNo());
		map.put("pager.totalRows", page.getTotal());
		map.put("rows", page.getRows());
		
		return map;
	}
}
