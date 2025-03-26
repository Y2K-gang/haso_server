package org.example.haso.domain.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.haso.domain.auth.MemberType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Getter
@Setter
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
//    String tel; // 전화번호
    String phoneNumber;

    @Column(nullable = false)
    String storeName; // 상호명

    @Column(nullable = false)
    String storeNo; // 사업장 번호

    @Column(nullable = false)
    String faxNo; // 팩스 번호

    @Column(nullable = false)
    String businessNo; // 사업자 등록 번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberType role;


}
