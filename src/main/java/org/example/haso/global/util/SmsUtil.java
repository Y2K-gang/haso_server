package org.example.haso.global.util;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Component;
import net.nurigo.sdk.message.model.Message;
import org.springframework.beans.factory.annotation.Value;
import javax.annotation.PostConstruct;


@Component
public class SmsUtil {

    @Value("${coolsms.api.key}")
    private String apiKey;
    @Value("${coolsms.api.secret}")
    private String apiSecretKey;

    private DefaultMessageService messageService;

    @PostConstruct
    private void init(){
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }

    // 단일 메시지 발송 예
    public SingleMessageSentResponse sendOne(String to, String verificationCode) {
        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력
        message.setFrom("발신번호 입력");
        message.setTo(to);
        message.setText("아래의 인증번호를 입력해주세요\n" + verificationCode);

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        return response;
    }

}