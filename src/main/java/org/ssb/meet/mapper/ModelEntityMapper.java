package org.ssb.meet.mapper;

import org.ssb.meet.entity.MeetingEntity;
import org.ssb.meet.entity.MeetingRoomEntity;
import org.ssb.meet.entity.ParticipantEntity;
import org.ssb.meet.model.Meeting;
import org.ssb.meet.model.MeetingRoom;
import org.ssb.meet.model.Participant;

public interface ModelEntityMapper {

    ParticipantEntity mapParticipantModelToEntity(Participant domainModel);
    Participant mapParticipantEntityToModel(ParticipantEntity entity);
    MeetingRoomEntity mapMeetingRoomModelToEntity(MeetingRoom domainModel);
    MeetingRoom mapMeetingRoomEntityToModel(MeetingRoomEntity entity);
    MeetingEntity mapMeetingModelToEntity(Meeting domainModel);
    Meeting mapMeetingEntityToModel(MeetingEntity entity);
}
