package org.ssb.meet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssb.meet.entity.ParticipantEntity;
import org.ssb.meet.mapper.ModelEntityMapper;
import org.ssb.meet.model.Participant;
import org.ssb.meet.repository.ParticipantRepository;
import java.util.List;
import java.util.Optional;

/**
 * The ParticipantService class is responsible for handling operations related to participants.
 * It provides methods for saving, retrieving, and deleting participants.
 *
 * The ParticipantService class has the following dependencies:
 * - ParticipantRepository: A repository interface for performing CRUD operations on ParticipantEntity objects.
 * - ModelEntityMapper: A mapper interface for mapping between Participant and ParticipantEntity objects.
 *
 * The ParticipantService class has the following methods:
 * - save: Saves a participant by mapping it to a ParticipantEntity object and calling the save method of the repository.
 * - getAll: Retrieves all participants from the repository and maps them to Participant objects.
 * - getById: Retrieves a participant by its ID from the repository and maps it to a Participant object.
 * - delete: Deletes a participant by its ID from the repository.
 *
 */
@Service
@Transactional
public class ParticipantService implements CrudUseCase<Participant> {

    private final ParticipantRepository repository;
    private final ModelEntityMapper mapper;

    @Autowired
    public ParticipantService(
            ParticipantRepository repository,
            ModelEntityMapper modelEntityMapper
    ) {
        this.repository = repository;
        this.mapper = modelEntityMapper;
    }

    @Override
    public Participant save(Participant participant) {
        ParticipantEntity entity = mapper.mapParticipantModelToEntity(participant);
        return mapper.mapParticipantEntityToModel(repository.save(entity));
    }

    @Override
    public List<Participant> getAll() {
        return repository.findAll().stream().map(mapper::mapParticipantEntityToModel).toList();
    }

    @Override
    public Optional<Participant> getById(Long id) {
        return repository.findById(id).map(mapper::mapParticipantEntityToModel);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
