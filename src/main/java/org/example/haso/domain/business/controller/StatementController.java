package org.example.haso.domain.business.controller;

import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.entity.MemberEntity;
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
public class StatementController {

    @Autowired
    private BusinessService businessService;

    private StatementService statementService;
    private TransactionService transactionService;

//
//    // 거래처 상세 조회 & 거래 내역 전체 조회 (GET /business/{userId}/transactions)
//    @GetMapping("/{userId}/transactions")
//    public ResponseEntity<List<TransactionResponse>> getHistory(
//            @GetAuthenticatedUser MemberEntity member,
//            @PathVariable Long userId) {
//        List<TransactionResponse> responses = statementService.getHistory(userId);
//        return ResponseEntity.ok(responses);
//    }

    // 공급자용 거래 내역 생성 (POST /business/supply/{userId})
    @PostMapping("/supply/{userId}")
    public ResponseEntity<StatementResponse> createSupplyTransaction(
            @GetAuthenticatedUser MemberEntity member,
            @PathVariable Long userId,
            @RequestBody StatementRequest statementRequest) {
        StatementResponse response = statementService.createSupplyTransaction(userId, statementRequest);
        return ResponseEntity.status(201).body(response);
    }

    // 수요자용 거래 내역 생성 (POST /business/demand/{userId})
    @PostMapping("/demand/{userId}")
    public ResponseEntity<StatementResponse> createDemandTransaction(
            @GetAuthenticatedUser MemberEntity member,
            @PathVariable Long userId,
            @RequestBody StatementRequest statementRequest) {
        StatementResponse response = statementService.createDemandTransaction(userId, statementRequest);
        return ResponseEntity.status(201).body(response);
    }

    // 거래 내역 삭제 (DELETE /business/{userId}/transactions/{txnId})
    @DeleteMapping("/{userId}/transactions/{txnId}")
    public ResponseEntity<Long> deleteTransaction(
            @GetAuthenticatedUser MemberEntity member,
            @PathVariable Long userId,
            @PathVariable Long txnId) {
        Long deleteId = statementService.deleteTransaction(userId, txnId);
        return ResponseEntity.ok(deleteId);
    }

    // 거래명세표 조회 (GET /business/{userId}/transactions/{txnId})
    @GetMapping("/{userId}/transactions/{txnId}")
    public ResponseEntity<StatementResponse> getTransactionStatement(
            @GetAuthenticatedUser MemberEntity member,
            @PathVariable Long userId,
            @PathVariable Long txnId) {
        StatementResponse response = statementService.getTransactionStatement(userId, txnId);
        return ResponseEntity.ok(response);
    }
}
