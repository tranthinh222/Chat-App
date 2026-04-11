package com.thinhtran.chatapp.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqUpdateUserOnlineStatusDto {
    private Long userId;
    private Boolean isOnline;
}
