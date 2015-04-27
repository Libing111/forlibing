/* <p>文件名称: Role.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-5-9</p>
 * <p>完成日期：2013-5-9</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-5-9 上午9:36:21
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

import com.proper.uip.api.security.entity.User.UseStatus;

@Entity
@Table(name = "security_roles_c")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "com.proper.platform.security.entity.Role")
public class Role implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;
	
	/**
	 * 名称
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * code
	 */
	@Column(name = "code",nullable = false,unique = true)
	private String code;
	
	/**
	 * 角色分类Id
	 */
	@Column(name = "category_code")
	private String categoryCode;
	
	/**
	 * 角色分类名称
	 */
	@Column(name = "category_name")
	private String categoryName;
	
	@Column(name = "category_id")
	private String categoryId;
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

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
	 * 创建人
	 */
	private String createPerson;

	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 备注
	 */
	@Column(name = "decription")
	private String decription;
	
	/**
	 * 使用状态
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "locked_status", nullable = false)
	private UseStatus useStatus = UseStatus.STOP;
	
	/**
	 *扩展属性
	 */
	private String extendId;
	
	/**
	 * 扩展属性
	 */
	@Column(name = "extend_properties", nullable = true)
	private String extendPropertiesText;
	
	
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

	public String getDecription() {
		return decription;
	}

	public void setDecription(String decription) {
		this.decription = decription;
	}

	public UseStatus getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(UseStatus useStatus) {
		this.useStatus = useStatus;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getExtendId() {
		return extendId;
	}

	public void setExtendId(String extendId) {
		this.extendId = extendId;
	}

	public String getExtendPropertiesText() {
		return extendPropertiesText;
	}

	public void setExtendPropertiesText(String extendPropertiesText) {
		this.extendPropertiesText = extendPropertiesText;
	}
	
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

}
