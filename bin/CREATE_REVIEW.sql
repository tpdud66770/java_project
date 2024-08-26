CREATE TABLE review(
	review_num INT NOT NULL AUTO_INCREMENT,
	review_content VARCHAR(255),
	review_score INT NOT NULL,
	review_image VARCHAR(255),
	
	PRIMARY KEY(review_num),
	FOREIGN KEY(review_num) REFERENCES reservation(reserve_num)
)