package log;

import java.awt.*;
import javax.swing.*;

public class RoundedCheckBox extends JCheckBox {

    private static final int ARC_WIDTH = 10;
    private static final int ARC_HEIGHT = 10;
    private static final Color CHECK_MARK_COLOR = new Color(0, 123, 255);  // Bright blue color for the check mark

    public RoundedCheckBox(String text) {
        super(text);
        decorate();
    }

    protected void decorate() {
        setOpaque(false); // 배경 투명 설정
        setForeground(Color.BLACK); // Set text color to black for visibility
        setFocusPainted(false); // Do not highlight the border on focus
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand on hover
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background circle on check
        if (isSelected()) {
            graphics.setColor(CHECK_MARK_COLOR);
            graphics.fillRoundRect(0, 0, width, height, ARC_WIDTH, ARC_HEIGHT);
        }

        super.paintComponent(g);

        // Draw the check mark
        if (isSelected()) {
            drawCheckMark(graphics);
        }
    }

    private void drawCheckMark(Graphics2D g) {
        int boxWidth = Math.min(getWidth(), getHeight()) / 2;
        int pad = boxWidth / 4;
        int x1 = getWidth() / 2 - pad;
        int y1 = getHeight() / 2;
        int x2 = getWidth() / 2;
        int y2 = getHeight() / 2 + pad;
        int x3 = getWidth() / 2 + pad * 2;
        int y3 = getHeight() / 2 - pad;

        g.setColor(Color.WHITE); // 체크 마크 색상 설정
        g.setStroke(new BasicStroke(2)); // 체크 마크 굵기 설정
        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x2, y2, x3, y3);
    }
}
