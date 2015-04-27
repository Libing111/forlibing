package com.proper.uip.security.secureconf.racateResset.controller;

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
import com.proper.uip.api.security.entity.RacateResset;
import com.proper.uip.api.security.entity.ResourceSet;
import com.proper.uip.api.security.service.RaCategoryService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.resourceSet.service.ResourceSetService;
import com.proper.uip.security.secureconf.racateResset.service.RacateRessetService;
import com.proper.uip.web.BaseController;

@Controller
@RequestMapping(value = "/secureconf/racate/resset")
public class RacateRessetController extends BaseController{
	@Autowired
	private RaCategoryService raCategoryService;
	
	@Autowired
	private ResourceSetService resourceSetService;
	
	@Autowired
	private RacateRessetService racateRessetService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request, ModelMap model) {
		List<ResourceSet> resourceSetList = resourceSetService.getAllResourceSet();
		
		model.put("resourceSetList", resourceSetList);
		
		return "/racate/index";
	}
	
	@RequestMapping(value = "/racate/query")
	@ResponseBody
	public Map<String, Object> queryRacate(HttpServletRequest request,ModelMap model) {
		PageConfig pageConfig = this.createPageConfig(request);
		List<RaCategory> category = raCategoryService.getAllRaCategorys();
		Map<String, Object> map = new HashMap<String, Object>();
		Page<RaCategory> dataItemPage = new Page<RaCategory>(pageConfig);
		dataItemPage.setRows(category);
		
		map.put("pager.pageNo", dataItemPage.getPageNo());
		map.put("pager.totalRows", dataItemPage.getTotal());
		map.put("rows", dataItemPage.getRows());
		
		return map;
	}
	
	/**
	 * 添加资源集
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String  save(String raCategoryId, String resourceSetId, ModelMap model) {
		racateRessetService.save(raCategoryId, resourceSetId);
		
		return SUCCESS;
	}
	
	/**
	 * 资源集
	 * @param request
	 * @param name
	 * @param 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/resourceSet/query")
	@ResponseBody
	public Map<String, Object> queryResourceSet(HttpServletRequest request, String raCategoryId, ModelMap model) {
		PageConfig pageConfig = this.createPageConfig(request);
		Page<RacateResset> resourceSetList = racateRessetService.findAllResourceSet(pageConfig,raCategoryId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pager.pageNo", resourceSetList.getPageNo());
		map.put("pager.totalRows", resourceSetList.getTotal());
		map.put("rows", resourceSetList.getRows());
		
		return map;
	}
	
	@RequestMapping(value = "/del")
	public String delRole(String resourceSetId,String raCategoryId, ModelMap model) {
		if (StringUtils.isNotEmpty(resourceSetId) == false) {
			model.put("status", -1);
			this.setError(model, "删除失败 , 没有选中资源集");
		}
		
		try {
			racateRessetService.del(resourceSetId, raCategoryId);
		} catch (Exception e) {
			model.put("status", -1);
			this.setError(model, "删除失败,请联系管理员");
		}
		
		model.put("status", 1);
		
		return SUCCESS;
	}
	
}
