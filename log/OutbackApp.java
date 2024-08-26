package log;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import Frame.CoolSms;
import Frame.HeaderPanel;
import Frame.ReserveResult;
import Frame.ReserveSetDialog;
import database.MenuBean;
import database.MenuMgr;
import database.ReservationBean;
import database.ReservationMgr;
import database.UserMgr;
import project.BoardMain;
import restaurant1.manager_main;

public class OutbackApp extends JFrame {
    public String userId = "";
    public OutbackApp mainFrame = this;
    public static int WIDTH = 584;
    public static int HEIGHT = 786;
    public JPanel panel[] = new JPanel[4];
    public JButton btn[] = new JButton[4];
    public JPanel currentPanel = null;
    public ReservationBean reserveBean;
    public JPanel reservationSystemPanel;
    public JPanel homePanel;
    public JPanel cardPanel;
    public HeaderPanel headerPanel = new HeaderPanel(this);
    public ReviewMain reviewMain = new ReviewMain(userId, this, 0);
    public BoardMain boardMain = new BoardMain(this, userId);
    public boolean isManager = false;
    public int userPoint = 0;
    public String checkNumber = "?????";
    public UserMgr userMgr = new UserMgr();
    public OutbackApp() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(584, 836);
        this.setLocationRelativeTo(null); // Center the frame
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "정말로 종료하시겠습니까?", "종료 확인", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    // 원하는 동작 추가
                	if(userMgr.isUserTemp(userId)) {
    					userMgr.deleteUser(userId);
    					userId = "";
    				}
                    System.exit(0);  // 프로그램 종료
                }
            }
        });
        setResizable(false);
        // Main panel for card layout
        cardPanel = new JPanel(new CardLayout());

        // Home Panel
        homePanel = new JPanel();
        homePanel.setBackground(Color.WHITE);
        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));

        // Logo
        JLabel logoLabel = new JLabel(new ImageIcon(OutbackApp.class.getResource("/log/outback_logo.png")));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        homePanel.add(Box.createVerticalStrut(50));
        homePanel.add(logoLabel);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        RoundedButton loginButton = new RoundedButton("로그인");
        RoundedButton guestLoginButton = new RoundedButton("비회원 로그인");
        RoundedButton guestReservationButton = new RoundedButton("비회원 예약확인");
        RoundedButton signupButton = new RoundedButton("회원가입");

        // Set all button background colors to black
        loginButton.setBackground(Color.BLACK);
        guestLoginButton.setBackground(Color.BLACK);
        guestReservationButton.setBackground(Color.BLACK);
        signupButton.setBackground(Color.BLACK);

        // Set button text color to white
        loginButton.setForeground(Color.WHITE);
        guestLoginButton.setForeground(Color.WHITE);
        guestReservationButton.setForeground(Color.WHITE);
        signupButton.setForeground(Color.WHITE);

        buttonPanel.add(loginButton);
        buttonPanel.add(guestLoginButton);
        buttonPanel.add(guestReservationButton);
        buttonPanel.add(signupButton);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        homePanel.add(Box.createVerticalStrut(20));
        homePanel.add(buttonPanel);

        // Add home panel to card panel
        cardPanel.add(homePanel, "Home");

        // Login Panel
        JPanel loginPanel = createLoginPanel(cardPanel);
        cardPanel.add(loginPanel, "Login");

        // Guest Login Panel
        JPanel guestLoginPanel = createGuestLoginPanel(cardPanel);
        cardPanel.add(guestLoginPanel, "GuestLogin");

        // Guest Reservation Panel
        JPanel guestReservationPanel = createGuestReservationPanel(cardPanel);
        cardPanel.add(guestReservationPanel, "GuestReservation");

        // Signup Panel
        JPanel signupPanel = createSignupPanel(cardPanel);
        cardPanel.add(signupPanel, "Signup");

        // OutbackReservationSystem Panel
        reservationSystemPanel = createReservationSystemPanel(cardPanel);
        cardPanel.add(reservationSystemPanel, "ReservationSystem");

        // Add card panel to frame
        this.add(cardPanel);
        this.setVisible(true);

        // Button actions
        loginButton.addActionListener(e -> showPanel(cardPanel, "Login"));
        guestLoginButton.addActionListener(e -> showPanel(cardPanel, "GuestLogin"));
        guestReservationButton.addActionListener(e -> showPanel(cardPanel, "GuestReservation"));
        signupButton.addActionListener(e -> showPanel(cardPanel, "Signup"));
    }

    // 로그인 패널 생성 메서드
    public JPanel createLoginPanel(JPanel cardPanel) {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("아이디:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(idLabel, gbc);

        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);

        JLabel pwLabel = new JLabel("비밀번호:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(pwLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        JCheckBox adminCheckBox = new JCheckBox("관리자로 로그인");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginPanel.add(adminCheckBox, gbc);

        // 로그인 버튼
        RoundedButton loginButton = new RoundedButton("로그인");
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);

        // 아이디/비밀번호 찾기 버튼 추가
        RoundedButton findCredentialsButton = new RoundedButton("아이디/비밀번호 찾기");
        findCredentialsButton.setBackground(Color.BLACK);
        findCredentialsButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        loginPanel.add(findCredentialsButton, gbc);

        findCredentialsButton.addActionListener(e -> showFindCredentialsWindow());

        loginButton.addActionListener(e -> {
            userId = usernameField.getText();
            String password = new String(passwordField.getPassword());
            boolean isAdmin = adminCheckBox.isSelected();

            UserService userService = new UserService();
            boolean success;

            if (isAdmin) {
                success = userService.loginAdmin(userId, password);
                if (success) {
                    JOptionPane.showMessageDialog(loginPanel, "관리자 로그인 성공!");
                    isManager = true;
                    boardMain = new BoardMain(this, userId);
                    reviewMain = new ReviewMain(userId,this,0);
                    switchToPanel(new manager_main(this));  // 관리자 전용 페이지로 이동
                } else {
                    JOptionPane.showMessageDialog(loginPanel, "관리자 로그인 실패: ID 또는 비밀번호 오류");
                }
            } else {
                success = userService.loginUser(userId, password);
                if (success) {
                    JOptionPane.showMessageDialog(loginPanel, "로그인 성공!");
                    boardMain = new BoardMain(this, userId);
                    reviewMain = new ReviewMain(userId,this,0);
                    userPoint = new UserMgr().getUserPoint(userId);
                    updateHeaderPanel();
                    showPanel(cardPanel, "ReservationSystem");  // 사용자 페이지로 이동

                } else {
                    JOptionPane.showMessageDialog(loginPanel, "로그인 실패: ID 또는 비밀번호 오류");
                }
            }
        });

        // 뒤로가기 버튼
        RoundedButton backButton = new RoundedButton("뒤로가기");
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        loginPanel.add(backButton, gbc);

        backButton.addActionListener(e -> showPanel(cardPanel, "Home"));

        return loginPanel;
    }

    private void showFindCredentialsWindow() {
        JFrame findCredentialsFrame = new JFrame("아이디/비밀번호 찾기");
        findCredentialsFrame.setSize(400, 300);
        findCredentialsFrame.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.white);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 아이디 찾기 버튼
        RoundedButton findIdButton = new RoundedButton("아이디 찾기");
        findIdButton.setBackground(Color.BLACK);
        findIdButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(findIdButton, gbc);

        // 비밀번호 찾기 버튼
        RoundedButton findPwButton = new RoundedButton("비밀번호 찾기");
        findPwButton.setBackground(Color.BLACK);
        findPwButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(findPwButton, gbc);

        findIdButton.addActionListener(e -> showFindIdDialog(findCredentialsFrame));
        findPwButton.addActionListener(e -> showFindPwDialog(findCredentialsFrame));

        findCredentialsFrame.add(panel);
        findCredentialsFrame.setVisible(true);
    }

    private void showFindIdDialog(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "아이디 찾기", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(parentFrame);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.white);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("이름:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        JLabel phoneLabel = new JLabel("전화번호:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(phoneLabel, gbc);

        JTextField phoneField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(phoneField, gbc);

        // 인증번호 버튼 추가 (전화번호 아래에 위치)
        RoundedButton verifyPhoneButton = new RoundedButton("인증번호 받기");
        verifyPhoneButton.setBackground(Color.BLACK);
        verifyPhoneButton.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(verifyPhoneButton, gbc);
        verifyPhoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(panel, "인증번호가 발송되었습니다.");
                checkNumber = CoolSms.checkNumber(phoneField.getText());
            }
        });

        JLabel verificationCodeLabel = new JLabel("인증번호:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(verificationCodeLabel, gbc);

        JTextField verificationCodeField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(verificationCodeField, gbc);

        RoundedButton findIdButton = new RoundedButton("아이디 찾기");
        findIdButton.setBackground(Color.BLACK);
        findIdButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(findIdButton, gbc);

        findIdButton.addActionListener(e -> {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String verificationCode = verificationCodeField.getText();

            if (!verificationCode.equals(checkNumber)) {
                JOptionPane.showMessageDialog(dialog, "인증번호가 잘못되었습니다.");
                return;
            }

            UserService userService = new UserService();
            String userId = userService.findUserId(name, phone);

            if (userId != null) {
                JOptionPane.showMessageDialog(dialog, "아이디는: " + userId + "입니다.");
            } else {
                JOptionPane.showMessageDialog(dialog, "입력한 정보로 아이디를 찾을 수 없습니다.");
            }
        });

        dialog.add(panel);
        dialog.setVisible(true);
    }

    // 비밀번호 찾기 다이얼로그
    private void showFindPwDialog(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "비밀번호 찾기", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(parentFrame);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.white);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("아이디:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(idLabel, gbc);

        JTextField idField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        JLabel phoneLabel = new JLabel("전화번호:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(phoneLabel, gbc);

        JTextField phoneField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(phoneField, gbc);

        // 인증번호 버튼 추가 (전화번호 아래에 위치)
        RoundedButton verifyPhoneButton = new RoundedButton("인증번호 받기");
        verifyPhoneButton.setBackground(Color.BLACK);
        verifyPhoneButton.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(verifyPhoneButton, gbc);
        verifyPhoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkNumber = CoolSms.checkNumber(phoneField.getText());
                JOptionPane.showMessageDialog(panel, "인증번호가 발송되었습니다.");
            }
        });

        JLabel verificationCodeLabel = new JLabel("인증번호:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(verificationCodeLabel, gbc);

        JTextField verificationCodeField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(verificationCodeField, gbc);

        RoundedButton findPwButton = new RoundedButton("비밀번호 찾기");
        findPwButton.setBackground(Color.BLACK);
        findPwButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(findPwButton, gbc);

        findPwButton.addActionListener(e -> {
            String userId = idField.getText();
            String phone = phoneField.getText();
            String verificationCode = verificationCodeField.getText();

            if (!verificationCode.equals(checkNumber)) {
                JOptionPane.showMessageDialog(dialog, "인증번호가 잘못되었습니다.");
                return;
            }

            UserService userService = new UserService();
            String password = userService.findUserPassword(userId, phone);

            if (password != null) {
                JOptionPane.showMessageDialog(dialog, "비밀번호는: " + password + "입니다.");
            } else {
                JOptionPane.showMessageDialog(dialog, "입력한 정보로 비밀번호를 찾을 수 없습니다.");
            }
        });

        dialog.add(panel);
        dialog.setVisible(true);
    }

    public JPanel createGuestLoginPanel(JPanel cardPanel) {
        JPanel guestLoginPanel = new JPanel(new GridBagLayout());
        guestLoginPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("이름:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        guestLoginPanel.add(nameLabel, gbc);

        JTextField nameField = new RoundedTextField(15);  // Use RoundedTextField
        gbc.gridx = 1;
        guestLoginPanel.add(nameField, gbc);

        JLabel phoneLabel = new JLabel("전화번호:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        guestLoginPanel.add(phoneLabel, gbc);

        JTextField phoneField = new RoundedTextField(15);  // Use RoundedTextField
        gbc.gridx = 1;
        guestLoginPanel.add(phoneField, gbc);
        
        RoundedButton verifyPhoneButton = new RoundedButton("인증번호 받기");
        verifyPhoneButton.setBackground(Color.BLACK);
        verifyPhoneButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        guestLoginPanel.add(verifyPhoneButton, gbc);
        verifyPhoneButton.addActionListener(e -> {
            if (new UserService().isPhoneNumberDuplicated(phoneField.getText())) {
                 JOptionPane.showMessageDialog(cardPanel, "이미 사용 중인 전화번호입니다.");
                 return;
             }
            checkNumber = CoolSms.checkNumber(phoneField.getText());
            JOptionPane.showMessageDialog(cardPanel, "인증번호가 발송되었습니다.");
        });

        JLabel verificationCodeLabel = new JLabel("인증번호:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        guestLoginPanel.add(verificationCodeLabel, gbc);

        JTextField verificationCodeField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        guestLoginPanel.add(verificationCodeField, gbc);
        
        RoundedButton guestLoginButton = new RoundedButton("비회원 로그인");
        guestLoginButton.setBackground(Color.BLACK);
        guestLoginButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        guestLoginPanel.add(guestLoginButton, gbc);
        guestLoginButton.addActionListener(e -> {
            String name = nameField.getText();
            String phone = phoneField.getText();
            if(!checkNumber.equals(verificationCodeField.getText())) {
            	JOptionPane.showMessageDialog(guestLoginPanel, "인증번호가 잘못되었습니다.");
            	return;
            }
            UserService userService = new UserService();
            boolean success = userService.guestLogin(name, phone);
            
            if (success) {
                JOptionPane.showMessageDialog(guestLoginPanel, "비회원 로그인 성공!");
                userId = phone;
                checkNumber ="????";
                updateHeaderPanel();
                showPanel(cardPanel, "ReservationSystem"); // Move to ReservationSystem
            } else {
                JOptionPane.showMessageDialog(guestLoginPanel, "비회원 로그인 실패: 이름 또는 전화번호가 잘못되었습니다.");
            }
        });

        RoundedButton backButton = new RoundedButton("뒤로가기");
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        guestLoginPanel.add(backButton, gbc);

        backButton.addActionListener(e -> showPanel(cardPanel, "Home"));

        return guestLoginPanel;
    }

    public JPanel createGuestReservationPanel(JPanel cardPanel) {
        JPanel guestReservationPanel = new JPanel(new GridBagLayout());
        guestReservationPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel reservationLabel = new JLabel("예약번호:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        guestReservationPanel.add(reservationLabel, gbc);

        JTextField reservationField = new RoundedTextField(15);  // Use RoundedTextField
        gbc.gridx = 1;
        guestReservationPanel.add(reservationField, gbc);

        RoundedButton checkReservationButton = new RoundedButton("예약확인");
        checkReservationButton.setBackground(Color.BLACK);
        checkReservationButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        guestReservationPanel.add(checkReservationButton, gbc);

        checkReservationButton.addActionListener(e -> {
            String reservationNumber = reservationField.getText();

            // 예약 확인 로직 추가
            if (reservationNumber.isEmpty()) {
                JOptionPane.showMessageDialog(guestReservationPanel, "예약번호를 입력하세요.");
            } else {
                // 예시: 데이터베이스에서 예약 정보를 확인하는 로직 추가
            	try {
            		switchToPanel(new ReserveResult(ReserveResult.TYPE_TEMP, Integer.parseInt(reservationNumber), mainFrame));
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(guestReservationPanel, "존재하지 않는 예약번호입니다.");
				}
                
            }
        });

        RoundedButton backButton = new RoundedButton("뒤로가기");
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        guestReservationPanel.add(backButton, gbc);

        backButton.addActionListener(e -> showPanel(cardPanel, "Home"));

        return guestReservationPanel;
    }

    public JPanel createSignupPanel(JPanel cardPanel) {
        JPanel signupPanel = new JPanel(new GridBagLayout());
        signupPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("이름:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        signupPanel.add(nameLabel, gbc);

        JTextField nameField = new RoundedTextField(15);  // Use RoundedTextField
        gbc.gridx = 1;
        signupPanel.add(nameField, gbc);

        JLabel idLabel = new JLabel("아이디:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        signupPanel.add(idLabel, gbc);

        JTextField usernameField = new RoundedTextField(15);  // Use RoundedTextField
        gbc.gridx = 1;
        signupPanel.add(usernameField, gbc);

        JLabel pwLabel = new JLabel("비밀번호:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        signupPanel.add(pwLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);  // JPasswordField as it is
        gbc.gridx = 1;
        signupPanel.add(passwordField, gbc);

        JLabel confirmPwLabel = new JLabel("비밀번호 확인:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        signupPanel.add(confirmPwLabel, gbc);

        JPasswordField confirmPasswordField = new JPasswordField(15);  // JPasswordField as it is
        gbc.gridx = 1;
        signupPanel.add(confirmPasswordField, gbc);

        JLabel phoneLabel = new JLabel("전화번호:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        signupPanel.add(phoneLabel, gbc);

        JTextField phoneField = new RoundedTextField(15);  // Use RoundedTextField
        gbc.gridx = 1;
        signupPanel.add(phoneField, gbc);

        // 인증번호 버튼 추가 (전화번호 아래에 위치)
        RoundedButton verifyPhoneButton = new RoundedButton("인증번호 받기");
        verifyPhoneButton.setBackground(Color.BLACK);
        verifyPhoneButton.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 5;
        signupPanel.add(verifyPhoneButton, gbc);

        JLabel verificationCodeLabel = new JLabel("인증번호:");
        gbc.gridx = 0;
        gbc.gridy = 6;
        signupPanel.add(verificationCodeLabel, gbc);

        JTextField verificationCodeField = new RoundedTextField(15);  // Use RoundedTextField
        gbc.gridx = 1;
        gbc.gridy = 6;
        signupPanel.add(verificationCodeField, gbc);

        // 회원가입 버튼
        RoundedButton signupButton = new RoundedButton("회원가입");
        signupButton.setBackground(Color.BLACK);
        signupButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        signupPanel.add(signupButton, gbc);

        signupButton.addActionListener(e -> {
            String name = nameField.getText();
            String userId = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String phone = phoneField.getText();
            String verificationCode = verificationCodeField.getText();

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(signupPanel, "비밀번호가 일치하지 않습니다.");
                return;
            }

            // 전화번호 중복 확인
            UserService userService = new UserService();
           

            // 인증번호 확인 로직
            if (!verificationCode.equals(checkNumber)) {
                JOptionPane.showMessageDialog(signupPanel, "인증번호가 잘못되었습니다.");
                return;
            }

            boolean success = userService.registerUser(userId, password, name, phone);

            if (success) {
                JOptionPane.showMessageDialog(signupPanel, "회원가입 성공!");
                this.userId = userId;
                checkNumber = "????";
                updateHeaderPanel();
                showPanel(cardPanel, "ReservationSystem"); // Move to ReservationSystem
            } else {
                JOptionPane.showMessageDialog(signupPanel, "회원가입 실패: 사용자 이름이 중복되었거나, DB 오류가 발생했습니다.");
            }
        });

        RoundedButton backButton = new RoundedButton("뒤로가기");
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        signupPanel.add(backButton, gbc);

        backButton.addActionListener(e -> showPanel(cardPanel, "Home"));

        // 인증번호 버튼 이벤트
        verifyPhoneButton.addActionListener(e -> {
            if (new UserService().isPhoneNumberDuplicated(phoneField.getText())) {
                 JOptionPane.showMessageDialog(signupPanel, "이미 사용 중인 전화번호입니다.");
                 return;
             }
            checkNumber = CoolSms.checkNumber(phoneField.getText());
            JOptionPane.showMessageDialog(signupPanel, "인증번호가 발송되었습니다.");
        });

        return signupPanel;
    }
    public void updateHeaderPanel() {
    	reviewMain = new ReviewMain(userId, this, 0);
    	boardMain = new BoardMain(this, userId);
    }

    public JPanel createReservationSystemPanel(JPanel cardPanel) {
        JPanel reservationSystemPanel = new JPanel();
        reservationSystemPanel.setBackground(Color.WHITE);
        reservationSystemPanel.setLayout(new BoxLayout(reservationSystemPanel, BoxLayout.Y_AXIS));

        // Logo
        JLabel logoLabel = new JLabel(new ImageIcon(OutbackApp.class.getResource("/log/outback_logo.png")));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        reservationSystemPanel.add(Box.createVerticalStrut(50)); // Add some space at the top
        reservationSystemPanel.add(logoLabel);

        // First row of buttons (Reservation)
        JPanel reservationPanel = new JPanel();
        reservationPanel.setBackground(Color.WHITE);
        RoundedButton makeReservationButton = new RoundedButton("예약하기");
        RoundedButton checkReservationButton = new RoundedButton("예약확인");

        // Setting background and foreground for reservation buttons
        makeReservationButton.setBackground(Color.BLACK);
        makeReservationButton.setForeground(Color.WHITE);
        checkReservationButton.setBackground(Color.BLACK);
        checkReservationButton.setForeground(Color.WHITE);

        reservationPanel.add(makeReservationButton);
        reservationPanel.add(checkReservationButton);

        // Second row of buttons (Board, Review, Logout)
        JPanel otherOptionsPanel = new JPanel();
        otherOptionsPanel.setBackground(Color.WHITE);
        RoundedButton boardButton = new RoundedButton("게시판");
        RoundedButton reviewButton = new RoundedButton("리뷰");
        RoundedButton logoutButton = new RoundedButton("로그아웃");

        // Setting background and foreground for other buttons
        boardButton.setBackground(Color.WHITE);
        boardButton.setForeground(Color.BLACK);
        reviewButton.setBackground(Color.WHITE);
        reviewButton.setForeground(Color.BLACK);
        logoutButton.setBackground(Color.WHITE);
        logoutButton.setForeground(Color.BLACK);

        otherOptionsPanel.add(boardButton);
        otherOptionsPanel.add(reviewButton);
        otherOptionsPanel.add(logoutButton);

        // Adding panels to reservation system panel
        reservationSystemPanel.add(Box.createVerticalStrut(20));
        reservationSystemPanel.add(reservationPanel);
        reservationSystemPanel.add(Box.createVerticalStrut(20));
        reservationSystemPanel.add(otherOptionsPanel);

        // Button actions
        makeReservationButton.addActionListener(e -> new ReserveSetDialog(mainFrame));
        reviewButton.addActionListener(e -> switchToPanel(new ReviewMain(userId, mainFrame, 0)));

        checkReservationButton.addActionListener(e -> {
            try {
                int reserveNum = new ReservationMgr().getReservationNum(userId);
                switchToPanel(new ReserveResult(ReserveResult.TYPE_USER, reserveNum, mainFrame));
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(mainFrame, "예약내역이 존재하지않습니다.");
            }
        });
        boardButton.addActionListener(e -> switchToPanel(new BoardMain(mainFrame, userId)));
        logoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(reservationSystemPanel, "로그아웃되었습니다.");
            showPanel(cardPanel, "Home"); // Return to Home Panel
        });

        return reservationSystemPanel;
    }

    public void showPanel(JPanel cardPanel, String panelName) {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, panelName);
        currentPanel = cardPanel;
        revalidate();
        repaint();
    }

    public void switchToPanel(JPanel panel) {
        remove(currentPanel);
        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();

        currentPanel = panel;
    }

    public static void main(String[] args) {
        new OutbackApp();
    }
}