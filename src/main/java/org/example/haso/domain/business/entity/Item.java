package org.example.haso.domain.business.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.haso.domain.business.dto.BusinessRequest;
import org.example.haso.domain.business.dto.BusinessResponse;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    private String itemName; // 품목명
    private String itemCategory; // 품종
    private String unit; // 단위 (규격)
    private int quantity; // 수량
    private int unitPrice; // 단가
    private int supplyPrice; // 공급가액
    private int vatAmount; // 부가세액

    @OneToOne
    @JoinColumn(name = "txnId", nullable = false)
    private Transaction transaction; // 거래 내역과의 관계
}
