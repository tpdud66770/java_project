package database;

import java.nio.file.attribute.AclEntry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class ReservationMgr {
	
	private DBConnectionMgr pool;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	String sql ="";
	
	public ReservationMgr() {
		try {
			pool = DBConnectionMgr.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean insertReservation(ReservationBean bean) {
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "Insert reservation Values(null,?,?,?,?,?,now())";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,bean.getTbl_num());
			pstmt.setString(2,bean.getUser_id());
			pstmt.setString(3, bean.getReserve_time());
			pstmt.setInt(4, bean.getReserve_member());
			pstmt.setInt(5, 0);
			flag = pstmt.executeUpdate() == 1 ? true : false;
		} catch (Exception e) {
			System.out.println("Reservation Insert Error");
		} finally {
			pool.freeConnection(con,pstmt);
		}
		return flag;
	}
	public String chageFormatTime(String reserve_time) {
		String result = "";
		int index = reserve_time.lastIndexOf(":");		
		result = reserve_time.substring(0,index);
		return result;
	}
	public ReservationBean getReservation(int reserve_num) {
		ReservationBean bean = new ReservationBean();
		try {
			con = pool.getConnection();
			sql = "select *  from reservation where reserve_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, reserve_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bean.setReserve_num(rs.getInt(1));
				bean.setTbl_num(rs.getInt(2));
				bean.setUser_id(rs.getString(3));
				bean.setReserve_time(rs.getString(4));
				bean.setReserve_member(rs.getInt(5));
				bean.setReserve_price(rs.getInt(6));
				bean.setReserve_at(rs.getString(7));
			}
		} catch (Exception e) {
			System.out.println("Reservation Select Error");
		} finally {
			pool.freeConnection(con,pstmt,rs);
		}
		
		return bean;
	}
	
	public ReservationBean getLastReservation() {
		ReservationBean bean = new ReservationBean();
		try {
			con = pool.getConnection();
			sql = "select *  from reservation ORDER BY reserve_num DESC LIMIT 1";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bean.setReserve_num(rs.getInt(1));
				bean.setTbl_num(rs.getInt(2));
				bean.setUser_id(rs.getString(3));
				bean.setReserve_time(rs.getString(4));
				bean.setReserve_member(rs.getInt(5));
				bean.setReserve_price(rs.getInt(6));
				bean.setReserve_at(rs.getString(7));
			}
		} catch (Exception e) {
			System.out.println("Reservation Select Error");
		} finally {
			pool.freeConnection(con,pstmt,rs);
		}
		
		return bean;
	}
	
	public boolean updatePrice(int reserve_num,int price) {
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE reservation SET reserve_price = ? WHERE reserve_num = ?;";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, price);
			pstmt.setInt(2, reserve_num);
			flag = pstmt.executeUpdate() == 1 ? true : false;
		} catch (Exception e) {
			System.out.println("Reservation Insert Error");
		} finally {
			pool.freeConnection(con,pstmt);
		}
		return flag;
	}
	
	public Vector<ReservationBean> getReservedTableNum(String reserve_time){
		Vector<ReservationBean> vlist = new Vector<ReservationBean>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM reservation WHERE reserve_time = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,reserve_time);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ReservationBean bean = new ReservationBean();
				bean.setReserve_num(rs.getInt(1));
				bean.setTbl_num(rs.getInt(2));
				bean.setUser_id(rs.getString(3));
				bean.setReserve_time(rs.getString(4));
				bean.setReserve_member(rs.getInt(5));
				bean.setReserve_price(rs.getInt(6));
				bean.setReserve_at(rs.getString(7));
				vlist.add(bean);
			}
		} catch (Exception e) {
			System.out.println("Reservation Select Error");
		} finally {
			pool.freeConnection(con,pstmt,rs);
		}
		
		return vlist;
	}
	
	public int getReservationNum(String user_id) {
		ReservationBean bean = new ReservationBean();
		try {
			con = pool.getConnection();
			sql = "SELECT reserve_num FROM reservation WHERE user_id = ? AND NOT EXISTS (SELECT 1 FROM review WHERE review.review_num = reservation.reserve_num)ORDER BY reserve_num DESC LIMIT 1;";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bean.setReserve_num(rs.getInt(1));
			}
		} catch (Exception e) {
			System.out.println("Reservation Select Error");
		} finally {
			pool.freeConnection(con,pstmt,rs);
		}
		
		return bean.getReserve_num();
	}
}
