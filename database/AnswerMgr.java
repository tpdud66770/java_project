package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class AnswerMgr {
	private DBConnectionMgr pool;
    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;
    String sql = "";
    
    
    public AnswerMgr() {
    	try {
            pool = DBConnectionMgr.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
    
   
    
    
    public Vector<AnswerBean> loadComments(int board_num) {
    	
        Vector<AnswerBean> comments = new Vector<AnswerBean>();
        
        try {
			con = pool.getConnection();
			sql = "select * from answer where board_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();
			
			 while (rs.next()) {
                 AnswerBean comment = new AnswerBean();
                 comment.setAnswer_num(rs.getInt("answer_num"));
                 comment.setBoard_num(rs.getInt("board_num"));
                 comment.setAnswer_id(rs.getString("answer_id"));
                 comment.setAnswer_content(rs.getString("answer_content"));
                 comment.setAnswer_at(rs.getString("answer_at"));

                 comments.add(comment);
             }
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con,pstmt,rs);
		}
		return comments;
       

       
    }
    
    
    public boolean insertComment(AnswerBean comment) {
        boolean result = false;
        try {
            con = pool.getConnection();
            sql = "INSERT INTO answer (board_num, answer_id, answer_content, answer_at) VALUES (?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, comment.getBoard_num());
            pstmt.setString(2, comment.getAnswer_id());
            pstmt.setString(3, comment.getAnswer_content());
            pstmt.setString(4, comment.getAnswer_at());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return result;
    }
    
    

}
