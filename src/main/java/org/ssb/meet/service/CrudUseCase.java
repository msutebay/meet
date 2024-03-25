package org.ssb.meet.service;

import org.ssb.meet.exception.MeetingRoomNotAvailableException;
import org.ssb.meet.exception.ParticipantNotAvailableException;
import org.ssb.meet.model.BaseModel;

import java.util.List;
import java.util.Optional;

public interface CrudUseCase<T extends BaseModel> {
    T save(T entity) throws MeetingRoomNotAvailableException, ParticipantNotAvailableException;
    List<T> getAll();
    Optional<T> getById(Long id);
    void delete(Long id);
}
