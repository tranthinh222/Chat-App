package com.thinhtran.chatapp.domain.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReqForgotPasswordDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;
}
