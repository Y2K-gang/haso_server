package org.example.haso.domain.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.haso.domain.auth.entity.MemberEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ChatRoom chatRoom; // 메시지가 속한 채팅방

    @ManyToOne
    private MemberEntity sender; // 메시지를 보낸 사용자

    private String message; // 메시지 내용

    private LocalDateTime sendTime; // 메시지가 보낸 시간

    public ChatMessage(ChatRoom chatRoom, MemberEntity sender, String message) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.message = message;
        this.sendTime = LocalDateTime.now();
    }
}
