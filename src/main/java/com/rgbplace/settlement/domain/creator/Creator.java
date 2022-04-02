package com.rgbplace.settlement.domain.creator;


import com.rgbplace.settlement.domain.channel.Channel;
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
@Table(name = "TBL_CREATOR_INFO")
@EntityListeners(AuditingEntityListener.class)
@Builder(builderMethodName = "CreatorBuilder")
public class Creator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false, name = "name")
    private String name;

    @Column(length = 200, nullable = false, name = "uid")
    private String uid;

    @Column(nullable = false, name = "rs")
    private float rs;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="channel", nullable = false)
    private Channel channel;

    @Column(name = "cdtm")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "udtm")
    @LastModifiedDate
    private LocalDateTime updatedDate;

    public static CreatorBuilder builder(CreatorDto creatorDto) {
        return CreatorBuilder()
                .name(creatorDto.getName())
                .uid(creatorDto.getUid())
                .rs(creatorDto.getRs())
                .channel(creatorDto.getChannel());
    }
}
