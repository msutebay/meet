package org.ssb.meet.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.ssb.meet.exception.MeetingRoomNotAvailableException;
import org.ssb.meet.exception.ParticipantNotAvailableException;
import org.ssb.meet.mapper.ModelContractMapper;
import org.ssb.meet.openapi.api.MeetingCommandControllerApi;
import org.ssb.meet.openapi.model.Meeting;
import org.ssb.meet.service.MeetingService;

@RestController
public class MeetingCommandController implements MeetingCommandControllerApi {

    private MeetingService service;

    private ModelContractMapper mapper;

    @Autowired
    public void setService(MeetingService service) {
        this.service = service;
    }

    @Autowired
    public void setMapper(ModelContractMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Void> deleteMeeting(Long id) {
        return MeetingCommandControllerApi.super.deleteMeeting(id);
    }

    @Override
    public ResponseEntity<Meeting> saveMeeting(Meeting meeting) {
        try {
            org.ssb.meet.model.Meeting savedMeeting = service.saveMeeting(
                        mapper.mapMeetingContractToModel(meeting)
            );
            return ResponseEntity.ok().body(mapper.mapMeetingModelToContract(savedMeeting));
        } catch (MeetingRoomNotAvailableException | ParticipantNotAvailableException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
