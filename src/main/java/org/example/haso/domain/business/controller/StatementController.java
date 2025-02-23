package org.example.haso.domain.business.controller;

import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.business.dto.statement.GetStatementResponse;
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

    @Autowired
    private StatementService statementService;

    @Autowired
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
            @PathVariable String userId,
            @RequestBody StatementRequest statementRequest) {
        StatementResponse response = statementService.createSupplyTransaction(member, userId, statementRequest);
        return ResponseEntity.status(201).body(response);
    }

    // 수요자용 거래 내역 생성 (POST /business/demand/{userId})
    @PostMapping("/demand/{userId}")
    public ResponseEntity<StatementResponse> createDemandTransaction(
            @GetAuthenticatedUser MemberEntity member,
            @PathVariable String userId,
            @RequestBody StatementRequest statementRequest) {
        StatementResponse response = statementService.createDemandTransaction(member, userId, statementRequest);
        return ResponseEntity.status(201).body(response);
    }

    // 거래 내역 삭제 (DELETE /business/{userId}/transactions/{txnId})
    @DeleteMapping("/{userId}/transactions/{txnId}")
    public ResponseEntity<Integer> deleteTransaction(
            @GetAuthenticatedUser MemberEntity member,
            @PathVariable String userId,
            @PathVariable int transactionId) {
        int deleteId = statementService.deleteTransaction(member, userId, transactionId);
        return ResponseEntity.ok(deleteId);
    }

    // 거래명세표 조회 (GET /business/{userId}/transactions/{transactionId})
    @GetMapping("/{userId}/transactions/{transactionId}")
    public ResponseEntity<GetStatementResponse> getTransactionStatement(
            @GetAuthenticatedUser MemberEntity member,
            @PathVariable String userId,
            @PathVariable int transactionId) {
        GetStatementResponse response = statementService.getTransactionStatement(member, userId, transactionId);
        return ResponseEntity.ok(response);
    }
}
