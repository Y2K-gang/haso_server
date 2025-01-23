package org.example.haso.domain.business.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.haso.domain.business.dto.BusinessRequest;
import org.example.haso.domain.business.dto.TransactionRequest;
import org.example.haso.domain.business.dto.TransactionResponse;

import java.util.Date;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long txnId;

    private String content;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Business business; // 거래처와의 관계

    @OneToOne(mappedBy = "transaction", fetch = FetchType.LAZY)
    private Item item;
}

