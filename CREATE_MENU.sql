CREATE TABLE MENU(
	menu_num INT NOT NULL AUTO_INCREMENT,
	menu_name CHAR(20) NOT NULL,
	menu_type CHAR(20) NOT NULL,
	menu_price INT NOT NULL,
	menu_details VARCHAR(255),
	menu_image VARCHAR(255),
	
	PRIMARY KEY(MENU_NUM)
)