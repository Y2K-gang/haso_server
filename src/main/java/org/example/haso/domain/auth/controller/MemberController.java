package org.example.haso.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.dto.RefreshMemberRequest;
import org.example.haso.domain.auth.dto.SigninMemberRequest;
import org.example.haso.domain.auth.dto.SignupMemberRequest;
import org.example.haso.domain.auth.service.MemberService;
import org.example.haso.global.BaseResponse;
import org.example.haso.global.auth.TokenInfo;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@CrossOrigin(origins = "*")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public BaseResponse<TokenInfo> createMember(@Valid @RequestBody SignupMemberRequest dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // 유효성 검사 오류가 있으면 오류 메시지를 반환
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), errorMessage, null);
        }


        return new BaseResponse<> (
                HttpStatus.OK.value(),
                "회원가입을 완료하였습니다",
                memberService.signupMember(dto)
        );
    }

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
