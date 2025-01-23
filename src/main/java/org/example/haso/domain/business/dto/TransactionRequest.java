package org.example.haso.domain.business.dto;

import lombok.*;
import org.example.haso.domain.business.entity.Business;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {

    private String content; // 거래 내용
    private Date date;     // 거래 날짜

//    public static GetBusinessResponse from(Business business) {
//        return GetBusinessResponse.builder()
//                .userId(business.getUserId())
//                .build();
//    }
}
