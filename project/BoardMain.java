package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import Frame.HeaderPanel;
import database.BoardBean;
import database.BoardMgr;
import log.OutbackApp;
import log.ReviewMain;

public class BoardMain extends JPanel {
    private ImageIcon logo;
    private JPanel listPanel; // 게시글 리스트를 담는 패널
    private int mileage = 0; // 초기 마일리지
    private JLabel mileageLabel; // 마일리지 점수를 표시할 라벨
    public BoardMain me = this;
    OutbackApp mainFrame;
    private String userId;
    private BoardMgr boardMgr;
    public HeaderPanel headerPanel;
    public BoardMain(OutbackApp mainFrame, String userId) {
    	this.mainFrame = mainFrame;
    	this.userId = userId;
    	this.boardMgr = new BoardMgr();
    	
        setSize(584, 836);
        setLayout(null);
        setBackground(Color.WHITE);
        
        headerPanel = new HeaderPanel(mainFrame);
        headerPanel.setBounds(0, 0, 584, 100);
        add(headerPanel);

        
        
        // 게시글 리스트를 담을 패널
        listPanel = new JPanel();
        listPanel.setLayout(null);
        listPanel.setBackground(Color.WHITE);
        
        
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBounds(0, 100, 584, 640);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // 가로 스크롤 비활성화
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED); // 세로 스크롤 활성화
        add(scrollPane);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBounds(0, 740, 584, 60);
        bottomPanel.setLayout(null);
        bottomPanel.setBackground(Color.WHITE);
        add(bottomPanel);
        
        RoundedButton writeButton = new RoundedButton("글쓰기");
        writeButton.setBounds(242, 10, 100, 40);
        writeButton.setBackground(Color.BLACK);
        writeButton.setForeground(Color.WHITE);
        writeButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        if(!mainFrame.isManager) {
        	bottomPanel.add(writeButton);
        }
        
      
        

        // 글쓰기 버튼 클릭 이벤트
        writeButton.addActionListener(new ActionListener() {
			
        	@Override
			public void actionPerformed(ActionEvent e) {
				BoardWrite boardwrite = new BoardWrite(BoardMain.this, mainFrame.userId);
				mainFrame.switchToPanel(boardwrite);
			}
        });

        Vector<BoardBean> savedPosts = boardMgr.getPostsByBoardId(mainFrame.userId);
        if(savedPosts != null) {
        	updatePosts(savedPosts);
        }
    
        

        setVisible(true);
    }
    // 게시글을 업데이트하고 화면에 표시하는 메서드
    public void updatePosts(Vector<BoardBean> posts) {
        listPanel.removeAll(); // 기존 게시글 제거
        headerPanel.updateUserPoint();

        int yOffset = 10;
        for (int i = posts.size() - 1; i >= 0; i--) {
            BoardBean post = posts.get(i);
            
            JPanel postPanel = new JPanel();
            postPanel.setLayout(null);
            postPanel.setBounds(10, yOffset, 540, 70);
            postPanel.setBackground(Color.WHITE);
            postPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, false));
            
            // 작성자, 날짜, 제목
            JLabel userLabel = new JLabel(post.getBoard_id());
            userLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
            userLabel.setBounds(20, 10, 200, 20);
            postPanel.add(userLabel);

            JLabel dateLabel = new JLabel(post.getBoard_at());
            dateLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
            dateLabel.setBounds(20, 30, 200, 20);
            postPanel.add(dateLabel);
            System.out.println(post.getBoard_title());
            JLabel titleLabel = new JLabel(post.getBoard_title());
            titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
            titleLabel.setBounds(100, 20, 500, 20);
            postPanel.add(titleLabel);
            
            // 게시글 패널 클릭 이벤트
            postPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                	BoardContect boardContent = new BoardContect(BoardMain.this, post);
                    mainFrame.switchToPanel(boardContent); // BoardContect 창 열기
                }
            });

            listPanel.add(postPanel);
            yOffset += 80;
        }
        listPanel.setPreferredSize(new java.awt.Dimension(584, yOffset)); // 전체 패널의 크기를 설정하여 스크롤이 작동하도록 함
        listPanel.revalidate();
        listPanel.repaint();
        
    }
    
    
}
