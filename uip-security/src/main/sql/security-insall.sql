prompt PL/SQL Developer import file
prompt Created on 2013年8月9日 by ibm
set feedback off
set define off
prompt Dropping SECURITY_RA_C...
drop table SECURITY_RA_C cascade constraints;
prompt Dropping SECURITY_RESOURCES_C...
drop table SECURITY_RESOURCES_C cascade constraints;
prompt Dropping SECURITY_RESOURCE_MOC_C...
drop table SECURITY_RESOURCE_MOC_C cascade constraints;
prompt Dropping SECURITY_RESSETS_C...
drop table SECURITY_RESSETS_C cascade constraints;
prompt Dropping SECURITY_RESSET_RES_C...
drop table SECURITY_RESSET_RES_C cascade constraints;
prompt Dropping SECURITY_ROLES_C...
drop table SECURITY_ROLES_C cascade constraints;
prompt Dropping SECURITY_ROLES_RESOURCES_C...
drop table SECURITY_ROLES_RESOURCES_C cascade constraints;
prompt Dropping SECURITY_ROLES_USERS_C...
drop table SECURITY_ROLES_USERS_C cascade constraints;
prompt Dropping SECURITY_USERS_C...
drop table SECURITY_USERS_C cascade constraints;
prompt Creating SECURITY_RA_C...
create table SECURITY_RA_C
(
  ID                 VARCHAR2(255) not null,
  DESCRIPTION        VARCHAR2(255),
  ADMIN_ACCOUNT      VARCHAR2(255),
  NAME               VARCHAR2(255),
  CATEGORY_CODE      VARCHAR2(255),
  CATEGORY_NAME      VARCHAR2(255),
  VERSION            VARCHAR2(255),
  NEVER_EXPIRED      CHAR(1) not null,
  DUE_DATE           DATE,
  LAST_CHANGE_PERSON VARCHAR2(255),
  CHANGE_TIME        DATE,
  CREATE_PERSON      VARCHAR2(255),
  CREATE_TIME        DATE,
  CODE               VARCHAR2(255),
  EXTEND_ID          VARCHAR2(255),
  CATEGORY_ID        VARCHAR2(255)
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

prompt Creating SECURITY_RESOURCES_C...
create table SECURITY_RESOURCES_C
(
  ID              VARCHAR2(255) not null,
  DESCRIPTION     VARCHAR2(255),
  MOC             VARCHAR2(255),
  NAME            VARCHAR2(255),
  PARENT          VARCHAR2(255),
  URL             VARCHAR2(255),
  CODE            VARCHAR2(255),
  SEQUENCE_NUMBER NUMBER(10),
  ICON            VARCHAR2(255),
  CATEGORY_CODE   VARCHAR2(255),
  CATEGORY_NAME   VARCHAR2(255),
  PARENT_ID       VARCHAR2(255),
  CATEGORY_ID     VARCHAR2(255)
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
alter table SECURITY_RESOURCES_C
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

prompt Creating SECURITY_RESOURCE_MOC_C...
create table SECURITY_RESOURCE_MOC_C
(
  ID          VARCHAR2(255) not null,
  DESCRIPTION VARCHAR2(255),
  NAME        VARCHAR2(255),
  PARENT      VARCHAR2(255),
  TYPE        VARCHAR2(255)
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
alter table SECURITY_RESOURCE_MOC_C
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

prompt Creating SECURITY_RESSETS_C...
create table SECURITY_RESSETS_C
(
  ID          VARCHAR2(255) not null,
  CODE        VARCHAR2(255),
  DESCRIPTION VARCHAR2(255),
  NAME        VARCHAR2(255)
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
alter table SECURITY_RESSETS_C
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

prompt Creating SECURITY_RESSET_RES_C...
create table SECURITY_RESSET_RES_C
(
  ID               VARCHAR2(255) not null,
  DESCRIPTION      VARCHAR2(255),
  RESOURCE_ID      VARCHAR2(255) not null,
  RESOURCE_NAME    VARCHAR2(255),
  RESOURCESET_ID   VARCHAR2(255) not null,
  RESOURCESET_NAME VARCHAR2(255)
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
alter table SECURITY_RESSET_RES_C
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

prompt Creating SECURITY_ROLES_C...
create table SECURITY_ROLES_C
(
  ID            VARCHAR2(255) not null,
  DECRIPTION    VARCHAR2(255),
  NAME          VARCHAR2(255),
  LOCKED_STATUS VARCHAR2(255) not null,
  CATEGORY_CODE VARCHAR2(255),
  CATEGORY_NAME VARCHAR2(255),
  CODE          VARCHAR2(255),
  RA_ID         VARCHAR2(255),
  RA_NAME       VARCHAR2(255),
  RA_CODE       VARCHAR2(255),
  CATEGORY_ID   VARCHAR2(255)
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
alter table SECURITY_ROLES_C
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

prompt Creating SECURITY_ROLES_RESOURCES_C...
create table SECURITY_ROLES_RESOURCES_C
(
  ID            VARCHAR2(255) not null,
  DESCRIPTION   VARCHAR2(255),
  RESOURCE_ID   VARCHAR2(255) not null,
  RESOURCE_NAME VARCHAR2(255),
  ROLE_ID       VARCHAR2(255) not null,
  ROLE_NAME     VARCHAR2(255)
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
alter table SECURITY_ROLES_RESOURCES_C
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

prompt Creating SECURITY_ROLES_USERS_C...
create table SECURITY_ROLES_USERS_C
(
  ID          VARCHAR2(255) not null,
  USER_ID     VARCHAR2(255) not null,
  LOGIN_NAME  VARCHAR2(255) not null,
  USER_NAME   VARCHAR2(255),
  ROLE_NAME   VARCHAR2(255),
  ROLE_ID     VARCHAR2(255) not null,
  DESCRIPTION VARCHAR2(255)
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
alter table SECURITY_ROLES_USERS_C
  add constraint ID primary key (ID)
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

prompt Creating SECURITY_USERS_C...
create table SECURITY_USERS_C
(
  ID                VARCHAR2(255) not null,
  ACTIVE_STATUS     VARCHAR2(255) default 'ACTIVE' not null,
  DECRIPTION        VARCHAR2(255),
  EMAIL             VARCHAR2(255),
  LOGIN_NAME        VARCHAR2(255) not null,
  NAME              VARCHAR2(255),
  PASSWORD          VARCHAR2(255) not null,
  LOCKED_STATUS     VARCHAR2(255) default 'UNLOCKED' not null,
  CATEGORY_CODE     VARCHAR2(255),
  CATEGORY_NAME     VARCHAR2(255),
  EXTEND_PROPERTIES VARCHAR2(255),
  DUE_DATE          DATE,
  RA_CODE           VARCHAR2(255),
  RA_ID             VARCHAR2(255),
  RA_NAME           VARCHAR2(255),
  CODE              VARCHAR2(255),
  EXTEND_ID         VARCHAR2(255),
  NEVER_EXPIRED     CHAR(1) default 1 not null,
  CATEGORY_ID       VARCHAR2(255)
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
alter table SECURITY_USERS_C
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
alter table SECURITY_USERS_C
  add constraint UK_E9878F85FC404D98A7B12ED0A1E unique (LOGIN_NAME)
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

prompt Disabling triggers for SECURITY_RA_C...
alter table SECURITY_RA_C disable all triggers;
prompt Disabling triggers for SECURITY_RESOURCES_C...
alter table SECURITY_RESOURCES_C disable all triggers;
prompt Disabling triggers for SECURITY_RESOURCE_MOC_C...
alter table SECURITY_RESOURCE_MOC_C disable all triggers;
prompt Disabling triggers for SECURITY_RESSETS_C...
alter table SECURITY_RESSETS_C disable all triggers;
prompt Disabling triggers for SECURITY_RESSET_RES_C...
alter table SECURITY_RESSET_RES_C disable all triggers;
prompt Disabling triggers for SECURITY_ROLES_C...
alter table SECURITY_ROLES_C disable all triggers;
prompt Disabling triggers for SECURITY_ROLES_RESOURCES_C...
alter table SECURITY_ROLES_RESOURCES_C disable all triggers;
prompt Disabling triggers for SECURITY_ROLES_USERS_C...
alter table SECURITY_ROLES_USERS_C disable all triggers;
prompt Disabling triggers for SECURITY_USERS_C...
alter table SECURITY_USERS_C disable all triggers;
prompt Loading SECURITY_RA_C...
prompt Table is empty
prompt Loading SECURITY_RESOURCES_C...
insert into SECURITY_RESOURCES_C (ID, DESCRIPTION, MOC, NAME, PARENT, URL, CODE, SEQUENCE_NUMBER, ICON, CATEGORY_CODE, CATEGORY_NAME, PARENT_ID, CATEGORY_ID)
values ('9df43ee7-82f1-41e4-b90b-bf9a9480f052', '资源集', 'module', '资源集', 'somp.security', 'http://test.proper.com:8082/security/resourceSet/index', 'somp.security.resourceSet', 610, 'http://test.proper.com:8082/file/platform/statics/images/menu-item-notify.png', null, null, null, null);
insert into SECURITY_RESOURCES_C (ID, DESCRIPTION, MOC, NAME, PARENT, URL, CODE, SEQUENCE_NUMBER, ICON, CATEGORY_CODE, CATEGORY_NAME, PARENT_ID, CATEGORY_ID)
values ('BBD7A5193ECC493DB202A7F0DECF386C', '社会组织政府管理平台', 'system', '社会组织政府管理平台', null, null, 'somp', 0, null, null, null, null, null);
insert into SECURITY_RESOURCES_C (ID, DESCRIPTION, MOC, NAME, PARENT, URL, CODE, SEQUENCE_NUMBER, ICON, CATEGORY_CODE, CATEGORY_NAME, PARENT_ID, CATEGORY_ID)
values ('D7432D3B296441279D8FE1E11A654EE2', '安全管理', 'subsystem', '安全管理', 'somp', 'http://test.proper.com:8082/security/index', 'somp.security', 6, 'http://test.proper.com:8082/file/platform/statics/images/menu-item-security.png', null, ',,,', 'BBD7A5193ECC493DB202A7F0DECF386C', null);
insert into SECURITY_RESOURCES_C (ID, DESCRIPTION, MOC, NAME, PARENT, URL, CODE, SEQUENCE_NUMBER, ICON, CATEGORY_CODE, CATEGORY_NAME, PARENT_ID, CATEGORY_ID)
values ('AABDD8D7CBE24838A4664D5F570477C9', '角色管理', 'module', '角色管理', 'somp.security', 'http://test.proper.com:8082/security/role/index', 'somp.security.role', 602, 'http://test.proper.com:8082/file/platform/statics/images/menu-item-notify.png', null, null, 'D7432D3B296441279D8FE1E11A654EE2', null);
insert into SECURITY_RESOURCES_C (ID, DESCRIPTION, MOC, NAME, PARENT, URL, CODE, SEQUENCE_NUMBER, ICON, CATEGORY_CODE, CATEGORY_NAME, PARENT_ID, CATEGORY_ID)
values ('GGBDD8D7CBE24838A4664D5F570477C9', '用户管理', 'module', '用户管理', 'somp.security', 'http://test.proper.com:8082/security/user/index', 'somp.security.user', 603, 'http://test.proper.com:8082/file/platform/statics/images/menu-item-notify.png', null, null, 'D7432D3B296441279D8FE1E11A654EE2', null);
insert into SECURITY_RESOURCES_C (ID, DESCRIPTION, MOC, NAME, PARENT, URL, CODE, SEQUENCE_NUMBER, ICON, CATEGORY_CODE, CATEGORY_NAME, PARENT_ID, CATEGORY_ID)
values ('FFBDD8D7CBE24838A4664D5F570477C9', '资源管理', 'module', '资源管理', 'somp.security', 'http://test.proper.com:8082/security/resource/index', 'somp.security.resource', 601, 'http://test.proper.com:8082/file/platform/statics/images/menu-item-notify.png', null, null, 'D7432D3B296441279D8FE1E11A654EE2', null);
insert into SECURITY_RESOURCES_C (ID, DESCRIPTION, MOC, NAME, PARENT, URL, CODE, SEQUENCE_NUMBER, ICON, CATEGORY_CODE, CATEGORY_NAME, PARENT_ID, CATEGORY_ID)
values ('398c0748-2e0e-4935-bd2a-86bc216f93e4', '注册机构管理', 'module', '注册机构管理', 'somp.security', 'http://test.proper.com:8082/security/registrationAuthority/index', 'somp.security.organization', 609, 'http://test.proper.com:8082/file/platform/statics/images/menu-item-security.png', null, null, null, null);
insert into security_resources_c (ID, DESCRIPTION, MOC, NAME, PARENT, URL, CODE, SEQUENCE_NUMBER, ICON, CATEGORY_CODE, CATEGORY_NAME, PARENT_ID, CATEGORY_ID)
values ('09514e5d-2d71-4fa4-97b8-12b07a8186ad', '分屏和栏目设置', 'module', '分屏栏目', 'somp.home', 'http://test.proper.com:8082/home/group/index', 'somp.home.application.configuration', 101, 'http://test.proper.com:8082/file/platform/statics/images/menu-item-notify.png', '', '', '', '');
insert into security_resources_c (ID, DESCRIPTION, MOC, NAME, PARENT, URL, CODE, SEQUENCE_NUMBER, ICON, CATEGORY_CODE, CATEGORY_NAME, PARENT_ID, CATEGORY_ID)
values ('7E0960C6B1CC44959DCFB5E36D63F3E1', '首页', 'subsystem', '首页', 'somp', 'http://test.proper.com:8082/home/index', 'somp.home', 1, 'http://test.proper.com:8082/file/platform/statics/images/menu-item-home.png', '', '', 'BBD7A5193ECC493DB202A7F0DECF386C', '');
commit;
prompt 11 records loaded
prompt Loading SECURITY_RESOURCE_MOC_C...
prompt Table is empty
prompt Loading SECURITY_RESSETS_C...
prompt Table is empty
prompt Loading SECURITY_RESSET_RES_C...
prompt Table is empty
prompt Loading SECURITY_ROLES_C...
insert into SECURITY_ROLES_C (ID, DECRIPTION, NAME, LOCKED_STATUS, CATEGORY_CODE, CATEGORY_NAME, CODE, RA_ID, RA_NAME, RA_CODE, CATEGORY_ID)
values ('79aaea92-7c8c-4b63-9fd7-03ed1adf26a5', null, 'admin_role', 'STOP', null, null, null, null, null, null, null);
commit;
prompt 1 records loaded
prompt Loading SECURITY_ROLES_RESOURCES_C...
prompt Loading SECURITY_ROLES_RESOURCES_C...
insert into SECURITY_ROLES_RESOURCES_C (ID, DESCRIPTION, RESOURCE_ID, RESOURCE_NAME, ROLE_ID, ROLE_NAME)
values ('7a913309-edf4-4f01-b325-db4c3fb0f204', '分屏和栏目设置', '09514e5d-2d71-4fa4-97b8-12b07a8186ad', '分屏栏目', '79aaea92-7c8c-4b63-9fd7-03ed1adf26a5', 'admin_role');
insert into SECURITY_ROLES_RESOURCES_C (ID, DESCRIPTION, RESOURCE_ID, RESOURCE_NAME, ROLE_ID, ROLE_NAME)
values ('67bfdf46-26cf-4cf6-8896-22869aedd711', '资源集', '9df43ee7-82f1-41e4-b90b-bf9a9480f052', '资源集', '79aaea92-7c8c-4b63-9fd7-03ed1adf26a5', 'admin_role');
insert into SECURITY_ROLES_RESOURCES_C (ID, DESCRIPTION, RESOURCE_ID, RESOURCE_NAME, ROLE_ID, ROLE_NAME)
values ('b11f6d49-f255-4c01-9fa0-ce9588abcfc3', '首页', '7E0960C6B1CC44959DCFB5E36D63F3E1', '首页', '79aaea92-7c8c-4b63-9fd7-03ed1adf26a5', 'admin_role');
insert into SECURITY_ROLES_RESOURCES_C (ID, DESCRIPTION, RESOURCE_ID, RESOURCE_NAME, ROLE_ID, ROLE_NAME)
values ('f633a7cb-589a-4cd5-8d67-044a87124586', '安全管理', 'D7432D3B296441279D8FE1E11A654EE2', '安全管理', '79aaea92-7c8c-4b63-9fd7-03ed1adf26a5', 'admin_role');
insert into SECURITY_ROLES_RESOURCES_C (ID, DESCRIPTION, RESOURCE_ID, RESOURCE_NAME, ROLE_ID, ROLE_NAME)
values ('35a13f88-c628-4698-8030-2f9e41a18547', '角色管理', 'AABDD8D7CBE24838A4664D5F570477C9', '角色管理', '79aaea92-7c8c-4b63-9fd7-03ed1adf26a5', 'admin_role');
insert into SECURITY_ROLES_RESOURCES_C (ID, DESCRIPTION, RESOURCE_ID, RESOURCE_NAME, ROLE_ID, ROLE_NAME)
values ('9acd796d-f37d-41b3-b9e7-f1b51eac2014', '用户管理', 'GGBDD8D7CBE24838A4664D5F570477C9', '用户管理', '79aaea92-7c8c-4b63-9fd7-03ed1adf26a5', 'admin_role');
insert into SECURITY_ROLES_RESOURCES_C (ID, DESCRIPTION, RESOURCE_ID, RESOURCE_NAME, ROLE_ID, ROLE_NAME)
values ('293e2845-6c92-457f-a2e0-b56d880269aa', '资源管理', 'FFBDD8D7CBE24838A4664D5F570477C9', '资源管理', '79aaea92-7c8c-4b63-9fd7-03ed1adf26a5', 'admin_role');
insert into SECURITY_ROLES_RESOURCES_C (ID, DESCRIPTION, RESOURCE_ID, RESOURCE_NAME, ROLE_ID, ROLE_NAME)
values ('f92a61d3-57d4-43d5-a4d7-6a6adb836f0e', '注册机构管理', '398c0748-2e0e-4935-bd2a-86bc216f93e4', '注册机构管理', '79aaea92-7c8c-4b63-9fd7-03ed1adf26a5', 'admin_role');
commit;
prompt 15 records loaded
prompt Loading SECURITY_ROLES_USERS_C...
insert into SECURITY_ROLES_USERS_C (ID, USER_ID, LOGIN_NAME, USER_NAME, ROLE_NAME, ROLE_ID, DESCRIPTION)
values ('c4e7cbef-6578-4f0e-801b-2f1ccf0544ab', 'dc65766c-0176-4a1e-ad0e-dd06ba645c7l', 'admin', 'admin', 'admin_role', '79aaea92-7c8c-4b63-9fd7-03ed1adf26a5', null);
prompt 1 records loaded
prompt Loading SECURITY_USERS_C...
insert into SECURITY_USERS_C (ID, ACTIVE_STATUS, DECRIPTION, EMAIL, LOGIN_NAME, NAME, PASSWORD, LOCKED_STATUS, CATEGORY_CODE, CATEGORY_NAME, EXTEND_PROPERTIES, DUE_DATE, RA_CODE, RA_ID, RA_NAME, CODE, EXTEND_ID, NEVER_EXPIRED, CATEGORY_ID)
values ('dc65766c-0176-4a1e-ad0e-dd06ba645c7l', 'INACTIVE', null, null, 'admin', 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'STOP', null, null, null, null, null, null, null, null, null, '1', null);
commit;
prompt 1 records loaded
prompt Enabling triggers for SECURITY_RA_C...
alter table SECURITY_RA_C enable all triggers;
prompt Enabling triggers for SECURITY_RESOURCES_C...
alter table SECURITY_RESOURCES_C enable all triggers;
prompt Enabling triggers for SECURITY_RESOURCE_MOC_C...
alter table SECURITY_RESOURCE_MOC_C enable all triggers;
prompt Enabling triggers for SECURITY_RESSETS_C...
alter table SECURITY_RESSETS_C enable all triggers;
prompt Enabling triggers for SECURITY_RESSET_RES_C...
alter table SECURITY_RESSET_RES_C enable all triggers;
prompt Enabling triggers for SECURITY_ROLES_C...
alter table SECURITY_ROLES_C enable all triggers;
prompt Enabling triggers for SECURITY_ROLES_RESOURCES_C...
alter table SECURITY_ROLES_RESOURCES_C enable all triggers;
prompt Enabling triggers for SECURITY_ROLES_USERS_C...
alter table SECURITY_ROLES_USERS_C enable all triggers;
prompt Enabling triggers for SECURITY_USERS_C...
alter table SECURITY_USERS_C enable all triggers;
set feedback on
set define on
prompt Done.
