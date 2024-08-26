package restaurant1;


import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JCheckBox;

public class RoundedCheckBox extends JCheckBox {

    public RoundedCheckBox() { 
        super(); 
        decorate(); 
    } 
    
    public RoundedCheckBox(String text) { 
        super(text); 
        decorate(); 
    }
    
    protected void decorate() {
        setOpaque(false); // 배경 투명 설정
    }

    @Override
    protected void paintComponent(Graphics g) {
        Color c = Color.black; // 배경색 결정
        Color o = Color.white; // 글자색 결정
        Color checkMarkColor = Color.white; // 체크 표시 색상
        
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 체크박스의 배경색 결정
        if (getModel().isArmed()) {
            graphics.setColor(c.darker());
        } else if (getModel().isRollover()) {
            graphics.setColor(c.brighter());
        } else {
            graphics.setColor(c);
        }

        // 라운드된 사각형 배경 그리기
        graphics.fillRoundRect(0, 0, width, height, 10, 10);

        // 텍스트 그리기
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds();
        int textX = (width - stringBounds.width) / 2;
        int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();
        graphics.setColor(o);
        graphics.setFont(getFont());
        graphics.drawString(getText(), textX, textY);

        // 체크 상태일 경우 체크 표시 그리기
        if (isSelected()) {
            graphics.setColor(checkMarkColor);
            int checkSize = Math.min(width, height) / 2; // 체크 표시 크기
            int checkX = (width - checkSize) / 2;
            int checkY = (height - checkSize) / 2;
            graphics.drawLine(checkX, checkY + checkSize / 2, checkX + checkSize / 2, checkY + checkSize);
            graphics.drawLine(checkX + checkSize / 2, checkY + checkSize, checkX + checkSize, checkY);
        }

        graphics.dispose();
        super.paintComponent(g);
    }
}
