package com.proper.uip.api.security.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "secureconf_usercate_role_c")
public class UsercateRole implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;
	
	private String userCategoryId;
	private String userCategoryCode;
	
	private String userCategoryName;
	
	private String roleName;
	
	private String roleCode;
	
	private String roleId;

	
	public String getUserCategoryId() {
		return userCategoryId;
	}

	public void setUserCategoryId(String userCategoryId) {
		this.userCategoryId = userCategoryId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserCategoryCode() {
		return userCategoryCode;
	}

	public void setUserCategoryCode(String userCategoryCode) {
		this.userCategoryCode = userCategoryCode;
	}

	public String getUserCategoryName() {
		return userCategoryName;
	}

	public void setUserCategoryName(String userCategoryName) {
		this.userCategoryName = userCategoryName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}
