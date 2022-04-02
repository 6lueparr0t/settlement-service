package com.rgbplace.settlement.domain.creator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatorRepository extends JpaRepository<Creator,Long> {
    Creator findByUid(String uid);
}
