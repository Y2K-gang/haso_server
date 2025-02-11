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
public class TransactionController {

    @Autowired
    private BusinessService businessService;

    private StatementService statementService;
    private TransactionService transactionService;


    // 공급자용 전체 조회 (GET /business/supply/{userId})
    @GetMapping("/supply/{userId}")
    public ResponseEntity<List<TransactionResponse>> getAllSupplyBusiness(
            @GetAuthenticatedUser MemberEntity member,
            @PathVariable String userId) {
        List<TransactionResponse> responses = transactionService.getAllSupplyBusiness(member, userId);
        return ResponseEntity.ok(responses);
    }

    // 수요자용 전체 조회 (GET /business/demand/{userId})
    @GetMapping("/demand/{userId}")
    public ResponseEntity<List<TransactionResponse>> getAllDemandBusiness(
            @GetAuthenticatedUser MemberEntity member,
            @PathVariable String userId) {
        List<TransactionResponse> responses = transactionService.getAllDemandBusiness(member, userId);
        return ResponseEntity.ok(responses);
    }

}
