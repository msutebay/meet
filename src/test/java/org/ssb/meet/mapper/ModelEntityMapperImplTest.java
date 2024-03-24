package org.ssb.meet.mapper;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.ssb.meet.entity.MeetingEntity;
import org.ssb.meet.entity.MeetingRoomEntity;
import org.ssb.meet.entity.ParticipantEntity;
import org.ssb.meet.model.Meeting;
import org.ssb.meet.model.MeetingRoom;
import org.ssb.meet.model.Participant;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ModelEntityMapperImplTest {


    // mapParticipantModelToEntity should correctly map a Participant domain model to a ParticipantEntity entity
    @Test
    void map_participant_model_to_entity_successfully() {
        // Arrange
        ModelMapper modelMapper = mock(ModelMapper.class);
        ModelEntityMapperImpl mapper = new ModelEntityMapperImpl(modelMapper);
        Participant domainModel = new Participant(1L, "John Doe", "john.doe@example.com");

        // Act
        ParticipantEntity entity = mapper.mapParticipantModelToEntity(domainModel);

        // Assert
        assertEquals(domainModel.getId(), entity.getId());
        assertEquals(domainModel.getName(), entity.getName());
        assertEquals(domainModel.getEmail(), entity.getEmail());
    }

    // mapParticipantEntityToModel should correctly map a ParticipantEntity entity to a Participant domain model
    @Test
    void map_participant_entity_to_model_successfully() {
        // Arrange
        ModelMapper modelMapper = new ModelMapper();
        ModelEntityMapperImpl mapper = new ModelEntityMapperImpl(modelMapper);
        ParticipantEntity entity = new ParticipantEntity(1L, "John Doe", "john.doe@example.com");

        // Act
        Participant domainModel = mapper.mapParticipantEntityToModel(entity);

        // Assert
        assertEquals(entity.getId(), domainModel.getId());
        assertEquals(entity.getName(), domainModel.getName());
        assertEquals(entity.getEmail(), domainModel.getEmail());
    }

    // mapMeetingRoomEntityToModel should correctly map a MeetingRoomEntity entity to a MeetingRoom domain model
    @Test
    void map_meeting_room_entity_to_model_successfully() {
        // Arrange
        ModelMapper modelMapper = new ModelMapper();
        ModelEntityMapperImpl mapper = new ModelEntityMapperImpl(modelMapper);
        MeetingRoomEntity entity = new MeetingRoomEntity(1L);
        entity.setName("Conference Room");

        // Act
        MeetingRoom domainModel = mapper.mapMeetingRoomEntityToModel(entity);

        // Assert
        assertEquals(entity.getId(), domainModel.getId());
        assertEquals(entity.getName(), domainModel.getName());
    }

    // mapMeetingEntityToModel should correctly map a MeetingEntity entity to a MeetingRoom domain model
    @Test
    void map_meeting_entity_to_model_successfully() {
        // Arrange
        ModelMapper modelMapper = new ModelMapper();
        ModelEntityMapperImpl mapper = new ModelEntityMapperImpl(modelMapper);
        MeetingEntity entity = new MeetingEntity(1L);
        entity.setTitle("Meeting 1");
        entity.setStartTime(LocalDateTime.now());
        entity.setEndTime(LocalDateTime.now());
        MeetingRoomEntity meetingRoomEntity = new MeetingRoomEntity(1L);
        meetingRoomEntity.setName("Meeting Room 1");
        entity.setMeetingRoom(meetingRoomEntity);
        List<ParticipantEntity> participants = new ArrayList<>();
        participants.add(new ParticipantEntity(1L, "John Doe", "john.doe@example.com"));
        entity.setParticipants(participants);

        // Act
        Meeting result = mapper.mapMeetingEntityToModel(entity);

        // Assert
        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getTitle(), result.getTitle());
        assertEquals(entity.getStartTime(), result.getStartTime());
        assertEquals(entity.getEndTime(), result.getEndTime());
        assertEquals(entity.getMeetingRoom().getId(), result.getMeetingRoom().getId());
        assertEquals(entity.getMeetingRoom().getName(), result.getMeetingRoom().getName());
        assertEquals(entity.getParticipants().size(), result.getParticipants().size());
    }
}