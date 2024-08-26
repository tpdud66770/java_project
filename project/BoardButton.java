package project;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardButton extends JButton {
	
	private final Font font = new Font("Malgun Gothic", Font.PLAIN, 14);
    public BoardButton() {
        super("게시판");
        setBounds(400, 10, 60, 30);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setFont(font);
        setBorder(new EmptyBorder(0, 0, 0, 0)); // 기본 외곽선 제거
        setContentAreaFilled(false); // 배경 없애기
        
        
    }
}