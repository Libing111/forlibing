package com.proper.uip.api.bpm.extension;

import java.util.Set;

public abstract class BpmFilterRuleExtension implements Comparable<BpmFilterRuleExtension>  {

	private String id;

	private String name;
	
	private String code;

	public abstract Set<String> parse(Set<String> personIdSet,String initiatorId);
	
	@Override
	public int compareTo(BpmFilterRuleExtension bpmFilterRuleExtension) {
		 return this.getCode().compareTo(bpmFilterRuleExtension.getCode());
	}

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
}
