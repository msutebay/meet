package org.ssb.meet.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.ssb.meet.mapper.ModelContractMapper;
import org.ssb.meet.openapi.api.MeetingRoomCommandControllerApi;
import org.ssb.meet.openapi.model.ApiError;
import org.ssb.meet.openapi.model.MeetingRoom;
import org.ssb.meet.service.InputValidator;
import org.ssb.meet.service.MeetingRoomService;

import java.util.Locale;

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
        return MeetingRoomCommandControllerApi.super.deleteMeetingRoom(id);
    }

    @Override
    public ResponseEntity<MeetingRoom> saveMeetingRoom(MeetingRoom meetingRoom) {
        if(!inputValidator.validateMeetingRoomName(meetingRoom.getName())) {
            ResponseEntity<ApiError> error = ResponseEntity.badRequest().body(
                    new ApiError(messageSource.getMessage("invalid.meeting.room.name", null, Locale.getDefault()))
            );
            return (ResponseEntity<MeetingRoom>)(ResponseEntity<?>) error;
        }

        org.ssb.meet.model.MeetingRoom savedMeetingRoom = service.saveMeetingRoom(
                mapper.mapMeetingRoomContractToModel(meetingRoom)
        );
        return ResponseEntity.ok().body(mapper.mapMeetingRoomModelToContract(savedMeetingRoom));
    }
}
