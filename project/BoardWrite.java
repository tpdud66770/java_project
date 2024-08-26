package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import Frame.HeaderPanel;
import database.BoardBean;
import database.BoardMgr;
import log.OutbackApp;

public class BoardWrite extends JPanel {
    private ImageIcon logo;
    public static Vector<BoardBean> posts = new Vector<>();   // 게시글 임시저장
    private BoardMain boardMain; // 기존 BoardMain 창을 참조하는 변수
    private JLabel imagePreviewLabel; // 이미지 미리보기 라벨
    private File selectedImageFile; // 선택된 이미지 파일 저장
    private int mileage = 0; // 초기 마일리지
    private JLabel mileageLabel; // 마일리지 점수를 표시할 라벨
    
    private BoardMgr boardMgr; // 데이터베이스 관리 객체
    private String userId; // 로그인한 사용자 아이디
    OutbackApp mainFrame;
    
    // BoardWrite 생성자에 BoardMain 인스턴스를 받는 생성자 추가
    public BoardWrite(BoardMain boardMain, String userId) {
        this.boardMain = boardMain;
        this.boardMgr = new BoardMgr();
        this.userId = boardMain.mainFrame.userId;

        setSize(584, 836);
        setLayout(null);
        
        JPanel topPanel = new HeaderPanel(boardMain.mainFrame);
        topPanel.setBounds(0, 0, 584, 100);
        add(topPanel);

        // 내용 패널
        JPanel contentPanel = new JPanel();
        contentPanel.setBounds(10, 110, 550, 622);
        contentPanel.setLayout(null);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new LineBorder(new Color(211, 211, 211), 1));
        add(contentPanel);

        JLabel titleLabel = new JLabel("제목");
        titleLabel.setBounds(20, 20, 40, 20);
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        contentPanel.add(titleLabel);

        JTextField board_title = new JTextField();
        board_title.setBounds(20, 50, 520, 30);
        board_title.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
        contentPanel.add(board_title);

        // 이미지 미리보기 라벨
        imagePreviewLabel = new JLabel();
        imagePreviewLabel.setBounds(30, 90, 130, 130);
        contentPanel.add(imagePreviewLabel);

        RoundedButton imageButton = new RoundedButton("이미지추가");
        imageButton.setBounds(20, 250, 100, 30);
        imageButton.setBackground(Color.BLACK);
        imageButton.setForeground(Color.WHITE);
        imageButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        contentPanel.add(imageButton);
        
        JTextArea board_content = new JTextArea();
        board_content.setBounds(30, 290, 500, 300);
        board_content.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
        board_content.setLineWrap(true);
        board_content.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(board_content);
        scrollPane.setBounds(20, 290, 520, 310);
        contentPanel.add(scrollPane);

        // 하단 패널
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBounds(0, 740, 584, 60);
        bottomPanel.setLayout(null);
        bottomPanel.setBackground(Color.WHITE);
        add(bottomPanel);

        RoundedButton submitButton = new RoundedButton("등록");
        submitButton.setBounds(242, 10, 100, 40);
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        bottomPanel.add(submitButton);

        // 이미지 버튼 클릭 이벤트
        imageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedImageFile = fileChooser.getSelectedFile();
                    // 이미지 미리보기 설정
                    ImageIcon previewIcon = new ImageIcon(new ImageIcon(selectedImageFile.getPath()).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
                    imagePreviewLabel.setIcon(previewIcon);
                }
            }
        });

        // 등록 버튼 클릭 이벤트
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = board_title.getText();
                String content = board_content.getText();
                String date = new SimpleDateFormat("yyyy.MM.dd").format(new Date()); // 현재 날짜로 설정

                BoardBean newBoard = new BoardBean();
                newBoard.setBoard_id(userId);
                newBoard.setBoard_title(title);
                newBoard.setBoard_content(content);
                newBoard.setBoard_at(date);
                
                // 이미지 파일을 InputStream으로 변환하여 BoardBean에 설정
                if (selectedImageFile != null) {
                    try {
                        InputStream imgStream = new FileInputStream(selectedImageFile);
                        newBoard.setBoard_img(imgStream); // BLOB 필드에 저장할 이미지 데이터 설정
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                boolean boardInsert = boardMgr.insertBoard(newBoard);
                
                if(boardInsert) {
                    Vector<BoardBean> updatedPosts = boardMgr.getPostsByBoardId(userId);
                    
                    // BoardMain 화면 갱신
                    if (boardMain != null) {
                        boardMain.updatePosts(updatedPosts); 
                    }
                    JOptionPane.showMessageDialog(null, "게시글이 등록되었습니다!");
                    boardMain.mainFrame.switchToPanel(boardMain); // BoardMain으로 전환
                } else {
                    JOptionPane.showMessageDialog(null, "게시글 등록에 실패했습니다!");
                }
            }
         });

        setVisible(true);
    }
}
