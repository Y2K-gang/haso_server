package org.example.haso.domain.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.haso.domain.business.entity.Business;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetBusinessResponse {

    private Long userId; // 거래처 id

    public static GetBusinessResponse from(Business business) {
        return GetBusinessResponse.builder()
                .userId(business.getUserId())
                .build();
    }
}
