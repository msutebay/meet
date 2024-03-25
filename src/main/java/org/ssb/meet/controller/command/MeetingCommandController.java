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

/**
 * MeetingCommandController is a REST controller class that handles meeting command operations.
 * It provides endpoints for creating and deleting meetings.
 * This class implements the MeetingCommandControllerApi interface.
 *
 * Methods:
 * - deleteMeeting(Long id): ResponseEntity - Deletes a meeting with the specified ID.
 *   - @Override - Indicates that this method overrides the deleteMeeting method from the MeetingCommandControllerApi interface.
 *   - @RequestMapping - Specifies the HTTP DELETE endpoint for deleting a meeting.
 *   - @Parameter - Specifies the ID of the meeting to be deleted as a path variable.
 *   - @ApiResponse - Specifies the response code and description for the deleteMeeting operation.
 *
 * - saveMeeting(Meeting meeting): ResponseEntity - Creates a new meeting.
 *   - @Override - Indicates that this method overrides the saveMeeting method from the MeetingCommandControllerApi interface.
 *   - @RequestMapping - Specifies the HTTP POST endpoint for creating a meeting.
 *   - @Parameter - Specifies the meeting object to be created as a request body.
 *   - @ApiResponse - Specifies the response codes and descriptions for the saveMeeting operation.
 *
 * Dependencies:
 * - MeetingService - A service class that handles meeting-related operations.
 * - ModelContractMapper - A mapper interface for mapping between domain models and contract models.
 *
 */
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
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Meeting> saveMeeting(Meeting meeting) {
        try {
            org.ssb.meet.model.Meeting savedMeeting = service.save(
                        mapper.mapMeetingContractToModel(meeting)
            );
            return ResponseEntity.ok().body(mapper.mapMeetingModelToContract(savedMeeting));
        } catch (MeetingRoomNotAvailableException | ParticipantNotAvailableException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
