prompt PL/SQL Developer import file
prompt Created on 2013年8月20日星期二 by Administrator
set feedback off
set define off
prompt Dropping HOME_APPLICATION_C...
drop table HOME_APPLICATION_C cascade constraints;
prompt Dropping HOME_GROUP_C...
drop table HOME_GROUP_C cascade constraints;
prompt Dropping HOME_PAGING_C...
drop table HOME_PAGING_C cascade constraints;
prompt Dropping HOME_PAGING_GROUP_C...
drop table HOME_PAGING_GROUP_C cascade constraints;
prompt Creating HOME_APPLICATION_C...
create table HOME_APPLICATION_C
(
  id                   VARCHAR2(255) not null,
  application_group_id VARCHAR2(255),
  icon                 VARCHAR2(255),
  name                 VARCHAR2(255),
  resouce_id           VARCHAR2(255),
  resouce_moc          VARCHAR2(255),
  sequence_number      NUMBER(10),
  url                  VARCHAR2(255),
  user_id              VARCHAR2(255),
  user_login_name      VARCHAR2(255),
  system_category      VARCHAR2(255)
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
alter table HOME_APPLICATION_C
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

prompt Creating HOME_GROUP_C...
create table HOME_GROUP_C
(
  id              VARCHAR2(255) not null,
  description     VARCHAR2(255),
  name            VARCHAR2(255),
  sequence_number NUMBER(10),
  user_id         VARCHAR2(255),
  user_login_name VARCHAR2(255),
  system_category VARCHAR2(255)
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
alter table HOME_GROUP_C
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

prompt Creating HOME_PAGING_C...
create table HOME_PAGING_C
(
  id              VARCHAR2(255) not null,
  description     VARCHAR2(255),
  name            VARCHAR2(255),
  sequence_number NUMBER(10),
  user_id         VARCHAR2(255),
  user_login_name VARCHAR2(255),
  system_category VARCHAR2(255)
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
alter table HOME_PAGING_C
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

prompt Creating HOME_PAGING_GROUP_C...
create table HOME_PAGING_GROUP_C
(
  id              VARCHAR2(255) not null,
  group_id        VARCHAR2(255),
  group_name      VARCHAR2(255),
  paging_id       VARCHAR2(255),
  paging_name     VARCHAR2(255),
  sequence_number NUMBER(10),
  user_id         VARCHAR2(255),
  user_login_name VARCHAR2(255),
  system_category VARCHAR2(255)
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
alter table HOME_PAGING_GROUP_C
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

prompt Disabling triggers for HOME_APPLICATION_C...
alter table HOME_APPLICATION_C disable all triggers;
prompt Disabling triggers for HOME_GROUP_C...
alter table HOME_GROUP_C disable all triggers;
prompt Disabling triggers for HOME_PAGING_C...
alter table HOME_PAGING_C disable all triggers;
prompt Disabling triggers for HOME_PAGING_GROUP_C...
alter table HOME_PAGING_GROUP_C disable all triggers;
prompt Loading HOME_APPLICATION_C...
prompt Table is empty
prompt Loading HOME_GROUP_C...
prompt Table is empty
prompt Loading HOME_PAGING_C...
prompt Table is empty
prompt Loading HOME_PAGING_GROUP_C...
prompt Table is empty
prompt Enabling triggers for HOME_APPLICATION_C...
alter table HOME_APPLICATION_C enable all triggers;
prompt Enabling triggers for HOME_GROUP_C...
alter table HOME_GROUP_C enable all triggers;
prompt Enabling triggers for HOME_PAGING_C...
alter table HOME_PAGING_C enable all triggers;
prompt Enabling triggers for HOME_PAGING_GROUP_C...
alter table HOME_PAGING_GROUP_C enable all triggers;
set feedback on
set define on
prompt Done.
