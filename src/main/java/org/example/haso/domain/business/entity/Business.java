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
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; // 거래처 id

    private String business_no; // 사업자 등록 번호

    private String business_address; // 사업장 주소

    private String fax_number; // 팩스 번호

    private String trade_name; // 상호

    @OneToMany(mappedBy = "business", fetch = FetchType.LAZY)
    private List<Transaction> transactions; // 거래 내역 리스트

    public Business(BusinessRequest businessRequest) {
        this.userId = businessRequest.getUserId();
    }
}
