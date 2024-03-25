package org.ssb.meet.controller.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.ssb.meet.openapi.api.ParticipantQueryControllerApi;
import org.ssb.meet.openapi.model.Participant;
import org.ssb.meet.mapper.ModelContractMapper;
import org.ssb.meet.service.ParticipantService;
import java.util.List;

/**
 * ParticipantQueryController is a REST controller class that handles participant query operations.
 * It implements the ParticipantQueryControllerApi interface.
 * This class is responsible for retrieving all participants.
 *
 * The constructor initializes the ParticipantService and ModelContractMapper dependencies.
 *
 * The getParticipants() method is an overridden method from the ParticipantQueryControllerApi interface.
 * It retrieves all participants using the ParticipantService and maps them to the Participant model using the ModelContractMapper.
 * The participants are then returned as a ResponseEntity with a status code of 200 (OK).
 *
 * This class is part of the participant-query-controller package.
 */
@RestController
public class ParticipantQueryController implements ParticipantQueryControllerApi {

    private final ParticipantService service;

    private final ModelContractMapper mapper;

    @Autowired
    public ParticipantQueryController(ParticipantService service, ModelContractMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<List<Participant>> getParticipants() {
        return ResponseEntity.ok().body(service.getAll().stream().map(mapper::mapParticipantModelToContract).toList());
    }
}
