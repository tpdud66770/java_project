package Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import database.MenuMgr;
import database.OrdersBean;
import database.OrdersMgr;

public class ResultDialog extends JDialog {
    Vector<OrdersBean> vlist = new Vector<OrdersBean>();
    OrdersMgr mgr = new OrdersMgr();
    MenuMgr mgr2 = new MenuMgr();

    Font font = new Font("Malgun Gothic", Font.PLAIN, 14);

    public ResultDialog(int reserve_num, ReserveResult frame) {
        setLayout(new BorderLayout());
        JPanel innerBox = new JPanel();
        
        // FlowLayout을 사용하여 왼쪽 정렬되도록 설정
        innerBox.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel navBox = new DialogNavPanel("주문한 목록", this);
        setUndecorated(true);
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        getContentPane().setBackground(Color.white);
        innerBox.setBackground(Color.white);
        getContentPane().setMinimumSize(new Dimension(200, 400));
        setTitle("주문한 메뉴");
        Point panelLocation = frame.getLocationOnScreen();
        setLocation(panelLocation.x + 500, panelLocation.y + 400);
        vlist = mgr.getOrders(reserve_num);
        
        for (OrdersBean ordersBean : vlist) {
            int menuNum = ordersBean.getMenu_num();
            int menuAmount = ordersBean.getMenu_amount();
            String menuName = mgr2.getMenu(menuNum).getMenu_name();
            
            // 메뉴 항목을 담을 패널
            JPanel labelBox = new JPanel(new FlowLayout(FlowLayout.LEFT)); // FlowLayout 사용
            labelBox.setBackground(Color.white);

            // 각 라벨 생성 및 설정
            JLabel dotLabel = new JLabel("• ");
            JLabel nameLabel = new JLabel(menuName + " ");
            JLabel amountLabel = new JLabel(menuAmount + "개");

            dotLabel.setFont(font);
            nameLabel.setFont(font);
            amountLabel.setFont(font);

            // 라벨들을 패널에 추가
            labelBox.add(dotLabel);
            labelBox.add(nameLabel);
            labelBox.add(amountLabel);
            
            // 패널을 innerBox에 추가
            innerBox.add(labelBox);
        }

        add(innerBox, BorderLayout.CENTER);
        add(navBox, BorderLayout.NORTH);
        setVisible(true);
        int height = 50 + vlist.size() * 32;
        setSize(350, height);
    }
}
