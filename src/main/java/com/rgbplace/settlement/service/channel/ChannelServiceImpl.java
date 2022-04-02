package com.rgbplace.settlement.service.channel;

import com.rgbplace.settlement.domain.channel.Channel;
import com.rgbplace.settlement.domain.channel.ChannelRepository;
import com.rgbplace.settlement.dto.ChannelDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;

    @Override
    public Map<String, Object> infoRegister(ChannelDto channelDto, HttpServletRequest request) throws IOException {
        try {
            Channel channel = channelRepository.findByName(channelDto.getName());

            Map<String, Object> data = new HashMap<>();
            if(channel != null) {
                data.put("status", 400);
                data.put("error", "이미 존재하는 채널 명 입니다.");

                return data;
            }

            channel = Channel.builder(channelDto).build();
            Channel createdChannel = channelRepository.save(channel);
            data.put("status", 200);
            data.put("error", "");
            data.put("response", createdChannel);

            return data;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IOException(e);
        }

    }
}
