package restaurant1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;

import Frame.HeaderPanel;
import log.OutbackApp;

public class manager_menu extends JPanel implements ActionListener {
    JRadioButton ch[] = new JRadioButton[4];
    RoundedButton1 logOutBtn, editBtn, inBtn;
    String bl;
    JLabel b[] = new JLabel[9]; // JButton에서 JLabel로 변경
    JButton btn[] = new JButton[4];
    JPanel jp, jp1, jp2;
    JLabel logolb; // 이미지 라벨
    JLabel lb[] = new JLabel[4];
    JLabel mn[] = new JLabel[4]; // 메뉴 이름 라벨
    JLabel mc[] = new JLabel[4]; // 메뉴 가격 라벨
    Font font1 = new Font("Malgun Gothic", Font.PLAIN, 13);
    Font font2 = new Font("Malgun Gothic", Font.PLAIN, 11);
    MenuMgr mgr = new MenuMgr();
    ButtonGroup group = new ButtonGroup();
    int chnum = 1;
    public static int num;
    int menuNumber;
    OutbackApp mainframe;
    MenuBean bean = new MenuBean();

    public void updateMenuDisplay() {
        for (int i = 0; i < 4; i++) {
            MenuBean bean = mgr.getMenu(i + 1);
            mn[i].setText(bean.getMenu_name());
            mc[i].setText(Integer.toString(bean.getMenu_price())+"원");
            lb[i].setIcon(resizeImage(bean.getMenu_image(), 180, 120)); // 이미지 크기 조정
        }
    }

