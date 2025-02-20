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

    private String user; // userId -> 유저별 데이터 구별 위함

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int txnId;

    private String userId; // 거래처 id
//    private String content;

    private Date date;

    private BusinessType btype;

//    @ManyToOne
//    @JoinColumn(name = "userId", nullable = false)
//    private Business business; // 거래처와의 관계
//
//    @OneToOne(mappedBy = "transaction", fetch = FetchType.LAZY)
//    private Item item;
}

