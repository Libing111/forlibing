package com.proper.uip.bpm.scenario.category.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.uip.api.bpm.definitions.entity.ScenarioCategory;
import com.proper.uip.api.bpm.service.BpmScenarioStrategy;
import com.proper.uip.bpm.scenario.category.service.BpmScenarioStrategyService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.web.BaseController;


@Controller
@RequestMapping(value = "/scenario/category")
public class BpmScenarioCategoryController extends BaseController{
	
	@Autowired
	private List<BpmScenarioStrategy> bpmScenarioStrategyList;
	
	@Autowired
	private BpmScenarioStrategyService bpmScenarioStrategyService;
	
	@RequestMapping("/index")
	public String index(ModelMap model) {
		
		return "/scenarioCategory/index";
	}
	
	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request,String name, ModelMap model) {
		PageConfig pageConfig = this.createPageConfig(request);
		Page<ScenarioCategory> scenarioStrategyList = bpmScenarioStrategyService.findAll(pageConfig, name);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pager.pageNo", scenarioStrategyList.getPageNo());
		map.put("pager.totalRows", scenarioStrategyList.getTotal());
		map.put("rows", scenarioStrategyList.getRows());
		
		return map;
	}
	
	/**
	 * 创建
	 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, ModelMap model) {
		
		model.put("bpmScenarioStrategyList", bpmScenarioStrategyList);
		
		return "/scenarioCategory/add";
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(HttpServletRequest request, ModelMap model) {
		ScenarioCategory scenarioCategory = new ScenarioCategory();
		String strategyName = request.getParameter("strategyName");
		String name = request.getParameter("name");
		String strategyId = request.getParameter("strategyId");
		scenarioCategory.setId(null);
		scenarioCategory.setName(name);
		scenarioCategory.setStrategyId(strategyId);
		scenarioCategory.setStrategyName(strategyName);
		
		bpmScenarioStrategyService.saveScenarioStrategy(scenarioCategory);
		
		return SUCCESS;
	}
	
}
