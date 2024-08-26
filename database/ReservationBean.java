package database;

public class ReservationBean {
	private int reserve_num;
	private int tbl_num;
	private String user_id;
	private String reserve_time;
	private int reserve_member;
	private int reserve_price;
	private String reserve_at;
	public int getReserve_num() {
		return reserve_num;
	}
	public void setReserve_num(int reserve_num) {
		this.reserve_num = reserve_num;
	}
	public int getTbl_num() {
		return tbl_num;
	}
	public void setTbl_num(int tbl_num) {
		this.tbl_num = tbl_num;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getReserve_time() {
		return reserve_time;
	}
	public void setReserve_time(String reserve_time) {
		this.reserve_time = reserve_time;
	}
	public int getReserve_member() {
		return reserve_member;
	}
	public void setReserve_member(int reserve_member) {
		this.reserve_member = reserve_member;
	}
	public int getReserve_price() {
		return reserve_price;
	}
	public void setReserve_price(int reserve_price) {
		this.reserve_price = reserve_price;
	}
	public String getReserve_at() {
		return reserve_at;
	}
	public void setReserve_at(String reserve_at) {
		this.reserve_at = reserve_at;
	}
	
	
}
