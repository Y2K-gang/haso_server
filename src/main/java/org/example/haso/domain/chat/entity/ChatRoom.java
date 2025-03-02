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

    @ManyToOne
    private MemberEntity user1; // 채팅방의 첫 번째 사용자 (User1)

    @ManyToOne
    private MemberEntity user2; // 채팅방의 두 번째 사용자 (User2)

    @ManyToMany
    private List<MemberEntity> participants; // 채팅방에 참여한 사용자들 (User1, User2, 추가적인 참여자들)

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.REMOVE)
    private List<ChatMessage> chatMessages;

    
    public ChatRoom(MemberEntity user1, MemberEntity user2) {
        this.user1 = user1;
        this.user2 = user2;
        this.participants = List.of(user1, user2); // 두 명의 사용자가 초기 참여자
    }

    // 사용자 추가 메서드
    public void addParticipants(MemberEntity user) {
        this.participants.add(user);
    }
}
