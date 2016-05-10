drop table news_tag;
drop table news_author;
drop table comments;
drop table tag;
drop table author;
drop table news;
drop table user_roles;
drop table users;
drop sequence news_seq;
drop sequence tag_seq;
drop sequence author_seq;
drop sequence comments_seq;
drop sequence users_seq;

create table news (
  news_id number (20) primary key not null, 
  short_text nvarchar2 (100) not null, 
	full_text nvarchar2(2000) not null, 
  title nvarchar2(30) not null, 
  creation_date timestamp not null, 
  modification_date date not null
);
create sequence news_seq start with 1000;
commit;
create table tag (
  tag_id number(20) primary key not null, 
  tag_name nvarchar2(30) not null
);
create sequence tag_seq start with 1000;
commit;
create table author (
  author_id number(20) primary key not null, 
  name nvarchar2(30) not null,
  expired timestamp
);
create sequence author_seq start with 1000;
commit;
create table comments (
  comment_id number(20) primary key not null, 
  comment_text nvarchar2(100) not null, 
  creation_date timestamp not null,
  news_id number(20) not null, 
  foreign key(news_id) references news(news_id) on delete cascade
);
create sequence comments_seq start with 1000;
commit;
create table news_author (
  news_id number(20) not null, 
  author_id number(20) not null,
  foreign key(news_id) references news(news_id) on delete cascade, 
  foreign key(author_id) references author(author_id) on delete cascade
);
commit;
create table news_tag (
  news_id number(20) not null, 
  tag_id number(20) not null,
  foreign key(news_id) references news(news_id) on delete cascade,
  foreign key(tag_id) references tag(tag_id) on delete cascade
);
commit;
create table users(
  user_id number (20) primary key not null, 
  user_name nvarchar2 (50) not null, 
	login varchar2(30) not null, 
  password varchar2(40) not null
);
create sequence users_seq start with 1000;
commit;
create table user_roles(
  user_id number(20) not null,
  role_name varchar2(50) not null,
  foreign key(user_id) references users(user_id) on delete cascade
);
commit;