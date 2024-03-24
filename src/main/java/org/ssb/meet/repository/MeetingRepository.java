package org.ssb.meet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ssb.meet.entity.MeetingEntity;
import org.ssb.meet.entity.MeetingRoomEntity;
import org.ssb.meet.entity.ParticipantEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<MeetingEntity, Long> {
    // You can define custom query methods here if needed
    List<MeetingEntity> findByParticipantsContains(ParticipantEntity participant);
    List<MeetingEntity> findByMeetingRoom(MeetingRoomEntity meetingRoom);
    List<MeetingEntity> findByMeetingRoomAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            MeetingRoomEntity meetingRoom, LocalDateTime proposedEndTime, LocalDateTime proposedStartTime);

    List<MeetingEntity> findByParticipantsContainsAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            ParticipantEntity participant, LocalDateTime proposedEndTime, LocalDateTime proposedStartTime);
}
