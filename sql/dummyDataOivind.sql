insert into room (adress, capacity) values('not chosen', null);
insert into room (adress, capacity) values('test1', 2);
insert into room (adress, capacity) values('test2', 3);
insert into room (adress, capacity) values('test3', 4);
insert into room (adress, capacity) values('test4', 10);
insert into room (adress, capacity) values('test5', 30);
 
insert into users (username, password) values ('jonatan', 123456);
insert into users (username, password) values ('johannes', 123456);
insert into users (username, password) values ('ofossan', 123456);
insert into users (username, password) values ('Erik', 123456);
insert into users (username, password) values ('simen', 123456);
insert into users (username, password) values ('Ole', 123456);

insert into calendar.group(name) values ('Gruppe33');

insert into memberOf values (1, 1);
insert into memberOf values (2, 1);
insert into memberOf values (3, 1);
insert into memberOf values (4, 1);
insert into memberOf values (5, 1);
insert into memberOf values (6, 1);


select * from room;
select * from users;
select * from calendar.group;
select * from event;
select * from alarm;
select * from subgroup;
select * from memberOf;
select * from eventParticipant;
