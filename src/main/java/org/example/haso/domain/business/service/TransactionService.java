package org.example.haso.domain.business.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.auth.repository.MemberRepository;
import org.example.haso.domain.business.dto.transaction.TransactionResponse;
import org.example.haso.domain.business.model.Business;
import org.example.haso.domain.business.model.BusinessType;
import org.example.haso.domain.business.model.Statement;
import org.example.haso.domain.business.model.Transaction;
import org.example.haso.domain.business.repository.BusinessRepository;
import org.example.haso.domain.business.repository.ItemRepository;
import org.example.haso.domain.business.repository.StatementRepository;
import org.example.haso.domain.business.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private StatementRepository statementRepository;

    @Autowired
    private MemberRepository memberRepository;


    // 공급자용 전체 조회
    @Transactional
    public List<TransactionResponse> getAllSupplyBusiness(MemberEntity member, String userId) {

        MemberEntity memberEntity = memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("입력하신 userId로 user를 찾을 수 없어요"));

        List<Transaction> transactions = transactionRepository.findByUserAndUserIdAndBtype(member.getUserId(), memberEntity.getUserId(), BusinessType.SUPPLY);

//        String user = member.getUserId();
//        List<Statement> statements = statementRepository.findByUserAndUserIdAndBtype(user, memberEntity.getUserId(), BusinessType.SUPPLY);
//        String user = member.getUserId();
//        List<Statement> statements = statementRepository.findByUserIdAndUserIdAndBtype(member.getUserId(), memberEntity.getUserId(), BusinessType.SUPPLY);

        return transactions.stream()
                .map(TransactionResponse::from)
                .toList();
    }

    // 수요자용 전체 조회 (Demand-side query)
    @Transactional
    public List<TransactionResponse> getAllDemandBusiness(MemberEntity member, String userId) {
        MemberEntity memberEntity = memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("입력하신 userId로 user를 찾을 수 없어요"));

        List<Transaction> transactions = transactionRepository.findByUserAndUserIdAndBtype(member.getUserId(), memberEntity.getUserId(), BusinessType.SUPPLY);
        return transactions.stream()
                .map(TransactionResponse::from)
                .toList();
    }

}



