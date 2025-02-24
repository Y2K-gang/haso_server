package org.example.haso.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.dto.BusinessValidateRequest;
import org.example.haso.domain.auth.service.BusinessValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class BusinessValidationController {

    private final BusinessValidationService businessValidationService;

    @PostMapping("/validate")
    public ResponseEntity<String> validateBusinessNumber(@RequestBody BusinessValidateRequest request) {
        System.out.println("Received request: " + request);

        try {
            String result = businessValidationService.validateBusinessNumber(request);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("입력 오류: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("API 요청 중 오류 발생: " + e.getMessage());
        }
    }

}
