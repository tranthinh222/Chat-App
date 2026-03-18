package com.thinhtran.chatapp.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResUserDto {
    private long id;
    private String username;
    private String email;
    private String phone;
    private Instant createdAt;
    private Instant updatedAt;
}
