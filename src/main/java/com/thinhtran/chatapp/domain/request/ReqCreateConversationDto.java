package com.thinhtran.chatapp.domain.request;

import com.thinhtran.chatapp.util.constant.RoomTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqCreateConversationDto {
    @NotNull
    private RoomTypeEnum roomType;

    private String roomName;

    @NotNull
    private List<Long> memIds;
}
