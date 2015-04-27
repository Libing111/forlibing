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
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "security_ra_c")
public class RegistrationAuthority implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 注册机构Id
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;
	
	
	/**
	 * 注册机构名称
	 */
	private String name;
	
	/**
	 * 管理员账号
	 */
	private String adminAccount;
	
	/**
	 * 注册机构编号
	 */
	private String code;
	
	/**
	 * 注册机构版本
	 */
	private String version;

	/**
	 * 机构的分类
	 */
	private String categoryName;
	
	/**
	 * 机构分类的编号
	 */
	private String categoryCode;
	
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	private String categoryId;
	
	/**
	 * 永不过期
	 */
	@org.hibernate.annotations.Type(type="yes_no")
	private boolean  neverExpired;
	
	/**
	 * 到期的时间
	 * due data
	 */
	private Date dueDate;
	
	/**
	 * 备注
	 */
	private String description;
	
	/**
	 * 最后修改人
	 */
	private String lastChangePerson ;
	
	/**
	 * 修改时间
	 */
	private Date changeTime;
	
	/**
	 * 创建人
	 */
	private String createPerson;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 *扩展Id
	 */
	private String extendId;
	
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}


	

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	
	

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLastChangePerson() {
		return lastChangePerson;
	}

	public void setLastChangePerson(String lastChangePerson) {
		this.lastChangePerson = lastChangePerson;
	}

	
	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	

	public String getAdminAccount() {
		return adminAccount;
	}

	public void setAdminAccount(String adminAccount) {
		this.adminAccount = adminAccount;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isNeverExpired() {
		return neverExpired;
	}

	public void setNeverExpired(boolean neverExpired) {
		this.neverExpired = neverExpired;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getExtendId() {
		return extendId;
	}

	public void setExtendId(String extendId) {
		this.extendId = extendId;
	}

	
	
	
	
}
