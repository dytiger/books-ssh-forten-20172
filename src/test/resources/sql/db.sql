DROP DATABASE books;

CREATE DATABASE books /*!40100 DEFAULT CHARACTER SET utf8 */;

use books;

CREATE TABLE book
(
	id int(11) PRIMARY KEY AUTO_INCREMENT,
  name varchar(50) NOT NULL,
	author varchar(50) NOT NULL,
	press varchar(50) NOT NULL,
	pub_date date NOT NULL,
	price int(5) NOT NULL,
	amount int(3) NOT NULL
);

CREATE TABLE users
(
	id int(11) PRIMARY KEY AUTO_INCREMENT,
	login_name varchar(30) UNIQUE NOT NULL,
	password varchar(36) NOT NULL,
	name varchar(30) NOT NULL,
	library_card varchar(10) NOT NULL,
	user_level int(2) DEFAULT 0 NOT NULL
);

CREATE TABLE borrow_info
(
	id int(11) PRIMARY KEY AUTO_INCREMENT,
	uid int(11),
	bid int(11),
	borrow_time date,
	borrow_status char(2) DEFAULT 'PB'
);

INSERT INTO users (login_name,name,password,library_card,user_level) VALUES
('admin','万雷','1','100000',99),
('u1','李靖','1','110000',0),
('u2','王双','1','111000',1),
('u3','刘大强','1','111100',0),
('u4','张成','1','111110',2),
('u5','孙万','1','111111',10);

INSERT INTO book (name,author,press,price,amount,pub_date) VALUES
('Java面试宝典','张小华','清华大学出版社',38,5,'2015-06-01'),
('Python入门教程','刘刚','电子工业出版社',64,3,'2005-02-01'),
('Pro Spring 5','李丰','中国电力出版社',89,2,'2017-10-01'),
('移动游戏开发','王华','人民邮电出版社',50,2,'2017-03-01'),
('Java面试宝典（第2版）','程晓','电子工业出版社',48,7,'2014-02-01'),
('诗经译注','张洋','清华大学出版社',32,2,'2014-08-01'),
('中国古典文学概述','刘苗','清华大学出版社',102,1,'2009-06-01'),
('大学英语四级真题','尹九江','人民邮电出版社',18,12,'2016-01-01'),
('数据库原理','楚凌','人民邮电出版社',33,6,'2015-06-01'),
('算法','封江海','电子工业出版社',98,3,'2013-03-01'),
('数据结构','刘灡','清华大学出版社',45,8,'2016-04-01'),
('C语言程序设计','王建宇','人民邮电出版社',33,15,'2011-09-01'),
('Oracle DBA教程','刘怀楚','电子工业出版社',68,1,'2012-12-01');

INSERT INTO borrow_info (uid,bid,borrow_time,borrow_status) VALUES
(1,2,'2017-06-30','BD'),
(2,3,'2017-09-10','BD'),
(2,6,'2017-10-16','BD'),
(3,12,'2017-11-30','BD'),
(4,12,'2017-12-01','BD'),
(4,8,'2017-11-05','BD'),
(1,7,'2017-12-10','BD'),
(2,10,'2017-12-19','PB');

commit;