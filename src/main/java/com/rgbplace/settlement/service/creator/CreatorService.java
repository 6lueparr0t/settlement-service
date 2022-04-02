package com.rgbplace.settlement.service.creator;

import com.rgbplace.settlement.domain.channel.Channel;
import com.rgbplace.settlement.domain.creator.Creator;
import com.rgbplace.settlement.dto.CreatorDto;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface CreatorService {
    Map<String, Object> infoRegister(CreatorDto creatorDto, HttpServletRequest request) throws IOException;
}
