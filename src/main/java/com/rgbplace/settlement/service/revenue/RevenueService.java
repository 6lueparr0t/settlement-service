package com.rgbplace.settlement.service.revenue;

import com.rgbplace.settlement.dto.ChannelRevenueShareDto;
import com.rgbplace.settlement.dto.CreatorRevenueShareDto;
import com.rgbplace.settlement.dto.RevenueDto;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface RevenueService {
    Map<String, Object> record(RevenueDto revenueDto, HttpServletRequest request) throws IOException;
    Map<String, Object> channelRevenueShareInquiry(ChannelRevenueShareDto channelRevenueShareDto, HttpServletRequest request) throws IOException;
    Map<String, Object> creatorRevenueShareInquiry(CreatorRevenueShareDto creatorRevenueShareDto, HttpServletRequest request) throws IOException;
    Map<String, Object> channelRevenueShareInquiryTotal(HttpServletRequest request) throws IOException;
}
