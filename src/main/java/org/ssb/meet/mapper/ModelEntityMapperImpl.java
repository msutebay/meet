package org.ssb.meet.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ssb.meet.entity.MeetingEntity;
import org.ssb.meet.entity.MeetingRoomEntity;
import org.ssb.meet.entity.ParticipantEntity;
import org.ssb.meet.model.Meeting;
import org.ssb.meet.model.MeetingRoom;
import org.ssb.meet.model.Participant;

import java.util.function.Function;

@Component
public class ModelEntityMapperImpl implements ModelEntityMapper{

    private final ModelMapper modelMapper;

    @Autowired
    public ModelEntityMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public ParticipantEntity mapParticipantModelToEntity(Participant domainModel) {
        return mapDomainModelToEntity(
                domainModel,
                p -> new ParticipantEntity(p.getId(), p.getName(), p.getEmail())
        );
    }

    @Override
    public Participant mapParticipantEntityToModel(ParticipantEntity entity) {
        return mapEntityToDomainModel(
                entity,
                p -> new Participant(p.getId(), p.getName(), p.getEmail())
        );
    }

    @Override
    public MeetingRoomEntity mapMeetingRoomModelToEntity(MeetingRoom domainModel) {
        return modelMapper.map(domainModel, MeetingRoomEntity.class);
    }

    @Override
    public MeetingRoom mapMeetingRoomEntityToModel(MeetingRoomEntity entity) {
        return modelMapper.map(entity, MeetingRoom.class);
    }

    @Override
    public MeetingEntity mapMeetingModelToEntity(Meeting domainModel) {
        return modelMapper.map(domainModel, MeetingEntity.class);
    }

    @Override
    public Meeting mapMeetingEntityToModel(MeetingEntity entity) {
        return modelMapper.map(entity, Meeting.class);
    }

    private <D, E> E mapDomainModelToEntity(D domainModel, Function<D, E> mapper) {
        return mapper.apply(domainModel);
    }

    private <E, D> D mapEntityToDomainModel(E entity, Function<E, D> mapper) {
        return mapper.apply(entity);
    }
}
