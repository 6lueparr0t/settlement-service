package com.rgbplace.settlement.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChannelDto {
    @NotNull
    private String name;

    private String desc;
}
