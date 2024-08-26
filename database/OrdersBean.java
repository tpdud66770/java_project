package database;

public class OrdersBean {
	private int order_num;
	private int reserve_num;
	private int menu_num;
	private int menu_amount;
	
	public int getOrder_num() {
		return order_num;
	}
	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}
	public int getReserve_num() {
		return reserve_num;
	}
	public void setReserve_num(int reserve_num) {
		this.reserve_num = reserve_num;
	}
	public int getMenu_num() {
		return menu_num;
	}
	public void setMenu_num(int menu_num) {
		this.menu_num = menu_num;
	}
	public int getMenu_amount() {
		return menu_amount;
	}
	public void setMenu_amount(int menu_amount) {
		this.menu_amount = menu_amount;
	}
}
