package com.proper.uip.bpm.definitions.entity;

public class IdentityLinkEntity {
	
	public static final String PERSON = "person";
	
	public static final String ORG = "org";
	
	public static final String JOB = "job";
	
	public static final String POSITION = "position";

	private String personId;
	
	private String orgId;
	
	private String jobId;
	
	private String type;
	
	private String name;

	public IdentityLinkEntity(String personId,String orgId,String jobId,String type,String name) {
		// TODO Auto-generated constructor stub
		this.personId = personId;
		this.orgId = orgId;
		this.jobId = jobId;
		this.type = type;
		this.name = name;
	}
	
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
