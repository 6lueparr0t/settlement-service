package com.rgbplace.settlement.service.creator;

import com.rgbplace.settlement.domain.channel.Channel;
import com.rgbplace.settlement.domain.channel.ChannelRepository;
import com.rgbplace.settlement.domain.creator.Creator;
import com.rgbplace.settlement.domain.creator.CreatorRepository;
import com.rgbplace.settlement.dto.CreatorDto;
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
public class CreatorServiceImpl implements CreatorService {
    private final ChannelRepository channelRepository;
    private final CreatorRepository creatorRepository;

    @Override
    public Map<String, Object> infoRegister(CreatorDto creatorDto, HttpServletRequest request) throws IOException{
        try {
            Channel channel = channelRepository.findByName(creatorDto.getChannelName());

            Map<String, Object> data = new HashMap<>();
            if(channel == null) {
                data.put("status", 400);
                data.put("error", "채널명이 존재하지 않습니다.");
                return data;
            }

            creatorDto.setChannel(channel);
            Creator creator = Creator.builder(creatorDto).build();

            Creator createdCreator = creatorRepository.save(creator);
            data.put("status", 200);
            data.put("error", "");
            data.put("response", createdCreator);

            return data;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IOException(e);
        }
    }
}
