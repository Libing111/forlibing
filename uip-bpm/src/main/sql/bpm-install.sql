prompt PL/SQL Developer import file
prompt Created on 2013Äê8ÔÂ13ÈÕ by ibm
set feedback off
set define off
prompt Dropping BPM_PRODEF_ASSIGN_C...
drop table BPM_PRODEF_ASSIGN_C cascade constraints;
prompt Dropping BPM_PRODEF_MONITOR_C...
drop table BPM_PRODEF_MONITOR_C cascade constraints;
prompt Dropping BPM_TASKDEF_C...
drop table BPM_TASKDEF_C cascade constraints;
prompt Creating BPM_PRODEF_ASSIGN_C...
create table BPM_PRODEF_ASSIGN_C
(
  ID                    VARCHAR2(255) not null,
  IDENTITY_LINK_ID      VARCHAR2(255),
  IDENTITY_LINK_NAME    VARCHAR2(255),
  PROCESS_DEFINITION_ID VARCHAR2(255),
  TASK_DEFINITION_KEY   VARCHAR2(255),
  TYPE_ID               VARCHAR2(255),
  TYPE_NAME             VARCHAR2(255)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table BPM_PRODEF_ASSIGN_C
  add primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt Creating BPM_PRODEF_MONITOR_C...
create table BPM_PRODEF_MONITOR_C
(
  ID                    VARCHAR2(255) not null,
  IDENTITY_LINK_ID      VARCHAR2(255),
  IDENTITY_LINK_NAME    VARCHAR2(255),
  PROCESS_DEFINITION_ID VARCHAR2(255),
  TYPE_ID               VARCHAR2(255),
  TYPE_NAME             VARCHAR2(255),
  IDENTITY_LINK_NAMES   VARCHAR2(255)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table BPM_PRODEF_MONITOR_C
  add primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt Creating BPM_TASKDEF_C...
create table BPM_TASKDEF_C
(
  ID                    VARCHAR2(255) not null,
  IDENTITY_LINK_NAMES   VARCHAR2(255),
  PROCESS_DEFINITION_ID VARCHAR2(255),
  SEQUENCE_NUMBER       NUMBER(10) not null,
  TASK_DEFINITION_KEY   VARCHAR2(255),
  TASK_DEFINITION_NAME  VARCHAR2(255)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table BPM_TASKDEF_C
  add primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt Disabling triggers for BPM_PRODEF_ASSIGN_C...
alter table BPM_PRODEF_ASSIGN_C disable all triggers;
prompt Disabling triggers for BPM_PRODEF_MONITOR_C...
alter table BPM_PRODEF_MONITOR_C disable all triggers;
prompt Disabling triggers for BPM_TASKDEF_C...
alter table BPM_TASKDEF_C disable all triggers;
prompt Loading BPM_PRODEF_ASSIGN_C...
prompt Table is empty
prompt Loading BPM_PRODEF_MONITOR_C...
prompt Table is empty
prompt Loading BPM_TASKDEF_C...
prompt Table is empty
prompt Enabling triggers for BPM_PRODEF_ASSIGN_C...
alter table BPM_PRODEF_ASSIGN_C enable all triggers;
prompt Enabling triggers for BPM_PRODEF_MONITOR_C...
alter table BPM_PRODEF_MONITOR_C enable all triggers;
prompt Enabling triggers for BPM_TASKDEF_C...
alter table BPM_TASKDEF_C enable all triggers;
set feedback on
set define on
prompt Done.
