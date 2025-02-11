package org.example.haso.domain.business.dto.statement;

import lombok.*;
import org.example.haso.domain.business.dto.item.ItemResponse;
import org.example.haso.domain.business.model.BusinessType;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatementResponse {

    private int txnId; // 거래 내역 id
    private BusinessType btype;
    private Date date; // 거래 일자 (YYYY-MM-DD)
    private String businessAddress; // 사업장 주소
    private String faxNumber; // 팩스 번호
    private String tradeName; // 상호
    private List<ItemResponse> items; // 품목 정보 리스트



}
