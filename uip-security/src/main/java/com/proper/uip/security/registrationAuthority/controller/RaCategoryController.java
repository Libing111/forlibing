/* <p>文件名称: RaCategoryController.java </p>
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
package com.proper.uip.security.registrationAuthority.controller;

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

import com.proper.uip.api.security.entity.RaCategory;
import com.proper.uip.api.security.entity.RegistrationAuthority;
import com.proper.uip.api.security.entity.RoleCategory;
import com.proper.uip.api.security.service.RaCategoryService;
import com.proper.uip.api.security.service.RegistrationAuthorityService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.web.BaseController;
@Controller
@RequestMapping(value = "/raCategory")
public class RaCategoryController extends BaseController{
	@Autowired
	private RaCategoryService raCategoryService;
	
	@Autowired
	private RegistrationAuthorityService registrationAuthorityService;
	
	@RequestMapping("/index")
	public String index(ModelMap model) {
		
		return "/registrationAuthority/raCategory/index";
	}
	
	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, String name, ModelMap model) {
		PageConfig pageConfig = this.createPageConfig(request);
		String stop = request.getParameter("stop");
		
		Page<RaCategory> raCategoryList = raCategoryService.findAll(pageConfig, name, stop);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pager.pageNo", raCategoryList.getPageNo());
		map.put("pager.totalRows", raCategoryList.getTotal());
		map.put("rows", raCategoryList.getRows());
		
		return map;
	}
	/**
	 * 增加
	 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request,ModelMap model) {
		
		return "/registrationAuthority/raCategory/add";
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value = "/update")
	public String update(String id, ModelMap model) {
		RaCategory raCategoryEntity = raCategoryService.getById(id);
		model.addAttribute("raCategoryEntity", raCategoryEntity);
		return "/registrationAuthority/raCategory/update";
	}
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(HttpServletRequest request, RaCategory raCategory, ModelMap model) {
		RaCategory raCategoryOld = raCategoryService.getByName(raCategory.getName());
		if (raCategoryOld != null) {
			return "此名称已经被使用！请重新填写！";
		}
		raCategoryOld = raCategoryService.getByCode(raCategory.getCode());
		
		if (raCategoryOld != null) {
			return "此编号已经被使用！请重新填写！";
		}
		raCategoryOld = raCategoryService.getBySequenceNumber(raCategory.getSequenceNumber());
		if (raCategoryOld != null) {
			return "此序号已经被使用！请重新填写！";
		}
		//新增
		if (raCategory.getId() == null || raCategory.getId().equals("")) {
			raCategory.setStop(false);
			raCategory.setId(null);
		}
		//保存管理信息
		raCategoryService.saveRaCategory(raCategory);
		
		return SUCCESS;

	}
	/**
	 * 保存
	 */
	@RequestMapping(value = "/updateSave")
	@ResponseBody
	public String updateSave(HttpServletRequest request,RaCategory raCategory, String id, ModelMap model) {
		RaCategory raCategoryEntity = raCategoryService.getById(id);
		
		RaCategory raCategoryOld = raCategoryService.getByName(raCategory.getName());
		if (raCategoryOld != null && raCategoryEntity.getId().equals(raCategoryOld.getId()) == false) {
			return "此名称已经被使用！请重新填写！";
		}
		raCategoryOld = raCategoryService.getByCode(raCategory.getCode());
		
		if (raCategoryOld != null && raCategoryEntity.getId().equals(raCategoryOld.getId()) == false) {
			return "此编号已经被使用！请重新填写！";
		}
		raCategoryOld = raCategoryService.getBySequenceNumber(raCategory.getSequenceNumber());
		if (raCategoryOld != null && raCategoryEntity.getId().equals(raCategoryOld.getId()) == false) {
			return "此序号已经被使用！请重新填写！";
		}
		
		
		
		raCategoryEntity.setCode(raCategory.getCode());
		raCategoryEntity.setDescription(raCategory.getDescription());
		raCategoryEntity.setName(raCategory.getName());
		raCategoryEntity.setSequenceNumber(raCategory.getSequenceNumber());

		raCategoryService.updateSaveRaCategory(raCategoryEntity);
		
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
					List<RegistrationAuthority> raList = registrationAuthorityService.getByCategoryId(id);
					if(raList.size() > 0){
						model.put("status", -1);
						this.setError(model, "删除失败,此分类正在使用,不允许删除！");
						return SUCCESS;
					}
					raCategoryService.delete(id);
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
		RaCategory raCategory = raCategoryService.getById(id);
		try {
			List<RegistrationAuthority> raList = registrationAuthorityService.getByCategoryId(id);
			if(raList.size() > 0){
				model.put("status", -1);
				this.setError(model, "停用失败,此分类正在使用,不允许停用！");
				return SUCCESS;
			}
			if(raCategory.isStop() == true){
				model.put("status", -1);
				this.setError(model, "此分类已经被停用，不允许再次停用！");
				return SUCCESS;
			}
			raCategory.setStop(true);
			raCategoryService.saveRaCategory(raCategory);
			
		} catch (Exception e) {
			model.put("status", -1);
			this.setError(model, "停用失败,请联系管理员");
		}
		
		model.put("status", 1);
		
		return SUCCESS;
	}
	
	@RequestMapping(value = "/nostop")
	public String onUnAnnounce(HttpServletRequest request,String id, ModelMap model) {
		RaCategory raCategory = raCategoryService.getById(id);
		try {
			if(raCategory.isStop() == false){
				model.put("status", -1);
				this.setError(model, "此分类已经被启用，不允许再次启用！");
				return SUCCESS;
			}
			raCategory.setStop(false);
			raCategoryService.saveRaCategory(raCategory);
			
		} catch (Exception e) {
			model.put("status", -1);
			this.setError(model, "停用失败,请联系管理员");
		}
		
		model.put("status", 1);
		
		return SUCCESS;
	}
}
