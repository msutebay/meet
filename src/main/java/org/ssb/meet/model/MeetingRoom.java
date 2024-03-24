package org.ssb.meet.model;

public class MeetingRoom extends BaseModel {

    private String name;

    // Constructor
    public MeetingRoom(Long id, String name) {
        super(id); // Call to superclass constructor to initialize createdAt and updatedAt
        this.name = name;
    }

    public MeetingRoom() {
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Override toString() method for debugging/logging purposes
    @Override
    public String toString() {
        return "Meeting Room{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                '}';
    }
}
