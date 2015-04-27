package com.proper.uip.security.secureconf.usercateRole.controller;


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

import com.proper.uip.api.security.entity.UserCategory;
import com.proper.uip.api.security.entity.UsercateRole;
import com.proper.uip.api.security.service.UserCategoryService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.secureconf.usercateRole.service.UsercateRoleService;
import com.proper.uip.web.BaseController;

@Controller
@RequestMapping(value = "/secureconf/usercate/role")
public class UsercateRoleController extends BaseController{
	@Autowired
	private UserCategoryService userCategoryService;
	
	@Autowired
	private UsercateRoleService usercateRoleService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request, ModelMap model) {

		return "/usercateRole/role/index";
	}
	
	@RequestMapping(value = "/usercategory/query")
	@ResponseBody
	public Map<String, Object> queryUsercategory(HttpServletRequest request,ModelMap model) {
		PageConfig pageConfig = this.createPageConfig(request);
		List<UserCategory> category = userCategoryService.getAllUserCategorys();
		Map<String, Object> map = new HashMap<String, Object>();
		Page<UserCategory> dataItemPage = new Page<UserCategory>(pageConfig);
		dataItemPage.setRows(category);
		
		map.put("pager.pageNo", dataItemPage.getPageNo());
		map.put("pager.totalRows", dataItemPage.getTotal());
		map.put("rows", dataItemPage.getRows());
		
		return map;
	}
	
	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request,String userCategoryId,ModelMap model) {
		PageConfig pageConfig = this.createPageConfig(request);
		Map<String, Object> map = new HashMap<String, Object>();
		Page<UsercateRole> usercateRolePage = usercateRoleService.findRole(pageConfig,userCategoryId);
		
		map.put("pager.pageNo", usercateRolePage.getPageNo());
		map.put("pager.totalRows", usercateRolePage.getTotal());
		map.put("rows", usercateRolePage.getRows());
		
		return map;
	}
	/**
	 * 添加角色
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String addRole(ModelMap model) {
		
		return "/usercateRole/role/newStandard";
	}
	/**
	 * 保存角色
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public void saveRole(String ids, String userCategoryId, ModelMap model) {
		UserCategory userCategory = userCategoryService.getById(userCategoryId);
		usercateRoleService.save(ids, userCategory);

		
	}
	
	/**
	 * 角色删除
	 */

	@RequestMapping(value = "/del")
	public String delRole(String roleId,String userCategoryId, ModelMap model) {
		if (StringUtils.isNotEmpty(roleId) == false) {
			model.put("status", -1);
			this.setError(model, "删除失败 , 没有选中角色");
		}
		
		try {
			usercateRoleService.delRole(roleId, userCategoryId);
		} catch (Exception e) {
			model.put("status", -1);
			this.setError(model, "删除失败,请联系管理员");
		}
		
		model.put("status", 1);
		
		return SUCCESS;
	}
	
	
}
