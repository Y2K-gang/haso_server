package org.example.haso.domain.chat.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OutChatMessageVO {

    private String id;
    private String senderName;  // 보낸 사람의 이름
    private String domain;  // 채팅방 이름
    private String content;  // 메시지 내용
}
