package org.example.haso.domain.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.haso.global.exception.CustomException;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberException {

    ALREADY_EXIST(CustomException.of(HttpStatus.BAD_REQUEST.value(), "이미 존재하는 유저입니다")),
    NOT_EXIST(CustomException.of(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 유저입니다")),
    PASSWORD_NOT_MATCH(CustomException.of(HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다"));

    private final CustomException exception;

}