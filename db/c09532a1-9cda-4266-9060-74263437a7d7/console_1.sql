create database exam;
use exam;
insert into student value (1,'张三',18);
insert into student
values (2,'李四',19),
       (3,'王五',20);
select * from student;
show databases like 'ex%';
show databases like '%exa';
CREATE TABLE Member(
                       MemberID VARCHAR(10) PRIMARY KEY,
                       mname VARCHAR(4)  NOT NULL,
                       address VARCHAR(30) NOT NULL,
                       Tel VARCHAR(20) NOT NULL,
                       passwd VARCHAR(10) NOT NULL
);
CREATE TABLE Class(
                      ClassID VARCHAR(10) PRIMARY KEY,
                      Classname VARCHAR(10) NOT NULL
);
CREATE TABLE shopcar (
                         shoppingcar VARCHAR(10) PRIMARY KEY,
                         thingID VARCHAR(10) NOT NULL,
                         thingname VARCHAR(20) NOT NULL,
                         MemberID VARCHAR(10) NOT NULL,
                         Num VARCHAR(10) NOT NULL,
                         CONSTRAINT FOREIGN KEY (thingID) REFERENCES Thing(thingID),
                         CONSTRAINT FOREIGN KEY (thingname) REFERENCES Thing(thingname),
                         CONSTRAINT FOREIGN KEY (MemberID) REFERENCES Member(MemberID)
);
CREATE TABLE Thing (
                       thingID VARCHAR(10) PRIMARY KEY,
                       thingname VARCHAR(20) not NULL,
                       price VARCHAR(10) NOT NULL,
                       introduce VARCHAR(100) NOT NULL,
                       picture VARCHAR(50),
                       ClassID VARCHAR(10),
                       sales VARCHAR(10) NOT NULL,
                       CONSTRAINT FOREIGN KEY (ClassID) REFERENCES Class(ClassID)
);
CREATE TABLE shopcar (
                         shoppingcar VARCHAR(10) PRIMARY KEY,
                         thingID VARCHAR(10) NOT NULL,
                         thingname VARCHAR(20) NOT NULL,
                         MemberID VARCHAR(10) NOT NULL,
                         Num VARCHAR(10) NOT NULL,
                         CONSTRAINT FOREIGN KEY (thingID) REFERENCES Thing(thingID),
                         CONSTRAINT FOREIGN KEY (thingname) REFERENCES Thing(thingname),
                         CONSTRAINT FOREIGN KEY (MemberID) REFERENCES Member(MemberID)
);
CREATE INDEX thing_thingname ON thing(thingname);
CREATE TABLE shopcar (
                         shoppingcar VARCHAR(10) PRIMARY KEY,
                         thingID VARCHAR(10) NOT NULL,
                         thingname VARCHAR(20) NOT NULL,
                         MemberID VARCHAR(10) NOT NULL,
                         Num VARCHAR(10) NOT NULL,
                         CONSTRAINT FOREIGN KEY (thingID) REFERENCES Thing(thingID),
                         CONSTRAINT key2 FOREIGN KEY (thingname) REFERENCES Thing(thingname),
                         CONSTRAINT key3 FOREIGN KEY (MemberID) REFERENCES Member(MemberID)
);
CREATE TABLE Myorder(
                        orderID VARCHAR(10) PRIMARY KEY,
                        MemberID VARCHAR(10) not NULL,
                        ordertime datetime NOT NULL,
                        thingID VARCHAR(10) not NULL,
                        thingname VARCHAR(20) NOT NULL,
                        CONSTRAINT FOREIGN KEY (MemberID) REFERENCES Member(MemberID),
                        CONSTRAINT FOREIGN KEY (thingID) REFERENCES Thing(thingID),
                        CONSTRAINT FOREIGN KEY (thingname) REFERENCES Thing(thingname)
);
show index from thing;
INSERT INTO Member VALUES (520,'罗小华','贵州省贵阳市南明区花果园双子塔',17625832855,'lxh520');
INSERT INTO Member VALUES (521,'刘大虎','四川省成都市锦江区锦天府88号',15155559966,'ldh520');
INSERT INTO Member VALUES (522,'陈程城','黑龙江省大兴安岭地区漠河市花园小区',18813145200,'ccc520');
INSERT INTO class VALUES (110,'家居百货');
INSERT INTO class VALUES (111,'服装服饰');
INSERT INTO class VALUES (112,'美妆护肤');
INSERT INTO class VALUES (113,'电子数码');
INSERT INTO class VALUES (114,'食品生鲜');
INSERT INTO thing VALUES ('B1234','安踏跑步鞋',169,'夏季新款安踏跑步鞋复古潮流透气运动鞋','D/img/1.jpg',111,17);
INSERT INTO thing VALUES ('D4268','华为mate 40 pro',6799,'运行8+128G/8+256G,CPU麒麟9000,6.76英寸','D/img/2.jpg',113,125);
INSERT INTO thing VALUES ('C2234','丽丽贝尔化妆棉',14.3,'丽丽贝尔纯棉压边化妆棉省水湿敷','D/img/3.jpg',112,300);
INSERT INTO thing VALUES ('C1355','完美日记四合院眼影',118,'完美日记国家地理桃金四合院十六色眼影盘','D/img/4.jpg',112,74);
INSERT INTO thing VALUES ('E5289','三只松鼠爆款零食组合',25,'休闲小吃网红充饥夜宵零食整箱','D/img/5.jpg',114,289);
INSERT INTO thing VALUES ('A5489','洁丽雅洗脸巾',12.9,'洁丽雅一次性洗脸巾抽式婴儿家用加厚毛巾','D/img/6.jpg',110,856);
INSERT INTO thing VALUES ('B4581','香影碎花连衣裙',449,'白鹿明星同款香影粉色泡泡袖网纱碎花连衣裙','D/img/7.jpg',111,1256);
INSERT INTO thing VALUES ('D1578','佳能入门级单反相机',4710,'佳能EOS M200 M100学生入门级M6二代微单反相机','D/img/8.jpg',113,242);
INSERT INTO thing VALUES ('E1568','德芙巧克力礼盒装',58,'德芙巧克力七夕情人节生日礼物网红零食送女友','D/img/9.jpg',114,288);
INSERT INTO thing VALUES ('C6574','正品限量圣罗兰YSL迪奥口红套装',2560,'圣罗兰香奈儿纪梵希迪奥TF口红气垫套装','D/img/10.jpg',112,36);
INSERT INTO thing VALUES ('A2567','免手洗拖把',47,'拖把免手洗家用一拖净平板吸水','D/img/11.jpg',110,6247);
INSERT INTO thing VALUES ('B8924','YEP白色短袖女装',88,'YEP白色T恤女短袖中长款宽松纯棉上衣夏季新款','D/img/12.jpg',111,4512);
INSERT INTO thing VALUES ('D6544','JBL PULSE4音乐脉动4蓝牙音箱',1888,'JBL PULSE4音乐脉动4无线蓝牙音箱炫彩低音卓面音箱','D/img/13.jpg',113,11);
INSERT INTO thing VALUES ('E1106','蒙牛纯甄酸奶原味',32,'蒙牛纯甄酸奶200g*12盒常温风味早餐奶','D/img/14.jpg',114,14);
INSERT INTO thing VALUES ('A1999','索尼新品电视机75寸',6900,'索尼KD-75X80J 75寸4K HDR安卓智能液晶平板电视机','D/img/15.jpg',110,45);
INSERT INTO myorder VALUES ('MD145',520,'2021-05-20 14:45:16','A2567','免手洗拖把');
INSERT INTO myorder VALUES ('MD335',521,'2021-04-25 11:23:14','B1234','安踏跑步鞋');
INSERT INTO myorder VALUES ('MD826',522,'2021-06-11 19:24:52','D6544','JBL PULSE4音乐脉动4蓝牙音箱');
INSERT INTO shopcar VALUES ('SC10','E5289','三只松鼠爆款零食组合',520,2);
INSERT INTO shopcar VALUES ('SC11','B4581','香影碎花连衣裙',521,1);
INSERT INTO shopcar VALUES ('SC12','C1355','完美日记四合院眼影',522,3);
CREATE INDEX sy1 ON Member (MemberID);
CREATE INDEX sy2 ON Class (ClassID);
CREATE INDEX sy3 ON Thing (thingID(10));
CREATE INDEX sy4 ON shopcar (shoppingcar);
CREATE INDEX sy5 ON Myorder (orderID(10));
SHOW INDEX FROM Member;
CREATE PROCEDURE xiugaiTel(in m_name VARCHAR(5),in m_tel VARCHAR(21))
    READS SQL DATA
