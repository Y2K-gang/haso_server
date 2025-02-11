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

    private String itemName; // 품목명
    private String unit; // 단위 (규격)
    private int quantity; // 수량
    private int unitPrice; // 단가
    private int supplyPrice; // 공급가액
    private int vatAmount; // 부가세액
    private int outAmt; // 미수금 (outstanding amount)
    private String depAcc; // 입금 계좌 (deposit account)

    @ManyToOne
    @JoinColumn(name = "txnId", nullable = false)
    private Statement statement; // 거래 내역과의 관계
}
