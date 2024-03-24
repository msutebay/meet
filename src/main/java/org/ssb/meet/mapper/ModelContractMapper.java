package org.ssb.meet.mapper;

import org.ssb.meet.model.Meeting;
import org.ssb.meet.model.MeetingRoom;
import org.ssb.meet.model.Participant;

public interface ModelContractMapper {
    org.ssb.meet.openapi.model.Participant mapParticipantModelToContract(Participant domainModel);
    Participant mapParticipantContractToModel(org.ssb.meet.openapi.model.Participant contract);
    org.ssb.meet.openapi.model.MeetingRoom mapMeetingRoomModelToContract(MeetingRoom domainModel);
    MeetingRoom mapMeetingRoomContractToModel(org.ssb.meet.openapi.model.MeetingRoom contract);
    org.ssb.meet.openapi.model.Meeting mapMeetingModelToContract(Meeting domainModel);
    Meeting mapMeetingContractToModel(org.ssb.meet.openapi.model.Meeting contract);
}