BEGIN
    UPDATE Member
    SET Tel = m_tel
    WHERE mname = m_name;
END;
SET @m_name ='罗小华';
SET @m_tel = 15523455432;
CALL xiugaiTel(@m_name,@m_tel);
SELECT *FROM Member;
CREATE PROCEDURE chaxunthing()
BEGIN
    SELECT
        *FROM Thing;
END;
CALL chaxunthing();
CREATE PROCEDURE thingtop10()
BEGIN
    SELECT * FROM Thing
    ORDER BY sales DESC
    LIMIT 10;
END;
CALL thingtop10();
CREATE PROCEDURE pro1 (in m_name VARCHAR(5))
    READS SQL DATA
BEGIN
    SELECT mname,member.MemberID,shoppingcar,thingID,thingname,Num
    FROM shopcar LEFT JOIN member
                           ON member.MemberID=shopcar.MemberID
    WHERE member.mname=m_name;
END;
SET @m_name='陈程城';
CALL pro1(@m_name);
ALTER TABLE shopcar MODIFY Num INT(10);
CREATE PROCEDURE affair_pro (in m_id CHAR(10),in number INT)
    MODIFIES SQL DATA
BEGIN
    DECLARE CONTINUE HANDLER FOR 1690
        BEGIN
            ROLLBACK;
        END;
    START TRANSACTION;
    UPDATE shopcar SET Num=Num+number
    WHERE shopcar.MemberID=m_id;
    COMMIT;
