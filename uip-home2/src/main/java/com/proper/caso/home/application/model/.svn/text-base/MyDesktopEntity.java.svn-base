/* <p>文件名称: MyDesktopEntity.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-11-3</p>
 * <p>完成日期：2014-11-3</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-11-3上午10:09:20
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.caso.home.application.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "mydesktop_app_c")
public class MyDesktopEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id; 
	
	/**
	 * 用户id
	 */
	@Column(nullable = false)
	private String userId;
	
	/**
	 * 资源Id
	 */
	@Column(nullable = false)
	private String resourceId;
	
	/**
	 * 资源名称
	 */
	@Column(nullable = false)
	private String title;
	
	
	/**
	 * 资源url
	 */
	@Column(nullable = false)
	private String url;
	
	/**
	 * 图标
	 */
	@Column(nullable = false)
	private String icon;
	
	/**
	 * 是否可关闭
	 */
	@Column(nullable = false)
	@org.hibernate.annotations.Type(type = "yes_no")
	private boolean closeable;


	/**
	 * 显示顺序
	 */
	@Column(name = "sequence_number")
	private int sequenceNumber = 0;

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getResourceId() {
		return resourceId;
	}


	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
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


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public int getSequenceNumber() {
		return sequenceNumber;
	}


	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public boolean isCloseable() {
		return closeable;
	}


	public void setCloseable(boolean closeable) {
		this.closeable = closeable;
	}
}

