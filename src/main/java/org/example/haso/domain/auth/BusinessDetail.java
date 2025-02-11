package org.example.haso.domain.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessDetail {

    private String b_no;
    private String start_dt;
    private String p_nm;

    @Builder.Default
    private String p_nm2 = "";  // 기본값 설정
    @Builder.Default
    private String b_nm = "";
    @Builder.Default
    private String corp_no = "";
    @Builder.Default
    private String b_sector = "";
    @Builder.Default
    private String b_type = "";
    @Builder.Default
    private String b_adr = "";

    public BusinessDetail(String b_no, String start_dt, String p_nm) {
        this.b_no = b_no;
        this.start_dt = start_dt;
        this.p_nm = p_nm;
    }

}
