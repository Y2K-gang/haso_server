package org.example.haso.domain.auth;

import lombok.*;
import org.example.haso.domain.auth.dto.BusinessValidateRequest;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessRequestFormat {

    private List<BusinessDetail> businesses;

    public static BusinessRequestFormat from(BusinessValidateRequest request) {
        return new BusinessRequestFormat(
                Collections.singletonList(
                        new BusinessDetail(
                                request.getB_no(),
                                request.getStart_dt(),
                                request.getP_nm()
                        )
                )
        );
    }
}




