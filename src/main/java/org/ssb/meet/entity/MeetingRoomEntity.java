package org.ssb.meet.entity;

import jakarta.persistence.Entity;

@Entity
public class MeetingRoomEntity extends BaseEntity {
    private String name;

    public MeetingRoomEntity(Long id) {
        super(id);
    }

    public MeetingRoomEntity() {
        super();
    }

    // Getter ve setter metotları
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
