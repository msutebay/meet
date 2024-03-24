package org.ssb.meet.controller.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.ssb.meet.mapper.ModelContractMapper;
import org.ssb.meet.openapi.api.MeetingRoomQueryControllerApi;
import org.ssb.meet.openapi.model.MeetingRoom;
import org.ssb.meet.service.MeetingRoomService;
import java.util.List;

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
        return ResponseEntity.ok().body(service.getAllMeetingRooms().stream().map(mapper::mapMeetingRoomModelToContract).toList());
    }
}
