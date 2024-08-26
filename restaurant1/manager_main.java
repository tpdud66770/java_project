package restaurant1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import Frame.HeaderPanel;
import log.OutbackApp;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class manager_main extends JPanel implements ActionListener {
	ImageIcon logo;
	RoundedButton1 btn1,btn2,logOutBtn;
    OutbackApp mainFrame;
    JPanel jp, jp1/*메뉴 패널*/, jp2/*매출 패널*/, jp3/*로그와 로그아웃 패널*/;
    JLabel label1, label2, label3, label4, imglabel;
    Font font = new Font("Malgun Gothic", Font.PLAIN, 20);
    Font font1 = new Font("Malgun Gothic", Font.PLAIN, 13);

    public manager_main(OutbackApp outbackApp) {
    	
	
        this.mainFrame = outbackApp;
        this.setLayout(null); 

        jp = new JPanel();
        jp.setLayout(null);
        jp.setBounds(0, 0, 584, 836); //메인 패널 위치와 크기
        jp.setBackground(Color.white);

        // 첫 번째 패널
        jp1 = new JPanel();
        jp1.setLayout(null);
        jp1.setBorder(new TitledBorder(new LineBorder(Color.gray, 1)));
        jp1.setBounds(50, 200, 484, 200); 
        jp1.setBackground(Color.white);
        label1 = new JLabel("메뉴");
        label1.setBounds(30,10,50,50);
        label1.setFont(font);
        label2 = new JLabel("수정, 삭제, 추가 및 메뉴 관리");
        label2.setBounds(30,40,200,50);
        btn1=new RoundedButton1("메뉴 수정");
        btn1.setFont(font1);
        btn1.setBounds(30,130,100,30);
        jp1.add(label1);
        jp1.add(label2);
        jp1.add(btn1);
        
        // 두 번째 패널
        jp2 = new JPanel();
        jp2.setLayout(null);
        jp2.setBorder(new TitledBorder(new LineBorder(Color.gray, 1)));
        jp2.setBounds(50, 500, 484, 200); 
        jp2.setBackground(Color.white);
        label3 = new JLabel("매출");
        label3.setBounds(30,10,50,50);
        label3.setFont(font);
        label4 = new JLabel("일별, 주별, 월별 예산 관리");
        label4.setBounds(30,40,200,50);
        btn2=new RoundedButton1("매출 보기");
        btn2.setFont(font1);
        btn2.setBounds(30,130,100,30);
        jp2.add(label3);
        jp2.add(label4);
        jp2.add(btn2);
        jp3 = new HeaderPanel(mainFrame);
        jp3.setBounds(0,0,544,100);
        jp.add(jp1);
        jp.add(jp2);
        jp.add(jp3);
        this.add(jp);
        this.setVisible(true);
        btn1.addActionListener(this);
        btn2.addActionListener(this);
    }
    
    @Override
	   public void paint(Graphics g) {
		   super.paint(g);
		   g.drawLine(20, 100, 544, 100);

	   }

    @Override
    public void actionPerformed(ActionEvent e) {
    	Object o = e.getSource();
        if(o==btn1) {
        	mainFrame.switchToPanel(new manager_menu(mainFrame));
        }else if(o==btn2) {
        	mainFrame.switchToPanel(new manager_sales(mainFrame));
        }
    }
}