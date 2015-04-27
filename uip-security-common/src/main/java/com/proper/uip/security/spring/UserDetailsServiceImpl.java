/* <p>文件名称: UserDetailsServiceImpl.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-5-9</p>
 * <p>完成日期：2013-5-9</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-5-9 下午5:06:02
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.proper.uip.api.security.entity.RoleResourceRelation;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.security.dao.RoleResourceRelationDao;
import com.proper.uip.security.dao.UsersDao;

@Service("userDetailsManager")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
public class UserDetailsServiceImpl implements UserDetailsService {  
	@Autowired(required=true)  
	private UsersDao usersDao;  
	@Autowired(required=true)
	private RoleResourceRelationDao roleResourceRelationDao;
	
	public UsersDao getUsersDao() {
		return usersDao;
	}

	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	public RoleResourceRelationDao getRoleResourceRelationDao() {
		return roleResourceRelationDao;
	}

	public void setRoleResourceRelationDao(
			RoleResourceRelationDao roleResourceRelationDao) {
		this.roleResourceRelationDao = roleResourceRelationDao;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {  
      System.out.println("username is " + username);  
      List<User> userList = this.usersDao.findBy("loginName", username);
      
      if(userList == null || userList.isEmpty()) {  
          throw new UsernameNotFoundException(username);  
      }
      
      User user = userList.get(0);
      
      Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(user);  
        
      boolean enables = true;  
      boolean accountNonExpired = true;  
      boolean credentialsNonExpired = true;  
      boolean accountNonLocked = true;  
        
      UserDetailsImpl userdetail = new UserDetailsImpl(grantedAuths, 
    		  user.getPassword(), user.getLoginName(), accountNonExpired, accountNonLocked, credentialsNonExpired, enables);  

      return userdetail;  
  }  
    
  //取得用户的权限  
  private Set<GrantedAuthority> obtionGrantedAuthorities(User user) {  
      Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();  
      
      String hql = "select rrr from RoleResourceRelation rrr, RoleUserRelation rur where rrr.roleId=rur.roleId and rur.userId=?";
      
      List<RoleResourceRelation> roleUserRelationList = roleResourceRelationDao.find(hql, user.getId());
      
      Set<String> resourceSet = new HashSet<String>(); 
      String resourceId = null;
      for(RoleResourceRelation roleResourceRelation : roleUserRelationList){
    	  resourceId = roleResourceRelation.getResourceId();
    	  if(resourceSet.contains(resourceId)){
    		  continue;
    	  }
    	  
    	 
    	  authSet.add(new SimpleGrantedAuthority(resourceId));  
    	  
    	  resourceSet.add(roleResourceRelation.getResourceId());
      }
       
      return authSet;  
  }  
}  