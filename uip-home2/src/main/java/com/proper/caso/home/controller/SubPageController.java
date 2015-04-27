/* <p>文件名称: SubPageController.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-11-4</p>
 * <p>完成日期：2014-11-4</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-11-4下午3:31:30
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.caso.home.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.caso.home.application.model.MenuNode;
import com.proper.caso.home.service.HomeService;
import com.proper.uip.web.BaseController;

@Controller
@RequestMapping(value = "/subpage")
public class SubPageController extends BaseController{
	@Autowired
	@Qualifier("HomeServiceImpl-API")
	private HomeService homeService;
	/**
	 * 模块列表页
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String subpage(HttpServletRequest request, ModelMap modelMap) {
		String menuId = request.getParameter("menuId");
		
		modelMap.put("menuId", menuId);
		return "subpage";
	}
	
	@RequestMapping(value = "/module/getList")
	@ResponseBody
	public List<MenuNode> modules(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		String menuId = request.getParameter("menuId");
		List<MenuNode> menuNodeList = homeService.getModules(request, menuId);
		
		return menuNodeList;
	}
}

