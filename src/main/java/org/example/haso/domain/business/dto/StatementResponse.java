package org.example.haso.domain.business.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatementResponse {

    private Long txnId; // 거래 내역 id
    private String date; // 작성 일자 (YYYY-MM-DD)
    private String businessAddress; // 사업장 주소
    private String faxNumber; // 팩스 번호
    private String tradeName; // 상호
    private List<ItemResponse> items; // 품목 정보 리스트

}
