package com.thinhtran.chatapp.domain.response;

import com.thinhtran.chatapp.util.constant.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResUserDto {
    private long id;
    private String username;
    private String email;
    private String phone;
    private RoleEnum role;
    private Instant createdAt;
    private Instant updatedAt;
}
