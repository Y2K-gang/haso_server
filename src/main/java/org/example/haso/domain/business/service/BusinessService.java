package org.example.haso.domain.business.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.haso.domain.business.dto.*;
import org.example.haso.domain.business.model.Business;
import org.example.haso.domain.business.model.Item;
import org.example.haso.domain.business.model.Transaction;
import org.example.haso.domain.business.repository.BusinessRepository;
import org.example.haso.domain.business.repository.ItemRepository;
import org.example.haso.domain.business.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessService {

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ItemRepository itemRepository;

    // 거래처 생성
    @Transactional
    public BusinessResponse createBusiness(BusinessRequest businessRequest) {
        Business business = new Business(businessRequest);
        business = businessRepository.save(business);
        return new BusinessResponse(business);
    }

    // 거래처 삭제
    @Transactional
    public Long deleteBusiness(Long userId) {
        businessRepository.deleteById(userId);
        return userId;
    }

    // 거래처 전체 조회
    @Transactional
    public List<GetBusinessResponse> getAllBusiness() {
        List<Business> businesses = businessRepository.findAll();
        return businesses.stream()
                .map(GetBusinessResponse::from)
                .toList();
    }

    // 거래처 상세 조회 & 거래 내역 전체 조회
    @Transactional
    public List<TransactionResponse> getHistory(Long userId) {
        Business business = businessRepository.findById(userId).orElseThrow(() -> new RuntimeException("Business not found"));

        // 거래 내역 찾기
        List<Transaction> transactions = transactionRepository.findByBusiness(business);

        // Transaction -> TransactionResponse 변환
        return transactions.stream()
                .map(transaction -> new TransactionResponse(
                        transaction.getTxnId(),
                        transaction.getContent(),
                        transaction.getDate()))
                .toList();

    }

    // 거래 내역 생성
    @Transactional
    public TransactionResponse createTransaction(Long userId, TransactionRequest transactionRequest) {

        // 거래처 찾기
        Business business = businessRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Business not found"));

        // 거래 내역 생성
        Transaction transaction = Transaction.builder()
                .date(transactionRequest.getDate())
                .content(transactionRequest.getContent())
                .business(business) // 거래처와 연관 설정
                .build();

        transaction = transactionRepository.save(transaction);

        return new TransactionResponse(transaction.getTxnId(), transactionRequest.getContent(), transaction.getDate());
    }

    // 거래 내역 삭제
    @Transactional
    public Long deleteTransaction(Long userId, Long txnId) {

//        // 거래처 찾기
//        Business business = businessRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("Business not found"));

        // 거래 내역 찾기
        Transaction transaction = transactionRepository.findById(txnId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // 거래 내역이 거래처에 속하는지
        if (!transaction.getBusiness().getUserId().equals(userId)) {
            throw new RuntimeException("Transaction does not belong to the specified business");
        }

        transactionRepository.delete(transaction);
        return txnId;
    }


    // 거래 내역 상세 조회 & 거래명세표 조회
    @Transactional
    public StatementResponse getTransactionStatement(Long userId, Long txnId) {
        // 거래 내역 찾기
        Transaction transaction = transactionRepository.findById(txnId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // 거래 내역이 거래처에 속하는지
        if (!transaction.getBusiness().getUserId().equals(userId)) {
            throw new RuntimeException("Transaction does not belong to the specified business");
        }

        // 거래처 찾기
        Business business = transaction.getBusiness();

        // 품목 정보 조회
        Item item = itemRepository.findByTransaction(transaction);

        // StatementResponse 객체로 변환 및 반환
        return StatementResponse.builder()
                .txnId(transaction.getTxnId())
                .date(transaction.getDate().toString()) // 날짜 -> YYYY-MM-DD
                .businessAddress(business.getBusiness_address())
                .faxNumber(business.getFax_number())
                .tradeName(business.getTrade_name())
                .items(List.of(ItemResponse.builder()
                        .itemName(item.getItemName())
                        .itemCategory(item.getItemCategory())
                        .unit(item.getUnit())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .supplyPrice(item.getSupplyPrice())
                        .vatAmount(item.getVatAmount())
                        .build()))
                .build();
    }

}
