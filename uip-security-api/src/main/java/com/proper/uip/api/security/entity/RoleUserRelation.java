/* <p>文件名称: RoleUserRelation.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-5-10</p>
 * <p>完成日期：2013-5-10</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-5-10 下午1:22:27
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
@Table(name = "security_roles_users_c")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "com.proper.platform.security.entity.RoleUserRelation")
public class RoleUserRelation implements Serializable{
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
	 * 角色名称，唯一
	 */
	@Column(name = "role_name")
	private String roleName;
	
	/**
	 * 用户id，唯一
	 */
	@Column(name = "user_id", nullable = false)
	private String userId;
	
	/**
	 * 用户登录名称，唯一
	 */
	@Column(name = "login_name", nullable = false)
	private String loginName;
	
	/**
	 * 用户姓名
	 */
	@Column(name = "user_name")
	private String useName;
	
	/**
	 * 备注
	 */
	@Column(name = "description")
	private String description;

	public RoleUserRelation(){
		
	}
	public RoleUserRelation(User user, Role role){
		this.setId(null);
		this.setLoginName(user.getName());
		this.setRoleId(role.getId());
		this.setRoleName(role.getName());
		this.setUseName(user.getName());
		this.setUserId(user.getId());
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUseName() {
		return useName;
	}

	public void setUseName(String useName) {
		this.useName = useName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
