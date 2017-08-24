drop database if exists momosoft;
create database momosoft;
use momosoft;

-- 用户表
create table users (
  id bigint auto_increment, -- 用户主键自增
  username varchar(100),
  password varchar(100),
  password_salt varchar(100),
  constraint pk_users primary key(id)
) charset=utf8 ENGINE=InnoDB;
create unique index idx_users_username on users(username);

-- 角色表
create table user_roles(
  id bigint auto_increment, -- 角色主键自增
  user_id bigint, -- 对应用户主键
  role_name varchar(100),
  constraint pk_user_roles primary key(id)
) charset=utf8 ENGINE=InnoDB;
create unique index idx_user_roles on user_roles(user_id, id);

-- 角色对应的权限表
create table roles_permissions(
  id bigint auto_increment,
  role_id bigint,
  permission varchar(100),
  constraint pk_roles_permissions primary key(id)
) charset=utf8 ENGINE=InnoDB;
create unique index idx_roles_permissions on roles_permissions(role_id, permission);

insert into users(username,password)values('kang','123');
