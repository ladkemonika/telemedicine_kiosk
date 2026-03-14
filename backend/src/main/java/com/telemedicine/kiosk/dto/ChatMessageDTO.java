package com.telemedicine.kiosk.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessageDTO {
    private Long id;
    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String receiverName;
    private String content;
    private String timestamp;
}
