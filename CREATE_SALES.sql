CREATE TABLE sales(
	sales_num INT NOT NULL AUTO_INCREMENT,
	sales_menu INT NOT NULL,
	sales_amount INT NOT NULL,
	sales_price INT NOT NULL,
	sales_at TIMESTAMP NOT NULL,
	
	PRIMARY KEY(sales_num),
	FOREIGN KEY(sales_num) REFERENCES reservation(reserve_num)
)