package org.example.haso.domain.product.dto;

import lombok.*;
import org.example.haso.domain.product.entity.Category;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    private String title; // 상품 제목
    private MultipartFile picture; // 상품 사진
    private int quantity; // 상품 수량(kg)
    private int price; // 상품 가격(kg)
    private String description; // 상품 설명
    private String location; // 장소
    private Category category; // 상품 종류


}
