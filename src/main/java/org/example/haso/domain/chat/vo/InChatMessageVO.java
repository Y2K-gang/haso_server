package org.example.haso.domain.chat.vo;

import lombok.*;
import org.example.haso.domain.auth.entity.MemberEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InChatMessageVO {

    private MemberEntity sender;  // 사용자 정보
    private String message;  // 메시지 내용
}
