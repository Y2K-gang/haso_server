package org.example.haso.domain.auth.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;

public record SignupMemberRequest (

        @NotBlank(message = "userId는 공백일 수 없습니다")
        String userId,

        @NotBlank(message = "name는 공백일 수 없습니다")
        String name,

        @NotBlank(message = "password는 공백일 수 없습니다")
        String password,

        @NotBlank(message = "confirmPassword는 공백일 수 없습니다")
        String confirmPassword,

        @NotBlank(message = "tel는 공백일 수 없습니다")
        String phoneNumber,
//        String tel,

        @NotBlank(message = "storeName는 공백일 수 없습니다")
        String storeName,

        @NotBlank(message = "storeNo는 공백일 수 없습니다")
        String storeNo,

        @NotBlank(message = "faxNo는 공백일 수 없습니다")
        String faxNo,

        @NotBlank(message = "businessNo는 공백일 수 없습니다")
        String businessNo
) {

        @AssertTrue(message = "Passwords do not match")
        public boolean isPasswordMatch() {
                return this.password.equals(this.confirmPassword);
        }
}
