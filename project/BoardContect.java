package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import Frame.HeaderPanel;
import database.AnswerBean;
import database.BoardBean;
import database.BoardMgr;
import log.OutbackApp;
import database.AnswerMgr;

public class BoardContect extends JPanel {
    private ImageIcon logo;
    private int mileage = 0; // 초기 마일리지
    private JLabel mileageLabel; // 마일리지 점수를 표시할 라벨
    
    private Vector<AnswerBean> comments;    // 댓글 리스트
    private JPanel commentPanel;  // 댓글 표시할 패널
    private JScrollPane commentScrollPane; // 댓글 스크롤 패널
    
    private BoardMain boardMain;
    private BoardBean post;
    private String userId; //현재 로그인한 사용자 아이디
    private AnswerMgr answerMgr; //댓글 관리 객체
    public BoardMgr boardMgr; // BoardMgr 객체 선언
    private JTextField commentInput;
    OutbackApp mainFrame;
    
    public BoardContect(BoardMain boardMain, BoardBean post) {
        this.boardMain = boardMain;
        this.post = post;
        this.userId = boardMain.mainFrame.userId;  
        this.answerMgr = new AnswerMgr();  //객체 생성
        this.mainFrame = mainFrame;
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBounds(0, 740, 584, 100); // 높이를 100으로 조정하여 댓글 입력 영역 포함
        bottomPanel.setLayout(null);
        bottomPanel.setBackground(Color.WHITE);

        if(boardMain.mainFrame.isManager) {
            add(bottomPanel);
            commentInput = new JTextField();
            commentInput.setBounds(20, 670, 540, 30);
            add(commentInput);
            
            // 엔터키 입력 리스너 추가
            commentInput.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        postComment();
                    }
                }
            });
        }
        
        // 댓글 쓰기 버튼 추가
        RoundedButton comentButton = new RoundedButton("댓글 쓰기");
        comentButton.setBounds(242, 10, 100, 40);
        comentButton.setBackground(Color.BLACK);
        comentButton.setForeground(Color.WHITE);
        comentButton.setFont(new Font("Malgun Gothic", Font.BOLD, 12));
        comentButton.setBorder(new LineBorder(Color.BLACK, 1)); // 외곽선 추가
        bottomPanel.add(comentButton);

        // 댓글 쓰기 버튼 액션 리스너
        comentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postComment();
            }
        });

        initializeUI();
        loadComments();
    }

    private void initializeUI() {
        setSize(584, 836);
        setLayout(null);
        setBackground(Color.WHITE);

        JPanel topPanel = new HeaderPanel(boardMain.mainFrame);
        topPanel.setBounds(0, 0, 584, 100);
        add(topPanel);

        //목록 버튼
        RoundedButton listBtn = new RoundedButton("목록");
        listBtn.setBounds(430, 100, 53, 24);
        listBtn.setBackground(Color.BLACK);
        listBtn.setForeground(Color.WHITE);
        listBtn.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
        listBtn.setBorder(new LineBorder(Color.BLACK, 1)); // 외곽선 추가
        add(listBtn);
        
        listBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardMain.mainFrame.switchToPanel(boardMain); // BoardMain으로 전환      
            }
        });
        
        // 작성자 정보(회원 아이디)
        JLabel userIdLabel = new JLabel(post.getBoard_id());
        userIdLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
        userIdLabel.setBounds(45, 103, 180, 20);
        add(userIdLabel);

        // 작성일자
        JLabel dateLabel = new JLabel(post.getBoard_at());
        dateLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
        dateLabel.setBounds(144, 103, 180, 20);
        add(dateLabel);

        // 게시글 제목
        JLabel titleLabel = new JLabel(post.getBoard_title());
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 22));
        titleLabel.setBounds(45, 152, 300, 20);
        add(titleLabel);

        // 이미지 추가 및 게시글 내용 설정
        addContentArea();

        // 댓글 표시 부분 설정
        setupCommentSection();
    }

    private void addContentArea() {
        JTextArea contentArea;
        if (post.getBoard_img() != null) {
            try {
                InputStream imgStream = post.getBoard_img();
                BufferedImage bufferedImage = ImageIO.read(imgStream);

                if (bufferedImage != null) {
                    ImageIcon postImage = new ImageIcon(bufferedImage);
                    Image originalImage = postImage.getImage();

                    int width = originalImage.getWidth(null);
                    int height = originalImage.getHeight(null);
                    float aspectRatio = (float) width / height;

                    int newWidth = 130;
                    int newHeight = 130;

                    if (aspectRatio > 1) { // 가로가 더 긴 경우
                        newHeight = (int) (newWidth / aspectRatio);
                    } else if (aspectRatio < 1) { // 세로가 더 긴 경우
                        newWidth = (int) (newHeight * aspectRatio);
                    }

                    Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                    JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                    imageLabel.setBounds(45, 190, newWidth, newHeight); 
                    add(imageLabel);

                    contentArea = new JTextArea(post.getBoard_content());
                    contentArea.setBounds(45, 330, 350, 200);
                } else {
                    contentArea = new JTextArea(post.getBoard_content());
                    contentArea.setBounds(45, 190, 350, 200);
                }
            } catch (Exception e) {
                e.printStackTrace();
                contentArea = new JTextArea(post.getBoard_content());
                contentArea.setBounds(45, 190, 350, 200);
            }
        } else {
            contentArea = new JTextArea(post.getBoard_content());
            contentArea.setBounds(45, 190, 350, 200);
        }
        
        contentArea.setFont(new Font("Malgun Gothic", Font.PLAIN, 17));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        add(contentArea);
    }

    private void setupCommentSection() {
        // 댓글 패널
        commentPanel = new JPanel();
        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));
        commentPanel.setBackground(Color.WHITE);

        // 댓글 스크롤 패널
        commentScrollPane = new JScrollPane(commentPanel);
        commentScrollPane.setBounds(25, 500, 540, 150); 
        commentScrollPane.setBorder(null);
        commentScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 가로 스크롤 제거
        add(commentScrollPane);
    }

    private void loadComments() {
        int boardNum = post.getBoard_num();
        
        comments = answerMgr.loadComments(boardNum);
        
        if(comments != null && !comments.isEmpty()) {
            displayComments();
        } else {
            System.out.println("아직 달린 댓글이 없습니다..." + post.getBoard_num());
        }
    }

    // 댓글을 게시하는 메소드
    private void postComment() {
        String newComment = commentInput.getText();
        if (!newComment.isEmpty()) {
            // 새 댓글을 AnswerBean 객체로 만들어서 데이터베이스에 저장
            AnswerBean comment = new AnswerBean();
            comment.setAnswer_id(userId); // 현재 사용자 ID 설정
            comment.setAnswer_content(newComment);
            comment.setBoard_num(post.getBoard_num());
            comment.setAnswer_at(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

            answerMgr.insertComment(comment);

            loadComments();

            commentInput.setText("");
        }
    }

    // 댓글 목록을 표시하는 메소드
    private void displayComments() {
        commentPanel.removeAll(); // 기존 댓글 제거

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd"); // 입력 형식 설정 (DB에서 가져온 형식에 맞게 수정)
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy.MM.dd"); // 출력 형식 설정

        for (AnswerBean comment : comments) {
            JPanel singleCommentPanel = new JPanel();
            singleCommentPanel.setLayout(null); // 레이아웃을 null로 설정하여 위치를 직접 지정
            singleCommentPanel.setBackground(Color.WHITE);

            JLabel nameLabel = new JLabel(comment.getAnswer_id());
            nameLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 12));
            nameLabel.setBounds(20, 30, 95, 15); // 위치 조정
            singleCommentPanel.add(nameLabel);

            // 시간 포맷 변환
            String formattedDate = comment.getAnswer_at(); // 기본값은 원본 문자열로 설정
            try {
                Date parsedDate = inputFormat.parse(comment.getAnswer_at()); // String을 Date로 변환
                formattedDate = outputFormat.format(parsedDate); // Date를 원하는 출력 형식으로 변환
            } catch (Exception e) {
                e.printStackTrace(); // 변환 오류가 발생할 경우 기본값(원본 문자열)을 유지하고 로그에 오류 출력
            }

            JLabel timeLabel = new JLabel(formattedDate);
            timeLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
            timeLabel.setBounds(120, 30, 200, 15); // 위치 조정
            singleCommentPanel.add(timeLabel);
            
            JTextArea commentText = new JTextArea(comment.getAnswer_content());
            commentText.setLineWrap(true);
            commentText.setWrapStyleWord(true);
            commentText.setEditable(false);
            commentText.setBackground(null); 
            commentText.setBorder(null); 
            commentText.setBounds(20, 55, 450, 40); // 위치 조정
            singleCommentPanel.add(commentText);
            
            JSeparator separator = new JSeparator();
            separator.setBounds(10, 105, 520, 1); // 위치와 크기 조정
            separator.setForeground(Color.BLACK); // 구분선 검은색으로 설정
            singleCommentPanel.add(separator);

            singleCommentPanel.setPreferredSize(new java.awt.Dimension(540, 90)); // 패널 크기 조정
            commentPanel.add(singleCommentPanel);
        }

        // 스크롤바를 맨 위로 설정
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                commentScrollPane.getVerticalScrollBar().setValue(0);
            }
        });

        commentPanel.revalidate();
        commentPanel.repaint();
    }
}
