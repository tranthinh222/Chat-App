package com.thinhtran.chatapp.domain.response;

import java.time.Instant;

import com.thinhtran.chatapp.util.constant.MessageStatusEnum;
import com.thinhtran.chatapp.util.constant.MessageTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResMessageChatDto {
    private Long messageId;
    private Long conversationId;
    private String content;
    private MessageTypeEnum messageType;
    private ResSenderDto sender;
    private ResMessageFileDto file;
    private Instant createdAt;
    private MessageStatusEnum status;
}
