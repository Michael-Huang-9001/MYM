DELIMITER |
create trigger pastappt
	after update on Appointments
	for each row
	begin
		if (new.date_time < now())
			delete from Appointments
			where date_time = new.date_time;
		end if;
	end;
DELIMITER ;

DELIMITER //
create trigger insAgent
before insert on Agent
for each row
begin
	if (select * from User where new.phoneNumber = phoneNumber)
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'agent already exists';
	end if
end;
end//
DELIMITER ;

DELIMITER //
create trigger insUser
	before insert on User
	for each row 
	begin
		if (select * from User where new.phoneNumber = phoneNumber)
		then
			signal SQLSTATE '45000'
			set MESSAGE_TEXT = 'error - user already exists';
		end if;
end//
DELIMITER ;
