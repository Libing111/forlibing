prompt PL/SQL Developer import file
prompt Created on 2013年8月9日 by ibm
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
  ID                   VARCHAR2(255) not null,
  APPLICATION_GROUP_ID VARCHAR2(255),
  ICON                 VARCHAR2(255),
  NAME                 VARCHAR2(255),
  RESOUCE_ID           VARCHAR2(255),
  RESOUCE_MOC          VARCHAR2(255),
  SEQUENCE_NUMBER      NUMBER(10),
  URL                  VARCHAR2(255),
  USER_ID              VARCHAR2(255),
  USER_LOGIN_NAME      VARCHAR2(255)
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
  ID              VARCHAR2(255) not null,
  DESCRIPTION     VARCHAR2(255),
  NAME            VARCHAR2(255),
  SEQUENCE_NUMBER NUMBER(10),
  USER_ID         VARCHAR2(255),
  USER_LOGIN_NAME VARCHAR2(255)
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
  ID              VARCHAR2(255) not null,
  DESCRIPTION     VARCHAR2(255),
  NAME            VARCHAR2(255),
  SEQUENCE_NUMBER NUMBER(10),
  USER_ID         VARCHAR2(255),
  USER_LOGIN_NAME VARCHAR2(255)
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
  ID              VARCHAR2(255) not null,
  GROUP_ID        VARCHAR2(255),
  GROUP_NAME      VARCHAR2(255),
  PAGING_ID       VARCHAR2(255),
  PAGING_NAME     VARCHAR2(255),
  SEQUENCE_NUMBER NUMBER(10),
  USER_ID         VARCHAR2(255),
  USER_LOGIN_NAME VARCHAR2(255)
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
insert into home_application_c (ID, APPLICATION_GROUP_ID, ICON, NAME, RESOUCE_ID, RESOUCE_MOC, SEQUENCE_NUMBER, URL, USER_ID, USER_LOGIN_NAME)
values ('ea5e7bc7-5dea-4342-8945-1d52275775e9', '147b02f3-cddb-4d8f-8c0a-e4d4426b534f', 'http://test.proper.com:8082/file/platform/statics/images/menu-item-notify.png', '分屏栏目', '09514e5d-2d71-4fa4-97b8-12b07a8186ad', 'module', 101, 'http://test.proper.com:8082/home/group/index', 'system.default.user', 'system.default.user');
commit;
prompt Loading HOME_GROUP_C...
insert into home_group_c (ID, DESCRIPTION, NAME, SEQUENCE_NUMBER, USER_ID, USER_LOGIN_NAME)
values ('147b02f3-cddb-4d8f-8c0a-e4d4426b534f', '业务流程包含工作任务、流程设置等', '业务流程', 2, 'system.default.user', 'system.default.user');
commit;
prompt Loading HOME_PAGING_C...
insert into home_paging_c (ID, DESCRIPTION, NAME, SEQUENCE_NUMBER, USER_ID, USER_LOGIN_NAME)
values ('bfd36c6d-73c5-4096-9ff0-83c6499e6258', '包含系统管理、工作流和人员等', '第一屏', 1, 'system.default.user', 'system.default.user');
commit;
prompt Loading HOME_PAGING_GROUP_C...
insert into home_paging_group_c (ID, GROUP_ID, GROUP_NAME, PAGING_ID, PAGING_NAME, SEQUENCE_NUMBER, USER_ID, USER_LOGIN_NAME)
values ('92b7d72c-4edd-420b-9728-a82783b99ae3', '147b02f3-cddb-4d8f-8c0a-e4d4426b534f', '业务流程', 'bfd36c6d-73c5-4096-9ff0-83c6499e6258', '第一屏', 2, 'system.default.user', 'system.default.user');
commit;
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
