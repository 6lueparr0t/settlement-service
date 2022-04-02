package com.rgbplace.settlement.domain.revenue;

import com.rgbplace.settlement.dto.ChannelRevenueShareForCreatorInterface;
import com.rgbplace.settlement.dto.ChannelRevenueShareInterface;
import com.rgbplace.settlement.dto.CreatorRevenueShareInterface;
import com.rgbplace.settlement.dto.RevenueShareTotalInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue,Long> {
    @Query(value = "SELECT year(b.date) as year, month(b.date) as month, sum(b.profit) as total " +
            "FROM TBL_CHANNEL_INFO a " +
            "JOIN TBL_REVENUE b ON a.id = b.channel " +
            "WHERE a.id = ?1 " +
            "GROUP BY year(b.date), month(b.date)", nativeQuery = true)
    List<ChannelRevenueShareInterface> getProfitSumByChannelId(Long channelId);

    @Query(value = "SELECT year(b.date) as year, month(b.date) as month, floor(sum(b.profit)*c.rs) as profit, c.uid as uid, c.name as name " +
            "FROM TBL_CHANNEL_INFO a " +
            "JOIN TBL_REVENUE b ON a.id = b.channel " +
            "JOIN TBL_CREATOR_INFO c ON a.id = c.channel " +
            "WHERE a.id = ?1 " +
            "GROUP BY year(b.date), month(b.date), c.uid", nativeQuery = true)
    List<ChannelRevenueShareForCreatorInterface> getProfitSumByChannelIdForCreator(Long channelId);

    @Query(value = "SELECT year(b.date) as year, month(b.date) as month, floor(sum(b.profit)*a.rs) as profit " +
            "FROM TBL_CREATOR_INFO a " +
            "JOIN TBL_REVENUE b ON a.channel = b.channel " +
            "WHERE a.uid = ?1 " +
            "GROUP BY year(b.date), month(b.date)", nativeQuery = true)
    List<CreatorRevenueShareInterface> getProfitSumByUid(String uid);

    @Query(value = "SELECT a.year as year, a.month as month, b.total as total, b.total-sum(profit) as netIncome " +
            "FROM (" +
                "SELECT year(b.date) as year, month(b.date) as month, floor(sum(b.profit)*c.rs) as profit " +
                "FROM TBL_CHANNEL_INFO a " +
                "JOIN TBL_REVENUE b ON a.id = b.channel " +
                "JOIN TBL_CREATOR_INFO c ON a.id = c.channel " +
                "GROUP BY year(b.date), month(b.date), c.uid) a " +
            "JOIN (" +
                "SELECT year(b.date) as year, month(b.date) as month, sum(b.profit) as total " +
                "FROM TBL_CHANNEL_INFO a " +
                "JOIN TBL_REVENUE b ON a.id = b.channel " +
                "GROUP BY year(b.date), month(b.date)) b " +
            "ON a.year = b.year and a.month = b.month " +
            "GROUP BY a.year, a.month", nativeQuery = true)
    List<RevenueShareTotalInterface> getProfitTotal();
}
