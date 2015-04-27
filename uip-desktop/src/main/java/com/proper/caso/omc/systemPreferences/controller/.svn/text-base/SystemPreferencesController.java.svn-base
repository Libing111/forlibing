package com.proper.caso.omc.systemPreferences.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.caso.omc.systemPreferences.entity.SystemPreferences;
import com.proper.caso.omc.systemPreferences.service.SystemPreferencesService;
import com.proper.uip.api.security.entity.ResourceCategory;
import com.proper.uip.api.security.service.ResourceCategoryService;
import com.proper.uip.web.BaseController;

@Controller
@RequestMapping(value = "/systema/preferences")
public class SystemPreferencesController extends BaseController{
	@Autowired
	private ResourceCategoryService resourceCategoryService;
	
	@Autowired
	private SystemPreferencesService systemPreferencesService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request, ModelMap model) {
		//系统分类
		String systemCategory = request.getParameter("systemCategory");
		model.put("systemCategory", systemCategory);
		//资源分类
		String resouceCategoryList = "resouceCategoryList";
		SystemPreferences resouceCategory = systemPreferencesService.getRc(resouceCategoryList);
		if(resouceCategory == null){
			List<ResourceCategory> categoryList = resourceCategoryService.getAllResourceCategorys();
			
			model.put("categoryList", categoryList);
			
			return "/systemPreferences/index";
		}
		String[] value = resouceCategory.getValue().split(",");
		model.put("value", value);
		//桌面文字标题
		String desktopTitle = "desktopTitle";
		SystemPreferences desktopTitleSY = systemPreferencesService.getRc(desktopTitle);
		if(desktopTitleSY != null){
			model.put("desktopTitleSY", desktopTitleSY);
		}
		//桌面背景图片路径
		String backgroudpicture = "backgroudpicture";
		SystemPreferences backgroudpictureSY = systemPreferencesService.getRc(backgroudpicture);
		if(backgroudpictureSY != null){
			model.put("backgroudpictureSY", backgroudpictureSY);
		}
		List<ResourceCategory> categoryList = resourceCategoryService.getAllResourceCategorys();
		model.put("categoryList", categoryList);
		return "/systemPreferences/index";
		
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(HttpServletRequest request, ModelMap model) {
		String systemCategory = request.getParameter("systemCategory");
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String picture = request.getParameter("picture");
		String msgCode = request.getParameter("msgCode");
		systemPreferencesService.saveSystemPreferences(systemCategory,id,title,picture,msgCode);
		
		return SUCCESS;
	}
	
}


