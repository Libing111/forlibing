/* <p>文件名称: defaultBpmScenarioStrategy.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-10-31</p>
 * <p>完成日期：2013-10-31</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-10-31 下午2:09:08
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.proper.uip.api.bpm.definitions.entity.AssignmentEntity;
import com.proper.uip.api.bpm.definitions.entity.ScenarioItem;
import com.proper.uip.api.bpm.service.BpmScenarioStrategy;


@Service("com.proper.caso.bpm.service.impl.DefaultBpmScenarioStrategy")
public class DefaultBpmScenarioStrategy extends BpmScenarioStrategy {
	
	public DefaultBpmScenarioStrategy(){
		super.setName("默认");
		super.setId("com.proper.caso.bpm.service.impl.DefaultBpmScenarioStrategy");
	}

	@Override
	public boolean check(String initiator, AssignmentEntity entity) {
		return true;
	}

	@Override
	public List<ScenarioItem> getScenarioItems() {
		List<ScenarioItem> itemList = new ArrayList<ScenarioItem>();
		itemList.add(ScenarioItem.Default); 
		return itemList;
	}

}
