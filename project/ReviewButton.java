package project;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;

public class ReviewButton extends JButton {

    public ReviewButton() {
        super("리뷰");
        setBounds(350, 10, 60, 30);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setFont(new Font("Malgun Gothic", Font.BOLD, 12));
        setBorder(new EmptyBorder(0, 0, 0, 0)); // 기본 외곽선 제거
        setContentAreaFilled(false); // 배경 없애기
    }
}
