package org.example.haso.domain.business.controller;

import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.business.dto.business.BusinessRequest;
import org.example.haso.domain.business.dto.business.BusinessResponse;
import org.example.haso.domain.business.dto.business.GetBusinessResponse;
import org.example.haso.domain.business.dto.statement.StatementRequest;
import org.example.haso.domain.business.dto.statement.StatementResponse;
import org.example.haso.domain.business.dto.transaction.TransactionResponse;
import org.example.haso.domain.business.service.BusinessService;
import org.example.haso.domain.business.service.StatementService;
import org.example.haso.domain.business.service.TransactionService;
import org.example.haso.global.auth.GetAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    private StatementService statementService;
    private TransactionService transactionService;

    // 거래처 검색 (GET /business/search)
    @GetMapping("/search")
    public ResponseEntity<BusinessResponse> search(
            @GetAuthenticatedUser MemberEntity member,
            @RequestParam BusinessRequest request) {
        BusinessResponse response = businessService.search(member, request);
        return ResponseEntity.ok(response);

    }

    // 거래처 생성 (POST /business)
    // 취급물품 가져오는 거 수정
    @PostMapping
    public ResponseEntity<GetBusinessResponse> createBusiness(
            @GetAuthenticatedUser MemberEntity member,
            @RequestBody BusinessRequest request) {
        GetBusinessResponse response = businessService.createBusiness(member, request);
        return ResponseEntity.status(201).body(response);
    }

    // 거래처 삭제 (DELETE /business/{userId})
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteBusiness(
            @GetAuthenticatedUser MemberEntity member,
            @PathVariable String userId) {
        String deleteId = businessService.deleteBusiness(member, userId);
        return ResponseEntity.ok(deleteId);
    }

}
