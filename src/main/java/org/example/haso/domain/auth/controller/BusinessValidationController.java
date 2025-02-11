package org.example.haso.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.dto.BusinessValidateRequest;
import org.example.haso.domain.auth.service.BusinessValidationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class BusinessValidationController {

    private final BusinessValidationService businessValidationService;

    @PostMapping("/validate")
    public String validateBusinessNumber(@RequestBody BusinessValidateRequest request) {
        // API 호출 및 결과 처리
        return businessValidationService.validateBusinessNumber(request);
    }

}
