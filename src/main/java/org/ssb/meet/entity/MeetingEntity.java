package org.ssb.meet.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class MeetingEntity extends BaseEntity {
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "meeting_room_id")
    private MeetingRoomEntity meetingRoom;

    @ManyToMany
    @JoinTable(
            name = "meeting_participants",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private List<ParticipantEntity> participants;

    public MeetingEntity(Long id) {
        super(id);
    }

    public MeetingEntity() {
        super();
    }

    // Getter ve setter metotları
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public MeetingRoomEntity getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(MeetingRoomEntity meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public List<ParticipantEntity> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantEntity> participants) {
        this.participants = participants;
    }
}