package org.ssb.meet.mapper;

import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.junit.jupiter.api.Test;
import org.ssb.meet.model.Meeting;
import org.ssb.meet.model.MeetingRoom;
import org.ssb.meet.model.Participant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ModelContractMapperImplTest {

    // Mapping a Participant domain model to a Participant contract should return a valid Participant contract
    @Test
    void map_participant_model_to_contract_successfully() {
        // Arrange
        ModelMapper modelMapper = mock(ModelMapper.class);
        ModelContractMapperImpl mapper = new ModelContractMapperImpl(modelMapper);
        Participant participant = new Participant(1L, "Mehmet Yildiz", "mehmetyildiz@example.com");

        // When
        org.ssb.meet.openapi.model.Participant result = mapper.mapParticipantModelToContract(participant);

        // Then
        assertNotNull(result);
        assertEquals(participant.getId(), result.getId());
        assertEquals(participant.getName(), result.getName());
        assertEquals(participant.getEmail(), result.getEmail());
    }

    // Mapping a Participant contract to a Participant domain model should return a valid Participant domain model
    @Test
    void map_participant_contract_to_domain_model_successfully() {
        // Arrange
        org.ssb.meet.openapi.model.Participant contract = new org.ssb.meet.openapi.model.Participant();
        contract.setId(1L);
        contract.setName("Mehmet Yildiz");
        contract.setEmail("mehmetyildiz@example.com");
        Participant domainModel = new Participant(1L, "Mehmet Yildiz", "mehmetyildiz@example.com");
        ModelMapper modelMapper = Mockito.mock(ModelMapper.class);
        Mockito.when(modelMapper.map(contract, Participant.class)).thenReturn(domainModel);
        ModelContractMapperImpl mapper = new ModelContractMapperImpl(modelMapper);

        // When & Then
        Participant result = mapper.mapParticipantContractToModel(contract);
        assertThat(domainModel)
                .usingRecursiveComparison()
                .ignoringFields("createdAt", "updatedAt")
                .isEqualTo(result);
    }

    // Mapping a MeetingRoom domain model to a MeetingRoom contract should return a valid MeetingRoom contract
    @Test
    void map_meeting_room_model_to_contract_successfully() {
        // Arrange
        ModelMapper modelMapperMock = Mockito.mock(ModelMapper.class);
        MeetingRoom domainModel = new MeetingRoom(1L, "Meeting Room 1");
        org.ssb.meet.openapi.model.MeetingRoom contract = new org.ssb.meet.openapi.model.MeetingRoom();
        contract.setId(1L);
        contract.setName("Meeting Room 1");
        Mockito.when(modelMapperMock.map(domainModel, org.ssb.meet.openapi.model.MeetingRoom.class)).thenReturn(contract);
        ModelContractMapperImpl mapper = new ModelContractMapperImpl(modelMapperMock);

        // When
        org.ssb.meet.openapi.model.MeetingRoom result = mapper.mapMeetingRoomModelToContract(domainModel);

        // Then
        assertEquals(contract, result);
    }

    // Mapping a MeetingRoom contract to a MeetingRoom domain model should return a valid MeetingRoom domain model
    @Test
    void map_meeting_room_contract_to_model_successfully() {
        // Arrange
        ModelMapper modelMapperMock = Mockito.mock(ModelMapper.class);
        org.ssb.meet.openapi.model.MeetingRoom contract = new org.ssb.meet.openapi.model.MeetingRoom();
        contract.setId(1L);
        contract.setName("Meeting Room 1");
        ModelContractMapperImpl mapper = new ModelContractMapperImpl(modelMapperMock);
        MeetingRoom expectedDomainModel = new MeetingRoom(1L, "Meeting Room 1");
        Mockito.when(modelMapperMock.map(contract, MeetingRoom.class)).thenReturn(expectedDomainModel);

        // When
        MeetingRoom actualDomainModel = mapper.mapMeetingRoomContractToModel(contract);

        // Then
        Mockito.verify(modelMapperMock).map(contract, MeetingRoom.class);
        assertEquals(expectedDomainModel, actualDomainModel);
    }

    // Mapping a Meeting domain model to a Meeting contract should return a valid Meeting contract
    @Test
    void map_meeting_model_to_contract_successfully() {
        // Arrange
        MeetingRoom meetingRoom = new MeetingRoom(1L, "Room 1");
        List<Participant> participants = new ArrayList<>();
        participants.add(new Participant(1L, "Mehmet Yildiz", "mehmet.yildiz@example.com"));
        participants.add(new Participant(2L, "Jane Smith", "jane.smith@example.com"));
        LocalDateTime startTime = LocalDateTime.of(2022, 1, 1, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2022, 1, 1, 12, 0);
        Meeting meeting = new Meeting(1L, "Meeting 1", startTime, endTime, meetingRoom, participants);
        ModelContractMapperImpl mapper = new ModelContractMapperImpl(new ModelMapper());

        // When
        org.ssb.meet.openapi.model.Meeting contract = mapper.mapMeetingModelToContract(meeting);

        // Then
        assertEquals(meeting.getId(), contract.getId());
        assertEquals(meeting.getTitle(), contract.getTitle());
        assertEquals(meeting.getStartTime(), contract.getStartTime());
        assertEquals(meeting.getEndTime(), contract.getEndTime());
        assertEquals(meeting.getMeetingRoom().getId(), contract.getMeetingRoom().getId());
        assertEquals(meeting.getMeetingRoom().getName(), contract.getMeetingRoom().getName());
        assertEquals(meeting.getParticipants().size(), contract.getParticipants().size());
        for (int i = 0; i < meeting.getParticipants().size(); i++) {
            Participant participant = meeting.getParticipants().get(i);
            org.ssb.meet.openapi.model.Participant contractParticipant = contract.getParticipants().get(i);
            assertEquals(participant.getId(), contractParticipant.getId());
            assertEquals(participant.getName(), contractParticipant.getName());
            assertEquals(participant.getEmail(), contractParticipant.getEmail());
        }
    }

    // Mapping a Meeting contract to a Meeting domain model should return a valid Meeting domain model
    @Test
    void map_meeting_contract_to_model_successfully() {
        // Arrange
        org.ssb.meet.openapi.model.Meeting contract = new org.ssb.meet.openapi.model.Meeting();
        contract.setId(1L);
        contract.setTitle("Meeting Title");
        contract.setStartTime(LocalDateTime.now());
        contract.setEndTime(LocalDateTime.now());
        org.ssb.meet.openapi.model.MeetingRoom meetingRoomContract = new org.ssb.meet.openapi.model.MeetingRoom();
        meetingRoomContract.setId(1L);
        meetingRoomContract.setName("Meeting Room");
        contract.setMeetingRoom(meetingRoomContract);
        List<org.ssb.meet.openapi.model.Participant> participantContracts = new ArrayList<>();
        org.ssb.meet.openapi.model.Participant participantContract = new org.ssb.meet.openapi.model.Participant();
        participantContract.setId(1L);
        participantContract.setName("Mehmet Yildiz");
        participantContract.setEmail("mehmet.yildiz@example.com");
        participantContracts.add(participantContract);
        contract.setParticipants(participantContracts);
        Meeting domainModel = new Meeting(1L, "Meeting Title", LocalDateTime.now(), LocalDateTime.now(), new MeetingRoom(1L, "Meeting Room"), Arrays.asList(new Participant(1L, "Mehmet Yildiz", "mehmet.yildiz@example.com")));
        ModelMapper modelMapper = Mockito.mock(ModelMapper.class);
        Mockito.when(modelMapper.map(contract, Meeting.class)).thenReturn(domainModel);
        ModelContractMapperImpl mapper = new ModelContractMapperImpl(modelMapper);

        // When
        Meeting result = mapper.mapMeetingContractToModel(contract);

        // Then
        assertEquals(domainModel, result);
    }

}
