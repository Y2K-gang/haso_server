package org.example.haso.global.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.haso.global.BaseResponse;
import org.example.haso.global.exception.CustomException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final FilterErrorSender responser;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException exception) {
            responser.send(response, exception);
        }
    }

    @ExceptionHandler(CustomException.class)
    public BaseResponse<?> handleCustomException(CustomException exception) {
        // 여기서 custom exception을 처리하는 로직을 수정하여 더 자세한 오류 메시지를 전송하도록 해주세요.
        return new BaseResponse<>(exception.getStatus(), exception.getMessage(), null);
    }


}
