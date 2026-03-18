package com.thinhtran.chatapp.domain.response;

import com.thinhtran.chatapp.util.constant.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResUserJwtDto {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private GenderEnum gender;
    private String avatar;
}