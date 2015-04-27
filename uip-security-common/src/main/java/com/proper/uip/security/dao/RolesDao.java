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

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.proper.uip.api.security.entity.Role;
import com.proper.uip.common.core.dao.HibernateDao;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;

@Repository("roleDao")
public class RolesDao extends HibernateDao<Role, String> {
	public Page<Role> findRolePage(PageConfig config, String name, String categoryName, String raId, String raName) {
		Criteria criteria = createCriteria(name,categoryName, raId, raName);

		Page<Role> rolePage = new Page<Role>(config);

		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		rolePage.setTotal(totalCount);
		
		criteria = createCriteria(name,categoryName, raId, raName);


		int fromIndex = rolePage.getOffset();
		if(fromIndex < 0){
			System.out.println("Error! fromIndex = " + fromIndex);
			System.out.println("Error! Class=" + this.getClass().getName());
			System.out.println("Error! fromIndex = " + fromIndex);
			fromIndex = 0;
		}
		criteria.setFirstResult(fromIndex);
		criteria.setMaxResults(rolePage.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<Role> personList = criteria.list();
		rolePage.setRows(personList);
	
		return rolePage;
	}

	private Criteria createCriteria(String name,String categoryName, String raId,String raName) {
		Criteria criteria = this.getSession().createCriteria(Role.class);
		
		List<String> categoryCodeList = new ArrayList<String>();
		
		categoryCodeList.add("role.category.built-in");
		categoryCodeList.add("role.category.department.personnel");
		categoryCodeList.add("role.category.department");
		categoryCodeList.add("role.category.system.personnel");
		categoryCodeList.add("role.category.rule");
		String searchTextMode;
		if(name != null && name.trim().isEmpty() == false){
			searchTextMode = "%" + name + "%";
			criteria.add(Restrictions.like("name",searchTextMode));
		}
		if(categoryName != null && categoryName.trim().isEmpty() == false){
			searchTextMode = "%" + categoryName + "%";
			criteria.add(Restrictions.like("categoryName",searchTextMode));
		}
		if(raName != null && raName.trim().isEmpty() == false){
			searchTextMode = "%" + raName + "%";
			criteria.add(Restrictions.like("raName",searchTextMode));
		}
		if(raId != null && raId.trim().isEmpty() == false){
			criteria.add(Restrictions.eq("raId", raId));
		}
		criteria.add(Restrictions.not(Restrictions.in("categoryCode", categoryCodeList)));
		criteria.addOrder(Order.desc("createTime"));
		return criteria;
	}
	
	public Page<Role> findAllPage(PageConfig config, String name, String categoryName,String raName) {
		Criteria criteria = createCriteria(name,categoryName, raName);

		Page<Role> rolePage = new Page<Role>(config);

		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		rolePage.setTotal(totalCount);
		
		criteria = createCriteria(name,categoryName,raName);


		int fromIndex = rolePage.getOffset();
		if(fromIndex < 0){
			System.out.println("Error! fromIndex = " + fromIndex);
			System.out.println("Error! Class=" + this.getClass().getName());
			System.out.println("Error! fromIndex = " + fromIndex);
			fromIndex = 0;
		}
		criteria.setFirstResult(fromIndex);
		criteria.setMaxResults(rolePage.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<Role> personList = criteria.list();
		rolePage.setRows(personList);
	
		return rolePage;
	}

	private Criteria createCriteria(String name,String categoryName,String raName) {
		Criteria criteria = this.getSession().createCriteria(Role.class);
		List<String> categoryCodeList = new ArrayList<String>();
		
		categoryCodeList.add("role.category.built-in");
		categoryCodeList.add("role.category.department.personnel");
		categoryCodeList.add("role.category.department");
		categoryCodeList.add("role.category.system.personnel");
		
		String searchTextMode;
		if(name != null && name.trim().isEmpty() == false){
			searchTextMode = "%" + name + "%";
			criteria.add(Restrictions.like("name",searchTextMode));
		}
		if(categoryName != null && categoryName.trim().isEmpty() == false){
			searchTextMode = "%" + categoryName + "%";
			criteria.add(Restrictions.like("categoryName",searchTextMode));
		}
		if(raName != null && raName.trim().isEmpty() == false){
			searchTextMode = "%" + raName + "%";
			criteria.add(Restrictions.like("raName",searchTextMode));
		}
		
		criteria.add(Restrictions.not(Restrictions.in("categoryCode", categoryCodeList)));
		criteria.addOrder(Order.desc("createTime"));
		
		
		return criteria;
	}
}
