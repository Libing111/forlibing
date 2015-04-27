/* <p>文件名称: YzmAction.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-1-13</p>
 * <p>完成日期：2014-1-13</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-1-13下午1:30:17
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.cas.captcha.servlet;

import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.execution.RequestContext;

import com.proper.uip.cas.captcha.CaptchaServiceSingleton;

public class YzmAction {
	public final String submit(RequestContext context) throws Exception {
		Boolean flag = Boolean.valueOf(false);
		System.out.println("YzmAction is submiting....................");
		String yzm = context.getRequestParameters().get("yzm");
		String captchaId = WebUtils.getHttpServletRequest(context).getSession()
				.getId();
		flag = CaptchaServiceSingleton.getInstance().validateResponseForID(
				captchaId, yzm);
		if (flag.booleanValue()) {
			return "success";
		}
		return "error";
	}

}
