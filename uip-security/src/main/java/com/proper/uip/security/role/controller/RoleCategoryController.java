/* <p>文件名称: RoleCategoryController.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-3-12</p>
 * <p>完成日期：2014-3-12</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-3-12下午3:47:48
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	lichunyan
 */
package com.proper.uip.security.role.controller;

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

import com.proper.uip.api.security.entity.Role;
import com.proper.uip.api.security.entity.RoleCategory;
import com.proper.uip.api.security.service.RoleCategoryService;
import com.proper.uip.api.security.service.RoleService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.web.BaseController;
@Controller
@RequestMapping(value = "/roleCategory")
public class RoleCategoryController extends BaseController{
	@Autowired
	private RoleCategoryService roleCategoryService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/index")
	public String index(ModelMap model) {
		
		return "/role/roleCategory/index";
	}
	
	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, String name, ModelMap model) {
		PageConfig pageConfig = this.createPageConfig(request);
		String stop = request.getParameter("stop");
		
		Page<RoleCategory> roleCategoryList = roleCategoryService.findAll(pageConfig, name, stop);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pager.pageNo", roleCategoryList.getPageNo());
		map.put("pager.totalRows", roleCategoryList.getTotal());
		map.put("rows", roleCategoryList.getRows());
		
		return map;
	}
	/**
	 * 增加
	 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request,ModelMap model) {
		
		return "/role/roleCategory/add";
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value = "/update")
	public String update(String id, ModelMap model) {
		RoleCategory roleCategoryEntity = roleCategoryService.getById(id);
		model.addAttribute("roleCategoryEntity", roleCategoryEntity);
		return "/role/roleCategory/update";
	}
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(HttpServletRequest request, RoleCategory roleCategory, ModelMap model) {
		RoleCategory roleCategoryOld = roleCategoryService.getByName(roleCategory.getName());
		
		if (roleCategoryOld != null) {
			return "此名称已经被使用！请重新填写！";
		}
		roleCategoryOld = roleCategoryService.getByCode(roleCategory.getCode());
		
		if (roleCategoryOld != null) {
			return "此编号已经被使用！请重新填写！";
		}
		roleCategoryOld = roleCategoryService.getBySequenceNumber(roleCategory.getSequenceNumber());
		if (roleCategoryOld != null) {
			return "此序号已经被使用！请重新填写！";
		}
		//新增
		if (roleCategory.getId() == null || roleCategory.getId().equals("")) {
			roleCategory.setStop(false);
			roleCategory.setId(null);
		}
		//保存管理信息
		roleCategoryService.saveRoleCategory(roleCategory);
		
		return SUCCESS;

	}
	/**
	 * 保存
	 */
	@RequestMapping(value = "/updateSave")
	@ResponseBody
	public String updateSave(HttpServletRequest request,RoleCategory roleCategory, String id, ModelMap model) {
		RoleCategory roleCategoryEntity = roleCategoryService.getById(id);
		RoleCategory roleCategoryOld = roleCategoryService.getByName(roleCategory.getName());
		
		if (roleCategoryOld != null && roleCategoryOld.getId().equals(roleCategoryEntity.getId()) == false) {
			return "此名称已经被使用！请重新填写！";
		}
		roleCategoryOld = roleCategoryService.getByCode(roleCategory.getCode());
		
		if (roleCategoryOld != null && roleCategoryOld.getId().equals(roleCategoryEntity.getId()) == false) {
			return "此编号已经被使用！请重新填写！";
		}
		roleCategoryOld = roleCategoryService.getBySequenceNumber(roleCategory.getSequenceNumber());
		if (roleCategoryOld != null && roleCategoryOld.getId().equals(roleCategoryEntity.getId()) == false) {
			return "此序号已经被使用！请重新填写！";
		}
		roleCategoryEntity.setCode(roleCategory.getCode());
		roleCategoryEntity.setDescription(roleCategory.getDescription());
		roleCategoryEntity.setName(roleCategory.getName());
		roleCategoryEntity.setSequenceNumber(roleCategory.getSequenceNumber());

		roleCategoryService.updateSaveRoleCategory(roleCategoryEntity);
		
		return SUCCESS;

	}
	/**
	 * 删除
	 */

	@RequestMapping(value = "/del")
	public String del(String ids, ModelMap model) {
		try {
			if (StringUtils.isNotEmpty(ids)) {
				for (String id : ids.split(",")) {
					List<Role> roleList = roleService.getByCategoryId(id);
					if(roleList.size() > 0){
						model.put("status", -1);
						this.setError(model, "删除失败,此分类正在使用,不允许删除！");
						return SUCCESS;
					}
					roleCategoryService.delete(id);
				}
			}
		} catch (Exception e) {
			model.put("status", -1);
			this.setError(model, "删除失败,请联系管理员");
		}
		
		model.put("status", 1);
		
		return SUCCESS;
	}
	@RequestMapping(value = "/stop")
	public String nostop(HttpServletRequest request,String id, ModelMap model) {
		RoleCategory roleCategory = roleCategoryService.getById(id);
		try {
			List<Role> roleList = roleService.getByCategoryId(id);
			if(roleList.size() > 0){
				model.put("status", -1);
				this.setError(model, "停用失败,此分类正在使用,不允许停用！");
				return SUCCESS;
			}
			if(roleCategory.isStop() == true){
				model.put("status", -1);
				this.setError(model, "此分类已经被停用，不允许再次停用！");
				return SUCCESS;
			}
			roleCategory.setStop(true);
			roleCategoryService.saveRoleCategory(roleCategory);
			
		} catch (Exception e) {
			model.put("status", -1);
			this.setError(model, "停用失败,请联系管理员");
		}
		
		model.put("status", 1);
		
		return SUCCESS;
	}
	
	@RequestMapping(value = "/nostop")
	public String onUnAnnounce(HttpServletRequest request,String id, ModelMap model) {
		RoleCategory roleCategory = roleCategoryService.getById(id);
		try {
			if(roleCategory.isStop() == false){
				model.put("status", -1);
				this.setError(model, "此分类已经被启用，不允许再次启用！");
				return SUCCESS;
			}
			roleCategory.setStop(false);
			roleCategoryService.saveRoleCategory(roleCategory);
			
		} catch (Exception e) {
			model.put("status", -1);
			this.setError(model, "停用失败,请联系管理员");
		}
		
		model.put("status", 1);
		
		return SUCCESS;
	}
}
