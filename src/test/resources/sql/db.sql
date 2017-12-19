-- 这里是数据库的初始化脚本

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

commit;