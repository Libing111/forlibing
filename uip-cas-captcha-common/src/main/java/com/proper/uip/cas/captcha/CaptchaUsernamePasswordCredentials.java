/* <p>文件名称: CaptchaUsernamePasswordCredentials.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-1-13</p>
 * <p>完成日期：2014-1-13</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-1-13上午11:05:57
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.cas.captcha;

import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;

public class CaptchaUsernamePasswordCredentials extends UsernamePasswordCredentials {
	private static final long serialVersionUID = 1L;
	
	private String yzm;

	public String getYzm() {
		return yzm;
	}

	public void setYzm(String yzm) {
		this.yzm = yzm;
	}
	
	@Override 
	public boolean equals(final Object object) {
	    boolean result = super.equals(object);
	    if(result == false){
	    	return result;
	    }
	    
	    if(object instanceof CaptchaUsernamePasswordCredentials == false){
			return false;
		}
	    CaptchaUsernamePasswordCredentials credentials = (CaptchaUsernamePasswordCredentials) object;
	    if(yzm == null && credentials.yzm == null){
	    	return true;
	    }
	    
	    if(yzm == null && credentials.yzm != null){
	    	return false;
	    }
	    if(yzm != null && credentials.yzm == null){
	    	return false;
	    }
	    
	    return yzm.equals(credentials.yzm);
	}

}

