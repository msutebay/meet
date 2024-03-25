package org.ssb.meet.controller.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.ssb.meet.mapper.ModelContractMapper;
import org.ssb.meet.openapi.api.MeetingQueryControllerApi;
import org.ssb.meet.openapi.model.Meeting;
import org.ssb.meet.service.MeetingService;

import java.util.List;

/**
 * MeetingQueryController is a REST controller class that handles meeting query operations.
 * It implements the MeetingQueryControllerApi interface.
 * This class is responsible for retrieving all meetings.
 *
 * The class is responsible for handling HTTP requests and returning the appropriate response.
 *
 * Injected the MeetingService and ModelContractMapper into the controller.
 * These dependencies are required for retrieving the meetings and mapping them to the contract model.
 *
 * The getMeetings() method is an implementation of the getMeetings() method defined in the MeetingQueryControllerApi interface.
 * It retrieves all meetings from the MeetingService and maps them to the contract model using the ModelContractMapper.
 * The meetings are then returned as a ResponseEntity with a status code of 200 (OK).
 *
 * returns ResponseEntity - A response entity containing a list of meetings and a status code of 200 (OK).
 */
@RestController
public class MeetingQueryController implements MeetingQueryControllerApi {

    private final MeetingService service;

    private final ModelContractMapper mapper;

    @Autowired
    public MeetingQueryController(MeetingService service, ModelContractMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<List<Meeting>> getMeetings() {
        return ResponseEntity.ok().body(service.getAll().stream().map(mapper::mapMeetingModelToContract).toList());
    }
}
