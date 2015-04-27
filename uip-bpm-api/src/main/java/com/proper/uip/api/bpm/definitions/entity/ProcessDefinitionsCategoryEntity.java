package com.proper.uip.api.bpm.definitions.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "bpm_prodef_c_category")
public class ProcessDefinitionsCategoryEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 流程定义分类id
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id; 
	/**
	 * 流程定义分类name
	 */
	private String name;
	/**
	 * 流程定义分类code
	 */
	@Column(nullable = false,unique = true)
	private String code;
	/**
	 * 流程定义分类备注
	 */
	private String description;
	/**
	 * 流程定义分类父亲id
	 */
	private String parentId;
	/**
	 * 流程定义分类父亲name
	 */
	private String parentName;
	/**
	 * 流程定义分类父亲name
	 */
	private String parentCode;
	/**
	 * 流程定义分类序号
	 */
	private int sequence;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	
}
