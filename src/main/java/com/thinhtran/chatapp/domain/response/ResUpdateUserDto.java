package com.thinhtran.chatapp.domain.response;

import com.thinhtran.chatapp.util.constant.GenderEnum;
import lombok.Data;

import java.time.Instant;

@Data
public class ResUpdateUserDto {
    private String username;
    private String phone;
    private GenderEnum gender;
    private String avatar;
    private Instant updatedAt;
}
