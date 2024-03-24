package org.ssb.meet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ssb.meet.entity.ParticipantEntity;

@Repository
public interface ParticipantRepository extends JpaRepository<ParticipantEntity, Long> {
    // You can define custom query methods here if needed
}