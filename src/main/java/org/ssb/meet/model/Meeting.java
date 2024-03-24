package org.ssb.meet.model;

import java.time.LocalDateTime;
import java.util.List;

public class Meeting extends BaseModel {

    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private MeetingRoom meetingRoom;
    private List<Participant> participants;

    // Constructors
    public Meeting(Long id, String title, LocalDateTime startTime, LocalDateTime endTime, MeetingRoom meetingRoom, List<Participant> participants) {
        super(id);
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.meetingRoom = meetingRoom;
        this.participants = participants;
    }
    public Meeting() {
    }

    // Getters and setters
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

    public MeetingRoom getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(MeetingRoom meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    // Override toString() method for debugging/logging purposes
    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + this.getId() +
                ", title='" + this.getTitle() + '\'' +
                ", startTime='" + this.getStartTime() + '\'' +
                ", endTime='" + this.getEndTime() + '\'' +
                ", meetingRoom='" + this.getMeetingRoom() + '\'' +
                ", participants='" + this.getParticipants().stream().map(Participant::toString) + '\'' +
                '}';
    }
}
