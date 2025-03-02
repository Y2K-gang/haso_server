package org.example.haso.domain.chat.repository;

import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findByParticipants(MemberEntity member);
}
