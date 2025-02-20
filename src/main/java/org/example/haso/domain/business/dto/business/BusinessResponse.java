package org.example.haso.domain.business.dto.business;

import lombok.*;
import org.example.haso.domain.business.model.Business;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessResponse {

    private String  userId; // 거래처 id
    private String business_no; // 사업자 등록 번호
    private String storeNo; // 사업장 주소
    private String fax_number; // 팩스 번호
    private String trade_name; // 상호

    // entity -> dto
    public BusinessResponse(Business business) {
        this.userId = business.getUserId();
        this.business_no = business.getBusiness_no();
        this.storeNo = business.getStoreNo();
        this.fax_number = business.getFax_number();
        this.trade_name = business.getTrade_name();
    }
}
