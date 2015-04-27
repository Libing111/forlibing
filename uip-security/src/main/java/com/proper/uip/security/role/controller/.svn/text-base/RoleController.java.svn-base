 package com.proper.uip.security.role.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.hr.job.entity.Job;
import com.proper.hr.job.service.IJobService;
import com.proper.hr.job.utils.enums.JobStatus;
import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.ResourceChildNode;
import com.proper.uip.api.security.entity.ResourceTreeNode;
import com.proper.uip.api.security.entity.Role;
import com.proper.uip.api.security.entity.RoleCategory;
import com.proper.uip.api.security.entity.RoleRuleRelation;
import com.proper.uip.api.security.entity.RoleUserRelation;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.extension.RoleFilterRuleExtension;
import com.proper.uip.api.security.service.RoleCategoryService;
import com.proper.uip.api.security.service.RoleResourceRelationService;
import com.proper.uip.api.security.service.RoleRuleRelationService;
import com.proper.uip.api.security.service.RoleService;
import com.proper.uip.api.security.service.RoleUserRelationService;
import com.proper.uip.api.security.service.SecurityService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.web.BaseController;

@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController{
	
	@Autowired
	private RoleService roleSerivce;

	@Autowired
	private RoleResourceRelationService roleResourceRelationService;
	
	@Autowired
	private SecurityService securityService;

	@Autowired
	private RoleCategoryService roleCategoryService;
	
	@Autowired
	private RoleUserRelationService roleUserRelationService;
	
	@Autowired
	private RoleRuleRelationService roleRuleRelationService;
	
	@Autowired
	private IJobService jobService;
	
	@Autowired(required=false)
	@Qualifier("roleCategoryUnsuppressibleManually")
	private String roleCategoryUnsuppressibleManually;//逗号分隔
	
	@Autowired(required=false)
	@Qualifier("roleCategoryDefault")
	private String roleCategoryDefault;//逗号分隔
	
	@Autowired(required=false)
	@Qualifier("roleCategoryRule")
	private String roleCategoryRule;//逗号分隔
	
	@Autowired
	List<RoleFilterRuleExtension> roleFilterRuleExtensionList;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request,ModelMap model) {
		User user = securityService.getCurrentUser(request);
		List<RoleCategory> categoryList = roleCategoryService.getAllRoleCategorys();
		String moc = "subsystem";
		List<Resource> resourcePerentList = roleSerivce.getAllResourceByRa(user,moc);
		
		model.put("resourcePerentList", resourcePerentList);
		model.put("categoryList", categoryList);
		return "/role/index2";
	}
	
	@RequestMapping(value = "/queryChildResource")
	@ResponseBody
	public Map<String,List<ResourceChildNode>> queryChildResource(HttpServletRequest request, ModelMap model) {
		User user = this.getCurrentUser();
		
		String parentChildCode = request.getParameter("parentChildCode");
		
		Map<String,List<ResourceChildNode>> resourceChildNodeMap = new HashMap<String, List<ResourceChildNode>>();
		
		List<ResourceChildNode> resourceChildNodeList = roleSerivce.findChildResource(user,parentChildCode);
		resourceChildNodeMap.put("list", resourceChildNodeList);
		
		return resourceChildNodeMap;
		
	}
	@RequestMapping(value = "/resourceTree/queryAll")
	@ResponseBody
	public List<ResourceTreeNode> queryAllTree(HttpServletRequest request, ModelMap model) {
		User user = this.getCurrentUser();
		List<ResourceTreeNode> resourceTreeNodeList = roleSerivce.buildResourceTree(user);
		return resourceTreeNodeList;
	}
	
	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, String name, String categoryName, String raName, ModelMap model) {
		PageConfig pageConfig = this.createPageConfig(request);
		User user = securityService.getCurrentUser(request);
		String raId = user.getRaId();
		if(user.getLoginName().equals("admin")){
			Page<Role> rolePage = roleSerivce.findAll(pageConfig,name, categoryName,raName);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pager.pageNo", rolePage.getPageNo());
			map.put("pager.totalRows", rolePage.getTotal());
			map.put("rows", rolePage.getRows());
			
			return map;
		}
	
		Page<Role> rolePage = roleSerivce.findAllRole(pageConfig, name, categoryName, raId,raName);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pager.pageNo", rolePage.getPageNo());
		map.put("pager.totalRows", rolePage.getTotal());
		map.put("rows", rolePage.getRows());
		
		return map;
	}

	
	
	

	/**
	 * 保存角色
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(HttpServletRequest request, Role roleEntity, ModelMap model) {
		String ruleId = request.getParameter("ruleId");
		String principalIds = request.getParameter("principalIds");
		String principalValues = request.getParameter("principalValues");
		if(roleEntity.getId().equals("")){
			roleEntity.setId(null);
		}
		Role role = roleSerivce.getCode(roleEntity.getCode());
		if (role != null ) {
			return "此编号已经被使用！请重新填写！";
		}
		User userRa = securityService.getCurrentUser(request);
		Role roleWithName = roleSerivce.getNameUnique(roleEntity.getName(),userRa.getRaId());
	
		if (roleWithName != null) {
			return "此机构下此名称已经被使用！请重新填写！";
		}
			//取当前人注册机构的Id,name,code
		User user = securityService.getCurrentUser(request);
		RoleCategory dataItem = roleCategoryService.getByCode(roleCategoryRule);
		roleEntity.setCreatePerson(user.getName());
		roleEntity.setCreateTime(new Date());
			
		roleEntity.setCategoryName(dataItem.getName());
		roleEntity.setCategoryId(dataItem.getId());
		roleEntity.setCategoryCode(dataItem.getCode());
		roleEntity.setRaId(user.getRaId());
		roleEntity.setRaCode(user.getRaCode());
		roleEntity.setRaName(user.getRaName());
		roleEntity.putExtendProperty("ruleId", ruleId);
		//新增
		if(ruleId == null || ruleId.equals("")){
			dataItem = roleCategoryService.getByCode(roleCategoryDefault);
			roleEntity.setCategoryName(dataItem.getName());
			roleEntity.setCategoryId(dataItem.getId());
			roleEntity.setCategoryCode(dataItem.getCode());
			
			roleSerivce.saveRole(roleEntity);
			return SUCCESS;
		}
		roleSerivce.saveRole(roleEntity,ruleId, principalIds,principalValues);
		return SUCCESS;

	}
	/**
	 * 保存角色
	 */
	@RequestMapping(value = "/updateSave")
	@ResponseBody
	public String updateSave(HttpServletRequest request, Role roleEntity, ModelMap model) {
		String ruleId = request.getParameter("ruleId");
		String principalIds = request.getParameter("principalIds");
		String principalValues = request.getParameter("principalValues");
		if(roleEntity.getId().equals("")){
			roleEntity.setId(null);
		}
		Role role = roleSerivce.getCode(roleEntity.getCode());
		if (role != null && role.getId().equals(roleEntity.getId()) == false) {
			return "此编号已经被使用！请重新填写！";
		}
		User userRa = securityService.getCurrentUser(request);
		Role roleWithName = roleSerivce.getNameUnique(roleEntity.getName(),userRa.getRaId());
	
		if (roleWithName != null && roleWithName.getId().equals(roleEntity.getId()) == false) {
			return "此机构下此名称已经被使用！请重新填写！";
		}
		Role oldRole = roleSerivce.getRoleById(roleEntity.getId());
		oldRole.setName(roleEntity.getName());
		oldRole.setCode(roleEntity.getCode());
		oldRole.setDecription(roleEntity.getDecription());
		oldRole.putExtendProperty("ruleId", ruleId);
		if(ruleId == null || ruleId.equals("")){
			roleSerivce.saveRole(oldRole);
			return SUCCESS;
		}
		roleSerivce.saveRole(oldRole,ruleId, principalIds,principalValues);
		return SUCCESS;

	}

	/**
	 * 角色修改
	 */

	@RequestMapping(value = "/update")
	public String update(String id, ModelMap model) {
		User user = this.getCurrentUser();
		List<RoleCategory> categoryList = roleCategoryService.getAllRoleCategorys();
		Role roleEntity = roleSerivce.getRoleById(id);
		RoleRuleRelation roleRuleRelation = roleRuleRelationService.getRoleRuleRelationByRoleId(roleEntity.getId());
		
		
		//String ruleId = roleEntity.getExtendProperty("ruleId");
		List<RoleFilterRuleExtension> roleFilterRuleExtensionLists = new ArrayList<RoleFilterRuleExtension>();
		for (RoleFilterRuleExtension RoleFilterRuleExtension : roleFilterRuleExtensionList) {
			if(RoleFilterRuleExtension.getCode().equals("6")){
				continue;
			}
			roleFilterRuleExtensionLists.add(RoleFilterRuleExtension);
		}
		model.put("roleFilterRuleExtensionList", roleFilterRuleExtensionLists);
		model.put("categoryList", categoryList);
		if(roleRuleRelation != null){
			model.put("ruleId", roleRuleRelation.getRuleId());
			model.put("ruleValue", roleRuleRelation.getRuleValue());
			model.put("ruleKey", roleRuleRelation.getRuleKey());
		}
		
		model.put("userId", user.getId());
		model.addAttribute("roleEntity", roleEntity);

		return "/role/updateForm";
	}
	
	/**
	 * 角色增加
	 */
	@RequestMapping(value = "/add")
	public String add(ModelMap model) {
		User user = this.getCurrentUser();
		List<RoleCategory> categoryList = roleCategoryService.getAllRoleCategorys();
		
		model.put("categoryList", categoryList);
		model.put("userId", user.getId());
		List<RoleFilterRuleExtension> roleFilterRuleExtensionLists = new ArrayList<RoleFilterRuleExtension>();
		for (RoleFilterRuleExtension RoleFilterRuleExtension : roleFilterRuleExtensionList) {
			if(RoleFilterRuleExtension.getCode().equals("6")){
				continue;
			}
			roleFilterRuleExtensionLists.add(RoleFilterRuleExtension);
		}
		model.put("roleFilterRuleExtensionList", roleFilterRuleExtensionLists);
		return "/role/newStandard";
	}
	
	

	/**
	 * 角色删除
	 */

	@RequestMapping(value = "/del")
	public String del(String ids, ModelMap model) {
		List<String> roleCategoryUnsuppressibleManuallyList = Arrays.asList(roleCategoryUnsuppressibleManually.split(","));
		Set<String> roleCategoryUnsuppressibleManuallySet = new HashSet<String>();
		roleCategoryUnsuppressibleManuallySet.addAll(roleCategoryUnsuppressibleManuallyList);
		try {
			if (StringUtils.isNotEmpty(ids)) {
				for (String id : ids.split(",")) {
					Role role = roleSerivce.getRoleById(id);
					List<RoleUserRelation> roleUserRelation = roleUserRelationService.getRoleId(id);
					if(roleUserRelation.size() > 0){
						model.put("status", -1);
						this.setError(model, "此角色被引用确定要删除吗？");
						return SUCCESS;
					}
					if(role.getCategoryCode() != null && roleCategoryUnsuppressibleManuallySet.contains(role.getCategoryCode())){
						model.put("status", -1);
						this.setError(model, "删除失败,此账号不允许删除！");
						return SUCCESS;
					}
					roleSerivce.deleteRoleById(id);
				}
			}
		} catch (Exception e) {
			model.put("status", -1);
			this.setError(model, "删除失败,请联系管理员");
		}
		
		model.put("status", 1);
		
		return SUCCESS;
	}

	
	@RequestMapping(value = "/confirmDel")
	public String confirmDel(String ids, ModelMap model) {
		List<String> roleCategoryUnsuppressibleManuallyList = Arrays.asList(roleCategoryUnsuppressibleManually.split(","));
		Set<String> roleCategoryUnsuppressibleManuallySet = new HashSet<String>();
		roleCategoryUnsuppressibleManuallySet.addAll(roleCategoryUnsuppressibleManuallyList);
		try {
			if (StringUtils.isNotEmpty(ids)) {
				for (String id : ids.split(",")) {
//					Role role = roleSerivce.getRoleById(id);
//					
//					if(role.getCategoryCode() != null && roleCategoryUnsuppressibleManuallySet.contains(role.getCategoryCode())){
//						model.put("status", -1);
//						this.setError(model, "删除失败,此账号不允许删除！");
//						return SUCCESS;
//					}
					roleSerivce.deleteRoleById(id);
				}
			}
		} catch (Exception e) {
			model.put("status", -1);
			this.setError(model, "删除失败,请联系管理员");
		}
		
		model.put("status", 1);
		
		return SUCCESS;
	}
