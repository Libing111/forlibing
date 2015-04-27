/* <p>文件名称: HomeMessageExtension.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-11-3</p>
 * <p>完成日期：2014-11-3</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-11-3上午11:41:04
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.api.desktop.extension;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.proper.uip.api.desktop.entity.HomeMessageEntity;
import com.proper.uip.api.desktop.entity.MessageReportEntity;
import com.proper.uip.api.security.entity.User;

/**
 * 首页消息扩展点
 * @author zhanghuafeng
 */
public abstract class HomeMessageExtension implements Comparable<HomeMessageExtension>{
	/**
	 * 功能模块资源码
	 */
	private String resourceCode;
	
	/**
	 * 功能类别
	 */
	private String messageCode;
	
	/**
	 * 功能名称
	 */
	private String title;
	
	/**
	 * 首页显示顺序
	 */
	private int sequenceNumber;
	
	/**
	 * 默认显示消息记录数
	 */
	private int defaultCount;
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public String getResourceCode(){
		return this.resourceCode;
	}
	
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public int getDefaultCount() {
		return defaultCount;
	}

	public void setDefaultCount(int defaultCount) {
		this.defaultCount = defaultCount;
	}

	/**
	 * 获取当前用户消息总数
	 * @param user(当前用户)
	 * @return
	 */
	public abstract int getTotalCount(User user);
	
	
	/**
	 * 根据指定时间段获取消息总数
	 * @param user
	 * @param beginTime（如果为null，就不指定开始时间）
	 * @param endTime（如果为null,就不指定结束时间）
	 * @return（如果都为空，就是所有数据）
	 */
	public abstract int getMessageCount(User user,Date beginTime, Date endTime);
	
	/**
	 * 根据指定时间段获取消息列表
	 * @param user
	 * @param beginTime（如果为null，就不指定开始时间）
	 * @param endTime（如果为null,就不指定结束时间）
	 * @return（如果都为空，就是所有数据）
	 */
	public abstract List<HomeMessageEntity> getMessageListByTime(User user,Date beginTime, Date endTime);
	
	/**
	 * 获取当前用户指定数量的消息列表
	 * @param user
	 * @param msgCount，消息数量，0或者-1 取所有
	 * @return
	 */
	public abstract List<HomeMessageEntity> getMessageList(HttpServletRequest request,User user, int msgCount);
	
	
	/**
	 *  获取当前用户信息分类统计
	 * @param request
	 * @param user
	 * @return
	 */
	public abstract List<MessageReportEntity> getMessageReportList(HttpServletRequest request,User user);
	
	@Override
    public int compareTo(HomeMessageExtension messageExtension) {
		if(this.sequenceNumber > messageExtension.sequenceNumber){
			return 1;
		}
		return -1;
    }

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	
	
}
