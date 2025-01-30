package org.example.haso.domain.business.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {
    private Long txnId; // 거래 내역 id
    private String item_name; // 품목명
    private Date date; // 거래 날짜
}
