SET sql_mode='';
DROP DATABASE IF EXISTS Housing_Lookup;
CREATE DATABASE Housing_Lookup;
USE Housing_Lookup; 
DROP TABLE IF EXISTS RealEstateCompany;
CREATE TABLE RealEstateCompany(
        phoneNumber VARCHAR(20),
        agencyName VARCHAR(30),
        agencyID INT NOT NULL AUTO_INCREMENT,
        PRIMARY KEY(agencyID)
);


DROP TABLE IF EXISTS Agent;
CREATE TABLE Agent (
        agentName VARCHAR(30),
        agencyID INT,
        agentID INT AUTO_INCREMENT,
        FOREIGN KEY(agencyID) 
                REFERENCES RealEstateCompany(agencyID)
                ON DELETE CASCADE,
        PRIMARY KEY(agentID)
);


DROP TABLE IF EXISTS User;
CREATE TABLE User(
        userName VARCHAR(30),
        phoneNumber VARCHAR(20),
        income INT,
        agentID INT,
        userID INT AUTO_INCREMENT,
        updatedAt TIMESTAMP 
                NOT NULL 
                DEFAULT NOW() ON UPDATE NOW(),
        FOREIGN KEY(agentID)
                REFERENCES Agent(agentID)
                ON DELETE CASCADE,
        PRIMARY KEY(userID)
);


DROP TABLE IF EXISTS House;
CREATE TABLE House(
        houseType VARCHAR(10),
        street VARCHAR(30),
        city VARCHAR(30),
        state VARCHAR(20),
        year INT,
        cost INT,
        bedroomCount INT,
        bathroomCount INT,
        squareFeet DOUBLE,
        agentID INT,
        houseID INT AUTO_INCREMENT,
        FOREIGN KEY(agentID) 
                REFERENCES Agent(agentID)
                ON DELETE CASCADE,
        PRIMARY KEY(houseID)
);


DROP TABLE IF EXISTS Appointments;
CREATE TABLE Appointments(
        userID INT,
        agentID INT,
        houseID INT,
        date_time DATETIME,
        appointmentID INT AUTO_INCREMENT,
        FOREIGN KEY(agentID) 
                REFERENCES Agent(agentID)
                ON DELETE CASCADE,
        FOREIGN KEY(userID) 
                REFERENCES User(userID)
                ON DELETE CASCADE,
        PRIMARY KEY(appointmentID)
);

DROP TABLE IF EXISTS Archive;
CREATE TABLE Archive(
        userName VARCHAR(30),
        phoneNumber VARCHAR(20),
        income INT,
        agentID INT,
        userID INT NOT NULL,
        updatedAt TIMESTAMP NOT NULL
);

DROP PROCEDURE IF EXISTS archiveInactiveUser;
DELIMITER //
CREATE PROCEDURE archiveInactiveUser(IN cutoff VARCHAR(50))
BEGIN
        # Insert User tuples to Archive relation
        INSERT INTO Archive (userName, phoneNumber, income, agentID, userID, updatedAt)
                # Find users that has not been after cutoff date
                SELECT userName, phoneNumber, income, agentID, userID, updatedAt
                FROM User
                WHERE updatedAt < cutoff;
        # Delete archived users tuples
        DELETE
        FROM User
        WHERE updatedAt < cutoff;
END//
DELIMITER ;

LOAD DATA LOCAL INFILE '/Users/Apollo/Documents/SJSU/2017-FALL/CS157A/db-project/mysql/RealEstateCompany.txt' INTO TABLE RealEstateCompany;
LOAD DATA LOCAL INFILE '/Users/Apollo/Documents/SJSU/2017-FALL/CS157A/db-project/mysql/Agent.txt' INTO TABLE Agent;
LOAD DATA LOCAL INFILE '/Users/Apollo/Documents/SJSU/2017-FALL/CS157A/db-project/mysql/User.txt' INTO TABLE User;
LOAD DATA LOCAL INFILE '/Users/Apollo/Documents/SJSU/2017-FALL/CS157A/db-project/mysql/House.txt' INTO TABLE House;
LOAD DATA LOCAL INFILE '/Users/Apollo/Documents/SJSU/2017-FALL/CS157A/db-project/mysql/Appointments.txt' INTO TABLE Appointments;
