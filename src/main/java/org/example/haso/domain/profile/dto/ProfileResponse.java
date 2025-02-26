package org.example.haso.domain.profile.dto;

import lombok.*;
import org.example.haso.domain.product.dto.GetProductResponse;
import org.example.haso.domain.product.entity.Category;
import org.example.haso.domain.profile.entity.Profile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {

    private String userId; // 사용자 아이디
    private List<Category> handlingProduct; // 취급 상품
    private List<GetProductResponse> products; // 등록된 상품 목록

    public ProfileResponse(Profile profile, List<GetProductResponse> products) {
        this.userId = profile.getUserId();
        this.handlingProduct = profile.getHandlingProduct();
        this.products = products;

////        this.products = profile.getProduct().stream()
////                .map(product -> new GetProductResponse(product))
////                .collect(Collectors.toList());
    }
}