    public manager_menu(OutbackApp mainframe) {
        ImageIcon xIcon = new ImageIcon("Frame/Imgs/x.png");
        Image scaledXImage = xIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        ImageIcon scaledXIcon = new ImageIcon(scaledXImage);

        this.setLayout(null);
        this.mainframe = mainframe;
        jp = new JPanel();
        jp.setLayout(null);
        jp.setBounds(0, 0, 584, 836); // 메인 패널 위치와 크기
        jp.setBackground(Color.white);

        // 젤 위의 패널
        jp1 = new HeaderPanel(mainframe);
        jp1.setBounds(0, 0, 544, 100);

        jp2 = new JPanel();
        jp2.setLayout(null);
        jp2.setBounds(10, 120, 550, 800);
        jp2.setBackground(Color.white);

        // 라디오 버튼
        for (int i = 0; i < 4; i++) {
            ch[i] = new JRadioButton();
            ch[i].setBounds(30, 70 + 130 * i, 20, 15);
            ch[i].setBackground(Color.white);
            group.add(ch[i]);
            jp2.add(ch[i]);
        }

        // 이미지
        for (int i = 0; i < 4; i++) {
            lb[i] = new JLabel();
            lb[i].setBounds(70, 20 + i * 130, 180, 120);
            jp2.add(lb[i]);
        }


        // 메뉴 이름
        for (int i = 0; i < 4; i++) {
            mn[i] = new JLabel();
            mn[i].setBounds(280, 30 + 130 * i, 200, 50);
            mn[i].setFont(font1);
            jp2.add(mn[i]);
        }

        // 메뉴 가격
        for (int i = 0; i < 4; i++) {
            mc[i] = new JLabel();
            mc[i].setBounds(280, 70 + 130 * i, 200, 50);
            mc[i].setFont(font2);
            mc[i].setForeground(Color.gray);
            jp2.add(mc[i]);
        }

        for (int j = 0; j < 4; j++) {
            MenuBean bean = mgr.getMenu(j + 1);
            mn[j].setText(bean.getMenu_name());
            mc[j].setText(Integer.toString(bean.getMenu_price())+"원");
            lb[j].setIcon(resizeImage(bean.getMenu_image(), 180, 120));
        }

        // 숫자 버튼을 JLabel로 대체
        for (int i = 0; i < 9; i++) {
            final int index = i;
            b[i] = new JLabel((i + 1) + "", JLabel.CENTER);
            b[i].setOpaque(true);
            b[i].setBackground(Color.white);
            b[i].setForeground(Color.black);
            b[i].setFont(font2);
            b[i].setBounds(110 + i * 40, 550, 30, 30);
            b[i].setBorder(new javax.swing.border.LineBorder(Color.white));

            // 마우스 리스너 추가
            b[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    JLabel obj = (JLabel) e.getSource();
                    obj.setForeground(Color.white);
                    obj.setBackground(Color.black);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    JLabel obj = (JLabel) e.getSource();
                    if (!obj.getText().equals(bl)) {
                        obj.setForeground(Color.black);
                        obj.setBackground(Color.white);
                    }
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    for (int i = 0; i < 9; i++) {
                        b[i].setForeground(Color.black);
                        b[i].setBackground(Color.white);
                    }
                    JLabel obj = (JLabel) e.getSource();
                    obj.setForeground(Color.white);
                    obj.setBackground(Color.black);
                    bl = obj.getText();
                    for (int j = 0; j < 4; j++) {
                        menuNumber = (index * 4) + (j + 1);
                        MenuBean bean = mgr.getMenu(menuNumber);
                        chnum = 1 + index * 4;
                        mn[j].setText(bean.getMenu_name());
                        mc[j].setText(Integer.toString(bean.getMenu_price())+"원");
                        lb[j].setIcon(resizeImage(bean.getMenu_image(), 180, 120)); // 이미지 크기 조정
                    }
                }
            });
            jp2.add(b[i]);
        }

        // 삭제 버튼
        for (int i = 0; i < 4; i++) {
            final int index = i;
            btn[i] = new JButton(scaledXIcon);
            btn[i].setHorizontalAlignment(JButton.CENTER);
            btn[i].setPreferredSize(new Dimension(15, 15));
            btn[i].setBorderPainted(false);
            btn[i].setContentAreaFilled(false);
            btn[i].setBounds(480, 50 + i * 130, 15, 15);
            btn[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	for (int i = 0; i < 9; i++) {
                        b[i].setForeground(Color.black);
                        b[i].setBackground(Color.white);
                    }
                    b[0].setForeground(Color.white);
                    b[0].setBackground(Color.black);
                    for (int i = 0; i < 4; i++) {
                        if (e.getSource() == btn[i]) {
                            mgr.deleteMenu(menuNumber - 3 + i);
                            bean.setMenu_num(menuNumber - 3 + i);
                            mgr.updatedel(bean);
                            JOptionPane.showMessageDialog(manager_menu.this, "삭제 완료 되었습니다.");
                            updateMenuDisplay();
                        }
                    }
                }
            });
            jp2.add(btn[i]);
        }

        // 추가하기 버튼
        inBtn = new RoundedButton1("추가하기");
        inBtn.setFont(font1);
        inBtn.setForeground(Color.white);
        inBtn.setBackground(Color.black);
        inBtn.setBounds(128, 620, 85, 30);
        jp2.add(inBtn);
        inBtn.addActionListener(this);

        // 수정하기 버튼
        editBtn = new RoundedButton1("수정하기");
        editBtn.setFont(font1);
        editBtn.setForeground(Color.white);
        editBtn.setBackground(Color.black);
        editBtn.setBounds(328, 620, 85, 30);
        jp2.add(editBtn);
        editBtn.addActionListener(this);

        jp.add(jp1);
        jp.add(jp2);
        this.add(jp);
        this.setVisible(true);
    }

    // 이미지 크기를 조정하는 메서드
    private ImageIcon resizeImage(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawLine(20, 100, 544, 100);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editBtn) {
            if (!ch[0].isSelected() && !ch[1].isSelected() && !ch[2].isSelected() && !ch[3].isSelected()) {
                JOptionPane.showMessageDialog(manager_menu.this, "체크 후 수정 가능합니다");
            } else {
            	for (int i = 0; i < 9; i++) {
                    b[i].setForeground(Color.black);
                    b[i].setBackground(Color.white);
                }
            	b[0].setForeground(Color.white);
                b[0].setBackground(Color.black);
                for (int i = 0; i < 4; i++) {
                    if (ch[i].isSelected()) {
                        num = chnum + i;
                    }
                }
                Edit_menu g1 = new Edit_menu(this); // manager_menu 인스턴스를 전달
                g1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                g1.setVisible(true);
            }
        } else if (e.getSource() == inBtn) {
        	for (int i = 0; i < 9; i++) {
                b[i].setForeground(Color.black);
                b[i].setBackground(Color.white);
            }
            b[0].setForeground(Color.white);
            b[0].setBackground(Color.black);
            num = 0;
            Edit_menu g1 = new Edit_menu(this); // manager_menu 인스턴스를 전달
            g1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            g1.setVisible(true);
        }
    }
}
