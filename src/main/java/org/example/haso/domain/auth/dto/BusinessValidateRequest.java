package org.example.haso.domain.auth.dto;

import lombok.*;
import org.example.haso.domain.auth.BusinessDetail;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessValidateRequest {

    private List<BusinessDetail> businesses; // 사업체 리스트

    @Override
    public String toString() {
        return "BusinessValidateRequest{" +
                "businesses=" + businesses +
                '}';
    }
}
