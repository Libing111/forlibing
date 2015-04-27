package com.proper.uip.api.bpm.extension;

import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricTaskInstanceQuery;

import com.proper.uip.api.bpm.tasks.entity.HistoricTaskResponse;

public abstract class BpmTasksByCategoryExtension {
	/**
	 * 流程分类任务添加扩展字段
	 * 1、审批意见 HistoricTaskResponse.setReviewOpinion(reviewOpinion)
	 * 2、若有其它需要在待办现实的业务字段，根据约定放到variables（Map）中
	 * @param taskResponseList
	 * @return
	 */
	public abstract List<HistoricTaskResponse> extend(List<HistoricTaskResponse> taskResponseList);
	
	/**
	 * 查询条件过滤
	 * @param taskQuery
	 * @param conditionMap(key:字段名,value:查询的值)
	 * @return
	 */
	public HistoricTaskInstanceQuery filter(HistoricTaskInstanceQuery taskQuery, Map<String, String> conditionMap){
		return taskQuery;
	}
}
