package com.thinhtran.chatapp.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinhtran.chatapp.util.constant.RoomTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResConversationDto {
    private Long id;

    private RoomTypeEnum roomType;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant createdAt;
    private List<ResMemberDto> members;
}
