package org.ssb.meet.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ssb.meet.model.Meeting;
import org.ssb.meet.model.MeetingRoom;
import org.ssb.meet.model.Participant;

import java.util.function.Function;

@Component
public class ModelContractMapperImpl implements ModelContractMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public ModelContractMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public org.ssb.meet.openapi.model.Participant mapParticipantModelToContract(Participant domainModel) {
        return mapDomainModelToContract(
                domainModel,
                p -> new org.ssb.meet.openapi.model.Participant(p.getId(), p.getName(), p.getEmail())
        );
    }

    @Override
    public Participant mapParticipantContractToModel(org.ssb.meet.openapi.model.Participant contract) {
        return mapContractToDomainModel(
                contract,
                p -> new Participant(p.getId(), p.getName(),p.getEmail())
        );
    }

    @Override
    public org.ssb.meet.openapi.model.MeetingRoom mapMeetingRoomModelToContract(MeetingRoom domainModel) {
        return modelMapper.map(domainModel, org.ssb.meet.openapi.model.MeetingRoom.class);
    }

    @Override
    public MeetingRoom mapMeetingRoomContractToModel(org.ssb.meet.openapi.model.MeetingRoom contract) {
        return modelMapper.map(contract, MeetingRoom.class);
    }

    @Override
    public org.ssb.meet.openapi.model.Meeting mapMeetingModelToContract(Meeting domainModel) {
        return modelMapper.map(domainModel, org.ssb.meet.openapi.model.Meeting.class);
    }

    @Override
    public Meeting mapMeetingContractToModel(org.ssb.meet.openapi.model.Meeting contract) {
        return modelMapper.map(contract, Meeting.class);
    }

    /*
    @Override
    public org.ssb.meet.openapi.model.Participant mapParticipantModelToContract(Participant domainModel) {
        return modelMapper.map(domainModel, org.ssb.meet.openapi.model.Participant.class);
    }

    @Override
    public Participant mapParticipantContractToModel(org.ssb.meet.openapi.model.Participant contract) {
        return modelMapper.map(contract, Participant.class);
    }
    */

    private <D, C> C mapDomainModelToContract(D domainModel, Function<D, C> mapper) {
        return mapper.apply(domainModel);
    }

    private <C, D> D mapContractToDomainModel(C contract, Function<C, D> mapper) {
        return mapper.apply(contract);
    }
}
