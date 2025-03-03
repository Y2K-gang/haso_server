package org.example.haso.domain.profile.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.haso.domain.product.entity.Category;
import org.example.haso.domain.product.entity.Product;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {

    @Id
    @Column(nullable = false, unique = true)
    private String userId; // 사용자 아이디

    @ElementCollection
    private List<Category> handlingProduct; // 취급 상품

    @OneToMany
//    @JoinColumn(name = "product_id", nullable = false)
    private List<Product> product;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private Edit edit;
}
