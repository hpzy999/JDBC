create database Mybase01;
use Mybase01;


-- drop table if exists class;
create table class(
                      c_id int(5) primary key ,
                      c_name varchar(20) not null ,
                      c_num int(5) not null
);

drop table if exists student;
create table student (
                         s_id int(5) primary key AUTO_INCREMENT unique ,
                         s_name varchar(20) not null ,
                         s_classId int(5) not null,
                         constraint  key1 foreign key (s_classId) references class(c_id)
);

drop table student;
drop table class;