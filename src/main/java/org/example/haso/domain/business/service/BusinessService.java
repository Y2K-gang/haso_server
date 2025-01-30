package org.example.haso.domain.business.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.haso.domain.business.dto.*;
import org.example.haso.domain.business.model.Business;
import org.example.haso.domain.business.model.Item;
import org.example.haso.domain.business.model.Statement;
import org.example.haso.domain.business.repository.BusinessRepository;
import org.example.haso.domain.business.repository.ItemRepository;
import org.example.haso.domain.business.repository.StatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessService {

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
//    private TransactionRepository transactionRepository;
    private StatementRepository statementRepository;

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
        List<Statement> statements = statementRepository.findByBusiness(business);

        // Statement -> TransactionResponse 변환
        return statements.stream()
                .map(statement -> new TransactionResponse(
                        statement.getTxnId(),
                        statement.getItem().getItemName(),
                        statement.getDate()))
                .toList();

    }

    @Transactional
    public TransactionResponse createTransaction(Long userId, StatementRequest statementRequest) {

        // 거래처 찾기
        Business business = businessRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Business not found"));

        // Item 객체 생성 (StatementRequest의 items를 기반으로)
        Item item = Item.builder()
                .itemName(statementRequest.getItems().getItemName())
                .unit(statementRequest.getItems().getUnit())
                .quantity(statementRequest.getItems().getQuantity())
                .unitPrice(statementRequest.getItems().getUnitPrice())
                .supplyPrice(statementRequest.getItems().getSupplyPrice())
                .vatAmount(statementRequest.getItems().getVatAmount())
                .outAmt(statementRequest.getItems().getOut_amt())
                .depAcc(statementRequest.getItems().getDep_acc())
                .build();

        // Statement 객체 생성
        Statement statement = Statement.builder()
                .date(statementRequest.getDate())
                .business(business)
                .item(item)
                .build();

        statement = statementRepository.save(statement);

        return new TransactionResponse(
                statement.getTxnId(),
                statement.getItem().getItemName(),
                statement.getDate()
        );
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


    // 거래 내역 상세 조회 & 거래명세표 조회
    @Transactional
    public StatementResponse getTransactionStatement(Long userId, Long txnId) {
        // 거래 내역 찾기
        Statement statement = statementRepository.findById(txnId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // 거래 내역이 거래처에 속하는지
        if (!statement.getBusiness().getUserId().equals(userId)) {
            throw new RuntimeException("Transaction does not belong to the specified business");
        }

        // 거래처 찾기
        Business business = statement.getBusiness();

        // 품목 정보 조회
        Item item = itemRepository.findByStatement(statement);

        ItemResponse itemResponse = ItemResponse.builder()
                .itemName(item.getItemName())
                .unit(item.getUnit())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .supplyPrice(item.getSupplyPrice())
                .vatAmount(item.getVatAmount())
                .build();

        return StatementResponse.builder()
                .txnId(statement.getTxnId())
                .date(statement.getDate())
                .businessAddress(business.getBusiness_address())  // 사업장 주소
                .faxNumber(business.getFax_number())  // 팩스 번호
                .tradeName(business.getTrade_name())  // 상호
                .items(itemResponse)  // 품목 정보 (단일 품목)
                .build();
    }

}
