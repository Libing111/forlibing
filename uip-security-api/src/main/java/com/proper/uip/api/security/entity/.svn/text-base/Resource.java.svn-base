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
package com.proper.uip.api.security.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "security_resources_c")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "com.proper.platform.security.entity.Resource")
public class Resource implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id; 
	
	/**
	 * 编号
	 */
	private String code;
	
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 简写名称
	 */
	private String simpleName;
	
	/**
	 * 父资源
	 */
	private String parent;
	
	/**
	 * 父资源Id
	 */
	private String parentId;
	
	/**
	 * 资源分类Id
	 */
	@Column(name = "category_code")
	private String categoryCode;
	
	/**
	 * 资源分类名称
	 */
	@Column(name = "category_name")
	private String categoryName;
	
	/**
	 * 资源分类名称
	 */
	@Column(name = "category_id")
	private String categoryId;
	
	/**
	 * 类型
	 */
	private String moc;
	
	/**
	 * 权限对应的Url地址
	 */
	private String url;
	
	/**
	 * 图标
	 */
	private String icon;
	
	/**
	 * 显示顺序
	 */
	@Column(name = "sequence_number",nullable = false,unique = true)
	private int sequenceNumber = 0;
	
	/**
	 * 备注
	 */
	private String description;
	
	/**
	 * 安全控制属性
	 */
	@org.hibernate.annotations.Type(type="yes_no")
	private boolean anonymously;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public boolean isAnonymously() {
		return anonymously;
	}

	public void setAnonymously(boolean anonymously) {
		this.anonymously = anonymously;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}




}
