drop database if exists calendar;
create database calendar;
use calendar;

CREATE TABLE calendar.group (
	groupID int auto_increment,

	PRIMARY KEY (groupID)
);

CREATE TABLE calendar.room (
	roomID int auto_increment,
	adress varchar(255),
	capacity int,

	PRIMARY KEY (roomID)
);

CREATE TABLE calendar.alert (
	alertID int auto_increment,
	time DateTime,

	PRIMARY KEY (alertID)
);

CREATE TABLE calendar.users (
	userID int auto_increment,
	username varchar(50),
	password varchar(50),

	PRIMARY KEY (userID)
); 

CREATE TABLE calendar.event (
	eventID int auto_increment,
	eventName varchar(50),
	startTime DateTime,
	endTime DateTime,
	description varchar(100),
	location varchar(100),
	roomID int,
	ownerID int,

	PRIMARY KEY (eventID),
	FOREIGN KEY (roomID) REFERENCES room(roomID),
	FOREIGN KEY (ownerID) REFERENCES users(userID)	
);

CREATE TABLE calendar.memberOf (
	userID int,
	groupID int,

	PRIMARY KEY (userID, groupID),
	FOREIGN KEY (userID) REFERENCES users(userID),
	FOREIGN KEY (groupID) REFERENCES calendar.group(groupID)
);

CREATE TABLE calendar.subgroup (
	groupID int,
	subgroupID int,

	PRIMARY KEY (groupID, subgroupID),
	FOREIGN KEY (groupID) REFERENCES calendar.group(groupID),
	FOREIGN KEY (subgroupID) REFERENCES calendar.group(groupID)
);

CREATE TABLE calendar.eventParticipant (
	alertID int,
	eventID int,
	userID int,
	isDeleted tinyint(1),
	pendingChange tinyint(1),
	response varchar(10),

	PRIMARY KEY (userID, eventID),
	FOREIGN KEY (alertID) REFERENCES alert(alertID),
	FOREIGN KEY (eventID) REFERENCES event(eventID),
	FOREIGN KEY (userID) REFERENCES users(userID)
);
show tables;
 