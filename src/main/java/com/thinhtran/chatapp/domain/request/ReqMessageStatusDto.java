package com.thinhtran.chatapp.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqMessageStatusDto {
    private Long messageId;
    private Long senderId;
}
