package org.example.haso.domain.product.dto;

import lombok.*;
import org.example.haso.domain.product.entity.Category;
import org.example.haso.domain.product.entity.Product;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetProductResponse {

    private int id; // 상품 id
    private String title; // 상품 제목
    private String picture; // 상품 사진
    private int quantity; // 상품 수량(kg)
    private int price; // 상품 가격(kg)
    private String location; // 장소
    private Category category; // 상품 종류


    public static GetProductResponse from(Product product) {
        return GetProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .picture(product.getPicture())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .location(product.getLocation())
                .category(product.getCategory())
                .build();
    }

}
