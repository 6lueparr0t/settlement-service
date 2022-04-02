package com.rgbplace.settlement.domain.channel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel,Long> {
    Channel findByName(String name);
    Channel findById(String id);
}
