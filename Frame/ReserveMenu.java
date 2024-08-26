package Frame;

import database.MenuBean;
import database.MenuMgr;
import database.OrdersBean;
import database.OrdersMgr;
import database.ReservationBean;
import database.ReservationMgr;
import database.UserMgr;
import log.OutbackApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.Flow;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ReserveMenu extends JPanel {
	ReserveMenu frame = this;
	JPanel innerBox = new JPanel();
	JPanel menuBox = new JPanel();
	JPanel navbar = new JPanel();
	JPanel reserveBtnBox = new JPanel();
	JPanel labelBox = new JPanel();
	JLabel label[] = new JLabel[4];
	JPanel pagePanel = new JPanel();
	HashMap<Integer,Integer> list = new HashMap<>();
	ListDialog listDialog;
	String flag = "0";
	String labelFlag = "파스타";
	MenuMgr menuMgr = new MenuMgr();
	Vector<MenuBean> vlist;
	ReservationMgr reservationMgr = new ReservationMgr();
	UserMgr userMgr = new UserMgr();
	OrdersMgr ordersMgr = new OrdersMgr();
	JPanel mainPanel = new JPanel();
	JPanel footPanel = new JPanel();
	OutbackApp mainframe;
	
	Font font = new Font("Malgun Gothic", Font.PLAIN, 12);
	Font labelFont = new Font("Malgun Gothic", Font.PLAIN, 13);
	public MenuBean topSteak = new MenuMgr().getBestMenuBySales("스테이크");
    public MenuBean topPasta = new MenuMgr().getBestMenuBySales("파스타");
    public MenuBean topPhilahp = new MenuMgr().getBestMenuBySales("필라프");
    public MenuBean topDrink = new MenuMgr().getBestMenuBySales("음료");
    public MenuBean topMenus[] = {topSteak, topPasta, topPhilahp, topDrink};
	public ReserveMenu(OutbackApp mf) {
		mainframe = mf;
		listDialog = new ListDialog(frame);
		HeaderPanel headerPanel = new HeaderPanel(mf);
		vlist = menuMgr.getMenusOfTypeBySales("파스타");
		JLabel pageIndex[] = new JLabel[(int)(vlist.size()/4)+1];
		setVisible(true);
		setLayout(new BorderLayout());
		innerBox.setBounds(50,30,OutbackApp.WIDTH-300,OutbackApp.HEIGHT-150);
		innerBox.setLayout(new BorderLayout());
		setBackground(Color.white);
		innerBox.setBackground(getBackground());
		navbar.setBackground(getBackground());
		reserveBtnBox.setBackground(getBackground());
		labelBox.setBackground(getBackground());
		menuBox.setBackground(getBackground());
		pagePanel.setBackground(getBackground());
		mainPanel.setBackground(getBackground());
		footPanel.setBackground(getBackground());
		label[0] = new JLabel("필라프");
		label[1] = new JLabel("파스타");
		label[2] = new JLabel("스테이크");
		label[3] = new JLabel("음료");
		for (int i = 0; i < label.length; i++) {
			label[i].setFont(labelFont);
			label[i].setForeground(Color.gray);
			label[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					JLabel obj = (JLabel)e.getSource();
					obj.setForeground(Color.black);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					JLabel obj = (JLabel)e.getSource();
					if(!obj.getText().equals(labelFlag)) {
						obj.setForeground(Color.gray);
					}
					
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					JLabel obj = (JLabel)e.getSource();
					labelFlag = obj.getText();
					for(int i = 0; i < label.length ; i++) {
						label[i].setForeground(Color.gray);
					}
					obj.setForeground(Color.black);
					vlist = menuMgr.getMenusOfTypeBySales(obj.getText());
					flag = "0";
					printMenu();
				}
			});
		}
		label[1].setForeground(Color.black);
		RoundedButton cartBtn = new RoundedButton("장바구니");
		cartBtn.setFont(font); 
		cartBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listDialog.reprintDialog();
				listDialog.setVisible(true);
			}
		});
		RoundedButton reserveBtn = new RoundedButton("예약하기");
		reserveBtn.setFont(font);
		labelBox.setLayout(new GridLayout(1,4,20,0));
		navbar.setLayout(new FlowLayout());
		reserveBtnBox.setLayout(new GridLayout(2,1,0,50));
		labelBox.add(label[0]);
		labelBox.add(label[1]);
		labelBox.add(label[2]);
		labelBox.add(label[3]);
		navbar.add(labelBox);
		navbar.add(new JLabel("                "));
		navbar.add(cartBtn);
		menuBox.setLayout(new GridLayout(4,1,0,10));
		for(int i = 0 ; i < 4; i++) {
			try {
				MenuBean bean = vlist.get(i);
				MenuItem menuItem = new MenuItem(bean,this);
				menuItem.setFont(font);
				menuBox.add(menuItem);
			} catch (Exception e) {
				break;
			}
			
			
		}
		printPageIndex(pageIndex);
		reserveBtnBox.add(pagePanel);
		reserveBtn.setPreferredSize(new Dimension(100,30));
		reserveBtnBox.add(reserveBtn);
		reserveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mf.reserveBean.setReserve_price(0);
				if(!reservationMgr.insertReservation(mf.reserveBean)) {
					JOptionPane.showMessageDialog(mf, "예상치 못한 오류가 발생했습니다. 다시한번 시도해주세요.");
					return;
				}
				ReservationBean currentReservation = reservationMgr.getLastReservation();
				int reserve_num = currentReservation.getReserve_num();
				Set<Integer> key = list.keySet();
				for (Integer keyElement : key) {
					OrdersBean bean = new OrdersBean();
					bean.setReserve_num(reserve_num);
					bean.setMenu_num(keyElement);
					bean.setMenu_amount(list.get(keyElement));
					ordersMgr.insertOrder(bean);
				}
				listDialog.setVisible(false);
				reservationMgr.updatePrice(reserve_num, ordersMgr.getTotalPrice(reserve_num));
				new CoolSms(mf.userId, currentReservation);
				JOptionPane.showMessageDialog(mf, "예약이 완료되었습니다.");
				
				if(userMgr.isUserTemp(mainframe.userId)) {
					userMgr.deleteUser(mainframe.userId);
					mainframe.userId = "";
				}
				mf.switchToPanel(new ReserveResult(ReserveResult.TYPE_TEMP,reserve_num,mf));
			}
		});
		menuBox.setPreferredSize(new Dimension(400, 600));
		innerBox.add(navbar,BorderLayout.NORTH);
		mainPanel.setLayout(new FlowLayout());
		mainPanel.add(menuBox);
		innerBox.add(mainPanel);
		footPanel.add(reserveBtnBox);
		innerBox.add(footPanel,BorderLayout.SOUTH);
		add(headerPanel,BorderLayout.NORTH);
		add(innerBox);
		validate();
	}
	
	public void printMenu() {
		menuBox.removeAll();
		menuBox.repaint();
		int flagValue = Integer.valueOf(flag);
		for(int i = flagValue * 4; i < (flagValue +1)*4 ; i++ ) {
			if(i == vlist.size()) break;
			menuBox.add(new MenuItem(vlist.get(i),this));
			menuBox.setFont(font);
		}
		pagePanel.removeAll();
		pagePanel.repaint();
		JLabel pageIndex[] = new JLabel[(int)(vlist.size()/4)+1];
		printPageIndex(pageIndex);
		validate();
	}
	
	public void printPageIndex(JLabel[] pageIndex) {
		for(int i = 0 ; i < pageIndex.length ; i++ ) 
		{
			pageIndex[i] = new JLabel((i+1)+"");
			pageIndex[i].setPreferredSize(new Dimension(20,20));
			pageIndex[i].setFont(font); 
			pageIndex[i].setOpaque(true);
			pageIndex[i].setBackground(Color.white);
			pageIndex[i].setHorizontalAlignment(JLabel.CENTER);
			pageIndex[i].setLayout(new FlowLayout(FlowLayout.CENTER));
			pageIndex[i].setName(String.valueOf(i));
			pageIndex[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					JLabel obj = (JLabel)e.getSource();
					obj.setBackground(Color.black);
					obj.setForeground(Color.white);
				}
				public void mouseExited(MouseEvent e) {
					JLabel obj = (JLabel)e.getSource();
					
					if(!obj.getName().equals(flag)) {
						obj.setBackground(Color.white);
						obj.setForeground(Color.black);
					}
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					JLabel obj = (JLabel)e.getSource();
					flag = obj.getName();
					for(int i = 0 ;  i < pageIndex.length;i++) {
						pageIndex[i].setBackground(Color.white);
						pageIndex[i].setForeground(Color.black);
					}
					obj.setBackground(Color.black);
					obj.setForeground(Color.white);
					printMenu();
				}
			});
			pagePanel.add(pageIndex[i]);
		}
	}
}
