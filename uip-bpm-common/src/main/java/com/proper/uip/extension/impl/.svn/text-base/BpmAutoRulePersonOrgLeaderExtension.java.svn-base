package com.proper.uip.extension.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.proper.hr.personnel.entity.Person;
import com.proper.hr.personnel.entity.UserPositionInfo;
import com.proper.hr.personnel.service.IPersonQueryNoTransactionalService;
import com.proper.hr.personnel.service.IUserPositionNoTransactionalService;
import com.proper.uip.api.bpm.extension.BpmAutoRuleExtension;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.security.dao.UsersDao;

public class BpmAutoRulePersonOrgLeaderExtension extends BpmAutoRuleExtension {


    @Autowired
    private IUserPositionNoTransactionalService userPositionService;
    
    @Autowired
    private IPersonQueryNoTransactionalService personQueryService;
    
    @Autowired
    private UsersDao usersDao;

    
    @Override
    public Set<String> parse(String initiatorId) {
      //根据流程发起人的personId查询其职位信息
        List<UserPositionInfo> userPositionInfoList = userPositionService.getPersonUserPositionInfoByDates(getCurrentUser(), initiatorId, new Date(), new Date(), null);
        //用于存放流程发起人所在的机构Id
        Set<String> orgIdSet = new HashSet<String>();
        Set<String> candidateUsers = new HashSet<String>();
        List<Person> leaderList = new ArrayList<Person>();
        //根据流程发起人的职位信息查询出所有机构ID
        for(UserPositionInfo userPositionInfo:userPositionInfoList){
            orgIdSet.add(userPositionInfo.getOrgId());
            
            leaderList.addAll(new HashSet(personQueryService.getPersonOrgLeader(getCurrentUser(),initiatorId, userPositionInfo.getOrgId(), new Date(), new Date())));
//          Set set = new HashSet(personQueryService.getOrgLeader(getCurrentUser(), userPositionInfo.getOrgId(), new Date(), new Date())); 
//          candidateUsers.addAll(set);
        }
        for(Person leader :leaderList){
            candidateUsers.add(leader.getPersonId());
        }
        
//      candidateUsers.add(initiatorId);
        return candidateUsers;
    }

    
    protected User getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if(principal instanceof org.springframework.security.core.userdetails.User == false){
            return null;
        }
        
        org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) principal;   
        String userName = springUser.getUsername();   
        
        User user = usersDao.findUniqueBy("account", userName);
        
        return user;
    }
}
