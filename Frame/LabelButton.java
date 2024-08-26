package Frame;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import log.OutbackApp;

public class LabelButton extends JLabel{
	
	JPanel switchPanel;
	OutbackApp mainframe;
	Color defaultColor = Color.black;
	Color activeColor = Color.gray;
	LabelButton(OutbackApp mainframe,JPanel switchPanel,String text){
		setText(text);
		this.mainframe = mainframe;
		this.switchPanel = switchPanel;
		setForeground(defaultColor);
		addMouseListener(new MouseHandler());
	}
	public class MouseHandler extends MouseAdapter{
		
		@Override
		public void mouseEntered(MouseEvent e) {
			setForeground(activeColor);
		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			setForeground(defaultColor);
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				mainframe.switchToPanel(switchPanel);
			} catch (Exception e2) {
				System.out.println("ERROR IN SWITCHPANEL");
			}
			
		}
	}
	
}

