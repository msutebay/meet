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
        when(mockMapper.mapMeetingContractToModel(meetingContract)).thenReturn(meetingModel);

        org.ssb.meet.model.Meeting savedMeetingModel = new org.ssb.meet.model.Meeting();
        when(mockService.saveMeeting(meetingModel)).thenReturn(savedMeetingModel);

        Meeting savedMeetingContract = new Meeting();
        when(mockMapper.mapMeetingModelToContract(savedMeetingModel)).thenReturn(savedMeetingContract);

        // Act
        ResponseEntity<Meeting> response = controller.saveMeeting(meetingContract);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedMeetingContract, response.getBody());
    }

    // Meeting with unavailable participant throws ParticipantNotAvailableException
    @Test
    void meeting_could_not_be_saved_due_to_unavailable_participant() throws MeetingRoomNotAvailableException, ParticipantNotAvailableException {
        // Create a mock MeetingService
        MeetingService service = Mockito.mock(MeetingService.class);

        // Create a mock ModelContractMapper
        ModelContractMapper mapper = Mockito.mock(ModelContractMapper.class);

        // Create a MeetingCommandController instance and set the mock objects
        MeetingCommandController controller = new MeetingCommandController();
        controller.setService(service);
        controller.setMapper(mapper);

        // Create a sample Meeting object
        Meeting meeting = new Meeting();

        // Create a sample Participant object
        Participant participant = new Participant();
        participant.setName("John Doe");

        // Add the participant to the meeting's participants list
        meeting.setParticipants(Collections.singletonList(participant));

        // Create a sample org.ssb.meet.model.Meeting object
        org.ssb.meet.model.Meeting modelMeeting = new org.ssb.meet.model.Meeting();

        // Mock the mapper's mapMeetingContractToModel method to return the modelMeeting object
        Mockito.when(mapper.mapMeetingContractToModel(meeting)).thenReturn(modelMeeting);

        // Mock the service's saveMeeting method to throw a ParticipantNotAvailableException
        Mockito.when(service.saveMeeting(modelMeeting)).thenThrow(new ParticipantNotAvailableException("Unavailable participant"));

        // Assert that the controller's saveMeeting method throws a RuntimeException with the correct message
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

        when(mockService.saveMeeting(any())).thenThrow(new MeetingRoomNotAvailableException("Meeting room is not available"));

        // Act
        Executable saveMeeting = () -> controller.saveMeeting(meeting);

        // Assert
        assertThrows(RuntimeException.class, saveMeeting, "Meeting room is not available");
    }

}
