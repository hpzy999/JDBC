#学生表 Student
create table Student(Sid varchar(6), Sname varchar(10), Sage datetime, Ssex varchar(10))comment '学生表';
insert into Student values('01' , '赵雷' , '1990-01-01' , '男');
insert into Student values('02' , '钱电' , '1990-12-21' , '男');
insert into Student values('03' , '孙风' , '1990-05-20' , '男');
insert into Student values('04' , '李云' , '1990-08-06' , '男');
insert into Student values('05' , '周梅' , '1991-12-01' , '女');
insert into Student values('06' , '吴兰' , '1992-03-01' , '女');
insert into Student values('07' , '郑竹' , '1989-07-01' , '女');
insert into Student values('08' , '王菊' , '1990-01-20' , '女');

    #成绩表 SC
create table SC(Sid varchar(10), Cid varchar(10), score decimal(18,1))comment '成绩表';
insert into SC values('01' , '01' , 80);
insert into SC values('01' , '02' , 90);
insert into SC values('01' , '03' , 99);
insert into SC values('02' , '01' , 70);
insert into SC values('02' , '02' , 60);
insert into SC values('02' , '03' , 80);
insert into SC values('03' , '01' , 80);
insert into SC values('03' , '02' , 80);
insert into SC values('03' , '03' , 80);
insert into SC values('04' , '01' , 50);
insert into SC values('04' , '02' , 30);
insert into SC values('04' , '03' , 20);
insert into SC values('05' , '01' , 76);
insert into SC values('05' , '02' , 87);
insert into SC values('06' , '01' , 31);
insert into SC values('06' , '03' , 34);
insert into SC values('07' , '02' , 89);
insert into SC values('07' , '03' , 98);
    #课程表 Course
create table Course(Cid varchar(10),Cname varchar(10),Tid varchar(10))comment '课程表';
insert into Course values('01' , '语文' , '02');
insert into Course values('02' , '数学' , '01');
insert into Course values('03' , '英语' , '03');

   # 教师表 Teacher
create table Teacher(Tid varchar(10),Tname varchar(10))comment '教师表';
insert into Teacher values('01' , '张三');
insert into Teacher values('02' , '李四');
insert into Teacher values('03' , '王五');

alter table sqlwork.course add foreign key (Tid) references sqlwork.teacher(Tid);
alter table sqlwork.sc add foreign key (Cid) references sqlwork.course(Cid);
alter table sqlwork.sc add foreign key (Sid) references sqlwork.student(sid);

select *from sqlwork.student;

#1.查询" 01 “课程比” 02 "课程成绩高的学生的信息及课程分数
select st.*,s1.score,s2.score from sqlwork.student st,sc s1,sc s2
where s1.Sid = st.Sid
and s2.Sid = st.Sid
and s1.Cid = 01
and s2.Cid = 02
and s1.score > s2.score;

#2.查询平均成绩大于等于 60 分的同学的学生编号和学生姓名和平均成绩
select st.Sid,st.Sname,avg(sc.score) avgscore from sqlwork.student st,sqlwork.sc sc
where st.Sid = sc.Sid
group by st.Sid
having avg(sc.score) >= 60;

#3.查询在 SC 表存在成绩的学生信息
select *from sqlwork.student st
where st.Sid in
(select distinct(sqlwork.sc.Sid) from sqlwork.sc);#子查询 sc中不重复的Sid

#4.查询所有同学的学生编号、学生姓名、选课总数、所有课程的总成绩(没成绩的显示为 null )
select st.Sid,st.Sname,count(sc.cid),sum(sc.score)
from sqlwork.student st,sqlwork.sc sc
where st.Sid = sc.Sid
group by st.Sid
having count(sc.Cid) and sum(sc.score);


#5.查询「李」姓老师的数量
select count(*) from sqlwork.teacher t
where t.Tname like '李%';

#6.查询学过「张三」老师授课的同学的信息
select st.*
from sqlwork.student st
left join sqlwork.sc sc on st.Sid = sc.Sid
join sqlwork.course co on co.Cid = sc.Cid
join sqlwork.teacher t on t.Tid = co.Tid
and t.Tname like '张三';


#7.查询没有学全所有课程的同学的信息
select st.* from sqlwork.student st,sc sc1,sc sc2,sc sc3
where st.Sid = sc1.Sid
and sc1.Sid = sc2.Sid
and sc2.Sid = sc3.Sid
and sc1.Cid = 01
and sc2.Cid = 02
and sc3.Cid = 03;


