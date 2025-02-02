package org.example.haso.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record SignupMemberRequest (

        @NotBlank
        String userId,

        @NotBlank
        String name,

        @NotBlank
        String password,

        @NotBlank
        String confirmPassword,

        @NotBlank
        String tel,

        @NotBlank
        String storeName,

        @NotBlank
        String storeNo,

        @NotBlank
        String faxNo,

        @NotBlank
        String businessNo
) {
        public boolean isPasswordMatch() {
                return this.password.equals(this.confirmPassword);
        }
}
