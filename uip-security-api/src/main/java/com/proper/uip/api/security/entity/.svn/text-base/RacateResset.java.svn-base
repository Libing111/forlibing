package com.proper.uip.api.security.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "secureconf_racate_resset")
public class RacateResset implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;
	
	private String raCategoryId;
	
	private String raCategoryName;
	
	private String raCategoryCode;
	
	private String resourceSetId;
	
	private String resourceSetName;
	
	private String resourceSetCode;
	
	public RacateResset(){
		
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRaCategoryId() {
		return raCategoryId;
	}

	public void setRaCategoryId(String raCategoryId) {
		this.raCategoryId = raCategoryId;
	}

	public String getRaCategoryName() {
		return raCategoryName;
	}

	public void setRaCategoryName(String raCategoryName) {
		this.raCategoryName = raCategoryName;
	}

	public String getRaCategoryCode() {
		return raCategoryCode;
	}

	public void setRaCategoryCode(String raCategoryCode) {
		this.raCategoryCode = raCategoryCode;
	}

	public String getResourceSetId() {
		return resourceSetId;
	}

	public void setResourceSetId(String resourceSetId) {
		this.resourceSetId = resourceSetId;
	}

	public String getResourceSetName() {
		return resourceSetName;
	}

	public void setResourceSetName(String resourceSetName) {
		this.resourceSetName = resourceSetName;
	}

	public String getResourceSetCode() {
		return resourceSetCode;
	}

	public void setResourceSetCode(String resourceSetCode) {
		this.resourceSetCode = resourceSetCode;
	}
	
	
}
