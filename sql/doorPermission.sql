-- 门禁组表
drop table if exists asc_door_group;
create table if not exists asc_door_group(
    group_id int primary key auto_increment comment '主键',
    group_name varchar(20) not null comment '门禁组名称',
    status            char(1)         default '0'                comment '状态（0正常 1停用）',
    del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
    create_by         varchar(64)     default ''                 comment '创建者',
    create_time 	    datetime                                   comment '创建时间',
    update_by         varchar(64)     default ''                 comment '更新者',
    update_time       datetime                                   comment '更新时间'
)engine=innodb auto_increment=1 comment = '门禁组表';

-- 门禁组和门关联表
drop table if exists asc_door_group_door;
create table if not exists asc_door_group_door(
    door_group_id int comment '门禁组ID,关联asc_device_door_group表group_id字段',
    door_id int comment '门ID,关联asc_device_door表door_id字段',
    primary key (door_group_id, door_id)
)engine=innodb auto_increment=1 comment = '门禁组和门关联表';


-- 用户组表
drop table if exists asc_user_group;
create table if not exists asc_user_group(
    group_id int primary key auto_increment comment '主键',
    group_name varchar(20) not null comment '用户组名称',
    status            char(1)         default '0'                comment '状态（0正常 1停用）',
    del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
    create_by         varchar(64)     default ''                 comment '创建者',
    create_time 	    datetime                                   comment '创建时间',
    update_by         varchar(64)     default ''                 comment '更新者',
    update_time       datetime                                   comment '更新时间'
)engine=innodb auto_increment=1 comment = '用户组表';

-- 用户组和用户关联表
drop table if exists asc_user_group_user;
create table if not exists asc_user_group_user(
    user_group_id int comment '用户组ID,关联asc_user_group表group_id字段',
    user_id int comment '用户ID,关联sys_user表user_id字段',
    primary key (user_group_id, user_id)
)engine=innodb auto_increment=1 comment = '用户组和用户关联表';

-- 门禁组和用户组关联表
drop table if exists asc_door_group_user_group;
create table if not exists asc_door_group_user_group(
    door_group_id int comment '门禁组ID,关联asc_device_door_group表group_id字段,',
    user_group_id int comment '用户组ID,关联asc_user_group表group_id字段',
    primary key (door_group_id, user_group_id)
)engine=innodb auto_increment=1 comment = '用户组和用户关联表';