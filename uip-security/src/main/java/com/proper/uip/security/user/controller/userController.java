package com.proper.uip.security.user.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.hr.personnel.entity.Person;
import com.proper.hr.personnel.service.IPersonService;
import com.proper.uip.api.security.entity.RegistrationAuthority;
import com.proper.uip.api.security.entity.Role;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.entity.User.ActiveStatus;
import com.proper.uip.api.security.entity.UserCategory;
import com.proper.uip.api.security.service.RoleService;
import com.proper.uip.api.security.service.SecurityService;
import com.proper.uip.api.security.service.UserCategoryService;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.registrationAuthority.service.RegistrationAuthorityService;
import com.proper.uip.security.user.service.UserService;
import com.proper.uip.security.utils.PasswordEncodingUtil;
import com.proper.uip.web.BaseController;

@Controller
@RequestMapping(value = "/user")
public class userController extends BaseController {
	@Autowired
	private RoleService roleSerivce;

	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private UserService userSerivce;
	
	@Autowired
	private IPersonService personService;
	
	@Autowired
	@Qualifier("defaultPassword")
	private String defaultPassword = "123456";
	
	@Autowired
	private UserCategoryService userCategoryService;

	@Autowired(required=false)
	@Qualifier("userCategoryUnsuppressibleManually")
	private String userCategoryUnsuppressibleManually;//逗号分隔
	
	@Autowired(required=false)
	@Qualifier("userCategoryDefault")
	private String userCategoryDefault;//逗号分隔
	
