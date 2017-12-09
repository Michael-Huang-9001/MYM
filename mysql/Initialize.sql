SET sql_mode='';
DROP DATABASE IF EXISTS Housing_Lookup;
CREATE DATABASE Housing_Lookup;
USE Housing_Lookup; 

#DROP TABLE IF EXISTS RealEstateCompany;
CREATE TABLE RealEstateCompany(
        phoneNumber VARCHAR(20),
        agencyName VARCHAR(30),
        agencyID INT NOT NULL AUTO_INCREMENT,
        PRIMARY KEY(agencyID)
);


#DROP TABLE IF EXISTS Agent;
CREATE TABLE Agent (
        agentName VARCHAR(30),
        phoneNumber VARCHAR(30),
        agencyID INT,
        agentID INT AUTO_INCREMENT,
        FOREIGN KEY(agencyID) 
                REFERENCES RealEstateCompany(agencyID)
                ON DELETE CASCADE,
        PRIMARY KEY(agentID)
);


#DROP TABLE IF EXISTS User;
CREATE TABLE User(
        userName VARCHAR(30),
        phoneNumber VARCHAR(20),
        income INT,
        agentID INT,
        updatedAt TIMESTAMP 
                NOT NULL 
                DEFAULT NOW() ON UPDATE NOW(),
        FOREIGN KEY(agentID)
                REFERENCES Agent(agentID)
                ON DELETE CASCADE,
        PRIMARY KEY(userName)
);


#DROP TABLE IF EXISTS House;
CREATE TABLE House(
        houseType VARCHAR(10),
        street VARCHAR(30),
        city VARCHAR(30),
        state VARCHAR(20),
        zipcode VARCHAR(5),
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


# DROP TABLE IF EXISTS Appointments;
CREATE TABLE Appointments(
        userName VARCHAR(30),
        agentID INT,
        houseID INT,
        date_time DATETIME,
        appointmentID INT AUTO_INCREMENT,
        FOREIGN KEY(agentID) 
                REFERENCES Agent(agentID)
                ON DELETE CASCADE,
        FOREIGN KEY(userName) 
                REFERENCES User(userName)
                ON DELETE CASCADE,
        FOREIGN KEY(houseID) 
                REFERENCES House(houseID)
                ON DELETE CASCADE,
        PRIMARY KEY(appointmentID)
);

DROP TABLE IF EXISTS Archive;
CREATE TABLE Archive(
        userName VARCHAR(30),
        phoneNumber VARCHAR(20),
        income INT,
        agentID INT,
        updatedAt TIMESTAMP NOT NULL
);

DROP PROCEDURE IF EXISTS archiveInactiveUser;
DELIMITER //
CREATE PROCEDURE archiveInactiveUser(IN cutoff VARCHAR(50))
BEGIN
        # Insert User tuples to Archive relation
        INSERT INTO Archive (userName, phoneNumber, income, agentID, updatedAt)
                # Find users that has not been after cutoff date
                SELECT userName, phoneNumber, income, agentID, updatedAt
                FROM User
                WHERE updatedAt < cutoff;
        # Delete archived users tuples
        DELETE
        FROM User
        WHERE updatedAt < cutoff;
END//
DELIMITER ;

LOAD DATA LOCAL INFILE '/Users/yk/Code/CS157A/project/mysql/RealEstateCompany.csv' 
INTO TABLE RealEstateCompany
FIELDS TERMINATED BY ',';

LOAD DATA LOCAL INFILE '/Users/yk/Code/CS157A/project/mysql/Agent.csv'
INTO TABLE Agent
FIELDS TERMINATED BY ',';

LOAD DATA LOCAL INFILE '/Users/yk/Code/CS157A/project/mysql/User.csv'
INTO TABLE User
FIELDS TERMINATED BY ',';

LOAD DATA LOCAL INFILE '/Users/yk/Code/CS157A/project/mysql/House.csv'
INTO TABLE House
FIELDS TERMINATED BY ',';

LOAD DATA LOCAL INFILE '/Users/yk/Code/CS157A/project/mysql/Appointments.csv'
INTO TABLE Appointments
FIELDS TERMINATED BY ',';
