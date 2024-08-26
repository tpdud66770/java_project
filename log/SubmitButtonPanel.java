package log;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class SubmitButtonPanel extends JPanel {
    private JButton submitButton;

    public SubmitButtonPanel(String buttonText, ActionListener actionListener) {
        setLayout(null);
        setBackground(Color.WHITE);
        
        submitButton = new RoundedButton1(buttonText);
        submitButton.setBounds(0, 0, 90, 30);
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Malgun Gothic", Font.BOLD, 12));
        submitButton.setBorder(new LineBorder(Color.BLACK, 1)); // 외곽선 추가
        submitButton.addActionListener(actionListener);
        
        add(submitButton);
    }

    public JButton getSubmitButton() {
        return submitButton;
    }
}
