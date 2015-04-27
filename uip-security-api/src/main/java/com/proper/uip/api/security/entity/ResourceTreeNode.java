package com.proper.uip.api.security.entity;

import com.proper.uip.api.security.entity.Resource;


public class ResourceTreeNode {

	private String id;
	
	private String parentId;
	
	private String name;
	
	private boolean open;
	
	private boolean checked;
	
	private boolean chkDisabled;
	
	private String icon;

	public ResourceTreeNode(Resource resource, Resource parentResouce, boolean checked, boolean chkDisabled){
		this.id = resource.getCode();
		
		this.open = true;
		this.parentId = "-1";
		if(parentResouce != null){
			this.parentId = parentResouce.getCode();
			this.open = false;
		}
		
		this.name = resource.getName();
		this.checked = checked;
		this.chkDisabled = chkDisabled;
	}

	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getParentId() {
		return parentId;
	}


	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}

	
}
