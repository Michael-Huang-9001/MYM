9:25
INSERT 
INTO 
Appointments 
VALUES
(1, 1, 1, 11/22/17, 9:25);

INSERT 
INTO 
House 
(houseType, street, city, state, year, cost, bedroomCount, bathroomCount, squareFeet, agentID)
VALUES
('apartment', '805 Wall St.', 'Lompoc', 'CA 93436', 1987, 1500, 4, 4, 2500, 2);

1. Create new home, real estate agency, agent, and user.
INSERT INTO RealEstateCompany
        (phoneNumber, agencyName)
        VALUES
        ('(222) 681-9973', 'New Agency');

2. Modify the existed agent, user, house, and appointment.
3. Find agents who can introduce houses that value less than a certain price.
4. Find user who have certain income.
5. Find addresses of houses that have X bed X bathrooms.
SELECT street, city, state
FROM House 
WHERE bedroomCount = 2
        and bathroomCount = 2;

6. Find agents who have clients that over 50K.
7. Find the price of houses that belongs to Agency A
SELECT *
FROM House
WHERE House.agentID IN (
        SELECT agentID
        FROM Agent
        WHERE Agent.agencyID IN (
                SELECT agencyID
                FROM RealEstateCompany
                WHERE RealEstateCompany.agencyName = 'East West Commercial Real Esta' 
        )
);

8. Delete user, agent, appointment
9. Find agency that has houses located in city A.
10. Find agency that has agents who can show houses built before some year.
11. Determine if a user can afford a home (check if income is 2x monthly cost).
12. Find available/booked appointments.
13. Look up user current booked appointments.
SELECT *
FROM Appointments A
WHERE EXISTS (
        SELECT *
        FROM User
        WHERE User.userID = A.userID
                AND User.userID = 2
);

14. List all the appointment of agents who belong to a certain agency.
15. Find the number of users who are interested in a certain house
SELECT houseID, COUNT(houseID)
FROM Appointments
GROUP BY houseID;
