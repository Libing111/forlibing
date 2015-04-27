package com.proper.uip.bpm.definitions.entity;

import com.proper.uip.api.security.entity.RegistrationAuthority;
import com.proper.uip.api.security.entity.User;




public class UserTreeNode {
	public static String NODE_TYPE_RA = "node.type.ra";
	public static String NODE_TYPE_USER = "node.type.user";
	public static String NODE_TYPE_USER_CATEGORY = "node.type.user.category";
	
	private String id;
	
	private String parentId;
	
	private String name;
	
	//用户Id
	private String userId;
	
	private String extendId;
	
	//用户登录名
	private String loginName;
	
	//注册机构Id
	private String raId;
	
	private String open;
	
	
	private String drag = "true";

	private String icon;
	
	private String type;

	public UserTreeNode(RegistrationAuthority ra, String id){
		this.id = id;
		this.open = "true";
		this.parentId = "-1";
		
		
		this.name = ra.getName();
		this.raId = ra.getId();
		
		this.type = UserTreeNode.NODE_TYPE_RA;
		this.drag = "false";
	}
	
	public UserTreeNode(User user, String id, String parentId){
		this.id = id;
		
		this.open = "true";
		this.parentId = parentId;
		
		this.loginName = user.getLoginName();
		this.userId = user.getId();
		this.extendId = user.getExtendId();
		this.name = user.getName();
		
		this.type = UserTreeNode.NODE_TYPE_USER;
		this.drag = "true";
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getExtendId() {
		return extendId;
	}

	public void setExtendId(String extendId) {
		this.extendId = extendId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getRaId() {
		return raId;
	}

	public void setRaId(String raId) {
		this.raId = raId;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getDrag() {
		return drag;
	}

	public void setDrag(String drag) {
		this.drag = drag;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
}
