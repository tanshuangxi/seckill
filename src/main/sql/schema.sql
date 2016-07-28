
--创建数据库
create database seckill;

--使用数据库
use seckill;

--创建秒杀表
create table seckill(
`seckill_id` bigint not null AUTO_INCREMENT Comment '商品id',
`name` varchar(120) not null comment '商品名称',
`number` int not null comment '库存数量',
`create_time` timestamp not null default current_timestamp comment '创建时间',
`start_time` timestamp not null comment '秒杀开始时间',
`end_time` timestamp not null comment '秒杀结束时间',

primary key (seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

--初始化数据
insert into
    seckill (name, number, start_time, end_time)
values
    ('1000秒杀iphone6',  1000, '2015-10-12 23:23:00', '2015-10-13 23:10:10'),
    ('500秒杀iphone5',  300, '2015-10-12 23:23:00', '2015-10-13 23:10:10'),
    ('3000秒杀iphon4',  200, '2015-10-12 23:23:00', '2015-10-13 23:10:10'),
    ('100秒杀iphone4s',  100, '2015-10-12 23:23:00', '2015-10-13 23:10:10');

--秒杀明细表
create table success_killed(
`seckill_id` bigint not null comment '秒杀商品id',
`user_phone` bigint not null comment '用户手机号',
`state` tinyint not null default -1 comment '状态标示： -1: 无效 0: 成功 1: 已付款',
`create_time` timestamp not null comment '创建时间',
primary key(seckill_id, user_phone),
key idx_create_time(create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment='秒杀成功明细表';