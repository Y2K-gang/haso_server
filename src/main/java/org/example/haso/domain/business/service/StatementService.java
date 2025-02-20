package org.example.haso.domain.business.service;

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
import org.example.haso.domain.business.model.Business;
import org.example.haso.domain.business.model.BusinessType;
import org.example.haso.domain.business.model.Item;
import org.example.haso.domain.business.model.Statement;
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
    private StatementRepository statementRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public StatementResponse createTransaction(MemberEntity member, String userId, StatementRequest statementRequest, BusinessType btype) {
        // 거래처 찾기
        Business business = businessRepository.findByUserId(userId);

        // Statement 객체 생성 및 저장
        Statement statement = Statement.builder()
                .user(member.getUserId())
                .date(statementRequest.getDate())
                .tel(statementRequest.getTel())
                .faxNumber(statementRequest.getFaxNumber())
                .businessNo(statementRequest.getBusinessNo())
                .tradeName(statementRequest.getTradeName())
                .businessAddress(statementRequest.getBusinessAddress())
                .name(statementRequest.getName())
                .business(business)
                .btype(btype)
                .build();

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
                        .unit_auto(itemRequest.getUnit_auto())
                        .quantity_auto(itemRequest.getQuantity_auto())
                        .vat(itemRequest.getVat())
                        .total(itemRequest.getTotal())
                        .acquirerName(itemRequest.getAcquirerName())
                        .out_amt(itemRequest.getOut_amt())
                        .dep_acc(itemRequest.getDep_acc())
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
        return createTransaction(member, userId, statementRequest, BusinessType.SUPPLY);
    }

    @Transactional
    public StatementResponse createDemandTransaction(MemberEntity member, String userId, StatementRequest statementRequest) {

        return createTransaction(member, userId, statementRequest, BusinessType.DEMAND);
    }

    @Transactional
    public GetStatementResponse getTransactionStatement(MemberEntity member, String userId, int txnId) {
//        Statement member_userId = Statement.fromMemberEntity(member);

        String user = member.getUserId();  // 여기서 userId를 가져옵니다.
        Statement statement = statementRepository.findByUserAndTxnId(user, txnId);

//        // 거래처 찾기
        Business business = statement.getBusiness();

        // 품목 정보 조회
        List<Item> items = itemRepository.findByStatement(statement);

        // 품목 정보를 리스트로 변환
        List<GetItemResponse> itemResponses = items.stream()
                .map(item -> GetItemResponse.builder()
                        .itemId(item.getItemId())
                        .itemName(item.getItemName())
                        .unit(item.getUnit())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .supplyPrice(item.getSupplyPrice())
                        .vatAmount(item.getVatAmount())
                        .unit_auto(item.getUnit_auto())
                        .quantity_auto(item.getQuantity_auto())
                        .vat(item.getVat())
                        .total(item.getTotal())
                        .acquirerName(item.getAcquirerName())
                        .out_amt(item.getOut_amt())
                        .dep_acc(item.getDep_acc())
                        .build())
                .collect(Collectors.toList());

        return GetStatementResponse.builder()
                .txnId(statement.getTxnId())
                .btype(statement.getBtype())
                .date(statement.getDate())
                .name(statement.getName())
                .tel(statement.getTel())
                .businessNo(business.getBusiness_no())
                .businessAddress(statement.getBusinessAddress())
                .faxNumber(business.getFax_number())  // 팩스 번호
                .tradeName(business.getTrade_name())  // 상호
                .items(itemResponses)  // 품목 정보 (리스트)
                .build();
    }




    // 거래 내역 삭제
    @Transactional
    public int deleteTransaction(MemberEntity member, String userId, int txnId) {

        String user = member.getUserId();  // 여기서 userId를 가져옵니다.
        Statement statement = statementRepository.findByUserAndTxnId(user, txnId);

        statementRepository.delete(statement);
        return txnId;
    }


}
