package org.example.haso.domain.auth.dto;

import jakarta.validation.constraints.AssertTrue;
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

        @AssertTrue(message = "Passwords do not match")
        public boolean isPasswordMatch() {
                return this.password.equals(this.confirmPassword);
        }
}
