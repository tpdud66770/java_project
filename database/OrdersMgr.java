package database;

import java.nio.file.attribute.AclEntry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class OrdersMgr {
	
	private DBConnectionMgr pool;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	String sql ="";
	
	public OrdersMgr() {
		try {
			pool = DBConnectionMgr.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Vector<OrdersBean> getOrders(int reserve_num){
		Vector<OrdersBean> vlist = new Vector<OrdersBean>();
		try {
			con = pool.getConnection();
			sql = "select * from orders where reserve_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, reserve_num);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				OrdersBean bean = new OrdersBean();
				bean.setOrder_num(rs.getInt(1));
				bean.setReserve_num(rs.getInt(2));
				bean.setMenu_num(rs.getInt(3));
				bean.setMenu_amount(rs.getInt(4));
				vlist.add(bean);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			pool.freeConnection(con,pstmt,rs);
		}
		return vlist;
	}
	
	public boolean insertOrder(OrdersBean bean) {
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "Insert orders Values(null,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,bean.getReserve_num());
			pstmt.setInt(2, bean.getMenu_num());
			pstmt.setInt(3, bean.getMenu_amount());
			flag = pstmt.executeUpdate() == 1 ? true : false;
		} catch (Exception e) {
			System.out.println("Orders Insert Error");
		} finally {
			pool.freeConnection(con,pstmt);
		}
		return flag;
	}
	
	public int getTotalPrice(int reserve_num) {
		int totalPrice = 0;
		try {
			con = pool.getConnection();
			sql = "SELECT o.reserve_num, SUM(m.menu_price * o.menu_amount) AS total_price FROM menu m JOIN orders o ON m.menu_num = o.menu_num WHERE reserve_num = ? GROUP BY o.reserve_num;";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, reserve_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				totalPrice = rs.getInt(2);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			pool.freeConnection(con,pstmt,rs);
		}
		return totalPrice;
	}
}
