package com.rgbplace.settlement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rgbplace.settlement.Application;
import com.rgbplace.settlement.domain.channel.Channel;
import com.rgbplace.settlement.domain.channel.ChannelRepository;
import com.rgbplace.settlement.domain.creator.CreatorRepository;
import com.rgbplace.settlement.domain.revenue.Revenue;
import com.rgbplace.settlement.domain.revenue.RevenueRepository;
import com.rgbplace.settlement.dto.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
public class ChannelControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private CreatorRepository creatorRepository;

    @Autowired
    private RevenueRepository revenueRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    //given
    private final String channelName = "GoGilDong TV";
    private final String desc = "test";

    private final String name1 = "GilDong-Go";
    private final String uid1 = "gdg";

//    private final String name2 = "GilDong-Hong";
//    private final String uid2 = "gdh";

    private final float rs1 = 0.8f;
//    private final float rs2 = 0.2f;

    private final long profit = 10000;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @After
    public void tearDown() throws Exception {
        revenueRepository.deleteAll();
        creatorRepository.deleteAll();
        channelRepository.deleteAll();
    }

    @Test
    public void channel_info_register_test() throws Exception {
        ChannelDto channelDto = ChannelDto.builder()
                .name(channelName)
                .desc(desc)
                .build();

        String url = "http://localhost:" + port + "/channel/info/register";

        //when
        mvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(new ObjectMapper().writeValueAsString(channelDto)))
            .andExpect(status().isCreated());

        //then
        List<Channel> all = channelRepository.findAll();
        assertThat(all.get(0).getName()).isEqualTo(channelName);
        assertThat(all.get(0).getDesc()).isEqualTo(desc);
    }

    @Test
    public void channel_revenue_record_test() throws Exception {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());

        ChannelDto channelDto = ChannelDto.builder()
                .name(channelName)
                .desc(desc)
                .build();

        CreatorDto creatorDto = CreatorDto.builder()
                .name(name1)
                .uid(uid1)
                .rs(rs1)
                .channelName(channelName)
                .build();

        RevenueDto revenueDto = RevenueDto.builder()
                .channelName(channelName)
                .profit(profit)
                .date(new Date())
                .build();

        //when
        String url1 = "http://localhost:" + port + "/channel/info/register";
        mvc.perform(post(url1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(channelDto)))
                .andExpect(status().isCreated());

        //when
        String url2 = "http://localhost:" + port + "/creator/info/register";
        mvc.perform(post(url2)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(creatorDto)))
                .andExpect(status().isCreated());

        //when
        String url3 = "http://localhost:" + port + "/channel/revenue/record";
        mvc.perform(post(url3)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(revenueDto)))
                .andExpect(status().isCreated());

        //then
        List<Revenue> all = revenueRepository.findAll();
        assertThat(all.get(0).getProfit()).isEqualTo(profit);
        assertThat(all.get(0).getDate()).isEqualTo(date);
    }

    @Test
    public void channel_revenue_inquiry_test() throws Exception {

        ChannelDto channelDto = ChannelDto.builder()
                .name(channelName)
                .desc(desc)
                .build();

        CreatorDto creatorDto = CreatorDto.builder()
                .name(name1)
                .uid(uid1)
                .rs(rs1)
                .channelName(channelName)
                .build();

        RevenueDto revenueDto = RevenueDto.builder()
                .channelName(channelName)
                .profit(profit)
                .date(new Date())
                .build();

        ChannelRevenueShareDto channelRevenueShareDto = ChannelRevenueShareDto.builder()
                .channelName(channelName)
                .build();

        //when
        String url1 = "http://localhost:" + port + "/channel/info/register";
        mvc.perform(post(url1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(channelDto)))
                .andExpect(status().isCreated());

        //when
        String url2 = "http://localhost:" + port + "/creator/info/register";
        mvc.perform(post(url2)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(creatorDto)))
                .andExpect(status().isCreated());

        //when
        String url3 = "http://localhost:" + port + "/channel/revenue/record";
        int count = (int)(Math.random() * 4 + 1);

        for(int i=0; i<count; i++) {
            mvc.perform(post(url3)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(new ObjectMapper().writeValueAsString(revenueDto)))
                    .andExpect(status().isCreated());
        }

        //then
        String url4 = "http://localhost:" + port + "/channel/revenue/inquiry";
        mvc.perform(post(url4)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(channelRevenueShareDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", equalTo(200)))
                .andExpect(jsonPath("$.response.channel[0].total", is((int)(profit*count))));
    }

    @Test
    public void channel_revenue_inquiry_total_test() throws Exception {
        ChannelDto channelDto = ChannelDto.builder()
                .name(channelName)
                .desc(desc)
                .build();

        CreatorDto creatorDto = CreatorDto.builder()
                .name(name1)
                .uid(uid1)
                .rs(rs1)
                .channelName(channelName)
                .build();

        RevenueDto revenueDto = RevenueDto.builder()
                .channelName(channelName)
                .profit(profit)
                .date(new Date())
                .build();

        ChannelRevenueShareDto channelRevenueShareDto = ChannelRevenueShareDto.builder()
                .channelName(channelName)
                .build();

        //when
        String url1 = "http://localhost:" + port + "/channel/info/register";
        mvc.perform(post(url1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(channelDto)))
                .andExpect(status().isCreated());

        //when
        String url2 = "http://localhost:" + port + "/creator/info/register";
        mvc.perform(post(url2)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(creatorDto)))
                .andExpect(status().isCreated());

        //when
        String url3 = "http://localhost:" + port + "/channel/revenue/record";
        int count = (int)(Math.random() * 4 + 1);

        for(int i=0; i<count; i++) {
            mvc.perform(post(url3)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(new ObjectMapper().writeValueAsString(revenueDto)))
                    .andExpect(status().isCreated());
        }

        int total = (int) profit*count;
        int creatorProfit = (int) (profit*count*rs1);
        int netIncome = total - creatorProfit;

        //then
        String url4 = "http://localhost:" + port + "/channel/revenue/inquiry/total";
        mvc.perform(get(url4)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString("")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", equalTo(200)))
                .andExpect(jsonPath("$.response.data[0].netIncome", is(netIncome)));
    }
}
