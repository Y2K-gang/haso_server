package org.example.haso.domain.auth.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;

public record SmsRequest (

        @NotBlank(message = "phoneNumber 공백일 수 없습니다")
        String phoneNumber
) {

}
