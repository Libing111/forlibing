/* <p>文件名称: RoleCategoryService.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-3-12</p>
 * <p>完成日期：2014-3-12</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-3-12下午3:22:51
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	lichunyan
 */
package com.proper.uip.api.security.service;

import java.util.List;

import com.proper.uip.api.security.entity.RoleCategory;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;


public interface RoleCategoryService {
	/**
	 * 分页获取所有角色分类
	 * @return
	 */
	Page<RoleCategory> findAll(PageConfig config, String name, String stop);
	/**
	 * 获取所有角色分类
	 * @return
	 */
	List<RoleCategory> getAllRoleCategorys();
	
	/**
	 * 保存角色分类
	 * @return
	 */
	void saveRoleCategory(RoleCategory roleCategory);
	
	/**
	 * 修改保存角色分类
	 * @return
	 */
	void updateSaveRoleCategory(RoleCategory roleCategory);
	/**
	 * 获取角色分类
	 * @param id
	 * @return
	 */
	RoleCategory getById(String id);
	/**
	 * 获取角色分类
	 * @param code
	 * @return
	 */
	RoleCategory getByCode(String code);
	/**
	 * 获取角色分类
	 * @param code
	 * @return
	 */
	RoleCategory getBySequenceNumber(int sequenceNumber);
	/**
	 * 获取角色分类
	 * @param code
	 * @return
	 */
	RoleCategory getByName(String name);
	/**
	 * 删除角色分类
	 * @param id
	 */
	void delete(String id);
	
}
