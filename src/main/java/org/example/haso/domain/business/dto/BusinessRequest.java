package org.example.haso.domain.business.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessRequest {

    private Long userId; // 거래처 id
}
