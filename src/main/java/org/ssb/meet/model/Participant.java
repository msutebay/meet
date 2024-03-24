package org.ssb.meet.model;

public class Participant extends BaseModel {

    private String name;
    private String email;

    // Constructor
    public Participant(Long id, String name, String email) {
        super(id); // Call to superclass constructor to initialize createdAt and updatedAt
        this.name = name;
        this.email = email;
    }
    public Participant() {
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Override toString() method for debugging/logging purposes
    @Override
    public String toString() {
        return "Participant{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                '}';
    }
}
