package org.example.haso.domain.business.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;

    private String itemName; // 품종 및 퓸목
    private String unit; // 단위 (규격)
    private int quantity; // 수량
    private int unitPrice; // 단가
    private int supplyPrice; // 공급가액
    private int vatAmount; // 부가세액

//    private int unit_auto;// 수량 (자동 계산된 값)
//    private int quantity_auto;// 공급가액 (자동 계산된 값)
//    private int vat; // VAT
//    private int total; // 합계
//    private String acquirerName; // 인수
//
//    private int out_amt;	// 미수금
//    private String dep_acc;	// 입금계좌

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "txn_Id", nullable = false)
    private Statement statement; // 거래 내역과의 관계

}
