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
insert into users (username, password) values ('Gruppe1Medlem1', 1234567);
insert into users (username, password) values ('Gruppe1Medlem2', 1234567);
insert into users (username, password) values ('Gruppe2Medlem1', 1234567);
insert into users (username, password) values ('Gruppe2Medlem2', 1234567);
insert into users (username, password) values ('Gruppe3Medlem1', 1234567);
insert into users (username, password) values ('Gruppe3Medlem2', 1234567);
insert into users (username, password) values ('Gruppe4Medlem1', 1234567);
insert into users (username, password) values ('Gruppe4Medlem2', 1234567);

insert into alarm (time) values ('2014-03-05 09:00:00');
insert into alarm (time) values ('2014-03-06 10:00:00');

insert into event (eventName, startTime, endTime, description, location, roomID, ownerID) values ('Systemtest', '2014-03-05 10:00:00', '2014-03-05 12:00:00', 'Test av hele systemet', 'Paa Gloeshaugen', 1, 2);
insert into event (eventName, startTime, endTime, description, location, roomID, ownerID) values ('Brukbarhetstest', '2014-03-06 11:00:00', '2014-03-06 13:00:00', 'Test med 4 testpersoner', 'Paa Gloeshaugen', 2, 2);

insert into eventParticipant values(1, 1, 2, 0, 1, 'unknown');
insert into eventParticipant values(Null, 1, 1, 0, 1, 'Yes');

insert into calendar.group(name) values ('gruppe1');
insert into calendar.group(name) values ('gruppe2BarnAv1');
insert into calendar.group(name) values ('gruppe3BarnAv1');
insert into calendar.group(name) values ('gruppe4BarnAv2');
insert into calendar.group(name) values ('gruppe5BarnAv2');

insert into subgroup values (1, 2);
insert into subgroup values (1, 3);
insert into subgroup values (2, 4);
insert into subgroup values (2, 5);

insert into memberOf values (1, 1);
insert into memberOf values (2, 1);
insert into memberOf values (3, 2);
insert into memberOf values (4, 2);
insert into memberOf values (5, 3);
insert into memberOf values (6, 3);
insert into memberOf values (7, 4);
insert into memberOf values (8, 5);

select * from room;
select * from users;
select * from calendar.group;
select * from event;
select * from alarm;
select * from subgroup;
select * from memberOf;
select * from eventParticipant;
