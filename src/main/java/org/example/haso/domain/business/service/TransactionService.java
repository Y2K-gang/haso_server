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
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private StatementRepository statementRepository;

    @Autowired
    private MemberRepository memberRepository;


    // 공급자용 조회 (SUPPLY)
    @Transactional
    public List<TransactionResponse> getAllSupplyBusiness(MemberEntity member, String userId) {
        return getTransactionsByType(member, userId, BusinessType.SUPPLY);
    }

    // 수요자용 조회 (DEMAND)
    @Transactional
    public List<TransactionResponse> getAllDemandBusiness(MemberEntity member, String userId) {
        return getTransactionsByType(member, userId, BusinessType.DEMAND);
    }

    // 공통 조회 메서드
    private List<TransactionResponse> getTransactionsByType(MemberEntity member, String userId, BusinessType btype) {
        // business 조회
        Business business = businessRepository.findByUserId(userId);
        if (business == null) {
            return List.of(); // business가 없으면 빈 리스트 반환
        }

        // 트랜잭션 조회
        List<Transaction> transactions = transactionRepository.findByBtypeAndUserIdAndBusinessUserId(btype, member.getUserId(), business.getUserId());

        return transactions.stream()
                .map(TransactionResponse::from)
                .toList();
    }


}
