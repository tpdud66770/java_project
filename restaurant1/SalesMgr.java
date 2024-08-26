package restaurant1;

import java.nio.file.attribute.AclEntry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class SalesMgr {
	private DBConnectionMgr pool;

	public SalesMgr() {
		pool = DBConnectionMgr.getInstance();
	}

	public Vector<SalesBean> todaySales() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<SalesBean> vlist = new Vector<SalesBean>();
		try {
			con = pool.getConnection();
			sql = "SELECT \r\n" + "    TIME_LIST.sales_time, \r\n"
					+ "    COALESCE(SUM(s.sales_price), 0) AS total_sales_price\r\n" + "FROM \r\n"
					+ "    (SELECT '11:00:00' AS sales_time UNION ALL\r\n" + "     SELECT '13:00:00' UNION ALL\r\n"
					+ "     SELECT '15:00:00' UNION ALL\r\n" + "     SELECT '17:00:00' UNION ALL\r\n"
					+ "     SELECT '19:00:00' UNION ALL\r\n" + "     SELECT '21:00:00' UNION ALL\r\n"
					+ "     SELECT '23:00:00') AS TIME_LIST\r\n" + "LEFT JOIN \r\n"
					+ "    (SELECT DATE_FORMAT(sales_at, '%H:00:00') AS sales_time, sales_price\r\n"
					+ "     FROM sales \r\n" + "     WHERE DATE(sales_at) = CURDATE()  -- 오늘 날짜 조건 추가\r\n"
					+ "       AND DATE_FORMAT(sales_at, '%H:%i:%s') IN ('11:00:00', '13:00:00', '15:00:00', \r\n"
					+ "                                                 '17:00:00', '19:00:00', '21:00:00', \r\n"
					+ "                                                 '23:00:00')) AS s \r\n"
					+ "ON TIME_LIST.sales_time = s.sales_time\r\n" + "GROUP BY TIME_LIST.sales_time\r\n"
					+ "ORDER BY TIME_LIST.sales_time;";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				SalesBean bean = new SalesBean();
				bean.setSales_total_price(rs.getInt(2));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;

	}

	public Vector<SalesBean> weekSales() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<SalesBean> vlist = new Vector<SalesBean>();
		try {
			con = pool.getConnection();
			sql = "SELECT \r\n"
					+ "    WEEKDAY_LIST.weekday_name, \r\n"
					+ "    CASE \r\n"
					+ "        WHEN WEEKDAY(CURDATE()) >= WEEKDAY_LIST.weekday_order \r\n"
					+ "        THEN COALESCE(SUM(s.sales_price), 0)\r\n"
					+ "        ELSE 0\r\n"
					+ "    END AS total_sales_price\r\n"
					+ "FROM \r\n"
					+ "    (SELECT 0 AS weekday_order, 'Monday' AS weekday_name UNION ALL\r\n"
					+ "     SELECT 1, 'Tuesday' UNION ALL\r\n"
					+ "     SELECT 2, 'Wednesday' UNION ALL\r\n"
					+ "     SELECT 3, 'Thursday' UNION ALL\r\n"
					+ "     SELECT 4, 'Friday' UNION ALL\r\n"
					+ "     SELECT 5, 'Saturday' UNION ALL\r\n"
					+ "     SELECT 6, 'Sunday') AS WEEKDAY_LIST\r\n"
					+ "LEFT JOIN \r\n"
					+ "    (SELECT WEEKDAY(sales_at) AS weekday_order, sales_price\r\n"
					+ "     FROM sales \r\n"
					+ "     WHERE DATE(sales_at) BETWEEN CURDATE() - INTERVAL WEEKDAY(CURDATE()) DAY \r\n"
					+ "                             AND CURDATE()) AS s \r\n"
					+ "ON WEEKDAY_LIST.weekday_order = s.weekday_order\r\n"
					+ "GROUP BY WEEKDAY_LIST.weekday_name, WEEKDAY_LIST.weekday_order\r\n"
					+ "ORDER BY WEEKDAY_LIST.weekday_order;\r\n"
					+ "";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				SalesBean bean = new SalesBean();
				bean.setSales_total_price(rs.getInt(2));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;

	}

	public Vector<SalesBean> monthSales() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<SalesBean> vlist = new Vector<SalesBean>();
		try {
			con = pool.getConnection();
			sql = "SELECT \r\n"
					+ "    DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL -(7 - n) MONTH), '%Y-%m') AS sales_month,\r\n"
					+ "    COALESCE(SUM(s.sales_price), 0) AS total_sales_price\r\n"
					+ "FROM \r\n"
					+ "    (SELECT 1 AS n UNION ALL\r\n"
					+ "     SELECT 2 UNION ALL\r\n"
					+ "     SELECT 3 UNION ALL\r\n"
					+ "     SELECT 4 UNION ALL\r\n"
					+ "     SELECT 5 UNION ALL\r\n"
					+ "     SELECT 6 UNION ALL\r\n"
					+ "     SELECT 7) AS MONTH_LIST\r\n"
					+ "LEFT JOIN \r\n"
					+ "    (SELECT DATE_FORMAT(sales_at, '%Y-%m') AS sales_month, sales_price\r\n"
					+ "     FROM sales) AS s\r\n"
					+ "ON DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL -(7 - n) MONTH), '%Y-%m') = s.sales_month\r\n"
					+ "GROUP BY sales_month, n\r\n"
					+ "ORDER BY n;";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				SalesBean bean = new SalesBean();
				bean.setSales_total_price(rs.getInt(2));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;

	}
}
