package com.fixStay.backend.controller;

import com.fixStay.backend.dto.MessageRequest;
import com.fixStay.backend.model.Message;
import com.fixStay.backend.model.User;
import com.fixStay.backend.repository.MessageRepository;
import com.fixStay.backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageController(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageRequest request) {
        User sender = userRepository.findUserByEmailAddress(request.senderEmail())
                .orElseThrow(() -> new RuntimeException("Eroare: Expeditorul nu a fost găsit."));

        User receiver = userRepository.findUserByEmailAddress(request.receiverEmail())
                .orElseThrow(() -> new RuntimeException("Eroare: Destinatarul nu a fost găsit."));

        Message msg = new Message();
        msg.setSender(sender);
        msg.setReceiver(receiver);
        msg.setContent(request.content());
        msg.setTimestamp(LocalDateTime.now());

        messageRepository.save(msg);

        return ResponseEntity.ok("Mesaj trimis cu succes!");
    }


    @GetMapping("/conversation")
    public ResponseEntity<List<Message>> getConversation(
            @RequestParam String user1,
            @RequestParam String user2) {

        List<Message> chatHistory = messageRepository.findConversation(user1, user2);
        return ResponseEntity.ok(chatHistory);
    }
}