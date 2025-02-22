package org.example.haso.domain.business.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.haso.domain.auth.entity.MemberEntity;

import java.util.Date;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

//    private String user; // userId -> 유저별 데이터 구별 위함

    @Id
    private int txnId;

    private String userId; // 거래처 id
    private Date date;

    private BusinessType btype;

    @OneToOne(mappedBy = "transaction")
    private Statement statement;

    @ManyToOne
    @JoinColumn(name = "business", nullable = false)
    private Business business; // 거래처와의 관계

}

