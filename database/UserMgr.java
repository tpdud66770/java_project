package database;

import java.nio.file.attribute.AclEntry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class UserMgr {
	
	private DBConnectionMgr pool;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	String sql ="";
	
	public UserMgr() {
		try {
			pool = DBConnectionMgr.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public UserBean getUser(String userId) {
		UserBean bean = new UserBean();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM user WHERE user_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				bean.setUser_id(rs.getString(1));
				bean.setUser_pwd(rs.getString(2));
				bean.setUser_name(rs.getString(3));
				bean.setUser_phone(rs.getString(4));
				bean.setUser_temp(rs.getBoolean(5));
				bean.setUser_point(rs.getInt(6));
			}
		} catch (Exception e) {
			System.err.println("User Select Error");
		}
		return bean;
	}
	
	public Boolean isUserTemp(String userId) {
		Boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "SELECT user_temp FROM user WHERE user_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				flag = rs.getBoolean(1);
			}
		} catch (Exception e) {
			System.err.println("User Select Error");
		}
		return flag;
	}
	
	public void deleteUser(String userId) {
		try {
			con = pool.getConnection();
			sql = "DELETE From user WHERE user_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.err.println("User DELETE Error");
		}
	}
	public int getUserPoint(String userId) {
		int userPoint = 0;
		try {
			con = pool.getConnection();
			sql = "SELECT user_point FROM user WHERE user_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				userPoint = rs.getInt(1);
			}
		} catch (Exception e) {
			System.err.println("User Select Error");
		}
		return userPoint;
	}

}