#8.查询至少有一门课与学号为" 01 "的同学所学相同的同学的信息
select distinct st.*
from sqlwork.student st
left join sqlwork.sc sc
on st.Sid = sc.Sid
where sc.Cid in(
    select sc2.Cid
    from sqlwork.student st2
    left join sqlwork.sc sc2
    on st2.Sid = sc2.Sid
    where sc2.Sid = '01'
);

#9.查询和" 01 "号的同学学习的课程完全相同的其他同学的信息
select distinct st1.*
from sqlwork.student st1,sqlwork.student st2,sqlwork.student st3
join sqlwork.sc sc1,sqlwork.sc sc2,sqlwork.sc sc3
where st1.Sid = sc1.Sid
and st2.Sid = sc2.Sid
and st3.Sid = sc3.Sid
;

#10.查询没学过"张三"老师讲授的任一门课程的学生姓名
select distinct st.Sname from sqlwork.student st
where st.Sid not in(
    select sc.Sid from sqlwork.sc
    join sqlwork.course co on sc.Cid = co.Cid
    join sqlwork.teacher t on co.Tid = t.Tid
    where t.Tname = '张三'
    );

#11.查询两门及其以上不及格课程的同学的学号，姓名及其平均成绩
select st.Sid,st.sname,avg(sc.score)
from sqlwork.student st
join sqlwork.sc sc
where st.Sid = sc.Sid
and sc.score < 60
group by st.Sid
having count(*) > 1;

#12.检索" 01 "课程分数小于 60，按分数降序排列的学生信息
select st.* from sqlwork.student st,sqlwork.sc sc
where st.Sid = sc.Sid
and sc.Cid = 01
and sc.score < 60
order by sc.score desc ;


#13.按平均成绩从高到低显示所有学生的所有课程的成绩以及平均成绩
SELECT st.*,
    GROUP_CONCAT(c.Cname) as '课程',
    GROUP_CONCAT(sc.score) as '分数',
    AVG(sc.score) as '平均分'

FROM sqlwork.student st
LEFT JOIN sqlwork.sc sc on st.Sid=sc.Sid
JOIN course c on sc.Cid=c.cid
GROUP BY sc.Sid ORDER BY AVG(sc.score) DESC;

#14.查询各科成绩前三名的记录
  -- 解法一：成绩无重复情况
select st.Sname,co.Cname,sc1.* from sqlwork.sc sc1,sqlwork.sc sc2,sqlwork.student st,sqlwork.course co
where st.Sid = sc1.Sid and sc1.Cid = sc2.Cid and co.Cid = sc1.Cid and sc1.score <= sc2.score
group by sc1.Sid,sc1.Cid,sc1.score
having count(sc2.Sid) < 4
order by sc1.Cid,sc1.score desc ;


   -- 解法二：成绩有重复情况
SELECT a.* FROM (
SELECT st.Sid,st.Sname,c.Cid,c.Cname,sc.score FROM sqlwork.student st
LEFT JOIN sqlwork.sc sc on sc.Sid = st.sid
INNER JOIN sqlwork.course c on c.cid=sc.Cid and c.cid='01'
ORDER BY sc.score DESC LIMIT 0,3) as a
UNION ALL
SELECT b.* FROM (
SELECT st.Sid,st.Sname,c.Cid,c.Cname,sc.score FROM student st
LEFT JOIN sqlwork.sc sc on sc.Sid=st.sid
INNER JOIN sqlwork.course c on c.Cid=sc.Cid and c.Cid='02'
ORDER BY sc.score DESC LIMIT 0,3) as b
UNION ALL
SELECT c.* FROM (
SELECT st.Sid,st.Sname,c.Cid,c.Cname,sc.score FROM sqlwork.student st
LEFT JOIN sqlwork.sc sc on sc.Sid=st.Sid
INNER JOIN sqlwork.course c on c.cid=sc.Cid and c.cid='03'
ORDER BY sc.score DESC LIMIT 0,4) as c;

#15.查询出只选修两门课程的学生学号和姓名
select st.Sid,st.Sname from sqlwork.student st
join sqlwork.sc sc on st.Sid = sc.Sid
group by st.Sid
having count(sc.Cid) < 3;

#16.查询名字中含有「风」字的学生信息
select st.* FROM sqlwork.student st
where st.Sname regexp '[*风]';

#17.成绩不重复，查询选修「张三」老师所授课程的学生中，成绩最高的学生信息及其成绩
   -- 解法一
select st.*,max(sc.score) '最高成绩' from sqlwork.student st
left join sqlwork.sc sc on st.Sid = sc.Sid
join sqlwork.course c on sc.Cid = c.Cid
join sqlwork.teacher th on c.Tid = th.Tid and th.Tname = '张三';

   -- 解法二
