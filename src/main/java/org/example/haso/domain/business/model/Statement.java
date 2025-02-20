package org.example.haso.domain.business.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.business.dto.item.ItemRequest;

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

    private String user; // userId -> 유저별 데이터 구별 위함

    private Date date; // 거래 일자 (YYYY-MM-DD)
    private String tel; // 전화번호
    private String faxNumber; // 팩스 번호
    private String businessNo; // 사업자 등록 번호
    private String tradeName; // 상호
    private String businessAddress; // 사업장 주소
    private String name; //  성명

    @OneToMany(mappedBy = "statement", cascade = CascadeType.ALL)
//    private List<Item> items = new ArrayList<>();
    private List<Item> items; // 품목 목록

    @OneToOne
    @JoinColumn(name = "user_Id", nullable = false)
    private Business business; // 거래처와의 관계

    private BusinessType btype;


    public static Statement fromMemberEntity(MemberEntity member) {
        // userId가 null인지 확인
        if (member.getUserId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        return Statement.builder()
                .user(member.getUserId()) // user_id 설정
                .build();
    }

}
