package org.ssb.meet.controller.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.ssb.meet.mapper.ModelContractMapper;
import org.ssb.meet.openapi.api.MeetingRoomQueryControllerApi;
import org.ssb.meet.openapi.model.MeetingRoom;
import org.ssb.meet.service.MeetingRoomService;
import java.util.List;

/**
 * MeetingRoomQueryController is a REST controller class that handles meeting room queries.
 * It implements the MeetingRoomQueryControllerApi interface.
 * This class is responsible for handling HTTP GET requests for retrieving meeting rooms.
 * It uses the MeetingRoomService and ModelContractMapper classes for retrieving and mapping meeting room data.
 * The getMeetingRooms method returns a ResponseEntity containing a list of MeetingRoom objects.
 * The MeetingRoom objects are retrieved from the MeetingRoomService and mapped using the ModelContractMapper.
 * This class is annotated with @RestController to indicate that it is a REST controller.
 */
@RestController
public class MeetingRoomQueryController implements MeetingRoomQueryControllerApi {

    private final MeetingRoomService service;

    private final ModelContractMapper mapper;

    @Autowired
    public MeetingRoomQueryController(MeetingRoomService service, ModelContractMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<List<MeetingRoom>> getMeetingRooms() {
        return ResponseEntity.ok().body(service.getAll().stream().map(mapper::mapMeetingRoomModelToContract).toList());
    }
}
