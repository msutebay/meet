package org.ssb.meet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssb.meet.entity.MeetingRoomEntity;
import org.ssb.meet.mapper.ModelEntityMapper;
import org.ssb.meet.model.MeetingRoom;
import org.ssb.meet.repository.MeetingRoomRepository;
import java.util.List;
import java.util.Optional;

/**
 * The MeetingRoomService class is responsible for handling operations related to meeting rooms.
 * It provides methods to save, retrieve, and delete meeting rooms.
 *
 * The MeetingRoomService class has a dependency on the MeetingRoomRepository and ModelEntityMapper interfaces.
 *
 * The MeetingRoomService class provides the following methods:
 * - save: Saves a meeting room by mapping the MeetingRoom object to a MeetingRoomEntity and saving it in the repository.
 * - getAll: Retrieves all meeting rooms from the repository and maps them to MeetingRoom objects.
 * - getById: Retrieves a meeting room by its ID from the repository and maps it to a MeetingRoom object.
 * - delete: Deletes a meeting room by its ID from the repository.
 *
 */
@Service
@Transactional
public class MeetingRoomService implements CrudUseCase<MeetingRoom> {

    private final MeetingRoomRepository repository;
    private final ModelEntityMapper mapper;

    @Autowired
    public MeetingRoomService(
            MeetingRoomRepository repository,
            ModelEntityMapper modelEntityMapper
    ) {
        this.repository = repository;
        this.mapper = modelEntityMapper;
    }

    @Override
    public MeetingRoom save(MeetingRoom meetingRoom) {
        MeetingRoomEntity entity = mapper.mapMeetingRoomModelToEntity(meetingRoom);
        return mapper.mapMeetingRoomEntityToModel(repository.save(entity));
    }

    @Override
    public List<MeetingRoom> getAll() {
        return repository.findAll().stream().map(mapper::mapMeetingRoomEntityToModel).toList();
    }

    @Override
    public Optional<MeetingRoom> getById(Long id) {
        return repository.findById(id).map(mapper::mapMeetingRoomEntityToModel);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
