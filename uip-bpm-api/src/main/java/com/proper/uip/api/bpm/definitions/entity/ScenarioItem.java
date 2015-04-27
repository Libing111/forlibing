/* <p>文件名称: ScenarioItem.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-10-31</p>
 * <p>完成日期：2013-10-31</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-10-31 下午2:00:32
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.api.bpm.definitions.entity;

import java.io.Serializable;

public class ScenarioItem implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String code;
	private String name;
	
	public ScenarioItem() {
	}
	
	public ScenarioItem(String id, String code, String name) {
		this.id = id;
		this.code = code;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public static ScenarioItem Default = new ScenarioItem(){
		private static final long serialVersionUID = 1L;

		{
			this.setCode("bpm.scenario.item.defaut");
			this.setId("bpm.scenario.item.defaut");
			this.setName("N.A.");
		}
	};
}
