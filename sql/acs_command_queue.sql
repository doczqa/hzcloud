-- 命令任务
drop table if exists acs_command_queue;
create table acs_command_queue(
task_id int primary key auto_increment,
controller_id int comment '控制器ID',
door_id int comment '门ID',
command int comment '命令',
data longtext comment '暂存数据',
create_time datetime comment '创建时间',
issue_time datetime comment '下发时间',
elapsed_time int comment '耗时',
status char(1) comment '状态 0:已执行 1:未执行'
)engine=innodb auto_increment=1 comment = '命令任务';

-- 命令任务日志
drop table if exists acs_command_queue_log;
create table acs_command_queue_log(
id int primary key auto_increment,
task_id int comment '任务ID',
controller_id int comment '控制器ID',
door_id int comment '门ID',
command int comment '命令',
data longtext comment '暂存数据',
create_time datetime comment '创建时间',
issue_time datetime comment '下发时间',
status char(1) comment '状态 0:成功 1:失败',
description text comment '描述'
)engine=innodb auto_increment=1 comment = '命令任务';