package com.rgbplace.settlement.controller;

import com.rgbplace.settlement.dto.CreatorDto;
import com.rgbplace.settlement.dto.CreatorRevenueShareDto;
import com.rgbplace.settlement.service.creator.CreatorService;
import com.rgbplace.settlement.service.revenue.RevenueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/creator")
@Slf4j
public class CreatorController {

    private static final String APPLICATION_JSON_VALUE = "application/json";
    private final CreatorService creatorService;
    private final RevenueService revenueService;

    @PostMapping("/info/register")
    public ResponseEntity<Map<String, Object>> infoRegister(HttpServletRequest request,
                                                          HttpServletResponse response,
                                                          @RequestBody CreatorDto creatorDto) throws IOException {

        final URI uri = linkTo(methodOn(this.getClass()).infoRegister(request, response, creatorDto)).toUri();

        try {
            Map<String, Object> data = creatorService.infoRegister(creatorDto, request);

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
                                                              @RequestBody CreatorRevenueShareDto creatorRevenueShareDto) throws IOException {

        final URI uri = linkTo(methodOn(this.getClass()).revenueInquiry(request, response, creatorRevenueShareDto)).toUri();

        try {
            Map<String, Object> data = revenueService.creatorRevenueShareInquiry(creatorRevenueShareDto, request);

            response.setContentType(APPLICATION_JSON_VALUE);
            return ResponseEntity.created(uri).body(data);
        } catch (Exception e) {
            log.error(e.getMessage());

            throw new IOException(e);
        }
    }
}
