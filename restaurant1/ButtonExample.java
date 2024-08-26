package restaurant1;

import javax.swing.*;
import java.awt.*;

public class ButtonExample extends JButton {
    public ButtonExample() {
        setPreferredSize(new Dimension(50, 50));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // 배경색을 흰색으로 설정
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        // 30x30 크기의 검은색 사각형을 그리기
        g.setColor(Color.BLACK);
        g.fillRect(10, 10, 30, 30); // (10, 10) 위치에 30x30 사각형 그리기
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Custom Button Example");
        ButtonExample button = new ButtonExample();
        
        // 버튼의 기본 배경색을 흰색으로 설정
        button.setBackground(Color.WHITE);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.add(button);
        frame.pack();
        frame.setVisible(true);
    }
}