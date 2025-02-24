package org.example.haso.domain.business.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.business.dto.item.ItemRequest;
import org.example.haso.domain.business.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

    @Column(name = "user_id", nullable = false)
    private String user; // userId -> 유저별 데이터 구별 위함

    private Date date; // 거래 일자 (YYYY-MM-DD)
    private String tel; // 전화번호
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

    @OneToMany(mappedBy = "statement", cascade = CascadeType.ALL)
//    private List<Item> items = new ArrayList<>();
    private List<Item> items; // 품목 목록

    @ManyToOne
    @JoinColumn(name = "business", nullable = false)
    private Business business; // 거래처와의 관계

    private BusinessType btype;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

//    public static Statement fromMemberEntity(MemberEntity member) {
//        // userId가 null인지 확인
//        if (member.getUserId() == null) {
//            throw new IllegalArgumentException("User ID cannot be null");
//        }
//
//        return Statement.builder()
//                .user(member.getUserId()) // user_id 설정
//                .build();
//    }

}
