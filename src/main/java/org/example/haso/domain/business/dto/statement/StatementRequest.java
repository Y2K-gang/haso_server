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
//    private String tel; // 전화번호
    private String phoneNumber;
    private String faxNumber; // 팩스 번호
    private String businessNo; // 사업자 등록 번호
    private String tradeName; // 상호
    private String businessAddress; // 사업장 주소
    private String name; //  성명

    private int unit_auto;// 수량 (자동 계산된 값)
    private int quantity_auto;// 공급가액 (자동 계산된 값)
    private int vat; // VAT
    private int total; // 합계
    private String acquirerName; // 인수

    private int out_amt;	// 미수금
    private String dep_acc;	// 입금계좌

    private List<ItemRequest> items; // 품목 정보 리스트

}