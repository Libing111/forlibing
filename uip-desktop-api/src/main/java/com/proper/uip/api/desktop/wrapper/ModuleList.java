/* <p>文件名称: ModuleList.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-11-25</p>
 * <p>完成日期：2014-11-25</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-11-25下午4:19:42
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.api.desktop.wrapper;

import java.io.Serializable;
import java.util.List;

import com.proper.uip.api.desktop.entity.ApplicationAndGroup;

public class ModuleList implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<ApplicationAndGroup> moduleList;
	
	public ModuleList(){
	}
	
	public ModuleList(List<ApplicationAndGroup> moduleList){
		this.moduleList = moduleList;
	}

	public List<ApplicationAndGroup> getModuleList() {
		return moduleList;
	}

	public void setModuleList(List<ApplicationAndGroup> moduleList) {
		this.moduleList = moduleList;
	}
}

