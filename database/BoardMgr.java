package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import java.sql.Statement;

public class BoardMgr {
    private DBConnectionMgr pool;
    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;
    String sql = "";
   
    public BoardMgr() {
        try {
            pool = DBConnectionMgr.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean insertBoard(BoardBean boardBean) {
        boolean flag = false;
        
        try {
            con = pool.getConnection();
            sql = "INSERT INTO board (board_id, board_img, board_title, board_content, board_at) VALUES (?, ?, ?, ?, now())";
            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setString(1, boardBean.getBoard_id());  
            
         // BLOB 필드에 InputStream을 사용하여 이미지 데이터를 저장
            if (boardBean.getBoard_img() != null) {
                pstmt.setBlob(2, boardBean.getBoard_img());
            } else {
                pstmt.setNull(2, java.sql.Types.BLOB);
            }
                
              
            pstmt.setString(3, boardBean.getBoard_title());    
            pstmt.setString(4, boardBean.getBoard_content()); 
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int generatedBoardNum = rs.getInt(1);
                    boardBean.setBoard_num(generatedBoardNum);
                    flag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return flag;
    }
    
    
    public Vector<BoardBean> loadPosts(String userId) {
        Vector<BoardBean> posts = new Vector<BoardBean>();
        
        try {
        	 con = pool.getConnection();
             sql = "SELECT * FROM board";
             pstmt = con.prepareStatement(sql);
             rs = pstmt.executeQuery();
             
             while (rs.next()) {
                 BoardBean post = new BoardBean();
                 post.setBoard_num(rs.getInt("board_num"));
                 post.setBoard_id(rs.getString("board_id"));
                 // BLOB 필드에서 데이터를 가져옴
                 Blob blob = rs.getBlob("board_img");
                 if (blob != null) {
                     InputStream inputStream = blob.getBinaryStream();
                     post.setBoard_img(inputStream);
                 }
                 post.setBoard_title(rs.getString("board_title"));
                 post.setBoard_content(rs.getString("board_content"));
                 post.setBoard_at(rs.getString("board_at"));
                 posts.add(post);
             }
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             pool.freeConnection(con, pstmt, rs);
         }
         
         return posts;
    }
    
    // board_id에 맞는 게시글 목록을 반환하는 메서드
    public Vector<BoardBean> getPostsByBoardId(String boardId) {
        Vector<BoardBean> posts = new Vector<>();
        
        try {
            con = pool.getConnection();
            sql = "SELECT * FROM board WHERE board_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, boardId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                BoardBean post = new BoardBean();
                post.setBoard_num(rs.getInt("board_num"));
                post.setBoard_id(rs.getString("board_id"));
                // BLOB 필드에서 데이터를 가져옴
                Blob blob = rs.getBlob("board_img");
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    post.setBoard_img(inputStream);
                }
                post.setBoard_title(rs.getString("board_title"));
                post.setBoard_content(rs.getString("board_content"));
                post.setBoard_at(rs.getString("board_at"));
                posts.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        
        return posts;
    }
    
    public Vector<BoardBean> getAllPosts() {
        Vector<BoardBean> posts = new Vector<>();
        
        try {
            con = pool.getConnection();
            sql = "SELECT * FROM board";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                BoardBean post = new BoardBean();
                post.setBoard_num(rs.getInt("board_num"));
                post.setBoard_id(rs.getString("board_id"));
                // BLOB 필드에서 데이터를 가져옴
                Blob blob = rs.getBlob("board_img");
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    post.setBoard_img(inputStream);
                }
                post.setBoard_title(rs.getString("board_title"));
                post.setBoard_content(rs.getString("board_content"));
                post.setBoard_at(rs.getString("board_at"));
                posts.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        
        return posts;
    }
    
    
    

    
}
