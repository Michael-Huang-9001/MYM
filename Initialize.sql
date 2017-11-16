#Put SET sql_mode='';
DROP DATABASE IF EXISTS Housing_Lookup;
CREATE DATABASE Housing_Lookup;
USE Housing_Lookup; 

#DROP TABLE IF EXISTS RealEstateCompany;
CREATE TABLE RealEstateCompany(
phoneNumber VARCHAR(10),
agencyName VARCHAR(30),
agencyID INT AUTO_INCREMENT,
PRIMARY KEY(agencyID)
);

#DROP TABLE IF EXISTS Agent;
CREATE TABLE Agent (
agentName VARCHAR(30),
agencyID INT,
agentID INT AUTO_INCREMENT,
FOREIGN KEY(agencyID) REFERENCES RealEstateCompany(agencyID),
PRIMARY KEY(agentID)
);


#DROP TABLE IF EXISTS User
CREATE TABLE User(
userName VARCHAR(30),
phoneNumber VARCHAR(10),
income INT,
agentID INT,
userID INT AUTO_INCREMENT,
FOREIGN KEY(agentID) REFERENCES Agent(agentID),
PRIMARY KEY(userID)
);


#DROP TABLE IF EXISTS House
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
FOREIGN KEY(agentID) REFERENCES Agent(agentID),
PRIMARY KEY(houseID)
);


#DROP TABLE IF EXISTS Appointments
CREATE TABLE Appointments(
userID INT,
agentName VARCHAR(30),
agentID INT,
houseID INT,
date DATE,
time TIME,
appointmentID INT AUTO_INCREMENT,
FOREIGN KEY(agentID) REFERENCES Agent(agentID),
FOREIGN KEY(userID) REFERENCES User(userID),
PRIMARY KEY(appointmentID)
);

LOAD DATA LOCAL INFILE 'C:/Users/Michael/Desktop/Workspace/Java/Housing Lookup/realestatecompanies.txt' INTO TABLE RealEstateCompany;
