/* <p>文件名称: RoleCategoryInactivationExtension.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-3-13</p>
 * <p>完成日期：2014-3-13</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-3-13上午9:36:06
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.api.security.extension;

/**
 * 角色分类停用处理扩展点
 * @author zhanghuafeng
 *
 */
public interface RoleCategoryInactivationExtension {
	/**
	 * 获取资源码
	 * @return
	 */
	String getResourceCode();
	
	/**
	 * 检查是否可以停用
	 * @param raCategoryCode
	 * @return
	 */
	boolean canInactive(String raCategoryCode);
}