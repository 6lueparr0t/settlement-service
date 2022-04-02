package com.rgbplace.settlement.dto;

import com.rgbplace.settlement.domain.channel.Channel;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatorDto {
    @NotNull
    private String name;

    @NotNull
    private String uid;

    @NotNull
    private float rs;

    @NotNull
    private String channelName;

    private Channel channel;
}
