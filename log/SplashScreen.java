package log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashScreen extends JPanel {
    private float alpha = 0f; // 투명도 조절을 위한 변수
    private Timer timer;

    public SplashScreen() {
        setBackground(Color.WHITE);

        // 타이머를 설정하여 투명도를 점진적으로 증가시키는 애니메이션 효과
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += 0.05f;
                if (alpha > 1) {
                    alpha = 1;
                    timer.stop();
                    // 애니메이션이 끝난 후 메인 애플리케이션을 실행
                    showMainApp();
                }
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)); // 투명도 설정

        // 로고 이미지 로드
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/log/outback_logo.png"));
        Image logoImage = logoIcon.getImage();

        // 이미지의 중앙 정렬
        int x = (getWidth() - logoImage.getWidth(null)) / 2;
        int y = (getHeight() - logoImage.getHeight(null)) / 2;
        g2d.drawImage(logoImage, x, y, this);
    }

    // 메인 애플리케이션을 표시하는 메서드
    private void showMainApp() {
        JFrame mainFrame = new OutbackApp(); // OutbackApp 메인 애플리케이션 프레임
        mainFrame.setVisible(true);

        // 현재 스플래시 화면을 감추고 메인 화면을 보여줌
        SwingUtilities.getWindowAncestor(this).dispose();
    }

    public static void main(String[] args) {
        JFrame splashFrame = new JFrame("Outback Splash Screen");
        splashFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        splashFrame.setUndecorated(true); // 타이틀 바 제거
        splashFrame.add(new SplashScreen());
        splashFrame.setSize(584, 836); // 스플래시 화면 크기 설정
        splashFrame.setLocationRelativeTo(null); // 화면 중앙에 배치
        splashFrame.setVisible(true);
    }
}