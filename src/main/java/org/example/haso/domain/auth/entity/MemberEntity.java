package org.example.haso.domain.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.haso.domain.auth.MemberType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long id;

//    @Column(nullable = false)
    @Id
    String userId; // 사용자 아이디

    @Column(nullable = false)
    String name; // 본명

    @Column(nullable = false)
    String password; // 비밀번호

    @Column(nullable = false)
    String tel; // 전화번호

    @Column(nullable = false)
    String storeName; // 상호명

    @Column(nullable = false)
    String storeNo; // 사업장 번호

    @Column(nullable = false)
    String faxNo; // 사업장 번호

    @Column(nullable = false)
    String businessNo; // 사업자 등록 번호

    @Enumerated(EnumType.STRING)
    private MemberType role;

//    // 비밀번호 저장 전에 암호화
//    @PrePersist
//    @PreUpdate
//    public void encryptPassword() {
//        if (this.password != null) {
//            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            this.password = passwordEncoder.encode(this.password);  // 비밀번호 암호화
//        }
//    }

}
