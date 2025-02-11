package org.example.haso.domain.business.dto.statement;

import lombok.*;
import org.example.haso.domain.business.dto.item.ItemRequest;

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
    private List<ItemRequest> items; // 품목 정보 리스트

}