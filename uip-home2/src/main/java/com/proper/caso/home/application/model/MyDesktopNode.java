package com.proper.caso.home.application.model;




public class MyDesktopNode {

	private String value;
	
	private String key;
	
	
	public MyDesktopNode(MenuNode menuNode){
		this.value = menuNode.getId();
		this.key = menuNode.getTitle();
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}

	
}
