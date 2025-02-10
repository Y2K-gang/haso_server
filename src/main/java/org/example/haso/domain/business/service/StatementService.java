package org.example.haso.domain.business.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.haso.domain.business.dto.item.ItemResponse;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatementService {

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private StatementRepository statementRepository;

    @Autowired
    private ItemRepository itemRepository;


//    // 거래처 상세 조회 & 거래 내역 전체 조회
//    @Transactional
//    public List<TransactionResponse> getHistory(Long userId) {
//        Business business = businessRepository.findById(userId).orElseThrow(() -> new RuntimeException("Business not found"));
//
//        // 거래 내역 찾기
//        List<Statement> statements = statementRepository.findByBusiness(business);
//
//        // Statement -> TransactionResponse 변환
//        return statements.stream()
//                .map(statement -> new TransactionResponse(
//                        statement.getTxnId(),
//                        statement.getItem().getItemName(),
//                        statement.getDate()))
//                .toList();
//
//    }

    @Transactional
    public StatementResponse createSupplyTransaction(Long userId, StatementRequest statementRequest) {

        // 거래처 찾기
        Business business = businessRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Business not found"));

        // Item 객체 생성 (StatementRequest의 items를 기반으로)
        List<Item> items = statementRequest.getItems().stream()
                .map(itemRequest -> Item.builder()
                        .itemName(itemRequest.getItemName())
                        .unit(itemRequest.getUnit())
                        .quantity(itemRequest.getQuantity())
                        .unitPrice(itemRequest.getUnitPrice())
                        .supplyPrice(itemRequest.getSupplyPrice())
                        .vatAmount(itemRequest.getVatAmount())
                        .outAmt(itemRequest.getOut_amt())
                        .depAcc(itemRequest.getDep_acc())
                        .build())
                .collect(Collectors.toList());

        // Statement 객체 생성
        Statement statement = Statement.builder()
                .date(statementRequest.getDate())
                .business(business)
                .btype(BusinessType.SUPPLY)  // BusinessType 설정 (공급 거래)
                .build();

        statement = statementRepository.save(statement);

        // 품목들을 Statement와 연관
        for (Item item : items) {
            item.setStatement(statement);  // items를 변경하지 않고 한 번만 사용
        }
        itemRepository.saveAll(items);

        return new StatementResponse(
                statement.getTxnId(),
                statement.getBtype(), // 거래 유형 (공급 거래)
                statement.getDate(),
                business.getBusiness_address(), // 사업장 주소
                business.getFax_number(), // 팩스 번호
                business.getTrade_name(), // 상호
                items.stream().map(item -> new ItemResponse(
                        item.getItemId(),
                        item.getItemName(),
                        item.getUnit(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSupplyPrice(),
                        item.getVatAmount(),
                        item.getOutAmt(),
                        item.getDepAcc()
                )).collect(Collectors.toList())  // 품목 리스트
        );
    }

    @Transactional
    public StatementResponse createDemandTransaction(Long userId, StatementRequest statementRequest) {

        // 거래처 찾기
        Business business = businessRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Business not found"));

        // Item 객체 생성 (StatementRequest의 items를 기반으로)
        List<Item> items = statementRequest.getItems().stream()
                .map(itemRequest -> Item.builder()
                        .itemName(itemRequest.getItemName())
                        .unit(itemRequest.getUnit())
                        .quantity(itemRequest.getQuantity())
                        .unitPrice(itemRequest.getUnitPrice())
                        .supplyPrice(itemRequest.getSupplyPrice())
                        .vatAmount(itemRequest.getVatAmount())
                        .outAmt(itemRequest.getOut_amt())
                        .depAcc(itemRequest.getDep_acc())
                        .build())
                .collect(Collectors.toList());

        // Statement 객체 생성
        Statement statement = Statement.builder()
                .date(statementRequest.getDate())
                .business(business)
                .btype(BusinessType.DEMAND)  // BusinessType 설정 (수요 거래)
                .build();

        statement = statementRepository.save(statement);

        // 품목들을 Statement와 연관
        for (Item item : items) {
            item.setStatement(statement);  // items를 변경하지 않고 한 번만 사용
        }
        itemRepository.saveAll(items);

        return new StatementResponse(
                statement.getTxnId(),
                statement.getBtype(), // 거래 유형 (수요 거래)
                statement.getDate(),
                business.getBusiness_address(), // 사업장 주소
                business.getFax_number(), // 팩스 번호
                business.getTrade_name(), // 상호
                items.stream().map(item -> new ItemResponse(
                        item.getItemId(),
                        item.getItemName(),
                        item.getUnit(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSupplyPrice(),
                        item.getVatAmount(),
                        item.getOutAmt(),
                        item.getDepAcc()
                )).collect(Collectors.toList())  // 품목 리스트
        );
    }

    @Transactional
    public StatementResponse getTransactionStatement(Long userId, Long txnId) {
        // 거래 내역 찾기
        Statement statement = statementRepository.findById(txnId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // 거래 내역이 거래처에 속하는지 확인
        if (!statement.getBusiness().getUserId().equals(userId)) {
            throw new RuntimeException("Transaction does not belong to the specified business");
        }

        // 거래처 찾기
        Business business = statement.getBusiness();

        // 품목 정보 조회
        List<Item> items = itemRepository.findByStatement(statement);

        // 품목 정보를 리스트로 변환
        List<ItemResponse> itemResponses = items.stream()
                .map(item -> ItemResponse.builder()
                        .itemId(item.getItemId()) // Added itemId for completeness
                        .itemName(item.getItemName())
                        .unit(item.getUnit())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .supplyPrice(item.getSupplyPrice())
                        .vatAmount(item.getVatAmount())
                        .out_amt(item.getOutAmt()) // Ensure it's mapped correctly
                        .dep_acc(item.getDepAcc()) // Ensure it's mapped correctly
                        .build())
                .collect(Collectors.toList());

        return StatementResponse.builder()
                .txnId(statement.getTxnId())
                .btype(statement.getBtype())  // 거래유형을 statement에서 직접 가져옵니다.
                .date(statement.getDate())
                .businessAddress(business.getBusiness_address())  // 사업장 주소
                .faxNumber(business.getFax_number())  // 팩스 번호
                .tradeName(business.getTrade_name())  // 상호
                .items(itemResponses)  // 품목 정보 (리스트)
                .build();
    }




    // 거래 내역 삭제
    @Transactional
    public Long deleteTransaction(Long userId, Long txnId) {

//        // 거래처 찾기
//        Business business = businessRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("Business not found"));

        // 거래 내역 찾기
        Statement statement = statementRepository.findById(txnId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // 거래 내역이 거래처에 속하는지
        if (!statement.getBusiness().getUserId().equals(userId)) {
            throw new RuntimeException("Transaction does not belong to the specified business");
        }

        statementRepository.delete(statement);
        return txnId;
    }


}
