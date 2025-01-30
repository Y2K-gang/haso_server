package org.example.haso.domain.business.dto;

import lombok.*;
import java.util.Date;


import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatementRequest {

    private Date date; // 거래 일자 (YYYY-MM-DD)
    private String businessAddress; // 사업장 주소
    private String faxNumber; // 팩스 번호
    private String tradeName; // 상호
    private ItemRequest items; // 품목 정보 리스트

}