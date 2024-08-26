package Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import database.ReservationBean;
import database.ReservationMgr;
import log.OutbackApp;
import log.ReviewMain;

public class ReserveResult extends JPanel {
	public static final int TYPE_USER = 0;
	public static final int TYPE_TEMP = 1;
	ReserveResult frame = this;
	ResultDialog rd;
	JLabel notice = new JLabel("예약이 완료 되었습니다.");
	JPanel noticePane = new JPanel();
	JLabel title[] = new JLabel[5];
	String titleText[] = {"예약 번호","테이블 번호","메뉴","가격","예약 날짜 및 시간"};
	JLabel content[] = new JLabel[5];
	JPanel innerBox = new JPanel();
	JPanel tcBox = new JPanel();
	JPanel btnPane = new JPanel();
	int reserve_num ;
	RoundedButton completeBtn = new RoundedButton("식사완료");
	ReservationMgr reservationMgr = new ReservationMgr();
	
	Font font = new Font("Malgun Gothic", Font.PLAIN, 13);
	Font titleFont = new Font("Malgun Gothic", Font.BOLD, 13);
	
	
	public ReserveResult(int type, int reserve_num,OutbackApp mainframe) {
		this.reserve_num =reserve_num;
	    ReservationBean bean = reservationMgr.getReservation(reserve_num);
	    String contentStr[] = {bean.getReserve_num() + "", (bean.getTbl_num()) + "", "보기    ▶", NumberFormat.getInstance().format(bean.getReserve_price()) + "", reservationMgr.chageFormatTime(bean.getReserve_time())};
	    HeaderPanel headerPanel = new HeaderPanel(mainframe);
	    setLayout(new BorderLayout());
	    setBackground(Color.white);

	    innerBox.setLayout(null);
	    innerBox.setPreferredSize(new Dimension(400, 700));  // innerBox의 크기를 설정
	    innerBox.setBackground(Color.white);

	    tcBox.setLayout(new GridLayout(5, 2, 30, 50));
	    tcBox.setBounds(150, 100, 300, 500);  // tcBox의 크기와 위치 설정
	    tcBox.setBackground(Color.white);

	    noticePane.setBounds(130, 30, 300, 50);  // noticePane의 크기와 위치 설정
	    noticePane.setBackground(Color.white);

	    btnPane.setBounds(130, 650, 300, 50);  // btnPane의 크기와 위치 설정
	    btnPane.setBackground(Color.white);

	    notice.setForeground(Color.gray);
	    notice.setFont(font);
	    noticePane.add(notice);

	    for (int i = 0; i < title.length; i++) {
	        title[i] = new JLabel(titleText[i]);
	        content[i] = new JLabel(contentStr[i]);
	        title[i].setFont(titleFont); // 폰트 설정
			content[i].setFont(font); 
	        title[i].setForeground(Color.black);
	        content[i].setForeground(Color.gray);
	        tcBox.add(title[i]);
	        tcBox.add(content[i]);
	    }

	    content[2].addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            if (rd == null) {
	                rd = new ResultDialog(reserve_num, frame);
	            } else if(!rd.isVisible()){
	                Point panelLocation = frame.getLocationOnScreen();
	                rd.setLocation(panelLocation.x + 500, panelLocation.y + 400);
	                rd.setVisible(true);
	            }
	            else if(rd.isVisible()) {
	            	rd.setVisible(false);
	            }
	        }
	    });

	    innerBox.add(tcBox);
	    innerBox.add(noticePane);
	    if (type == TYPE_USER) {
	    	completeBtn.setFont(font);
	    	completeBtn.addMouseListener(new MouseAdapter() {
	    		@Override
	    		public void mouseClicked(MouseEvent e) {
	    			ReviewMain reviewmain = new ReviewMain(mainframe.userId,mainframe,reserve_num);
	    			mainframe.switchToPanel(reviewmain);
	    			reviewmain.showReviewDialog();
	    		}
			});
	        btnPane.add(completeBtn);
	        innerBox.add(btnPane);
	    }

	    add(headerPanel, BorderLayout.NORTH);
	    add(innerBox, BorderLayout.CENTER);  // innerBox를 BorderLayout.CENTER에 추가

	    validate();  // 레이아웃 갱신
	    repaint();   // 화면 갱신
	}


}
