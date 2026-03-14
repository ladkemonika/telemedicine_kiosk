package com.telemedicine.kiosk.controller;

import com.telemedicine.kiosk.dto.ChatMessageDTO;
import com.telemedicine.kiosk.entity.User;
import com.telemedicine.kiosk.service.ChatService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/send")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR')")
    public ResponseEntity<ChatMessageDTO> sendMessage(
            @AuthenticationPrincipal User user,
            @RequestBody ChatRequest request) {
        return ResponseEntity.ok(chatService.sendMessage(user.getId(), request.getReceiverId(), request.getContent()));
    }

    @GetMapping("/history/{otherUserId}")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR')")
    public ResponseEntity<List<ChatMessageDTO>> getChatHistory(
            @AuthenticationPrincipal User user,
            @PathVariable Long otherUserId) {
        return ResponseEntity.ok(chatService.getChatHistory(user.getId(), otherUserId));
    }
}

@Data
class ChatRequest {
    private Long receiverId;
    private String content;
}
