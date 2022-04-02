package com.rgbplace.settlement.domain.revenue;


import com.rgbplace.settlement.domain.channel.Channel;
import com.rgbplace.settlement.domain.creator.Creator;
import com.rgbplace.settlement.dto.CreatorDto;
import com.rgbplace.settlement.dto.RevenueDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@Table(name = "TBL_REVENUE")
@EntityListeners(AuditingEntityListener.class)
@Builder(builderMethodName = "RevenueBuilder")
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="channel", nullable = false)
    private Channel channel;

    @Column(nullable = false, name = "profit")
    private Long profit;

    @Temporal(TemporalType.DATE)
    @Column(name="date", nullable = false)
    private Date date;

    @Column(name = "cdtm")
    @CreatedDate
    private LocalDateTime createdDate;

    public static RevenueBuilder builder(RevenueDto revenueDto) {
        return RevenueBuilder()
                .profit(revenueDto.getProfit())
                .date(revenueDto.getDate())
                .channel(revenueDto.getChannel());
    }
}
