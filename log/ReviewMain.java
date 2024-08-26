package log;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import Frame.HeaderPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReviewMain extends JPanel {
    private OutbackApp mainframe;
    private String userId;
    private List<Review> reviews = new ArrayList<>();
    private JPanel reviewPanel;
    private int selectStar = 0;
    private ImageIcon selectedImage = null;
    private int mileage = 0;
    private String dbUrl = "jdbc:mysql://113.198.238.95:3306/restaurant";
    private String dbUser = "root";
    private String dbPassword = "1234";
    private HeaderPanel headerPanel;
    private int reserveNum;
    private ImageIcon defaultLogoImage;

    public ReviewMain(String userId, OutbackApp mainframe, int reserveNum) {
        this.reserveNum = reserveNum;
        this.mainframe = mainframe;
        this.userId = userId;
        setSize(584, 836);
        setLayout(null);
        headerPanel = new HeaderPanel(mainframe);
        add(headerPanel);
        
        headerPanel.setBounds(0, 0, 584, 100);

        reviewPanel = new JPanel();
        reviewPanel.setBackground(Color.WHITE);
        reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(reviewPanel);
        scrollPane.setBounds(0, 100, 584, 680);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        add(scrollPane);

        // 기본 로고 이미지 로드
        try {
            BufferedImage logoImage = ImageIO.read(getClass().getResource("/log/outback_logo3.jpg"));
            defaultLogoImage = new ImageIcon(logoImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        loadReviewsFromDB();
        updateReviewPanel();

        setVisible(true);
    }

    public void showReviewDialog() {
        JDialog reviewDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "리뷰 작성", Dialog.ModalityType.APPLICATION_MODAL);
        reviewDialog.setSize(400, 400);
        reviewDialog.setLayout(null);
        reviewDialog.getContentPane().setBackground(Color.WHITE);

        JTextField reviewTextArea = new JTextField(20);
        reviewTextArea.setBounds(10, 10, 365, 100);
        reviewTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        reviewDialog.add(reviewTextArea);

        JLabel userInfoLabel = new JLabel();
        userInfoLabel.setText(userId + "          " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        userInfoLabel.setBounds(10, 260, 200, 30);
        userInfoLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
        reviewDialog.add(userInfoLabel);

        JPanel starPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        starPanel.setBounds(10, 120, 365, 80);
        starPanel.setBackground(Color.WHITE);
        reviewDialog.add(starPanel);

        JButton[] stars = new JButton[5];
        for (int i = 0; i < 5; i++) {
            stars[i] = new JButton(new StarIcon(30, 30, Color.GRAY));
            stars[i].setBorderPainted(false);
            stars[i].setContentAreaFilled(false);
            final int starIndex = i;
            stars[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectStar = starIndex + 1;
                    updateStarIcons(stars);
                }
            });
            starPanel.add(stars[i]);
        }

        RoundedButton1 imageButton = new RoundedButton1("이미지 추가");
        imageButton.setBounds(160, 300, 100, 30);
        imageButton.setBackground(Color.BLACK);
        imageButton.setForeground(Color.WHITE);
        imageButton.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
        reviewDialog.add(imageButton);

        JLabel imagePreviewLabel = new JLabel();
        imagePreviewLabel.setBounds(50, 180, 100, 100);
        reviewDialog.add(imagePreviewLabel);

        imageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(reviewDialog);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        BufferedImage originalImage = ImageIO.read(selectedFile);

                        // 이미지에 테두리 추가
                        BufferedImage borderedImage = addBorderToImage(originalImage, Color.BLACK, 4);

                        // 이미지 미리보기 업데이트
                        ImageIcon imageIcon = new ImageIcon(borderedImage);
                        Image scaledImage = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        selectedImage = new ImageIcon(scaledImage);
                        imagePreviewLabel.setIcon(selectedImage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(reviewDialog, "이미지 로드 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        RoundedButton1 submitButton = new RoundedButton1("등록하기");
        submitButton.setBounds(270, 300, 90, 30);
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
        submitButton.setBorder(new LineBorder(Color.BLACK, 1));
        reviewDialog.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String reviewText = reviewTextArea.getText();
                int rating = selectStar;

                // 이미지를 첨부하지 않은 경우 기본 로고 이미지 사용
                ImageIcon imageToSave = (selectedImage != null) ? selectedImage : defaultLogoImage;

                saveReviewToDB(userId, reviewText, rating, imageToSave, reserveNum);

                // 주문 금액의 1%를 마일리지로 적립
                int reservePrice = getReservationPrice(reserveNum);
                int mileagePoints = calculateMileage(reservePrice);
                updateMileage(userId, mileagePoints);

                Review review = new Review(userId, LocalDate.now(), reviewText, rating, imageToSave);
                reviews.add(review);
                updateReviewPanel();
                reviewDialog.dispose();
            }
        });

        reviewDialog.setLocationRelativeTo(this);
        reviewDialog.setVisible(true);
    }

    private BufferedImage addBorderToImage(BufferedImage originalImage, Color borderColor, int borderWidth) {
        int widthWithBorder = originalImage.getWidth() + 2 * borderWidth;
        int heightWithBorder = originalImage.getHeight() + 2 * borderWidth;
        BufferedImage imageWithBorder = new BufferedImage(widthWithBorder, heightWithBorder, originalImage.getType());
        Graphics2D g2d = imageWithBorder.createGraphics();
        g2d.setColor(borderColor);
        g2d.fillRect(0, 0, widthWithBorder, heightWithBorder);
        g2d.drawImage(originalImage, borderWidth, borderWidth, null);
        g2d.dispose();
        return imageWithBorder;
    }

    private void updateStarIcons(JButton[] stars) {
        for (int i = 0; i < stars.length; i++) {
            if (i < selectStar) {
                stars[i].setIcon(new StarIcon(30, 30, Color.RED));
            } else {
                stars[i].setIcon(new StarIcon(30, 30, Color.GRAY));
            }
        }
    }

    public void updateReviewPanel() {
        headerPanel.updateUserPoint();
        reviewPanel.removeAll();
        loadReviewsFromDB();
        for (Review review : reviews) {
            JPanel containerPanel = new JPanel(null);
            containerPanel.setBackground(Color.WHITE);
            containerPanel.setPreferredSize(new Dimension(550, 150));
            containerPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));

            JPanel imagePanel = new JPanel(null);
            imagePanel.setBounds(60, 25, 100, 100);
            imagePanel.setBackground(Color.WHITE);
            if (review.getImage() != null) {
                Image scaledImage = review.getImage().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                JLabel reviewImageLabel = new JLabel(scaledIcon);
                reviewImageLabel.setBounds(0, 0, 100, 100);
                reviewImageLabel.setBorder(new LineBorder(Color.GRAY, 1));
                imagePanel.add(reviewImageLabel);
            }
            containerPanel.add(imagePanel);

            JPanel textPanel = new JPanel(null);
            textPanel.setBounds(220, 25, 400, 100);
            textPanel.setBackground(Color.WHITE);

            JLabel starLabel = new JLabel(generateStars(review.getRating()));
            starLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
            starLabel.setForeground(Color.RED);
            starLabel.setBounds(0, 0, 200, 30);
            textPanel.add(starLabel);

            JLabel reviewTextLabel = new JLabel(review.getText());
            reviewTextLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
            reviewTextLabel.setBounds(0, 30, 400, 30);
            textPanel.add(reviewTextLabel);

            JLabel userInfoLabel = new JLabel("<html><b>" + review.getUserId() + "</b><br>"
                    + review.getDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + "</html>");
            userInfoLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
            userInfoLabel.setBounds(0, 60, 400, 30);
            textPanel.add(userInfoLabel);

            containerPanel.add(textPanel);
            reviewPanel.add(containerPanel, 0);
        }

        reviewPanel.revalidate();
        reviewPanel.repaint();
    }

    private String generateStars(int rating) {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (i < rating) {
                stars.append("★");
            } else {
                stars.append("☆");
            }
        }
        return stars.toString();
    }

    private void loadReviewsFromDB() {
        try {
            Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM review ORDER BY review_at ASC");
            ResultSet rs = stmt.executeQuery();
            reviews.clear();
            while (rs.next()) {
                String userId = rs.getString("review_id");
                String reviewText = rs.getString("review_content");
                int rating = rs.getInt("review_score");
                byte[] imageBytes = rs.getBytes("review_image");
                ImageIcon imageIcon = (imageBytes != null) ? new ImageIcon(imageBytes) : defaultLogoImage;
                LocalDate reviewDate = rs.getDate("review_at").toLocalDate();
                reviews.add(new Review(userId, reviewDate, reviewText, rating, imageIcon));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveReviewToDB(String userId, String reviewText, int rating, ImageIcon imageIcon, int reserveNum) {
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO review (review_content, review_score, review_image, review_id, review_num) VALUES (?, ?, ?, ?, ?)")) {
            
            stmt.setString(1, reviewText);
            stmt.setInt(2, rating);

            if (imageIcon != null) {
                // ImageIcon을 BufferedImage로 변환
                Image image = imageIcon.getImage();
                BufferedImage bufferedImage = new BufferedImage(
                    image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = bufferedImage.createGraphics();
                g2.drawImage(image, 0, 0, null);
                g2.dispose();

                // 이미지를 ByteArrayOutputStream으로 변환
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "jpg", baos);
                byte[] imageBytes = baos.toByteArray();
                stmt.setBytes(3, imageBytes);  // BLOB 필드에 이미지 데이터를 저장
            } else {
                stmt.setNull(3, java.sql.Types.BLOB);
            }

            stmt.setString(4, userId);
            stmt.setInt(5, reserveNum);
            stmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private int getReservationPrice(int reserveNum) {
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement("SELECT reserve_price FROM reservation WHERE reserve_num = ?")) {
            stmt.setInt(1, reserveNum);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("reserve_price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int calculateMileage(int reservePrice) {
        return (int) (reservePrice * 0.01); // 예약 금액의 1%를 마일리지로 계산
    }

    private void updateMileage(String userId, int mileagePoints) {
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement("UPDATE user SET user_point = user_point + ? WHERE user_id = ?")) {
            stmt.setInt(1, mileagePoints);
            stmt.setString(2, userId);
            stmt.executeUpdate();
            headerPanel.updateUserPoint(); // 마일리지 적립 후 헤더 업데이트
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class Review implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;
    private LocalDate date;
    private String text;
    private int rating;
    private ImageIcon image;

    public Review(String userId, LocalDate date, String text, int rating, ImageIcon image) {
        this.userId = userId;
        this.date = date;
        this.text = text;
        this.rating = rating;
        this.image = image;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public int getRating() {
        return rating;
    }

    public ImageIcon getImage() {
        return image;
    }
}
