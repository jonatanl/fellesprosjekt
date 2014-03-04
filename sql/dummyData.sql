insert into room (adress, capacity) values('test', 10);
insert into room (adress, capacity) values('test', 12);
insert into room (adress, capacity) values('test', 13);
insert into room (adress, capacity) values('test', 14);
insert into room (adress, capacity) values('test', 30);
 
insert into users (username, password) values ('jonatan', 123456);
insert into users (username, password) values ('johannes', 123456);
insert into users (username, password) values ('ofossan', 123456);
insert into users (username, password) values ('Erik', 1234567);

insert into alert (time) values ('2014-03-05 09:00:00');
insert into alert (time) values ('2014-03-06 10:00:00');

insert into event (eventName, startTime, endTime, description, location, roomID, ownerID) values ('Systemtest', '2014-03-05 10:00:00', '2014-03-05 12:00:00', 'Test av hele systemet', 'Paa Gloeshaugen', 1, 2);
insert into event (eventName, startTime, endTime, description, location, roomID, ownerID) values ('Brukbarhetstest', '2014-03-06 11:00:00', '2014-03-06 13:00:00', 'Test med 4 testpersoner', 'Paa Gloeshaugen', 2, 2);

insert into eventParticipant values(1, 1, 2, 0, 1, 'unknown');
insert into eventParticipant values(1, 1, 1, 0, 1, 'unknown');
insert into eventParticipant values(Null, 1, 2, 0, 1, 'Yes');

insert into calendar.group(name) values ('gruppe1');
insert into calendar.group(name) values ('gruppe2');
insert into calendar.group(name) values ('gruppe3');

insert into subgroup values (1, 2);
insert into subgroup values (1, 3);

insert into memberOf values (1, 1);
insert into memberOf values (2, 1);
				


select * from room;
select * from users;
select * from calendar.group;
select * from event;
select * from alert;
select * from subgroup;
select * from memberOf;
select * from eventParticipant
