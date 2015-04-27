/* <p>文件名称: UsersDao.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-5-9</p>
 * <p>完成日期：2013-5-9</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-5-9 下午5:07:40
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.security.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.proper.uip.api.security.entity.Role;
import com.proper.uip.api.security.entity.RoleUserRelation;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.entity.UsercateRole;
import com.proper.uip.api.security.service.UserRoleStrategy;
import com.proper.uip.common.core.dao.HibernateDao;

@Repository("userDao")
public class UsersDao extends HibernateDao<User, String> {
	
	@Autowired(required = false)
    private Map<String, UserRoleStrategy>  userRoleStrategyMap;

	@Autowired
	private RoleUserRelationDao ruDao;
	
	@Autowired
	private UsercateRoleDao usercateRoleDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public User save(User entity){
		boolean toCreate = false;
		//创建用户时，默认登录名和账号相同
		if(entity.getId() == null){
			String account = entity.getAccount() != null ? entity.getAccount() : entity.getLoginName();
	
			entity.setAccount(account);
			if(entity.getLoginName() == null){
				entity.setLoginName(account);
			}
			if(entity.getLoginName().trim().equals("") == true){
				entity.setLoginName(account);
			}
			
			toCreate = true;
		}
		
		User user = super.save(entity);
		
		//修改用户
		if(toCreate == false){
			return user;
		}
		
		//用户分类不存在
		String userCategoryCode = entity.getCategoryCode();
		if(userCategoryCode == null){
			return user;
		}
		if(userCategoryCode.trim().equals("") == true){
			return user;
		}
		
        //处理用户默认角色
		UserRoleStrategy strategy = getUserRoleStrategy(entity);
		if(strategy == null){
			return user;
		}
		
		Criteria criteria = this.getSession().createCriteria(Role.class);
		criteria.add(Restrictions.in("code",strategy.getRoleCodeList()));
		List<Role> roleList = criteria.list();
		if(roleList == null || roleList.isEmpty() == true){
			return user;
		}
		RoleUserRelation roleUserRelation = null;
		
		List<RoleUserRelation> roleUserRelationList = new ArrayList<RoleUserRelation>();
		
		for (Role role : roleList) {
			roleUserRelation = new RoleUserRelation(user, role);
			roleUserRelationList.add(roleUserRelation);
		}
		
		if(roleUserRelationList.isEmpty() == false){
			ruDao.save(roleUserRelationList);
		}
		
		return user;
	}

	private UserRoleStrategy getUserRoleStrategy(User entity) {
		if(userRoleStrategyMap != null){
			UserRoleStrategy strategy = userRoleStrategyMap.get(entity.getCategoryCode());
			return strategy;	
		}
		UserRoleStrategy strategy = new UserRoleStrategy();
		
		List<UsercateRole> usercateRoleList = usercateRoleDao.findBy("userCategoryCode", entity.getCategoryCode());
		if(usercateRoleList == null || usercateRoleList.isEmpty() == true ){
			return strategy;
		}
		
		List<String> roleCodeList = new ArrayList<String>();
		for (UsercateRole usercateRole : usercateRoleList) {
			roleCodeList.add(usercateRole.getRoleCode());
		}
		String userCategoryCode = entity.getCategoryCode();
		
		strategy.setRoleCodeList(roleCodeList);
		strategy.setUserCategoryCode(userCategoryCode);
		
		return strategy;
	}
}
