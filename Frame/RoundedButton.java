package Frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

public class RoundedButton extends JButton {
	RoundedButton me = this;
    public RoundedButton() { super(); decorate(); } 
    public RoundedButton(String text) { super(text); decorate(); }
    public RoundedButton(Action action) { super(action); decorate(); } 
    public RoundedButton(Icon icon) { super(icon); decorate(); } 
    public RoundedButton(String text, Icon icon) { super(text, icon); decorate(); } 
    protected void decorate() { setBorderPainted(false); setOpaque(false); addMouseListener(new MouseAdapter() {
    	@Override
    	public void mouseEntered(MouseEvent e) {
    		me.setLocation(getX() +1,getY()+1);
    		me.setSize(new Dimension(me.getWidth() - 2,me.getHeight() - 2));
    	}
    	@Override
    	public void mouseExited(MouseEvent e) {
    		// TODO Auto-generated method stub
    		me.setLocation(getX() -1 ,getY()-1);
    		me.setSize(new Dimension(me.getWidth() + 2,me.getHeight() + 2));
    	}
    });}
    @Override 
    protected void paintComponent(Graphics g) {
       Color c=Color.black; //배경색 결정
       Color o=Color.white;//글자색 결정
       int width = getWidth(); 
       int height = getHeight(); 
       Graphics2D graphics = (Graphics2D) g; 
       graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
       if (getModel().isArmed()) { graphics.setColor(c.darker()); } 
       else if (getModel().isRollover()) { graphics.setColor(c.brighter()); } 
       else { graphics.setColor(c); } 
       graphics.fillRoundRect(0, 0, width, height, 10, 10); 
       FontMetrics fontMetrics = graphics.getFontMetrics(); 
       Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds(); 
       int textX = (width - stringBounds.width) / 2; 
       int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent(); 
       graphics.setColor(o); 
       graphics.setFont(getFont()); 
       graphics.drawString(getText(), textX, textY); 
       graphics.dispose(); 
       super.paintComponent(g); 
       }
    
    }