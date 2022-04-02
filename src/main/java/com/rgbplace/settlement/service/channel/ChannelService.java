package com.rgbplace.settlement.service.channel;

import com.rgbplace.settlement.dto.ChannelDto;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface ChannelService {
    Map<String, Object> infoRegister(ChannelDto channelDto, HttpServletRequest request) throws IOException;
}
