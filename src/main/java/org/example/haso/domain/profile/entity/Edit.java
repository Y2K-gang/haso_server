package org.example.haso.domain.profile.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.haso.domain.product.entity.Product;
import org.example.haso.domain.profile.dto.EditProfileRequest;

import java.util.List;

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
    private String tel; // 전화번호
    private String businessNo; // 사업자 등록 번호
    private String faxNo; // 팩스 번호
    private String storeNo; // 사업장 번호
    private String storeName; // 상호명

    @ElementCollection
    private List<String> handlingProduct; // 취급 물품

    @OneToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    // 프로필 수정 메소드
    public void edit(EditProfileRequest request) {
        this.password = request.getPassword() != null ? request.getPassword() : this.password;
        this.name = request.getName() != null ? request.getName() : this.name;
        this.tel = request.getTel() != null ? request.getTel() : this.tel;
        this.businessNo = request.getBusiness_no() != null ? request.getBusiness_no() : this.businessNo;
        this.faxNo = request.getFax_no() != null ? request.getFax_no() : this.faxNo;
        this.storeNo = request.getStore_no() != null ? request.getStore_no() : this.storeNo;
        this.storeName = request.getStore_name() != null ? request.getStore_name() : this.storeName;
        this.handlingProduct = request.getHandlingProduct() != null ? request.getHandlingProduct() : this.handlingProduct;
    }
}
