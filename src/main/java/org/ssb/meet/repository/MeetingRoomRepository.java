package org.ssb.meet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ssb.meet.entity.MeetingRoomEntity;

@Repository
public interface MeetingRoomRepository extends JpaRepository<MeetingRoomEntity, Long> {
    // You can define custom query methods here if needed
}
