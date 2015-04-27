/* <p>文件名称: RoleResourceRelation.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-5-10</p>
 * <p>完成日期：2013-5-10</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-5-10 下午1:29:27
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
@Table(name = "security_roles_resources_c")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "com.proper.platform.security.entity.RoleUserRelation")
public class RoleResourceRelation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;
	
	/**
	 * 角色id，唯一
	 */
	@Column(name = "role_id", nullable = false)
	private String roleId;
	
	/**
	 * 角色名称
	 */
	@Column(name = "role_name")
	private String roleName;
	
	/**
	 * 资源Id，唯一
	 */
	@Column(name = "resource_id", nullable = false)
	private String resourceId;
	
	/**
	 * 资源名称，唯一
	 */
	@Column(name = "resource_name")
	private String resourceName;	/**
	
	 * 备注
	 */
	@Column(name = "description")
	private String description;
	
	public RoleResourceRelation(Resource resource, Role role){
		this.id = null;
		this.resourceId = resource.getId();
		this.resourceName = resource.getName();
		this.roleId = role.getId();
		this.roleName = role.getName();
		this.description = resource.getDescription();
		
	}
	public RoleResourceRelation(){
		
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
