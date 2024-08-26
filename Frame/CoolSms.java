package Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JTextField;

import database.ReservationBean;
import database.ReservationMgr;
import database.UserBean;
import database.UserMgr;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;

public class CoolSms {
    static DefaultMessageService messageService = NurigoApp.INSTANCE.initialize("NCS05PHUJJCQP4KS", "GVI8ZIVSAONHOZGGTXDHCDHXGMW6SSSY", "https://api.coolsms.co.kr");
    
    public static String checkNumber(String Phone) {
    	Random random = new Random();
        int randomNumber = 100000 + random.nextInt(900000); // 100000 ~ 999999
        String Text = "인증번호는 : " + String.valueOf(randomNumber) + " 입니다.";
        Message message = new Message();
        message.setFrom("010-8013-1233"); // 계정에서 등록한 발신번호 입력
        message.setTo(Phone); // 수신번호 입력
        message.setText(Text); // 보낼 문자 내용
        
        try {
            // 메시지 전송
            messageService.send(message);
            System.out.println("Message sent successfully!");
        } catch (NurigoMessageNotReceivedException exception) {
            // 발송에 실패한 메시지 목록 확인
            System.out.println("Failed to send message. Details:");
            System.out.println(exception.getFailedMessageList());
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            // 기타 예외 처리
            System.out.println("An error occurred: " + exception.getMessage());
            
        }
        return	String.valueOf(randomNumber);
    }
    public CoolSms(String userId,ReservationBean bean) {
        // CoolSMS API Key, API Secret을 이용해 서비스 초기화
        // 메시지 객체 생성 및 설정
    	UserBean userBean = new UserMgr().getUser(userId);
    	ReservationBean reservationBean = bean;

    	String Text = ""
    			+ userBean.getUser_name() + "님\n"
    			+ new ReservationMgr().chageFormatTime(reservationBean.getReserve_time())+ "에\n"
    			+ reservationBean.getTbl_num() +"번 테이블  "
    			+ reservationBean.getReserve_member() + "인 예약되었습니다.\n"
    			+ "예약번호 : " + reservationBean.getReserve_num();
        Message message = new Message();
        message.setFrom("010-8013-1233"); // 계정에서 등록한 발신번호 입력
        message.setTo(userBean.getUser_phone()); // 수신번호 입력
        message.setText(Text); // 보낼 문자 내용

        try {
            // 메시지 전송
            messageService.send(message);
            System.out.println("Message sent successfully!");
        } catch (NurigoMessageNotReceivedException exception) {
            // 발송에 실패한 메시지 목록 확인
            System.out.println("Failed to send message. Details:");
            System.out.println(exception.getFailedMessageList());
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            // 기타 예외 처리
            System.out.println("An error occurred: " + exception.getMessage());
            
        }
    }
}
    