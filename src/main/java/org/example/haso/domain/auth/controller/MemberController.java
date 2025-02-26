package org.example.haso.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.dto.RefreshMemberRequest;
import org.example.haso.domain.auth.dto.SigninMemberRequest;
import org.example.haso.domain.auth.dto.SignupMemberRequest;
import org.example.haso.domain.auth.service.MemberService;
import org.example.haso.global.BaseResponse;
import org.example.haso.global.auth.TokenInfo;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8345")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<TokenInfo>> createMember(@Valid @RequestBody SignupMemberRequest dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            BaseResponse<TokenInfo> response = new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), errorMessage, null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        BaseResponse<TokenInfo> response = new BaseResponse<>(
                HttpStatus.OK.value(),
                "회원가입을 완료하였습니다",
                memberService.signupMember(dto)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    // 회원가입 - 기본 정보 받고 인증 코드 전송
//    @PostMapping("/signup")
//    public ResponseEntity<BaseResponse<String>> sendVerificationCode(@Valid @RequestBody SignupMemberRequest dto, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            String errorMessage = bindingResult.getAllErrors().stream()
//                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                    .collect(Collectors.joining(", "));
//            return ResponseEntity.badRequest().body(new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), errorMessage, null));
//        }
//
//        return ResponseEntity.ok(new BaseResponse<>(
//                HttpStatus.OK.value(),
//                memberService.sendVerificationCode(dto),
//                null
//        ));
//    }
//
//    // 전화번호 인증 - 인증 후 나머지 정보 입력받고 최종 저장
//    @PostMapping("/verify")
//    public ResponseEntity<BaseResponse<TokenInfo>> verifyPhoneNumber(
//            @RequestParam String tel,
//            @RequestParam String code,
//            @RequestBody SignupMemberRequest dto) {
//
//        return ResponseEntity.ok(new BaseResponse<>(
//                HttpStatus.OK.value(),
//                "전화번호 인증이 완료되었습니다.",
//                memberService.verifyPhoneNumber(tel, code, dto)
//        ));
//    }

    @PostMapping("/signin")
    public BaseResponse<TokenInfo> getMember(@RequestBody SigninMemberRequest dto) {
        return new BaseResponse<>(
                HttpStatus.OK.value(),
                "로그인 완료",
                memberService.signinMember(dto)
        );
    }

    @PostMapping("/refresh")
    public BaseResponse<String> refreshMember(@RequestBody RefreshMemberRequest dto) {
        return new BaseResponse<>(
                HttpStatus.OK.value(),
                "refresh 완료",
                memberService.refreshMember(dto)
        );

    }

}
