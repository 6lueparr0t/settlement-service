package com.rgbplace.settlement.controller;

import com.rgbplace.settlement.dto.ChannelDto;
import com.rgbplace.settlement.dto.ChannelRevenueShareDto;
import com.rgbplace.settlement.dto.RevenueDto;
import com.rgbplace.settlement.service.channel.ChannelService;
import com.rgbplace.settlement.service.revenue.RevenueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/channel")
@Slf4j
public class ChannelController {

    private static final String APPLICATION_JSON_VALUE = "application/json";
    private final ChannelService channelService;
    private final RevenueService revenueService;

    @PostMapping("/info/register")
    public ResponseEntity<Map<String, Object>> infoRegister(HttpServletRequest request,
                                                          HttpServletResponse response,
                                                          @RequestBody ChannelDto channelDto) throws IOException {

        final URI uri = linkTo(methodOn(this.getClass()).infoRegister(request, response, channelDto)).toUri();

        try {
            Map<String, Object> data = channelService.infoRegister(channelDto, request);

            response.setContentType(APPLICATION_JSON_VALUE);
            return ResponseEntity.created(uri).body(data);
        } catch (Exception e) {
            log.error(e.getMessage());

            throw new IOException(e);
        }
    }

    @PostMapping("/revenue/record")
    public ResponseEntity<Map<String, Object>> revenueRecord(HttpServletRequest request,
                                                             HttpServletResponse response,
                                                             @RequestBody RevenueDto revenueDto) throws IOException {

        final URI uri = linkTo(methodOn(this.getClass()).revenueRecord(request, response, revenueDto)).toUri();

        try {
            Map<String, Object> data = revenueService.record(revenueDto, request);

            response.setContentType(APPLICATION_JSON_VALUE);
            return ResponseEntity.created(uri).body(data);
        } catch (Exception e) {
            log.error(e.getMessage());

            throw new IOException(e);
        }
    }

    @PostMapping("/revenue/inquiry")
    public ResponseEntity<Map<String, Object>> revenueInquiry(HttpServletRequest request,
                                                             HttpServletResponse response,
                                                             @RequestBody ChannelRevenueShareDto channelRevenueShareDto) throws IOException {

        final URI uri = linkTo(methodOn(this.getClass()).revenueInquiry(request, response, channelRevenueShareDto)).toUri();

        try {
            Map<String, Object> data = revenueService.channelRevenueShareInquiry(channelRevenueShareDto, request);

            response.setContentType(APPLICATION_JSON_VALUE);
            return ResponseEntity.created(uri).body(data);
        } catch (Exception e) {
            log.error(e.getMessage());

            throw new IOException(e);
        }
    }

    @GetMapping("/revenue/inquiry/total")
    public ResponseEntity<Map<String, Object>> revenueInquiryTotal(HttpServletRequest request,
                                                              HttpServletResponse response) throws IOException {

        final URI uri = linkTo(methodOn(this.getClass()).revenueInquiryTotal(request, response)).toUri();

        try {
            Map<String, Object> data = revenueService.channelRevenueShareInquiryTotal(request);

            response.setContentType(APPLICATION_JSON_VALUE);
            return ResponseEntity.created(uri).body(data);
        } catch (Exception e) {
            log.error(e.getMessage());

            throw new IOException(e);
        }
    }
}
