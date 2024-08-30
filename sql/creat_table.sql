-- auto-generated definition
create table t_user
(
    id            bigint auto_increment comment 'id'
        primary key,
    user_name     varchar(255)                       null comment '用户名',
    user_account  varchar(255)                       null comment '账号',
    avatar_url    varchar(1024)                      null comment '头像',
    gender        tinyint                            null comment '性别',
    user_password varchar(255)                       null comment '密码',
    phone         varchar(255)                       null comment '电话',
    email         varchar(255)                       null comment '邮箱',
    planet_code   varchar(255)                       null comment '星球编号',
    user_status   int      default 0                 not null comment '用户状态 0 - 正常',
    user_role     int      default 0                 not null comment '用户权限 0 - 普通用户 1 - 管理员',
    create_time   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete     tinyint  default 0                 not null comment '是否删除'
);

alter table t_user add COLUMN tags varchar(1024) null comment '标签列表'