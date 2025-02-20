package org.example.haso.domain.business.dto.statement;

import lombok.*;
import org.example.haso.domain.business.dto.item.ItemRequest;
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
    private BusinessType btype; // 수요/거래
    private List<ItemResponse> items; // 품목 정보 리스트


}
