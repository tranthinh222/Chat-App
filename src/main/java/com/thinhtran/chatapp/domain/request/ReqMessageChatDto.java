package com.thinhtran.chatapp.domain.request;

import com.thinhtran.chatapp.util.constant.MessageTypeEnum;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqMessageChatDto {
    @NotNull
    private Long conversationId;
    private String content;
    private MessageTypeEnum messageType;
    private Long senderId;
    private ReqFileDto file;
}
