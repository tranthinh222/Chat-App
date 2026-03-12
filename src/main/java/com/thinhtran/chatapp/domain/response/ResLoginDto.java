package com.thinhtran.chatapp.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResLoginDto {
    private String accessToken;
    private ResUserJwtDto user;

}
