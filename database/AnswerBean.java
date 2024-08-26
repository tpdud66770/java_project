package database;

import java.sql.Date;

public class AnswerBean {

	private int answer_num;
	private int board_num;
	private String answer_id;
	private String answer_content;
	private String answer_at;
	
	
	public int getAnswer_num() {
		return answer_num;
	}
	
	public void setAnswer_num(int answer_num) {
		this.answer_num = answer_num;
	}
	
	public int getBoard_num() {
        return board_num;
    }

    public void setBoard_num(int board_num) {
        this.board_num = board_num;
    }
	
    public String getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(String answer_id) {
        this.answer_id = answer_id;
    }

    public String getAnswer_content() {
        return answer_content;
    }

    public void setAnswer_content(String answer_content) {
        this.answer_content = answer_content;
    }

    public String getAnswer_at() {
        return answer_at;
    }

    public void setAnswer_at(String answer_at) {
        this.answer_at = answer_at;
    }
    
}
