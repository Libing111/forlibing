package com.proper.uip.bpm.definitions.entity;

import com.proper.uip.api.bpm.definitions.entity.ProcessDefinitionsCategoryEntity;


public class DefinitionsCategoryNode {
	private int id;
	
	private int parentId;
	
	private String name;
	
	private boolean open;
	
	private boolean checked;
	
	private boolean chkDisabled;
	
	private String icon;
	
	private String extendId;
	
	private String parentExtendId;
	
	private String iconOpen = "/file/libs/icons/folderopen.gif";
	
	private String iconClose = "/file/libs/icons/folderclosed.gif";
	
	public DefinitionsCategoryNode(ProcessDefinitionsCategoryEntity processDefinitionsCategory, boolean checked, boolean chkDisabled, int id, int parentId){
		this.id = id;
		this.open = true;
		this.parentId = parentId;
		this.name = processDefinitionsCategory.getName();
		this.checked = checked;
		this.chkDisabled = chkDisabled;
		this.extendId = processDefinitionsCategory.getId();
		this.parentExtendId = processDefinitionsCategory.getParentId();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getExtendId() {
		return extendId;
	}

	public void setExtendId(String extendId) {
		this.extendId = extendId;
	}

	public String getParentExtendId() {
		return parentExtendId;
	}

	public void setParentExtendId(String parentExtendId) {
		this.parentExtendId = parentExtendId;
	}

	public String getIconOpen() {
		return iconOpen;
	}

	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}

	public String getIconClose() {
		return iconClose;
	}

	public void setIconClose(String iconClose) {
		this.iconClose = iconClose;
	}
	
}
