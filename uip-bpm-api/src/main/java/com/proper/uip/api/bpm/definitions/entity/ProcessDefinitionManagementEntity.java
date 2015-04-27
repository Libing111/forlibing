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


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.activiti.engine.repository.ProcessDefinition;
@Entity
@Table(name = "bpm_prodef_mana_c")
public class ProcessDefinitionManagementEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	  @Id
	  private String id;

	  private String key;
	  private int version;
	  private String name;
	  private String categoryId;
	  private String categoryName;
	  private String categoryCode;
	  private int categorySequence;
	  public ProcessDefinitionManagementEntity(ProcessDefinition processDefinition,ProcessDefinitionsCategoryEntity processDefinitionsCategory){
		  this.id = processDefinition.getId();
		  this.key = processDefinition.getKey();
		  this.name = processDefinition.getName();
		  this.version = processDefinition.getVersion();
		  this.categoryCode = processDefinitionsCategory.getCode();
		  this.categoryId = processDefinitionsCategory.getId();
		  this.categoryName = processDefinitionsCategory.getName();
		  this.categorySequence = processDefinitionsCategory.getSequence();
	  }
	  public ProcessDefinitionManagementEntity(){
		  
	  }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public int getCategorySequence() {
		return categorySequence;
	}
	public void setCategorySequence(int categorySequence) {
		this.categorySequence = categorySequence;
	}

}
