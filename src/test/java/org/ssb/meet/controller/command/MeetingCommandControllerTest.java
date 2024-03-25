package org.ssb.meet.controller.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.ssb.meet.exception.MeetingRoomNotAvailableException;
import org.ssb.meet.exception.ParticipantNotAvailableException;
import org.ssb.meet.mapper.ModelContractMapper;
import org.ssb.meet.mapper.ModelContractMapperImpl;
import org.ssb.meet.openapi.model.Meeting;
import org.ssb.meet.openapi.model.MeetingRoom;
import org.ssb.meet.openapi.model.Participant;
import org.ssb.meet.service.MeetingService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MeetingCommandControllerTest {

    // Meeting is successfully saved
    @Test
    void meeting_successfully_saved() throws MeetingRoomNotAvailableException, ParticipantNotAvailableException {
        // Arrange
        MeetingService mockService = mock(MeetingService.class);
        ModelContractMapper mockMapper = mock(ModelContractMapper.class);
        MeetingCommandController controller = new MeetingCommandController();
        controller.setService(mockService);
        controller.setMapper(mockMapper);
        Meeting meetingContract = new Meeting();
        org.ssb.meet.model.Meeting meetingModel = new org.ssb.meet.model.Meeting();
        org.ssb.meet.model.Meeting savedMeetingModel = new org.ssb.meet.model.Meeting();
        when(mockMapper.mapMeetingContractToModel(meetingContract)).thenReturn(meetingModel);
        when(mockService.save(meetingModel)).thenReturn(savedMeetingModel);
        Meeting savedMeetingContract = new Meeting();
        when(mockMapper.mapMeetingModelToContract(savedMeetingModel)).thenReturn(savedMeetingContract);

        // When
        ResponseEntity<Meeting> response = controller.saveMeeting(meetingContract);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedMeetingContract, response.getBody());
    }

    // Meeting with unavailable participant throws ParticipantNotAvailableException
    @Test
    void meeting_could_not_be_saved_due_to_unavailable_participant() throws MeetingRoomNotAvailableException, ParticipantNotAvailableException {
        // Arrange
        MeetingService service = Mockito.mock(MeetingService.class);
        ModelContractMapper mapper = Mockito.mock(ModelContractMapper.class);
        MeetingCommandController controller = new MeetingCommandController();
        controller.setService(service);
        controller.setMapper(mapper);
        Meeting meeting = new Meeting();
        Participant participant = new Participant();
        participant.setName("Mehmet Yildiz");
        meeting.setParticipants(Collections.singletonList(participant));
        org.ssb.meet.model.Meeting modelMeeting = new org.ssb.meet.model.Meeting();
        Mockito.when(mapper.mapMeetingContractToModel(meeting)).thenReturn(modelMeeting);
        Mockito.when(service.save(modelMeeting)).thenThrow(new ParticipantNotAvailableException("Unavailable participant"));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            controller.saveMeeting(meeting);
        }, "Unavailable participant");
    }

    // Meeting with unavailable meeting room throws MeetingRoomNotAvailableException
    @Test
    void meeting_could_not_be_saved_due_to_unavailable_meeting_room() throws MeetingRoomNotAvailableException, ParticipantNotAvailableException {
        // Arrange
        MeetingService mockService = mock(MeetingService.class);
        ModelMapper modelMapper = new ModelMapper();
        ModelContractMapper mapper = new ModelContractMapperImpl(modelMapper);
        MeetingCommandController controller = new MeetingCommandController();
        controller.setService(mockService);
        controller.setMapper(mapper);
        Meeting meeting = new Meeting();
        meeting.setId(1L);
        meeting.setTitle("Test Meeting");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);
        meeting.setStartTime(startTime);
        meeting.setEndTime(endTime);
        MeetingRoom meetingRoom = new MeetingRoom();
        meetingRoom.setId(1L);
        meetingRoom.setName("Test Room");
        meeting.setMeetingRoom(meetingRoom);
        List<Participant> participants = new ArrayList<>();
        Participant participant = new Participant();
        participant.setId(1L);
        participant.setName("Test Participant");
        participant.setEmail("test@example.com");
        participants.add(participant);
        meeting.setParticipants(participants);
        when(mockService.save(any())).thenThrow(new MeetingRoomNotAvailableException("Meeting room is not available"));

        // When
        Executable saveMeeting = () -> controller.saveMeeting(meeting);

        // Then
        assertThrows(RuntimeException.class, saveMeeting, "Meeting room is not available");
    }

}
