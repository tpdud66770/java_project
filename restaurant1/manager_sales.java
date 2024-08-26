package restaurant1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import Frame.HeaderPanel;
import log.OutbackApp;

import javax.swing.JLabel;

public class manager_sales extends JPanel implements ActionListener{
	ImageIcon logo;
    JButton btn1, btn2,btn3;
    RoundedButton logOutBtn;
    JPanel jp,jp1,jp2;
    JLabel label1,label2,label3,imglabel;
    public Font font = new Font("Malgun Gothic", Font.PLAIN, 20);


    public manager_sales(OutbackApp mainframe) {
        this.setLayout(null); 

        jp = new JPanel();
        jp.setLayout(null);
        jp.setBounds(0, 0, 584, 836); //메인 패널 위치와 크기
        jp.setBackground(Color.white);
        
        jp1 = new JPanel();
        jp1.setLayout(null);
        jp1.setBounds(10, 120, 484, 800); 
        jp1.setBackground(Color.white);
        
        label1=new JLabel("ⓘ");
        label1.setFont(font);
        label1.setBounds(195, 78, 30, 50);
        label2=new JLabel("ⓘ");
        label2.setFont(font);
        label2.setBounds(195, 228, 30, 50);
        label3=new JLabel("ⓘ");
        label3.setFont(font);
        label3.setBounds(195, 378, 30, 50);
        
        btn1=new JButton("당일 매출");
        btn1.setFont(font);
        btn1.setBorderPainted(false);
        btn1.setContentAreaFilled(false);
        btn1.setFocusPainted(false);
        btn1.setBounds(215, 80, 144, 50);
        
        btn2=new JButton("주별 매출");
        btn2.setFont(font);
        btn2.setBorderPainted(false);
        btn2.setContentAreaFilled(false);
        btn2.setFocusPainted(false);
        btn2.setBounds(215, 230, 144, 50);
        
        btn3=new JButton("월별 매출");
        btn3.setFont(font);
        btn3.setBorderPainted(false);
        btn3.setContentAreaFilled(false);
        btn2.setFocusPainted(false);
        btn3.setBounds(215, 380, 144, 50);
        
        jp1.add(btn1);
        jp1.add(btn2);
        jp1.add(btn3);
        jp1.add(label1);
        jp1.add(label2);
        jp1.add(label3);
        
        //젤 위의 패널
        jp2 = new HeaderPanel(mainframe);
        jp2.setBounds(0, 0, 544, 100); 
        
//        jp2.setBackground(Color.white);
//        logOutBtn=new RoundedButton("로그아웃");
//        logOutBtn.setBounds(450, 30, 60, 30);
//        //이미지 리사이징
//        ImageIcon icon = new ImageIcon("restaurant\\img\\logo.png");            
//        Image img = icon.getImage();            
//        Image updateImg = img.getScaledInstance(170, 100, Image.SCALE_SMOOTH);           
//        ImageIcon updateIcon = new ImageIcon(updateImg);            
//        imglabel = new JLabel(updateIcon);
//        imglabel.setBounds(10, 25, 100, 50);
//        jp2.add(logOutBtn);
//        jp2.add(imglabel);
        
        jp.add(jp1);  
        jp.add(jp2);
        this.add(jp);
        this.setVisible(true);
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn3.addActionListener(this);
        
    }
    
    @Override
	   public void paint(Graphics g) {
		   super.paint(g);
		   g.drawLine(20, 100, 544, 100);

	   }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	Object o=e.getSource();
    	if(o==btn1) {
    		Graph1 g1 = new Graph1();
    		g1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    		g1.setVisible(true);
    						
    		}else if(o==btn2) {
    			Graph2 g1 = new Graph2();
    			g1.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    			g1.setVisible(true);
    		}else if(o==btn3) {
    			Graph3 g1 = new Graph3();
    			g1.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    			g1.setVisible(true);
    		}
    	}
    	
    }

    
