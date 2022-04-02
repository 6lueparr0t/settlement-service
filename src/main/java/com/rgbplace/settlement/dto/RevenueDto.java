package com.rgbplace.settlement.dto;

import com.rgbplace.settlement.domain.channel.Channel;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenueDto {
    @NotNull
    private String channelName;

    @NotNull
    private Long profit;

    @NotNull
    private Date date;

    private Channel channel;
}
