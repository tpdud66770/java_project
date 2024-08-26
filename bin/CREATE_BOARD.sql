CREATE TABLE board(
	board_num INT NOT NULL AUTO_INCREMENT,
	board_type CHAR(10) NOT NULL,
	board_id CHAR(20) NOT NULL,
	board_content VARCHAR(200) NOT NULL,
	board_answer VARCHAR(200) NOT NULL,
	board_answer_id CHAR(20) NOT NULL,
	
	PRIMARY KEY(board_num),
	FOREIGN KEY(board_id) REFERENCES user(user_id),
	FOREIGN KEY(board_answer_id) REFERENCES manager(manager_id)
)