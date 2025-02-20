package org.example.haso.domain.business.dto.transaction;

import lombok.*;
import org.example.haso.domain.business.model.Statement;
import org.example.haso.domain.business.model.Transaction;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {
    private int txnId; // 거래 내역 id
    private Date date; // 거래 날짜

    // Transaction -> TransactionResponse 변환 메서드 추가
    public static TransactionResponse from(Transaction transaction) {
        return TransactionResponse.builder()
                .txnId(transaction.getTxnId())
                .date(transaction.getDate())
                .build();
    }
}

