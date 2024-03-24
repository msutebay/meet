package org.ssb.meet.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.ssb.meet.openapi.model.ApiError;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex) {
        ApiError error = new ApiError(ex.getMessage());
        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(MeetingRoomNotAvailableException.class)
    ResponseEntity<ApiError> handleMeetingRoomNotAvailableException(MeetingRoomNotAvailableException ex) {
        ApiError error = new ApiError(ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ParticipantNotAvailableException.class)
    ResponseEntity<ApiError> handleParticipantNotAvailableException(ParticipantNotAvailableException ex) {
        ApiError error = new ApiError(ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}