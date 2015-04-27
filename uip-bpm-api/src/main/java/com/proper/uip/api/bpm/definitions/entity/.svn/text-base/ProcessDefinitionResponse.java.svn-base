/* <p>文件名称: ProcessDefinitionResponse.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-7-25</p>
 * <p>完成日期：2013-7-25</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-7-25 下午2:35:23
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.api.bpm.definitions.entity;

import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;

public class ProcessDefinitionResponse {

	  private String id;
	  private String url;
	  private String key;
	  private int version;
	  private String name;
	  private String description;
	  private String deploymentId;
	  private String deploymentUrl;
	  private String resource;
	  private String diagramResource;
	  private String category;
	  private boolean graphicalNotationDefined = false;
	  private boolean suspended = false;
	  private boolean startFormDefined = false;
	  
	  
	  public ProcessDefinitionResponse(ProcessDefinitionEntity processDefinition){
		  this.id = processDefinition.getId();
		  
		  this.category = processDefinition.getCategory();
		  this.deploymentId = processDefinition.getDeploymentId();
		  //this.deploymentUrl = processDefinition.ge;
		  this.description = processDefinition.getDescription();
		  this.diagramResource = processDefinition.getDiagramResourceName();
		  this.graphicalNotationDefined = processDefinition.isGraphicalNotationDefined();
		  this.id = processDefinition.getId();
		  this.key = processDefinition.getKey();
		  this.name = processDefinition.getName();
		  this.resource = processDefinition.getResourceName();
		  //this.startFormDefined = processDefinition.s;
		  this.suspended = processDefinition.isSuspended();
		  //this.url = processDefinition.getu;
		  this.version = processDefinition.getVersion();
	  }
	  
	  public String getId() {
	    return id;
	  }
	  public void setId(String id) {
	    this.id = id;
	  }
	  public String getUrl() {
	    return url;
	  }
	  public void setUrl(String url) {
	    this.url = url;
	  }
	  public String getKey() {
	    return key;
	  }
	  public void setKey(String key) {
	    this.key = key;
	  }
	  public int getVersion() {
	    return version;
	  }
	  public void setVersion(int version) {
	    this.version = version;
	  }
	  public String getName() {
	    return name;
	  }
	  public void setName(String name) {
	    this.name = name;
	  }
	  public String getDeploymentId() {
	    return deploymentId;
	  }
	  public void setDeploymentId(String deploymentId) {
	    this.deploymentId = deploymentId;
	  }
	  public String getDeploymentUrl() {
	    return deploymentUrl;
	  }
	  public void setDeploymentUrl(String deploymentUrl) {
	    this.deploymentUrl = deploymentUrl;
	  }
	  public String getCategory() {
	    return category;
	  }
	  public void setCategory(String category) {
	    this.category = category;
	  }
	  public void setResource(String resource) {
	    this.resource = resource;
	  }
	  public String getResource() {
	    return resource;
	  }
	  public String getDescription() {
	    return description;
	  }
	  public void setDescription(String description) {
	    this.description = description;
	  }
	  public void setDiagramResource(String diagramResource) {
	    this.diagramResource = diagramResource;
	  }
	  public String getDiagramResource() {
	    return diagramResource;
	  }
	  public void setGraphicalNotationDefined(boolean graphicalNotationDefined) {
	    this.graphicalNotationDefined = graphicalNotationDefined;
	  }
	  public boolean isGraphicalNotationDefined() {
	    return graphicalNotationDefined;
	  }
	  public void setSuspended(boolean suspended) {
	    this.suspended = suspended;
	  }
	  public boolean isSuspended() {
	    return suspended;
	  }
	  public void setStartFormDefined(boolean startFormDefined) {
	    this.startFormDefined = startFormDefined;
	  }
	  public boolean isStartFormDefined() {
	    return startFormDefined;
	  }
	}
