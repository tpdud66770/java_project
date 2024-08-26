package Frame;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import database.ReservationBean;
import database.ReservationMgr;
import log.OutbackApp;

public class ReserveTable extends JPanel {
	int tablePosition[][] = {{40,30},
	{410,30},
	{450,180},
	{450,300},
	{450,430},
	{200,185},
	{300,285},
	{300,410}};
	JPanel me = this;
	JPanel controlPanel = new JPanel();
	JLayeredPane tablePanel = new JLayeredPane();
	JPanel southPanel = new JPanel();
	JLabel imgBox = new JLabel("");
	JPanel backgroundPanel = new JPanel();
	JPanel foregroundPanel = new JPanel();
	
	ImageIcon backImg = new ImageIcon("Frame/Imgs/restaurantImg.png");
	RoundedTextField tf = new RoundedTextField(15);
	RoundedButton btn = new RoundedButton("메뉴 예약하기");
	JLabel label = new JLabel("선택된 좌석");
	JLabel empty = new JLabel("");
	ImageIcon img[][] = new ImageIcon[4][2];
	String imgSrc[] = {"Frame/Imgs/4.png","Frame/Imgs/4Active.png",
			"Frame/Imgs/8.png","Frame/Imgs/8Active.png",
			"Frame/Imgs/H2.png","Frame/Imgs/H2Active.png","Frame/Imgs/V2.png","Frame/Imgs/V2Active.png"};
	
	ImageIcon disableImg = new ImageIcon("Frame/Imgs/disabled2.png");
	ImageIcon reservedImg = new ImageIcon("Frame/Imgs/reserved2.png");
	ImageIcon disable2Img = new ImageIcon("Frame/Imgs/disabled.jpg");
	ImgButton tables[] = new ImgButton[8];
	String reservedTable[];
	ReservationMgr reservationMgr = new ReservationMgr();
	public ReserveTable(OutbackApp mf) {
		HeaderPanel headerPanel = new HeaderPanel(mf);
		setVisible(true);
		setLayout(new BorderLayout());
		tf.setEditable(false);
		tf.setBackground(Color.white);
		label.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,10));
		controlPanel.setLayout(new GridLayout(2,2,100,0));
		controlPanel.add(label);
		controlPanel.add(empty);
		controlPanel.add(tf);
		controlPanel.add(btn);
		Vector<ReservationBean> vlist = reservationMgr.getReservedTableNum(mf.reserveBean.getReserve_time());
		reservedTable = new String[vlist.size()];
		for(int i = 0 ; i < vlist.size(); i++) {
			reservedTable[i] = String.valueOf(vlist.get(i).getTbl_num());
		}
		southPanel.add(controlPanel);
		int x = 0;
		for (int i = 0; i < img.length; i++ ) {
			for (int j = 0; j < img[i].length; j++) {
				img[i][j] = new ImageIcon(imgSrc[x]);
				img[i][j] = resizeImg(img[i][j],100,100);
				x++;
			}
		}
		
		for (int i = 0; i < tables.length; i++) {
			boolean flag = false;
			for(int j = 0 ; j < reservedTable.length;j++) {
				if(reservedTable[j].equals(String.valueOf(i+1))) {
					flag = true;
					break;
				}
			}
				int memberNum = mf.reserveBean.getReserve_member();
				switch(i) {
			case 0: case 1:
				if(memberNum < 5) {
					tables[i] = new ImgButton(disable2Img);
					flag = true;
				}
				else if(flag) {
					tables[i] = new ImgButton(reservedImg);
				}
				else {
					tables[i] = new ImgButton(img[1][0],img[1][1],100,100);
				}
				
				break;
			case 2: case 3: case 4:
				if(memberNum > 4) {
					tables[i] = new ImgButton(disableImg);
					flag= true;
				}
				else if(memberNum == 1) {
					tables[i] = new ImgButton(disable2Img);
				}
				else if(flag) {
					tables[i] = new ImgButton(reservedImg);
				}
				else {
					tables[i] = new ImgButton(img[0][0],img[0][1]);
				}
				
				break;
			case 5:
				if(memberNum > 2) {
					tables[i] = new ImgButton(disableImg,60,60);
					flag =true;
				}
				else if(flag) {
					tables[i] = new ImgButton(reservedImg,60,60);
				}
				else {
					tables[i] = new ImgButton(img[2][0],img[2][1],100,50);
				}
				break;
			case 6: case 7:
				if(memberNum > 2) {
					tables[i] = new ImgButton(disableImg,75,75);
					flag = true;
				}
				else if(flag) {
					tables[i] = new ImgButton(reservedImg,75,75);
				}
				else {
					tables[i] = new ImgButton(img[3][0],img[3][1],50,100);
				}
				
				break;
			}
			
			tables[i].setName(String.valueOf(i+1));
			if(!flag) {
				tables[i].addMouseListener(new MouseAdapter() {
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
						// TODO Auto-generated method stub
						ImgButton obj = (ImgButton)e.getSource();
						for(int i = 0; i < tables.length ; i++) {
							tables[i].flag =false;
							tables[i].setIcon(tables[i].defaultImg);
						}
						obj.setIcon(obj.hoverImg);
						obj.flag = true;
						tf.setText(obj.getName() + "번 테이블");
						tf.setName((Integer.valueOf(obj.getName())) + "");
					}
				});
			}
		}
		backImg = resizeImg(backImg,OutbackApp.WIDTH,OutbackApp.HEIGHT -150);
		imgBox.setIcon(backImg);
		backgroundPanel.add(imgBox);
		backgroundPanel.setBackground(Color.white);
		backgroundPanel.setBounds(0,0,OutbackApp.WIDTH, OutbackApp.HEIGHT-150);
		tablePanel.add(backgroundPanel,JLayeredPane.DEFAULT_LAYER);
		
		for (int i = 0; i < tablePosition.length; i++) {
			tables[i].setBounds(tablePosition[i][0],tablePosition[i][1],150,100);
			tablePanel.add(tables[i],JLayeredPane.PALETTE_LAYER);
		}
		add(headerPanel,BorderLayout.NORTH);
		add(tablePanel);
		add(southPanel,BorderLayout.SOUTH);
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tf.getText().isEmpty()) {
					JOptionPane.showMessageDialog(mf, "테이블을 선택해주세요.");
					return;
				}
				mf.reserveBean.setTbl_num(Integer.parseInt(tf.getName()));
				mf.switchToPanel(new ReserveMenu(mf));
			}
		});
	}
	public static ImageIcon resizeImg(ImageIcon img,int width,int height) {
		Image originalImage = img.getImage();
		Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon resultImage = new ImageIcon(resizedImage);
		return resultImage;
	}
}
