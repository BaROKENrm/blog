drop database if exists servlet_blog;
create database servlet_blog character set utf8mb4;
use servlet_blog;
create table user(
    id int primary key auto_increment,
    username varchar(20) not null unique ,
    password varchar(20) not null,
    nickname varchar(20),
    sex bit,
    birthday date,
    head varchar(50)
);

create table article(
    id int primary key auto_increment,
    title varchar(20) not null,
    content mediumtext not null,
    create_time timestamp default now(),
    view_count int default 0,
    user_id int,
    foreign key (user_id) references user(id)
);

insert into user(username,password) values('abc','123');
insert into article( title, content, user_id) value ('排序','public ...',1);
insert into article( title, content, user_id) value ('数组','public ...',1);
insert into article( title, content, user_id) value ('接口','public ...',1);
select id, username, password, nickname, sex, birthday, head from user where username = ?;