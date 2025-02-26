package org.example.haso.domain.business.dto.business;

import lombok.*;
import org.example.haso.domain.business.model.Business;
import org.example.haso.domain.product.entity.Category;
import org.example.haso.domain.profile.entity.Profile;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class GetBusinessResponse {

    private String userId; // 거래처 id
    private List<Category> handlingProduct; // 취급 상품

    public static GetBusinessResponse from(Business business) {
        Profile profile = new Profile();
        return GetBusinessResponse.builder()
                .userId(business.getUserId())
                .handlingProduct(profile.getHandlingProduct())
                .build();
    }
}


