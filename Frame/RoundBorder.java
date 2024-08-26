package Frame;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

public class RoundBorder implements Border {
	
	int radius;
	public RoundBorder(int radius) {
		this.radius = radius;
	}
	
	@Override
	public Insets getBorderInsets(Component c) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isBorderOpaque() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		
		g.drawRoundRect(x, y, width-1, height-1, radius, radius);
	}
	
}
