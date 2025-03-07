package org.example.haso.domain.auth.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoolSmsService {

    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecretKey;

    @Value("${coolsms.api.number}")
    private String fromPhoneNumber;

    private static final int CODE_LENGTH = 4;

    public String sendSms(String to) throws CoolsmsException {
        String verificationCode = generateRandomNumber();
        try {
            Message coolsms = new Message(apiKey, apiSecretKey);
            HashMap<String, String> params = new HashMap<>();
            params.put("to", to);
            params.put("from", fromPhoneNumber);
            params.put("type", "sms");
            params.put("text", "인증번호는 [" + verificationCode + "] 입니다.");

            coolsms.send(params);
            log.info("SMS 전송 성공: {} -> {}", fromPhoneNumber, to);

            return verificationCode;
        } catch (Exception e) {
            log.error("SMS 전송 실패: {}", e.getMessage());
            throw new CoolsmsException("Failed to send SMS", 500);
        }

    }

    private String generateRandomNumber() {
        Random rand = new Random();
        StringBuilder numStr = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            numStr.append(rand.nextInt(10));
        }
        return numStr.toString();
    }

    public String validationSMS(String validation, HttpSession session) {
        String storedCode = (String) session.getAttribute("validation");

        if (storedCode == null) {
            return "인증번호 만료, 처음부터 다시 시도해주세요.";
        } else if (storedCode.equals(validation)) {
            session.removeAttribute("validation"); // 인증 성공 후 삭제
            return "인증 완료";
        } else {
            return "인증 실패";
        }
    }
}


//    public ResponseEntity<?> sendSmsToFindEmail(FindEmailRequestDto requestDto) {
//        String name = requestDto.getName();
////        //수신번호 형태에 맞춰 "-"을 ""로 변환
////        String phoneNum = requestDto.getPhoneNum().replaceAll("-","");
//
////        User foundUser = userRepository.findByNameAndPhone(name, phoneNum).orElseThrow(()->
////                new NoSuchElementException("회원이 존재하지 않습니다."));
//
////        String receiverEmail = foundUser.getEmail();
//
//        String verificationCode = validationUtil.createCode();
//        smsUtil.sendOne(phoneNum, verificationCode);
//
////        //인증코드 유효기간 5분 설정
////        redisUtil.setDataExpire(verificationCode, receiverEmail, 60 * 5L);
//
//        return ResponseEntity.ok(new Message("SMS 전송 성공"));
//    }
//
//    // 회원가입 (1단계) - 기본 정보 받고 인증 코드 전송
//    @Transactional
//    public String sendVerificationCode(String tel) {
//
//        // 6자리 인증 코드 생성
//        String verificationCode = generateVerificationCode();
//
////        // Redis에 임시 저장 (5분 후 자동 삭제)
////        redisUtil.setDataExpire(tel,
////                dto.userId() + "|" + dto.name() + "|" + encodedPassword + "|" + dto.tel(),
////                60 * 5L);
//
//        // 인증 코드 전송
//        redisUtil.setDataExpire(tel, verificationCode, 60 * 5L);
//
//        smsUtil.sendOne(tel, verificationCode);
//
//        return "인증 코드가 전송되었습니다.";
//    }
//
//    // 전화번호 인증 (2단계) - 인증 후 추가 정보 입력받아 최종 저장
//    @Transactional
//    public void verifyPhoneNumber(String tel, String inputCode) {
//        String storedCode = redisUtil.getData(tel);
//        System.out.println("Redis에 저장된 인증 코드: " + storedCode);
//
//
//        if (storedCode == null) {
//            System.out.println("Redis에 저장된 인증 코드 없음: " + tel);
//            throw new IllegalArgumentException("인증 코드가 만료되었거나 존재하지 않습니다.");
//        }
//
//        if (!storedCode.equals(inputCode)) {
//            throw new IllegalArgumentException("인증 코드가 일치하지 않습니다.");
//        }
//
//        // 인증 성공 시 Redis에서 저장된 회원 정보 가져오기
//        String tempData = redisUtil.getData(tel);
//        if (tempData == null) {
//            throw new IllegalArgumentException("회원 정보가 만료되었습니다. 다시 시도해주세요.");
//        }
////
////        // Redis에서 가져온 데이터를 분리
////        String[] dataParts = tempData.split("\\|");
////        String userId = dataParts[0];
////        String name = dataParts[1];
////        String encodedPassword = dataParts[2];
//////        String phoneNumber = dataParts[3];
////
////        // 인증 완료 후 Redis에서 삭제
////        redisUtil.deleteData(tel);
////
////        // 최종적으로 DB에 저장할 Entity 생성
////        MemberEntity entity = MemberEntity.builder()
////                .userId(userId)
////                .name(name)
////                .password(encodedPassword)
////                .tel(phoneNumber)
////                .storeName(dto.storeName())
////                .storeNo(dto.storeNo())
////                .faxNo(dto.faxNo())
////                .businessNo(dto.businessNo())
////                .role(MemberType.ROLE_USER)
////                .build();
////
////        repository.save(entity);
//    }
//
//    // 6자리 인증 코드 생성
//    private String generateVerificationCode() {
//        return String.valueOf((int) (Math.random() * 900000) + 100000);
//    }


