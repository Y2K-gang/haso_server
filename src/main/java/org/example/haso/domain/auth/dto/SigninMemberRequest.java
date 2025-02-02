package org.example.haso.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record SigninMemberRequest (

        @NotBlank
        String userId,

        @NotBlank
        String password

) { }