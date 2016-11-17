create database day23;
use day23;
create table categories(
	id int primary key auto_increment,
	name varchar(100) not null unique,
	description varchar(255)
);
create table books(
	id int primary key auto_increment,
	name varchar(100) not null,
	author varchar(100),
	price float(8,2),
	path varchar(100),
	filename varchar(100) unique,
	description varchar(18000),
	categoryId int,
	constraint category_id_fk foreign key(categoryId) references categories(id)
);
create table customers(
	id int primary key auto_increment,
	username varchar(100) not null unique,
	password varchar(100) not null,
	phonenum varchar(40) not null,
	address varchar(1000) not null,
	email varchar(100)
);
create table orders(
	num varchar(100) primary key,
	quantity int,
	price float(12,2),
	status int,
	customerId int,
	gentime datetime,
	constraint customer_id_fk foreign key(customerId) references customers(id)
);
create table orderitems(
	id int primary key auto_increment,
	quantity int,
	price float(12,2),
	bookId int,
	ordernum varchar(100),
	constraint book_id_fk foreign key(bookId) references books(id),
	constraint ordernum_fk foreign key(ordernum) references orders(num)
);
create table ordernum(
	prefix varchar(100) unique,#20150803
	count int #Ë³ÐòºÅ  1
);