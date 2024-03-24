package org.ssb.meet.controller.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.ssb.meet.openapi.api.ParticipantQueryControllerApi;
import org.ssb.meet.openapi.model.Participant;
import org.ssb.meet.mapper.ModelContractMapper;
import org.ssb.meet.service.ParticipantService;

import java.util.List;

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
        return ResponseEntity.ok().body(service.getAllParticipants().stream().map(mapper::mapParticipantModelToContract).toList());
    }
}
