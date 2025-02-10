package org.example.haso.domain.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.haso.domain.auth.entity.MemberEntity;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private List<MemberEntity> participants; // 채팅방에 참여한 사용자들 (1:1 채팅)

    private String roomName; // 채팅방 이름 (상대방 ID로 설정)

    // 1:1 채팅방을 생성하는 생성자
    public ChatRoom(MemberEntity user1, MemberEntity user2) {
        this.participants = List.of(user1, user2);
        this.roomName = user1.getUserId() + "-" + user2.getUserId(); // 채팅방 이름은 두 사용자 ID로 설정
    }
}
