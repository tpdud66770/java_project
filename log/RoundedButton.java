package log;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

public class RoundedButton extends JButton {
    RoundedButton me = this;

    public RoundedButton() { 
        super(); 
        decorate(); 
    } 

    public RoundedButton(String text) { 
        super(text); 
        decorate(); 
    }

    public RoundedButton(Action action) { 
        super(action); 
        decorate(); 
    } 

    public RoundedButton(Icon icon) { 
        super(icon); 
        decorate(); 
    } 

    public RoundedButton(String text, Icon icon) { 
        super(text, icon); 
        decorate(); 
    }

    protected void decorate() {
        // 모든 버튼의 테두리와 불투명성을 설정
        setBorderPainted(false);
        setOpaque(false);

        // 버튼 텍스트가 "게시판", "리뷰", "로그아웃"이 아니면 마우스 이벤트 추가
        if (!isExcludedButton()) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    me.setLocation(getX() +1,getY()+1);
                    me.setSize(new Dimension(me.getWidth() - 2,me.getHeight() - 2));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    me.setLocation(getX() -1 ,getY()-1);
                    me.setSize(new Dimension(me.getWidth() + 2,me.getHeight() + 2));
                }
            });
        }
    }

    private boolean isExcludedButton() {
        String text = getText();
        return "게시판".equals(text) || "리뷰".equals(text) || "로그아웃".equals(text);
    }

    @Override 
    protected void paintComponent(Graphics g) {
        // "게시판", "리뷰", "로그아웃" 버튼이 아닌 경우에만 커스텀 그래픽 적용
        if (!isExcludedButton()) {
            Color c = Color.black; // 배경색
            Color o = Color.white; // 글자색
            int width = getWidth(); 
            int height = getHeight(); 
            Graphics2D graphics = (Graphics2D) g; 
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
            if (getModel().isArmed()) { 
                graphics.setColor(c.darker()); 
            } else if (getModel().isRollover()) { 
                graphics.setColor(c.brighter()); 
            } else { 
                graphics.setColor(c); 
            } 
            graphics.fillRoundRect(0, 0, width, height, 10, 10); 
            FontMetrics fontMetrics = graphics.getFontMetrics(); 
            Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds(); 
            int textX = (width - stringBounds.width) / 2; 
            int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent(); 
            graphics.setColor(o); 
            graphics.setFont(getFont()); 
            graphics.drawString(getText(), textX, textY); 
            graphics.dispose(); 
        } else {
            super.paintComponent(g); // "게시판", "리뷰", "로그아웃" 버튼은 기본 렌더링 사용
        }
    }
}
