/* <p>文件名称: SecurityAccountBuilder.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-7-4</p>
 * <p>完成日期：2013-7-4</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-7-4 下午2:43:40
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.api.security.service;

import java.util.List;

public class SecurityAccountBuilder {
	
	private String userCategoryCode = null;
	
	private String userNamePrefix = null;
	
	private List<String> roleCodeList = null;
	
	
	public String getUserCategoryCode() {
		return userCategoryCode;
	}


	public void setUserCategoryCode(String userCategoryCode) {
		this.userCategoryCode = userCategoryCode;
	}


	public List<String> getRoleCodeList() {
		return roleCodeList;
	}


	public void setRoleCodeList(List<String> roleCodeList) {
		this.roleCodeList = roleCodeList;
	}


	public String getUserNamePrefix() {
		return userNamePrefix;
	}


	public void setUserNamePrefix(String userNamePrefix) {
		this.userNamePrefix = userNamePrefix;
	}
	
}
