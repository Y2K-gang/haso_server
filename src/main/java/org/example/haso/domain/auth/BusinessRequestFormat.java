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
    private List<BusinessDetail> businesses;

    public static BusinessRequestFormat from(BusinessValidateRequest request, String serviceKey) {
        BusinessDetail businessDetail = new BusinessDetail(
                request.getB_no(),
                request.getStart_dt(),
                request.getP_nm()
        );
        return new BusinessRequestFormat(serviceKey, Collections.singletonList(businessDetail));
    }
}
