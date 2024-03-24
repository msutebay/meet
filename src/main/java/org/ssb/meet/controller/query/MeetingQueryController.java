package org.ssb.meet.controller.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.ssb.meet.mapper.ModelContractMapper;
import org.ssb.meet.openapi.api.MeetingQueryControllerApi;
import org.ssb.meet.openapi.model.Meeting;
import org.ssb.meet.service.MeetingService;

import java.util.List;

@RestController
public class MeetingQueryController implements MeetingQueryControllerApi {

    @Autowired
    private MeetingService service;

    @Autowired
    private ModelContractMapper mapper;

    @Override
    public ResponseEntity<List<Meeting>> getMeetings() {
        return ResponseEntity.ok().body(service.getAllMeetings().stream().map(mapper::mapMeetingModelToContract).toList());
    }
}
