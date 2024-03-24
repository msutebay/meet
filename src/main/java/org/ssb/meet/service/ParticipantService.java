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

@Service
@Transactional
public class ParticipantService {

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

    public Participant saveParticipant(Participant participant) {
        ParticipantEntity entity = mapper.mapParticipantModelToEntity(participant);
        return mapper.mapParticipantEntityToModel(repository.save(entity));
    }

    public List<Participant> getAllParticipants() {
        return repository.findAll().stream().map(mapper::mapParticipantEntityToModel).toList();
    }

    public Optional<Participant> getParticipantById(Long id) {
        return repository.findById(id).map(mapper::mapParticipantEntityToModel);
    }

    public void deleteParticipantById(Long id) {
        repository.deleteById(id);
    }
}
