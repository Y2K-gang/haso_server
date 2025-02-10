package org.example.haso.domain.chat.controller;

import org.example.haso.domain.chat.entity.ChatMessage;
import org.example.haso.domain.chat.entity.ChatRoom;
import org.example.haso.domain.chat.repository.ChatMessageRepository;
import org.example.haso.domain.chat.repository.ChatRoomRepository;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.auth.repository.MemberRepository;
import org.example.haso.domain.chat.bean.RedisSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private RedisSession redisSession;

    // 1:1 채팅방 생성
    @PostMapping("/create")
    public ChatRoom createChatRoom(@RequestParam String userId1, @RequestParam String userId2) {
        MemberEntity user1 = memberRepository.findById(userId1).orElseThrow(() -> new RuntimeException("User1 not found"));
        MemberEntity user2 = memberRepository.findById(userId2).orElseThrow(() -> new RuntimeException("User2 not found"));

        // 1:1 채팅방 생성
        ChatRoom chatRoom = new ChatRoom(user1, user2);
        return chatRoomRepository.save(chatRoom);
    }

    // 채팅방에 메시지 전송
    @PostMapping("/{chatRoomId}/messages")
    public void sendMessage(@PathVariable Long chatRoomId, @RequestBody String message, @RequestParam String senderId) {
        MemberEntity sender = memberRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));

        // 메시지 생성 및 저장
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new RuntimeException("Chat Room not found"));
        ChatMessage chatMessage = new ChatMessage(chatRoom, sender, message);
        chatMessageRepository.save(chatMessage);

        // WebSocket을 통해 메시지 전송
        simpMessagingTemplate.convertAndSend("/topic/chats/" + chatRoomId, chatMessage);
    }

    // 채팅방 메시지 조회
    @GetMapping("/{chatRoomId}/messages")
    public List<ChatMessage> getMessages(@PathVariable Long chatRoomId) {
        return chatMessageRepository.findByChatRoomId(chatRoomId);
    }
}
