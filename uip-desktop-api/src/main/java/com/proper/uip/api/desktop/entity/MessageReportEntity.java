
package com.proper.uip.api.desktop.entity;

import java.io.Serializable;

public class MessageReportEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 数量
	 */
	private int value;
	/**
	 * 分类的名称
	 */
	private String name;

	
	public MessageReportEntity() {
	}




	public int getValue() {
		return value;
	}




	public void setValue(int value) {
		this.value = value;
	}




	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	

}
