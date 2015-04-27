/* <p>文件名称: HelloMBean.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-7-30</p>
 * <p>完成日期：2013-7-30</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-7-30 上午9:38:05
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.bpm.mbean;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component("hello")
//@ManagedResource(objectName="bean:name=bpm-test", description="bpm-test")
public class BpmTest implements BpmTestMBean {
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private IdentityService identityService;

    private String processDefinitionKey = null;
    
    private String currentUserLoginName = "admin";
    
    private Map<String, Object> variables = new HashMap<String, Object>();

    public BpmTest(){
    	processDefinitionKey ="ProcessTest";
    }
   
    public BpmTest(String processDefinitionKey){
        this.processDefinitionKey = processDefinitionKey;
    }
   
    @ManagedAttribute
    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    @ManagedOperation
    public void startupProcess() {
    	ProcessInstance processInstance = null;
    	try {
    		//-currentUserLoginName：当前用户登录名，当前用户从安全服务获取
    		identityService.setAuthenticatedUserId(currentUserLoginName);
    		//processDefinitionKey：流程定义Key
    		//-variables：流程变量
    		processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
    	} catch(Exception ex){
    		ex.printStackTrace();
    	} finally {
    		  identityService.setAuthenticatedUserId(null);
    	}
    	
    	if(processInstance != null){
    		System.out.println("processInstanceId=" + processInstance.getProcessInstanceId());
    	}
    }

    @ManagedAttribute
    public void setProcessDefinitionKey(String processDefinitionKey) {
    	this.processDefinitionKey = processDefinitionKey;
    }

}
