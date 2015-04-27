/* <p>文件名称: Resource.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-5-9</p>
 * <p>完成日期：2013-5-9</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-5-9 上午10:06:42
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.api.desktop.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "home_app_and_group_c")
public class ApplicationAndGroup implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id; 
	
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 资源名称
	 */
	private String resourceName;
	
	/**
	 * 资源Id
	 */
	private String resourceId;
	
	/**
	 * 资源code
	 */
	private String resourceCode;
	/**
	 * 父
	 */
	private String parent;
	
	
	
	/**
	 * 类型 (屏，组，资源)
	 */
	private String moc;
	

	
	/**
	 * 显示顺序
	 */
	@Column(name = "sequence_number")
	private int sequenceNumber = 0;

	/**
	 * systemCategory 平台标示
	 */

	private String systemCategory;

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



	public String getParent() {
		return parent;
	}



	public void setParent(String parent) {
		this.parent = parent;
	}



	public String getMoc() {
		return moc;
	}



	public void setMoc(String moc) {
		this.moc = moc;
	}



	public int getSequenceNumber() {
		return sequenceNumber;
	}



	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}



	public String getSystemCategory() {
		return systemCategory;
	}



	public void setSystemCategory(String systemCategory) {
		this.systemCategory = systemCategory;
	}



	public String getResourceName() {
		return resourceName;
	}



	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}



	public String getResourceId() {
		return resourceId;
	}



	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}



	public String getResourceCode() {
		return resourceCode;
	}



	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}
	
	

}
