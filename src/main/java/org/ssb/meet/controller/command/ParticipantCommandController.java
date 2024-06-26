package org.ssb.meet.controller.command;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.ssb.meet.openapi.api.ParticipantCommandControllerApi;
import org.ssb.meet.openapi.model.Participant;
import org.ssb.meet.mapper.ModelContractMapper;
import org.ssb.meet.service.ParticipantService;

/**
 * ParticipantCommandController is a REST controller class that handles participant-related operations.
 * It implements the ParticipantCommandControllerApi interface.
 * This class is responsible for handling HTTP requests and delegating the processing to the ParticipantService class.
 * It also uses the ModelContractMapper class for mapping between contract and model objects.
 *
 * This class provides the following operations:
 * - deleteParticipant: Deletes a participant by ID.
 * - saveParticipant: Saves a new participant.
 *
 */
@RestController
public class ParticipantCommandController implements ParticipantCommandControllerApi {

    private ParticipantService service;

    private ModelContractMapper mapper;

    @Autowired
    public void setService(ParticipantService service) {
        this.service = service;
    }

    @Autowired
    public void setMapper(ModelContractMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Void> deleteParticipant(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Participant> saveParticipant(@Valid Participant participant) {
        org.ssb.meet.model.Participant savedParticipant = service.save(
                mapper.mapParticipantContractToModel(participant)
        );
        return ResponseEntity.ok().body(mapper.mapParticipantModelToContract(savedParticipant));
    }
}
