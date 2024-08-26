package Frame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NoticeDialog extends JDialog {
	
	Font font = new Font("Malgun Gothic", Font.PLAIN, 12);
	
	public NoticeDialog(JDialog me,String title,String text) {
		setSize(150,100);
		setTitle(title);
		setVisible(true);
		setLocationRelativeTo(me);
		setModal(true);
		JPanel pane = new JPanel();
		JLabel label = new JLabel(text);
		label.setFont(font);
		
		pane.setPreferredSize(new Dimension(200,150));
		pane.setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS));
		pane.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.setAlignmentY(Component.CENTER_ALIGNMENT);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		pane.add(Box.createVerticalGlue());
		pane.add(label);
		pane.add(Box.createVerticalGlue());
		add(pane);
		validate();
	}
	
	public NoticeDialog(JPanel me,String title,String text) {
		setSize(150,100);
		setTitle(title);
		setVisible(true);
		setLocationRelativeTo(me);
		setModal(true);
		JPanel pane = new JPanel();
		JLabel label = new JLabel(text);
		label.setFont(font); 
		
		pane.setPreferredSize(new Dimension(200,150));
		pane.setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS));
		pane.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.setAlignmentY(Component.CENTER_ALIGNMENT);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		pane.add(Box.createVerticalGlue());
		pane.add(label);
		pane.add(Box.createVerticalGlue());
		add(pane);
		validate();
	}
}
