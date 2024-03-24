package org.ssb.meet.entity;


import jakarta.persistence.Entity;

@Entity
public class ParticipantEntity extends BaseEntity {
    private String name;
    private String email;

    public ParticipantEntity(Long id, String name, String email) {
        super(id);
        this.name = name;
        this.email = email;
    }

    public ParticipantEntity() {
        super();
    }

    // Getter ve setter metotları
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
}