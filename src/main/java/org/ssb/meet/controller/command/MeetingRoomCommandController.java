package org.ssb.meet.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.ssb.meet.mapper.ModelContractMapper;
import org.ssb.meet.openapi.api.MeetingRoomCommandControllerApi;
import org.ssb.meet.openapi.model.ApiError;
import org.ssb.meet.openapi.model.MeetingRoom;
import org.ssb.meet.service.validators.InputValidator;
import org.ssb.meet.service.MeetingRoomService;
import java.util.Locale;

/**
 * The MeetingRoomCommandController class is a REST controller that handles the command operations for meeting rooms.
 * It implements the MeetingRoomCommandControllerApi interface, which defines the REST endpoints for creating and deleting meeting rooms.
 *
 * This class is responsible for validating the input data and performing the necessary operations using the MeetingRoomService, InputValidator, ModelContractMapper, and MessageSource dependencies.
 *
 * The deleteMeetingRoom method handles the DELETE /meeting-rooms/{id} endpoint, which deletes a meeting room with the specified ID.
 *
 * The saveMeetingRoom method handles the POST /meeting-rooms endpoint, which creates a new meeting room.
 * It validates the meeting room name using the InputValidator and returns an error response if the name is invalid.
 * Otherwise, it saves the meeting room using the MeetingRoomService and returns the saved meeting room as a response.
 *
 * This class is annotated with @RestController to indicate that it is a REST controller.
 * It also autowires the MeetingRoomService, InputValidator, ModelContractMapper, and MessageSource dependencies using the @Autowired annotation.
 */
@RestController
public class MeetingRoomCommandController implements MeetingRoomCommandControllerApi {

    private MeetingRoomService service;

    private InputValidator inputValidator;

    private ModelContractMapper mapper;

    private MessageSource messageSource;

    @Autowired
    public void setService(MeetingRoomService service) {
        this.service = service;
    }

    @Autowired
    public void setInputValidator(InputValidator inputValidator) {
        this.inputValidator = inputValidator;
    }

    @Autowired
    public void setMapper(ModelContractMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public ResponseEntity<Void> deleteMeetingRoom(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<MeetingRoom> saveMeetingRoom(MeetingRoom meetingRoom) {
        if(!inputValidator.validateMeetingRoomName(meetingRoom.getName())) {
            ResponseEntity<ApiError> error = ResponseEntity.badRequest().body(
                    new ApiError(messageSource.getMessage("invalid.meeting.room.name", null, Locale.getDefault()))
            );
            return (ResponseEntity<MeetingRoom>)(ResponseEntity<?>) error;
        }

        org.ssb.meet.model.MeetingRoom savedMeetingRoom = service.save(
                mapper.mapMeetingRoomContractToModel(meetingRoom)
        );
        return ResponseEntity.ok().body(mapper.mapMeetingRoomModelToContract(savedMeetingRoom));
    }
}
