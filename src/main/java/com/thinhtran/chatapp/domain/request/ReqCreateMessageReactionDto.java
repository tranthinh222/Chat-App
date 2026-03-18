package com.thinhtran.chatapp.domain.request;

import com.thinhtran.chatapp.domain.MessageReactionId;
import com.thinhtran.chatapp.util.constant.ReactionEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReqCreateMessageReactionDto {
    @NotNull
    private MessageReactionId id;
    @NotNull
    private ReactionEnum reactionType;

}
