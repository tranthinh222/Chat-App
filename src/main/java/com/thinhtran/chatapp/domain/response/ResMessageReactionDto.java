package com.thinhtran.chatapp.domain.response;

import com.thinhtran.chatapp.util.constant.ReactionEnum;


public interface MessageReactionDto {
    Long getUserId();
    String getUsername();
    String getAvatar();
    ReactionEnum getReactionType();

}
