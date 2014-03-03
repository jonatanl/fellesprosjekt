

CREATE TABLE users (
	userID int,
	username varchar(50),
	password varchar(50),

	PRIMARY KEY (userID)
); 

CREATE TABLE memberOf (
	userID int,
	groupID int,

	PRIMARY KEY (userID, groupID),
	FOREIGN KEY (userID) REFERENCES users(userID),
	FOREIGN KEY (groupID) REFERENCES group(groupID)
);

CREATE TABLE group (
	groupID int,

	PRIMARY KEY (groupID)
);

CREATE TABLE subgroup (
	groupID int,
	subgroupID int,

	PRIMARY KEY (groupID, containsID)
	FOREIGN KEY (groupID) REFERENCES group(groupID),
	FOREIGN KEY (subgroupID) REFERENCES group(groupID)
);

CREATE TABLE event (
	eventID int,
	eventName varchar(50),
	startTime DateTime,
	endTime DateTime,
	description varchar(100),
	location varchar(100),
	roomID int,

	PRIMARY KEY (eventID),
	FOREIGN KEY (roomID) REFERENCES room(roomID)
);

CREATE TABLE room (
	roomID int,
	adress varchar(255),
	capacity int,

	PRIMARY KEY (roomID)
);

CREATE TABLE alert (
	alertID int,
	time DateTime,

	PRIMARY KEY (alertID)
);

CREATE TABLE eventParticipant (
	alertID int,
	eventID int,
	userID int,
	isDeleted tinyint(1),
	pendingChange tinyint(1),
	response varchar(10),

	PRIMARY KEY (alertID, userID, eventID),
	FOREIGN KEY (alertID) REFERENCES alert(alertID),
	FOREIGN KEY (eventID) REFERENCES event(eventID),
	FOREIGN KEY (userID) REFERENCES user(userID)
);