package log;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class ImageSelectPanel extends JPanel {
    private ImageIcon selectedImage; // 선택된 이미지 아이콘
    private JLabel imagePreviewLabel; // 이미지 미리보기 라벨

    public ImageSelectPanel() {
        setLayout(null);
        setBackground(Color.WHITE);
        setBorder(new LineBorder(Color.BLACK, 1)); // 외곽선 추가 (원하는 경우)
        setPreferredSize(new Dimension(400, 150));

        imagePreviewLabel = new JLabel();
        imagePreviewLabel.setBounds(50, 20, 100, 100); // 이미지 미리보기 위치
        add(imagePreviewLabel);

        RoundedButton1 imageButton = new RoundedButton1("이미지 추가");
        imageButton.setBounds(170, 60, 90, 30);
        imageButton.setBackground(Color.BLACK);
        imageButton.setForeground(Color.WHITE);
        imageButton.setFont(new Font("Malgun Gothic", Font.BOLD, 12));
        add(imageButton);

        imageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(ImageSelectPanel.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    ImageIcon imageIcon = new ImageIcon(selectedFile.getPath());
                    Image scaledImage = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    selectedImage = new ImageIcon(scaledImage);
                    imagePreviewLabel.setIcon(selectedImage); // 이미지 미리보기 설정
                }
            }
        });
    }

    public ImageIcon getSelectedImage() {
        return selectedImage;
    }
}