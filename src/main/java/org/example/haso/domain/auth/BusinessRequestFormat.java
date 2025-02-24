package org.example.haso.domain.auth;

import lombok.*;
import org.example.haso.domain.auth.dto.BusinessValidateRequest;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessRequestFormat {

    private String serviceKey;  // serviceKey 추가
    private List<BusinessDetail> businesses; // 사업체 리스트

    public static BusinessRequestFormat from(BusinessValidateRequest request, String serviceKey) {
        return new BusinessRequestFormat(serviceKey, request.getBusinesses());
    }
}