//	@RequestMapping(value = "/authority/query")
//	@ResponseBody
//	public List<TreeNode> queryAuthority(String roleId, HttpServletRequest request, ModelMap model) {
//		List<TreeNode> tree = roleSerivce.buildAuthorityTree(roleId);
//		
//		return tree;
//	}
	
	@RequestMapping(value = "/resourceTree/query")
	@ResponseBody
	public List<ResourceTreeNode> queryTree(String roleId, boolean chkDisabled, HttpServletRequest request, ModelMap model) {
		List<ResourceTreeNode> resourceTreeNodeList = roleSerivce.buildResourceTree(roleId, chkDisabled);
		return resourceTreeNodeList;
	}
	
	@RequestMapping(value = "/resourceTree/queryList")
	@ResponseBody
	public List<ResourceTreeNode> queryTreeList(String roleId, boolean chkDisabled, HttpServletRequest request, ModelMap model) {
		List<ResourceTreeNode> resourceTreeNodeList = roleSerivce.buildResourceTree(roleId);
		return resourceTreeNodeList;
	}
	/**
	 * 添加角色树管理
	 */
	@RequestMapping(value = "/tree/update")
	@ResponseBody
	public String  updateTree(String roleId, String resourceSequenceNumbers, ModelMap model) {
		
		roleResourceRelationService.updateTree(roleId, resourceSequenceNumbers);
		
		return SUCCESS;
	}
	@RequestMapping(value = "/addResource")
	@ResponseBody
	public String  addResource(String roleId, String childResourceIds, ModelMap model) {
		
		roleResourceRelationService.updateTree(roleId, childResourceIds);
		
		return SUCCESS;
	}
	@RequestMapping(value = "/queryResource")
	@ResponseBody
	public Map<String,String> queryResource(String roleId, HttpServletRequest request, ModelMap model) {
		Map<String,String> resourceMap = roleSerivce.getFilterResource(roleId);
		
		
		return resourceMap;
	}
	/**
	 * 全选一行
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/clickParentResource")
	@ResponseBody
	public Map<String,String>  clickParentResource(HttpServletRequest request, ModelMap model) {
		User user = this.getCurrentUser();
		String pagingId = request.getParameter("pagingId");
		Map<String,String> allResourceMap = roleSerivce.getChildResource(request, user,pagingId);
		
		return allResourceMap;
	}
	
	@RequestMapping(value = "/allResource")
	@ResponseBody
	public Map<String,String>  allResource(HttpServletRequest request, ModelMap model) throws UnsupportedEncodingException {
		User user = this.getCurrentUser();
		Map<String, String> allResourceMap = new HashMap<String, String>();
		String moc = "subsystem";
		List<Resource> resourcePerentList = roleSerivce.getAllResourceByRa(user,moc);
		
		for (Resource pagingResource : resourcePerentList) {
			StringBuffer childResourceId = new StringBuffer();
			List<Resource> resourceNodeList =   roleSerivce.findResource(user,pagingResource.getCode());
			for (Resource resource : resourceNodeList) {
				childResourceId.append(",").append(resource.getCode());
			}
			
			allResourceMap.put(pagingResource.getCode(), childResourceId.toString());
		}
		return allResourceMap;
	}
	/*
	 * 职务选择器
	 */
	@RequestMapping("/range/job")
	@ResponseBody
	public Map<String,Object> candidateJobs(HttpServletRequest request){
//		Map<String, Object> params = new HashMap<String, Object>();
		String name = request.getParameter("name");
//		if(name != null && !name.equals("")){
//			params.put("name", "%"+name+"%");
//		}
//		params.put("status", Status.ACTIVE);
		Page<Job> page = jobService.getJobPage(this.createPageConfig(request), null, name, JobStatus.ACTIVE);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pager.totalRows", page.getTotal());
		map.put("rows", page.getRows());
		return map;
	}
	/*
	 * 职务选择器
	 */
	@RequestMapping("/range/job/selected")
	@ResponseBody
	public Map<String,Object> candidateJobsOfSelected(@RequestParam("ids") String idstr){
		List<Job> jobList = new ArrayList<Job>();
		String[] ids = idstr.split(",");
		Set<String> idSet = new HashSet<String>();
		for(String id : ids){
			idSet.add(id);
		}
		jobList = jobService.getJobByIds(idSet);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pager.totalRows", jobList.size());
		map.put("rows", jobList);
		return map;
	}
}
