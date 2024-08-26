package log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;

public class InsertReviewIntoDatabase {

    public InsertReviewIntoDatabase() {
		
	}

	// 리뷰 데이터를 데이터베이스에 삽입하는 메소드
    public void insertReview(String reviewId, String reviewText, int rating, String reviewImage) {
        String sql = "INSERT INTO review (review_id, review_content, review_score, review_image, review_at) VALUES (?, ?, ?, ?, NOW())";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, reviewId);
            pstmt.setString(2, reviewText);
            pstmt.setInt(3, rating);
            pstmt.setString(4, reviewImage);
          
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();  // SQL 에러가 발생하면 로그에 출력합니다.
        }
    }
}
