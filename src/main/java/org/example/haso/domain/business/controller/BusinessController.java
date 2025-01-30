package org.example.haso.domain.business.controller;

import org.example.haso.domain.business.dto.*;
import org.example.haso.domain.business.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    // 거래처 생성(검색) (POST /business/findUser)
    @PostMapping
    public ResponseEntity<BusinessResponse> createBusiness(@RequestBody BusinessRequest businessRequest) {
        BusinessResponse response = businessService.createBusiness(businessRequest);
        return ResponseEntity.status(201).body(response);
    }

    // 거래처 삭제 (DELETE /business/{userId})
    @DeleteMapping("/{userId}")
    public ResponseEntity<Long> deleteBusiness(@PathVariable Long userId) {
        Long deleteId = businessService.deleteBusiness(userId);
        return ResponseEntity.ok(deleteId);
    }

    // 거래처 전체 조회 (GET /business)
    @GetMapping
    public ResponseEntity<List<GetBusinessResponse>> getAllBusiness() {
        List<GetBusinessResponse> responses = businessService.getAllBusiness();
        return ResponseEntity.ok(responses);
    }

    // 거래처 상세 조회 & 거래 내역 전체 조회 (GET /business/{userId}/transactions)
    @GetMapping("/{userId}/transactions")
    public ResponseEntity<List<TransactionResponse>> getHistory(@PathVariable Long userId) {
        List<TransactionResponse> responses = businessService.getHistory(userId);
        return ResponseEntity.ok(responses);
    }

    // 거래 내역 생성 (POST /business/{userId}/transactions)
    @PostMapping("/{userId}/transactions")
    public ResponseEntity<TransactionResponse> createTransaction(@PathVariable Long userId, @RequestBody StatementRequest statementRequest) {
        TransactionResponse response = businessService.createTransaction(userId, statementRequest);
        return ResponseEntity.status(201).body(response);
    }

    // 거래 내역 삭제 (DELETE /business/{userId}/transactions/{txnId})
    @DeleteMapping("/{userId}/transactions/{txnId}")
    public ResponseEntity<Long> deleteTransaction(@PathVariable Long userId, @PathVariable Long txnId) {
        Long deleteId = businessService.deleteTransaction(userId, txnId);
        return ResponseEntity.ok(deleteId);
    }

    // 거래 내역 상세 조회 & 거래명세표 조회 (GET /business/{userId}/transactions/{txnId})
    @GetMapping("/{userId}/transactions/{txnId}")
    public ResponseEntity<StatementResponse> getTransactionStatement(@PathVariable Long userId, @PathVariable Long txnId) {
        StatementResponse response = businessService.getTransactionStatement(userId, txnId);
        return ResponseEntity.ok(response);
    }
}
