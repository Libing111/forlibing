package com.proper.uip.extension.impl;

import java.util.HashSet;
import java.util.Set;

import com.proper.uip.api.bpm.extension.BpmAutoRuleExtension;

public class BpmAutoRuleInitiatorExtension extends BpmAutoRuleExtension {
	public Set<String> parse(String initiatorId){
		Set<String> candidateUsers = new HashSet<String>();
		candidateUsers.add(initiatorId);
		return candidateUsers;
	}

}
