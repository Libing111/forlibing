/* <p>文件名称: ResourceCategoryController.java </p>
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
package com.proper.uip.security.resource.controller;

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

import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.ResourceCategory;
import com.proper.uip.api.security.service.ResourceCategoryService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.resource.service.ResourceService;
import com.proper.uip.web.BaseController;
@Controller
@RequestMapping(value = "/resourceCategory")
public class ResourceCategoryController extends BaseController{
	@Autowired
	private ResourceCategoryService resourceCategoryService;
	
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping("/index")
	public String index(ModelMap model) {
		
		return "/resource/resourceCategory/index";
	}
	
	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, String name, ModelMap model) {
		PageConfig pageConfig = this.createPageConfig(request);
		String stop = request.getParameter("stop");
		
		Page<ResourceCategory> resourceCategoryList = resourceCategoryService.findAll(pageConfig, name, stop);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pager.pageNo", resourceCategoryList.getPageNo());
		map.put("pager.totalRows", resourceCategoryList.getTotal());
		map.put("rows", resourceCategoryList.getRows());
		
		return map;
	}
	/**
	 * 增加
	 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request,ModelMap model) {
		
		return "/resource/resourceCategory/add";
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value = "/update")
	public String update(String id, ModelMap model) {
		ResourceCategory resourceCategoryEntity = resourceCategoryService.getById(id);
		model.addAttribute("resourceCategoryEntity", resourceCategoryEntity);
		return "/resource/resourceCategory/update";
	}
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(HttpServletRequest request, ResourceCategory resourceCategory, ModelMap model) {
		//新增
		ResourceCategory resourceCategoryOld = resourceCategoryService.getByCode(resourceCategory.getCode());
		if (resourceCategoryOld != null) {
			return "此编号已经被使用！请重新填写！";
		}
		
		resourceCategoryOld = resourceCategoryService.getBySequenceNumber(resourceCategory.getSequenceNumber());
		
		if (resourceCategoryOld != null) {
			return "此序号已经被使用！请重新填写！";
		}
		resourceCategoryOld = resourceCategoryService.getByName(resourceCategory.getName());
		
		if (resourceCategoryOld != null) {
			return "此名称已经被使用！请重新填写！";
		}
		if (resourceCategory.getId() == null || resourceCategory.getId().equals("")) {
			resourceCategory.setStop(false);
			resourceCategory.setId(null);
		}
		//保存管理信息
		resourceCategoryService.saveResourceCategory(resourceCategory);
		
		return SUCCESS;

	}
	/**
	 * 保存
	 */
	@RequestMapping(value = "/updateSave")
	@ResponseBody
	public String updateSave(HttpServletRequest request,ResourceCategory resourceCategory, String id, ModelMap model) {
		//新增
				ResourceCategory resourceCategoryOld = resourceCategoryService.getByCode(resourceCategory.getCode());
				if (resourceCategoryOld != null  && resourceCategoryOld.getId().equals(resourceCategory.getId()) == false) {
					return "此编号已经被使用！请重新填写！";
				}
				
				resourceCategoryOld = resourceCategoryService.getBySequenceNumber(resourceCategory.getSequenceNumber());
				
				if (resourceCategoryOld != null && resourceCategoryOld.getId().equals(resourceCategory.getId()) == false) {
					return "此序号已经被使用！请重新填写！";
				}
				resourceCategoryOld = resourceCategoryService.getByName(resourceCategory.getName());
				
				if (resourceCategoryOld != null && resourceCategoryOld.getId().equals(resourceCategory.getId()) == false) {
					return "此名称已经被使用！请重新填写！";
				}		
		
		ResourceCategory resourceCategoryEntity = resourceCategoryService.getById(id);
		
		resourceCategoryEntity.setCode(resourceCategory.getCode());
		resourceCategoryEntity.setDescription(resourceCategory.getDescription());
		resourceCategoryEntity.setName(resourceCategory.getName());
		resourceCategoryEntity.setSequenceNumber(resourceCategory.getSequenceNumber());

		resourceCategoryService.updateSaveResourceCategory(resourceCategoryEntity);
		
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
					List<Resource> resourceList = resourceService.getByCategoryId(id);
					if(resourceList.size() > 0){
						model.put("status", -1);
						this.setError(model, "删除失败,此分类正在使用,不允许删除！");
						return SUCCESS;
					}
					resourceCategoryService.delete(id);
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
		ResourceCategory resourceCategory = resourceCategoryService.getById(id);
		try {
			List<Resource> resourceList = resourceService.getByCategoryId(id);
			if(resourceList.size() > 0){
				model.put("status", -1);
				this.setError(model, "停用失败,此分类正在使用,不允许停用！");
				return SUCCESS;
			}
			if(resourceCategory.isStop() == true){
				model.put("status", -1);
				this.setError(model, "此分类已经被停用，不允许再次停用！");
				return SUCCESS;
			}
			resourceCategory.setStop(true);
			resourceCategoryService.saveResourceCategory(resourceCategory);
			
		} catch (Exception e) {
			model.put("status", -1);
			this.setError(model, "停用失败,请联系管理员");
		}
		
		model.put("status", 1);
		
		return SUCCESS;
	}
	
	@RequestMapping(value = "/nostop")
	public String onUnAnnounce(HttpServletRequest request,String id, ModelMap model) {
		ResourceCategory resourceCategory = resourceCategoryService.getById(id);
		try {
			if(resourceCategory.isStop() == false){
				model.put("status", -1);
				this.setError(model, "此分类已经被启用，不允许再次启用！");
				return SUCCESS;
			}
			resourceCategory.setStop(false);
			resourceCategoryService.saveResourceCategory(resourceCategory);
			
		} catch (Exception e) {
			model.put("status", -1);
			this.setError(model, "启用失败,请联系管理员");
		}
		
		model.put("status", 1);
		
		return SUCCESS;
	}
}
