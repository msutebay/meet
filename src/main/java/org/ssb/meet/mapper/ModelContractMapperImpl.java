package org.ssb.meet.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ssb.meet.model.Meeting;
import org.ssb.meet.model.MeetingRoom;
import org.ssb.meet.model.Participant;
import java.util.function.Function;

/**
 * ModelContractMapperImpl is a class that implements the ModelContractMapper interface.
 * It provides methods to map between domain models and contract models.
 * The mapping is done using the ModelMapper library.
 *
 * This class is annotated with @Component, indicating that it is a Spring component and can be autowired.
 * It has a constructor that takes a ModelMapper object as a parameter and autowires it using the @Autowired annotation.
 *
 * The class implements the methods defined in the ModelContractMapper interface.
 * These methods perform the mapping between domain models and contract models using the ModelMapper library.
 *
 * The class also defines two private helper methods, mapDomainModelToContract and mapContractToDomainModel,
 * which are used to perform the actual mapping using lambda expressions.
 *
 */
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

    private <D, C> C mapDomainModelToContract(D domainModel, Function<D, C> mapper) {
        return mapper.apply(domainModel);
    }

    private <C, D> D mapContractToDomainModel(C contract, Function<C, D> mapper) {
        return mapper.apply(contract);
    }
}
