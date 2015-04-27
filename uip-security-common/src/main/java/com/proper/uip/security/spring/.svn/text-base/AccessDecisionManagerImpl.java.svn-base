/* <p>文件名称: AccessDecisionManagerImpl.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-5-9</p>
 * <p>完成日期：2013-5-9</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-5-9 下午5:02:23
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.security.spring;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.FilterInvocation;

import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.security.dao.ResourcesDao;


public class AccessDecisionManagerImpl implements AccessDecisionManager {
	@Autowired
	private ResourcesDao resourcesDao;
	
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (authentication.getDetails() == null) {
			return;
		}
		
		FilterInvocation filterInvocation = (FilterInvocation) object;
		String url = filterInvocation.getRequestUrl();
		
		if(url.startsWith("/file/")){
			return;
		}
		
		Object principal = authentication.getPrincipal();
		
		
		if(principal instanceof User == false){
			// 没有权限
			throw new AccessDeniedException(" 没有权限访问！ ");
		}
		
		User user = (User) principal;
		if (true) {
			return;
		}
		
		//if(user.getUsername().equals("admin")){
		/**
		 * select re.* from security_resource_c re ,security_roles_resources_c rr, security_roles_users_c ru
			where re.id = rr.resource_id 
			and rr.role_id = ru.role_id
			and ru.login_name = '8df182cb-85cd-483e-9744-4b8e6fee2431'
			and re.url = '';
		 */
		StringBuffer hql = new StringBuffer("select re from Resource re ,RoleResourceRelation rr, RoleUserRelation ru");
		hql.append(" where")
		   .append(" re.id = rr.resourceId")
		   .append(" and")
		   .append(" rr.roleId = ru.roleId")
		   .append(" and")
		   .append(" ru.loginName = ?")
		   .append(" and")
		   .append(" re.url = ?");
		List<Resource> resourceList = resourcesDao.find(hql.toString(), user.getUsername(), url);
		if (resourceList == null || resourceList.isEmpty()) {
			// 没有权限
			throw new AccessDeniedException(" 没有权限访问！ ");
			
		}
			return;
//		// 所请求的资源拥有的权限(一个资源对多个权限)
//		Iterator<ConfigAttribute> iterator = configAttributes.iterator();
//		while (iterator.hasNext()) {
//			ConfigAttribute configAttribute = iterator.next();
//			// 访问所请求资源所需要的权限
//			String needPermission = configAttribute.getAttribute();
//			System.out.println("needPermission is " + needPermission);
//			// 用户所拥有的权限authentication
//			for (GrantedAuthority ga : authentication.getAuthorities()) {
//				if(needPermission == null){
//					continue;
//				}
//				if (needPermission.equals(ga.getAuthority())) {
//					return;
//				}
//			}
//		}
//		// 没有权限
//		throw new AccessDeniedException(" 没有权限访问！ ");
	}

	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

}