select st.*,sc.score '最高成绩' from sqlwork.student st,sqlwork.sc sc,sqlwork.course c,sqlwork.teacher th
where st.Sid = sc.Sid
and sc.Cid = c.Cid
and c.Tid = th.Tid
and th.Tname = '张三'
order by sc.score desc limit 1;

#18.成绩有重复的情况下，查询选修「张三」老师所授课程的学生中，成绩最高的学生信息及其成绩
select student.*,sc.score from course, sc, teacher,student
where
      teacher.Tid = course.Tid
  and teacher.Tname = '张三'
  and course.Cid = sc.Cid
  and student.Sid = sc.Sid
  and sc.score in (
    select
        max(score)
    from
        course,
        sc,
        teacher,
        student
    where
            teacher.Tid = course.Tid
      and teacher.Tname = '张三'
      and course.Cid = sc.Cid
      and student.Sid = sc.Sid
);

select st.Sname,t1.score from sqlwork.student st,(
select sc.Cid,sc.Sid,sc.score ,rank() over (order by score desc ) 排名
from sqlwork.teacher t,sqlwork.course c,sc
where t.Tname = '张三' and sc.Cid = c.Cid and t.Tid = c.Tid
) t1
where st.Sid = t1.Sid and t1.排名 = 1;



#19.查询" 01 "课程比" 02 "课程成绩高的学生的信息及课程分数


#20.查询同时存在" 01 "课程和" 02 "课程的情况
select a.*
from (select * from sqlwork.student st) a
         inner join
    (select * from sc where (sc.Cid = '01')) b
    on a.Sid = b.Sid
        inner join
     (select * from sc where (sc.Cid = '02')) c
     on b.Sid = c.Sid
group by a.Sid
;

#21.查询存在" 01 "课程但可能不存在" 02 "课程的情况(不存在时显示为 null )
select *
from  (select * from sc where sc.Cid = 01)a
          left join (select *from sc where sc.Cid = 02)b
                    on a.Sid = b.Sid ;

#22.查询不存在" 01 "课程但存在" 02 "课程的情况
  -- 解法一
select *
from (select *from sc where sc.Cid = 02)a
left join (select *from sc where sc.Cid = 01 )b
on a.Sid = b.Sid
where b.Sid is null;
-- 解法二
select *from sc where sc.Sid not in (select sc.Sid from sc where sc.Cid = 01);

#23.查询平均成绩大于等于 60 分的同学的学生编号和学生姓名和平均成绩
select st.Sid,st.Sname,avg(sc.score) from sqlwork.student st
right join sc on sc.Sid = st.Sid
group by st.Sid
having avg(sc.score) >= 60;

#24.查询在 SC 表存在成绩的学生信息
select st.* from sqlwork.student st where st.Sid in (select sc.sid from sc);

#25.查询所有同学的学生编号、学生姓名、选课总数、所有课程的总成绩(没成绩的显示为 null )
select st.Sid,st.Sname,s.cou,s.sum from sqlwork.student st
left join (select count(Cid) cou,sum(score) sum,sc.Sid,sc.Cid from sc group by Sid)s on s.Sid = st.Sid;

select st.*,B.sumsc
from student st
    left join (select sum(score)sumsc,Sid from sc group by Sid)B on st.Sid=B.Sid;

#26.查有成绩的学生信息
select st.* from sqlwork.student st
where Sid in(select sc.Sid from sc);

#27.查询「李」姓老师的数量
select count(th.Tid),th.Tname from teacher th
where Tname regexp '^[李]';

#28.查询学过「张三」老师授课的同学的信息
select st.* from sqlwork.student st
left join sc s on s.Sid = st.Sid
inner join sqlwork.course c on s.Cid = c.Cid
inner join sqlwork.teacher th on c.Tid = th.Tid
and th.Tname regexp '^[张][三]$';

#29.查询没有学全所有课程的同学的信息
select st.*,count(s.Cid)from sqlwork.student st,sc s
where st.Sid = s.Sid
group by st.Sid
having count(s.Cid) < 3;

#30.查询至少有一门课与学号为" 01 "的同学所学相同的同学的信息
select st.* from sqlwork.student st,sc s
where st.Sid = s.Sid and s.Cid in(select s2.Cid from sqlwork.student st2
    right join sc s2 on st2.Sid = s2.Sid and st2.Sid = 01)
group by st.Sid;

#31.查询和" 01 "号的同学学习的课程 完全相同的其他同学的信息


#32.查询没学过"张三"老师讲授的任一门课程的学生姓名
#33.查询两门及其以上不及格课程的同学的学号，姓名及其平均成绩
#34.检索" 01 "课程分数小于 60，按分数降序排列的学生信息
select st.*,s.score from sqlwork.student st,sc s
where st.Sid = s.Sid
and s.Cid = 01
and s.score < 60
order by s.score;

