package org.example.haso.domain.business.model;


import jakarta.persistence.*;
import lombok.*;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.business.dto.business.BusinessRequest;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Business {

    private String user; // userId -> 유저별 데이터 구별 위함

    @Id
    private String userId; // 거래처 id

    private String business_no; // 사업자 등록 번호

    private String storeNo; // 사업장 번호

    private String fax_number; // 팩스 번호

    private String trade_name; // 상호
//
//    @OneToMany(mappedBy = "business", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Statement> statements; // 거래 내역 리스트

    public Business(BusinessRequest request) {
        this.userId = request.getUserId();
//        this.business_no = request.getBusinessNo();
//        this.business_address = request.getBusinessAddress();

    }

    public static Business fromMemberEntity(MemberEntity memberEntity, MemberEntity member) {
        return Business.builder()
                .user(member.getUserId())
                .userId(memberEntity.getUserId())
                .business_no(memberEntity.getBusinessNo())
                .storeNo(memberEntity.getStoreNo())
                .fax_number(memberEntity.getFaxNo())
                .trade_name(memberEntity.getStoreName())
                .build();
    }
}
