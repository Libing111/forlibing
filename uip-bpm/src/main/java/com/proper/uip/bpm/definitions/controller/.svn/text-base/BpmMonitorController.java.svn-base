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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.uip.api.bpm.definitions.entity.MonitorEntity;
import com.proper.uip.api.security.entity.RegistrationAuthority;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.service.RegistrationAuthorityService;
import com.proper.uip.api.security.service.UserService;
import com.proper.uip.bpm.definitions.service.BpmDefinitionService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.web.BaseController;


@Controller
@RequestMapping(value = "/definitions/monitor")
public class BpmMonitorController extends BaseController{
	@Autowired
	private BpmDefinitionService bpmDefinitionService;
	
	@Autowired
	private RegistrationAuthorityService raService;

	@Autowired
	private UserService userService;
	
	@RequestMapping("/index")
	public String index(ModelMap model) {
		
		return "/definitions/monitor/index";
	}

	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, ModelMap model) {
		//初始化返回值Map
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		String processDefinitionId = request.getParameter("processDefinitionId");
		if(processDefinitionId == null){
			return map;
		}
		
		List<MonitorEntity> entityList = bpmDefinitionService.getMonitorList(processDefinitionId);
		
		//分页数据转换成Map（QUI只识别Map）
		map.put("pager.pageNo", 1);
		map.put("pager.totalRows", entityList.size());
		map.put("rows", entityList);
				
		return map;
	}
	
	
	/**
	 * 设置监控人用户
	 */
	@RequestMapping(value = "/addUser")
	public String addIdentityLink(HttpServletRequest request, ModelMap model) {
		String processDefinitionId = request.getParameter("processDefinitionId");
		List<RegistrationAuthority> raList = raService.getAllRa();
		
		model.put("processDefinitionId", processDefinitionId);
		model.put("raList", raList);
		
		return "/definitions/monitor/addUser";
	}
	
	/**
	 * 保存监控人
	 */
	@RequestMapping(value = "/saveUser")
	@ResponseBody
	public String saveUser(HttpServletRequest request,  ModelMap model) {
		
		String loginNames = request.getParameter("loginNames");
		String processDefinitionId = request.getParameter("processDefinitionId");
		
		bpmDefinitionService.saveMonitorIdentityLink(processDefinitionId, loginNames);
		return SUCCESS;

	}

	
	@RequestMapping(value = "/user/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, String name, String loginName,String raName, ModelMap model) {
		PageConfig pageConfig = this.createPageConfig(request);
		String raId = request.getParameter("raId");
		Page<User> userPage = userService.findAllUser(pageConfig, raId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pager.pageNo", userPage.getPageNo());
		map.put("pager.totalRows", userPage.getTotal());
		map.put("rows", userPage.getRows());
		
		return map;
	}
	
	
	@RequestMapping(value = "/del")
	public String del(String ids, ModelMap model) {
		try {
			if (StringUtils.isNotEmpty(ids)) {
				for (String id : ids.split(",")) {
					bpmDefinitionService.deleteMonitorById(id);
				}
			}
		} catch (Exception e) {
			model.put("status", -1);
			this.setError(model, "删除失败,请联系管理员");
		}
		
		model.put("status", 1);
		
		return SUCCESS;
	}
	
}
