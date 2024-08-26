package log;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import javax.swing.Icon;

public class StarIcon implements Icon {
    private int width;
    private int height;
    private Color color;
    private Color lineColor;

    public StarIcon(int width, int height, Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
        this.lineColor = Color.BLACK;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double cx = x + width / 2.0;
        double cy = y + height / 2.0;
        double r = Math.min(width, height) / 2.0;
        double innerR = r * 0.5;

        GeneralPath path = new GeneralPath();
        for (int i = 0; i < 10; i++) {
            double angle = Math.toRadians((i * 36) - 90);
            double len = (i % 2 == 0) ? r : innerR;
            double px = cx + Math.cos(angle) * len;
            double py = cy + Math.sin(angle) * len;
            if (i == 0) {
                path.moveTo(px, py);
            } else {
                path.lineTo(px, py);
            }
        }
        path.closePath();

        g2d.setColor(color);
        
        g2d.fill(path);
        
        g2d.setColor(lineColor);
        
        g2d.draw(path);
        
        g2d.dispose();
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }
}