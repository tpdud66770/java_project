package Frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DialogNavPanel extends JPanel{
	private final Font font = new Font("Malgun Gothic", Font.PLAIN, 20);
	public DialogNavPanel(String title,JDialog parent) {
		setLayout(null);
		setPreferredSize(new Dimension(350, 40));
		setBackground(Color.white);
		JLabel titleLabel = new JLabel(title);
		
		ImageIcon xIcon = new ImageIcon("Frame/Imgs/x.png");
		Image scaledXImage = xIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon scaledXIcon = new ImageIcon(scaledXImage);
		JLabel deleteBtn = new JLabel(scaledXIcon);
		deleteBtn.setHorizontalAlignment(JLabel.CENTER);
		deleteBtn.setPreferredSize(new Dimension(16,16));
		
		titleLabel.setFont(font);
		titleLabel.setBounds(10,0,300,40);
		deleteBtn.setBounds(320,10,15,15);
		add(titleLabel);
		add(deleteBtn);
		deleteBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parent.setVisible(false);
			}
		});
		
		
	
	}
	
}
