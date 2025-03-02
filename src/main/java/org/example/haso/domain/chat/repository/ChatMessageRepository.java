package org.example.haso.domain.chat.repository;

import org.example.haso.domain.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoomId(Long chatRoomId);

    void deleteByChatRoomId(Long chatRoomId);
}
