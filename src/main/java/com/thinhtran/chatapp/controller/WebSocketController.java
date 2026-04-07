package com.thinhtran.chatapp.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.thinhtran.chatapp.domain.request.ReqMessageChatDto;
import com.thinhtran.chatapp.domain.request.ReqMessageStatusDto;
import com.thinhtran.chatapp.domain.response.ResMessageStatus;
import com.thinhtran.chatapp.service.MessageService;
import com.thinhtran.chatapp.service.MessageStatusService;
import com.thinhtran.chatapp.util.constant.MessageStatusEnum;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final MessageStatusService messageStatusService;

    @MessageMapping("/chat.group")
    public void handleGroupChat(@Payload @Valid ReqMessageChatDto message) {
        Long userId = message.getSenderId();
        if (userId == null) {
            throw new IllegalArgumentException("senderId is required");
        }

        // Service handles broadcast to both topic and queues
        this.messageService.sendGroupMessage(message, userId);
    }

    @MessageMapping("/chat.private")
    public void handleDirectChat(@Payload @Valid ReqMessageChatDto message) {
        Long senderId = message.getSenderId();
        if (senderId == null) {
            throw new IllegalArgumentException("senderId is required");
        }

        this.messageService.sendDirectMessage(message, senderId);

    }

    @MessageMapping("/chat.delivered")
    public void handleDelivered(ReqMessageStatusDto dto) {
        if (dto == null || dto.getMessageId() == null) {
            throw new IllegalArgumentException("messageId is required");
        }

        Long userId = dto.getSenderId();
        if (userId == null) {
            throw new IllegalArgumentException("senderId is required");
        }

        System.out.println("🔔 [WebSocketController] handleDelivered called - messageId: " + dto.getMessageId()
                + ", userId: " + userId);

        messageStatusService.markAsDelivered(dto.getMessageId(), userId);

        Long senderId = this.messageService.findSenderIdByMessageId(dto.getMessageId());
        Long conversationId = this.messageService.findConversationIdByMessageId(dto.getMessageId());

        System.out.println("📤 [WebSocketController] Broadcasting DELIVERED to /topic/conversations/" + conversationId);

        ResMessageStatus status = new ResMessageStatus(dto.getMessageId(), MessageStatusEnum.DELIVERED, conversationId);

        // Send to sender (original behavior)
        messagingTemplate.convertAndSendToUser(senderId.toString(), "/queue/status", status);

        // DO NOT broadcast to conversation topic - only relevant user should see their
        // own status
        System.out.println("   ✅ Sent DELIVERED status to sender's queue");
    }

    @MessageMapping("/chat.seen")
    public void handleSeen(ReqMessageStatusDto dto) {
        if (dto == null || dto.getMessageId() == null) {
            throw new IllegalArgumentException("messageId is required");
        }

        Long userId = dto.getSenderId();
        if (userId == null) {
            throw new IllegalArgumentException("senderId is required");
        }

        System.out.println("🔔 [WebSocketController] handleSeen called - messageId: " + dto.getMessageId()
                + ", userId: " + userId);

        messageStatusService.markAsSeen(dto.getMessageId(), userId);

        Long originalSenderId = this.messageService.findSenderIdByMessageId(dto.getMessageId());
        Long conversationId = this.messageService.findConversationIdByMessageId(dto.getMessageId());

        System.out.println("📤 [WebSocketController] Sending status to sender " + originalSenderId + ", convId: "
                + conversationId);
        System.out.println("   → Sending to /queue/status of user: " + originalSenderId);
        System.out.println("   → Broadcasting to /topic/conversations/" + conversationId);

        ResMessageStatus status = new ResMessageStatus(dto.getMessageId(), MessageStatusEnum.SEEN, conversationId);
        System.out.println("   → Status object: " + status);

        // Send status to the ORIGINAL SENDER (person who sent message) so they see
        // status update
        messagingTemplate.convertAndSendToUser(originalSenderId.toString(), "/queue/status", status);
        System.out.println("   ✅ Sent SEEN status to sender's queue");

        // DO NOT broadcast to conversation topic - only the sender should see that
        // others viewed their message
    }

}
