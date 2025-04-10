package org.example.haso.domain.business.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.auth.repository.MemberRepository;
import org.example.haso.domain.business.dto.item.GetItemResponse;
import org.example.haso.domain.business.dto.item.ItemResponse;
import org.example.haso.domain.business.dto.statement.GetStatementResponse;
import org.example.haso.domain.business.dto.statement.StatementRequest;
import org.example.haso.domain.business.dto.statement.StatementResponse;
import org.example.haso.domain.business.dto.transaction.TransactionResponse;
import org.example.haso.domain.business.model.*;
import org.example.haso.domain.business.repository.BusinessRepository;
import org.example.haso.domain.business.repository.ItemRepository;
import org.example.haso.domain.business.repository.StatementRepository;
import org.example.haso.domain.business.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatementService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private StatementRepository statementRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public StatementResponse createStatement(MemberEntity member, String userId, StatementRequest statementRequest, BusinessType btype) {
        // 거래처 찾기
        Business business = businessRepository.findByUserId(userId);

        if (business == null) {
            System.out.println("Business not found for userId: " + member.getUserId());
            throw new IllegalArgumentException("Business not found");
        }

        Transaction transaction = createTransaction(member, userId, business, statementRequest, btype);


        // Statement 객체 생성 및 저장
        Statement statement = Statement.builder()
                .user(member.getUserId())
                .date(statementRequest.getDate())
//                .tel(statementRequest.getTel())
                .phoneNumber(statementRequest.getPhoneNumber())
                .faxNumber(statementRequest.getFaxNumber())
                .businessNo(statementRequest.getBusinessNo())
                .tradeName(statementRequest.getTradeName())
                .businessAddress(statementRequest.getBusinessAddress())
                .name(statementRequest.getName())
                .business(business)
                .btype(btype)
                .unit_auto(statementRequest.getUnit_auto())
                .quantity_auto(statementRequest.getQuantity_auto())
                .vat(statementRequest.getVat())
                .total(statementRequest.getTotal())
//                .acquirerName(statementRequest.getAcquirerName())
                .acquirerName(null)
                .out_amt(statementRequest.getOut_amt())
                .dep_acc(statementRequest.getDep_acc())
                .transaction(transaction)
                .build();

        if (btype.equals(BusinessType.SUPPLY)) { // 공급
            statement.setAcquirerName(statementRequest.getAcquirerName());
        } else { // 수요
            statement.setAcquirerName(member.getUserId());
        }

        statement = statementRepository.save(statement);


        // Item 객체 생성
        List<Item> items = statementRequest.getItems().stream()
                .map(itemRequest -> Item.builder()
                        .itemName(itemRequest.getItemName())
                        .unit(itemRequest.getUnit())
                        .quantity(itemRequest.getQuantity())
                        .unitPrice(itemRequest.getUnitPrice())
                        .supplyPrice(itemRequest.getSupplyPrice())
                        .vatAmount(itemRequest.getVatAmount())
                        .build())
                .collect(Collectors.toList());

        for (Item item : items) {
            item.setStatement(statement);
        }

        // 품목 리스트 저장
        itemRepository.saveAll(items);


        return new StatementResponse(
                statement.getTxnId(),
                statement.getBtype(),
                items.stream().map(item -> new ItemResponse(
                        item.getItemId()
                )).collect(Collectors.toList())
        );
    }


    @Transactional
    public StatementResponse createSupplyTransaction(MemberEntity member, String userId, StatementRequest statementRequest) {
        return createStatement(member, userId, statementRequest, BusinessType.SUPPLY);
    }

    @Transactional
    public StatementResponse createDemandTransaction(MemberEntity member, String userId, StatementRequest statementRequest) {

        return createStatement(member, userId, statementRequest, BusinessType.DEMAND);
    }

    @Transactional
    public GetStatementResponse getTransactionStatement(MemberEntity member, String userId, int transactionId) {

//        String user = member.getUserId();  // 여기서 userId를 가져옵니다.

        Statement statement = statementRepository
                .findByTransaction_TxnId(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Statement not found for transactionId: " + transactionId));

        Business business = statement.getBusiness();

        List<Item> items = itemRepository.findByStatement(statement);

        List<GetItemResponse> itemResponses = items.stream()
                .map(item -> GetItemResponse.builder()
                        .itemId(item.getItemId())
                        .itemName(item.getItemName())
                        .unit(item.getUnit())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .supplyPrice(item.getSupplyPrice())
                        .vatAmount(item.getVatAmount())
                        .build())
                .collect(Collectors.toList());

        return GetStatementResponse.builder()
                .txnId(statement.getTxnId())
                .btype(statement.getBtype())
                .date(statement.getDate())
                .name(statement.getName())
//                .tel(statement.getTel())
                .phoneNumber(statement.getPhoneNumber())
                .businessNo(business.getBusiness_no())
                .businessAddress(statement.getBusinessAddress())
                .faxNumber(business.getFax_number())
                .tradeName(business.getTrade_name())
                .unit_auto(statement.getUnit_auto())
                .quantity_auto(statement.getQuantity_auto())
                .vat(statement.getVat())
                .total(statement.getTotal())
                .acquirerName(statement.getAcquirerName())
                .out_amt(statement.getOut_amt())
                .dep_acc(statement.getDep_acc())
                .items(itemResponses)
                .build();
    }




    // 거래 내역 삭제
    @Transactional
    public int deleteTransaction(MemberEntity member, String userId, int transactionId) {
        String user = member.getUserId();

        Statement statement = statementRepository
                .findByTransaction_TxnId(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Statement not found for transactionId: " + transactionId));

        statementRepository.delete(statement);
        return transactionId;
    }


    public Transaction createTransaction(MemberEntity member, String userId, Business business, StatementRequest statementRequest, BusinessType btype) {

        Transaction transaction = new Transaction();

        transaction.setBtype(btype);
        transaction.setBusiness(business);
//        transaction.setUser(business.getUser());
        transaction.setUserId(member.getUserId());
        transaction.setDate(statementRequest.getDate());

        transactionRepository.save(transaction);

        business.addTransaction(transaction);

        return transaction;
    }




}
