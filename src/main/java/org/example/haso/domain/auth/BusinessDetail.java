package org.example.haso.domain.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessDetail {

    private String b_no;  // 사업자등록번호
    private String start_dt; // 개업일자(YYYYMMDD 포맷)
    private String p_nm; // 대표자 성명
    private String p_nm2; // 추가 대표자 성명 (옵션)
    private String b_nm; // 상호
    private String corp_no; // 법인번호
    private String b_sector; // 업종
    private String b_type; // 사업 유형
    private String b_adr; // 사업 주소
}
