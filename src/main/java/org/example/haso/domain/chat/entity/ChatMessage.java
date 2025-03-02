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

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "chat_room_id", referencedColumnName = "id")
    private ChatRoom chatRoom;


    @ManyToOne
    private MemberEntity sender; // 메세지 보낸 이

    private String message; // 메시지 내용

    private LocalDateTime sendTime; // 메시지 보낸 시간

    public ChatMessage(ChatRoom chatRoom, MemberEntity sender, String message) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.message = message;
        this.sendTime = LocalDateTime.now();
    }
}
