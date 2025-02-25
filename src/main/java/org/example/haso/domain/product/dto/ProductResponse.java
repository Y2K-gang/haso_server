package org.example.haso.domain.product.dto;

import lombok.*;
import org.example.haso.domain.business.model.Business;
import org.example.haso.domain.product.entity.Category;
import org.example.haso.domain.product.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private int id; // 상품 id
    private String userId; // 작성자 id
    private String title; // 상품 제목
//    private String picture; // 상품 사진
    private int quantity; // 상품 수량(kg)
    private int price; // 상품 가격(kg)
    private String description; // 상품 설명
    private String location; // 장소
    private Category category; // 상품 종류
    private LocalDateTime createdDate; // 생성된 시간

    // Entity → DTO 변환
    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .userId(product.getUserId().getUserId())
                .title(product.getTitle())
//                .picture(product.getPicture()) // 실제 저장된 경로
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .description(product.getDescription())
                .location(product.getLocation())
                .category(product.getCategory())
                .createdDate(product.getCreatedDate())
                .build();
    }

}

