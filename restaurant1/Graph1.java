package restaurant1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Graph1 extends JFrame {
	JLabel label;
	Vector<SalesBean> vlist;
	SalesMgr mgr=new SalesMgr();
	int[] todaysales=new int[7];
	Font font1 = new Font("Malgun Gothic", Font.PLAIN, 14);

	public Graph1() {
		super("당일 매출");

		buildGUI();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(500, 100);
		setSize(600, 400);
		 for (int i = 0; i < 7; i++) {
		        todaysales[i] = 0;
		    }
		vlist = mgr.todaySales();
		for (int i = 0; i < 7; i++) {
			SalesBean bean = vlist.get(i);
			todaysales[i]+=bean.getSales_total_price(); 
			
		}
		setVisible(true);
		
	}

	private void buildGUI() {
		setLayout(new BorderLayout());

		Container contentpane = getContentPane();
		ResultPanel resultPanel = new ResultPanel();
		label = new JLabel("단위: 원");
		label.setFont(font1);
		label.setBounds(480, 10, 50, 50);
		contentpane.add(label);
		// 그래프를 그릴 패널
		contentpane.add(resultPanel, BorderLayout.CENTER);
		
	}

	// 결과물(그래프)이 나타날 패널
	class ResultPanel extends JPanel {

		public void paint(Graphics g) {
			super.paint(g); // 부모 클래스의 paint 메소드 호출
			int j = 100;
			g.clearRect(0, 0, getWidth(), getHeight());
			g.drawLine(100, 250, 520, 250);// 제일 밑 가로줄

			for (int i = 0; i < 10; i++) {
				g.drawString(i * 200000 + "", 25, 255 - (20 * i));// 열 값
				g.drawLine(100, 250 - (20 * i), 520, 250 - (20 * i));// 모든 가로줄들
			}

			for (int i = 11; i < 24; i += 2) {
				g.drawLine(100, 20, 100, 250);// 제일 왼쪽 세로줄
				g.drawString(i + ":00", j, 270);// 행 값
				
				j += 65;
			}
			g.setFont(font1);
			g.setColor(Color.BLACK);

			// 꺾은선 그래프 그리기
			int[] xPoints = { 110, 175, 240, 305, 370, 435, 500 };
			int[] yPoints = new int[7];
			for(int i=0;i<7;i++) {
				yPoints[i]=250-todaysales[i]/10000;
			}

					
			int nPoints = xPoints.length;

			g.drawPolyline(xPoints, yPoints, nPoints);

			// 데이터 포인트에 원 그리기
			g.setColor(Color.BLACK);
			for (int i = 0; i < nPoints; i++) {
				g.fillOval(xPoints[i] - 5, yPoints[i] - 5, 10, 10);
			}
		}
	}

}
