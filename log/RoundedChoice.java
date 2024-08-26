package log;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedChoice extends JComboBox<String> {
    private Shape shape;

    public RoundedChoice(String[] items) {
        super(items);
        setOpaque(false); // 배경을 투명하게 설정
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // 내부 여백 설정
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15); // 둥근 배경 그리기
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15); // 둥근 테두리 그리기
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        }
        return shape.contains(x, y);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        String[] items = {"Option 1", "Option 2", "Option 3"};
        RoundedChoice roundedChoice = new RoundedChoice(items);
        roundedChoice.setBackground(Color.WHITE); // 배경 색상 설정
        roundedChoice.setForeground(Color.BLACK); // 텍스트 색상 설정

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.add(roundedChoice);
        frame.setSize(300, 100);
        frame.setVisible(true);
    }
}
