package com.proper.uip.api.security.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import net.sf.json.JSONObject;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "security_users_c")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "com.proper.platform.security.entity.User")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	/**
	 * 登录名，唯一
	 */
	@Column(name = "login_name", unique = true, nullable = false)
	private String loginName;
	
	/**
	 * 账号，唯一
	 */
	@Column(name = "account", unique = true, nullable = false)
	private String account;

	/**
	 * 密码
	 */
	@Column(name = "password", nullable = false)
	private String password;//密码

	/**
	 * 姓名
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 编号
	 */
	@Column(name = "code")
	private String code;

	/**
	 * 用户分类Id
	 */
	@Column(name = "category_code")
	private String categoryCode;

	/**
	 * 用户分类名称
	 */
	@Column(name = "category_name")
	private String categoryName;
	
	/**
	 * 用户分类名称
	 */
	@Column(name = "category_id")
	private String categoryId;

	/**
	 * 注册机构Id
	 */
	@Column(name = "ra_id")
	private String raId;

	/**
	 * 注册机构名称
	 */
	@Column(name = "ra_name")
	private String raName;

	/**
	 * 注册机构编号
	 */
	@Column(name = "ra_code")
	private String raCode;

	/**
	 * 备注
	 */
	@Column(name = "decription")
	private String decription;

	/**
	 * 启用状态
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "active_status", nullable = false)
	private ActiveStatus activeStatus = ActiveStatus.INACTIVE;

	/**
	 * 锁定状态
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "locked_status", nullable = false)
	private UseStatus useStatus = UseStatus.STOP;

	/**
	 * 扩展属性
	 */
	@Column(name = "extend_properties", nullable = true)
	private String extendPropertiesText;

	/**
	 * 永不过期
	 */
	@Column(nullable = false)
	@org.hibernate.annotations.Type(type = "yes_no")
	private boolean neverExpired;

	/**
	 * 到期的时间
	 * due data
	 */
	private Date dueDate;
	
	/**
	 * 创建人
	 */
	private String createPerson;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 *扩展属性
	 */
	private String extendId;

	public String getExtendProperty(String key) {
		JSONObject jsonObject = JSONObject.fromObject(extendPropertiesText);

		if (jsonObject.containsKey(key)) {
			return jsonObject.get(key).toString();
		}
		return null;
	}

	public void putExtendProperty(String key, String value) {
		if (this.extendPropertiesText == null) {
			this.extendPropertiesText = "{}";
		}
		JSONObject jsonObject = JSONObject.fromObject(extendPropertiesText);
		if(jsonObject.containsKey(key) == true){
			jsonObject.remove(key);
		}
		
		jsonObject.put(key, value);

		this.extendPropertiesText = jsonObject.toString();
	}

	public void putExtendProperty(Map<String, String> extendProperties) {
		String value = null;
		for(String key : extendProperties.keySet()){
			value = extendProperties.get(key);
			this.putExtendProperty(key, value);
		}
	}

	/**
	 * 邮箱，用于找回密码
	 */
	@Column(name = "email")
	private String email;

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

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getExtendId() {
		return extendId;
	}

	public void setExtendId(String extendId) {
		this.extendId = extendId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ActiveStatus getActiveStatus() {
		return activeStatus;
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

	public void setActiveStatus(ActiveStatus activeStatus) {
		this.activeStatus = activeStatus;
	}

	public UseStatus getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(UseStatus useStatus) {
		this.useStatus = useStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDecription() {
		return decription;
	}

	public void setDecription(String decription) {
		this.decription = decription;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}

	/**
	 * 账号激活状态
	 */
	public static enum ActiveStatus {
		/**
		 *  激活
		 */
		ACTIVE,
		/**
		 *  未激活
		 */
		INACTIVE
	}

	/**
	 * 账号使用状态
	 */
	public static enum UseStatus {
		/**
		 *  启用
		 */
		USE,
		/**
		 *  停用
		 */
		STOP

	}

	public String getExtendPropertiesText() {
		return extendPropertiesText;
	}

	public void setExtendPropertiesText(String extendPropertiesText) {
		this.extendPropertiesText = extendPropertiesText;
	}

	public String getRaId() {
		return raId;
	}

	public void setRaId(String raId) {
		this.raId = raId;
	}

	public String getRaName() {
		return raName;
	}

	public void setRaName(String raName) {
		this.raName = raName;
	}

	public String getRaCode() {
		return raCode;
	}

	public void setRaCode(String raCode) {
		this.raCode = raCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", loginName=" + loginName + ", name=" + name + ", raId=" + raId
				+ ", extendPropertiesText=" + extendPropertiesText + ", extendId=" + extendId + "]";
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

}
