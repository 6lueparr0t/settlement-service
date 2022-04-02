package com.rgbplace.settlement.service.revenue;

import com.rgbplace.settlement.domain.channel.Channel;
import com.rgbplace.settlement.domain.channel.ChannelRepository;
import com.rgbplace.settlement.domain.creator.Creator;
import com.rgbplace.settlement.domain.creator.CreatorRepository;
import com.rgbplace.settlement.domain.revenue.Revenue;
import com.rgbplace.settlement.domain.revenue.RevenueRepository;
import com.rgbplace.settlement.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class RevenueServiceImpl implements RevenueService {
    private final ChannelRepository channelRepository;
    private final CreatorRepository creatorRepository;
    private final RevenueRepository revenueRepository;

    @Override
    public Map<String, Object> record(RevenueDto revenueDto, HttpServletRequest request) throws IOException{
        try {
            Channel channel = channelRepository.findByName(revenueDto.getChannelName());

            Map<String, Object> data = new HashMap<>();
            if(channel == null) {
                data.put("status", 400);
                data.put("error", "채널이 존재하지 않습니다.");

                return data;
            }

            revenueDto.setChannel(channel);
            Revenue revenue = Revenue.builder(revenueDto).build();
            Revenue createdRevenue = revenueRepository.save(revenue);
            data.put("status", 200);
            data.put("error", "");
            data.put("response", createdRevenue);

            return data;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IOException(e);
        }
    }

    @Override
    public Map<String, Object> channelRevenueShareInquiry(ChannelRevenueShareDto channelRevenueShareDto, HttpServletRequest request) throws IOException{
        try {
            Channel channel = channelRepository.findByName(channelRevenueShareDto.getChannelName());

            Map<String, Object> data = new HashMap<>();
            if(channel == null) {
                data.put("status", 400);
                data.put("error", "채널이 존재하지 않습니다.");

                return data;
            }

            List<ChannelRevenueShareInterface> creatorRevenueShare = revenueRepository.getProfitSumByChannelId(channel.getId());
            List<ChannelRevenueShareForCreatorInterface> creatorRevenueShareForCreator = revenueRepository.getProfitSumByChannelIdForCreator(channel.getId());
            data.put("status", 200);
            data.put("error", "");

            Map<String, Object> subData = new HashMap<>();
            subData.put("channelName", channel.getName());
            subData.put("id", channel.getId());
            subData.put("desc", channel.getDesc());
            subData.put("channel", creatorRevenueShare);
            subData.put("creator", creatorRevenueShareForCreator);

            data.put("response", subData);

            return data;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IOException(e);
        }
    }

    @Override
    public Map<String, Object> creatorRevenueShareInquiry(CreatorRevenueShareDto creatorRevenueShareDto, HttpServletRequest request) throws IOException{
        try {
            Creator creator = creatorRepository.findByUid(creatorRevenueShareDto.getUid());

            Map<String, Object> data = new HashMap<>();
            if(creator == null) {
                data.put("status", 400);
                data.put("error", "사용자가 존재하지 않습니다.");

                return data;
            }

            List<CreatorRevenueShareInterface> creatorRevenueShare = revenueRepository.getProfitSumByUid(creator.getUid());
            data.put("status", 200);
            data.put("error", "");

            Map<String, Object> subData = new HashMap<>();
            subData.put("uid", creator.getUid());
            subData.put("name", creator.getName());
            subData.put("channelName", creator.getChannel().getName());
            subData.put("rs", creator.getRs());
            subData.put("data", creatorRevenueShare);

            data.put("response", subData);

            return data;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IOException(e);
        }
    }

    @Override
    public Map<String, Object> channelRevenueShareInquiryTotal(HttpServletRequest request) throws IOException{
        try {
            Map<String, Object> data = new HashMap<>();
            List<RevenueShareTotalInterface> revenueShareTotal = revenueRepository.getProfitTotal();
            data.put("status", 200);
            data.put("error", "");

            Map<String, Object> subData = new HashMap<>();
            subData.put("data", revenueShareTotal);

            data.put("response", subData);

            return data;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IOException(e);
        }
    }
}
