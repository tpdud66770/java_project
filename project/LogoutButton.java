package project;

import java.awt.Color;
import java.awt.Font;

public class LogoutButton extends RoundedButton {

    public LogoutButton() {
        super("로그아웃");
        setBounds(470, 10, 60, 30);
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setFont(new Font("Malgun Gothic", Font.BOLD, 12));
    }
}
