package org.ssb.meet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssb.meet.entity.MeetingEntity;
import org.ssb.meet.exception.MeetingRoomNotAvailableException;
import org.ssb.meet.exception.ParticipantNotAvailableException;
import org.ssb.meet.mapper.ModelEntityMapper;
import org.ssb.meet.model.Meeting;
import org.ssb.meet.model.Participant;
import org.ssb.meet.repository.MeetingRepository;
import java.util.List;
import java.util.Optional;

/**
 * The MeetingService class is responsible for managing meetings.
 * It provides methods for saving, retrieving, and deleting meetings.
 * The class also checks the availability of meeting rooms and participants before saving a meeting.
 *
 * throws MeetingRoomNotAvailableException if the meeting room is not available at the proposed time.
 * throws ParticipantNotAvailableException if any participant is not available at the proposed time.
 */
@Service
@Transactional
public class MeetingService implements CrudUseCase<Meeting> {

    private final MeetingRepository repository;
    private final ModelEntityMapper mapper;

    @Autowired
    public MeetingService(
            MeetingRepository repository,
            ModelEntityMapper modelEntityMapper
    ) {
        this.repository = repository;
        this.mapper = modelEntityMapper;
    }

    @Override
    public Meeting save(Meeting meeting) throws MeetingRoomNotAvailableException, ParticipantNotAvailableException {
        checkProposedTimeAvailability(meeting);
        MeetingEntity entity = mapper.mapMeetingModelToEntity(meeting);
        return mapper.mapMeetingEntityToModel(repository.save(entity));
    }

    @Override
    public List<Meeting> getAll() {
        return repository.findAll().stream().map(mapper::mapMeetingEntityToModel).toList();
    }

    @Override
    public Optional<Meeting> getById(Long id) {
        return repository.findById(id).map(mapper::mapMeetingEntityToModel);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void checkProposedTimeAvailability(Meeting meeting) throws MeetingRoomNotAvailableException, ParticipantNotAvailableException {
        checkMeetingRoomAvailability(meeting);
        checkParticipantsAvailability(meeting);
    }

    private void checkParticipantsAvailability(Meeting meeting) throws ParticipantNotAvailableException {
        List<MeetingEntity> conflictingMeetings;

        for (Participant participant : meeting.getParticipants()) {
            conflictingMeetings = repository.findByParticipantsContainsAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                    mapper.mapParticipantModelToEntity(participant),
                    meeting.getEndTime(),
                    meeting.getStartTime()
            );

            if (!conflictingMeetings.isEmpty()) {
                // Participant is not available at the proposed time
                throw new ParticipantNotAvailableException(
                        "Unavailable participant:" + participant.getName()
                );
            }
        }
    }

    private void checkMeetingRoomAvailability(Meeting meeting) throws MeetingRoomNotAvailableException {
        List<MeetingEntity> conflictingMeetings = repository.findByMeetingRoomAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                mapper.mapMeetingRoomModelToEntity(meeting.getMeetingRoom()),
                meeting.getEndTime(),
                meeting.getStartTime()
        );

        if (!conflictingMeetings.isEmpty()) {
            // Meeting room is not available at the proposed time
            throw new MeetingRoomNotAvailableException("Meeting room is not available");
        }
    }
}
