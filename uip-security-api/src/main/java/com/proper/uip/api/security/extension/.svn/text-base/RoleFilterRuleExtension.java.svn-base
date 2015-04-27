package com.proper.uip.api.security.extension;

import java.util.Set;

public abstract class RoleFilterRuleExtension implements Comparable<RoleFilterRuleExtension>  {

	private String id;

	private String name;
	
	private String code;
	/**
	 * 检查用户是否拥角色权限
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public abstract boolean checkUser(String userId,String roleId);
	/**
	 * 检查部门是否拥有角色权限
	 * @param departmentId
	 * @param roleId
	 * @return
	 */
	public abstract boolean checkDeparment(String departmentId,String roleId);
	
	@Override
	public int compareTo(RoleFilterRuleExtension roleFilterRuleExtension) {
		 return this.getCode().compareTo(roleFilterRuleExtension.getCode());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
