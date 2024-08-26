package Frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import database.BoardBean;
import database.BoardMgr;
import database.UserMgr;
import log.OutbackApp;
import log.ReviewMain;
import project.BoardMain;
import restaurant1.manager_main;

public class HeaderPanel extends JPanel{
	ImageIcon logo = new ImageIcon("Frame/Imgs/outback_logo.png");
	ImageIcon hoverLogo;
	JLabel mainBtn = new JLabel();
	JLabel boardBtn = new JLabel("게시판");
	JLabel reviewBtn = new JLabel("리뷰");
	JLabel pointLabel;
	RoundedButton logoutBtn = new RoundedButton("로그아웃");
	OutbackApp mainframe;
	
	Font font = new Font("Malgun Gothic", Font.PLAIN, 12);
	
	public HeaderPanel(OutbackApp mainframe) {
		logo = ReserveTable.resizeImg(logo, 100, 80);
		hoverLogo = ReserveTable.resizeImg(logo, 92, 75);
		pointLabel = new JLabel("마일리지 : " + mainframe.userPoint + " 원");
		this.mainframe = mainframe;
		updateUserPoint();
		setBackground(Color.white);
		setLayout(null);
		
		mainBtn.setFont(font);
        boardBtn.setFont(font);
        reviewBtn.setFont(font);
        pointLabel.setFont(font);
        logoutBtn.setFont(font);
		
		
		reviewBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Vector<BoardBean> savedPosts = new BoardMgr().getPostsByBoardId(mainframe.userId);
		        if(savedPosts != null) {
		        	mainframe.reviewMain.updateReviewPanel();
		        	mainframe.switchToPanel(mainframe.reviewMain);
		        }
			}
		});
		boardBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Vector<BoardBean> savedPosts = new BoardMgr().getPostsByBoardId(mainframe.userId);
				if(mainframe.isManager) {
					savedPosts = new BoardMgr().getAllPosts();
				}
				else {
					savedPosts = new BoardMgr().getPostsByBoardId(mainframe.userId);
				}
		        if(savedPosts != null) {
		        	mainframe.boardMain.updatePosts(savedPosts);
		        }
		        mainframe.switchToPanel(mainframe.boardMain);
		        updateUserPoint();
			}
		});
		
		mainBtn.setBounds(20, 13, 110, 85);
		mainBtn.setIcon(logo);
		reviewBtn.setBounds(220,13,50,75);
		boardBtn.setBounds(270,13,50,75);
		pointLabel.setBounds(345,13,200,75);
		logoutBtn.setBounds(475,42,60,20);
		setPreferredSize(new Dimension(OutbackApp.WIDTH,80));
		add(mainBtn);
		mainBtn.addMouseListener(new BtnHandelr());
		add(reviewBtn);
		reviewBtn.addMouseListener(new BtnHandelr());
		add(boardBtn);
		boardBtn.addMouseListener(new BtnHandelr());
		add(pointLabel);
		if(mainframe.userId != "") {add(logoutBtn);}
		
		logoutBtn.addMouseListener(new BtnHandelr());
		setVisible(true);
	}
	public void updateUserPoint() {
		mainframe.userPoint =  new UserMgr().getUserPoint(mainframe.userId);
		pointLabel.setText("마일리지 : " + mainframe.userPoint + " 원");
	}
	public class BtnHandelr extends MouseAdapter{
	
		
		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == mainBtn) {
				JLabel obj = (JLabel)e.getSource();
				obj.setIcon(hoverLogo);
			}
			else if(e.getSource().equals(reviewBtn) || e.getSource().equals(boardBtn)) {
				JLabel obj = (JLabel)e.getSource();
				obj.setForeground(Color.gray);
			}
			else {
				return;
			}
		}
		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getSource() == mainBtn) {
				JLabel obj = (JLabel)e.getSource();
				obj.setIcon(logo);
			}
			else if(e.getSource() == reviewBtn || e.getSource() == boardBtn) {
				JLabel obj = (JLabel)e.getSource();
				obj.setForeground(Color.black);
			}
			else {
				return;
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			Object obj = e.getSource();
			if(obj == mainBtn) {
				if(mainframe.isManager) {
					mainframe.switchToPanel(new manager_main(mainframe));
				}
				else if(mainframe.userId == ""){
					mainframe.switchToPanel(mainframe.cardPanel);
					mainframe.showPanel(mainframe.cardPanel, "Home");
				}
				else {
					mainframe.switchToPanel(mainframe.cardPanel);
					mainframe.showPanel(mainframe.cardPanel, "ReservationSystem");
				}
				
			}
			else if(obj == logoutBtn) 
			{
				mainframe.switchToPanel(mainframe.cardPanel);
				mainframe.isManager = false;
				mainframe.userId = "";
				mainframe.showPanel(mainframe.cardPanel, "Home");
				
			}
		}
	}

}
