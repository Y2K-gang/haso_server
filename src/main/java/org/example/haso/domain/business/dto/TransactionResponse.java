package org.example.haso.domain.business.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {
    private Long txnId;
    private String content;
    private Date date;
}
