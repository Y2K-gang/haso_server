package org.example.haso.domain.profile.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditProfileRequest {

    private String userId; // 사용자 아이디
    private String password; // 비밀번호
    private String name; // 본명
    private String tel; // 전화번호
    private String business_no; // 사업자 등록 번호
    private String fax_no; // 팩스 번호
    private String store_no; // 사업장 번호
    private String store_name; // 상호명
    private List<String> handlingProduct; // 취급 물품

}
