package org.ssb.meet.controller.command;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.ssb.meet.mapper.ModelContractMapper;
import org.ssb.meet.openapi.model.Participant;
import org.ssb.meet.service.ParticipantService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ParticipantCommandControllerTest {

    // Controller successfully saves a new participant
    @Test
    void participant_successfully_saved() {
        // Arrange
        ParticipantService mockService = mock(ParticipantService.class);
        ModelContractMapper mockMapper = mock(ModelContractMapper.class);
        ParticipantCommandController controller = new ParticipantCommandController();
        controller.setService(mockService);
        controller.setMapper(mockMapper);
        Participant participant = new Participant(1L, "Mehmet Yildiz", "mehmetyildiz@example.com");
        org.ssb.meet.model.Participant savedParticipant = new org.ssb.meet.model.Participant(1L, "Mehmet Yildiz", "mehmetyildiz@example.com");
        when(mockMapper.mapParticipantContractToModel(participant)).thenReturn(savedParticipant);
        when(mockService.save(savedParticipant)).thenReturn(savedParticipant);
        when(mockMapper.mapParticipantModelToContract(savedParticipant)).thenReturn(participant);

        // When
        ResponseEntity<Participant> response = controller.saveParticipant(participant);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(participant, response.getBody());
        verify(mockMapper).mapParticipantContractToModel(participant);
        verify(mockService).save(savedParticipant);
        verify(mockMapper).mapParticipantModelToContract(savedParticipant);
    }
}
