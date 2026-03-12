package com.thinhtran.chatapp.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReqResetPasswordDto {
    @NotBlank(message = "Reset token is required")
    private String resetToken;

    @NotBlank(message = "New password is required")
    private String newPassword;
}
