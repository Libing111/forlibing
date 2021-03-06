package com.proper.uip.api.desktop.entity;



public class ApplicationAndGroupTreeNode {

	private int id;
	
	private int parentId;
	
	private String name;
	
	private boolean open;
	
	private boolean checked;
	
	private boolean chkDisabled;
	
	private String icon;
	
	private String moc;
	
	private String extendId;
	
	private String parentExtendId;

	public ApplicationAndGroupTreeNode(ApplicationAndGroup applicationAndGroup, boolean checked, boolean chkDisabled, int id, int parentId){
		this.id = id;
		this.open = true;
		this.parentId = parentId;
		this.name = applicationAndGroup.getName();
		this.checked = checked;
		this.chkDisabled = chkDisabled;
		this.moc = applicationAndGroup.getMoc();
		this.extendId = applicationAndGroup.getId();
		this.parentExtendId = applicationAndGroup.getParent();
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

	public String getMoc() {
		return moc;
	}

	public void setMoc(String moc) {
		this.moc = moc;
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


	
}
