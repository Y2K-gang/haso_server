package org.example.haso.domain.profile.dto;

import lombok.*;
import org.example.haso.domain.product.entity.Category;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditProfileRequest {

//    private String userId; // 사용자 아이디
    private String password; // 비밀번호
    private String name; // 본명
    private String tel; // 전화번호
    private String businessNo; // 사업자 등록 번호
    private String faxNo; // 팩스 번호
    private String storeNo; // 사업장 번호
    private String storeName; // 상호명
    private List<Category> handlingProduct; // 취급 물품

}
