package restaurant1;

import java.nio.file.attribute.AclEntry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;


public class MenuMgr {
	private DBConnectionMgr pool;

	public MenuMgr() {
		pool = DBConnectionMgr.getInstance();
	}

	public MenuBean getMenu(int menu_num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		MenuBean bean = new MenuBean();
		try {
			con = pool.getConnection();
			sql = "select * from menu where display_order = ?";
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
	
	public boolean insertMenu(MenuBean bean) {
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql=null;
		boolean flag=false;
		try {
			con=pool.getConnection();
			sql="insert into menu (menu_name, menu_type, menu_price,  menu_image) values (?, ?, ?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, bean.getMenu_name());
			pstmt.setString(2, bean.getMenu_type());
			pstmt.setInt(3, bean.getMenu_price());
			pstmt.setString(4, bean.getMenu_image());
			int cnt=pstmt.executeUpdate();
			if(cnt==1)flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con,pstmt);
		}
		return flag;
	}
	
	public boolean updateMenu(MenuBean bean) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    boolean flag = false;
	    try {
	        con = pool.getConnection();
	        String sql = "UPDATE menu SET menu_name = ?, menu_type = ?, menu_price = ?, menu_image = ? WHERE display_order = ?";
	        pstmt = con.prepareStatement(sql);
	        
	        pstmt.setString(1, bean.getMenu_name());
	        pstmt.setString(2, bean.getMenu_type());
	        pstmt.setInt(3, bean.getMenu_price());
	        pstmt.setString(4, bean.getMenu_image());
	        pstmt.setInt(5, bean.getMenu_num()); // 여기에 올바른 메뉴 번호를 설정
	        
	        // 쿼리 실행
	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected == 1) {
	            flag = true;
	        } else {
	            System.out.println("Update failed. No rows affected.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt);
	    }
	    return flag; 
	}

	
	public boolean deleteMenu(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag=false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM menu WHERE display_order = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			if(pstmt.executeUpdate()==1)
				flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	public boolean updatedel(MenuBean bean) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    String sql = null;
	    boolean flag = false;
	    try {
	        con = pool.getConnection();
	        sql = "UPDATE menu SET display_order = display_order - 1 WHERE display_order > ?;";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, bean.getMenu_num()); // Assuming you're trying to decrement display_order based on menu_num
	        if (pstmt.executeUpdate() > 0) flag = true; // Check if more than 0 rows were updated
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt);
	    }
	    return flag;
	}

	
}
