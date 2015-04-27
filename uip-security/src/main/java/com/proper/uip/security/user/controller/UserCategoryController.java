/* <p>文件名称: UserCategoryController.java </p>
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
package com.proper.uip.security.user.controller;

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

import com.proper.uip.api.security.entity.RoleCategory;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.entity.UserCategory;
import com.proper.uip.api.security.service.UserCategoryService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.user.service.UserService;
import com.proper.uip.web.BaseController;
@Controller
@RequestMapping(value = "/userCategory")
public class UserCategoryController extends BaseController{
	@Autowired
	private UserCategoryService userCategoryService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/index")
	public String index(ModelMap model) {
		
		return "/user/userCategory/index";
	}
	
	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, String name, ModelMap model) {
		PageConfig pageConfig = this.createPageConfig(request);
		String stop = request.getParameter("stop");
		
		Page<UserCategory> userCategoryList = userCategoryService.findAll(pageConfig, name,stop);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pager.pageNo", userCategoryList.getPageNo());
		map.put("pager.totalRows", userCategoryList.getTotal());
		map.put("rows", userCategoryList.getRows());
		
		return map;
	}
	/**
	 * 增加
	 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request,ModelMap model) {
		
		return "/user/userCategory/add";
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value = "/update")
	public String update(String id, ModelMap model) {
		UserCategory userCategoryEntity = userCategoryService.getById(id);
		model.addAttribute("userCategoryEntity", userCategoryEntity);
		return "/user/userCategory/update";
	}
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(HttpServletRequest request, UserCategory userCategory, ModelMap model) {
		UserCategory userCategoryOld = userCategoryService.getByName(userCategory.getName());
		
		if (userCategoryOld != null) {
			return "此名称已经被使用！请重新填写！";
		}
		userCategoryOld = userCategoryService.getByCode(userCategory.getCode());
		
		if (userCategoryOld != null) {
			return "此编号已经被使用！请重新填写！";
		}
		userCategoryOld = userCategoryService.getBySequenceNumber(userCategory.getSequenceNumber());
		if (userCategoryOld != null) {
			return "此序号已经被使用！请重新填写！";
		}
		//新增
		if (userCategory.getId() == null || userCategory.getId().equals("")) {
			userCategory.setStop(false);
			userCategory.setId(null);
		}
		//保存管理信息
		userCategoryService.saveUserCategory(userCategory);
		
		return SUCCESS;

	}
	/**
	 * 保存
	 */
	@RequestMapping(value = "/updateSave")
	@ResponseBody
	public String updateSave(HttpServletRequest request,UserCategory userCategory, String id, ModelMap model) {
		UserCategory userCategoryEntity = userCategoryService.getById(id);
		
		UserCategory userCategoryOld = userCategoryService.getByName(userCategory.getName());
		
		if (userCategoryOld != null && userCategoryOld.getId().equals(userCategoryEntity.getId()) == false) {
			return "此名称已经被使用！请重新填写！";
		}
		userCategoryOld = userCategoryService.getByCode(userCategory.getCode());
		
		if (userCategoryOld != null && userCategoryOld.getId().equals(userCategoryEntity.getId()) == false) {
			return "此编号已经被使用！请重新填写！";
		}
		userCategoryOld = userCategoryService.getBySequenceNumber(userCategory.getSequenceNumber());
		if (userCategoryOld != null && userCategoryOld.getId().equals(userCategoryEntity.getId()) == false) {
			return "此序号已经被使用！请重新填写！";
		}
		
		userCategoryEntity.setCode(userCategory.getCode());
		userCategoryEntity.setDescription(userCategory.getDescription());
		userCategoryEntity.setName(userCategory.getName());
		userCategoryEntity.setSequenceNumber(userCategory.getSequenceNumber());

		userCategoryService.updateSaveUserCategory(userCategoryEntity);
		
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
					List<User> userList = userService.getByCategoryId(id);
					if(userList.size() > 0){
						model.put("status", -1);
						this.setError(model, "删除失败,此分类正在使用,不允许删除！");
						return SUCCESS;
					}
					userCategoryService.delete(id);
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
		UserCategory userCategory = userCategoryService.getById(id);
		try {
			List<User> userList = userService.getByCategoryId(id);
			if(userList.size() > 0){
				model.put("status", -1);
				this.setError(model, "停用失败,此分类正在使用,不允许停用！");
				return SUCCESS;
			}
			if(userCategory.isStop() == true){
				model.put("status", -1);
				this.setError(model, "此分类已经被停用，不允许再次停用！");
				return SUCCESS;
			}
			userCategory.setStop(true);
			userCategoryService.saveUserCategory(userCategory);
			
		} catch (Exception e) {
			model.put("status", -1);
			this.setError(model, "停用失败,请联系管理员");
		}
		
		model.put("status", 1);
		
		return SUCCESS;
	}
	
	@RequestMapping(value = "/nostop")
	public String onUnAnnounce(HttpServletRequest request,String id, ModelMap model) {
		UserCategory userCategory = userCategoryService.getById(id);
		try {
			if(userCategory.isStop() == false){
				model.put("status", -1);
				this.setError(model, "此分类已经被启用，不允许再次启用！");
				return SUCCESS;
			}
			userCategory.setStop(false);
			userCategoryService.saveUserCategory(userCategory);
			
		} catch (Exception e) {
			model.put("status", -1);
			this.setError(model, "停用失败,请联系管理员");
		}
		
		model.put("status", 1);
		
		return SUCCESS;
	}
}
