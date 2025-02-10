package org.example.haso.domain.business.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Statement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int txnId; // 거래 내역 ID

    private Date date; // 거래 일자 (YYYY-MM-DD)
    private String businessAddress; // 사업장 주소
    private String faxNumber; // 팩스 번호
    private String tradeName; // 상호

    @ManyToOne
    @JoinColumn(name = "itemId", nullable = false)
    private Item item; // 단일 품목 정보

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Business business; // 거래처와의 관계

    private BusinessType btype;

//    public BusinessType getBtype() {
//        return btype;
//    }
//
//    public void setBtype(BusinessType btype) {
//        this.btype = btype;
//    }

}
