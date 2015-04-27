package com.proper.uip.security.role.entity;

import java.util.List;

import com.proper.uip.api.security.entity.Resource;

public class TreeNode {
	private String id;
	
	private String text;
	
	private String iconCls;
	
	private boolean checked;
	
	private List<TreeNode> children;
	
	private String resourceCode;
	
	private String roleId;
	
	public TreeNode(String roleId, Resource resource, boolean checked){
		this.id = resource.getId();
		this.text = resource.getName();
	//	this.iconCls = resource.getIcon();
		this.checked = checked;
		
		this.resourceCode = resource.getCode();
		this.roleId = roleId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
