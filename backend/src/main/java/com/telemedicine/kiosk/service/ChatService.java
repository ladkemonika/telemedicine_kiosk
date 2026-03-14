package com.telemedicine.kiosk.service;

import com.telemedicine.kiosk.dto.ChatMessageDTO;
import com.telemedicine.kiosk.entity.ChatMessage;
import com.telemedicine.kiosk.entity.User;
import com.telemedicine.kiosk.exception.ResourceNotFoundException;
import com.telemedicine.kiosk.repository.ChatMessageRepository;
import com.telemedicine.kiosk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    public ChatMessageDTO sendMessage(Long senderId, Long receiverId, String content) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found: " + senderId));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new ResourceNotFoundException("Receiver not found: " + receiverId));

        ChatMessage message = ChatMessage.builder()
                .sender(sender)
                .receiver(receiver)
                .content(content)
                .build();

        message = chatMessageRepository.save(message);
        return mapToDTO(message);
    }

    public List<ChatMessageDTO> getChatHistory(Long userId1, Long userId2) {
        return chatMessageRepository.findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
                userId1, userId2, userId1, userId2).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ChatMessageDTO mapToDTO(ChatMessage message) {
        return ChatMessageDTO.builder()
                .id(message.getId())
                .senderId(message.getSender().getId())
                .senderName(message.getSender().getFirstName() + " " + message.getSender().getLastName())
                .receiverId(message.getReceiver().getId())
                .receiverName(message.getReceiver().getFirstName() + " " + message.getReceiver().getLastName())
                .content(message.getContent())
                .timestamp(message.getTimestamp().toString())
                .build();
    }
}
