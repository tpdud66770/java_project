package database;

import java.nio.file.attribute.AclEntry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class MenuMgr {
	
	private DBConnectionMgr pool;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	String sql ="";
	
	public MenuMgr() {
		try {
			pool = DBConnectionMgr.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Vector<MenuBean> getMenus(){
		Vector<MenuBean> vlist = new Vector<MenuBean>();
		try {
			con = pool.getConnection();
			sql = "select * from menu";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MenuBean bean = new MenuBean();
				bean.setMenu_num(rs.getInt(1));
				bean.setMenu_name(rs.getString(2));
				bean.setMenu_type(rs.getString(3));
				bean.setMenu_price(rs.getInt(4));
				bean.setMenu_image(rs.getString(5));
				vlist.add(bean);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			pool.freeConnection(con,pstmt,rs);
		}
		return vlist;
	}
	public Vector<MenuBean> getMenusOfType(String menu_type){
		Vector<MenuBean> vlist = new Vector<MenuBean>();
		try {
			con = pool.getConnection();
			
			sql = "select * from menu where menu_type = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,menu_type);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MenuBean bean = new MenuBean();
				bean.setMenu_num(rs.getInt(1));
				bean.setMenu_name(rs.getString(2));
				bean.setMenu_type(rs.getString(3));
				bean.setMenu_price(rs.getInt(4));
				bean.setMenu_image(rs.getString(5));
				vlist.add(bean);
			}
		} catch (Exception e) {
			System.out.println("Menu Select Error");
		} finally {
			pool.freeConnection(con,pstmt,rs);
		}
		return vlist;
	}
	public MenuBean getMenu(int menu_num){
		MenuBean bean = new MenuBean();
		try {
			con = pool.getConnection();
			sql = "select * from menu where menu_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, menu_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bean.setMenu_num(rs.getInt(1));
				bean.setMenu_name(rs.getString(2));
				bean.setMenu_type(rs.getString(3));
				bean.setMenu_price(rs.getInt(4));
				bean.setMenu_image(rs.getString(5));
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			pool.freeConnection(con,pstmt,rs);
		}
		return bean;
	}
	public Vector<MenuBean> getMenusOfTypeBySales(String menu_type){
		Vector<MenuBean> vlist = new Vector<MenuBean>();
		try {
			con = pool.getConnection();
			sql = "SELECT m.*, IFNULL(SUM(s.sales_amount), 0) AS total_amount FROM menu m LEFT JOIN sales s ON m.menu_num = s.sales_menu WHERE m.menu_type = ? GROUP BY m.menu_num ORDER BY total_amount DESC";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,menu_type);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MenuBean bean = new MenuBean();
				bean.setMenu_num(rs.getInt(1));
				bean.setMenu_name(rs.getString(2));
				bean.setMenu_type(rs.getString(3));
				bean.setMenu_price(rs.getInt(4));
				bean.setMenu_image(rs.getString(5));
				vlist.add(bean);
			}
		} catch (Exception e) {
			System.out.println("Menu Select Error");
		} finally {
			pool.freeConnection(con,pstmt,rs);
		}
		return vlist;
	}
	
	public MenuBean getBestMenuBySales(String menu_type) {
		MenuBean bean = new MenuBean();
		try {
			con = pool.getConnection();
			sql = "SELECT m.*, IFNULL(SUM(s.sales_amount), 0) AS total_amount FROM menu m LEFT JOIN sales s ON m.menu_num = s.sales_menu WHERE m.menu_type = ? GROUP BY m.menu_num ORDER BY total_amount DESC LIMIT 1;";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, menu_type);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bean.setMenu_num(rs.getInt(1));
				bean.setMenu_name(rs.getString(2));
				bean.setMenu_type(rs.getString(3));
				bean.setMenu_price(rs.getInt(4));
				bean.setMenu_image(rs.getString(5));
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			pool.freeConnection(con,pstmt,rs);
		}
		return bean;
	}
	public String getMenuName(int menu_num) {
		return getMenu(menu_num).getMenu_name();
	}
}
