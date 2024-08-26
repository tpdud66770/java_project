CREATE TABLE USER(
	user_id CHAR(20) NOT NULL,
	user_pwd CHAR(20) NOT NULL,
	user_name CHAR(10) NOT NULL,
	user_phone CHAR(20) NOT NULL,
	user_temp BOOLEAN DEFAULT false,
	user_point INT,
	
	PRIMARY KEY(user_id)
)