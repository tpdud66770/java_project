package database;

import java.io.InputStream;
import java.io.Serializable;

public class BoardBean implements Serializable {
     private int board_num;
     private String board_title;
     private String board_content;
     private String board_id; 
     private String board_at;
     private InputStream board_img;

     public int getBoard_num() {
         return board_num;
     }

     public void setBoard_num(int board_num) {
         this.board_num = board_num;
     }

     public String getBoard_id() {  
         return board_id;
     }

     public void setBoard_id(String board_id) {  
         this.board_id = board_id;
     }

     public String getBoard_at() {
         return board_at;
     }

     public void setBoard_at(String board_at) {
         this.board_at = board_at;
     }

     public String getBoard_title() {
         return board_title;
     }

     public void setBoard_title(String board_title) {
         this.board_title = board_title;
     }

     public String getBoard_content() {
         return board_content;
     }

     public void setBoard_content(String board_content) {
         this.board_content = board_content;
     }

     public InputStream getBoard_img() {
         return board_img;
     }

     public void setBoard_img(InputStream board_img) {
         this.board_img = board_img;
     }
}
