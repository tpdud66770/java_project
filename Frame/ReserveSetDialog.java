package Frame;

import javax.swing.*;

import database.ReservationBean;
import log.OutbackApp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ReserveSetDialog extends JDialog {
	JDialog me = this;
    JLabel label[] = new JLabel[3];
    RoundedTextField dateField = new RoundedTextField(20);
    RoundedChoice memberChoice;
    RoundedChoice timeChoice;
    String time[] = {"시간 설정","11:00", "13:00", "15:00", "17:00", "19:00", "21:00", "23:00"};
    String member[] = new String[9];
    JPanel panel[] = new JPanel[9];
    RoundedButton btn = new RoundedButton("테이블 예약하기");
    
    Font font = new Font("Malgun Gothic", Font.PLAIN, 20);
    Font labelFont = new Font("Malgun Gothic", Font.BOLD, 16);

    
    public ReserveSetDialog(OutbackApp mf) {
    	mf.reserveBean = new ReservationBean();
        setSize(400, 600);
        getContentPane().setBackground(Color.white);
        setVisible(true);
        setTitle("예약");
        setLayout(null);
        setLocationRelativeTo(mf);
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEFT);
        label[0] = new JLabel("예약 날짜");
        label[1] = new JLabel("인원 수");
        label[2] = new JLabel("예약 시간");
        label[0].setFont(font);
        label[1].setFont(font);
        label[2].setFont(font);
        for (int i = 0; i < label.length; i++) {
        	label[i].setFont(labelFont);
            label[i].setAlignmentX(LEFT_ALIGNMENT);
        }
        for (int i = 0; i < 9; i++) {
        	
            member[i] = String.valueOf(i);
            if(i == 0) member[i] = "인원 설정";
        }
        timeChoice = new RoundedChoice(time);
        timeChoice.setFont(font); 
        memberChoice = new RoundedChoice(member);
        memberChoice.setFont(font);
        
        for (int i = 0; i < panel.length; i++) {
            panel[i] = new JPanel();
            panel[i].setBounds(50, 50 + i * 50, 300, 50);
            panel[i].setLayout(null);
            panel[i].setBackground(Color.white);
        }

        panel[0].add(label[0]);
        label[0].setBounds(0, 0, 300, 50);

        panel[1].add(dateField);
        dateField.setBounds(0, 0, 290, 50);
        dateField.setEditable(false);
        dateField.setFont(font);
        dateField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 내부 여백 설정
        dateField.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
              EnhancedCalendar calendar = new EnhancedCalendar(ReserveSetDialog.this);
                calendar.setVisible(true);
                String selectedDate = calendar.getSelectedDate();
                if (selectedDate != null) {
                    dateField.setText(selectedDate);
                }
           }
            
        });
        panel[2].add(label[1]);
        label[1].setBounds(0, 0, 300, 50);
        panel[3].add(memberChoice);
        memberChoice.setBounds(0, 0, 290, 50);
        memberChoice.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
        panel[4].add(label[2]);
        label[2].setBounds(0, 0, 300, 50);
        panel[5].add(timeChoice);
        timeChoice.setBounds(0, 0, 290, 50);
        timeChoice.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
        panel[7].add(btn);
        btn.setFont(font);
        btn.setBounds(0, 0, 300, 50);
        btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(dateField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(me, "날짜를 선택해주세요.");
				}
				else if(memberChoice.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog(me, "인원수를 정해주세요.");
				}
				else if(timeChoice.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog(me, "시간을 정해주세요.");
				}
				else {
					mf.reserveBean.setUser_id(mf.userId);
					mf.reserveBean.setReserve_member(Integer.parseInt(memberChoice.getSelectedItem().toString()));
					mf.reserveBean.setReserve_time(dateField.getText() + " "+ timeChoice.getSelectedItem());
					mf.switchToPanel(new ReserveTable(mf));
					dispose();
				}
			}
		});
        for (int i = 0; i < panel.length; i++) {
            add(panel[i]);
        }
        validate();
    }
}
