package com.thinhtran.chatapp.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResMemberDto {
    private Long memberId;
    private String username;
    private String avatar;
}
