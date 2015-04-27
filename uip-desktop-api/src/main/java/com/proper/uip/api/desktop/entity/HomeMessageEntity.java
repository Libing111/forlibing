
package com.proper.uip.api.desktop.entity;

import java.io.Serializable;

public class HomeMessageEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * id
	 */
	private String id;
	/**
	 * 弹出对话框标题
	 */
	private String title;
	/**
	 * 显示图标
	 */
	private String icon = "demo";
	/**
	 * 弹出页面是否为表单页面
	 */
	private String ptype = "default";
	/**
	 * 弹框的url
	 */
	private String url;
	
	/**
	 * 时间
	 */
	private String time;
	/**
	 * 显示数据分类code
	 */
	private String categoryCode;
	/**
	 * 显示数据分类name
	 */
	private String categoryName;
	/**
	 * 显示标题
	 */
	private String msgText;
	
	public HomeMessageEntity() {
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	



	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getMsgText() {
		return msgText;
	}

	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
	
	
}
