package log;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class BoradWrite extends JFrame {
    private ImageIcon logo;

    public BoradWrite() {
        setTitle("리뷰");
        setSize(584, 836);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        
        // 상단 패널
        JPanel topPanel = new JPanel();
        topPanel.setBounds(0, 0, 584, 50);
        topPanel.setLayout(null);
        add(topPanel);
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(new EmptyBorder(0, 0, 0, 0)); // 테두리 제거

        logo = new ImageIcon("project/logo.png");
        Image scaledLogo = logo.getImage().getScaledInstance(130, 40, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setBounds(10, 5, 150, 40);
        topPanel.add(logoLabel);

        
        JButton reviewButton = new ReviewButton();
        topPanel.add(reviewButton);

        JButton boardButton = new BoardButton1();
        topPanel.add(boardButton);

        JButton logoutButton = new LogoutButton();
        topPanel.add(logoutButton);

        // 내용 패널
        RoundedPanel contentPanel = new RoundedPanel(20);
        contentPanel.setBounds(10, 60, 554, 650);
        contentPanel.setLayout(null);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new LineBorder(new Color(211, 211, 211), 1));
        add(contentPanel);

        JLabel titleLabel = new JLabel("제목");
        titleLabel.setBounds(30, 20, 40, 20);
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        contentPanel.add(titleLabel);

        JTextField titleField = new JTextField("제목");
        titleField.setBounds(30, 20, 520, 30);
        titleField.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
        titleField.setBorder(new EmptyBorder(0, 0, 0, 0)); // 기본 외곽선 제거
        contentPanel.add(titleField);

        ImageIcon imageIcon = new ImageIcon("project/image.png");
        Image scaledImageIcon = imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JButton imageButton = new JButton(new ImageIcon(scaledImageIcon));
        imageButton.setBounds(20, 100, 30, 30);
        imageButton.setBackground(Color.WHITE);
        imageButton.setBorder(null);
        contentPanel.add(imageButton);

        JTextArea board_content = new JTextArea();
        board_content.setBounds(30, 140, 500, 430);
        board_content.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
        board_content.setBorder(new EmptyBorder(0, 0, 0, 0)); // 기본 외곽선 제거
        board_content.setLineWrap(true);
        board_content.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(board_content);
        scrollPane.setBounds(20, 140, 544, 460);
        contentPanel.add(scrollPane);

        // 하단 패널
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBounds(0, 740, 584, 60);
        bottomPanel.setLayout(null);
        bottomPanel.setBackground(Color.WHITE);
        add(bottomPanel);

        RoundedButton1 submitButton = new RoundedButton1("등록");
        submitButton.setBounds(242, 10, 100, 40);
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        bottomPanel.add(submitButton);
        
        setVisible(true);
    }

    public static void main(String[] args) {
        new BoradWrite();
    }
}