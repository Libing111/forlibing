package com.proper.uip.security.registrationAuthority.controller;


import java.util.ArrayList;
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
import com.proper.uip.api.security.entity.RaResourceSetRelation;
import com.proper.uip.api.security.entity.RegistrationAuthority;
import com.proper.uip.api.security.entity.ResourceSet;
import com.proper.uip.api.security.entity.Role;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.service.RaCategoryService;
import com.proper.uip.api.security.service.RoleService;
import com.proper.uip.api.security.service.SecurityService;
import com.proper.uip.api.security.service.UserService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.registrationAuthority.service.RaResourceSetRelationService;
import com.proper.uip.security.registrationAuthority.service.RegistrationAuthorityService;
import com.proper.uip.security.resourceSet.service.ResourceSetService;
import com.proper.uip.web.BaseController;

@Controller
@RequestMapping(value = "/registrationAuthority")
public class RegistrationAuthorityController extends BaseController {
	
	@Autowired
	private RegistrationAuthorityService registrationAuthorityService;
	
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private RaCategoryService raCategoryService;

	@Autowired
	private ResourceSetService resourceSetService;
	
	@Autowired
	private RaResourceSetRelationService raResourceSetRelationService;
	
	@Autowired
	private com.proper.uip.api.security.service.RegistrationAuthorityService raService;
	
	
	@RequestMapping("/index")
	public String index(ModelMap model) {
		List<RaCategory> categoryList = raCategoryService.getAllRaCategorys();
		List<ResourceSet> resourceSetList = resourceSetService.getAllResourceSet();
		
		model.put("categoryList", categoryList);
		
//		Map<String, List<ResourceSetNode>> resourceSetData = new HashMap<String, List<ResourceSetNode>>();
//		resourceSetData.put("treeNodes", resourceSetNode);
//		
		model.put("resourceSetList", resourceSetList);
		
		return "/registrationAuthority/index";
	}
	
	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, String name, String categoryName, ModelMap model) {
		PageConfig pageConfig = this.createPageConfig(request);
		Page<RegistrationAuthority> raList = registrationAuthorityService.findAllRa(pageConfig, name, categoryName);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pager.pageNo", raList.getPageNo());
		map.put("pager.totalRows", raList.getTotal());
		map.put("rows", raList.getRows());
		
		return map;
	}
	/**
	 * 资源集
	 * @param request
	 * @param name
	 * @param 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/raResourceSet/query")
	@ResponseBody
	public Map<String, Object> queryResourceSet(HttpServletRequest request, String raId, ModelMap model) {
		PageConfig pageConfig = this.createPageConfig(request);
		Page<RaResourceSetRelation> raResourceSetList = raResourceSetRelationService.findAllRaResourceSet(pageConfig,raId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pager.pageNo", raResourceSetList.getPageNo());
		map.put("pager.totalRows", raResourceSetList.getTotal());
		map.put("rows", raResourceSetList.getRows());
		
		return map;
	}
	/**
	 * 添加注册机构管理
	 */
	@RequestMapping(value = "/add")
	public String add(ModelMap model) {
		List<RaCategory> categoryList = raCategoryService.getAllRaCategorys();
		
		model.put("categoryList", categoryList);
		
		return "/registrationAuthority/newStandard";
	}
	
	
	
	
	/**
	 * 修改
	 */
	@RequestMapping(value = "/update")
	public String update(String id, ModelMap model) {
		RegistrationAuthority raEntity = registrationAuthorityService.getRaById(id);
		List<RaCategory> categoryList = raCategoryService.getAllRaCategorys();
		
		model.addAttribute("raEntity", raEntity);
		model.put("categoryList", categoryList);
		
		return "/registrationAuthority/updateForm";
	}
	

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(HttpServletRequest request, RegistrationAuthority raEntity, ModelMap model) {
		List<RegistrationAuthority> raOld = registrationAuthorityService.getByCodeList(raEntity.getCode());
		if (raOld.isEmpty() == false) {
			return "此编号已经被使用！请重新填写！";
		}
		RegistrationAuthority raOldByName = registrationAuthorityService.getByName(raEntity.getName());
		if (raOldByName != null) {
			return "此名称已经被使用！请重新填写！";
		}
		
		//取当前用户
		User currentUser = securityService.getCurrentUser(request);
	
		registrationAuthorityService.saveRa(currentUser,raEntity);

		return SUCCESS;
	}
		
	/**
	 * 保存修改
	 */
	@RequestMapping(value = "/saveUpdate")
	@ResponseBody
	public String saveUpdate(HttpServletRequest request, RegistrationAuthority raEntity, ModelMap model) {
		if (raEntity.getId() == null || raEntity.getId().equals("")) {
			this.save(request, raEntity, model);
		}
		List<RegistrationAuthority> raOld = registrationAuthorityService.getByCodeList(raEntity.getCode());
		List<String> raOldIdList = new ArrayList<String>();
		for (RegistrationAuthority ra : raOld) {
			raOldIdList.add(ra.getId());
		}
		String raOldId = null;
		for (String raId : raOldIdList) {
			if(raId.equals(raEntity.getId())){
				raOldId = raId;
			}
				
		}
		
		if (raOld != null && raEntity.getId().equals(raOldId) == false) {
			return "此编号已经被使用！请重新填写！";
		}
		RegistrationAuthority raOldByName = registrationAuthorityService.getByName(raEntity.getName());
		if (raOldByName != null && raEntity.getId().equals(raOldByName.getId()) == false) {
			return "此名称已经被使用！请重新填写！";
		}
		//取当前用户
		User currentUser = securityService.getCurrentUser(request);
		raEntity.setLastChangePerson(currentUser.getName());	
		registrationAuthorityService.saveUpdateRa(currentUser, raEntity);

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
					List<User> userList = userService.getByRaId(id);
					List<Role> roleList = roleService.getByRaId(id);
					if(userList.size() > 0 || roleList.size() > 0){
						model.put("status", -1);
						this.setError(model, "删除失败,此注册机构不允许删除！请先删除本机构下的用户或角色");
						return SUCCESS;
					}
					registrationAuthorityService.deleteRaById(id);
				}
			}
		} catch (Exception e) {
			model.put("status", -1);
			this.setError(model, "删除失败,请联系管理员");
		}
		
		model.put("status", 1);
		
		return SUCCESS;
	}
	
	/**
	 * 删除资源集
	 */

	@RequestMapping(value = "/deleteResourceSet")
	public String deleteResourceSet(String ids, ModelMap model) {
		try {
			if (StringUtils.isNotEmpty(ids)) {
				for (String id : ids.split(",")) {
					raResourceSetRelationService.delete(id);
				}
			}
		} catch (Exception e) {
			model.put("status", -1);
			this.setError(model, "删除失败,请联系管理员");
		}
		
		model.put("status", 1);
		
		return SUCCESS;
	}

	/**
	 * 添加资源集
	 */
	@RequestMapping(value = "/resourceSet/add")
	@ResponseBody
	public String  resourceSetAdd(String raId, String resourceSetId, ModelMap model) {
		RaResourceSetRelation raResourceSetRelation = raResourceSetRelationService.getByRaIdAndResourceId(raId,resourceSetId);
		if (raResourceSetRelation != null) {
			return "此资源集已经被添加过，不可以重复添加！";
		}
		raResourceSetRelationService.saveraResourceSet(raId, resourceSetId);
		raService.resetRaFullAuthorityByRaId(raId);
		return SUCCESS;
	}
}
