package restaurant1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import log.OutbackApp;

public class Edit_menu extends JFrame {
	Color gray = new Color(220, 220, 220);
	JPanel jp, jp1, jp2;
	JTextField mn, mc;
	JLabel imageLabel;
	RoundedButton1 editBtn,uploadBtn;
	File selectedFile;
	Font font1 = new Font("Malgun Gothic", Font.PLAIN, 14);
	Font font2 = new Font("Malgun Gothic", Font.PLAIN, 12);
	MenuBean bean;
	MenuMgr mgr = new MenuMgr();
	JComboBox<String> type;
	String a;
	private manager_menu mm;

	public Edit_menu(manager_menu mm) {
		
		super("메뉴 관리");
		this.mm=mm;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(500, 380);
		setSize(400, 400);
		setBackground(Color.white);

		MenuBean bean = mgr.getMenu(mm.num);
		jp = new JPanel();
		jp.setLayout(null);
		jp.setBounds(0, 0, 400, 400);
		jp.setBackground(Color.white);

		jp1 = new JPanel();
		jp1.setLayout(null);
		jp1.setBounds(50, 20, 300, 180);
		jp1.setBackground(Color.white);

		// JComboBox
		type = new JComboBox<>();
		type.setModel(new DefaultComboBoxModel<>(new String[] { "필라프", "파스타", "스테이크", "음료" }));
		type.setSelectedItem(bean.getMenu_type());
		type.setBounds(40, 150, 80, 20);
		jp1.add(type);

		// 이미지 불러오기
		imageLabel = new JLabel("이미지를 업로드", SwingConstants.CENTER);
		if (mm.num != 0) {
			bean = mgr.getMenu(mm.num);
			a=bean.getMenu_image();
			imageLabel.setIcon(resizeImage(bean.getMenu_image(), 180, 120));
			imageLabel.setText("");
		}
		imageLabel.setFont(font1);
		imageLabel.setBounds(40, 10, 180, 120);
		imageLabel.setBorder(new javax.swing.border.LineBorder(Color.black));

		uploadBtn = new RoundedButton1("이미지 추가");
		uploadBtn.setFont(font2);
		uploadBtn.setBounds(200, 150, 80, 20);
		uploadBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int option = fileChooser.showOpenDialog(null);

				if (option == JFileChooser.APPROVE_OPTION) {
					selectedFile = fileChooser.getSelectedFile();
					// 이미지를 라벨에 표시

					imageLabel.setIcon(resizeImage(selectedFile.getAbsolutePath(), 180, 120));
					imageLabel.setText("");
				}
			}
		});

		jp1.add(imageLabel);
		jp1.add(uploadBtn);

		jp2 = new JPanel();
		jp2.setLayout(null);
		jp2.setBackground(gray);
		jp2.setBounds(50, 200, 300, 110);

		mn = new JTextField();
		mn.setFont(font1);
		mn.setBackground(gray);
		mn.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		mn.setBounds(10, 30, 200, 25);
		if(bean.getMenu_name()==null) {
			mn.setText("메뉴 이름을 입력해주세요.");
		}else {
			mn.setText(bean.getMenu_name());
		}
		

		mc = new JTextField();
		mc.setFont(font2);
		mc.setBackground(gray);
		mc.setBounds(10, 60, 150, 25);
		mc.setForeground(Color.white);
		mc.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		mc.setText(Integer.toString(bean.getMenu_price()));

		jp2.add(mn);
		jp2.add(mc);

		// 완료 버튼
        editBtn = new RoundedButton1("완료");
        editBtn.setFont(font2);
        editBtn.setForeground(Color.white);
        editBtn.setBackground(Color.black);
        editBtn.setBounds(266, 320, 85, 30);
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedFile != null) {
                    saveImageToRestaurantFolder(selectedFile);
                }
                MenuBean bean = new MenuBean();
                bean.setMenu_name(mn.getText());
                bean.setMenu_type((String) type.getSelectedItem());
                bean.setMenu_price(Integer.parseInt(mc.getText()));
                bean.setMenu_image(selectedFile != null ? selectedFile.getAbsolutePath() : a);
                
                if (mm.num == 0) {    
                    if (mgr.insertMenu(bean)) {
                        JOptionPane.showMessageDialog(Edit_menu.this, "메뉴가 성공적으로 저장되었습니다.");
                        mm.updateMenuDisplay(); // 메뉴 갱신 호출
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(Edit_menu.this, "메뉴 저장에 실패하였습니다.");
                    }
                } else {  
                    bean.setMenu_num(mm.num);      
                    if (mgr.updateMenu(bean)) {
                        JOptionPane.showMessageDialog(Edit_menu.this, "메뉴가 성공적으로 업데이트되었습니다.");
                        mm.updateMenuDisplay(); // 메뉴 갱신 호출
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(Edit_menu.this, "메뉴 업데이트에 실패하였습니다.");
                    }
                }
            }
        });
        		
		jp.add(editBtn);
		jp.add(jp1);
		jp.add(jp2);
		this.add(jp);
		setVisible(true);

	}

	private ImageIcon resizeImage(String imagePath, int width, int height) {
		ImageIcon icon = new ImageIcon(imagePath);
		Image img = icon.getImage();
		Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(resizedImg);
	}

	public void saveImageToRestaurantFolder(File selectedFile) {
		try {
			Path projectDir = Paths.get(System.getProperty("user.dir"));
			Path restaurantFolder = projectDir.resolve("Frame/menuImg");
			Path destinationPath = restaurantFolder.resolve(selectedFile.getName());
			Files.copy(selectedFile.toPath(), destinationPath);
		} catch (IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "같은 파일이 존재하거나 오류가 발생하였습니다.");
		}
	}
}