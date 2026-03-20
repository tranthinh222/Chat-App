package com.thinhtran.chatapp.domain.response;

import com.thinhtran.chatapp.util.constant.MessageStatusEnum;
import com.thinhtran.chatapp.util.constant.MessageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqMessageChatDto {

    private Long messageId;
    private ResSenderDto  resSenderDto;
    private String content;
    private MessageTypeEnum messageType;
    private Instant createAt;

    private List<ResMessageFileDto> files;
    private List<ResMessageReactionDto> reactions;
    private MessageStatusEnum status;
}
