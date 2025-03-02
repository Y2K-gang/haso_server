package org.example.haso.domain.chat.controller;

import org.example.haso.domain.chat.entity.ChatMessage;
import org.example.haso.domain.chat.entity.ChatRoom;
import org.example.haso.domain.chat.repository.ChatMessageRepository;
import org.example.haso.domain.chat.repository.ChatRoomRepository;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.auth.repository.MemberRepository;
import org.example.haso.global.auth.GetAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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


    // 채팅방 생성 (POST /chats/create)
    @PostMapping("/create")
    public ResponseEntity<ChatRoom> createChatRoom(
            @GetAuthenticatedUser MemberEntity member,
            @RequestParam String userId2) {
        try {
            MemberEntity user2 = memberRepository.findById(userId2).orElseThrow(() -> new RuntimeException("User2 not found"));

            ChatRoom chatRoom = new ChatRoom(member, user2);
            ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedChatRoom);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // 메시지 전송 (POST /chats/{chatRoomId}/messages)
    @PostMapping("/{chatRoomId}/messages")
    public ResponseEntity<Void> sendMessage(
            @GetAuthenticatedUser MemberEntity member,
            @PathVariable Long chatRoomId,
            @RequestBody String message) {

        try {
            MemberEntity sender = member;

            ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                    .orElseThrow(() -> new RuntimeException("Chat Room not found"));

            ChatMessage chatMessage = new ChatMessage(chatRoom, sender, message);
            chatMessageRepository.save(chatMessage);

            // WebSocket으로 메시지 전송
            simpMessagingTemplate.convertAndSend("/topic/chats/" + chatRoomId, chatMessage);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 채팅 메세지 조회 (GET /chats/{chatRoomId}/messages)
    @GetMapping("/{chatRoomId}/messages")
    public ResponseEntity<List<ChatMessage>> getMessages(
            @GetAuthenticatedUser MemberEntity member,
            @PathVariable Long chatRoomId) {
        try {
            List<ChatMessage> messages = chatMessageRepository.findByChatRoomId(chatRoomId);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 채팅방 삭제 (DELETE /chats/{chatRoomId})
    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<Void> deleteChatRoom(@GetAuthenticatedUser MemberEntity member, @PathVariable Long chatRoomId) {
        Optional<ChatRoom> chatRoomOpt = chatRoomRepository.findById(chatRoomId);
        if (chatRoomOpt.isPresent()) {
            ChatRoom chatRoom = chatRoomOpt.get();

            chatRoomRepository.delete(chatRoom);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    // 채팅방 목록 조회 (GET /chats/rooms)
    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoom>> getChatRooms(@GetAuthenticatedUser MemberEntity member) {
        try {
            List<ChatRoom> chatRooms = chatRoomRepository.findByParticipants(member);

            if (chatRooms.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            return ResponseEntity.ok(chatRooms);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




}