	@Autowired
	private RegistrationAuthorityService raService;
	
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request, ModelMap model) {
		List<RegistrationAuthority> raList = raService.getAll();
		
		User user = securityService.getCurrentUser(request);
		String userRaId = user.getRaId();
		if(userRaId != null){
			raList = new ArrayList<RegistrationAuthority>();
			RegistrationAuthority ra = raService.getRaById(userRaId);
			if(ra != null){
				raList.add(ra);
			}
		}
		
		List<UserCategory> categoryList = userCategoryService.getAllUserCategorys();
		model.put("categoryList", categoryList);
		model.put("raList", raList);
		model.put("userId", user.getId());
		return "/user/index";
	}

	/**
	 * 用户增加
	 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, ModelMap model) {
		List<RegistrationAuthority> raList = raService.getAll();
		
		User user = securityService.getCurrentUser(request);
		String userRaId = user.getRaId();
		if(userRaId != null){
			raList = new ArrayList<RegistrationAuthority>();
			RegistrationAuthority ra = raService.getRaById(userRaId);
			if(ra != null){
				raList.add(ra);
			}
		}
		
		List<UserCategory> categoryList = userCategoryService.getAllUserCategorys();
		model.put("categoryList", categoryList);
		model.put("raList", raList);
		return "/user/newStandard";
	}
	
	/**
	 *用户修改
	 */

	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request, String id, ModelMap model) {
		List<RegistrationAuthority> raList = raService.getAll();
		
		User user = securityService.getCurrentUser(request);
		String userRaId = user.getRaId();
		if(userRaId != null){
			raList = new ArrayList<RegistrationAuthority>();
			RegistrationAuthority ra = raService.getRaById(userRaId);
			if(ra != null){
				raList.add(ra);
			}
		}
		
		List<UserCategory> categoryList = userCategoryService.getAllUserCategorys();
		model.put("categoryList", categoryList);
		model.put("raList", raList);
		
		User userEntity = userSerivce.getUserById(id);
		model.addAttribute("userEntity", userEntity);

		return "/user/newStandard";
	}
	
	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, String name, String loginName,String raName, ModelMap model) {
		User user = securityService.getCurrentUser(request);
		String raId = user.getRaId();
		PageConfig pageConfig = this.createPageConfig(request);
		if(user.getLoginName().equals("admin")){
			Page<User> userPage = userSerivce.findAll(pageConfig,name,loginName, raName);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pager.pageNo", userPage.getPageNo());
			map.put("pager.totalRows", userPage.getTotal());
			map.put("rows", userPage.getRows());
			
			return map;
		}
		Page<User> userPage = userSerivce.findAllUser(pageConfig, name, loginName, raName, raId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pager.pageNo", userPage.getPageNo());
		map.put("pager.totalRows", userPage.getTotal());
		map.put("rows", userPage.getRows());
		
		return map;
	}


	/**
	 * 保存用户
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(HttpServletRequest request, User userEntity, ModelMap model) {
		if(userEntity.getId().equals("")){
			userEntity.setId(null);
		}
		User user = userSerivce.getLogin(userEntity.getAccount());
		if (user != null && user.getId().equals(userEntity.getId()) == false) {
			return "此账号已经被使用！请重新填写！";
		}
		Person person = personService.getByJobNumber(userEntity.getAccount());
		if (user != null && person != null&& person.getPersonId().equals(user.getExtendId()) == false) {
			return "此账号已经被使用！请重新填写！";
		}
		
//		if(user == null && userEntity.getId() == null && person!=null ){
//			return "此账号已经被使用！请重新填写！";
//		}
		
		if(user == null  && person!=null ){
			return "此账号已经被使用！请重新填写！";
		}
		
		if(user == null && userEntity.getId() == null){
			UserCategory userCategory = userCategoryService.getByCode(userCategoryDefault);
			userEntity.setCategoryName(userCategory.getName());
			userEntity.setCategoryCode(userCategory.getCode());
			userEntity.setCategoryId(userCategory.getId());
			RegistrationAuthority raList = raService.getRaById(userEntity.getRaId());
			userEntity.setRaName(raList.getName());
			userEntity.setRaCode(raList.getCode());
			PasswordEncodingUtil passwordEncodingUtil = new PasswordEncodingUtil(PasswordEncodingUtil.ENCODING_ALGORITHM_MD5);
			//userEntity.setPassword(passwordEncodingUtil.encode(userEntity.getPassword()));
			userEntity.setPassword(passwordEncodingUtil.encode(defaultPassword));
			userEntity.setNeverExpired(true);
			User duser = securityService.getCurrentUser(request);
			userEntity.setCreatePerson(duser.getName());
			userEntity.setCreateTime(new Date());
			userEntity.setLoginName(userEntity.getAccount().trim());
			userEntity.setAccount(userEntity.getAccount().trim());
			
			userSerivce.saveUser(userEntity);
			return SUCCESS;
		}
		
		User userOld = userSerivce.getUserById(userEntity.getId());
		userOld.setCode(userEntity.getCode());
		userOld.setDecription(userEntity.getDecription());
		userOld.setEmail(userEntity.getEmail());
		userOld.setAccount(userEntity.getAccount().trim());
		userOld.setName(userEntity.getName());
		RegistrationAuthority raList = raService.getRaById(userEntity.getRaId());
		userOld.setRaName(raList.getName());
		userOld.setRaCode(raList.getCode());
		userOld.setRaId(raList.getId());
		userSerivce.saveUser(userOld);

		return SUCCESS;

	}


	/**
	 * 保存用户
	 */
	@RequestMapping(value = "/addPerson")
	@ResponseBody
	public String addPerson(HttpServletRequest request, ModelMap model) {
		String personIds = request.getParameter("personIds");
		String[] personIdArray = personIds.split(",");
		for(String personId:personIdArray)
		{
			Person person = personService.getByPersonId(personId);

			User userNew = userSerivce.getLogin(person.getJobNumber());
			if (userNew != null ) {
				return "("+person.getName()+") 此账号已经被使用！请联系管理员重新填写！";
			}
			User user = userSerivce.getByExtendId(personId);
			if (null == user) {
				user = new User();
				UserCategory userCategory = userCategoryService.getByCode(userCategoryDefault);
				user.setCategoryName(userCategory.getName());
				user.setCategoryCode(userCategory.getCode());
				user.setCategoryId(userCategory.getId());
				user.setActiveStatus(ActiveStatus.ACTIVE);
				user.setLoginName(person.getJobNumber());
				user.setName(person.getName());
				PasswordEncodingUtil passwordEncodingUtil = new PasswordEncodingUtil(PasswordEncodingUtil.ENCODING_ALGORITHM_MD5);
				//userEntity.setPassword(passwordEncodingUtil.encode(userEntity.getPassword()));
				user.setPassword(passwordEncodingUtil.encode(defaultPassword));
				user.setAccount(person.getJobNumber());
				user.setNeverExpired(true);
				user.setExtendId(person.getPersonId());
				RegistrationAuthority ra = raService.getByExtendId(person.getWorkUnitId());
				user.setRaCode(ra.getCode());
				user.setRaId(ra.getId());
				user.setRaName(ra.getName());
				user.setCreatePerson(getCurrentUser().getName());
				user.setCreateTime(new Date());
				userSerivce.saveUser(user);

				
				userSerivce.saveUser(user);
			}
		}
//		if(userEntity.getId().equals("")){
//			userEntity.setId(null);
//		}
//		User user = userSerivce.getLogin(userEntity.getAccount());
//		if (user != null && user.getId().equals(userEntity.getId()) == false) {
//			return "此账号已经被使用！请重新填写！";
//		}
//		if(user == null && userEntity.getId() == null){
//			UserCategory userCategory = userCategoryService.getByCode(userCategoryDefault);
//			userEntity.setCategoryName(userCategory.getName());
//			userEntity.setCategoryCode(userCategory.getCode());
//			userEntity.setCategoryId(userCategory.getId());
//			RegistrationAuthority raList = raService.getRaById(userEntity.getRaId());
//			userEntity.setRaName(raList.getName());
//			userEntity.setRaCode(raList.getCode());
//			PasswordEncodingUtil passwordEncodingUtil = new PasswordEncodingUtil(PasswordEncodingUtil.ENCODING_ALGORITHM_MD5);
//			//userEntity.setPassword(passwordEncodingUtil.encode(userEntity.getPassword()));
//			userEntity.setPassword(passwordEncodingUtil.encode(defaultPassword));
//			userEntity.setNeverExpired(true);
//			User duser = securityService.getCurrentUser(request);
//			userEntity.setCreatePerson(duser.getName());
//			userEntity.setCreateTime(new Date());
//			userEntity.setLoginName(userEntity.getAccount().trim());
//			userEntity.setAccount(userEntity.getAccount().trim());
//			
//			userSerivce.saveUser(userEntity);
//			return SUCCESS;
//		}
//		
//		User userOld = userSerivce.getUserById(userEntity.getId());
//		userOld.setCode(userEntity.getCode());
//		userOld.setDecription(userEntity.getDecription());
//		userOld.setEmail(userEntity.getEmail());
//		userOld.setAccount(userEntity.getAccount().trim());
//		userOld.setName(userEntity.getName());
//		userSerivce.saveUser(userOld);

		return SUCCESS;

	}

//	/**
//	 * 保存用户
//	 */
//	@RequestMapping(value = "/save")
//	@ResponseBody
//	public String save(HttpServletRequest request, User userEntity, ModelMap model) {
//		if(userEntity.getId().equals("")){
//			userEntity.setId(null);
//		}
//		User user = userSerivce.getLogin(userEntity.getLoginName());
//		if (user != null && user.getId().equals(userEntity.getId()) == false) {
//			return "此登录名已经被使用！请重新填写！";
//		}
//		if(user == null || userEntity.getId() == null){
//			DataItem dataItem = udmService.getDataItemByCode(userCategoryDefault);
//			userEntity.setCategoryName(dataItem.getName());
//			userEntity.setCategoryCode(dataItem.getCode());
//			RegistrationAuthority raList = raService.getRaById(userEntity.getRaId());
//			userEntity.setRaName(raList.getName());
//			userEntity.setRaCode(raList.getCode());
//			PasswordEncodingUtil passwordEncodingUtil = new PasswordEncodingUtil(PasswordEncodingUtil.ENCODING_ALGORITHM_MD5);
//			//userEntity.setPassword(passwordEncodingUtil.encode(userEntity.getPassword()));
//			userEntity.setPassword(passwordEncodingUtil.encode(defaultPassword));
//			userEntity.setNeverExpired(true);
//			User duser = securityService.getCurrentUser(request);
//			userEntity.setCreatePerson(duser.getName());
//			userEntity.setCreateTime(new Date());
//			
//			userSerivce.saveUser(userEntity);
//			return SUCCESS;
//		}
//		
//		User userOld = userSerivce.getUserById(userEntity.getId());
//		
//		User duser = securityService.getCurrentUser(request);
//		PasswordEncodingUtil passwordEncodingUtil = new PasswordEncodingUtil(PasswordEncodingUtil.ENCODING_ALGORITHM_MD5);
//		//user.setActiveStatus(userEntity.getActiveStatus());
//		userOld.setCode(userEntity.getCode());
//		userOld.setEmail(userEntity.getEmail());
//		userOld.setId(userEntity.getId());
//		userOld.setLoginName(userEntity.getLoginName());
//		userOld.setName(userEntity.getName());
//		userOld.setPassword(passwordEncodingUtil.encode(defaultPassword));
//		userOld.setRaCode(userEntity.getRaCode());
//		userOld.setRaId(userEntity.getRaId());
//		userOld.setRaName(userEntity.getRaName());
//		userOld.setCreatePerson(duser.getName());
//		
//		userSerivce.saveUser(userOld);
//
//		return SUCCESS;
//
//	}

	/**
	 * 用户删除
	 */

	@RequestMapping(value = "/del")
	public String del(String ids, ModelMap model) {
		List<String> userCategoryUnsuppressibleManuallyList = Arrays.asList(userCategoryUnsuppressibleManually.split(","));
		Set<String> userCategoryUnsuppressibleManuallySet = new HashSet<String>();
		userCategoryUnsuppressibleManuallySet.addAll(userCategoryUnsuppressibleManuallyList);
		
		try {
			if (StringUtils.isNotEmpty(ids)) {
				for (String id : ids.split(",")) {
//					User user = userSerivce.getUserById(id);
//					if(user.getCategoryCode() != null && userCategoryUnsuppressibleManuallySet.contains(user.getCategoryCode())){
//						model.put("status", -1);
//						this.setError(model, "删除失败,此账号不允许删除！");
//						return SUCCESS;
//					}
					userSerivce.deleteUserById(id);
				}
			}
		} catch (Exception e) {
			model.put("status", -1);
			this.setError(model, "删除失败,请联系管理员");
		}
		
		model.put("status", 1);
		
		return SUCCESS;
	}
	
	
	@RequestMapping(value = "/role/add")
	public String addRole(ModelMap model) {
		
		return "/user/role/newStandard";
	}
	

	@RequestMapping(value = "/role/query")
	@ResponseBody
	public Map<String, Object> queryRole(HttpServletRequest request, String name, String categoryName,String raName,ModelMap model) {
		User user = securityService.getCurrentUser(request);
		String raId = user.getRaId();
		PageConfig pageConfig = this.createPageConfig(request);
		Page<Role> roleList = roleSerivce.findAllRole(pageConfig, name,categoryName, raId,raName);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pager.pageNo", roleList.getPageNo());
		map.put("pager.totalRows", roleList.getTotal());
		map.put("rows", roleList.getRows());
		
		return map;
		
	}

	/**
	 * 保存角色
	 */
	@RequestMapping(value = "/role/save")
	@ResponseBody
	public void saveRole(String ids, String userId, ModelMap model) {
		
		userSerivce.saveRole(ids, userId);

		
	}
	
	@RequestMapping(value = "/roleUserRelation/query")
	@ResponseBody
	public Page<Role> queryRoleUserRelation(String userId, HttpServletRequest request, ModelMap model) {
		PageConfig pageConfig = this.createPageConfig(request);
		Page<Role> roleList = userSerivce.getRole(pageConfig, userId);
		
		return roleList;
	}
	
	/**
	 * 角色删除
	 */

	@RequestMapping(value = "/roleUserRelation/del")
	public String delRoleUserRelation(String roleId,String userId, ModelMap model) {
		if (StringUtils.isNotEmpty(roleId) == false) {
			model.put("status", -1);
			this.setError(model, "删除失败 , 没有选中角色");
		}
		
		try {
			userSerivce.delRoleUserRelation(roleId, userId);
		} catch (Exception e) {
			model.put("status", -1);
			this.setError(model, "删除失败,请联系管理员");
		}
		
		model.put("status", 1);
		
		return SUCCESS;
	}
	
	/**
	 * 验证
	 */
	@RequestMapping(value = "/queryUnique")
	public String queryUnique(User userEntity, ModelMap model) {
		if(userEntity.getId() == null){
			userEntity.setId("");
		}
		
		User user = userSerivce.getLogin(userEntity.getLoginName());
		if (user != null && user.getId().equals(userEntity.getId()) == false) {
			model.put("status", -1);
			this.setError(model, "此登录名已经被使用！请重新填写！");
			return SUCCESS;
		}
		
		
		model.put("status", 1);
		return SUCCESS;
		
	}
}
