package Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import database.MenuMgr;
import log.OutbackApp;

public class ListDialog extends JDialog{
	
	HashMap<Integer,Integer> list = new HashMap<Integer, Integer>();
	ReserveMenu owner;
	ListDialog me = this;
	Set<Integer> key;
	MenuMgr mgr = new MenuMgr();
	OutbackApp mainframe;
	int size = 0;
	int frameX;
    int frameY;
    JPanel navBox = new DialogNavPanel("장바구니",this);
	
    // 폰트 설정
    Font font = new Font("Malgun Gothic", Font.PLAIN, 12);
    Font labelFont = new Font("Malgun Gothic", Font.BOLD, 15);

	public ListDialog(ReserveMenu owner) {
		this.owner = owner;
		this.mainframe = owner.mainframe;
		setUndecorated(true);
		getRootPane().setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		frameX = mainframe.getX();
	    frameY = mainframe.getY();
	    setLocation(frameX + 550, frameY + 100);
		getContentPane().setBackground(Color.white);
	}

	public void reprintDialog() {
		frameX = mainframe.getX();
	    frameY = mainframe.getY();
	    setLocation(frameX + 550, frameY + 100);
		getContentPane().removeAll();
	    this.list = owner.list;
		key = list.keySet();
		size = list.size();
		JPanel innerBox = new JPanel();
		innerBox.setBackground(Color.white);
		innerBox.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		if(size == 0) {
			JLabel empty = new JLabel("장바구니가 비어있습니다");
			empty.setPreferredSize(new Dimension(200,40));
			empty.setFont(font);  // 폰트 설정
			innerBox.add(empty);
		}
		
		JPanel labelBox[] = new JPanel[size];
		JLabel menuName[] = new JLabel[size];
		JLabel amount[] = new JLabel[size];
		int idx = 0;
		
		for (Integer menuNum : key) {
			String menuNameStr = mgr.getMenuName(menuNum);
			menuName[idx] = new JLabel(menuNameStr);
			menuName[idx].setFont(font);  // 폰트 설정
			amount[idx] = new JLabel(String.valueOf(list.get(menuNum) + "개"));
			amount[idx].setFont(font);  // 폰트 설정
			labelBox[idx] = new JPanel();
			
			ImageIcon xIcon = new ImageIcon("Frame/Imgs/x.png");
			Image scaledXImage = xIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
			ImageIcon scaledXIcon = new ImageIcon(scaledXImage);
			JLabel deleteMenu = new JLabel(scaledXIcon);
			deleteMenu.setHorizontalAlignment(JLabel.CENTER);
			deleteMenu.setPreferredSize(new Dimension(15,15));
			
			labelBox[idx].setLayout(new FlowLayout(FlowLayout.LEFT));
			labelBox[idx].add(deleteMenu);
			labelBox[idx].setBackground(Color.white);
			labelBox[idx].setPreferredSize(new Dimension(300,30));
			labelBox[idx].add(menuName[idx]);
			labelBox[idx].add(amount[idx]);
			
			ImageIcon plusIcon = new ImageIcon("Frame/Imgs/plus.png");
			ImageIcon minusIcon = new ImageIcon("Frame/Imgs/minus.png");
			
			Image scaledPlusImage = plusIcon.getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH);
			Image scaledMinusImage = minusIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
			
			ImageIcon scaledPlusIcon = new ImageIcon(scaledPlusImage);
			ImageIcon scaledMinusIcon = new ImageIcon(scaledMinusImage);
			
			JLabel pushMenu = new JLabel(scaledPlusIcon);
			pushMenu.setFont(font);  // 폰트 설정
			JLabel popMenu = new JLabel(scaledMinusIcon);
			popMenu.setFont(font);  // 폰트 설정
			
			pushMenu.setPreferredSize(new Dimension(15,15));
			popMenu.setPreferredSize(new Dimension(15,15));
			
			deleteMenu.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					list.remove(menuNum);
					repaint();
					reprintDialog();
				}
			});
			pushMenu.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					list.put(menuNum, list.get(menuNum)+1);
					repaint();
					reprintDialog();
				}
			});
			popMenu.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					list.put(menuNum, list.get(menuNum)-1);
					if(list.get(menuNum) == 0) {
						list.remove(menuNum);
					}
					repaint();
					reprintDialog();
					owner.listDialog.setVisible(true);
				}
			});
			
			labelBox[idx].add(pushMenu);
			labelBox[idx].add(popMenu);
			innerBox.add(labelBox[idx]);
			idx++;
		}
		add(navBox, BorderLayout.NORTH);
		add(innerBox);
		validate();
		int height = 50 + list.size() * 35;
		setSize(350,height);
	}
}
