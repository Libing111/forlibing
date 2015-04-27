prompt PL/SQL Developer import file
prompt Created on 2013Äê9ÔÂ5ÈÕ by ibm
set feedback off
set define off
prompt Dropping FILE_UPLOAD_C...
drop table FILE_UPLOAD_C cascade constraints;
prompt Dropping FILE_UPLOAD_FILE_C...
drop table FILE_UPLOAD_FILE_C cascade constraints;
prompt Creating FILE_UPLOAD_C...
create table FILE_UPLOAD_C
(
  ID                   VARCHAR2(255) not null,
  BEGIN_TIME           DATE,
  BUSINESS_INSTANCE_ID VARCHAR2(255),
  END_TIME             DATE,
  USER_ID              VARCHAR2(255),
  USER_LOGIN_NAME      VARCHAR2(255),
  USER_NAME            VARCHAR2(255)
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
alter table FILE_UPLOAD_C
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

prompt Creating FILE_UPLOAD_FILE_C...
create table FILE_UPLOAD_FILE_C
(
  ID                   VARCHAR2(255) not null,
  BUSINESS_INSTANCE_ID VARCHAR2(255),
  FILE_CREATE_DATE     DATE,
  FILE_DESCRIPTION     VARCHAR2(255),
  FILE_ID              VARCHAR2(255),
  FILE_KIND            VARCHAR2(255),
  FILE_MODULE          VARCHAR2(255),
  FILE_NAME            VARCHAR2(255),
  FILE_SIZE            NUMBER(10),
  FILE_TYPE            VARCHAR2(255),
  TARGET_FILE_NAME     VARCHAR2(255),
  UPLOAD_ID            VARCHAR2(255),
  USER_ID              VARCHAR2(255),
  USER_LOGIN_NAME      VARCHAR2(255),
  USER_NAME            VARCHAR2(255),
  VERSION              VARCHAR2(255)
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
alter table FILE_UPLOAD_FILE_C
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

prompt Disabling triggers for FILE_UPLOAD_C...
alter table FILE_UPLOAD_C disable all triggers;
prompt Disabling triggers for FILE_UPLOAD_FILE_C...
alter table FILE_UPLOAD_FILE_C disable all triggers;
prompt Loading FILE_UPLOAD_C...
prompt Table is empty
prompt Loading FILE_UPLOAD_FILE_C...
prompt Table is empty
prompt Enabling triggers for FILE_UPLOAD_C...
alter table FILE_UPLOAD_C enable all triggers;
prompt Enabling triggers for FILE_UPLOAD_FILE_C...
alter table FILE_UPLOAD_FILE_C enable all triggers;
set feedback on
set define on
prompt Done.
