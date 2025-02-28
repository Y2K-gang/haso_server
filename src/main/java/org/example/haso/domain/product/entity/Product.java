package org.example.haso.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.product.dto.ProductRequest;
import org.example.haso.domain.profile.entity.Profile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 상품 id

    @ManyToOne
    private MemberEntity userId; // 작성자 id

    private String title; // 상품 제목

    private String picture; // 상품 사진

    private int quantity; // 상품 수량(kg)

    private	int price; // 상품 가격(kg)

    private String description; // 상품 설명

    private String location; //	장소

    private Category category; // 상품 종류

    private LocalDateTime createdDate; // 생성된 시간

//    @ManyToOne
//    private Profile profile;

    public Product(ProductRequest productRequest) {
        this.title = productRequest.getTitle();
        this.quantity = productRequest.getQuantity();
        this.price = productRequest.getPrice();
        this.description = productRequest.getDescription();
        this.location = productRequest.getLocation();
        this.category = productRequest.getCategory();
        this.picture = productRequest.getPicture() != null ? productRequest.getPicture().getOriginalFilename() : null;

    }

}
