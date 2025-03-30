package org.example.haso.domain.auth.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.example.haso.domain.auth.service.CoolSmsService;
import org.example.haso.global.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class SmsController {

    private final CoolSmsService coolSmsService;

    @PostMapping("/send")
    public ResponseEntity<BaseResponse<String>> sendSms(@RequestBody String phoneNumber, HttpSession session) {
//        String phoneNumber = body.get("phoneNumber");
        log.info("Sending SMS to(controller): {}", phoneNumber);

        try {
            String generatedCode = coolSmsService.sendSms(phoneNumber, session);
            return ResponseEntity.ok(new BaseResponse<>(
                    HttpStatus.OK.value(),
                    "인증번호가 전송되었습니다.",
                    generatedCode
            ));
        } catch (CoolsmsException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "SMS 전송 실패", null));
        }
    }


    @GetMapping("/validation/phone")
    public ResponseEntity<BaseResponse<String>> validationSMS(@RequestParam String validation, HttpSession session) {
        String result = coolSmsService.validationSMS(validation, session);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), result, null));
    }
}



//
//    // 회원가입 -인증 코드 전
//    @PostMapping("/signup/verify")
//    public ResponseEntity<BaseResponse<String>> sendVerificationCode(
////            @Valid @RequestBody SignupMemberRequest dto,
//            @RequestBody String tel
////            BindingResult bindingResult
//    ) {
////        if (bindingResult.hasErrors()) {
////            String errorMessage = bindingResult.getAllErrors().stream()
////                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
////                    .collect(Collectors.joining(", "));
////            return ResponseEntity.badRequest().body(new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), errorMessage, null));
////        }
//
//        return ResponseEntity.ok(new BaseResponse<>(
//                HttpStatus.OK.value(),
//                memberService.sendVerificationCode(tel),
//                null
//        ));
//    }
//
//    // 전화번호 인증 - 인증
//    @PostMapping("/verify")
//    public ResponseEntity<String> verifyPhoneNumber(
//            @RequestParam String tel,
//            @RequestParam String code
////            @RequestBody SignupMemberRequest dto
//    ) {
//
//        memberService.verifyPhoneNumber(tel, code);
//        return ResponseEntity.ok("휴대폰 인증 완료");
//    }
