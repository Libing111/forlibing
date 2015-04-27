package com.proper.caso.home.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.caso.home.service.AccountService;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.service.SecurityService;
import com.proper.uip.security.utils.PasswordEncodingUtil;
import com.proper.uip.web.BaseController;


@Controller
@RequestMapping(value = "/account")
public class AccountController extends BaseController{
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	@Qualifier("systemCategory")
	private String systemCategory;
	/**
	 * 账户管理
	 */
	@RequestMapping(value = "/index")
	public String add(HttpServletRequest request, ModelMap modelMap) {
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		
		User user = securityService.getCurrentUser(request);
		
		if(account != null){
			modelMap.put("account", account);
		}
		if(password != null){
			modelMap.put("password", password);
		}
		modelMap.put("user", user);
		
		modelMap.put("systemCategory", systemCategory);
		
		return "/account/index";
	}
	
	/**
	 * 密码管理
	 */
	@RequestMapping(value = "/password")
	public String passwordManagement(HttpServletRequest request, ModelMap model) {
		User user = securityService.getCurrentUser(request);
    	String oldPassword = request.getParameter("oldPassword");
    	String password = request.getParameter("password");
    	String confirmPassword = request.getParameter("confirmPassword");
    	if(oldPassword!= null){
    		model.put("oldPassword", oldPassword);
    	}
    	if(password!= null){
    		model.put("password", password);
    	}
    	if(confirmPassword!= null){
    		model.put("confirmPassword", confirmPassword);
    	}
		model.put("user", user);
		return "/account/pass";
	}
	/**
	 * 保存用户
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(HttpServletRequest request, User userEntity, ModelMap model) {
		User currentUser = this.securityService.getCurrentUser(request);
		
		User user = securityService.getAccount(userEntity.getAccount());
		if (user != null) {
			return "此账户已经被使用！请重新填写！";
		}
		
		PasswordEncodingUtil passwordEncodingUtil = new PasswordEncodingUtil(PasswordEncodingUtil.ENCODING_ALGORITHM_MD5);
		String ciphertext = passwordEncodingUtil.encode(userEntity.getPassword());
		if(ciphertext.equals(currentUser.getPassword()) == false){
			return "密码错误，请重新输入";
		}
		if(userEntity.getId() != null){
			accountService.save(currentUser,userEntity);
		}
		return SUCCESS;

	}
	/**
	 * 保存密码
	 */
	@RequestMapping(value = "/savePassWord")
	@ResponseBody
	public String savePassWord(HttpServletRequest request, User userEntity, ModelMap model) {
		String oldPassword = request.getParameter("oldPassword");
		User currentUser = this.securityService.getCurrentUser(request);
		PasswordEncodingUtil passwordEncodingUtil = new PasswordEncodingUtil(PasswordEncodingUtil.ENCODING_ALGORITHM_MD5);
		String ciphertext = passwordEncodingUtil.encode(oldPassword);
		if(ciphertext.equals(currentUser.getPassword()) == false){
			return "原密码错误，请重新输入";
		}
		if(userEntity.getId() != null){
			accountService.savePassWord(currentUser,userEntity);
		}
		return SUCCESS;

	}

	@RequestMapping(value = "/validate")
	@ResponseBody
	public String validate(HttpServletRequest request, String password, ModelMap model) {
		User currentUser = this.securityService.getCurrentUser(request);
		
		PasswordEncodingUtil passwordEncodingUtil = new PasswordEncodingUtil(PasswordEncodingUtil.ENCODING_ALGORITHM_MD5);
		String ciphertext = passwordEncodingUtil.encode(password);
		if(ciphertext.equals(currentUser.getPassword())){
			return SUCCESS;
		}
		return "ERROR";
	}

}
