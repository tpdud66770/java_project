CREATE TABLE RESERVATION(
	reserve_num INT NOT NULL AUTO_INCREMENT,
	tbl_num INT NOT NULL,
	user_id CHAR(20) NOT NULL,
	reserve_time DATE NOT NULL,
	reserve_member INT NOT NULL,
	menu_num INT NOT NULL,
	reserve_price INT NOT NULL,
	
  	PRIMARY KEY(reserve_num),
   FOREIGN KEY(user_id) REFERENCES user(user_id),
   FOREIGN KEY(tbl_num) REFERENCES tbl(tbl_num),
   FOREIGN KEY(menu_num) REFERENCES menu(menu_num)
)