#35.按平均成绩从高到低显示所有学生的所有课程的成绩以及平均成绩
#36.查询各科成绩最高分、最低分和平均分：
select c.Cid,c.Cname, max(s.score),min(s.score),avg(s.score) from sc s
right join sqlwork.course c on s.Cid = c.Cid
group by s.Cid;


#37.以如下形式显示：课程 ID，课程 name，最高分，最低分，平均分，及格率，中等率，优良率，优秀率
   #及格为>=60，中等为：70-80，优良为：80-90，优秀为：>=90
select s.Cid,c.Cname,
       max(s.score) as '最高分',
       min(s.score) as '最低分',
       avg(s.score) as '平均分',
       (select count(s.Sid) from sc s where s.score >= 60 and s.Cid = c.Cid)/count(s.Sid) '及格率',
       (select count(s.Sid) from sc s where s.score between 70 and 80 and s.Cid = c.Cid)/count(s.Sid) '中等率',
       (select count(s.Sid) from sc s where s.score between 80 and 90 and s.Cid = c.Cid)/count(s.Sid) '优良率',
       (select count(s.Sid) from sc s where s.score >= 90 and s.Cid = c.Cid)/count(s.Sid) '优秀率'
from sc s,sqlwork.course c
where s.Cid = c.Cid
group by s.Cid;

#38.要求输出课程号和选修人数，查询结果按人数降序排列，若人数相同，按课程号升序排列
select s.Cid,count(s.Sid) from sc s,sqlwork.course c
where (select count(s.Sid) where s.Cid = c.Cid)
group by s.Cid
order by s.Cid;

#39.按各科成绩进行排序，并显示排名， Score 重复时保留名次空缺


#40.按各科成绩进行排序，并显示排名， Score 重复时合并名次
#41.查询学生的总成绩，并进行排名，总分重复时保留名次空缺
select st.Sname,sum(s.score)  from sqlwork.student st,sc s
where st.Sid = s.Sid
group by st.Sid
order by sum(s.score) desc;
select Sid Studentid,Cid Classid,score from sc where score < 60 group by Studentid;

#select Sid,Sname,Sage,Ssex from sqlwork.student  where Sid = 07;
#42.查询学生的总成绩，并进行排名，总分重复时不保留名次空缺
#43.统计各科成绩各分数段人数：课程编号，课程名称，[100-85]，[85-70]，[70-60]，[60-0] 及所占百分比
#44.查询各科成绩前三名的记录

#45.查询每门课程被选修的学生数
#46.查询出只选修两门课程的学生学号和姓名
select st.Sid,st.Sname from sqlwork.student st,sc
where st.Sid = sc.Sid group by st.Sid having count(sc.Sid) =2;

#47.查询男生、女生人数
#48.查询名字中含有「风」字的学生信息
#49.查询同名同性学生名单，并统计同名人数
#50.查询 1990 年出生的学生名单
#51.查询每门课程的平均成绩，结果按平均成绩降序排列，平均成绩相同时，按课程编号升序排列
#52.查询平均成绩大于等于 85 的所有学生的学号、姓名和平均成绩
#53.查询课程名称为「数学」，且分数低于 60 的学生姓名和分数
#54.查询所有学生的课程及分数情况（存在学生没成绩，没选课的情况）
#55.查询任何一门课程成绩在 70 分以上的姓名、课程名称和分数
#56.查询不及格的课程
#57.查询课程编号为 01 且课程成绩在 80 分以上的学生的学号和姓名
#58.求每门课程的学生人数
#59.成绩不重复，查询选修「张三」老师所授课程的学生中，成绩最高的学生信息及其成绩
#60.成绩有重复的情况下，查询选修「张三」老师所授课程的学生中，成绩最高的学生信息及其成绩
#61.查询不同课程成绩相同的学生的学生编号、课程编号、学生成绩
#62.查询每门功成绩最好的前两名
#63.统计每门课程的学生选修人数（超过 5 人的课程才统计）。
#64.检索至少选修两门课程的学生学号
#65.查询选修了全部课程的学生信息
#66.查询各学生的年龄，只按年份来算
#按照出生日期来算，当前月日 < 出生年月的月日则，年龄减一
select st.Sname,year(now())-year(st.Sage)
+ if(month(now()) - month(st.Sage) > 0,0,-1)+
       if(month(now())-month(st.Sage)=0,dayofmonth(now())-dayofmonth(st.Sage),0) 年龄
from sqlwork.student st

#67.查询本周过生日的学生
#68.查询下周过生日的学生
#69.查询本月过生日的学生
#70.查询下月过生日的学生