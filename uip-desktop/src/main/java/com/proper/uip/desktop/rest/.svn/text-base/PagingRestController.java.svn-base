/* <p>文件名称: PagingRestController.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-11-24</p>
 * <p>完成日期：2014-11-24</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-11-24上午10:20:37
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.desktop.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.uip.api.desktop.entity.ApplicationAndGroup;
import com.proper.uip.api.desktop.wrapper.ModuleList;
import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.service.ResourceService;
import com.proper.uip.desktop.service.ModuleService;
import com.proper.uip.web.BaseController;

@Controller
public class PagingRestController extends BaseController {
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private ModuleService desktopService;
	
	@RequestMapping(method=RequestMethod.GET, value="/rest/pagings", headers="Accept=application/json")
	@ResponseBody
	public ModuleList pagings(HttpServletRequest request, ModelMap model) {
		List<ApplicationAndGroup> pagings = new ArrayList<ApplicationAndGroup>();
		
		String systemCategory = request.getParameter("systemCategory");
		if(systemCategory == null){
			ModuleList moduleList = new ModuleList(pagings);
			return moduleList;
		}
		
		User user = super.getCurrentUser();
		if(user == null){
			ModuleList moduleList = new ModuleList(pagings);
			return moduleList;
		}
		
		List<Resource> resourceList = resourceService.getModuleResourcesOfCurrentUser(request);
		Set<String> resourceSet = new HashSet<String>();
		for(Resource application : resourceList){
			resourceSet.add(application.getId());
		}
		
		pagings = desktopService.getModules(systemCategory, resourceSet, ModuleService.DESKTOP_MOC_PAGING);
		ModuleList moduleList = new ModuleList(pagings);
		return moduleList;
	}
}

