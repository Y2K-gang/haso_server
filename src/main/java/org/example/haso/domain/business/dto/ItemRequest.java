package org.example.haso.domain.business.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemRequest {

    private String itemName; // 품목명
    private String unit; // 단위 (규격)
    private int quantity; // 수량
    private int unitPrice; // 단가
    private int supplyPrice; // 공급가액
    private int vatAmount; // 부가세액
    private int out_amt;	// 미수금
    private String dep_acc;	// 입금계좌
}
