/* <p>文件名称: GroupNode.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-8-3</p>
 * <p>完成日期：2013-8-3</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-8-3 上午9:48:39
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.caso.home.application.model;

import java.util.List;

public class GroupNode {
	private String id;
	private String name;
	private List<ApplicationNode> children;
	
	public GroupNode(String id, String name, List<ApplicationNode> children) {
		super();
		this.id = id;
		this.name = name;
		this.children = children;
	}
	
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
	public List<ApplicationNode> getChildren() {
		return children;
	}
	public void setChildren(List<ApplicationNode> children) {
		this.children = children;
	}
	
}
