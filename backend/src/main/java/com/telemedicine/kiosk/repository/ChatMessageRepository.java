package com.telemedicine.kiosk.repository;

import com.telemedicine.kiosk.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
            Long senderId1, Long receiverId1, Long receiverId2, Long senderId2);
}
