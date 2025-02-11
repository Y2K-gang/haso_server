package org.example.haso.domain.auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessValidateRequest {

    private String b_no; // 사업자등록번호
    private String start_dt; // 개업일자(YYYYMMDD 포맷)
    private String p_nm; // 대표자 성명1

}