END;
SET @m_id=521;
SET @number=5;
CALL affair_pro(@m_id,@number);
SELECT *FROM shopcar WHERE MemberID=521;
select mname from Member group by MemberID;
select count(0) mname from Member group by MemberID;
select count(0),mname from Member group by MemberID;
select count(1),mname from Member group by MemberID;
select sid,cid,sname,cname,grade from myschool.student ,myschool.class,myschool.grade where sname='李四';
select sid,sname,cname,grade from myschool.student ,myschool.class,myschool.grade where student.cid = grade.cid;
select sname,cname,grade from myschool.student ,myschool.class,myschool.grade where student.cid = grade.cid;
select sname,cname,grade from myschool.student ,myschool.class,myschool.grade where student.sname ='李四'and student.cid = grade.cid;
select sname,cname,grade from myschool.student ,myschool.class,myschool.grade where student.sname ='李四'and student.cid=class.cid and student.cid = grade.cid;
select *
from myschool.student where sid = 4 and isnull(sname);
select *
from myschool.student where isnull(sname);
select *
from myschool.student where sid is not null;
select *
from myschool.student where sname is not null;
select *
from myschool.student where sid = 4 && sname is not null;
update myschool.student set sname = replace(sname,null,'王富贵') where sid = 4;
update myschool.student set sname = replace(sname,'那个村','王富贵') where sid = 4;
select *from myschool.student where sid = 4;
select *from myschool.student union select *from books;
select *from myschool.student union select myschool.class.cid;
select *from myschool.student union select *from myschool.class;
select sid,sname from myschool.student union select *from myschool.class;
select sid,sname from myschool.student union distinct
select *from myschool.class;
show create table myschool.student;
CREATE TABLE `student` (
                           `sid` int NOT NULL AUTO_INCREMENT,
                           `sname` varchar(5) NOT NULL,
                           `cid` int NOT NULL,
                           PRIMARY KEY (`sid`),
                           UNIQUE KEY `student_sid_uindex` (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='学生表'

select *from myschool.class;
alter table myschool.class drop teacher;
alter table myschool.class add teacher varchar(5) after cname;
alter table myschool.class modify teacher int not null;
alter table myschool.class modify teacher int;
alter table myschool.class modify teacher int unique;
desc myschool.class;
alter table myschool.class rename to myschool.class1;
alter table myschool.class1 rename to myschool.class;
alter table myschool.class modify teacher varchar(5);
show collation;
select 1+2;
select 1+2 加法;
select 9-4 `剑法`;
select 5*5 `乘法`;
select 66/5 `除法`;
select 45%6 `取余`;
alter table myschool.student add age tinyint after sname;
select ceil(99.99);
select floor(99.99);
select avg(55,79);
select avg(grade) from myschool.grade;
select avg(grade) from myschool.grade where ceil();
select ceil(avg(grade)) from myschool.grade;
select floor(avg(grade)) from myschool.grade;
select *from myschool.grade;
select sname from myschool.student where sname regexp '^王';
select teacher from myschool.class where class.teacher regexp '芳芳$';
select *from myschool.class where class.teacher regexp '芳芳$';
insert into myschool.student values (
                    5,'李雷雷',19,2,
                    6,'范汉卿',21,3);
commit;
insert into myschool.student values
                    ( 5,'李雷雷',19,2),
                    (6,'唐三',22,1),
                    (6,'范汉卿',21,3);
insert into myschool.student values
                    (5,'李雷雷',19,2),
                    (6,'唐三',22,1),
                    (7,'范汉卿',21,3);
alter table myschool.class add index classtable(cid);
select *from myschool.class;
show index from myschool.class;
create index sy1 on myschool.student(sid,sname);
show index from myschool.student;
create temporary table books (
    bid tinyint primary key ,
    bname varchar(10) not null ,
    price int ,
    index sy2 (bid)
);
create temporary table books (
    bid tinyint primary key ,
    bname varchar(10) not null ,
    price int ,
    index sy2 (bid)
)

select *
from books;
create temporary table books (
    bid tinyint primary key ,
    bname varchar(10) not null ,
    price int ,
    index sy2 (bid)
)

select *from books;
desc books;
show indexes from books;
alter table books modify bname varchar(50);
insert into books values (101,'MySQL数据库原理',74),
                         (102,'Java开发基础',50),
                         (103,'spring5框架基础+实战',88);
create temporary table books (
    bid tinyint primary key ,
    bname varchar(50) not null ,
    price int ,
    index sy2 (bid)
)

select *from books;
show create table books;
CREATE TEMPORARY TABLE `book` (
                                   `bid` tinyint NOT NULL,
                                   `bname` varchar(50) DEFAULT NULL,
                                   `price` int DEFAULT NULL,
                                   PRIMARY KEY (`bid`),
                                   KEY `sy2` (`bid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
insert into book select *from books;
select char_length(sname) from student;
select *from student;
select concat('{',sid,'',sname,'',age,'',cid,'}') from myschool.student;
select concat('{',sid,' ',sname,' ',age,' ',cid,'}') from myschool.student;
select insert('I love you  ',12,9,'very love');
select strcmp('love','love');
select substr('I love china',1,3);

select lpad('I love china ',13,'y');
select left('i love china',5);
select char_length(sname) from myschool.student;
select char_length('I love china');
select lpad('I love china ',8,'y');
select right('I love china',5);



select rpad('love',8,'z');

select field('l','I love china');

select field('love','I' ,'love',' china');

select find_in_set('you','I, love, you');

select lower('I LOVE YOU');
select adddate('2020-05-01',1000);
select curdate();
select current_date;
select current_time;
select current_timestamp;
select current_timestamp();
select datediff(current_date,'2025-05-01');
select date_add(current_date,interval 50 year );
select *from books;
select *from book;
select *from myschool.student;
select @@tx_isolation;
show engines;
select @@global.tx_isolation;
update myschool.student set sname = '张三丰' where sid = 1;
set autocommit = 0;
start transaction;
SELECT @@tx_isolation;
set global transaction isolation level repeatable read;

select dayofyear(now());
select dayofmonth(now());
select hour(curtime());

/*加密*/
select md5('abc');
select sha('abc');
select sha2('abc',0);
select char_length(sha2('abc',224)) union
select char_length(sha2('abc',384)) union
select char_length(sha2('abc',512));

/*窗口函数*/
select *,rank() over t1 from myschool.student window t1 as (partition by age order by sid);
select *,rank() over (partition by sname order by sid)from myschool.student;
select *,dense_rank() over (partition by age order by sid)from myschool.student;
select *,percent_rank() over (partition by age order by cid)from myschool.student;

/*其他函数*/
select format(99.99,0);
select format(99.49,1);
select format(99.49,2);
select conv(111,2,10);
select inet_ntoa('127.0.0.1');
select inet_ntoa(2130706433);
select convert('黄富贵'using gbk);
select cast('a' as binary);
select cast('2021-09-01' as binary );
select cast('2021-09-01' as date );
select '2021-09-01';
select connection_id();
select version();
select user();
select now();# 获取当前时间
select curtime();
select current_date;
select current_timestamp;# 获取当前时间

select myschool.student.sname,myschool.student.sid,myschool.student.age,myschool.student.cid,myschool.class.cname
from myschool.student
inner join  myschool.class
where class.cid = student.cid
order by sid;
select *from myschool.grade;
select *from myschool.class;

select myschool.student.sid,myschool.student.sname,myschool.class.teacher,myschool.student.cid,myschool.student.age,
       myschool.class.cname,myschool.grade.grade
from myschool.grade,myschool.class
inner join myschool.student where student.cid = class.cid = grade.cid;

create table teacher (
    tid int auto_increment,
    tname varchar(10) not null ,
    ttel text(13),
    cid int,
    constraint k1 primary key (tid)
);
select *from myschool.teacher;