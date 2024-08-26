package Frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import database.MenuBean;
import database.MenuMgr;

public class MenuItem extends JPanel{
	public ImageIcon menuImg;
	public int menuNum;
	public int menuPrice;
	public String menuName;
	public ImageIcon pushBtnIcon = new ImageIcon("Frame/Imgs/pushBtn.png");
	public ImageIcon pushBtnIconHover = new ImageIcon("Frame/Imgs/pushBtnActive.png");
	public ImgButton pushBtn = new ImgButton(pushBtnIcon,pushBtnIconHover,30,30);
	
	private final Font font = new Font("Malgun Gothic", Font.PLAIN, 13);
	private final Font font2 = new Font("Malgun Gothic", Font.PLAIN, 11);
	
	public MenuItem(MenuBean bean , ReserveMenu frame) {
		this.menuImg = new ImageIcon(bean.getMenu_image());
		this.menuImg = ImgButton.resizeImg(this.menuImg, 100, 100);
		this.menuNum = bean.getMenu_num();
		this.menuName = bean.getMenu_name();
		this.menuPrice = bean.getMenu_price();
		setVisible(true);
		setBackground(Color.white);
		setLayout(null);
		JLabel bestFlag = new JLabel();
		
		for(int i = 0 ; i < frame.topMenus.length ; i++) {
			if(frame.topMenus[i].getMenu_num() == bean.getMenu_num()) {
				ImageIcon bestIcon = new ImageIcon("Frame/Imgs/Best.png");
				bestIcon = ImgButton.resizeImg(bestIcon, 30, 30);
				bestFlag.setBounds(100,10,30,30);
				bestFlag.setIcon(bestIcon);
				add(bestFlag);
			}
		}
		JLabel imgBox = new JLabel();
		JPanel labelBox = new JPanel();
		labelBox.setLayout(new GridLayout(2,1));
		labelBox.setBackground(getBackground());
		JLabel nameLabel = new JLabel(menuName);
		nameLabel.setFont(font);
		JLabel priceLabel = new JLabel(String.valueOf(menuPrice) + "원");
		priceLabel.setForeground(Color.gray);
		priceLabel.setFont(font2);
		imgBox.setIcon(this.menuImg);
		imgBox.setBounds(30,10,100,100);
		add(imgBox);
        labelBox.setPreferredSize(new Dimension(100, 30));
		labelBox.add(nameLabel);
		labelBox.add(priceLabel);
		labelBox.setBounds(160,30,200,50);
		add(labelBox);
		pushBtn.setBounds(370,15,80,80);
		add(pushBtn);
		pushBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ImgButton obj = (ImgButton)e.getSource();
				obj.setIcon(obj.hoverImg);
				// TODO Auto-generated method stub
			
			}
			@Override
			public void mouseExited(MouseEvent e) {
				ImgButton obj = (ImgButton)e.getSource();
				if(!obj.flag) {
					obj.setIcon(obj.defaultImg);
				}
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.list.put(menuNum,frame.list.get(menuNum) != null ? frame.list.get(menuNum) + 1 : 1);
				frame.listDialog.reprintDialog();
				frame.listDialog.setVisible(true);
			}
		});
		validate();
	}
	
}
