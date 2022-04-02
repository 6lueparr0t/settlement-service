package com.rgbplace.settlement.domain.channel;


import com.rgbplace.settlement.domain.creator.Creator;
import com.rgbplace.settlement.dto.ChannelDto;
import com.rgbplace.settlement.dto.CreatorDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@Table(name = "TBL_CHANNEL_INFO")
@EntityListeners(AuditingEntityListener.class)
@Builder(builderMethodName = "ChannelBuilder")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 400, unique = true, nullable = false, name = "name")
    private String name;

    @Column(length = 4000, name = "desc")
    private String desc;

    @Column(name = "cdtm")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "udtm")
    @LastModifiedDate
    private LocalDateTime updatedDate;

    public static ChannelBuilder builder(ChannelDto channelDto) {
        return ChannelBuilder()
                .name(channelDto.getName())
                .desc(channelDto.getDesc());
    }
}
