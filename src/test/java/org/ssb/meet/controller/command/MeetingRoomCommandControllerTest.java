package org.ssb.meet.controller.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.ssb.meet.mapper.ModelContractMapper;
import org.ssb.meet.openapi.model.MeetingRoom;
import org.ssb.meet.service.validators.InputValidator;
import org.ssb.meet.service.MeetingRoomService;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class MeetingRoomCommandControllerTest {

    MeetingRoomCommandController controller = new MeetingRoomCommandController();
    MeetingRoomService mockService;
    ModelContractMapper mockMapper;
    InputValidator validator;
    MessageSource messageSource;

    @BeforeEach
    void setUp() {
        mockService = mock(MeetingRoomService.class);
        mockMapper = mock(ModelContractMapper.class);
        validator = new InputValidator();
        messageSource = mock(MessageSource.class);

        controller.setService(mockService);
        controller.setMapper(mockMapper);
        controller.setInputValidator(validator);
        controller.setMessageSource(messageSource);
    }

    // Valid MeetingRoom object is passed, with name length between 3 and 20 characters
    @Test
    void meeting_room_successfully_saved() {
        // Arrange
        MeetingRoom meetingRoom = new MeetingRoom(1L, "Valid Room");
        org.ssb.meet.model.MeetingRoom savedMeetingRoom = new org.ssb.meet.model.MeetingRoom(1L, "Valid Room");
        when(mockService.save(savedMeetingRoom)).thenReturn(savedMeetingRoom);
        when(mockMapper.mapMeetingRoomContractToModel(meetingRoom)).thenReturn(savedMeetingRoom);
        when(mockMapper.mapMeetingRoomModelToContract(savedMeetingRoom)).thenReturn(meetingRoom);

        // When
        ResponseEntity<MeetingRoom> response = controller.saveMeetingRoom(meetingRoom);

        // Then
        await().untilAsserted(() -> assertNotNull(response));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // MeetingRoom name length is less than 3 characters
    @Test
    void short_meeting_room_names_are_not_allowed() {
        // Arrange
        MeetingRoom meetingRoom = new MeetingRoom(1L, "AB");

        // When
        ResponseEntity<MeetingRoom> response = controller.saveMeetingRoom(meetingRoom);

        // Then
        await().untilAsserted(() -> assertNotNull(response));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // MeetingRoom name length is greater than 20 characters
    @Test
    void long_meeting_room_names_are_not_allowed() {
        // Arrange
        MeetingRoom meetingRoom = new MeetingRoom(1L, "This is a very long meeting room name");

        // When
        ResponseEntity<MeetingRoom> response = controller.saveMeetingRoom(meetingRoom);

        // Then
        await().untilAsserted(() -> assertNotNull(response));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
