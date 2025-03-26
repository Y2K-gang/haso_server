package org.example.haso.domain.profile.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.haso.domain.auth.MemberException;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.auth.repository.MemberRepository;
import org.example.haso.domain.product.entity.Category;
import org.example.haso.domain.product.entity.Product;
import org.example.haso.domain.profile.dto.EditProfileRequest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Edit {

    @Id
    private String userId; // 사용자 아이디

    private String password; // 비밀번호
    private String name; // 본명
//    private String tel; // 전화번호
    private String phoneNumber;
    private String businessNo; // 사업자 등록 번호
    private String faxNo; // 팩스 번호
    private String storeNo; // 사업장 번호
    private String storeName; // 상호명

    @ElementCollection
    private List<Category> handlingProduct; // 취급 물품

    @OneToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    // 프로필 수정 메소드
    public void edit(EditProfileRequest request) {
        this.password = request.getPassword() != null ? request.getPassword() : this.password;
        this.name = request.getName() != null ? request.getName() : this.name;
//        this.tel = request.getTel() != null ? request.getTel() : this.tel;
        this.phoneNumber = request.getPhoneNumber() != null ? request.getPhoneNumber() : this.phoneNumber;
        this.businessNo = request.getBusinessNo() != null ? request.getBusinessNo() : this.businessNo;
        this.faxNo = request.getFaxNo() != null ? request.getFaxNo() : this.faxNo;
        this.storeNo = request.getStoreNo() != null ? request.getStoreNo() : this.storeNo;
        this.storeName = request.getStoreName() != null ? request.getStoreName() : this.storeName;
        this.handlingProduct = request.getHandlingProduct() != null ? request.getHandlingProduct() : this.handlingProduct;
    }
}
