package org.ssb.meet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssb.meet.entity.MeetingRoomEntity;
import org.ssb.meet.entity.ParticipantEntity;
import org.ssb.meet.mapper.ModelEntityMapper;
import org.ssb.meet.model.MeetingRoom;
import org.ssb.meet.model.Participant;
import org.ssb.meet.repository.MeetingRoomRepository;
import org.ssb.meet.repository.ParticipantRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MeetingRoomService {

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

    public MeetingRoom saveMeetingRoom(MeetingRoom meetingRoom) {
        MeetingRoomEntity entity = mapper.mapMeetingRoomModelToEntity(meetingRoom);
        return mapper.mapMeetingRoomEntityToModel(repository.save(entity));
    }

    public List<MeetingRoom> getAllMeetingRooms() {
        return repository.findAll().stream().map(mapper::mapMeetingRoomEntityToModel).toList();
    }

    public Optional<MeetingRoom> getMeetingRoomById(Long id) {
        return repository.findById(id).map(mapper::mapMeetingRoomEntityToModel);
    }

    public void deleteMeetingRoomById(Long id) {
        repository.deleteById(id);
    }
}
