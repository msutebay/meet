package org.ssb.meet.exception;

public class ParticipantNotAvailableException extends Exception{
    public ParticipantNotAvailableException(String errorMessage) {
        super(errorMessage);
    }
}
